package com.netomi.chat.model.awsmqtt

data class NCWAwsCredentials(
    val accessKey: String,
    val secretKey: String,
    val sessionToken: String,
    val iotEndpoint: String
)
