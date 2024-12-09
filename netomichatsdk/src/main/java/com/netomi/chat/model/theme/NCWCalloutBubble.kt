package com.netomi.chat.model.theme

data class NCWCalloutBubble(
    val attachments: List<NCWAttachment>,
    val calloutBubbleEnabled: Boolean,
    val intent: String,
    val url: String
)