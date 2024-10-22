package com.netomi.chat.data.network

import com.netomi.chat.data.apiconstant.NCWApiConstant.HEADER_TUTORIAL_STATUS
import com.netomi.chat.data.apiconstant.NCWApiConstant.HEADER_USER_TYPE
import com.netomi.chat.data.apiconstant.NCWApiConstant.USER_TYPE
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes.ROUTE_APP_CONFIG
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit API interface for defining network endpoints in the NCW SDK.
 *
 * This interface provides methods to interact with the NCW server,
 * including fetching chat history and sending new messages.
 * Each method is annotated with the appropriate **HTTP method** (GET, POST)
 * and defines the corresponding API endpoint.
 *
 * ## Responsibilities:
 * Fetch chat history from the server using a **GET** request.
 * Send new messages to the server using a **POST** request.
 * Provide clear abstraction for network operations to be used by the **NCWChatRepository**.
 *
 * ## Usage:
 * The **NCWApiInterface** is used with **Retrofit** to make network calls.
 * @see NCWChatRepository Uses this interface to perform network operations.
 */

interface NCWApiInterface {
    /**
     * Fetches the chat history from the server.
     *
     * This method sends a **GET** request to retrieve the complete chat history.
     * It returns a **Retrofit Call** that wraps a list of `NCWMessage` objects.
     * The caller needs to execute the call synchronously or asynchronously.
     *
     * @return A `Response` object wrapping a ArrayList of `NCWMessage` objects.
     */
    @GET("chat/history")
    fun fetchChatHistory(): Response<NCWBaseResponse<ArrayList<NCWMessage>>>

    /**
     * Sends a new chat message to the NCW server.
     * This method sends a **POST** request with the message details in the request body.
     * The server processes the message and returns a status code indicating success or failure.
     *
     * @param message A `NCWMessage` object containing the message content and metadata.
     * @return A `Response` object wrapping a `Boolean` response (indicating the operation's success or failure).
     *
     */
    @POST("chat/send")
    fun sendMessage(@Body message: NCWMessage): Response<NCWBaseResponse<Boolean>>

    @GET(ROUTE_APP_CONFIG)
    suspend fun getAppConfiguration(
        @Header(HEADER_USER_TYPE) userType: String = USER_TYPE,
        @Header(HEADER_TUTORIAL_STATUS) tutorialStatus: String = "true"
    ): Response<NCWBaseResponse<AppConfigurationResponseModel>>


}