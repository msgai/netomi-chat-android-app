package com.netomi.chat.utils
interface NCWFeedbackActionCallback {
    fun onThumbUpClick(requestId: String, position: Int, attachmentIndex: Int)
    fun onThumbDownClick(requestId: String, position: Int, attachmentIndex: Int)
}