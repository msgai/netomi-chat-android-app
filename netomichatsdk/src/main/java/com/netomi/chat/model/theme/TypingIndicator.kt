package com.netomi.chat.model.theme

data class TypingIndicator(
    val enabled: Boolean,
    val maxTime: Int,
    val minTime: Int
)