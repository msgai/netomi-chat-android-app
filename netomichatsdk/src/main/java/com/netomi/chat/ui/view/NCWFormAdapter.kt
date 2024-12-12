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
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Component
import com.netomi.chat.utils.NCWDatePickerUtil
import com.netomi.chat.utils.NCWThemeUtils
import java.util.Calendar

data class FormData(
    var textInput: String? = null,
    var selectedRadio: String? = null,
    var selectedCheckboxes: List<String> = emptyList(),
    var fileUri: Uri? = null,
    var dropdownSelection: String? = null,
    var dateInput: String? = null
)

class NCWFormAdapter(private val items: List<Component>) : RecyclerView.Adapter<NCWFormAdapter.FormViewHolder>() {


    private val inputValues = mutableMapOf<String, Any?>()
    private val inputValuesSelected = MutableList(items.size) { FormData() } // List to hold FormData for each item

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

            // Check if Validation is enabled
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

                        // Check for max length if no min error
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


        /*  private fun createTextInput(component: Component) {
              addLabel(component)

              // Create EditText and TextView for input and validation messages
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

              // Add validation if present
              val validations = component.validations
              val minValidation = validations?.find { it.subType == "length-min-char" }
              val maxValidation = validations?.find { it.subType == "length-max-char" }

              if (component.Validation.name=="Validation"&& component.Validation.value)

              formContainer.addView(editText)
              formContainer.addView(textView)
              inputValues[component.id] = editText

              // Add TextWatcher to validate input dynamically
              editText.addTextChangedListener(object : TextWatcher {
                  override fun afterTextChanged(s: Editable?) {
                      val inputText = s.toString()
                      var errorMessage: String? = null

                      minValidation?.let {
                          val minLength = it.value[0].toIntOrNull()
                          if (minLength != null && inputText.length < minLength) {
                              errorMessage = it.errorMessage
                          }
                      }

                      // Check for max length if no min error
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
          }*/


        /* private fun createTextInput(component: Component) {
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

             // Add validation if present
             val validations = component.validations
             val minValidation = validations?.find { it.subType == "length-min-char" }
             val maxValidation = validations?.find { it.subType == "length-max-char" }



             formContainer.addView(editText)
             inputValues[component.id] = editText
             Log.e("AddInputValue","sss "+inputValues)
             editText.addTextChangedListener(object : TextWatcher {
                 override fun afterTextChanged(s: Editable?) {

                     inputValuesSelected[adapterPosition].textInput = s.toString()
                 }
                 // Implement other TextWatcher methods
                 override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                 override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
             })
         }*/

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
                // Implement other TextWatcher methods
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            Log.e("AddInputValue","2222 "+inputValues)
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
            Log.e("AddInputValue","3333 "+inputValues)
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
                Log.e("AddInputValue","4444 "+inputValues)
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
                        Log.e("AddInputValue","5555 "+inputValues)
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
                text = "dd/mm/yyyyy"

                setOnClickListener {
                   /* NCWDatePickerUtil.showDatePicker(itemView.context) { selectedDate ->
                        text = selectedDate
                        inputValues[component.id] = selectedDate
                        Log.e("AddInputValue", "66666 " + inputValues)
                    }*/
                   val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, AlertDialog.THEME_HOLO_LIGHT,
                        { _, year, month, dayOfMonth ->
                            text = "$dayOfMonth/${month + 1}/$year"
                            inputValues[component.id] = text
                            Log.e("AddInputValue","66666 "+inputValues)
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

            fileInputView.setOnClickListener {
               /* openFilePicker { fileUri ->
                    // Handle the selected file
                    uploadText.text = fileUri?.lastPathSegment ?: "No file selected"
                    inputValues[component.id] = fileUri
                }*/
            }
            formContainer.addView(fileInputView)
            inputValues[component.id] = null
            Log.e("AddInputValue","77777 "+inputValues)
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
                Log.e("Dataaa", "InputValues Size: ${inputValues.size}") // Log the size of inputValues

                val submissionDataList = mutableListOf<Pair<String, Any?>>()

                inputValuesSelected.forEachIndexed { index, formData ->
                    val component = items[index]
                    submissionDataList.add(Pair(component.id, formData))
                }

                Log.e("GetListtt","sss "+submissionDataList)

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
            }
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
