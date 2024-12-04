package com.netomi.chat.model.endchat

import com.google.gson.annotations.SerializedName

data class EndChatResponse(@SerializedName("statusMessage" ) var statusMessage : String? = null,
                           @SerializedName("statusCode"    ) var statusCode    : String? = null,
                           @SerializedName("status"        ) var status        : Int?    = null)