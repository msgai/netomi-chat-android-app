package com.netomi.chat.data.network

import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.messages.WebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes.ROUTE_GET_CONVERSATION_ID
import com.netomi.chat.utils.Routes.ROUTE_GET_MQTT_CREDENTIALS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    @POST("api/webhook-message")
    suspend fun sendMessage(@Body message: WebhookPayload?): Response<SendMessageResponse>

    @GET("json-config/{botRefId}.json")
    suspend fun getSdkTheme(
        @Path("botRefId") botRefId: String
    ): Response<ThemeResponse>

    /**
     * Fetches the Conversation ID from the NCW server.
     *
     * This method sends a **GET** request to retrieve the Conversation ID.
     * It returns a **Retrofit Call** that wraps a list of `GetConversationIdResponse` objects.
     * The caller needs to execute the call synchronously or asynchronously.
     * @param botRef A string representing the bot Reference ID we will get this from AI Studio
     * @return A `Response` object wrapping a Object of `GetConversationIdResponse` objects.
     */
    @GET(ROUTE_GET_CONVERSATION_ID)
    suspend fun getConversationId(@Query("botRef") botRef: String?): Response<GetConversationIdResponse>


    /**
     * Fetches the AWS MQTT Credentials from the NCW server.
     *
     * This method sends a **GET** request to retrieve the AWS MQTT Credentials.
     * It returns a **Retrofit Call** that wraps a list of `MQTTCredentialsResponse` objects.
     * The caller needs to execute the call synchronously or asynchronously.
     * @param botRef A string representing the bot Reference ID we will get this from AI Studio
     * @return A `Response` object wrapping a Object of `MQTTCredentialsResponse` objects.
     */
    @GET(ROUTE_GET_MQTT_CREDENTIALS)
    suspend fun getAWSMQTTCredentials(@Query("botRef") botRef: String?): Response<MQTTCredentialsResponse>


}