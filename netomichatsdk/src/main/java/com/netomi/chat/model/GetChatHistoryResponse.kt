package com.netomi.chat.model

import com.google.gson.annotations.SerializedName
import com.netomi.chat.model.messages.GenericChannelResponse

data class GetChatHistoryResponse(@SerializedName("conversationId" ) var conversationId : String?           = null,
                                  @SerializedName("botReferenceId" ) var botReferenceId : String?           = null,
                                  @SerializedName("responses"      ) var responses      : ArrayList<GenericChannelResponse> = arrayListOf(),
                                  @SerializedName("statusMessage"  ) var statusMessage  : String?           = null,
                                  @SerializedName("statusCode"     ) var statusCode     : String?           = null,
                                  @SerializedName("status"         ) var status         : Int?              = null)
