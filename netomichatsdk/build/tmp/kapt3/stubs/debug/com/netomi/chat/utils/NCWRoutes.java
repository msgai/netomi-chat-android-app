package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/netomi/chat/utils/NCWRoutes;", "", "()V", "API", "", "LOGIN", "LOGOUT", "ROUTE_BOT_JSON_CONFIG", "ROUTE_DOWNLOAD_TRANSCRIPT", "ROUTE_END_CHAT", "ROUTE_GET_CHAT", "ROUTE_GET_CONVERSATION_ID", "ROUTE_GET_LANGUAGE", "ROUTE_GET_MQTT_CREDENTIALS", "ROUTE_GET_PRESIGNED_URL", "ROUTE_GET_SURVEY_RULE", "ROUTE_SEND_CHAT", "ROUTE_SEND_TRANSCRIPT", "ROUTE_SURVEY", "ROUTE_UPLOAD_MEDIA", "ROUTE_WEBHOOK_EVENT", "WEBHOOK_EVENT", "netomichatsdk_debug"})
public final class NCWRoutes {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API = "api/";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_GET_CHAT = "api/webhook-history-v2";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_SEND_CHAT = "api/webhook-message";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_GET_CONVERSATION_ID = "api/getConversationId";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_GET_MQTT_CREDENTIALS = "api/mqtt";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_GET_PRESIGNED_URL = "api/upload";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_UPLOAD_MEDIA = "upload_media";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_END_CHAT = "api/webhook-event";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_BOT_JSON_CONFIG = "api/webhook-history-v2";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_SURVEY = "ROUTE_SURVEY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGIN = "api/login";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGOUT = "api/logout";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_GET_SURVEY_RULE = "api/survey_rule";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_WEBHOOK_EVENT = "api/webhook-event";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WEBHOOK_EVENT = "webhook-event";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_SEND_TRANSCRIPT = "api/sendTranscript";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_GET_LANGUAGE = "{botRefId}/language/configurable/{code}.json";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROUTE_DOWNLOAD_TRANSCRIPT = "api/downloadTranscript";
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.NCWRoutes INSTANCE = null;
    
    private NCWRoutes() {
        super();
    }
}