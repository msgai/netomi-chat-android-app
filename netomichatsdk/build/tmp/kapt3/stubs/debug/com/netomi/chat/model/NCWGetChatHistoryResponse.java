package com.netomi.chat.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0018\b\u0002\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\u000b\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0019\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bH\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010&\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019Jd\u0010\'\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0018\b\u0002\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020\fH\u00d6\u0001J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R.\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\"\u0010\u000b\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001c\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR \u0010\n\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u000f\"\u0004\b\u001e\u0010\u0011R \u0010\t\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u000f\"\u0004\b \u0010\u0011\u00a8\u0006."}, d2 = {"Lcom/netomi/chat/model/NCWGetChatHistoryResponse;", "", "conversationId", "", "botReferenceId", "responses", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/NCWGenericChannelResponse;", "Lkotlin/collections/ArrayList;", "statusMessage", "statusCode", "status", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V", "getBotReferenceId", "()Ljava/lang/String;", "setBotReferenceId", "(Ljava/lang/String;)V", "getConversationId", "setConversationId", "getResponses", "()Ljava/util/ArrayList;", "setResponses", "(Ljava/util/ArrayList;)V", "getStatus", "()Ljava/lang/Integer;", "setStatus", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getStatusCode", "setStatusCode", "getStatusMessage", "setStatusMessage", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)Lcom/netomi/chat/model/NCWGetChatHistoryResponse;", "equals", "", "other", "hashCode", "toString", "netomichatsdk_debug"})
public final class NCWGetChatHistoryResponse {
    @com.google.gson.annotations.SerializedName(value = "conversationId")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String conversationId;
    @com.google.gson.annotations.SerializedName(value = "botReferenceId")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String botReferenceId;
    @com.google.gson.annotations.SerializedName(value = "responses")
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> responses;
    @com.google.gson.annotations.SerializedName(value = "statusMessage")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String statusMessage;
    @com.google.gson.annotations.SerializedName(value = "statusCode")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String statusCode;
    @com.google.gson.annotations.SerializedName(value = "status")
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer status;
    
    public NCWGetChatHistoryResponse(@org.jetbrains.annotations.Nullable()
    java.lang.String conversationId, @org.jetbrains.annotations.Nullable()
    java.lang.String botReferenceId, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> responses, @org.jetbrains.annotations.Nullable()
    java.lang.String statusMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String statusCode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer status) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getConversationId() {
        return null;
    }
    
    public final void setConversationId(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBotReferenceId() {
        return null;
    }
    
    public final void setBotReferenceId(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> getResponses() {
        return null;
    }
    
    public final void setResponses(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStatusMessage() {
        return null;
    }
    
    public final void setStatusMessage(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStatusCode() {
        return null;
    }
    
    public final void setStatusCode(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getStatus() {
        return null;
    }
    
    public final void setStatus(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    public NCWGetChatHistoryResponse() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.NCWGetChatHistoryResponse copy(@org.jetbrains.annotations.Nullable()
    java.lang.String conversationId, @org.jetbrains.annotations.Nullable()
    java.lang.String botReferenceId, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> responses, @org.jetbrains.annotations.Nullable()
    java.lang.String statusMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String statusCode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer status) {
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