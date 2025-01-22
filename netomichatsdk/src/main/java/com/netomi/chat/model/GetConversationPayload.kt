package com.netomi.chat.model

data class GetConversationPayload(
    var botRefId: String? = null,
    var externalId: String? = null
)