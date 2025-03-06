package com.netomi.chat.model.messages

import com.netomi.chat.ui.view.FormData
import java.io.File

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
    var requestId: String? = null,
    var formValues: ArrayList<String>?=null,

    )

data class Properties(
    val intentId: String,
    val question: String?=null,
    val version: String,
    val backEnabled: Boolean,
    val skipEnabled: Boolean,
    val restartEnabled: Boolean,
    val isCustomReplyEnabled: Boolean,
    val formSubmissionProperties: FormSubmissionProperties?=null,
    val description: String?=null
)

data class FormSubmissionProperties(
    val submitButtonText: String?=null,
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
    var fileUpload: ArrayList<FileUploadData>?=null,
    var variableId:String="",

    var textInput: String? = null,
    var textAreaInput: String? = null,
    var selectedRadio: String? = null,
    var selectedCheckboxes: List<String> = emptyList(),
    var dropdownSelection: String? = null,
    var dateInput: String? = null

)

data class FileUploadData(
    var mediaType:String?=null,
    var fileUrl:String?=null,
    var title: String? = null,
    var fileSize: Long? = null,
    var isRetry :Boolean=false,
    var isCancelled :Boolean=false,
    var mimeType: String? = null,
    var file: File? = null,

    )


data class FileConfig(
    val attachmentTypes: List<String>?=null,
    val isShowAttachmentTypesEnabled: Boolean=false,
    val fileUploadType: String?=null,
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



