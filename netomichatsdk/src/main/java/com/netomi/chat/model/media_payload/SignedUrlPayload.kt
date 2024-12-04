package com.netomi.chat.model.media_payload

data class SignedUrlPayload(
    val fileType: String?=null,
    val uploadKeyPrefix: String
)
