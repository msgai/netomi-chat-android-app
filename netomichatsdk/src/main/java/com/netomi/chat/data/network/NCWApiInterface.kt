package com.netomi.chat.data.network

import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NCWApiInterface {
    // GET request to fetch chat history
    @GET("chat/history")
    fun fetchChatHistory(): Response<BaseResponse<ArrayList<NCWMessage>>>

    //// POST request to send a new message
    @POST("chat/send")
    fun sendMessage(@Body message: NCWMessage): Response<BaseResponse<Boolean>>
}