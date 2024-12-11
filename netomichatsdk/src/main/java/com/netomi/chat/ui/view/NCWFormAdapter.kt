package com.netomi.chat.ui.view

import android.app.DatePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Component
import java.util.Calendar

class NCWFormAdapter(private val items: List<Component>) : RecyclerView.Adapter<NCWFormAdapter.FormViewHolder>() {

    inner class FormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val formContainer: LinearLayout = itemView.findViewById(R.id.formContainer)
        private val btnSubmit: Button = itemView.findViewById(R.id.btnSubmit)
        private val inputValues = mutableMapOf<String, Any?>()
        fun bindForm(component: Component) {

            when (component.componentName) {
                "InputText" -> {
                val editText = EditText(itemView.context).apply {
                    hint = component.labels?.firstOrNull() ?: "Enter text"
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                formContainer.addView(editText)
                inputValues[component.id] = editText
            }
                "ChoiceField" -> {
                val radioGroup = RadioGroup(itemView.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                component.optionList?.forEach { option ->
                    val radioButton = RadioButton(itemView.context).apply {
                        text = option.value

                    }
                    radioGroup.addView(radioButton)
                }
                formContainer.addView(radioGroup)
                inputValues[component.id] = radioGroup
            }
                "DatePicker" -> {
                val textView = TextView(itemView.context).apply {
                    text = "Select Date"
                    setOnClickListener {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                text = "$dayOfMonth/${month + 1}/$year"
                                inputValues[component.id] = text
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                formContainer.addView(textView)
            }
                "AttachFile" -> {
                val button = Button(itemView.context).apply {
                    text = "Attach File"
                    setOnClickListener {
                        // Trigger file picker logic
                    }
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                formContainer.addView(button)
                inputValues[component.id] = null // Update with file URI/path when selected
            }
                // Handle other component types as needed...

        }

        // Handle submit button click
        btnSubmit.setOnClickListener {
            val isValid = validateInputs(component, inputValues)
            if (isValid) {
                // Collect all inputs and submit
                val submissionData = inputValues.mapValues { entry ->
                    when (val value = entry.value) {
                        is EditText -> value.text.toString()
                        is RadioGroup -> value.checkedRadioButtonId.let { id ->
                            (value.findViewById<RadioButton>(id)?.text ?: "")
                        }
                        else -> value
                    }
                }
                Log.d("FormSubmission", "Submitted Data: $submissionData")
            } else {
                Log.e("FormSubmission", "Validation failed")
            }
        }
    }

    private fun validateInputs(
        formSchema: Component,
        inputValues: Map<String, Any?>
    ): Boolean {

            val validationRules = formSchema.validations
            val inputValue = inputValues[formSchema.id]
            if (validationRules != null && inputValue is EditText) {
                // Example: Check required fields
                if (validationRules.any { it.type == "required" } && inputValue.text.isBlank()) {
                    inputValue.error = "This field is required"
                    return false
                }
            }


        return true
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_form_field, parent, false)
        return FormViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = items[position]
        holder.bindForm(item)


    }

    override fun getItemCount() = items.size
}
