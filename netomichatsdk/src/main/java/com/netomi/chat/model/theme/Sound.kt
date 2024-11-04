package com.netomi.chat.model.theme

data class Sound(
    val bot: Bot,
    val defaultSound: Boolean,
    val status: String,
    val user: User
)