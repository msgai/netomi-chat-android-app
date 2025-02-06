package com.netomi.chat.model

import com.netomi.chat.model.messages.EventObject
import com.netomi.chat.model.messages.FormSchema
import com.netomi.chat.model.messages.MultipleSourceDetail
import com.netomi.chat.model.messages.NCWAttachmentList
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.model.messages.NCWCarouselElement
import com.netomi.chat.model.messages.NCWCustomPayload
import com.netomi.chat.model.messages.NCWQuickReply
import com.netomi.chat.model.messages.SurveyField

data class NCWMessage(
    var message: String? = null,
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
    var formSchema: FormSchema? = null,
    var requestID:String?=null,
    var customPayload: NCWCustomPayload? = null,
    var feedbackValue: String?=null,
    var isReviewEnabled: Boolean = false,
    val surveyField: SurveyField? = null,
    var agentAvatar:String?=null,
    var attachmentIndex:Int=0,
    val multipleSourceDetails: ArrayList<MultipleSourceDetail> = arrayListOf(),

    )

enum class MessageType(val typeName: String) {
    TEXT("ai.msg.domain.responses.core.Text"),
    IMAGE("ai.msg.domain.responses.core.Image"),
    VIDEO("ai.msg.domain.responses.core.Video"),
    CAROUSEL("ai.msg.domain.responses.core.Carousel"),
    CARD("ai.msg.domain.responses.core.Card"),
    FILE("ai.msg.domain.responses.core.GenericFileAttachment"),
    MESSAGEINFO("ai.msg.domain.responses.core.MessageInfoAttachment"),
    MULTISOURCE("ai.msg.domain.responses.core.MultiSource");



    companion object {
        fun fromTypeName(typeName: String): MessageType {
            return values().find { it.typeName == typeName } ?: TEXT
        }
    }
}
enum class CarouselButtonType(val value: String) {
    WEB("WEB"),
    CALL("CALL"),
    POST_BACK("POST_BACK");

    companion object {
        // Helper method to map String to CarouselButtonType
        fun fromValue(value: String?): CarouselButtonType? {
            return values().find { it.value == value }
        }
    }
}

enum class CustomFieldName(val value: String) {
    FORM_SCHEMA("FORM_SCHEMA"),
    SURVEY_SCHEMA("SURVEY_SCHEMA"),
    DISABLE_INPUT_FIELD("DISABLE_INPUT_FIELD"),
    DISABLE_CHAT_INPUT("DISABLE_CHAT_INPUT"),
    END_CHAT("END_CHAT");


    companion object {
        // Helper method to map String to CarouselButtonType
        fun fromValue(value: String?): CustomFieldName? {
            return values().find { it.value == value }
        }
    }
}

