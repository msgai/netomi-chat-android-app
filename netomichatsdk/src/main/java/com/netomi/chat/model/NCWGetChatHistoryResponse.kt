package com.netomi.chat.model

import com.google.gson.annotations.SerializedName
import com.netomi.chat.model.messages.NCWGenericChannelResponse

data class NCWGetChatHistoryResponse(@SerializedName("conversationId" ) var conversationId : String?           = null,
                                     @SerializedName("botReferenceId" ) var botReferenceId : String?           = null,
                                     @SerializedName("responses"      ) var responses      : ArrayList<NCWGenericChannelResponse> = arrayListOf(),
                                     @SerializedName("statusMessage"  ) var statusMessage  : String?           = null,
                                     @SerializedName("statusCode"     ) var statusCode     : String?           = null,
                                     @SerializedName("status"         ) var status         : Int?              = null)
