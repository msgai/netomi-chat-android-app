package com.netomi.chat.utils
interface NCWFeedbackActionCallback {
    fun onThumbUpClick(requestId:String)
    fun onThumbDownClick(requestId: String)
}