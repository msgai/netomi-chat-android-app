package com.netomi.chat.model

import com.netomi.chat.model.messages.FormSchema
import com.netomi.chat.model.messages.NCWAttachmentList
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.model.messages.NCWCarouselElement
import com.netomi.chat.model.messages.NCWQuickReply

data class NCWMessage(
    val message: String? = null,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val type: MessageType=MessageType.TEXT,
    val id: String? = null,
    val timestamp: Long,
    val sender: String? = null,
    var largeImageUrl: String? = null,
    val carouselItems: List<NCWCarouselElement>? = null,
    val quickReply: NCWQuickReply? = null,
    var thumbnailUrl: String? = null,
    val title: String? = null,
    var buttons : ArrayList<NCWCarouselButton> = arrayListOf(),
    var isSameTimeMessage :Boolean=true,
    var isQuickReplyVisible: Boolean = true,
    var fileUrl: String? = null,
    val fileSize: String? = null,
    var isRetry :Boolean=false,
     var attachmentList: ArrayList<NCWAttachmentList>? = null,
    var formSchema: FormSchema? = null

    )

enum class MessageType(val typeName: String) {
    TEXT("ai.msg.domain.responses.core.Text"),
    IMAGE("ai.msg.domain.responses.core.Image"),
    VIDEO("ai.msg.domain.responses.core.Video"),
    CAROUSEL("ai.msg.domain.responses.core.Carousel"),
    CARD("ai.msg.domain.responses.core.Card"),
    FILE("ai.msg.domain.responses.core.GenericFileAttachment");


    companion object {
        fun fromTypeName(typeName: String): MessageType {
            return values().find { it.typeName == typeName } ?: TEXT
        }
    }
}
/*enum class MessageType {
    TEXT1, IMAGE, VIDEO
}*/
