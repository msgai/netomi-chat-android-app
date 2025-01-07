package com.netomi.sampleapplication.model

data class FetchJwtTokenResponse(
    val message: String,
    val statusCode: Int,
    val token: String
)