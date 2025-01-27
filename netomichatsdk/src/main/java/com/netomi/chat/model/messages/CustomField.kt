package com.netomi.chat.model.messages

import com.netomi.chat.ui.view.FormData

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
    val schema: ArrayList<Component>,
    var formData: ArrayList<FormData>?=null

)

data class Properties(
    val intentId: String,
    val question: String?=null,
    val version: String,
    val backEnabled: Boolean,
    val skipEnabled: Boolean,
    val restartEnabled: Boolean,
    val isCustomReplyEnabled: Boolean,
    val formSubmissionProperties: FormSubmissionProperties,
    val description: String?=null
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
    val subType: String?=null,
    val children: Int,
    val output: String,
    val variableType: String,
    val labels: List<String>?,
    val groupLabel: String?=null,
    val attributes: List<Attribute>?=null,
    val validations: List<Validation>?,
    val componentName: String,
    val additionalSettings: Map<String, Setting>,
    val dropDownSelections: Map<String, Setting>,
    val optionList: List<Option>?,
    val config:FileConfig?=null,
    var fileUpload: ArrayList<FileUploadData>?=null



)

data class FileUploadData(
    var mediaType:String?=null,
    var fileUrl:String?=null,
    var title: String? = null,
    var fileSize: Long? = null,
    var isRetry :Boolean=false,
    var isCancelled :Boolean=false,

)


data class FileConfig(
    val attachmentTypes: List<String>,
    val isShowAttachmentTypesEnabled: Boolean,
    val fileUploadType: String,
    val maxUploadSizeAllowed: Long

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



