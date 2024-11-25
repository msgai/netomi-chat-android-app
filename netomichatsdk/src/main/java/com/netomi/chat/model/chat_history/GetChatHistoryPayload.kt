package com.netomi.chat.model.chat_history

data class GetChatHistoryPayload(
    val conversationId: String,
    val requestBody: HistoryRequestBody,
    val botRefId: String
)

data class HistoryRequestBody(
    val numberOfMessages: Int,
    val numberOfDays: Int
)



