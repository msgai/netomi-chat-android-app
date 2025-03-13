package com.netomi.chat.model.messages;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b;\b\u0086\b\u0018\u00002\u00020\u0001B\u0095\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\u0010\b\u0002\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\u0018\b\u0002\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u001cj\b\u0012\u0004\u0012\u00020\u001d`\u001e\u0012\u0018\b\u0002\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020 0\u001cj\b\u0012\u0004\u0012\u00020 `\u001e\u00a2\u0006\u0002\u0010!J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010C\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u00100J\u000b\u0010D\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\u0011\u0010F\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015H\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0019\u0010K\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u001cj\b\u0012\u0004\u0012\u00020\u001d`\u001eH\u00c6\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0019\u0010M\u001a\u0012\u0012\u0004\u0012\u00020 0\u001cj\b\u0012\u0004\u0012\u00020 `\u001eH\u00c6\u0003J\u000b\u0010N\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010O\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010=J\t\u0010P\u001a\u00020\tH\u00c6\u0003J\u000b\u0010Q\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010S\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u00108J\u000b\u0010T\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u009e\u0002\u0010U\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0010\b\u0002\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\u0018\b\u0002\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u001cj\b\u0012\u0004\u0012\u00020\u001d`\u001e2\u0018\b\u0002\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020 0\u001cj\b\u0012\u0004\u0012\u00020 `\u001eH\u00c6\u0001\u00a2\u0006\u0002\u0010VJ\u0013\u0010W\u001a\u00020\t2\b\u0010X\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010Y\u001a\u00020\rH\u00d6\u0001J\t\u0010Z\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R*\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u001cj\b\u0012\u0004\u0012\u00020\u001d`\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010#R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010#R\u0019\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010#R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010#R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010#R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010/R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b\u0010\u00100R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010#R!\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020 0\u001cj\b\u0012\u0004\u0012\u00020 `\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010%R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010#R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0015\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u00109\u001a\u0004\b7\u00108R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010#R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010#R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b<\u0010=R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010#R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010#\u00a8\u0006["}, d2 = {"Lcom/netomi/chat/model/messages/NCWAttachmentContent;", "", "type", "", "text", "description", "timestamp", "", "isReviewEnabled", "", "attachmentResponseType", "intentId", "responseId", "", "id", "nodeName", "isVariationsEnabled", "largeImageUrl", "quickReply", "Lcom/netomi/chat/model/messages/NCWQuickReply;", "elements", "", "Lcom/netomi/chat/model/messages/NCWCarouselElement;", "carouselImageAspectRatio", "thumbnailUrl", "title", "feedbackValue", "buttons", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/NCWCarouselButton;", "Lkotlin/collections/ArrayList;", "multipleSourceDetails", "Lcom/netomi/chat/model/messages/MultipleSourceDetail;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Lcom/netomi/chat/model/messages/NCWQuickReply;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;)V", "getAttachmentResponseType", "()Ljava/lang/String;", "getButtons", "()Ljava/util/ArrayList;", "setButtons", "(Ljava/util/ArrayList;)V", "getCarouselImageAspectRatio", "getDescription", "getElements", "()Ljava/util/List;", "getFeedbackValue", "getId", "getIntentId", "()Z", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getLargeImageUrl", "getMultipleSourceDetails", "getNodeName", "getQuickReply", "()Lcom/netomi/chat/model/messages/NCWQuickReply;", "getResponseId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getText", "getThumbnailUrl", "getTimestamp", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getTitle", "getType", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Lcom/netomi/chat/model/messages/NCWQuickReply;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;)Lcom/netomi/chat/model/messages/NCWAttachmentContent;", "equals", "other", "hashCode", "toString", "netomichatsdk_debug"})
public final class NCWAttachmentContent {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String type = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String text = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long timestamp = null;
    private final boolean isReviewEnabled = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String attachmentResponseType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String intentId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer responseId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String nodeName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean isVariationsEnabled = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String largeImageUrl = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.NCWQuickReply quickReply = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> elements = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String carouselImageAspectRatio = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String thumbnailUrl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String feedbackValue = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> buttons;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> multipleSourceDetails = null;
    
    public NCWAttachmentContent(@org.jetbrains.annotations.Nullable()
    java.lang.String type, @org.jetbrains.annotations.Nullable()
    java.lang.String text, @org.jetbrains.annotations.Nullable()
    java.lang.String description, @org.jetbrains.annotations.Nullable()
    java.lang.Long timestamp, boolean isReviewEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String attachmentResponseType, @org.jetbrains.annotations.Nullable()
    java.lang.String intentId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer responseId, @org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String nodeName, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isVariationsEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String largeImageUrl, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReply quickReply, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> elements, @org.jetbrains.annotations.Nullable()
    java.lang.String carouselImageAspectRatio, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    java.lang.String feedbackValue, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> buttons, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> multipleSourceDetails) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getText() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getTimestamp() {
        return null;
    }
    
    public final boolean isReviewEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAttachmentResponseType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getIntentId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getResponseId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNodeName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean isVariationsEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLargeImageUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWQuickReply getQuickReply() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> getElements() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCarouselImageAspectRatio() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getThumbnailUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFeedbackValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> getButtons() {
        return null;
    }
    
    public final void setButtons(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> getMultipleSourceDetails() {
        return null;
    }
    
    public NCWAttachmentContent() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWQuickReply component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.NCWAttachmentContent copy(@org.jetbrains.annotations.Nullable()
    java.lang.String type, @org.jetbrains.annotations.Nullable()
    java.lang.String text, @org.jetbrains.annotations.Nullable()
    java.lang.String description, @org.jetbrains.annotations.Nullable()
    java.lang.Long timestamp, boolean isReviewEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String attachmentResponseType, @org.jetbrains.annotations.Nullable()
    java.lang.String intentId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer responseId, @org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String nodeName, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isVariationsEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String largeImageUrl, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReply quickReply, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> elements, @org.jetbrains.annotations.Nullable()
    java.lang.String carouselImageAspectRatio, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    java.lang.String feedbackValue, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> buttons, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> multipleSourceDetails) {
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