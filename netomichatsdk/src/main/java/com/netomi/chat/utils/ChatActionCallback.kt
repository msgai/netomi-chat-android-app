package com.netomi.chat.utils

import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.messages.CarouselButton
import com.netomi.chat.model.messages.QuickReplyOption

interface ChatActionCallback {

    fun onQuickReply(option: QuickReplyOption?, position: Int)
    fun onMediaClick(message: NCWMessage)
     fun carouselButtonAction(it: CarouselButton?)
}