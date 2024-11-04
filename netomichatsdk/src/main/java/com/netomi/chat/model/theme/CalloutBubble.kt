package com.netomi.chat.model.theme

data class CalloutBubble(
    val attachments: List<Attachment>,
    val calloutBubbleEnabled: Boolean,
    val intent: String,
    val url: String
)