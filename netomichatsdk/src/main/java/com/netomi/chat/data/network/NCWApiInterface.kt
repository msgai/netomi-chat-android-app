package com.netomi.chat.data.network

import com.netomi.chat.model.GetChatHistoryResponse
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.chat_history.GetChatHistoryPayload
import com.netomi.chat.model.endchat.EndChatRequest
import com.netomi.chat.model.endchat.EndChatResponse
import com.netomi.chat.model.media_payload.SignedUrlPayload
import com.netomi.chat.model.messages.WebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.GetPreSignedUrl
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes.ROUTE_END_CHAT
import com.netomi.chat.utils.Routes.ROUTE_GET_CHAT
import com.netomi.chat.utils.Routes.ROUTE_GET_CONVERSATION_ID
import com.netomi.chat.utils.Routes.ROUTE_GET_MQTT_CREDENTIALS
import com.netomi.chat.utils.Routes.ROUTE_GET_PRESIGNED_URL
import com.netomi.chat.utils.Routes.ROUTE_SEND_CHAT
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import okhttp3.MultipartBody
import okhttp3.ResponseBody

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

    @POST(ROUTE_GET_CHAT)
    suspend fun fetchChatHistory(@Body payload: GetChatHistoryPayload?): Response<GetChatHistoryResponse>

    /**
     * Sends a new chat message to the NCW server.
     * This method sends a **POST** request with the message details in the request body.
     * The server processes the message and returns a status code indicating success or failure.
     *
     * @param message A `NCWMessage` object containing the message content and metadata.
     * @return A `Response` object wrapping a `Boolean` response (indicating the operation's success or failure).
     *
     */
    @POST(ROUTE_SEND_CHAT)
    suspend fun sendMessage(@Body message: WebhookPayload?): Response<SendMessageResponse>

    /**
     * Fetches the SDK theme configuration from the server based on the provided bot reference ID.
     *
     * @param botRefId The unique identifier for the bot whose theme configuration is to be retrieved.
     * @return A [Response] object containing the [ThemeResponse] with the theme details.
     *         The response status and data can be checked to determine if the request was successful.
     */
    @GET("json-config-mobile/{botRefId}.json")
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

    @POST(ROUTE_GET_PRESIGNED_URL)
    suspend fun getPreSignedUrl(@Body payload: SignedUrlPayload?): Response<GetPreSignedUrl>


    @Multipart
    @POST
    suspend fun uploadFile(
        @Url url: String,
        @Part("key") key: RequestBody?,
        @Part("bucket") bucket: RequestBody?,
        @Part("X-Amz-Algorithm") amzAlgorithm: RequestBody?,
        @Part("X-Amz-Credential") amzCredential: RequestBody?,
        @Part("X-Amz-Date") amzDate: RequestBody?,
        @Part("Policy") policy: RequestBody?,
        @Part("X-Amz-Signature") amzSignature: RequestBody?,
        @Part("acl") acl: RequestBody?,
        @Part("Content-Type") contentType: RequestBody?,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>
    /*@Multipart
    @POST
    suspend fun uploadFile(
        @Url url: String,
        @Part("key") key: RequestBody?,
        @Part("bucket") bucket: RequestBody?,
        @Part("X-Amz-Algorithm") amzAlgorithm: RequestBody?,
        @Part("X-Amz-Credential") amzCredential: RequestBody?,
        @Part("X-Amz-Date") amzDate: RequestBody?,
        @Part("Policy") policy: RequestBody?,
        @Part("X-Amz-Signature") amzSignature: RequestBody?,
        @Part("acl") acl: RequestBody?,
        @Part("Content-Type") contentType: RequestBody?,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>*/

    @POST(ROUTE_END_CHAT)
    suspend fun hitEndChatAPI(@Body payload: EndChatRequest?): Response<EndChatResponse>

}