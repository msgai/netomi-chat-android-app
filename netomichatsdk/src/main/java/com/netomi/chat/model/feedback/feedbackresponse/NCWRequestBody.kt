package com.netomi.chat.model.feedback.feedbackresponse

import com.netomi.chat.model.feedback.feedbackrequest.NCWEventData

data class NCWRequestBody(
    val botReferenceId: String,
    val channelId: String,
    val conversationId: String,
    val eventData: NCWEventData,
    val eventName: String,
    val isPublishToMQTT: Boolean,
    val requestType: String,
    val timestamp: Long,
    val triggerType: String
)