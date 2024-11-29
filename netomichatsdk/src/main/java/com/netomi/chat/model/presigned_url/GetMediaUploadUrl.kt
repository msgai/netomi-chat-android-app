package com.netomi.chat.model.presigned_url

data class GetMediaUploadUrl(
    var url: String? = null,
    var type: String? = null,
    var title: String? = null,
    var fileSize: Long? = null,


    )
