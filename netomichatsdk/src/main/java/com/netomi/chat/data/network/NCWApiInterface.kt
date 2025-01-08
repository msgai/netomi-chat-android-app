package com.netomi.chat.data.network

import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.auth.LoginResponse
import com.netomi.chat.model.auth.LogoutResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEndChatResponse
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.survey.SubmitSurveyRequest
import com.netomi.chat.utils.NCWRoutes.LOGIN
import com.netomi.chat.utils.NCWRoutes.LOGOUT
import com.netomi.chat.utils.NCWRoutes.ROUTE_END_CHAT
import com.netomi.chat.utils.NCWRoutes.ROUTE_FEEDBACK_CHAT
import com.netomi.chat.utils.NCWRoutes.ROUTE_GET_CHAT
import com.netomi.chat.utils.NCWRoutes.ROUTE_GET_CONVERSATION_ID
import com.netomi.chat.utils.NCWRoutes.ROUTE_GET_MQTT_CREDENTIALS
import com.netomi.chat.utils.NCWRoutes.ROUTE_GET_PRESIGNED_URL
import com.netomi.chat.utils.NCWRoutes.ROUTE_SEND_CHAT
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
import retrofit2.http.Header

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
    suspend fun fetchChatHistory(@Body payload: NCWGetChatHistoryPayload?): Response<NCWGetChatHistoryResponse>

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
    suspend fun sendMessage(@Body message: NCWWebhookPayload?): Response<NCWSendMessageResponse>

    /**
     * Fetches the SDK theme configuration from the server based on the provided bot reference ID.
     *
     * @param botRefId The unique identifier for the bot whose theme configuration is to be retrieved.
     * @return A [Response] object containing the [NCWThemeResponse] with the theme details.
     *         The response status and data can be checked to determine if the request was successful.
     */
    @GET("json-config-mobile/{botRefId}.json")
    suspend fun getSdkTheme(
        @Path("botRefId") botRefId: String
    ): Response<NCWThemeResponse>

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
    suspend fun getConversationId(@Query("botRef") botRef: String?): Response<NCWGetConversationIdResponse>


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
    suspend fun getPreSignedUrl(@Body payload: NCWSignedUrlPayload?): Response<NCWGetPreSignedUrl>


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
    suspend fun hitEndChatAPI(
        @Body payload: NCWEndChatRequest?
    ): Response<NCWEndChatResponse>

    @POST(ROUTE_FEEDBACK_CHAT)
    suspend fun hitFeedbackAPI(@Body payload: NCWFeedbackRequest?): Response<NCWFeedbackResponse>

    @POST(ROUTE_END_CHAT)
    suspend fun hitSubmitSurveyRequestAPI(@Body payload: SubmitSurveyRequest?): Response<NCWEndChatResponse>

    @POST(LOGIN)
    suspend fun fetchJWT(
        @Header(LOGIN) userDetails: String
    ): Response<LoginResponse>

    @POST(LOGIN)
    suspend fun hitAuthenticateUserApi(
        @Header("x-bot-ref-id") botRefId: String,
        @Header("x-auth-enabled") authEnabled: String,
        @Header("x-auth-token") authToken: String,
    ): Response<LoginResponse>

    @POST(LOGOUT)
    suspend fun hitLogoutAPI(
        @Header("x-bot-ref-id") botRefId: String,
        @Header("x-auth-enabled") authEnabled: String,
        @Header("x-auth-token") authToken: String,
    ): Response<LogoutResponse>

}