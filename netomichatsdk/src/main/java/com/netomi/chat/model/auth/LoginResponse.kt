package com.netomi.chat.model.auth

data class LoginResponse(
    val authenticatedConversationId: String,
    val message: String,
    val statusCode: Int,
    val externalId: String?=null
)