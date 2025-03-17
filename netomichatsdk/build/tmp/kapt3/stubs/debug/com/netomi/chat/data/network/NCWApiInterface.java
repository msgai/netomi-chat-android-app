package com.netomi.chat.data.network;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00c2\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ,\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\n\b\u0003\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u00010\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015J(\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u000b2\b\b\u0001\u0010\u0018\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0019J<\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u000b2\b\b\u0001\u0010\u001c\u001a\u00020\u000b2\b\b\u0003\u0010\u001d\u001a\u00020\u000b2\b\b\u0003\u0010\u001e\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ \u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00032\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u00010\"H\u00a7@\u00a2\u0006\u0002\u0010#J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ2\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u000b2\b\b\u0001\u0010)\u001a\u00020\u000b2\b\b\u0001\u0010*\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010+J \u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00032\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u00010.H\u00a7@\u00a2\u0006\u0002\u0010/J \u00100\u001a\b\u0012\u0004\u0012\u0002010\u00032\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u000102H\u00a7@\u00a2\u0006\u0002\u00103J2\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u000b2\b\b\u0001\u0010)\u001a\u00020\u000b2\b\b\u0001\u0010*\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010+J \u00106\u001a\b\u0012\u0004\u0012\u00020-0\u00032\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u000107H\u00a7@\u00a2\u0006\u0002\u00108J \u00109\u001a\b\u0012\u0004\u0012\u00020:0\u00032\n\b\u0001\u0010;\u001a\u0004\u0018\u00010<H\u00a7@\u00a2\u0006\u0002\u0010=J\u001e\u0010>\u001a\b\u0012\u0004\u0012\u00020:0\u00032\b\b\u0001\u0010\u0005\u001a\u00020?H\u00a7@\u00a2\u0006\u0002\u0010@J\u0094\u0001\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\u00032\b\b\u0001\u0010C\u001a\u00020\u000b2\n\b\u0001\u0010D\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010F\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010G\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010H\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010I\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010J\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010K\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010L\u001a\u0004\u0018\u00010E2\n\b\u0001\u0010M\u001a\u0004\u0018\u00010E2\b\b\u0001\u0010N\u001a\u00020OH\u00a7@\u00a2\u0006\u0002\u0010P\u00a8\u0006Q"}, d2 = {"Lcom/netomi/chat/data/network/NCWApiInterface;", "", "fetchChatHistory", "Lretrofit2/Response;", "Lcom/netomi/chat/model/NCWGetChatHistoryResponse;", "payload", "Lcom/netomi/chat/model/chat_history/NCWGetChatHistoryPayload;", "(Lcom/netomi/chat/model/chat_history/NCWGetChatHistoryPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchJWT", "Lcom/netomi/chat/model/auth/LoginResponse;", "userDetails", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAWSMQTTCredentials", "Lcom/netomi/chat/model/mqtt/MQTTCredentialsResponse;", "botRefId", "getConversationId", "Lcom/netomi/chat/model/NCWGetConversationIdResponse;", "restart", "", "Lcom/netomi/chat/model/GetConversationPayload;", "(Ljava/lang/Boolean;Lcom/netomi/chat/model/GetConversationPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDownloadTranscriptUrl", "Lcom/netomi/chat/model/ChatTranscriptResponse;", "conversationId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLanguageStrings", "Lcom/netomi/chat/model/language/LanguageResponse;", "code", "origin", "referer", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPreSignedUrl", "Lcom/netomi/chat/model/presigned_url/NCWGetPreSignedUrl;", "Lcom/netomi/chat/model/media_payload/NCWSignedUrlPayload;", "(Lcom/netomi/chat/model/media_payload/NCWSignedUrlPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSdkTheme", "Lcom/netomi/chat/model/theme/NCWThemeResponse;", "getSurveyRule", "Lcom/netomi/chat/model/survey_rule/SurveyRuleResponse;", "hitAuthenticateUserApi", "authEnabled", "authToken", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hitEndChatAPI", "Lcom/netomi/chat/model/endchat/NCWEndChatResponse;", "Lcom/netomi/chat/model/endchat/NCWEndChatRequest;", "(Lcom/netomi/chat/model/endchat/NCWEndChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hitFeedbackAPI", "Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackResponse;", "Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackRequest;", "(Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hitLogoutAPI", "Lcom/netomi/chat/model/auth/LogoutResponse;", "hitSubmitSurveyRequestAPI", "Lcom/netomi/chat/survey/SubmitSurveyRequest;", "(Lcom/netomi/chat/survey/SubmitSurveyRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "Lcom/netomi/chat/model/NCWSendMessageResponse;", "message", "Lcom/netomi/chat/model/messages/NCWWebhookPayload;", "(Lcom/netomi/chat/model/messages/NCWWebhookPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTranscript", "Lcom/netomi/chat/model/transcript/NCWEmailRequest;", "(Lcom/netomi/chat/model/transcript/NCWEmailRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadFile", "Lokhttp3/ResponseBody;", "url", "key", "Lokhttp3/RequestBody;", "bucket", "amzAlgorithm", "amzCredential", "amzDate", "policy", "amzSignature", "acl", "contentType", "file", "Lokhttp3/MultipartBody$Part;", "(Ljava/lang/String;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "netomichatsdk_debug"})
public abstract interface NCWApiInterface {
    
    /**
     * Fetches the chat history from the server.
     *
     * This method sends a **GET** request to retrieve the complete chat history.
     * It returns a **Retrofit Call** that wraps a list of `NCWMessage` objects.
     * The caller needs to execute the call synchronously or asynchronously.
     *
     * @return A `Response` object wrapping a ArrayList of `NCWMessage` objects.
     */
    @retrofit2.http.POST(value = "api/webhook-history-v2")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object fetchChatHistory(@retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.NCWGetChatHistoryResponse>> $completion);
    
    /**
     * Sends a new chat message to the NCW server.
     * This method sends a **POST** request with the message details in the request body.
     * The server processes the message and returns a status code indicating success or failure.
     *
     * @param message A `NCWMessage` object containing the message content and metadata.
     * @return A `Response` object wrapping a `Boolean` response (indicating the operation's success or failure).
     */
    @retrofit2.http.POST(value = "api/webhook-message")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendMessage(@retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWWebhookPayload message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.NCWSendMessageResponse>> $completion);
    
    /**
     * Fetches the SDK theme configuration from the server based on the provided bot reference ID.
     *
     * @param botRefId The unique identifier for the bot whose theme configuration is to be retrieved.
     * @return A [Response] object containing the [NCWThemeResponse] with the theme details.
     *        The response status and data can be checked to determine if the request was successful.
     */
    @retrofit2.http.GET(value = "json-config-mobile/{botRefId}.json")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSdkTheme(@retrofit2.http.Path(value = "botRefId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.theme.NCWThemeResponse>> $completion);
    
    /**
     * Fetches the Conversation ID from the NCW server.
     *
     * This method sends a **GET** request to retrieve the Conversation ID.
     * It returns a **Retrofit Call** that wraps a list of `GetConversationIdResponse` objects.
     * The caller needs to execute the call synchronously or asynchronously.
     * @param botRef A string representing the bot Reference ID we will get this from AI Studio
     * @return A `Response` object wrapping a Object of `GetConversationIdResponse` objects.
     */
    @retrofit2.http.POST(value = "api/getConversationId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getConversationId(@retrofit2.http.Header(value = "restart")
    @org.jetbrains.annotations.Nullable()
    java.lang.Boolean restart, @retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.GetConversationPayload payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.NCWGetConversationIdResponse>> $completion);
    
    /**
     * Fetches the AWS MQTT Credentials from the NCW server.
     *
     * This method sends a **GET** request to retrieve the AWS MQTT Credentials.
     * It returns a **Retrofit Call** that wraps a list of `MQTTCredentialsResponse` objects.
     * The caller needs to execute the call synchronously or asynchronously.
     * @param botRef A string representing the bot Reference ID we will get this from AI Studio
     * @return A `Response` object wrapping a Object of `MQTTCredentialsResponse` objects.
     */
    @retrofit2.http.GET(value = "api/mqtt")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAWSMQTTCredentials(@retrofit2.http.Header(value = "x-bot-ref-id")
    @org.jetbrains.annotations.Nullable()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.mqtt.MQTTCredentialsResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/upload")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPreSignedUrl(@retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.media_payload.NCWSignedUrlPayload payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl>> $completion);
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object uploadFile(@retrofit2.http.Url()
    @org.jetbrains.annotations.NotNull()
    java.lang.String url, @retrofit2.http.Part(value = "key")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody key, @retrofit2.http.Part(value = "bucket")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody bucket, @retrofit2.http.Part(value = "X-Amz-Algorithm")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody amzAlgorithm, @retrofit2.http.Part(value = "X-Amz-Credential")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody amzCredential, @retrofit2.http.Part(value = "X-Amz-Date")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody amzDate, @retrofit2.http.Part(value = "Policy")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody policy, @retrofit2.http.Part(value = "X-Amz-Signature")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody amzSignature, @retrofit2.http.Part(value = "acl")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody acl, @retrofit2.http.Part(value = "Content-Type")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody contentType, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<okhttp3.ResponseBody>> $completion);
    
    @retrofit2.http.POST(value = "api/webhook-event")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hitEndChatAPI(@retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.endchat.NCWEndChatRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.endchat.NCWEndChatResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/webhook-event")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hitFeedbackAPI(@retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/webhook-event")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hitSubmitSurveyRequestAPI(@retrofit2.http.Body()
    @org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.SubmitSurveyRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.endchat.NCWEndChatResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object fetchJWT(@retrofit2.http.Header(value = "api/login")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userDetails, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.auth.LoginResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hitAuthenticateUserApi(@retrofit2.http.Header(value = "x-bot-ref-id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @retrofit2.http.Header(value = "x-auth-enabled")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authEnabled, @retrofit2.http.Header(value = "x-auth-token")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.auth.LoginResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/logout")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hitLogoutAPI(@retrofit2.http.Header(value = "x-bot-ref-id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @retrofit2.http.Header(value = "x-auth-enabled")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authEnabled, @retrofit2.http.Header(value = "x-auth-token")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.auth.LogoutResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/survey_rule")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSurveyRule(@retrofit2.http.Header(value = "x-bot-ref-id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.survey_rule.SurveyRuleResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/sendTranscript")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendTranscript(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.transcript.NCWEmailRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.NCWSendMessageResponse>> $completion);
    
    @retrofit2.http.GET(value = "{botRefId}/language/configurable/{code}.json")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLanguageStrings(@retrofit2.http.Path(value = "botRefId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @retrofit2.http.Path(value = "code")
    @org.jetbrains.annotations.NotNull()
    java.lang.String code, @retrofit2.http.Header(value = "Origin")
    @org.jetbrains.annotations.NotNull()
    java.lang.String origin, @retrofit2.http.Header(value = "Referer")
    @org.jetbrains.annotations.NotNull()
    java.lang.String referer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.language.LanguageResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/downloadTranscript")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDownloadTranscriptUrl(@retrofit2.http.Header(value = "x-bot-ref-id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @retrofit2.http.Header(value = "x-conversation-id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String conversationId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.netomi.chat.model.ChatTranscriptResponse>> $completion);
    
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
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}