package com.netomi.chat.model.messages

data class GenericChannelResponse(
    val type: String? = null,
    val botReferenceId: String? = null,
    val timestamp: Long? = null,
    val ownerType: String? = null,
    val channel: String? = null,
    val requestId: String? = null,
    val requestPayload: RequestPayload? = null,
    val customFields: List<Any> = emptyList(),
    val botId: String? = null,
    val attachments: List<Attachment>? = null,
    val customPayload: CustomPayload? = null,
    val triggerType: String? = null
)

data class RequestPayload(
    val conversationId: String? = null,
    val requestId: String? = null,
    val channelId: String? = null,
    val ownerType: String? = null,
    val eventType: String? = null,
    val triggerType: String? = null
)
data class Attachment(
    val type: String? = null,
    val attachment: AttachmentContent? = null
)

data class AttachmentContent(
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
    val quickReply: QuickReply? = null,
    val elements: List<CarouselElement>? = null,
    val carouselImageAspectRatio: String? = null
)

data class QuickReply(
    val description: String? = null,
    val type: String? = null,
    val options: List<QuickReplyOption>? = null
)

data class QuickReplyOption(
    val label: String? = null,
    val description: String? = null,
    val metadata: String? = null,
    val contentType: String? = null
)

data class CarouselElement(
    val title: String? = null,
    val imageUrl: String? = null,
    val subtitle: String? = null,
    val buttons: List<CarouselButton>? = null,
    val description: String? = null
)

data class CarouselButton(
    val type: String? = null,
    val title: String? = null,
    val payload: String? = null,
    val url: String? = null,
    val messengerExtensions: Boolean? = null
)

data class CustomPayload(
    val TRANSLATE_LANGUAGE: String? = null
)

