package com.netomi.chat.model.chat_history

data class NCWGetChatHistoryPayload(
    val conversationId: String,
    val requestBody: NCWHistoryRequestBody,
    val botRefId: String
)

data class NCWHistoryRequestBody(
    val numberOfMessages: Int,
    val numberOfDays: Int
)



