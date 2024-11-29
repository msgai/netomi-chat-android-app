package com.netomi.chat.model.messages

data class WebhookPayload(
    val botRefId: String? = null,
    val requestBody: RequestBody? = null
)

data class RequestBody(
    val conversationId: String? = null,
    val ownerType: String? = "BOT",
    val messagePayload: MessagePayload? = null,
    val userDetails: UserDetails? = null,
    val additionalAttributes: AdditionalAttributes? = null,
    var attachmentList: ArrayList<AttachmentList>? = null,
)

data class MessagePayload(
    val text: String? = null,
    val label: String? = null,
    val messageId: String? = null,
    val timestamp: Long? = null,
    val hideMessage: Boolean? = null,
    val largeImageUrl: String? = null,

    )

data class UserDetails(
    val userId: String? = null,
    val emailId: String? = null
)

data class AttachmentList(

    var type: String? = null,
    var actualType: String? = null,
    var attachmentId: String? = null,
    var percentage: Int? = null,
    var fileType: String? = null,
    var title: String? = null,
    var fileSize: Long? = null,
    var largeImageUrl: String? = null,
    var errorMessage: String? = null,
    var thumbnailUrl: String? = null,

    )

data class AdditionalAttributes(
    val CUSTOM_ATTRIBUTES: List<CustomAttribute>? = null
)

data class CustomAttribute(
    val type: String? = null,
    val name: String? = null,
    val value: String? = null,
    val scope: String? = null
)



