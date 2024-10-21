package com.netomi.chat.model

data class NCWMessage(
    val id: String,
    val sender: String,
    val content: String,
    val timestamp: Long
)
