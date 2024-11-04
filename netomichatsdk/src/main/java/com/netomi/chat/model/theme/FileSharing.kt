package com.netomi.chat.model.theme

data class FileSharing(
    val attachmentQuery: String,
    val fileSize: Int,
    val isEnabled: Boolean,
    val list: List<String>,
    val multiAttachment: Boolean,
    val sharingType: String,
    val showSupportedFileTypes: Boolean
)