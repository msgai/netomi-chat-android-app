package com.netomi.chat.model.feedback.feedbackrequest

data class NCWEventData(
    val eventInfo: NCWEventInfo,
    val eventType: String,
    val subType: String
)