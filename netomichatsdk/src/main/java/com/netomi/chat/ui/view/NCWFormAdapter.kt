package com.netomi.chat.ui.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.model.messages.NCWAttachmentList
import com.netomi.chat.model.messages.Properties
import com.netomi.chat.model.messages.Values

import com.netomi.chat.utils.NCWAppConstant.FORM_DATE_FORMAT
import com.netomi.chat.utils.NCWThemeUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class FormData(
    var textInput: String? = null,
    var selectedRadio: String? = null,
    var selectedCheckboxes: List<String> = emptyList(),
    var dropdownSelection: String? = null,
    var dateInput: String? = null,
    var fileUpload: ArrayList<FileUploadData>?= arrayListOf()
)

data class InputField(
    val editText: EditText,
    val errorTextView: TextView
)
data class DateField(
    val dateField: TextView,
    val errorTextView: TextView
)

class NCWFormAdapter(private val items: ArrayList<Component>, val properties: Properties, private val callBack: (Component?) -> Unit, private val formData: (String?, String?, ArrayList<NCWAttachmentList>) -> Unit) : RecyclerView.Adapter<NCWFormAdapter.FormViewHolder>() {


    private val inputValues = mutableMapOf<String, Any?>()
    private val inputValuesSelected = MutableList(items.size) { FormData() }

    fun updateFileUpload(position: Int, updatedFile: FileUploadData) {
      /*  if (position in items.indices) {

        }*/
    }

    fun updateItem(position: Int, component: Component) {
        if (position in items.indices) {
            items[position] = component
            notifyItemChanged(position)
        } else {
            Log.e("Adapter", "Invalid position: $position")
        }
    }

    inner class FormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val formContainer: LinearLayout = itemView.findViewById(R.id.formContainer)


        fun bindForm(component: Component) {
            formContainer.removeAllViews()

            when {
                component.isTextInput() -> createTextInput(component)
                component.isTextArea() -> createTextArea(component)
                component.isRadioGroup() -> createRadioGroup(component)
                component.isCheckboxGroup() -> createCheckboxGroup(component)
                component.isDropdown() -> createDropdown(component)
               component.isDateInput() -> createDateInput(component)
                component.isFileInput() -> createFileInput(component)
            }
            if (adapterPosition == items.lastIndex) {
                addSubmitButtonDynamically(formContainer, itemView.context,properties)
            }
        }


        private fun Component.isTextInput() = component == "input" && type == "text"
        private fun Component.isTextArea() = component == "textarea" && type == "textarea"
        private fun Component.isRadioGroup() = component == "input" && type == "choice" && subType == "radio"
        private fun Component.isCheckboxGroup() = component == "input" && type == "choice" && subType == "checkbox"
        private fun Component.isDropdown() = component == "select" && type == "choice"
        private fun Component.isDateInput() = component == "input" && type == "date"
        private fun Component.isFileInput() = component == "file" && type == "file"


