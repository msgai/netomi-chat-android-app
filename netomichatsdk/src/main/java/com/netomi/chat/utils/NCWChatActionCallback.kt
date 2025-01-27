package com.netomi.chat.utils

import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.model.messages.MultipleSourceDetail
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.model.messages.NCWQuickReplyOption

interface NCWChatActionCallback {

    fun onQuickReply(option: NCWQuickReplyOption?, position: Int)
    fun onMediaClick(message: NCWMessage)
    fun carouselButtonAction(it: NCWCarouselButton?)
    fun onRetryClicked(message: NCWMessage)
    fun onSourceClicked(multipleSourceDetail: MultipleSourceDetail)
    fun onScrollToPosition(isScrollToPosition:Boolean=true)

}