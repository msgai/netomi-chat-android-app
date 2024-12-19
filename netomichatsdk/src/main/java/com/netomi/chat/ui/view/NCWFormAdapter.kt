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
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.model.messages.FormSchema
import com.netomi.chat.model.messages.NCWAttachmentList
import com.netomi.chat.model.messages.Validation
import com.netomi.chat.model.messages.Values
import com.netomi.chat.utils.NCWAppConstant.FORM_DATE_FORMAT
import com.netomi.chat.utils.NCWParsingUtils
import com.netomi.chat.utils.NCWParsingUtils.parseDate
import com.netomi.chat.utils.NCWThemeUtils
import java.util.Calendar

data class FormData(
    var textInput: String? = null,
    var textAreaInput: String? = null,
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
data class TextAreaInputField(
    val editText: EditText,
    val errorTextView: TextView
)
data class DateField(
    val dateField: TextView,
    val errorTextView: TextView
)

class NCWFormAdapter(private val items: ArrayList<Component>, val formSchema: FormSchema, private val callBack: (Component?) -> Unit, private val formData: (String?, String?, ArrayList<NCWAttachmentList>) -> Unit) : RecyclerView.Adapter<NCWFormAdapter.FormViewHolder>() {


    private val inputValues = mutableMapOf<String, Any?>()
    private val inputValuesSelected = MutableList(items.size) { FormData() }

    var isClickable: Boolean = true

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
                addSubmitButtonDynamically(formContainer, itemView.context,formSchema)
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
                hint = component.attributes?.getOrNull(0)?.value?.toString()?.removeSurrounding("[", "]")
                setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
                createDrawable(this)
                setPadding(16, 30, 16, 30)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                layoutParams = defaultLayoutParams()
            }

            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(editText)
            formContainer.addView(errorTextView)

            if (component.additionalSettings["Required"]?.value == true) {
                inputValues[component.id] = InputField(editText, errorTextView)
            }

            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true
            if (isValidationEnabled) {
                val validations = component.validations.orEmpty()
                editText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val inputText = s.toString()
                        if (inputText.isNotEmpty()) {
                            val errorMessage = inputFieldValidation(inputText, validations)

                            if (errorMessage != null) {
                                errorTextView.text = errorMessage
                                errorTextView.visibility = View.VISIBLE
                            } else {
                                errorTextView.visibility = View.GONE
                                inputValuesSelected[adapterPosition].textInput = inputText
                            }
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            } else {
                editText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        inputValuesSelected[adapterPosition].textInput = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }

            formSchema.formData?.getOrNull(adapterPosition)?.textInput?.let { textInput ->
                editText.setText(textInput)
            }

            editText.isEnabled = isClickable
        }



        private fun createTextArea(component: Component) {
            addLabel(component)

            val editText = EditText(itemView.context).apply {
                hint = component.attributes?.getOrNull(0)?.value?.toString()?.replace("[", "")?.replace("]", "")
                createDrawable(this)
                setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
                setPadding(16, 16, 16, 16)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                gravity = Gravity.TOP
                layoutParams = defaultLayoutParams()
                minLines = 4
                isEnabled = isClickable
            }

            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(editText)
            formContainer.addView(errorTextView)

            // Store the input field in the map
            inputValues[component.id] = if (component.additionalSettings["Required"]?.value == true) {
                TextAreaInputField(editText, errorTextView)
            } else {
                editText
            }

            // Check if validation is enabled
            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true
            if (isValidationEnabled) {
                setupValidation(editText, errorTextView, component.validations)
            } else {
                // Handle simple text change without validation
                editText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        inputValuesSelected[adapterPosition].textAreaInput = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }

            // Restore previous input value if available
            formSchema.formData?.get(adapterPosition)?.textAreaInput?.let {
                editText.setText(it)
            }
            editText.isEnabled = isClickable
        }

        private fun setupValidation(
            editText: EditText,
            errorTextView: TextView,
            validations: List<Validation>?
        ) {
            if (validations.isNullOrEmpty()) return

            val textContainsValidation = validations.find { it.type == "text" && it.subType == "text-contains" }

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val inputText = s.toString()
                    val errorMessage = validateInput(inputText, textContainsValidation)

                    if (errorMessage != null) {
                        errorTextView.text = errorMessage
                        errorTextView.visibility = View.VISIBLE
                    } else {
                        errorTextView.visibility = View.GONE
                        inputValuesSelected[adapterPosition].textAreaInput = inputText
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        private fun validateInput(inputText: String, validation: Validation?): String? {
            if (validation == null) return null

            // Ensure the inputText contains at least one value in the validation list
            return if (validation.value.any { inputText.contains(it, ignoreCase = true) }) {
                null
            } else {
                validation.errorMessage
            }
        }

        private fun createRadioGroup(component: Component) {
            addRadioLabel(component)

            val radioGroup = RadioGroup(itemView.context).apply {
                layoutParams = defaultLayoutParams()
            }
            val isRadiaClickable=isClickable

            component.optionList?.forEach { option ->
                val radioButton = RadioButton(itemView.context).apply {
                    text = option.value
                    isEnabled = isRadiaClickable
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

            if (adapterPosition in (formSchema.formData?.indices ?: emptyList())) {
                val radioSelected = formSchema.formData?.get(adapterPosition)?.selectedRadio
                radioGroup.children.forEach { view ->
                    if (view is RadioButton) {
                        view.isChecked = view.text == radioSelected
                    }
                }
            }
        }

        private fun createCheckboxGroup(component: Component) {
            addRadioLabel(component)

            // Retrieve previously selected checkboxes if available
            val checkBoxSelected = formSchema.formData?.get(adapterPosition)?.textInput
            val previouslySelectedCheckboxes = checkBoxSelected?.split(",")?.map { it.trim() } ?: emptyList()

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

                    // Set the checkbox as checked if it was previously selected
                    isChecked = previouslySelectedCheckboxes.contains(text.toString())
                }

                formContainer.addView(checkBox)
                inputValues[component.id] = checkBox
                checkBox.isEnabled=isClickable

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
                if (isClickable) {
                    isDropdownOpen = !isDropdownOpen
                    itemsContainer.visibility = if (isDropdownOpen) View.VISIBLE else View.GONE
                    arrowIcon.setImageResource(
                        if (isDropdownOpen) R.drawable.ic_arrow_dropup else R.drawable.ic_arrow_dropdown
                    )
                }
            }
            val options = component.optionList?.map { it.value } ?: emptyList()
            itemsContainer.removeAllViews()
            options.forEach { option ->
                val optionView = TextView(itemView.context).apply {
                    text = option
                    textSize = 14f
                    setPadding(10, 16, 10, 16)
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

          dropdownView.isEnabled=isClickable
            if (adapterPosition in (formSchema.formData?.indices ?: emptyList())) {
              val selectedDropDown=formSchema.formData?.get(adapterPosition)?.selectedRadio
              selectedText.text=selectedDropDown
          }

        }

        private fun createDateInput(component: Component) {
            addLabel(component)

            lateinit var textViewError: TextView

            // Create the date input TextView
            val textView = TextView(itemView.context).apply {
                setPadding(16, 30, 16, 30)
                createDrawable(this)
                text = FORM_DATE_FORMAT
                layoutParams = defaultLayoutParams()

                setOnClickListener {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, AlertDialog.THEME_HOLO_LIGHT,
                        { _, year, month, dayOfMonth ->
                         //   val selectedDate = "$dayOfMonth-${month + 1}-$year"
                            val selectedDate = "${month + 1}-$dayOfMonth-$year"
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
            textView.isEnabled=isClickable
            formContainer.addView(textView)
            formContainer.addView(textViewError)

            inputValues[component.id] = DateField(textView, textViewError)

            if (adapterPosition in (formSchema.formData?.indices ?: emptyList())) {
                val textInput = formSchema.formData?.get(adapterPosition)?.textInput
                if (textInput != null) {
                    textView.text = textInput.toString()
                }
            }


        }


        private fun inputFieldValidation(inputText: String, validations: List<Validation>): String? {
            validations.forEach { validation ->
                when (validation.type) {
                    "length" -> {
                        when (validation.subType) {
                            "length-min-char" -> {
                                val minLength = validation.value.getOrNull(0)?.toIntOrNull()
                                if (minLength != null && inputText.length < minLength) {
                                    return validation.errorMessage
                                }
                            }
                            "length-max-char" -> {
                                val maxLength = validation.value.getOrNull(0)?.toIntOrNull()
                                if (maxLength != null && inputText.length > maxLength) {
                                    return validation.errorMessage
                                }
                            }
                        }
                    }

                    "number" -> {
                        val inputNumber = inputText.toIntOrNull()
                        if (inputNumber != null) {
                            when (validation.subType) {
                                "greater-than" -> {
                                    val minValue = validation.value.getOrNull(0)?.toIntOrNull()
                                    if (minValue != null && inputNumber <= minValue) {
                                        return validation.errorMessage
                                    }
                                }
                                "less-than" -> {
                                    val maxValue = validation.value.getOrNull(0)?.toIntOrNull()
                                    if (maxValue != null && inputNumber >= maxValue) {
                                        return validation.errorMessage
                                    }
                                }
                            }
                        } else if (inputText.isNotEmpty()) {
                            return "Invalid input. Please enter a valid number."
                        }
                    }

                    "text" -> {
                        when (validation.subType) {
                            "text-contains" -> {
                                val requiredText = validation.value.getOrNull(0).orEmpty()
                                if (!inputText.contains(requiredText)) {
                                    return validation.errorMessage
                                }
                            }
                            "text-doesnt-contains" -> {
                                val forbiddenText = validation.value.getOrNull(0).orEmpty()
                                if (inputText.contains(forbiddenText)) {
                                    return validation.errorMessage
                                }
                            }
                        }
                    }

                    "regex" -> {
                        when (validation.subType) {
                            "regex-contains" -> {
                                val pattern = validation.value.getOrNull(0)?.toRegex() ?: return null
                                if (!pattern.containsMatchIn(inputText)) {
                                    return validation.errorMessage
                                }
                            }
                        }
                    }
                }
            }
            return null
        }

        private fun validateDate(selectedDate: String, component: Component): String? {
            val validations = component.validations ?: emptyList()

            for (validation in validations) {
                val selectedDateObj = parseDate(selectedDate) ?: continue
                val minDate = validation.value.getOrNull(0)?.takeIf { it.isNotBlank() }?.let { parseDate(it) }
                val maxDate = validation.value.getOrNull(1)?.takeIf { it.isNotBlank() }?.let { parseDate(it) }

                when (validation.subType) {
                    "date-in-between" -> {
                        if ((minDate != null && selectedDateObj.before(minDate)) ||
                            (maxDate != null && selectedDateObj.after(maxDate))
                        ) {
                            return validation.errorMessage
                        }
                    }

                    "date-greater-than-or-equal" -> {
                        if (minDate != null && selectedDateObj.before(minDate)) {
                            return validation.errorMessage
                        }
                    }

                    "date-less-than-or-equal" -> {
                        if (maxDate != null && selectedDateObj.after(maxDate)) {
                            return validation.errorMessage
                        }
                    }
                }
            }
            return null
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

            formatHint.text = "Format: ${component.config?.attachmentTypes?.joinToString(",") ?: ""}"

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
                        recyclerDoc.adapter = NCWFormFilesAdapter(component.fileUpload!!,isClickable) { selectedOption ->
                            Log.d("SelectedOption", "Selected file: $selectedOption")
                            component.fileUpload?.remove(selectedOption)
                            inputValuesSelected.getOrNull(adapterPosition)?.fileUpload?.remove(selectedOption)
                            notifyDataSetChanged()
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

            formSchema.formData?.get(adapterPosition)?.fileUpload?.let {
                recyclerDoc.visibility = View.VISIBLE
                if (recyclerDoc.adapter == null) {
                    recyclerDoc.layoutManager = LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    recyclerDoc.adapter = NCWFormFilesAdapter(formSchema.formData?.get(adapterPosition)?.fileUpload!!,isClickable){

                    }

                }

            }




            uploadIcon.setOnClickListener {
                callBack(component) // Trigger the callback when upload icon is clicked
            }
            uploadIcon.isEnabled=isClickable
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
            formSchecma: FormSchema
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

            btnSubmit.isEnabled=isClickable

            parentLayout.addView(btnSubmit)
            setupSubmitButton(btnSubmit,parentLayout,formSchecma)
        }
        private fun setupSubmitButton(
            btnSubmit: TextView,
            parentLayout: LinearLayout,
            formSchema: FormSchema
        ) {

            if(formSchema.formData.isNullOrEmpty()) {
                isClickable =true
            } else {
                btnSubmit.visibility=View.GONE
                isClickable =false
                showFormSubmittedView(parentLayout, btnSubmit.context)
            }


            btnSubmit.setOnClickListener {
                Log.e("CheckList", "InputValues Size: ${inputValues.size}")
                if (validateInputs(inputValues)) {
                    // Create the payload and label response
                    val messagePayload = createPayload(inputValuesSelected)
                    val labelResponse = createLabelResponse(inputValuesSelected)
                  val attachePayload=createNCWAttachmentList(inputValuesSelected,formSchema.properties.intentId)

                  val attachmentList = arrayListOf(attachePayload)
                    formData(messagePayload, labelResponse,attachmentList)
                    btnSubmit.visibility=View.GONE
                    showFormSubmittedView(parentLayout, btnSubmit.context)
                    val formData= messagePayload.let {
                        NCWParsingUtils.parsePayloadToFormData(
                            it
                        )
                    }
                    this@NCWFormAdapter.formSchema.formData=formData


               }
                else{
                    Log.e("IsValidte","I am falseee")
                }


            }


        }

        private fun createNCWAttachmentList(
            inputValuesSelected: List<FormData>,
            attachmentId: String,
        ): NCWAttachmentList {
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
                    question = listOf(formSchema.properties.question?:""),
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
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
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
                setImageResource(R.drawable.ic_form_submitted)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
            }

            // Add text and icon to the horizontal layout
            horizontalLayout.addView(successMessage)
            horizontalLayout.addView(successIcon)

            // Add the horizontal layout to the parent layout
            parentLayout.addView(horizontalLayout)

        }
        private fun createPayload(inputValuesSelected: List<FormData>): String {
            val stringBuilder = StringBuilder("event://;LEARN_ATTRIBUTE_EVENT;")

            inputValuesSelected.forEachIndexed { index, formData ->
                val item = items[index] // Avoid repetitive indexing

                when {
                    item.isTextInput() -> {
                        val value = formData.textInput.orEmpty()
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$value&")
                    }
                    item.isTextArea() -> {
                        val value = formData.textAreaInput.orEmpty()
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_TEXTAREA::value=$value&")
                    }
                    item.isRadioGroup() -> {
                        val value = formData.selectedRadio.orEmpty()
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_SELECT::value=$value&")
                    }
                    item.isCheckboxGroup() -> {
                        val value = if (formData.selectedCheckboxes.isNotEmpty()) {
                            formData.selectedCheckboxes.joinToString(",")
                        } else ""
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$value&")
                    }
                    item.isDropdown() -> {
                        val value = formData.dropdownSelection.orEmpty()
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_SELECT::value=$value&")
                    }
                    item.isDateInput() -> {
                        val value = formData.dateInput.orEmpty()
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$value&")
                    }
                    item.isFileInput() -> {
                        val value = formData.fileUpload?.mapNotNull { it.fileUrl }?.joinToString(",").orEmpty()
                        stringBuilder.append("DEFAULT_OUTPUT_KEY_PILL::value=$value&")
                    }
                }
            }

            if (stringBuilder.endsWith("&")) {
                stringBuilder.setLength(stringBuilder.length - 1)
            }

            return stringBuilder.toString()
        }

        private fun createLabelResponse(inputValuesSelected: List<FormData>): String {
            val stringBuilder = StringBuilder()

            inputValuesSelected.forEach { formData ->
                formData.textInput?.let {
                    stringBuilder.append("$it\n")
                }
                formData.textAreaInput?.let {
                    stringBuilder.append("$it\n")
                }
                formData.selectedRadio?.let {
                    stringBuilder.append("$it\n")
                }

                if (formData.selectedCheckboxes.isNotEmpty()) {
                    val checkboxes = formData.selectedCheckboxes.joinToString(",")
                    stringBuilder.append("$checkboxes\n")
                }
                formData.dropdownSelection?.let {
                    stringBuilder.append("$it\n")
                }

                formData.dateInput?.let {
                    stringBuilder.append("$it\n")
                }

                formData.fileUpload?.let { fileUploads ->
                    if (fileUploads.isNotEmpty()) {
                        val fileUrls = fileUploads.mapNotNull { it.fileUrl }.joinToString(", ")
                        stringBuilder.append("$fileUrls\n")
                    }
                }
            }

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
                        items.forEach { item ->
                            if (item.id == key) {
                                if (item.additionalSettings["Required"]?.value == true) {
                                    val editText = value.editText
                                    val errorTextView = value.errorTextView
                                    if (editText.text.isNullOrBlank()) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = "This field is required"
                                        isValid = false
                                    } else {
                                        errorTextView.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    }
                    is DateField -> {
                        items.forEach { item ->
                            if (item.id == key) {
                                if (item.additionalSettings["Required"]?.value == true) {
                                    val editText = value.dateField
                                    val errorTextView = value.errorTextView
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

        val isDisabled = !formSchema.formData.isNullOrEmpty() // Pass this flag from the parent

        holder.itemView.isClickable = !isDisabled
        holder.itemView.isEnabled = !isDisabled

        // Disable child views if needed
        holder.itemView.setOnTouchListener(if (isDisabled) { _, _ -> true } else null)
    }

    override fun getItemCount() = items.size

}
