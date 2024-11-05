package com.netomi.chat.model.mqtt

data class Credentials(
    val IoTHostEndPoint: String,
    val SessionToken: String,
    val accessKeyId: String,
    val region: String,
    val secretAccessKey: String
)