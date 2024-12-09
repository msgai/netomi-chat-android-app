package com.netomi.chat.model.messages

import com.netomi.chat.utils.DeviceInfo

data class NCWWebhookPayload(
    val botRefId: String? = null,
    val requestBody: NCWRequestBody? = null
)

data class NCWRequestBody(
    val conversationId: String? = null,
    val ownerType: String? = "BOT",
    val messagePayload: NCWMessagePayload? = null,
    val userDetails: NCWUserDetails? = null,
    val additionalAttributes: NCWAdditionalAttributes? = null,
    var attachmentList: ArrayList<NCWAttachmentList>? = null,
)

data class NCWMessagePayload(
    val text: String? = null,
    val label: String? = null,
    val messageId: String? = null,
    val timestamp: Long? = null,
    val hideMessage: Boolean? = null,
    val largeImageUrl: String? = null,

    )

data class NCWUserDetails(
    val userId: String? = null,
    val emailId: String? = null
)

data class NCWAttachmentList(

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
    var fileURL: String? = null


    )

data class NCWAdditionalAttributes(
    val CUSTOM_ATTRIBUTES: ArrayList<DeviceInfo> = arrayListOf()
)

data class NCWCustomAttribute(
    val type: String? = null,
    val name: String? = null,
    val value: String? = null,
    val scope: String? = null
)



