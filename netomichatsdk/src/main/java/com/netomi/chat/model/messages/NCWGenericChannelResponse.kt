package com.netomi.chat.model.messages

data class NCWGenericChannelResponse(
    val type: String? = null,
    val botReferenceId: String? = null,
    val timestamp: Long? = null,
    val ownerType: String? = null,
    val channel: String? = null,
    val requestId: String? = null,
    val requestPayload: NCWRequestPayload? = null,

    val botId: String? = null,
    val attachments: List<NCWAttachment>? = null,
    val customPayload: NCWCustomPayload? = null,
    val triggerType: String? = null,
    val customFields: List<CustomField>? = null,
)

data class NCWRequestPayload(
    val conversationId: String? = null,
    val requestId: String? = null,
    val channelId: String? = null,
    val ownerType: String? = null,
    val eventType: String? = null,
    val triggerType: String? = null,
    var messagePayload: NCWMessagePayload? = null,
    var attachmentList: ArrayList<NCWAttachmentListRequest>? = null,

    )

data class NCWAttachmentListRequest(

    var type: String? = null,
    var title: String? = null,
    var timestamp: Int? = null,
    var largeImageUrl: String? = null,
    var isReviewEnabled: Boolean? = null,
    var thumbnailUrl: String? = null,
    var fileURL: String? = null,
    var fileSize: String? = null
)


data class NCWAttachment(
    val type: String? = null,
    val attachment: NCWAttachmentContent? = null
)

data class NCWAttachmentContent(
    val type: String? = null,
    val text: String? = null,
    val description: String? = null,
    val timestamp: Long? = null,
    val isReviewEnabled: Boolean? = null,
    val attachmentResponseType: String? = null,
    val intentId: String? = null,
    val responseId: Int? = null,
    val nodeName: String? = null,
    val isVariationsEnabled: Boolean? = null,
    val largeImageUrl: String? = null,
    val quickReply: NCWQuickReply? = null,
    val elements: List<NCWCarouselElement>? = null,
    val carouselImageAspectRatio: String? = null,
    val thumbnailUrl: String? = null,
    val title: String? = null,
    var buttons: ArrayList<NCWCarouselButton> = arrayListOf()

)

data class NCWQuickReply(
    val description: String? = null,
    val type: String? = null,
    val options: List<NCWQuickReplyOption>? = null
)

data class NCWQuickReplyOption(
    var label: String? = null,
    var description: String? = null,
    var metadata: String? = null,
    val contentType: String? = null
)

data class NCWCarouselElement(
    val title: String? = null,
    val imageUrl: String? = null,
    val subtitle: String? = null,
    val buttons: List<NCWCarouselButton>? = null,
    val description: String? = null
)

data class NCWCarouselButton(
    val type: String? = null,
    val title: String? = null,
    val payload: String? = null,
    val url: String? = null,
    val messengerExtensions: Boolean? = null
)

data class NCWCustomPayload(
    val TRANSLATE_LANGUAGE: String? = null
)


