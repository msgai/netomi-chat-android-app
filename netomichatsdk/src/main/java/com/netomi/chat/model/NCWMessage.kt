package com.netomi.chat.model

data class NCWMessage(
    val message: String? = null,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val type: MessageType=MessageType.TEXT,
    val id: String? = null,
    val timestamp: Long,
    val sender: String? = null,

    )
enum class MessageType {
    TEXT, IMAGE, VIDEO
}
