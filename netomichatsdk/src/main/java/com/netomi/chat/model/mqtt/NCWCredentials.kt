package com.netomi.chat.model.mqtt

data class NCWCredentials(
    val IoTHostEndPoint: String,
    val SessionToken: String,
    val accessKeyId: String,
    val region: String,
    val secretAccessKey: String,
    val expiresIn:Int
)