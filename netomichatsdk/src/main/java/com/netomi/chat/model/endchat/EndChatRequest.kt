package com.netomi.chat.model.endchat

data class EndChatRequest(
    val botRefId: String,
    val requestBody: RequestBody
)