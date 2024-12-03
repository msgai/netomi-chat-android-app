package com.netomi.chat.model.theme

data class TypingIndicator(
    val enabled: Boolean,
    val maxTime: Long,
    val minTime: Long
)