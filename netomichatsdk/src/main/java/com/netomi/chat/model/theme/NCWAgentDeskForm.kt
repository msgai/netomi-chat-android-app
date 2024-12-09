package com.netomi.chat.model.theme

data class NCWAgentDeskForm(
    val formFields: List<NCWFormField>,
    val formSubmissionPostText: String,
    val formSubmissionText: String,
    val formTitle: String,
    val header: String,
    val rejectFormText: String,
    val submitButtonText: String
)