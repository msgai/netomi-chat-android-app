package com.netomi.chat.model.language

data class ShowWarning(
    val cancelButtonText: String,
    val downloadTranscript: Boolean,
    val isEnabled: Boolean,
    val isSendTranscriptEnabled: Boolean,
    val restartButtonText: String,
    val warningText: String
)