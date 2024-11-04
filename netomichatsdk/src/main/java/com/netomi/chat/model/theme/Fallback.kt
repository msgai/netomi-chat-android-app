package com.netomi.chat.model.theme

data class Fallback(
    val duration: Int,
    val enabled: Boolean,
    val fallbackActionButtons: List<FallbackActionButton>,
    val maxRetry: Int,
    val message: String
)