        private fun createTextInput(component: Component) {
            addLabel(component)

            val editText = EditText(itemView.context).apply {
                hint = component.attributes?.get(0)?.value?.toString()?.replace("[", "")?.replace("]", "")
                setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
                createDrawable(this)
                setPadding(16, 30, 16, 30)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                layoutParams = defaultLayoutParams()
            }

            val textView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(editText)
            formContainer.addView(textView)
            if (component.additionalSettings["Required"]?.value == true) {
                inputValues[component.id] = InputField(editText, textView)
                Log.e("ADDDEDD","IFFFFFF")
            }
            else{
                Log.e("ADDDEDD","ELSSEEEE")
            }

            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true
            if (isValidationEnabled) {
                val validations = component.validations ?: emptyList()
                val validationType = validations.firstOrNull()?.type

                val minValidation = if (validationType == "length") validations.find { it.subType == "length-min-char" } else null
                val maxValidation = if (validationType == "length") validations.find { it.subType == "length-max-char" } else null

                val greaterThanValidation = if (validationType == "number") validations.find { it.subType == "greater-than" } else null
                val lessThanValidation = if (validationType == "number") validations.find { it.subType == "less-than" } else null

                editText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val inputText = s.toString()
                        var errorMessage: String? = null

                        when (validationType) {
                            "length" -> {
                                // Validate for length
                                minValidation?.let {
                                    val minLength = it.value[0].toIntOrNull()
                                    if (minLength != null && inputText.length < minLength) {
                                        errorMessage = it.errorMessage
                                    }
                                }
                                if (errorMessage == null) {
                                    maxValidation?.let {
                                        val maxLength = it.value[0].toIntOrNull()
                                        if (maxLength != null && inputText.length > maxLength) {
                                            errorMessage = it.errorMessage
                                        }
                                    }
                                }
                            }

                            "number" -> {
                                // Validate for numbers
                                if (inputText.isNotEmpty()) {
                                    val inputNumber = inputText.toIntOrNull()
                                    if (inputNumber != null) {
                                        greaterThanValidation?.let {
                                            val minValue = it.value[0].toIntOrNull()
                                            if (minValue != null && inputNumber <= minValue) {
                                                errorMessage = it.errorMessage
                                            }
                                        }
                                        if (errorMessage == null) {
                                            lessThanValidation?.let {
                                                val maxValue = it.value[0].toIntOrNull()
                                                if (maxValue != null && inputNumber >= maxValue) {
                                                    errorMessage = it.errorMessage
                                                }
                                            }
                                        }
                                    } else {
                                        errorMessage = "Invalid input. Please enter a valid number."
                                    }
                                }
                            }
                        }

                        // Display or hide error message
                        if (errorMessage != null) {
                            textView.text = errorMessage
                            textView.visibility = View.VISIBLE
                        } else {
                            textView.visibility = View.GONE
                        }

                        // Store valid input value
                        if (errorMessage == null) {
                            inputValuesSelected[adapterPosition].textInput = inputText
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }




            else {
                // If Validation is not enabled, store input without validation
                editText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        inputValuesSelected[adapterPosition].textInput = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
        }

        private fun createTextArea(component: Component) {
            addLabel(component)
            val editText = EditText(itemView.context).apply {
                hint = component.attributes?.get(0)?.value?.toString()?.replace("[", "")?.replace("]", "")
                createDrawable(this)
                setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
                setPadding(16, 16, 16, 16)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                gravity=Gravity.TOP
                layoutParams = defaultLayoutParams()
                minLines = 4
            }
            formContainer.addView(editText)
            inputValues[component.id] = editText
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    inputValuesSelected[adapterPosition].textInput = s.toString()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        private fun createRadioGroup(component: Component) {
            addRadioLabel(component)
            val radioGroup = RadioGroup(itemView.context).apply {
                layoutParams = defaultLayoutParams()
            }
            component.optionList?.forEach { option ->
                val radioButton = RadioButton(itemView.context).apply {
                    text = option.value
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            inputValuesSelected[adapterPosition].selectedRadio = text.toString()
                        }
                    }
                }
                radioGroup.addView(radioButton)
            }
            formContainer.addView(radioGroup)
            inputValues[component.id] = radioGroup

        }

        private fun createCheckboxGroup(component: Component) {
            addRadioLabel(component)

            component.optionList?.forEach { option ->
                val checkBox = CheckBox(itemView.context).apply {
                    text = option.value
                    setOnCheckedChangeListener { _, isChecked ->
                        val selectedCheckboxes = inputValuesSelected[adapterPosition].selectedCheckboxes.toMutableList()
                        if (isChecked) {
                            selectedCheckboxes.add(text.toString())
                        } else {
                            selectedCheckboxes.remove(text.toString())
                        }
                        inputValuesSelected[adapterPosition].selectedCheckboxes = selectedCheckboxes
                    }
                }

                formContainer.addView(checkBox)
                inputValues[component.id] = checkBox

            }
        }
        private fun createDropdown(component: Component) {
            addRadioLabel(component)
            val dropdownView = LayoutInflater.from(itemView.context)
                .inflate(R.layout.custom_dropdown_view, formContainer, false) as LinearLayout

            val rootView: RelativeLayout = dropdownView.findViewById(R.id.selected_view)
            val selectedText: TextView = dropdownView.findViewById(R.id.selected_text)
            val arrowIcon: ImageView = dropdownView.findViewById(R.id.arrow_icon)
            val itemsContainer: LinearLayout = dropdownView.findViewById(R.id.dropdown_items_container)
            var isDropdownOpen = false
            createDrawable(rootView)

            dropdownView.findViewById<RelativeLayout>(R.id.selected_view).setOnClickListener {
                isDropdownOpen = !isDropdownOpen
                itemsContainer.visibility = if (isDropdownOpen) View.VISIBLE else View.GONE
                arrowIcon.setImageResource(
                    if (isDropdownOpen) R.drawable.ic_arrow_dropup else R.drawable.ic_arrow_dropdown
                )
            }
            val options = component.optionList?.map { it.value } ?: emptyList()
            itemsContainer.removeAllViews()
            options.forEach { option ->
                val optionView = TextView(itemView.context).apply {
                    text = option
                    textSize = 14f
                    setPadding(10, 11, 10, 11)
                    setOnClickListener {
                        selectedText.text = option
                        isDropdownOpen = false
                        itemsContainer.visibility = View.GONE
                        arrowIcon.setImageResource(R.drawable.ic_arrow_dropdown)
                        inputValues[component.id] = option
                        inputValuesSelected[adapterPosition].dropdownSelection = option
                    }
                }
                itemsContainer.addView(optionView)
            }

            formContainer.addView(dropdownView)
        }
     /*   private fun createDateInput(component: Component) {
            addLabel(component)

            val textView = TextView(itemView.context).apply {
                setPadding(10, 30, 16, 30)
                createDrawable(this)
                text = FORM_DATE_FORMAT
                layoutParams = defaultLayoutParams()

                setOnClickListener {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, AlertDialog.THEME_HOLO_LIGHT,
                        { _, year, month, dayOfMonth ->
                            val selectedDate = "$dayOfMonth-${month + 1}-$year"
                            text = selectedDate
                            inputValuesSelected[adapterPosition].dateInput = selectedDate
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            val textViewError = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(textView)
            formContainer.addView(textViewError)

            inputValues[component.id] = DateField(textView, textViewError)

            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true
            if (isValidationEnabled) {
                val validations = component.validations ?: emptyList()
                val dateValidation = validations.find { it.subType == "date-in-between" }

                // Add TextWatcher-like validation for date selection
                textView.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val inputDate = textView.text.toString()
                        var errorMessage: String? = null

                        dateValidation?.let {
                            val minDate = it.value[0].takeIf { it.isNotBlank() }?.let { date -> parseDate(date) }
                            val maxDate = it.value[1].takeIf { it.isNotBlank() }?.let { date -> parseDate(date) }
                            val selectedDate = parseDate(inputDate)

                            if (minDate != null && selectedDate != null && selectedDate.before(minDate)) {
                                errorMessage = it.errorMessage
                            }
                            if (maxDate != null && selectedDate != null && selectedDate.after(maxDate)) {
                                errorMessage = it.errorMessage
                            }
                        }

                        // Show or hide error message
                        if (errorMessage != null) {
                            textViewError.text = errorMessage
                            textViewError.visibility = View.VISIBLE
                            textView.text= FORM_DATE_FORMAT
                        } else {
                            textViewError.visibility = View.GONE
                        }

                        // Store valid date input
                        if (errorMessage == null) {
                            inputValuesSelected[adapterPosition].dateInput = inputDate
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
        }*/


        private fun createDateInput(component: Component) {
            addLabel(component)

            lateinit var textViewError: TextView

            // Create the date input TextView
            val textView = TextView(itemView.context).apply {
                setPadding(10, 30, 16, 30)
                createDrawable(this)
                text = FORM_DATE_FORMAT
                layoutParams = defaultLayoutParams()

                setOnClickListener {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, AlertDialog.THEME_HOLO_LIGHT,
                        { _, year, month, dayOfMonth ->
                            val selectedDate = "$dayOfMonth-${month + 1}-$year"
                            val errorMessage = validateDate(selectedDate, component)
                            if (errorMessage == null) {
                                text = selectedDate
                                inputValuesSelected[adapterPosition].dateInput = selectedDate
                                textViewError.visibility = View.GONE
                            } else {
                                textViewError.text = errorMessage
                                textViewError.visibility = View.VISIBLE
                                text = FORM_DATE_FORMAT
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            // Initialize the error TextView
            textViewError = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(textView)
            formContainer.addView(textViewError)

            // Save the reference to input values
            inputValues[component.id] = DateField(textView, textViewError)
        }

        // Helper function to validate date selection
        private fun validateDate(selectedDate: String, component: Component): String? {
            val validations = component.validations ?: emptyList()
            val dateValidation = validations.find { it.subType == "date-in-between" }

            if (dateValidation != null) {
                val minDate = dateValidation.value[0].takeIf { it.isNotBlank() }?.let { parseDate(it) }
                val maxDate = dateValidation.value[1].takeIf { it.isNotBlank() }?.let { parseDate(it) }
                val selectedDateObj = parseDate(selectedDate)

                when {
                    minDate != null && selectedDateObj != null && selectedDateObj.before(minDate) -> {
                        return dateValidation.errorMessage
                    }
                    maxDate != null && selectedDateObj != null && selectedDateObj.after(maxDate) -> {
                        return dateValidation.errorMessage
                    }
                }
            }
            return null
        }



        private fun parseDate(dateString: String): Date? {
            return try {
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateString)
            } catch (e: ParseException) {
                null
            }
        }



        private fun createFileInput(component: Component) {
            val fileInputView = LayoutInflater.from(itemView.context)
                .inflate(R.layout.custom_file_input_view, formContainer, false) as ConstraintLayout

            val uploadIcon: ImageView = fileInputView.findViewById(R.id.upload_icon)
            val uploadText: TextView = fileInputView.findViewById(R.id.upload_text)
            val formatHint: TextView = fileInputView.findViewById(R.id.format_hint)
            val recyclerDoc: RecyclerView = fileInputView.findViewById(R.id.recyclerDoc)

            NCWThemeUtils.setUserConfigTextColor(uploadText)
            NCWThemeUtils.setTimeStampColor(formatHint)

            formatHint.text="Format:"+""+ (component.config?.attachmentTypes?.joinToString(",") ?:"")
            Log.d("FileUpload", "Before files: ${component.fileUpload}")
            try {
                if (!component.fileUpload.isNullOrEmpty()) {
                    Log.d("FileUpload", "All files: ${component.fileUpload}")
                    recyclerDoc.visibility = View.VISIBLE

                    // Update the input values for the adapter position
                    inputValuesSelected.getOrNull(adapterPosition)?.fileUpload?.apply {
                        clear()
                        addAll(component.fileUpload!!)
                    }

                    // Set up or update the adapter
                    if (recyclerDoc.adapter == null) {
                        recyclerDoc.layoutManager = LinearLayoutManager(
                            itemView.context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        recyclerDoc.adapter = NCWFormFilesAdapter(component.fileUpload!!) { selectedOption ->
                            Log.d("SelectedOption", "Selected file: $selectedOption")
                            component.fileUpload?.remove(selectedOption)
                            inputValuesSelected.getOrNull(adapterPosition)?.fileUpload?.remove(selectedOption)
                            (recyclerDoc.adapter as? NCWFormFilesAdapter)?.notifyDataSetChanged()
                        }
                    } else {
                        recyclerDoc.adapter!!.notifyDataSetChanged()
                    }
                } else {
                    recyclerDoc.visibility = View.GONE
                }
            } catch (ex: Exception) {
                Log.e("Exception", "Error inflating file input: ${ex.message}", ex)
            }



            /* try {
                 if (!component.fileUpload.isNullOrEmpty()) {
                     Log.d("FileUpload", "All files: ${component.fileUpload}")
                     recyclerDoc.visibility = View.VISIBLE

                     inputValuesSelected[adapterPosition].fileUpload?.addAll(component.fileUpload!!)
                     if (recyclerDoc.adapter == null) {
                         recyclerDoc.layoutManager = LinearLayoutManager(
                             itemView.context,
                             LinearLayoutManager.VERTICAL,
                             false
                         )
                         recyclerDoc.adapter = NCWFormFilesAdapter(component.fileUpload!!) { selectedOption ->
                             Log.d("SelectedOption", "Selected file: $selectedOption")
                             component.fileUpload?.remove(selectedOption)
                             inputValuesSelected[adapterPosition].fileUpload?.remove(selectedOption)


                         }
                     } else {
                         recyclerDoc.adapter!!.notifyDataSetChanged()
                         // (recyclerDoc.adapter as? NCWFormFilesAdapter)?.updateData(component.fileUpload)
                     }
                 } else {
                     recyclerDoc.visibility = View.GONE
                 }
             } catch (ex: Exception) {
                 Log.e("Exception", "Error inflating file input: ${ex.message}", ex)
             }*/

            uploadIcon.setOnClickListener {
                callBack(component) // Trigger the callback when upload icon is clicked
            }

            formContainer.addView(fileInputView)
            inputValues[component.id] = fileInputView
        }

        private fun addLabel(component: Component) {
            val textView = TextView(itemView.context).apply {
                text = component.labels?.firstOrNull() ?: "Label"
                typeface = Typeface.DEFAULT_BOLD
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                layoutParams=defaultLayoutParams()
            }
            NCWThemeUtils.setBotTextColor(textView)
            formContainer.addView(textView)
        }
        private fun addRadioLabel(component: Component) {
            val textView = TextView(itemView.context).apply {
                text = component.groupLabel
                layoutParams = defaultLayoutParams()
                typeface = Typeface.DEFAULT_BOLD
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                layoutParams=defaultLayoutParams()
            }
            NCWThemeUtils.setBotTextColor(textView)
            formContainer.addView(textView)
        }

        private fun defaultLayoutParams() = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 15, 0, 0) }

        private fun addSubmitButtonDynamically(
            parentLayout: LinearLayout,
            context: Context,
            properties: Properties
        ) {

            val btnSubmit = TextView(context).apply {
                text = "Submit"
                id = View.generateViewId()
                gravity = Gravity.CENTER
                setPadding(10, 30, 16, 30)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(10, 30, 16, 30) // Add padding/margins as needed
                }
                NCWThemeUtils.createRoundedDrawable(this)
            }



            parentLayout.addView(btnSubmit)
            setupSubmitButton(btnSubmit,parentLayout,properties)
        }
        private fun setupSubmitButton(
            btnSubmit: TextView,
            parentLayout: LinearLayout,
            properties: Properties
        ) {
            btnSubmit.setOnClickListener {
                Log.e("CheckList", "InputValues Size: ${inputValues.size}")
                if (validateInputs(inputValues)) {
                    Log.e("IsValidte","I am true")
                    // Create the payload and label response
                    val messagePayload = createPayload(inputValuesSelected)
                Log.e("MessagePayload","ssa "+messagePayload)
                    val labelResponse = createLabelResponse(inputValuesSelected)
                  val attachePayload=createNCWAttachmentList(inputValuesSelected,properties.intentId)
                Log.e("CreatePpppp","sss "+attachePayload)
                  val attachmentList = arrayListOf(attachePayload)
                    formData(messagePayload, labelResponse,attachmentList)
                    btnSubmit.visibility=View.GONE
                    showFormSubmittedView(parentLayout, btnSubmit.context)
               }
                else{
                    Log.e("IsValidte","I am falseee")
                }


            }
        }

        fun createNCWAttachmentList(
            inputValuesSelected: List<FormData>,
            attachmentId: String,
        ): NCWAttachmentList {
            /*val formValues = mutableListOf<String>()
            inputValuesSelected.forEach { formData ->
                formData.textInput?.let { formValues.add(it) }
                formData.selectedRadio?.let { formValues.add(it) }
                formData.selectedCheckboxes.forEach { formValues.add(it) }
                formData.dropdownSelection?.let { formValues.add(it) }
                formData.dateInput?.let { formValues.add(it) }
                formData.fileUpload?.mapNotNull { it.fileUrl }?.joinToString(",").let { formValues.add(it.toString()) }
            }*/
           /* val formValues = mutableListOf<String>()
            inputValuesSelected.forEach { formData ->
                formData.textInput?.let { formValues.add(it) } // Add single text input
                formData.selectedRadio?.let { formValues.add(it) } // Add single selected radio
                formData.selectedCheckboxes.joinToString(",").let { formValues.add(it) } // Join checkboxes into a comma-separated string
                formData.dropdownSelection?.let { formValues.add(it) } // Add single dropdown selection
                formData.dateInput?.let { formValues.add(it) } // Add single date input
                formData.fileUpload?.mapNotNull { it.fileUrl }
                    ?.joinToString(",")?.let { formValues.add(it) } // Join file URLs into a comma-separated string
            }
            Log.e("InputSelected","formData "+formValues)*/
            val formValues = mutableListOf<String>()
            inputValuesSelected.forEach { formData ->
                formData.textInput?.takeIf { it.isNotBlank() }?.let { formValues.add(it) } // Add only non-blank text input
                formData.selectedRadio?.takeIf { it.isNotBlank() }?.let { formValues.add(it) } // Add only non-blank selected radio
                formData.selectedCheckboxes
                    .filter { it.isNotBlank() }
                    .joinToString(",")
                    .takeIf { it.isNotBlank() }?.let { formValues.add(it) } // Add only non-blank checkboxes
                formData.dropdownSelection?.takeIf { it.isNotBlank() }?.let { formValues.add(it) } // Add only non-blank dropdown selection
                formData.dateInput?.takeIf { it.isNotBlank() }?.let { formValues.add(it) } // Add only non-blank date input
                formData.fileUpload
                    ?.mapNotNull { it.fileUrl }
                    ?.filter { it.isNotBlank() }
                    ?.joinToString(",")
                    ?.takeIf { it.isNotBlank() }?.let { formValues.add(it) } // Add only non-blank file URLs
            }

            val nonEmptyFormValues = formValues.filter { it.isNotBlank() }

            val schemaFormRequestId = items.map { it.id }

            return NCWAttachmentList(
                type = "ai.msg.domain.responses.core.MessageInfoAttachment",
                values = Values(
                    status = listOf("SUBMITTED"),
                    isSchemaForm = listOf("true"),
                    schemaFormRequestId = schemaFormRequestId,
                    question = listOf(properties.question?:""),
                    optionList = inputValuesSelected.flatMap { it.selectedCheckboxes },
                    formValues = nonEmptyFormValues
                ),
                attachmentId = attachmentId,
                timestamp = System.currentTimeMillis(),
            )
        }

        private fun showFormSubmittedView(parentLayout: LinearLayout, context: Context) {
            val horizontalLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.END
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 20, 0, 20)
                }
            }

