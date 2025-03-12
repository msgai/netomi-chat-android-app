package com.netomi.sampleapplication.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("statusCode")
    @Expose
    var code: Int,
    @SerializedName("errorCode")
    @Expose
    var errorCode: Int,
    @SerializedName("message")
    @Expose
    var message: String,
    @SerializedName("result")
    @Expose
    var data: T
)