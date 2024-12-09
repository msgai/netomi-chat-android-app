package com.netomi.chat.model.theme

data class NCWFileSharing(
    val attachmentQuery: String,
    val fileSize: Long,
    val isEnabled: Boolean,
    val list: List<String>,
    val multiAttachment: Boolean,
    val sharingType: String,
    val showSupportedFileTypes: Boolean
)