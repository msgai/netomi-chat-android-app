package com.netomi.chat.model.mqtt

data class MQTTCredentialsResponse(
    val credentials: Credentials,
    val message: String,
    val statusCode: Int
)