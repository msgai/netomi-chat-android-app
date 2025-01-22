package com.netomi.chat.model

import com.google.gson.annotations.SerializedName

data class NCWGetConversationIdResponse(@SerializedName("message"        ) var message        : String? = null,
                                        @SerializedName("statusCode"     ) var statusCode     : Int?    = null,
                                        @SerializedName("conversationId" ) var conversationID : String? = null)
