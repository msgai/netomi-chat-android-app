package com.netomi.chat.ui.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppConstant.FORM_DATE_FORMAT
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWParsingUtils.parseDate
import com.netomi.chat.utils.NCWThemeUtils
import com.netomi.chat.utils.toArrayList
import java.util.Calendar

data class FormData(
    var textInput: String? = null,
    var textAreaInput: String? = null,
    var selectedRadio: String? = null,
    var selectedCheckboxes: List<String> = emptyList(),
    var dropdownSelection: String? = null,
    var dateInput: String? = null,
    var fileUpload: ArrayList<FileUploadData>? = arrayListOf(),
    var variableId: String? = null)

data class InputField(
    val editText: EditText,
    val errorTextView: TextView
)

data class DateField(
    val dateField: EditText,
    val errorTextView: TextView,
    val container: RelativeLayout
)

data class RadioField(
    val radioGroup: RadioGroup,
    val errorTextView: TextView
)

data class CheckBoxField(
    val checkGroup: CheckBox,
    val errorTextView: TextView
)

data class DropdownField(
    val selectedValue: String,
    val errorTextView: TextView
)


class NCWFormAdapter(
    private val items: ArrayList<Component>,
    val formSchema: FormSchema,
    private val callBack: (Component?) -> Unit,
    private val formData: (String?, String?, ArrayList<NCWAttachmentList>) -> Unit,
    private val onRetry: (Component?, FileUploadData) -> Unit
) : RecyclerView.Adapter<NCWFormAdapter.FormViewHolder>() {


    private val inputValues = mutableMapOf<String, Any?>()
    private val inputValuesSelected = MutableList(items.size) { FormData() }
    var isClickable: Boolean = true

    fun updateItem(position: Int, component: Component) {
        if (position in items.indices) {
            items[position] = component
            notifyItemChanged(position)
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
                addSubmitButtonDynamically(formContainer, itemView.context, formSchema)
            }
        }


        private fun Component.isTextInput() = component == "input" && type == "text"
        private fun Component.isTextArea() = component == "textarea" && type == "textarea"
        private fun Component.isRadioGroup() =
            component == "input" && type == "choice" && subType == "radio"

        private fun Component.isCheckboxGroup() =
            component == "input" && type == "choice" && subType == "checkbox"

        private fun Component.isDropdown() = component == "select" && type == "choice"
        private fun Component.isDateInput() = component == "input" && type == "date"
        private fun Component.isFileInput() = component == "file" && type == "file"

        private fun createTextInput(component: Component) {
            addLabel(component)

            val editText = EditText(itemView.context).apply {
                hint = component.attributes?.getOrNull(0)?.value?.toString()
                    ?.removeSurrounding("[", "]")
                setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
                createDrawable(this)
                isSingleLine = true
                maxLines = 1
                imeOptions = EditorInfo.IME_ACTION_DONE
                setPadding(16, 30, 16, 30)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                layoutParams = defaultLayoutParams()
                NCWThemeUtils.setBotTextColor(this)

            }

            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(editText)
            formContainer.addView(errorTextView)
            inputValues[component.id] = InputField(editText, errorTextView)

            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true

            fun updateErrorView(
                errorText: String? = null,
                showError: Boolean = false,
                editText: EditText
            ) {
                if (showError) {
                    errorTextView.text = errorText
                    errorTextView.visibility = View.VISIBLE
                    createErrorDrawable(editText)
                } else {
                    errorTextView.text = ""
                    errorTextView.visibility = View.GONE
                    createDrawable(editText)
                }
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val inputText = s?.toString().orEmpty()
                    if (isValidationEnabled) {
                        val validations = component.validations.orEmpty()
                        if (inputText.isNotEmpty()) {
                            component.textInput=inputText
                            val errorMessage = inputFieldValidation(inputText, validations)
                            if (errorMessage != null) {
                                updateErrorView(errorMessage, true, editText)
                            } else {
                                updateErrorView(editText = editText)
                                inputValuesSelected[adapterPosition].textInput = inputText


                            }
                        } else if (component.additionalSettings["Required"]?.value == true) {
                            updateErrorView(
                                NCWThemeUtils.getThemeData()?.otherlocalized?.this_field_is_required ?:itemView.context.getString(R.string.field_required),
                                true,
                                editText
                            )
                        } else {
                            updateErrorView(editText = editText)
                        }
                    } else {
                        inputValuesSelected[adapterPosition].textInput = inputText
                        component.textInput=inputText
                        if (errorTextView.visibility == View.VISIBLE) {
                            errorTextView.visibility = View.GONE
                            updateErrorView(editText = editText)
                        }

                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            inputValuesSelected[adapterPosition].variableId = component.variableId

            formSchema.formValues?.getOrNull(adapterPosition)?.let { textInput ->
                editText.setText(textInput)
                editText.setTextColor(ContextCompat.getColor(itemView.context, R.color.hint_color))
            }
            if (!component.textInput.isNullOrEmpty())
            {
                editText.setText(component.textInput)
            }

            editText.isEnabled = isClickable
        }


        private fun createTextArea(component: Component) {
            addLabel(component)

            val editText = EditText(itemView.context).apply {
                hint = component.attributes?.getOrNull(0)?.value?.toString()?.replace("[", "")
                    ?.replace("]", "")
                createDrawable(this)
                setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
                setPadding(16, 16, 16, 16)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                gravity = Gravity.TOP
                layoutParams = defaultLayoutParams()
                minLines = 4
                isEnabled = isClickable
                NCWThemeUtils.setBotTextColor(this)
            }

            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(editText)
            formContainer.addView(errorTextView)
            inputValues[component.id] = InputField(editText, errorTextView)
            val isValidationEnabled = component.dropDownSelections["Validation"]?.value == true
            val validations =
                if (isValidationEnabled) component.validations.orEmpty() else emptyList()

            fun updateErrorView(
                errorText: String? = null,
                showError: Boolean = false,
                editText: EditText
            ) {
                if (showError) {
                    errorTextView.text = errorText
                    errorTextView.visibility = View.VISIBLE
                    createErrorDrawable(editText)
                } else {
                    errorTextView.text = ""
                    errorTextView.visibility = View.GONE
                    createDrawable(editText)
                }
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val inputText = s?.toString().orEmpty()

                    if (isValidationEnabled) {
                        if (inputText.isNotEmpty()) {
                            component.textAreaInput=inputText
                            val errorMessage = inputFieldValidation(inputText, validations)
                            if (errorMessage != null) {
                                updateErrorView(errorMessage, true, editText)
                            } else {
                                updateErrorView(editText = editText)
                                inputValuesSelected[adapterPosition].textAreaInput = inputText

                            }
                        } else if (component.additionalSettings["Required"]?.value == true) {
                            if (errorTextView.text.isEmpty()) {
                                updateErrorView(
                                    NCWThemeUtils.getThemeData()?.otherlocalized?.this_field_is_required ?:itemView.context.getString(R.string.field_required),
                                    true,
                                    editText
                                )
                            }
                        } else {
                            updateErrorView(editText = editText)
                        }
                    } else {
                        inputValuesSelected[adapterPosition].textAreaInput = inputText
                        component.textAreaInput=inputText
                        if (errorTextView.visibility == View.VISIBLE) {
                            errorTextView.visibility = View.GONE
                            updateErrorView(editText = editText)
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            inputValuesSelected[adapterPosition].variableId = component.variableId

            formSchema.formValues?.getOrNull(adapterPosition)?.let {
                editText.setText(it)
                editText.setTextColor(ContextCompat.getColor(itemView.context, R.color.hint_color))

            }
            if (!component.textAreaInput.isNullOrEmpty())
            {
                editText.setText(component.textAreaInput)
            }
            editText.isEnabled = isClickable

        }

        private fun createRadioGroup(component: Component) {
            addRadioLabel(component)

            val radioGroup = RadioGroup(itemView.context).apply {
                layoutParams = defaultLayoutParams()
            }
            val isRadiaClickable = isClickable
            formContainer.addView(radioGroup)
            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            formContainer.addView(errorTextView)

            component.optionList?.forEach { option ->
                val radioButton = RadioButton(itemView.context).apply {
                    text = option.value
                    NCWThemeUtils.setRadioButtonUserConfig(this)
                    buttonTintList = ContextCompat.getColorStateList(context, R.color.checkbox_tint)
                    isEnabled = isRadiaClickable
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            inputValuesSelected[adapterPosition].selectedRadio = text.toString()
                            component.selectedRadio = text.toString()
                            inputValuesSelected[adapterPosition].variableId = component.variableId
                            if (errorTextView.visibility == View.VISIBLE)
                                errorTextView.visibility = View.GONE
                        }
                    }
                }
                radioGroup.addView(radioButton)
            }


            if (component.additionalSettings["Required"]?.value == true) {
                inputValues[component.id] = RadioField(radioGroup, errorTextView)
            }

            val radioSelected = formSchema.formValues?.getOrNull(adapterPosition) ?: component.selectedRadio
            radioSelected?.let {
                radioGroup.children.forEach { view ->
                    if (view is RadioButton) {
                        view.isChecked = view.text == it
                    }
                }
                if (!isClickable) {
                    radioGroup.alpha = 0.5f }
            }
        }

        private fun createCheckboxGroup(component: Component) {
            addRadioLabel(component)
            addSpaceToContainer(5)
            // Retrieve previously selected checkboxes if available
            // val checkBoxSelected = formSchema.formData?.getOrNull(adapterPosition)?.textInput
            /* var previouslySelectedCheckboxes: List<String> = emptyList()
             val checkBoxSelected = formSchema.formValues?.getOrNull(adapterPosition)
             if (!checkBoxSelected.isNullOrEmpty()) {
                  previouslySelectedCheckboxes = checkBoxSelected?.split(",")?.map { it.trim() } ?: emptyList()
             }else
             {
                 val checkBoxSelected=component.selectedCheckboxes
                 if (!checkBoxSelected.isNullOrEmpty()) {
                     previouslySelectedCheckboxes = checkBoxSelected
                 }
             }*/
            var previouslySelectedCheckboxes: List<String> = emptyList()
            val checkBoxSelected = formSchema.formValues?.getOrNull(adapterPosition)?.takeIf { it.isNotEmpty() }
                ?.split(",")?.map { it.trim() } ?: component.selectedCheckboxes

            previouslySelectedCheckboxes = checkBoxSelected

            Log.e("previouslySelectedCheckboxes","previouslySelectedCheckboxes "+previouslySelectedCheckboxes)

            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }

            (errorTextView.parent as? ViewGroup)?.removeView(errorTextView)

            component.optionList?.forEach { option ->
                val checkBox = CheckBox(itemView.context).apply {
                    text = option.value
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 5, 0, 0)
                    }
                    NCWThemeUtils.setTitleColor(this)
                    buttonTintList = ContextCompat.getColorStateList(context, R.color.checkbox_tint)
                    setPadding(0, 0, 0, 0)
                    setOnCheckedChangeListener { _, isChecked ->
                        val selectedCheckboxes =
                            inputValuesSelected[adapterPosition].selectedCheckboxes.toMutableList()
                        inputValuesSelected[adapterPosition].variableId = component.variableId

                        if (isChecked) {
                            selectedCheckboxes.add(text.toString())
                        } else {
                            selectedCheckboxes.remove(text.toString())
                        }

                        inputValuesSelected[adapterPosition].selectedCheckboxes = selectedCheckboxes
                        component.selectedCheckboxes=selectedCheckboxes

                        if (errorTextView.visibility == View.VISIBLE) {
                            errorTextView.visibility = View.GONE
                        }
                    }

                    if (!previouslySelectedCheckboxes.isNullOrEmpty())
                        isChecked = previouslySelectedCheckboxes.contains(text.toString())


                }



                (checkBox.parent as? ViewGroup)?.removeView(checkBox)


                formContainer.addView(checkBox)

                if (component.additionalSettings["Required"]?.value == true) {
                    inputValues[component.id] = CheckBoxField(checkBox, errorTextView)
                }
                checkBox.isEnabled = isClickable
                if (!isClickable) {
                    checkBox.alpha = 0.5f }

            }

            formContainer.addView(errorTextView)
        }

        private fun createDropdown(component: Component) {
            addRadioLabel(component)
            val dropdownView = LayoutInflater.from(itemView.context)
                .inflate(R.layout.custom_dropdown_view, formContainer, false) as LinearLayout

            val rootView: RelativeLayout = dropdownView.findViewById(R.id.selected_view)
            val selectedText: TextView = dropdownView.findViewById(R.id.selected_text)
            val arrowIcon: ImageView = dropdownView.findViewById(R.id.arrow_icon)
            val itemsContainer: LinearLayout =
                dropdownView.findViewById(R.id.dropdown_items_container)
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
            formContainer.addView(dropdownView)
            val errorTextView = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(itemView.context, R.color.error_color))
                visibility = View.GONE
                tag = "error_${component.id}" // Set a tag for easy identification
            }
            formContainer.addView(errorTextView)

            val options = component.optionList?.map { it.value } ?: emptyList()
            itemsContainer.removeAllViews()
            if (component.subType == "radio") {
                options.forEach { option ->
                    val optionView = TextView(itemView.context).apply {
                        text = option
                        textSize = 14f
                        setPadding(15, 16, 10, 16)
                        NCWThemeUtils.setBotTextColor(this)
                        setOnClickListener {
                            selectedText.text = option
                            NCWThemeUtils.setBotTextColor(selectedText)
                            isDropdownOpen = false
                            itemsContainer.visibility = View.GONE
                            arrowIcon.setImageResource(R.drawable.ic_arrow_dropdown)
                            inputValues[component.id] = DropdownField(option, errorTextView)
                            inputValuesSelected[adapterPosition].dropdownSelection = option
                            Log.e("option ","option "+ inputValuesSelected[adapterPosition].dropdownSelection)
                            component.dropdownSelection = option
                            inputValuesSelected[adapterPosition].variableId = component.variableId
                            if (errorTextView.visibility == View.VISIBLE)
                                errorTextView.visibility = View.GONE
                        }
                    }
                    itemsContainer.addView(optionView)
                }
            }
            else if (component.subType == "checkbox") {
                val selectedItems = mutableSetOf<String>() // Maintain selected items in a set

                options.forEach { option ->
                    val optionView = CheckBox(itemView.context).apply {
                        text = option
                        textSize = 14f
                        setPadding(15, 16, 10, 16)
                        NCWThemeUtils.setBotTextColor(this)
                        buttonTintList = ContextCompat.getColorStateList(context, R.color.checkbox_tint)
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                selectedItems.add(option)
                            } else {
                                selectedItems.remove(option)
                            }
                            selectedText.text = when (selectedItems.size) {
                                0 -> NCWThemeUtils.getThemeData()?.otherlocalized?.select
                                    ?: itemView.context.getString(R.string.select)
                                else -> {
                                    val itemSelectedTemplate = NCWThemeUtils.getThemeData()?.otherlocalized?._item_selected
                                    if (itemSelectedTemplate != null) {
                                        String.format(itemSelectedTemplate.replace("%@", "%d"), selectedItems.size)
                                    } else {
                                        itemView.context.getString(
                                            R.string.items_selected,
                                            selectedItems.size.toString()
                                        )
                                    }
                                }
                            }
                            NCWThemeUtils.setBotTextColor(selectedText)
                            inputValues[component.id] =
                                DropdownField(selectedItems.joinToString(", "), errorTextView)
                            inputValuesSelected[adapterPosition].dropdownSelection =
                                selectedItems.joinToString(", ")
                            component.dropdownSelection=  selectedItems.joinToString(", ")
                            inputValuesSelected[adapterPosition].variableId = component.variableId
                            if (errorTextView.visibility == View.VISIBLE) {
                                errorTextView.visibility = View.GONE
                            }
                        }
                    }
                    itemsContainer.addView(optionView)
                }
            }





            if (component.additionalSettings["Required"]?.value == true) {
                inputValues[component.id] = DropdownField("", errorTextView)
            }

            dropdownView.isEnabled = isClickable

            val selectedDropDown = when {
                adapterPosition in (formSchema.formValues?.indices ?: emptyList()) -> {
                    formSchema.formValues?.getOrNull(adapterPosition)?.also {
                        selectedText.setTextColor(ContextCompat.getColor(itemView.context, R.color.hint_color))
                    }
                }
                !component.dropdownSelection.isNullOrEmpty() -> component.dropdownSelection
                else -> null
            }

            selectedDropDown?.let { value ->
               /* if (component.subType == "checkbox") {
                    val selectedCount = value.split(",").count { it.isNotBlank() }
                    selectedText.text = if (selectedCount > 0) {
                        "$selectedCount items selected"
                    } else {
                        itemView.context.getString(R.string.select)
                    }
                } else {
                    selectedText.text = value
                }*/
                if (component.subType == "checkbox") {
                    val selectedCount = value?.split(",")?.count { it.isNotBlank() } ?: 0

                    val itemSelectedTemplate = NCWThemeUtils.getThemeData()?.otherlocalized?._item_selected
                    val selectedMessage = if (selectedCount > 0) {
                        if (itemSelectedTemplate != null) {
                            String.format(itemSelectedTemplate.replace("%@", "%d"), selectedCount)
                        } else {
                            itemView.context.getString(R.string.items_selected, selectedCount.toString())
                        }
                    } else {
                        itemView.context.getString(R.string.select)
                    }

                    selectedText.text = selectedMessage
                } else {
                    selectedText.text = value
                }

            }


        }

        private fun createDateInput(component: Component) {
            addLabel(component)

            lateinit var textViewError: TextView

            val container = RelativeLayout(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                createDrawable(this)
                setPadding(16, 16, 16, 16)
            }

            // Create the date input TextView
            val textView = EditText(itemView.context).apply {
                id = View.generateViewId()
                hint = FORM_DATE_FORMAT
                isClickable = false
                isFocusable = false
                isFocusableInTouchMode = false
                isCursorVisible = false
                setPadding(10, 0, 16, 0)
                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_START)
                    addRule(RelativeLayout.CENTER_VERTICAL)
                    marginEnd = 48
                }
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                NCWThemeUtils.setBotTextColor(this)
                container.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, AlertDialog.THEME_HOLO_LIGHT,
                        { _, year, month, dayOfMonth ->
                            val selectedDate = "${month + 1}-$dayOfMonth-$year"
                            val showSelectedDate = "${month + 1}/$dayOfMonth/$year"
                            // val showSelectedDate = "$dayOfMonth/${month + 1}/$year"
                            val errorMessage = validateDate(selectedDate, component)
                            setText(showSelectedDate)
                            if (errorMessage == null) {

                                inputValuesSelected[adapterPosition].dateInput = selectedDate
                                inputValuesSelected[adapterPosition].variableId = component.variableId
                                component.dateInput=selectedDate
                                textViewError.visibility = View.GONE
                                textViewError.text = ""
                                createDrawable(container)
                            } else {
                                textViewError.text = errorMessage
                                textViewError.visibility = View.VISIBLE
                                createErrorDrawable(container)
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            val dateIcon = ImageView(itemView.context).apply {
                id = View.generateViewId()
                setImageResource(R.drawable.ic_calendar)
                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_END)
                    addRule(RelativeLayout.CENTER_VERTICAL)
                }
            }

            // Initialize the error TextView
            textViewError = TextView(itemView.context).apply {
                layoutParams = defaultLayoutParams()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                visibility = View.GONE
            }
            textView.isEnabled = isClickable

            container.isEnabled = isClickable

            // Add the TextView and icon to the container
            container.addView(textView)
            container.addView(dateIcon)

            formContainer.addView(container)
            formContainer.addView(textViewError)

            inputValues[component.id] = DateField(textView, textViewError, container)


            if (adapterPosition in (formSchema.formValues?.indices ?: emptyList())) {
                val textInput = formSchema.formValues?.getOrNull(adapterPosition)
                if (textInput != null) {
                    textView.setText(textInput)
                    textView.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.hint_color
                        )
                    )
                }
            }

            if (!component.dateInput.isNullOrEmpty()){
                textView.setText(component.dateInput)
            }
        }

        private fun inputFieldValidation(
            inputText: String,
            validations: List<Validation>
        ): String? {
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

                                "between" -> {
                                    val minValue = validation.value.getOrNull(0)?.toIntOrNull()
                                    val maxValue = validation.value.getOrNull(1)?.toIntOrNull()
                                    if (minValue != null && maxValue != null) {
                                        if (inputNumber < minValue || inputNumber > maxValue) {
                                            return validation.errorMessage
                                        }
                                    }
                                }

                                "equal-to" -> {
                                    val targetValue = validation.value.getOrNull(0)?.toIntOrNull()
                                    if (targetValue != null && inputNumber != targetValue) {
                                        return validation.errorMessage
                                    }
                                }

                                "number-only" -> {
                                    if (inputText.any { !it.isDigit() }) {
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

                            "text-email" -> {
                                if (!NCWAppUtils.isValidEmail(inputText)) {
                                    return validation.errorMessage
                                }
                            }

                            "text-url" -> {
                                if (!NCWAppUtils.isValidUrl(inputText)) {
                                    return validation.errorMessage
                                }
                            }
                        }
                    }

                    "regex" -> {
                        when (validation.subType) {
                            "regex-contains" -> {
                                val pattern =
                                    validation.value.getOrNull(0)?.toRegex() ?: return null
                                if (!pattern.containsMatchIn(inputText)) {
                                    return validation.errorMessage
                                }
                            }

                            "regex-doesnt-contains" -> {
                                val pattern =
                                    validation.value.getOrNull(0)?.toRegex() ?: return null
                                if (pattern.containsMatchIn(inputText)) {
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
                val minDate =
                    validation.value.getOrNull(0)?.takeIf { it.isNotBlank() }?.let { parseDate(it) }
                val maxDate =
                    validation.value.getOrNull(1)?.takeIf { it.isNotBlank() }?.let { parseDate(it) }

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

            val uploadMedia: ConstraintLayout =
                fileInputView.findViewById(R.id.file_input_container)
            val uploadText: TextView = fileInputView.findViewById(R.id.upload_text)
            val formatHint: TextView = fileInputView.findViewById(R.id.format_hint)
            val recyclerDoc: RecyclerView = fileInputView.findViewById(R.id.recyclerDoc)
            val uploadDocTitle: TextView = fileInputView.findViewById(R.id.uploadDocTitle)
            val label = component.labels?.firstOrNull() ?: ""
            uploadDocTitle.text = label
            NCWThemeUtils.setUserConfigTextColor(uploadDocTitle)
            NCWThemeUtils.setUserConfigTextColor(uploadText)
            uploadText.text= NCWThemeUtils.getThemeData()?.otherlocalized?.click_to_upload ?: itemView.context.getString(R.string.click_to_upload)
            NCWThemeUtils.setTimeStampColor(formatHint)
            if (component.config?.isShowAttachmentTypesEnabled == true) {
                formatHint.visibility = View.VISIBLE
                val formatTemplate = NCWThemeUtils.getThemeData()?.otherlocalized?.format
                val formatText = if (formatTemplate != null) {
                    String.format(formatTemplate.replace("%@", "%s"), component.config?.attachmentTypes?.joinToString(", ") ?: "")
                } else {
                    itemView.context.getString(
                        R.string.format,
                        component.config?.attachmentTypes?.joinToString(", ") ?: ""
                    )
                }
                formatHint.text = formatText
            } else {
                formatHint.visibility = View.GONE
            }
            try {
                if (!component.fileUpload.isNullOrEmpty()) {
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
                        recyclerDoc.adapter = NCWFormFilesAdapter(
                            component.fileUpload!!,
                            isClickable,
                            { selectedOption ->
                                //   component.fileUpload?.remove(selectedOption)
                                // inputValuesSelected.getOrNull(adapterPosition)?.fileUpload?.remove(selectedOption)
                                val position = component.fileUpload?.indexOf(selectedOption)
                                if (position != null && position >= 0) {
                                    //  component.fileUpload?.removeAt(position)
                                    inputValuesSelected.getOrNull(adapterPosition)?.fileUpload?.remove(
                                        selectedOption
                                    )
                                    (recyclerDoc.adapter as? NCWFormFilesAdapter)?.itemRemoved(
                                        position
                                    )

                                    uploadMedia.isEnabled=true
                                    uploadMedia.alpha = 1f


                                }
                            },
                            { retry ->
                                if (retry != null) {
                                    onRetry(component, retry)
                                }
                            })



                    } else {
                        recyclerDoc.adapter!!.notifyDataSetChanged()
                    }
                    val isMultipleUpload = component.config?.fileUploadType == NCWAppConstant.UPLOAD_FILE_MULTIPLE
                    if (!isMultipleUpload){
                        uploadMedia.isEnabled=false
                        uploadMedia.alpha = 0.5f
                    }
                } else {
                    recyclerDoc.visibility = View.GONE
                }


            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            formSchema.formValues?.getOrNull(adapterPosition)?.let{
                val formDataList = it.split(",")?.map { FileUploadData(fileUrl = it) } ?: emptyList()
                val convertList=formDataList.toArrayList()
                recyclerDoc.visibility = View.VISIBLE
                if (recyclerDoc.adapter == null) {
                    recyclerDoc.layoutManager = LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    //  val formDataList = formSchema.formData?.getOrNull(adapterPosition)?.fileUpload

                    if (!formDataList.isNullOrEmpty() && formDataList.size > 0) {
                        if (formDataList[0].fileUrl != null && formDataList[0].fileUrl?.isNotEmpty() == true) {
                            recyclerDoc.adapter = NCWFormFilesAdapter(
                                convertList,
                                isClickable, {}, {})
                        } else {
                            recyclerDoc.visibility = View.GONE
                        }
                    } else {
                        recyclerDoc.visibility = View.GONE
                    }

                }

            }


            uploadMedia.setOnClickListener {
                val isMultipleUpload = component.config?.fileUploadType == NCWAppConstant.UPLOAD_FILE_MULTIPLE
                val hasNoFiles = (component.fileUpload?.size ?: 0) < 1

                if (isMultipleUpload || hasNoFiles) {
                    callBack(component)

                }


            }
            uploadMedia.isEnabled = isClickable
            formContainer.addView(fileInputView)
            inputValues[component.id] = fileInputView
            inputValuesSelected[adapterPosition].variableId = component.variableId
        }

        private fun addLabel(component: Component) {
            addSpaceToContainer(5)
            val textView = TextView(itemView.context).apply {
                val label = component.labels?.firstOrNull() ?: "Label"
                val isRequired = component.additionalSettings["Required"]?.value == true

                text = if (isRequired) {
                    SpannableString("$label *").apply {
                        setSpan(
                            ForegroundColorSpan(Color.RED),
                            length - 1,
                            length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                } else {
                    label
                }

                typeface = Typeface.DEFAULT_BOLD
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                layoutParams = defaultLayoutParams()
            }
            NCWThemeUtils.setBotTextColor(textView)
            formContainer.addView(textView)
        }

        private fun addRadioLabel(component: Component) {
            addSpaceToContainer(5)
            val textView = TextView(itemView.context).apply {
                val label = component.groupLabel ?: "Label"
                val isRequired = component.additionalSettings["Required"]?.value == true

                text = if (isRequired) {
                    SpannableString("$label *").apply {
                        setSpan(
                            ForegroundColorSpan(Color.RED),
                            length - 1,
                            length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                } else {
                    label
                }

                layoutParams = defaultLayoutParams()
                typeface = Typeface.DEFAULT_BOLD
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            }
            NCWThemeUtils.setBotTextColor(textView)
            formContainer.addView(textView)
        }


        private fun defaultLayoutParams() = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 15, 0, 0) }

        private fun addSpaceToContainer(heightDp: Int) {
            val spaceView = View(itemView.context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dpToPx(heightDp)
                )
            }
            formContainer.addView(spaceView)
        }

        private fun dpToPx(dp: Int): Int {
            val density = itemView.context.resources.displayMetrics.density
            return (dp * density).toInt()
        }

        private fun addSubmitButtonDynamically(
            parentLayout: LinearLayout,
            context: Context,
            formSchecma: FormSchema
        ) {
            addSpaceToContainer(5)
            val btnSubmit = TextView(context).apply {
                text = NCWThemeUtils.getThemeData()?.otherlocalized?.submit ?:itemView.context.getString(R.string.submit)
                id = View.generateViewId()
                gravity = Gravity.CENTER
                setPadding(0, 30, 0, 30)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 30, 0, 30) // Add padding/margins as needed
                }

                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                NCWThemeUtils.createRoundedDrawable(this)
            }

            btnSubmit.isEnabled = isClickable

            parentLayout.addView(btnSubmit)
            setupSubmitButton(btnSubmit, parentLayout, formSchecma)
        }

        private fun setupSubmitButton(
            btnSubmit: TextView,
            parentLayout: LinearLayout,
            formSchema: FormSchema
        ) {

            if (formSchema.formValues.isNullOrEmpty()) {
                isClickable = true
            } else {
                btnSubmit.visibility = View.GONE
                isClickable = false
                showFormSubmittedView(parentLayout, btnSubmit.context)
            }


            btnSubmit.setOnClickListener {
                if (validateInputs(inputValues)) {

                    var isStillFileUpload = false

                    items.filter { it.component == "file" && it.type == "file" }
                        .forEach { matchList ->
                            matchList.fileUpload?.forEach { fileUploadData ->
                                if (fileUploadData.fileUrl == null && fileUploadData.mediaType == null) {
                                    isStillFileUpload = true
                                    return@forEach
                                }
                            }
                        }

                    if (isStillFileUpload) {
                        return@setOnClickListener
                    }

                    // Create the payload and label response
                    val messagePayload = createPayload(inputValuesSelected)
                    val labelResponse = createLabelResponse(inputValuesSelected)
                    val attachePayload = createNCWAttachmentList(inputValuesSelected, formSchema.properties.intentId)
                    val attachmentList = arrayListOf(attachePayload)
                    formData(messagePayload, labelResponse, attachmentList)
                    btnSubmit.visibility = View.GONE
                    showFormSubmittedView(parentLayout, btnSubmit.context)
                    /*val formData = messagePayload.let {
                        NCWParsingUtils.parsePayloadToFormData(
                            it
                        )
                    }
                    this@NCWFormAdapter.formSchema.formData = formData*/
                    this@NCWFormAdapter.formSchema.formValues = attachePayload.values?.formValues

                    isClickable=false
                    notifyDataSetChanged()

                }


            }


        }

        private fun createNCWAttachmentList(
            inputValuesSelected: List<FormData>,
            attachmentId: String,
        ): NCWAttachmentList {
            val formValues = arrayListOf<String>()

            items.forEachIndexed { index, item ->
                when {
                    item.isTextInput() -> {
                        val value=item.textInput.orEmpty()
                        formValues.add(value)
                    }

                    item.isTextArea() -> {
                        val value = item.textAreaInput.orEmpty()
                        formValues.add(value)
                    }

                    item.isRadioGroup() -> {
                        val value = item.selectedRadio.orEmpty()
                        formValues.add(value)
                    }

                    item.isCheckboxGroup() -> {
                        val value = if (item.selectedCheckboxes != null &&item.selectedCheckboxes.isNotEmpty()) {
                            item.selectedCheckboxes.joinToString(",")
                        } else ""

                        formValues.add(value)
                    }

                    item.isDropdown() -> {
                        val value = item.dropdownSelection.orEmpty()
                        formValues.add(value)
                    }

                    item.isDateInput() -> {
                        val value = item.dateInput.orEmpty()
                        formValues.add(value)
                    }

                    item.isFileInput() -> {
                        val value =
                            item.fileUpload?.mapNotNull { it.fileUrl }?.joinToString(",")
                                .orEmpty()

                        formValues.add(value)
                    }
                }
            }



          /*  inputValuesSelected.forEachIndexed { index, formData ->
                val item = items[index] // Avoid repetitive indexing

                when {
                    item.isTextInput() -> {
                        val value = formData.textInput.orEmpty()

                        formValues.add(value)
                    }

                    item.isTextArea() -> {
                        val value = formData.textAreaInput.orEmpty()
                        formValues.add(value)
                    }

                    item.isRadioGroup() -> {
                        val value = formData.selectedRadio.orEmpty()
                        formValues.add(value)
                    }

                    item.isCheckboxGroup() -> {
                        val value = if (formData.selectedCheckboxes.isNotEmpty()) {
                            formData.selectedCheckboxes.joinToString(",")
                        } else ""

                        formValues.add(value)
                    }

                    item.isDropdown() -> {
                        Log.e("dropdownSelection","compoent "+item.dropdownSelection)
                        Log.e("dropdownSelection","dropdownSelection "+formData.dropdownSelection)
                        val value = formData.dropdownSelection.orEmpty()
                        Log.e("dropdownSelection","value "+value)
                        formValues.add(value)
                    }

                    item.isDateInput() -> {
                        Log.e("dateInput","compoent "+item.dateInput)
                        val value = formData.dateInput.orEmpty()
                        formValues.add(value)
                    }

                    item.isFileInput() -> {
                        val value =
                            formData.fileUpload?.mapNotNull { it.fileUrl }?.joinToString(",")
                                .orEmpty()

                        formValues.add(value)
                    }
                }
            }*/


            val schemaFormRequestId = listOf(formSchema.requestId ?: "")
            return NCWAttachmentList(
                type = "ai.msg.domain.responses.core.MessageInfoAttachment",
                values = Values(
                    status = listOf("SUBMITTED"),
                    isSchemaForm = listOf("true"),
                    schemaFormRequestId = schemaFormRequestId,
                    question = listOf(formSchema.properties.question ?: ""),
                    optionList = inputValuesSelected.flatMap { it.selectedCheckboxes },
                    formValues = formValues
                ),
                attachmentId = attachmentId,
                timestamp = System.currentTimeMillis(),
            )
        }

        private fun showFormSubmittedView(parentLayout: LinearLayout, context: Context) {
            val horizontalLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 20, 0, 20)
                }
            }
            val successIcon = ImageView(context).apply {
                setImageResource(R.drawable.ic_form_submitted)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    setMargins(0, 0, 10, 0)
                }
            }


            val successMessage = TextView(context).apply {
                text =  NCWThemeUtils.getThemeData()?.otherlocalized?.submitted?:context.getString(R.string.submitted)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                setTextColor(ContextCompat.getColor(context, R.color.black))
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(10, 0, 10, 0)
                }
                typeface = Typeface.DEFAULT_BOLD

            }


            // Add text and icon to the horizontal layout
            horizontalLayout.addView(successIcon)
            horizontalLayout.addView(successMessage)


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
                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable="var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        }
                        else{
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$value&")
                        }
                    }

                    item.isTextArea() -> {
                        val value = formData.textAreaInput.orEmpty()
                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable = "var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        } else {
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_TEXTAREA::value=$value&")
                        }
                    }

                    item.isRadioGroup() -> {
                        val value = formData.selectedRadio.orEmpty()
                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable = "var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        } else {
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_SELECT::value=$value&")
                        }
                    }

                    item.isCheckboxGroup() -> {
                        val value = if (formData.selectedCheckboxes.isNotEmpty()) {
                            formData.selectedCheckboxes.joinToString(",")
                        } else ""

                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable = "var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        } else {
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$value&")
                        }
                    }

                    item.isDropdown() -> {
                        val value = formData.dropdownSelection.orEmpty()

                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable="var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        }
                        else {
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_SELECT::value=$value&")
                        }
                    }

                    item.isDateInput() -> {
                        val value = formData.dateInput.orEmpty()
                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable = "var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        } else {
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_INPUT::value=$value&")
                        }
                    }

                    item.isFileInput() -> {
                        val value =
                            formData.fileUpload?.mapNotNull { it.fileUrl }?.joinToString(",")
                                .orEmpty()

                        if (!formData.variableId.isNullOrEmpty()) {
                            val variable = "var_${formData.variableId}"
                            stringBuilder.append("$variable::value=$value&")
                        } else {
                            stringBuilder.append("DEFAULT_OUTPUT_KEY_PILL::value=$value&")
                        }
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
                                        createErrorDrawable(editText)
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = NCWThemeUtils.getThemeData()?.otherlocalized?.this_field_is_required ?:itemView.context.getString(R.string.field_required)
                                        isValid = false
                                    } else {

                                        errorTextView.visibility = View.GONE
                                    }
                                }
                                if (value.errorTextView.text.toString().isNotEmpty()) {

                                    isValid = false
                                    value.errorTextView.visibility = View.VISIBLE
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
                                    if (editText.text.isNullOrEmpty()) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = NCWThemeUtils.getThemeData()?.otherlocalized?.this_field_is_required ?:itemView.context.getString(R.string.field_required)
                                        createErrorDrawable(value.container)
                                        isValid = false // Set to false only if there's an error
                                    } else {
                                        errorTextView.visibility = View.GONE
                                    }
                                }
                                if (value.errorTextView.text.toString().isNotEmpty()) {
                                    isValid = false
                                    value.errorTextView.visibility = View.VISIBLE
                                }
                            }
                        }
                    }

                    is RadioField -> {
                        items.forEach { item ->
                            if (item.id == key) {
                                if (item.additionalSettings["Required"]?.value == true) {
                                    val radioGroup = value.radioGroup
                                    val errorTextView = value.errorTextView

                                    if (radioGroup.checkedRadioButtonId == -1) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = NCWThemeUtils.getThemeData()?.otherlocalized?.this_field_is_required ?:itemView.context.getString(R.string.field_required)
                                        isValid = false
                                    } else {
                                        errorTextView.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    }

                    is CheckBoxField -> {
                        items.forEach { item ->
                            if (item.id == key) {
                                if (item.additionalSettings["Required"]?.value == true) {
                                    val checkBox = value.checkGroup
                                    val errorTextView = value.errorTextView

                                    val parentView = checkBox.parent as? ViewGroup
                                    val selectedCount = parentView?.children
                                        ?.filterIsInstance<CheckBox>()
                                        ?.count { it.isChecked } ?: 0

                                    if (selectedCount == 0) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = "At least one option must be selected"
                                        isValid = false
                                    } else {
                                        errorTextView.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    }

                    is DropdownField -> { // Dropdown validation
                        items.forEach { item ->
                            if (item.id == key) {
                                if (item.additionalSettings["Required"]?.value == true) {
                                    val selectedValue = value.selectedValue
                                    val errorTextView = value.errorTextView
                                    if (selectedValue.isBlank()) {
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = NCWThemeUtils.getThemeData()?.otherlocalized?.this_field_is_required ?:itemView.context.getString(R.string.field_required)
                                        isValid = false
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


        private fun createDrawable(view: View) {
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 15f
                setStroke(1, Color.BLACK)
                setColor(Color.WHITE)
            }
            view.background = drawable

        }

    }

    private fun createErrorDrawable(view: View) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 15f
            setStroke(2, ContextCompat.getColor(view.context, R.color.error_color))
            setColor(Color.WHITE)
        }
        view.background = drawable

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ncw_form_item, parent, false)
        return FormViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        holder.bindForm(items[position])

        val isDisabled = !formSchema.formValues.isNullOrEmpty() // Pass this flag from the parent

        holder.itemView.isClickable = !isDisabled
        holder.itemView.isEnabled = !isDisabled

        // Disable child views if needed
        holder.itemView.setOnTouchListener(if (isDisabled) { _, _ -> true } else null)
    }

    override fun getItemCount() = items.size

}
