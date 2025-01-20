package com.netomi.chat.model.feedback.feedbackrequest

data class NCWEventInfo(
    val attachmentIndex: Int?=null,
    val feedbackValue: String?=null,
    val requestId: String?=null,
    var idleTime: Long?=null,
)