package com.netomi.chat.model.endchat

import com.netomi.chat.survey.NCWSignInUserDetails

data class NCWEventData(
    val eventType: String,
    val subType: String,
    val userdetails:NCWSignInUserDetails?=null
)