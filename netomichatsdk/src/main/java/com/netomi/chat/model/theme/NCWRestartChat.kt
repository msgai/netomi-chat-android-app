package com.netomi.chat.model.theme

data class NCWRestartChat(
    val isEnabled: Boolean=true,
    val placement: String,
    val showWarning: NCWShowWarning?=null
)