package com.netomi.chat.model.theme

data class ShowWarning(
    val cancelButtonText: String,
    val downloadTranscript: Boolean,
    val isEnabled: Boolean,
    val isSendTranscriptEnabled: Boolean,
    val restartButtonText: String,
    val warningText: String
)