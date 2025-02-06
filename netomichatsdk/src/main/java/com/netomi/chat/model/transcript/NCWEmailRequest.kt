package com.netomi.chat.model.transcript

data class NCWEmailRequest(
    val botRefId: String,
    val conversationId: String,
    val mail: String,
    val from: String?=null
)

