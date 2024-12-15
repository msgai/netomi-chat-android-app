package com.netomi.chat.ui.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
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
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileUploadData

import com.netomi.chat.utils.NCWAppConstant.FORM_DATE_FORMAT
import com.netomi.chat.utils.NCWThemeUtils
import java.util.Calendar

data class FormData(
    var textInput: String? = null,
    var selectedRadio: String? = null,
    var selectedCheckboxes: List<String> = emptyList(),
    var dropdownSelection: String? = null,
    var dateInput: String? = null,
    var fileUpload: ArrayList<FileUploadData>?= arrayListOf()
)

class NCWFormAdapter(private val items: ArrayList<Component>, private val callBack: (Component?) -> Unit,private val formData: (String?, String?) -> Unit) : RecyclerView.Adapter<NCWFormAdapter.FormViewHolder>() {


    private val inputValues = mutableMapOf<String, Any?>()
    private val inputValuesSelected = MutableList(items.size) { FormData() }

    fun updateFileUpload(position: Int, updatedFile: FileUploadData) {
      /*  if (position in items.indices) {

        }*/
    }

    fun updateItem(position: Int, component: Component) {
        items[position] = component
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
                addSubmitButtonDynamically(formContainer, itemView.context, component)
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
            inputValues[component.id] = editText

            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true
            if (isValidationEnabled) {
                val validations = component.validations ?: emptyList()
                val minValidation = validations.find { it.subType == "length-min-char" }
                val maxValidation = validations.find { it.subType == "length-max-char" }

                // Add TextWatcher to validate input dynamically
                editText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val inputText = s.toString()
                        var errorMessage: String? = null

                        // Check for min length
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
            } else {
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

        private fun createDateInput(component: Component) {
            addLabel(component)
            val textView = TextView(itemView.context).apply {
                setPadding(10, 30, 16, 30)
                createDrawable(this)
                text = FORM_DATE_FORMAT

                setOnClickListener {
                   /* NCWDatePickerUtil.showDatePicker(itemView.context) { selectedDate ->
                        text = selectedDate
                        inputValues[component.id] = selectedDate

                    }*/
                   val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, AlertDialog.THEME_HOLO_LIGHT,
                        { _, year, month, dayOfMonth ->
                            text = "$dayOfMonth/${month + 1}/$year"
                            inputValues[component.id] = text
                            inputValuesSelected[adapterPosition].dateInput=text.toString()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                layoutParams = defaultLayoutParams()
            }
            formContainer.addView(textView)
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

            try {
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
            }

            uploadIcon.setOnClickListener {
                callBack(component) // Trigger the callback when upload icon is clicked
            }

            formContainer.addView(fileInputView)
            inputValues[component.id] = fileInputView
        }


        /*   private fun createFileInput(component: Component) {
               val fileInputView = LayoutInflater.from(itemView.context)
                   .inflate(R.layout.custom_file_input_view, formContainer, false) as ConstraintLayout

               val uploadIcon: ImageView = fileInputView.findViewById(R.id.upload_icon)
               val uploadText: TextView = fileInputView.findViewById(R.id.upload_text)
               val formatHint: TextView = fileInputView.findViewById(R.id.format_hint)
               val recyclerDoc: RecyclerView = fileInputView.findViewById(R.id.recyclerDoc)

               NCWThemeUtils.setUserConfigTextColor(uploadText)
               NCWThemeUtils.setTimeStampColor(formatHint)


               try {
                   if (component.fileUpload.size>0){
                       Log.e("JASSSSSSS"," All"+component.fileUpload)
                       recyclerDoc.visibility=View.VISIBLE

   Log.e("JASSSSSSS","JASSSSSSS1 "+component.fileUpload)
                       Log.e("fileUpload","fileUpload Size"+ component.fileUpload.size)
                       if (recyclerDoc.adapter == null) {
                           recyclerDoc.layoutManager =
                               LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                       }
                       val chipAdapter = NCWFormFilesAdapter(component.fileUpload) { selectedOption ->
                       }
                       recyclerDoc.adapter = chipAdapter
                   }
                   else{
                       Log.e("JASSSSSSS"," Elseeeeee")
                       recyclerDoc.visibility=View.GONE
                   }

               }
               catch (ex:Exception)
               {

                   Log.e("JASSSSSSS"," Elsesss"+ex.printStackTrace())
               }

               Log.e("GetData","component"+component)

               uploadIcon.setOnClickListener {

                   callBack(component)
                  *//* openFilePicker { fileUri ->
                    // Handle the selected file
                    uploadText.text = fileUri?.lastPathSegment ?: "No file selected"
                    inputValues[component.id] = fileUri
                }*//*
            }
            formContainer.addView(fileInputView)


            inputValues[component.id] = fileInputView
        }*/


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
            component: Component
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
            setupSubmitButton(btnSubmit,component)
        }
        private fun setupSubmitButton(btnSubmit: TextView, component: Component) {
            btnSubmit.setOnClickListener {
                Log.e("CheckList", "InputValues Size: ${inputValues.size}")

                val submissionDataList = mutableListOf<Pair<String, Any?>>()

                inputValuesSelected.forEachIndexed { index, formData ->
                    val component = items[index]
                    submissionDataList.add(Pair(component.id, formData))
                }

                Log.e("CheckList", "All Values $submissionDataList")




                //formData(inputValuesSelected)
                val submissionData = mutableMapOf<String, Any?>()

                inputValues.forEach { (key, value) ->
                    when (value) {
                        is EditText -> {
                            submissionData[key] = value.text.toString() // Get text from EditText
                        }
                        is RadioGroup -> {
                            val selectedId = value.checkedRadioButtonId
                            val selectedRadioButton = value.findViewById<RadioButton>(selectedId)
                            submissionData[key] = selectedRadioButton?.text.toString() // Get selected RadioButton text
                        }
                        is List<*> -> {
                            // Assuming it's a list of CheckBoxes
                            val selectedCheckboxes = value.filterIsInstance<CheckBox>()
                                .filter { it.isChecked }
                                .map { it.text.toString() }
                            submissionData[key] = selectedCheckboxes // Store the list of selected CheckBoxes
                        }
                        is Uri -> {
                            submissionData[key] = value // Store the selected file URI
                        }
                        else -> {
                            Log.e("Dataaa", "Unknown input type: $value")
                        }
                    }
                }
                Log.e("Dataaa", "Submission Data: $submissionData") // Log the submission data

                // Create the payload and label response
                val messagePayload = createPayload(inputValuesSelected)
                val labelResponse = createLabelResponse(inputValuesSelected)

                // Call the formData callback with the payload and label
                formData(messagePayload, labelResponse)


            }
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


        private fun validateInputs(component: Component, inputValues: Map<String, Any?>): Boolean {
            var isValid = true

            inputValues.forEach { (key, value) ->
                when (value) {
                    is EditText -> {
                        if (value.text.isNullOrBlank()) {
                            value.error = "This field is required"
                            isValid = false
                        }
                    }
                    is RadioGroup -> {
                        if (value.checkedRadioButtonId == -1) {
                            Log.e("ValidationError", "RadioGroup with key $key is not selected")
                            isValid = false
                        }
                    }
                    is Spinner -> {
                        if (value.selectedItemPosition == 0) { // Assuming 0 is a placeholder item
                            Toast.makeText(
                                value.context,
                                "Please select a value for ${component.labels?.firstOrNull() ?: "Spinner"}",
                                Toast.LENGTH_SHORT
                            ).show()
                            isValid = false
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
