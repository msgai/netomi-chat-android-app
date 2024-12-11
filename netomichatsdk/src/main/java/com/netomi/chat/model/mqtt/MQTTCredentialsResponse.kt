package com.netomi.chat.model.mqtt

data class MQTTCredentialsResponse(
    val credentials: NCWCredentials,
    val message: String,
    val statusCode: Int,
    val expiresIn:Int
)