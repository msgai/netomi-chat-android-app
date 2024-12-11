package com.netomi.chat.model.theme

data class NCWFallback(
    val duration: Int,
    val enabled: Boolean,
    val fallbackActionButtons: List<NCWFallbackActionButton>,
    val maxRetry: Int,
    val message: String
)