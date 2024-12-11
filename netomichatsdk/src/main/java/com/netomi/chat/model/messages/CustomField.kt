package com.netomi.chat.model.messages

data class CustomField(
    val botId: String,
    val name: String,
    val type: String,
    val values: List<String>?,
    val rule: String?,
    val active: Boolean?
)

data class FormSchema(
    val properties: Properties,
    val schema: List<Component>
)

data class Properties(
    val intentId: String,
    val question: String,
    val version: String,
    val backEnabled: Boolean,
    val skipEnabled: Boolean,
    val restartEnabled: Boolean,
    val isCustomReplyEnabled: Boolean,
    val formSubmissionProperties: FormSubmissionProperties,
    val description: String
)

data class FormSubmissionProperties(
    val submitButtonText: String,
    val showSubmittedResponse: Boolean,
    val submissionText: String
)

data class Component(
    val id: String,
    val component: String,
    val type: String,
    val children: Int,
    val output: String,
    val variableType: String,
    val labels: List<String>?,
    val attributes: List<Attribute>?,
    val validations: List<Validation>?,
    val componentName: String,
    val additionalSettings: Map<String, Setting>,
    val dropDownSelections: Map<String, Setting>,
    val optionList: List<Option>?
)

data class Attribute(
    val type: String,
    val value: List<String>
)

data class Validation(
    val type: String,
    val subType: String,
    val value: List<String>,
    val errorMessage: String,
    val id: String
)

data class Setting(
    val name: String,
    val value: Boolean
)

data class Option(
    val value: String,
    val id: String
)



