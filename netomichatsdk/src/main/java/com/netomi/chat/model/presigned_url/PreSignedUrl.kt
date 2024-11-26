package com.netomi.chat.model.presigned_url

import com.google.gson.annotations.SerializedName

data class PreSignedUrl(
    @SerializedName("url"    ) var url    : String? = null,
    @SerializedName("fields" ) var fields : Fields? = Fields()
)