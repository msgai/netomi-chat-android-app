package com.netomi.chat.model.theme

data class NCWSound(
    val bot: NCWBot,
    val defaultSound: Boolean,
    val status: String,
    val user: NCWUser
)