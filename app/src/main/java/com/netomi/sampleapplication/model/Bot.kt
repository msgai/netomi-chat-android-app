package com.netomi.sampleapplication.model

data class Bot(
    val botId: String,
    val botName: String,
    val botRefId: String,
    val entryId: Int,
    val env: String,
    val logo: String,
    val username: String,
    val version: String
)