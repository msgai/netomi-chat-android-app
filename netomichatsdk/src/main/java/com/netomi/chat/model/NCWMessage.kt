package com.netomi.chat.model

data class NCWMessage(
    val message: String? = null,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val type: MessageType=MessageType.TEXT,
    val id: String? = null,
    val timestamp: Long,
    val sender: String? = null,
    val largeImageUrl: String? = null,
    )

enum class MessageType(val typeName: String) {
    TEXT("ai.msg.domain.responses.core.Text"),
    IMAGE("ai.msg.domain.responses.core.Image"),
    CAROUSEL("ai.msg.domain.responses.core.Carousel");

    companion object {
        fun fromTypeName(typeName: String): MessageType {
            return values().find { it.typeName == typeName } ?: TEXT
        }
    }
}
/*enum class MessageType {
    TEXT1, IMAGE, VIDEO
}*/
