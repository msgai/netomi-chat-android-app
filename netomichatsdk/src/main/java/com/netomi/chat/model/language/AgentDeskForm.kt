package com.netomi.chat.model.language

data class AgentDeskForm(
    val formFields: List<FormField>,
    val formSubmissionPostText: String,
    val formSubmissionText: String,
    val formTitle: String,
    val header: String,
    val rejectFormText: String,
    val submitButtonText: String
)