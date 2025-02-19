package com.netomi.chat.model.theme

data class NCWSound(
    val bot: NCWBot,
    var defaultSound: Boolean=false,
    val status: String,
    var isSound: Boolean?=null,
    val user: NCWUser
)