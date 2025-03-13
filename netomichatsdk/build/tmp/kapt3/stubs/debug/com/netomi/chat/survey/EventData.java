package com.netomi.chat.survey;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\tH\u00c6\u0003J?\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/netomi/chat/survey/EventData;", "", "eventType", "", "subType", "eventInfo", "Lcom/netomi/chat/survey/EventInfo;", "authenticatedConversationId", "userdetails", "Lcom/netomi/chat/survey/NCWSignInUserDetails;", "(Ljava/lang/String;Ljava/lang/String;Lcom/netomi/chat/survey/EventInfo;Ljava/lang/String;Lcom/netomi/chat/survey/NCWSignInUserDetails;)V", "getAuthenticatedConversationId", "()Ljava/lang/String;", "setAuthenticatedConversationId", "(Ljava/lang/String;)V", "getEventInfo", "()Lcom/netomi/chat/survey/EventInfo;", "getEventType", "getSubType", "getUserdetails", "()Lcom/netomi/chat/survey/NCWSignInUserDetails;", "setUserdetails", "(Lcom/netomi/chat/survey/NCWSignInUserDetails;)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class EventData {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String eventType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String subType = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.survey.EventInfo eventInfo = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String authenticatedConversationId;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.survey.NCWSignInUserDetails userdetails;
    
    public EventData(@org.jetbrains.annotations.NotNull()
    java.lang.String eventType, @org.jetbrains.annotations.NotNull()
    java.lang.String subType, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.survey.EventInfo eventInfo, @org.jetbrains.annotations.Nullable()
    java.lang.String authenticatedConversationId, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.NCWSignInUserDetails userdetails) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEventType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.survey.EventInfo getEventInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAuthenticatedConversationId() {
        return null;
    }
    
    public final void setAuthenticatedConversationId(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.survey.NCWSignInUserDetails getUserdetails() {
        return null;
    }
    
    public final void setUserdetails(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.NCWSignInUserDetails p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.survey.EventInfo component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.survey.NCWSignInUserDetails component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.survey.EventData copy(@org.jetbrains.annotations.NotNull()
    java.lang.String eventType, @org.jetbrains.annotations.NotNull()
    java.lang.String subType, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.survey.EventInfo eventInfo, @org.jetbrains.annotations.Nullable()
    java.lang.String authenticatedConversationId, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.NCWSignInUserDetails userdetails) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}