            val successMessage = TextView(context).apply {
                text = "Form Submitted Successfully"
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setTextColor(ContextCompat.getColor(context, R.color.black))
                gravity = Gravity.CENTER_VERTICAL // Align text vertically in the middle
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 10, 0) // Add some space between text and icon
                }
            }

            val successIcon = ImageView(context).apply {
                setImageResource(R.drawable.ic_assistant)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER_VERTICAL // Align icon vertically in the middle
                }
            }

            // Add text and icon to the horizontal layout
            horizontalLayout.addView(successMessage)
            horizontalLayout.addView(successIcon)

            // Add the horizontal layout to the parent layout
            parentLayout.addView(horizontalLayout)
        }



        fun createPayload(inputValuesSelected: List<FormData>): String {
            val stringBuilder = StringBuilder("event://;LEARN_ATTRIBUTE_EVENT;")

            // Iterate over each FormData object
            inputValuesSelected.forEach { formData ->
                // Add text input
                formData.textInput?.let {
                    stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$it&")
                }

                // Add selected radio
                formData.selectedRadio?.let {
                    stringBuilder.append("DEFAULT_OUTPUT_KEY_SELECT::value=$it&")
                }

                // Add selected checkboxes
                if (formData.selectedCheckboxes.isNotEmpty()) {
                    val checkboxes = formData.selectedCheckboxes.joinToString(",")
                    stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$checkboxes&")
                }

                // Add dropdown selection
                formData.dropdownSelection?.let {
                    stringBuilder.append("DEFAULT_OUTPUT_KEY_SELECT::value=$it&")
                }

                // Add date input
                formData.dateInput?.let {
                    stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$it&")
                }

                // Add file URLs
                formData.fileUpload?.let { fileUploads ->
                    if (fileUploads.isNotEmpty()) {
                        val fileUrls = fileUploads.mapNotNull { it.fileUrl }.joinToString(",")
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_PILL::value=$fileUrls&")
                    }
                }
            }

            // Remove the last '&' if it exists
            if (stringBuilder.endsWith("&")) {
                stringBuilder.setLength(stringBuilder.length - 1)
            }

            return stringBuilder.toString()
        }

        fun createLabelResponse(inputValuesSelected: List<FormData>): String {
            val stringBuilder = StringBuilder()

            // Iterate over each FormData object
            inputValuesSelected.forEach { formData ->
                // Add text input
                formData.textInput?.let {
                    stringBuilder.append("$it\n")
                }

                // Add selected radio
                formData.selectedRadio?.let {
                    stringBuilder.append("$it\n")
                }

                // Add selected checkboxes
                if (formData.selectedCheckboxes.isNotEmpty()) {
                    val checkboxes = formData.selectedCheckboxes.joinToString(",")
                    stringBuilder.append("$checkboxes\n")
                }

                // Add dropdown selection
                formData.dropdownSelection?.let {
                    stringBuilder.append("$it\n")
                }

                // Add date input
                formData.dateInput?.let {
                    stringBuilder.append("$it\n")
                }

                // Add file URLs
                formData.fileUpload?.let { fileUploads ->
                    if (fileUploads.isNotEmpty()) {
                        val fileUrls = fileUploads.mapNotNull { it.fileUrl }.joinToString(", ")
                        stringBuilder.append("$fileUrls\n")
                    }
                }
            }

            // Remove the last newline character if it exists
            if (stringBuilder.isNotEmpty()) {
                stringBuilder.setLength(stringBuilder.length - 1)
            }

            return stringBuilder.toString()
        }

        private fun validateInputs(inputValues: Map<String, Any?>): Boolean {
            var isValid = true

            inputValues.forEach { (key, value) ->
                when (value) {
                    is InputField -> {
                        Log.e("Hereee", "editextt")
                        items.forEach { item ->
                            if (item.id == key) {
                                Log.e("Hereee", "editextt if")
                                if (item.additionalSettings["Required"]?.value == true) {
                                    Log.e("Hereee", "editextt ifFFFFF")
                                    val editText = value.editText
                                    val errorTextView = value.errorTextView
                                    if (editText.text.isNullOrBlank()) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = "This field is required"
                                        isValid = false // Set to false only if there's an error
                                    } else {
                                        errorTextView.visibility = View.GONE
                                    }
                                } else {
                                    Log.e("Hereee", "editextt aEllslslslss")
                                }
                            }
                        }
                    }
                    is DateField -> {
                        Log.e("Hereee", "DateField")
                        items.forEach { item ->
                            if (item.id == key) {
                                if (item.additionalSettings["Required"]?.value == true) {
                                    val editText = value.dateField
                                    val errorTextView = value.errorTextView
                                    Log.e("DateField", "dsdsdssd" + editText.text)
                                    if (editText.text == FORM_DATE_FORMAT) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = "This field is required"
                                        isValid = false // Set to false only if there's an error
                                    } else {
                                        errorTextView.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return isValid
        }



        private fun createDrawable(view :View){
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 15f
                setStroke(1, Color.BLACK)
                setColor(Color.WHITE)
            }
            view.background=drawable

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ncw_form_item, parent, false)
        return FormViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        holder.bindForm(items[position])
    }

    override fun getItemCount() = items.size

}
