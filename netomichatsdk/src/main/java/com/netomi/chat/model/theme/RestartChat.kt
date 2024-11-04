package com.netomi.chat.model.theme

data class RestartChat(
    val isEnabled: Boolean,
    val placement: String,
    val showWarning: ShowWarning
)