package com.netomi.chat.ui.viewmodel;

/**
 * ViewModel for managing chat messages in the NCWChat application
 *
 * This class is responsible for holding and managing UI-related data for the chat activity.
 * It ensures that the chat data survives configuration changes, such as screen rotations.
 * Additionally, it provides methods to load chat history and send new messages.
 *
 * ## Responsibilities:
 * Load and manage the list of chat messages.
 * Expose chat messages through LiveData to be observed by the UI.
 * Provide a method to send new messages and update the message list.
 * Use `viewModelScope` to manage coroutines, ensuring proper lifecycle handling.
 *
 * ## Usage:
 * This ViewModel should be used in conjunction with the `ChatActivity`.
 * The UI observes the `chatMessages` LiveData to update the chat log in real-time.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00f0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010;\u001a\u00020U2\b\u0010V\u001a\u0004\u0018\u00010\fJ\u0010\u0010=\u001a\u00020U2\b\u0010W\u001a\u0004\u0018\u00010XJ+\u0010?\u001a\u00020U2\b\u0010V\u001a\u0004\u0018\u00010\f2\b\u0010Y\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010Z\u001a\u0004\u0018\u00010[\u00a2\u0006\u0002\u0010\\J\u0016\u0010]\u001a\u00020U2\u0006\u0010^\u001a\u00020\f2\u0006\u0010_\u001a\u00020\fJ\u0016\u0010`\u001a\u00020U2\u0006\u0010^\u001a\u00020\f2\u0006\u0010a\u001a\u00020\fJ\u000e\u0010b\u001a\u00020U2\u0006\u0010W\u001a\u00020\u0012J\u000e\u0010c\u001a\u00020U2\u0006\u0010d\u001a\u00020\fJ\u0016\u0010e\u001a\u00020U2\u0006\u0010f\u001a\u00020\f2\u0006\u0010d\u001a\u00020\fJ\u000e\u0010g\u001a\u00020U2\u0006\u0010h\u001a\u00020iJ\u000e\u0010j\u001a\u00020U2\u0006\u0010h\u001a\u00020kJ\u0016\u0010l\u001a\u00020U2\u0006\u0010f\u001a\u00020\f2\u0006\u0010d\u001a\u00020\fJ\u000e\u0010m\u001a\u00020U2\u0006\u0010h\u001a\u00020nJ\b\u0010o\u001a\u00020UH\u0002J\u001c\u0010p\u001a\u00020U2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020s0rH\u0082@\u00a2\u0006\u0002\u0010tJ\u0016\u0010M\u001a\u00020U2\u0006\u0010u\u001a\u00020\f2\u0006\u0010v\u001a\u00020wJ\u000e\u0010x\u001a\u00020U2\u0006\u0010h\u001a\u00020yJ\u000e\u0010z\u001a\u00020U2\u0006\u0010W\u001a\u00020{J\u000e\u0010|\u001a\u00020U2\u0006\u0010h\u001a\u00020\fJ\u0019\u0010}\u001a\u00020U2\b\u0010~\u001a\u0004\u0018\u00010\u007f2\u0007\u0010\u0080\u0001\u001a\u00020\u001eJ\u0016\u0010\u0081\u0001\u001a\u00020U2\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020s0rR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\r\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\f0-8F\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R)\u00100\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b1\u00102R\u000e\u00103\u001a\u000204X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b6\u00102R\u0019\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00068F\u00a2\u0006\u0006\u001a\u0004\b8\u00102R\u001d\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b:\u00102R\u001d\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b<\u00102R\u001d\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b>\u00102R\u001d\u0010?\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b@\u00102R\u001d\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bB\u00102R\u001d\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bD\u00102R\u001d\u0010E\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bF\u00102R\u001d\u0010G\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bH\u00102R\u001d\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bJ\u00102R\u001d\u0010K\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bL\u00102R\u001d\u0010M\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bN\u00102R\u0017\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00100\u00068F\u00a2\u0006\u0006\u001a\u0004\bP\u00102R\u001d\u0010Q\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bR\u00102R\u001d\u0010S\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\bT\u00102\u00a8\u0006\u0083\u0001"}, d2 = {"Lcom/netomi/chat/ui/viewmodel/NCWChatViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_NCW_Survey_RESPONSE", "Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent;", "Lcom/netomi/chat/utils/NCWState;", "Lcom/netomi/chat/model/endchat/NCWEndChatResponse;", "_NCW_endChatResponse", "_awsMessage", "Landroidx/lifecycle/MutableLiveData;", "", "_chatMessages", "Lcom/netomi/chat/utils/NCWBaseResponse;", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/NCWMessage;", "_errorFile", "Lcom/netomi/chat/model/media_payload/NCWSignedUrlPayload;", "_feedbackResponse", "Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackResponse;", "_getAWSMQTTCredentials", "Lcom/netomi/chat/model/mqtt/MQTTCredentialsResponse;", "_getChatHistory", "Lcom/netomi/chat/model/NCWGetChatHistoryResponse;", "_getConversationId", "Lcom/netomi/chat/model/NCWGetConversationIdResponse;", "_getLanguage", "Lcom/netomi/chat/model/language/LanguageResponse;", "_getSignedUrl", "Lcom/netomi/chat/model/presigned_url/NCWGetPreSignedUrl;", "_getTranscriptUrl", "Lcom/netomi/chat/model/ChatTranscriptResponse;", "_getUploadedMediaUrl", "Lcom/netomi/chat/model/presigned_url/NCWGetMediaUploadUrl;", "_loginResponse", "Lcom/netomi/chat/model/auth/LoginResponse;", "_logoutResponse", "Lcom/netomi/chat/model/auth/LogoutResponse;", "_sendMessage", "Lcom/netomi/chat/model/NCWSendMessageResponse;", "_sendMessages", "_surveyRuleResponse", "Lcom/netomi/chat/model/survey_rule/SurveyRuleResponse;", "awsMessage", "Landroidx/lifecycle/LiveData;", "getAwsMessage", "()Landroidx/lifecycle/LiveData;", "chatMessages", "getChatMessages", "()Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent;", "chatRepository", "Lcom/netomi/chat/data/repository/NCWChatRepository;", "endChatResponse", "getEndChatResponse", "errorFile", "getErrorFile", "feedbackResponse", "getFeedbackResponse", "getAWSMQTTCredentials", "getGetAWSMQTTCredentials", "getChatHistory", "getGetChatHistory", "getConversationId", "getGetConversationId", "getLanguage", "getGetLanguage", "getSignedUrl", "getGetSignedUrl", "getTranscriptUrl", "getGetTranscriptUrl", "getUploadedMediaUrl", "getGetUploadedMediaUrl", "loginResponse", "getLoginResponse", "logoutResponse", "getLogoutResponse", "sendMessage", "getSendMessage", "sendMessages", "getSendMessages", "surveyResponse", "getSurveyResponse", "surveyRuleResponse", "getSurveyRuleResponse", "", "botRef", "payload", "Lcom/netomi/chat/model/chat_history/NCWGetChatHistoryPayload;", "externalId", "onRestart", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)V", "getDownloadTranscriptUrl", "botRefId", "conversationId", "getLanguageStrings", "code", "getPreSignedUrl", "getSurveyRule", "botRefID", "hitAuthenticateUserApi", "jwtToken", "hitEndChatAPI", "message", "Lcom/netomi/chat/model/endchat/NCWEndChatRequest;", "hitFeedbackAPI", "Lcom/netomi/chat/model/feedback/feedbackrequest/NCWFeedbackRequest;", "hitLogoutApi", "hitSubmitSurveyRequestAPI", "Lcom/netomi/chat/survey/SubmitSurveyRequest;", "loadChatHistory", "processNextFile", "fileList", "", "Lcom/netomi/chat/model/media_payload/MultiFileModel;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "content", "timestamp", "", "sendMessageAPI", "Lcom/netomi/chat/model/messages/NCWWebhookPayload;", "sendTranscript", "Lcom/netomi/chat/model/transcript/NCWEmailRequest;", "updateAwsMessage", "uploadFile", "mediaUri", "Ljava/io/File;", "response", "uploadFilesSequentially", "mMultipleFile", "netomichatsdk_debug"})
public final class NCWChatViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.data.repository.NCWChatRepository chatRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.utils.NCWBaseResponse<java.util.ArrayList<com.netomi.chat.model.NCWMessage>>>> _chatMessages = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.model.NCWMessage> _sendMessages = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWSendMessageResponse>> _sendMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.endchat.NCWEndChatResponse>> _NCW_endChatResponse = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.endchat.NCWEndChatResponse>> _NCW_Survey_RESPONSE = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse>> _feedbackResponse = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.auth.LoginResponse>> _loginResponse = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.auth.LogoutResponse>> _logoutResponse = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.survey_rule.SurveyRuleResponse>> _surveyRuleResponse = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWGetConversationIdResponse>> _getConversationId = null;
    @org.jetbrains.annotations.NotNull()
    private com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.mqtt.MQTTCredentialsResponse>> _getAWSMQTTCredentials;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _awsMessage = null;
    @org.jetbrains.annotations.NotNull()
    private com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWGetChatHistoryResponse>> _getChatHistory;
    @org.jetbrains.annotations.NotNull()
    private com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl>> _getSignedUrl;
    @org.jetbrains.annotations.NotNull()
    private com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl>> _getUploadedMediaUrl;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.model.media_payload.NCWSignedUrlPayload> _errorFile = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.language.LanguageResponse>> _getLanguage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.ChatTranscriptResponse>> _getTranscriptUrl = null;
    
    public NCWChatViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.utils.NCWBaseResponse<java.util.ArrayList<com.netomi.chat.model.NCWMessage>>>> getChatMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.model.NCWMessage> getSendMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWSendMessageResponse>> getSendMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.endchat.NCWEndChatResponse>> getEndChatResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.endchat.NCWEndChatResponse>> getSurveyResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse>> getFeedbackResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.auth.LoginResponse>> getLoginResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.auth.LogoutResponse>> getLogoutResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.survey_rule.SurveyRuleResponse>> getSurveyRuleResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWGetConversationIdResponse>> getGetConversationId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.mqtt.MQTTCredentialsResponse>> getGetAWSMQTTCredentials() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getAwsMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.NCWGetChatHistoryResponse>> getGetChatHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl>> getGetSignedUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl>> getGetUploadedMediaUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.model.media_payload.NCWSignedUrlPayload> getErrorFile() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.language.LanguageResponse>> getGetLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.utils.NCWState<com.netomi.chat.model.ChatTranscriptResponse>> getGetTranscriptUrl() {
        return null;
    }
    
    public final void updateAwsMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    private final void loadChatHistory() {
    }
    
    /**
     * @param content The content of the message to be sent.
     */
    public final void sendMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String content, long timestamp) {
    }
    
    public final void sendMessageAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.NCWWebhookPayload message) {
    }
    
    public final void getConversationId(@org.jetbrains.annotations.Nullable()
    java.lang.String botRef, @org.jetbrains.annotations.Nullable()
    java.lang.String externalId, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean onRestart) {
    }
    
    public final void getAWSMQTTCredentials(@org.jetbrains.annotations.Nullable()
    java.lang.String botRef) {
    }
    
    public final void getChatHistory(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload payload) {
    }
    
    public final void getPreSignedUrl(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.media_payload.NCWSignedUrlPayload payload) {
    }
    
    public final void uploadFilesSequentially(@org.jetbrains.annotations.NotNull()
    java.util.List<com.netomi.chat.model.media_payload.MultiFileModel> mMultipleFile) {
    }
    
    private final java.lang.Object processNextFile(java.util.List<com.netomi.chat.model.media_payload.MultiFileModel> fileList, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void uploadFile(@org.jetbrains.annotations.Nullable()
    java.io.File mediaUri, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl response) {
    }
    
    public final void hitEndChatAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.endchat.NCWEndChatRequest message) {
    }
    
    public final void hitSubmitSurveyRequestAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.survey.SubmitSurveyRequest message) {
    }
    
    public final void hitFeedbackAPI(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest message) {
    }
    
    public final void hitAuthenticateUserApi(@org.jetbrains.annotations.NotNull()
    java.lang.String jwtToken, @org.jetbrains.annotations.NotNull()
    java.lang.String botRefID) {
    }
    
    public final void hitLogoutApi(@org.jetbrains.annotations.NotNull()
    java.lang.String jwtToken, @org.jetbrains.annotations.NotNull()
    java.lang.String botRefID) {
    }
    
    public final void getSurveyRule(@org.jetbrains.annotations.NotNull()
    java.lang.String botRefID) {
    }
    
    public final void sendTranscript(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.transcript.NCWEmailRequest payload) {
    }
    
    public final void getLanguageStrings(@org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    java.lang.String code) {
    }
    
    public final void getDownloadTranscriptUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
    java.lang.String conversationId) {
    }
}