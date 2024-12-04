package com.netomi.chat.model.endchat

data class RequestBody(
    val botReferenceId: String,
    val channelId: String,
    val conversationId: String,
    val eventData: EventData,
    val eventName: String,
    val isPublishToMQTT: Boolean,
    val requestType: String,
    val timestamp: Long,
    val triggerType: String
)