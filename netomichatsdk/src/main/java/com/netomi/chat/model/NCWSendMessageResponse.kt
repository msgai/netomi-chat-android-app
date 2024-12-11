package com.netomi.chat.model

import com.google.gson.annotations.SerializedName

data class NCWSendMessageResponse(@SerializedName("statusMessage" ) var statusMessage : String? = null,
                                  @SerializedName("statusCode"    ) var statusCode    : String? = null,
                                  @SerializedName("status"        ) var status        : Int?    = null)
