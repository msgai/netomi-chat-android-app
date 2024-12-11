package com.netomi.chat.model.endchat

data class NCWRequestBody(
    val botReferenceId: String,
    val channelId: String,
    val conversationId: String,
    val NCWEventData: NCWEventData,
    val eventName: String,
    val isPublishToMQTT: Boolean,
    val requestType: String,
    val timestamp: Long,
    val triggerType: String
)