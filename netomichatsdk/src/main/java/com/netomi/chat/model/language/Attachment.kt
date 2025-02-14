package com.netomi.chat.model.language

import com.netomi.chat.model.theme.NCWQuickReply

data class Attachment(
    val quickReply: NCWQuickReply,
    val text: String
)