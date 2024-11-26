package com.netomi.chat.model.presigned_url

import com.google.gson.annotations.SerializedName

data class GetPreSignedUrl(
    @SerializedName("message"      ) var message      : String?       = null,
    @SerializedName("statusCode"   ) var statusCode   : Int?          = null,
    @SerializedName("preSignedUrl" ) var preSignedUrl : PreSignedUrl? = PreSignedUrl()
)