package com.netomi.chat.model.messages;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00ad\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u00a2\u0006\u0002\u0010\u0017J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010/\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u000eH\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0016H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u00102\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u00108\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eH\u00c6\u0003J\u00b6\u0001\u00109\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u000e2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00c6\u0001\u00a2\u0006\u0002\u0010:J\u0013\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010>\u001a\u00020?H\u00d6\u0001J\t\u0010@\u001a\u00020\u0003H\u00d6\u0001R\u0019\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0019\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010)\u001a\u0004\b\'\u0010(R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001b\u00a8\u0006A"}, d2 = {"Lcom/netomi/chat/model/messages/NCWGenericChannelResponse;", "", "type", "", "botReferenceId", "timestamp", "", "ownerType", "channel", "requestId", "requestPayload", "Lcom/netomi/chat/model/messages/NCWRequestPayload;", "botId", "attachments", "", "Lcom/netomi/chat/model/messages/NCWAttachment;", "customPayload", "Lcom/netomi/chat/model/messages/NCWCustomPayload;", "triggerType", "customFields", "Lcom/netomi/chat/model/messages/CustomField;", "eventObject", "Lcom/netomi/chat/model/messages/EventObject;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/netomi/chat/model/messages/NCWRequestPayload;Ljava/lang/String;Ljava/util/List;Lcom/netomi/chat/model/messages/NCWCustomPayload;Ljava/lang/String;Ljava/util/List;Lcom/netomi/chat/model/messages/EventObject;)V", "getAttachments", "()Ljava/util/List;", "getBotId", "()Ljava/lang/String;", "getBotReferenceId", "getChannel", "getCustomFields", "getCustomPayload", "()Lcom/netomi/chat/model/messages/NCWCustomPayload;", "getEventObject", "()Lcom/netomi/chat/model/messages/EventObject;", "getOwnerType", "getRequestId", "getRequestPayload", "()Lcom/netomi/chat/model/messages/NCWRequestPayload;", "getTimestamp", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getTriggerType", "getType", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/netomi/chat/model/messages/NCWRequestPayload;Ljava/lang/String;Ljava/util/List;Lcom/netomi/chat/model/messages/NCWCustomPayload;Ljava/lang/String;Ljava/util/List;Lcom/netomi/chat/model/messages/EventObject;)Lcom/netomi/chat/model/messages/NCWGenericChannelResponse;", "equals", "", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class NCWGenericChannelResponse {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String type = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String botReferenceId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long timestamp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ownerType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String channel = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String requestId = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.NCWRequestPayload requestPayload = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String botId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.netomi.chat.model.messages.NCWAttachment> attachments = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.NCWCustomPayload customPayload = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String triggerType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.netomi.chat.model.messages.CustomField> customFields = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.EventObject eventObject = null;
    
    public NCWGenericChannelResponse(@org.jetbrains.annotations.Nullable()
    java.lang.String type, @org.jetbrains.annotations.Nullable()
    java.lang.String botReferenceId, @org.jetbrains.annotations.Nullable()
    java.lang.Long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String ownerType, @org.jetbrains.annotations.Nullable()
    java.lang.String channel, @org.jetbrains.annotations.Nullable()
    java.lang.String requestId, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWRequestPayload requestPayload, @org.jetbrains.annotations.Nullable()
    java.lang.String botId, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.NCWAttachment> attachments, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCustomPayload customPayload, @org.jetbrains.annotations.Nullable()
    java.lang.String triggerType, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.CustomField> customFields, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.EventObject eventObject) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBotReferenceId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOwnerType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getChannel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRequestId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWRequestPayload getRequestPayload() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBotId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.NCWAttachment> getAttachments() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWCustomPayload getCustomPayload() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTriggerType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.CustomField> getCustomFields() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.EventObject getEventObject() {
        return null;
    }
    
    public NCWGenericChannelResponse() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWCustomPayload component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.CustomField> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.EventObject component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component3() {
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
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWRequestPayload component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.NCWAttachment> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.NCWGenericChannelResponse copy(@org.jetbrains.annotations.Nullable()
    java.lang.String type, @org.jetbrains.annotations.Nullable()
    java.lang.String botReferenceId, @org.jetbrains.annotations.Nullable()
    java.lang.Long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String ownerType, @org.jetbrains.annotations.Nullable()
    java.lang.String channel, @org.jetbrains.annotations.Nullable()
    java.lang.String requestId, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWRequestPayload requestPayload, @org.jetbrains.annotations.Nullable()
    java.lang.String botId, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.NCWAttachment> attachments, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCustomPayload customPayload, @org.jetbrains.annotations.Nullable()
    java.lang.String triggerType, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.CustomField> customFields, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.EventObject eventObject) {
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