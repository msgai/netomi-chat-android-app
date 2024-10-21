package com.netomi.chat.utils


data class BaseResponse<T>(
    var code: Int,
    var message: String,
    var data: T
)