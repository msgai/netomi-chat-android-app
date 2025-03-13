package com.netomi.chat.data.repository;

/**
 * Repository responsible for managing chat-related data operations.
 *
 * This class acts as an abstraction layer between the ViewModel and the data sources (e.g., APIs).
 * It provides methods to fetch chat history and send new messages using a Retrofit API interface.
 * The repository ensures that data operations are encapsulated and managed in a consistent way.
 *
 * ## Responsibilities:
 * Fetch chat history from the server using the API.
 * Send new messages to the MQTT Broker/server.
 * Handle communication between the **ViewModel** and the **API**.
 *
 * ## Usage:
 * The **`NCWChatRepository`** is used by the **`NCWChatViewModel`** to perform data operations
 * such as loading chat history and sending new messages.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00d0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0086@\u00a2\u0006\u0002\u0010\rJD\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\t\"\u0004\b\u0000\u0010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\t0\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\t2\u0006\u0010\u0011\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\t2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\"J$\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\t2\u0006\u0010 \u001a\u00020\f2\u0006\u0010%\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\"J\u001c\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\t2\u0006\u0010\u0011\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u001c\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\t2\u0006\u0010,\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ,\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\t2\u0006\u0010/\u001a\u00020\f2\u0006\u0010,\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u00101J\u001c\u00102\u001a\b\u0012\u0004\u0012\u0002030\t2\u0006\u0010\u0011\u001a\u000204H\u0086@\u00a2\u0006\u0002\u00105J\u001c\u00106\u001a\b\u0012\u0004\u0012\u0002070\t2\u0006\u0010\u0011\u001a\u000208H\u0086@\u00a2\u0006\u0002\u00109J,\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\t2\u0006\u0010/\u001a\u00020\f2\u0006\u0010,\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u00101J\u001c\u0010<\u001a\b\u0012\u0004\u0012\u0002030\t2\u0006\u0010\u0011\u001a\u00020=H\u0086@\u00a2\u0006\u0002\u0010>J\u001c\u0010?\u001a\b\u0012\u0004\u0012\u00020@0\t2\u0006\u0010A\u001a\u00020BH\u0086@\u00a2\u0006\u0002\u0010CJ\u001c\u0010D\u001a\b\u0012\u0004\u0012\u00020@0\t2\u0006\u0010\u0011\u001a\u00020EH\u0086@\u00a2\u0006\u0002\u0010FJ&\u0010G\u001a\b\u0012\u0004\u0012\u00020H0\t2\b\u0010I\u001a\u0004\u0018\u00010J2\u0006\u0010K\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010LR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006M"}, d2 = {"Lcom/netomi/chat/data/repository/NCWChatRepository;", "Lcom/netomi/chat/data/network/NCWBaseService;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "apiInterface", "Lcom/netomi/chat/data/network/NCWApiInterface;", "kotlin.jvm.PlatformType", "getAWSMQTTCredentials", "Lcom/netomi/chat/utils/NCWState;", "Lcom/netomi/chat/model/mqtt/MQTTCredentialsResponse;", "botRef", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChatHistory", "Lcom/netomi/chat/model/NCWGetChatHistoryResponse;", "T", "payload", "Lcom/netomi/chat/model/chat_history/NCWGetChatHistoryPayload;", "liveData", "Landroidx/lifecycle/MutableLiveData;", "loadingType", "Lcom/netomi/chat/utils/NCWState$LoadingType;", "(Lcom/netomi/chat/model/chat_history/NCWGetChatHistoryPayload;Landroidx/lifecycle/MutableLiveData;Lcom/netomi/chat/utils/NCWState$LoadingType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversationId", "Lcom/netomi/chat/model/NCWGetConversationIdResponse;", "Lcom/netomi/chat/model/GetConversationPayload;", "onRestart", "", "(Lcom/netomi/chat/model/GetConversationPayload;Ljava/lang/Boolean;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDownloadTranscriptUrl", "Lcom/netomi/chat/model/ChatTranscriptResponse;", "botRefId", "conversationId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLanguageStrings", "Lcom/netomi/chat/model/language/LanguageResponse;", "code", "getPreSignedUrl", "Lcom/netomi/chat/model/presigned_url/NCWGetPreSignedUrl;", "Lcom/netomi/chat/model/media_payload/NCWSignedUrlPayload;", "(Lcom/netomi/chat/model/media_payload/NCWSignedUrlPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSurveyRule", "Lcom/netomi/chat/model/survey_rule/SurveyRuleResponse;", "botRefID", "hitAuthenticateUserApi", "Lcom/netomi/chat/model/auth/LoginResponse;", "jwtToken", "authEnabled", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hitEndChatAPI", "Lcom/netomi/chat/model/endchat/NCWEndChatResponse;", "Lcom/netomi/chat/model/endchat/NCWEndChatRequest;", "(Lcom/netomi/chat/model/endchat/NCWEndChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hitFeedbackAPI", "Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackResponse;", "Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackRequest;", "(Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hitLogoutApi", "Lcom/netomi/chat/model/auth/LogoutResponse;", "hitSubmitSurveyRequestAPI", "Lcom/netomi/chat/survey/SubmitSurveyRequest;", "(Lcom/netomi/chat/survey/SubmitSurveyRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "Lcom/netomi/chat/model/NCWSendMessageResponse;", "message", "Lcom/netomi/chat/model/messages/NCWWebhookPayload;", "(Lcom/netomi/chat/model/messages/NCWWebhookPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTranscript", "Lcom/netomi/chat/model/transcript/NCWEmailRequest;", "(Lcom/netomi/chat/model/transcript/NCWEmailRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadFile", "Lcom/netomi/chat/model/presigned_url/NCWGetMediaUploadUrl;", "mediaFile", "Ljava/io/File;", "preSignedUrl", "(Ljava/io/File;Lcom/netomi/chat/model/presigned_url/NCWGetPreSignedUrl;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "netomichatsdk_debug"})
public final class NCWChatRepository extends com.netomi.chat.data.network.NCWBaseService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final com.netomi.chat.data.network.NCWApiInterface apiInterface = null;
    
    public NCWChatRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object getChatHistory(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload payload, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.netomi.chat.utils.NCWState<T>> liveData, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.utils.NCWState.LoadingType loadingType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWGetChatHistoryResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendMessage(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.NCWWebhookPayload message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWSendMessageResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getConversationId(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.GetConversationPayload payload, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean onRestart, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWGetConversationIdResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAWSMQTTCredentials(@org.jetbrains.annotations.Nullable()
    java.lang.String botRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.mqtt.MQTTCredentialsResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPreSignedUrl(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.media_payload.NCWSignedUrlPayload payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadFile(@org.jetbrains.annotations.Nullable()
    java.io.File mediaFile, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl preSignedUrl, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hitEndChatAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.endchat.NCWEndChatRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.endchat.NCWEndChatResponse>> $completion) {
        return null;
    }
    
    /**
     * Hit Feedback API
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hitFeedbackAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hitSubmitSurveyRequestAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.survey.SubmitSurveyRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.endchat.NCWEndChatResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hitAuthenticateUserApi(@org.jetbrains.annotations.NotNull()
    java.lang.String jwtToken, @org.jetbrains.annotations.NotNull()
    java.lang.String botRefID, @org.jetbrains.annotations.NotNull()
    java.lang.String authEnabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.auth.LoginResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hitLogoutApi(@org.jetbrains.annotations.NotNull()
    java.lang.String jwtToken, @org.jetbrains.annotations.NotNull()
    java.lang.String botRefID, @org.jetbrains.annotations.NotNull()
    java.lang.String authEnabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.auth.LogoutResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSurveyRule(@org.jetbrains.annotations.NotNull()
    java.lang.String botRefID, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.survey_rule.SurveyRuleResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendTranscript(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.transcript.NCWEmailRequest payload, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWSendMessageResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getLanguageStrings(@org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.language.LanguageResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDownloadTranscriptUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    java.lang.String conversationId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<com.netomi.chat.model.ChatTranscriptResponse>> $completion) {
        return null;
    }
}