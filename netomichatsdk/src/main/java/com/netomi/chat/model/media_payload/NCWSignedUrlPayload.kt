package com.netomi.chat.model.media_payload

data class NCWSignedUrlPayload(
    val fileType: String?=null,
    val uploadKeyPrefix: String
)
