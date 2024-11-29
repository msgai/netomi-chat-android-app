package com.netomi.chat.utils

import com.netomi.chat.model.messages.QuickReplyOption

interface ChatActionCallback {

    fun onQuickReply(option: QuickReplyOption?, position: Int)
    fun onImageClick(imageUrl: String)
}