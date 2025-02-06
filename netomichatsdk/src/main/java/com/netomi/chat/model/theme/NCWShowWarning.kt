package com.netomi.chat.model.theme

data class NCWShowWarning(
    val cancelButtonText: String?=null,
    val downloadTranscript: Boolean,
    val isEnabled: Boolean=true,
    val isSendTranscriptEnabled: Boolean,
    val restartButtonText: String?=null,
    val warningText: String?=null
)