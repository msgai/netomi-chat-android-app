package com.netomi.chat.data.apiconstant;

/**
 * Object class that holds constant values used in API requests.
 *
 * This class provides a centralized location for defining API-related constants, such as
 * user-type values. These constants ensure consistency across the codebase
 * and reduce the chances of hardcoding strings multiple times.
 *
 * ## Responsibilities:
 * - Define constant values used for API headers (e.g., Authorization, Bearer tokens).
 * - Provide values for user-related API headers, such as user type and tutorial status.
 * - Ensure that constants are easily accessible throughout the NCW SDK.
 *
 * ## Usage:
 * The **`NCWApiConstant`** object is accessed directly since it is declared as an `object`.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/netomi/chat/data/apiconstant/NCWApiConstant;", "", "()V", "BOT_REF_ID", "", "CODE", "HEADER_AUTH", "HEADER_BEARER", "X_AUTH_ENABLE", "X_AUTH_TOKEN", "X_BOT_REF_ID", "X_CONVERSATION_ID", "netomichatsdk_debug"})
public final class NCWApiConstant {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HEADER_BEARER = "Bearer";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HEADER_AUTH = "Authorization";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String X_BOT_REF_ID = "x-bot-ref-id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String X_AUTH_ENABLE = "x-auth-enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String X_AUTH_TOKEN = "x-auth-token";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String X_CONVERSATION_ID = "x-conversation-id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CODE = "code";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BOT_REF_ID = "botRefId";
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.data.apiconstant.NCWApiConstant INSTANCE = null;
    
    private NCWApiConstant() {
        super();
    }
}