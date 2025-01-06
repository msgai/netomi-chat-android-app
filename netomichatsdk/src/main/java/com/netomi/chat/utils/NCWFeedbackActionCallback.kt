package com.netomi.chat.utils
interface NCWFeedbackActionCallback {
    fun onThumbUpClick(requestId: String, position: Int)
    fun onThumbDownClick(requestId: String, position: Int)
}