package com.netomi.chat.data.network

import com.netomi.chat.data.apiconstant.NCWApiConstant.HEADER_TUTORIAL_STATUS
import com.netomi.chat.data.apiconstant.NCWApiConstant.HEADER_USER_TYPE
import com.netomi.chat.data.apiconstant.NCWApiConstant.USER_TYPE
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.BaseResponse
import com.netomi.chat.utils.Routes.ROUTE_APP_CONFIG
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NCWApiInterface {
    // GET request to fetch chat history
    @GET("chat/history")
    fun fetchChatHistory(): Response<BaseResponse<ArrayList<NCWMessage>>>

    //// POST request to send a new message
    @POST("chat/send")
    fun sendMessage(@Body message: NCWMessage): Response<BaseResponse<Boolean>>

    @GET(ROUTE_APP_CONFIG)
    suspend fun getAppConfiguration(
        @Header(HEADER_USER_TYPE) userType: String = USER_TYPE,
        @Header(HEADER_TUTORIAL_STATUS) tutorialStatus: String = "true"
    ): Response<BaseResponse<AppConfigurationResponseModel>>


}