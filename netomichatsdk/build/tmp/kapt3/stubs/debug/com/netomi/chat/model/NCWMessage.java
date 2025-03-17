package com.netomi.chat.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b^\b\u0086\b\u0018\u00002\u00020\u0001B\u00f9\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\u0018\b\u0002\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0015j\b\u0012\u0004\u0012\u00020\u0016`\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0019\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0019\u0012\u001c\b\u0002\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u0017\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010&\u001a\u00020\u0019\u0012\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010(\u0012\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010*\u001a\u00020+\u0012\u0018\b\u0002\u0010,\u001a\u0012\u0012\u0004\u0012\u00020-0\u0015j\b\u0012\u0004\u0012\u00020-`\u0017\u00a2\u0006\u0002\u0010.J\u000b\u0010j\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003J\u000b\u0010l\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010m\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0019\u0010n\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0015j\b\u0012\u0004\u0012\u00020\u0016`\u0017H\u00c6\u0003J\t\u0010o\u001a\u00020\u0019H\u00c6\u0003J\t\u0010p\u001a\u00020\u0019H\u00c6\u0003J\u000b\u0010q\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010s\u001a\u00020\u0019H\u00c6\u0003J\u001d\u0010t\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u0017H\u00c6\u0003J\u000b\u0010u\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010v\u001a\u0004\u0018\u00010!H\u00c6\u0003J\u000b\u0010w\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010x\u001a\u0004\u0018\u00010$H\u00c6\u0003J\u000b\u0010y\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010z\u001a\u00020\u0019H\u00c6\u0003J\u000b\u0010{\u001a\u0004\u0018\u00010(H\u00c6\u0003J\u000b\u0010|\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010}\u001a\u00020+H\u00c6\u0003J\u0019\u0010~\u001a\u0012\u0012\u0004\u0012\u00020-0\u0015j\b\u0012\u0004\u0012\u00020-`\u0017H\u00c6\u0003J\u000b\u0010\u007f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0007H\u00c6\u0003J\f\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0082\u0001\u001a\u00020\nH\u00c6\u0003J\f\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0012\u0010\u0085\u0001\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eH\u00c6\u0003J\u0080\u0003\u0010\u0086\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0018\b\u0002\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0015j\b\u0012\u0004\u0012\u00020\u0016`\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u00192\u001c\b\u0002\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u00172\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010&\u001a\u00020\u00192\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010*\u001a\u00020+2\u0018\b\u0002\u0010,\u001a\u0012\u0012\u0004\u0012\u00020-0\u0015j\b\u0012\u0004\u0012\u00020-`\u0017H\u00c6\u0001J\u0015\u0010\u0087\u0001\u001a\u00020\u00192\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u0089\u0001\u001a\u00020+H\u00d6\u0001J\n\u0010\u008a\u0001\u001a\u00020\u0003H\u00d6\u0001R\u001c\u0010)\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u0010*\u001a\u00020+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R.\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R*\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0015j\b\u0012\u0004\u0012\u00020\u0016`\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u00108\"\u0004\b<\u0010:R\u0019\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u001c\u0010%\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u00100\"\u0004\bD\u00102R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u00100R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bF\u00100\"\u0004\bG\u00102R\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u00100R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u00100R\u001a\u0010\u001a\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010N\"\u0004\bO\u0010PR\u001a\u0010\u001d\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010N\"\u0004\bQ\u0010PR\u001a\u0010&\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010N\"\u0004\bR\u0010PR\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010N\"\u0004\bS\u0010PR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u00100\"\u0004\bU\u00102R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u00100\"\u0004\bW\u00102R!\u0010,\u001a\u0012\u0012\u0004\u0012\u00020-0\u0015j\b\u0012\u0004\u0012\u00020-`\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u00108R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b]\u00100\"\u0004\b^\u00102R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b_\u00100R\u0013\u0010\'\u001a\u0004\u0018\u00010(\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010aR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u00100\"\u0004\bc\u00102R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bf\u00100R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bg\u0010hR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bi\u00100\u00a8\u0006\u008b\u0001"}, d2 = {"Lcom/netomi/chat/model/NCWMessage;", "", "message", "", "imageUrl", "videoUrl", "type", "Lcom/netomi/chat/model/MessageType;", "id", "timestamp", "", "sender", "largeImageUrl", "carouselItems", "", "Lcom/netomi/chat/model/messages/NCWCarouselElement;", "quickReply", "Lcom/netomi/chat/model/messages/NCWQuickReply;", "thumbnailUrl", "title", "buttons", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/NCWCarouselButton;", "Lkotlin/collections/ArrayList;", "isSameTimeMessage", "", "isQuickReplyVisible", "fileUrl", "fileSize", "isRetry", "attachmentList", "Lcom/netomi/chat/model/messages/NCWAttachmentList;", "formSchema", "Lcom/netomi/chat/model/messages/FormSchema;", "requestID", "customPayload", "Lcom/netomi/chat/model/messages/NCWCustomPayload;", "feedbackValue", "isReviewEnabled", "surveyField", "Lcom/netomi/chat/model/messages/SurveyField;", "agentAvatar", "attachmentIndex", "", "multipleSourceDetails", "Lcom/netomi/chat/model/messages/MultipleSourceDetail;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/netomi/chat/model/MessageType;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/netomi/chat/model/messages/NCWQuickReply;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;ZZLjava/lang/String;Ljava/lang/String;ZLjava/util/ArrayList;Lcom/netomi/chat/model/messages/FormSchema;Ljava/lang/String;Lcom/netomi/chat/model/messages/NCWCustomPayload;Ljava/lang/String;ZLcom/netomi/chat/model/messages/SurveyField;Ljava/lang/String;ILjava/util/ArrayList;)V", "getAgentAvatar", "()Ljava/lang/String;", "setAgentAvatar", "(Ljava/lang/String;)V", "getAttachmentIndex", "()I", "setAttachmentIndex", "(I)V", "getAttachmentList", "()Ljava/util/ArrayList;", "setAttachmentList", "(Ljava/util/ArrayList;)V", "getButtons", "setButtons", "getCarouselItems", "()Ljava/util/List;", "getCustomPayload", "()Lcom/netomi/chat/model/messages/NCWCustomPayload;", "setCustomPayload", "(Lcom/netomi/chat/model/messages/NCWCustomPayload;)V", "getFeedbackValue", "setFeedbackValue", "getFileSize", "getFileUrl", "setFileUrl", "getFormSchema", "()Lcom/netomi/chat/model/messages/FormSchema;", "setFormSchema", "(Lcom/netomi/chat/model/messages/FormSchema;)V", "getId", "getImageUrl", "()Z", "setQuickReplyVisible", "(Z)V", "setRetry", "setReviewEnabled", "setSameTimeMessage", "getLargeImageUrl", "setLargeImageUrl", "getMessage", "setMessage", "getMultipleSourceDetails", "getQuickReply", "()Lcom/netomi/chat/model/messages/NCWQuickReply;", "setQuickReply", "(Lcom/netomi/chat/model/messages/NCWQuickReply;)V", "getRequestID", "setRequestID", "getSender", "getSurveyField", "()Lcom/netomi/chat/model/messages/SurveyField;", "getThumbnailUrl", "setThumbnailUrl", "getTimestamp", "()J", "getTitle", "getType", "()Lcom/netomi/chat/model/MessageType;", "getVideoUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "netomichatsdk_debug"})
public final class NCWMessage {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String message;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String imageUrl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String videoUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.model.MessageType type = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String id = null;
    private final long timestamp = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sender = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String largeImageUrl;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> carouselItems = null;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.model.messages.NCWQuickReply quickReply;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String thumbnailUrl;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> buttons;
    private boolean isSameTimeMessage;
    private boolean isQuickReplyVisible;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String fileUrl;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String fileSize = null;
    private boolean isRetry;
    @org.jetbrains.annotations.Nullable()
    private java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> attachmentList;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.model.messages.FormSchema formSchema;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String requestID;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.model.messages.NCWCustomPayload customPayload;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String feedbackValue;
    private boolean isReviewEnabled;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.SurveyField surveyField = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String agentAvatar;
    private int attachmentIndex;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> multipleSourceDetails = null;
    
    public NCWMessage(@org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String videoUrl, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.MessageType type, @org.jetbrains.annotations.Nullable()
    java.lang.String id, long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String sender, @org.jetbrains.annotations.Nullable()
    java.lang.String largeImageUrl, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> carouselItems, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReply quickReply, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> buttons, boolean isSameTimeMessage, boolean isQuickReplyVisible, @org.jetbrains.annotations.Nullable()
    java.lang.String fileUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String fileSize, boolean isRetry, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> attachmentList, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.FormSchema formSchema, @org.jetbrains.annotations.Nullable()
    java.lang.String requestID, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCustomPayload customPayload, @org.jetbrains.annotations.Nullable()
    java.lang.String feedbackValue, boolean isReviewEnabled, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SurveyField surveyField, @org.jetbrains.annotations.Nullable()
    java.lang.String agentAvatar, int attachmentIndex, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> multipleSourceDetails) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final void setMessage(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getImageUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getVideoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.MessageType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getId() {
        return null;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSender() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLargeImageUrl() {
        return null;
    }
    
    public final void setLargeImageUrl(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> getCarouselItems() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWQuickReply getQuickReply() {
        return null;
    }
    
    public final void setQuickReply(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReply p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getThumbnailUrl() {
        return null;
    }
    
    public final void setThumbnailUrl(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> getButtons() {
        return null;
    }
    
    public final void setButtons(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> p0) {
    }
    
    public final boolean isSameTimeMessage() {
        return false;
    }
    
    public final void setSameTimeMessage(boolean p0) {
    }
    
    public final boolean isQuickReplyVisible() {
        return false;
    }
    
    public final void setQuickReplyVisible(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFileUrl() {
        return null;
    }
    
    public final void setFileUrl(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFileSize() {
        return null;
    }
    
    public final boolean isRetry() {
        return false;
    }
    
    public final void setRetry(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> getAttachmentList() {
        return null;
    }
    
    public final void setAttachmentList(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.FormSchema getFormSchema() {
        return null;
    }
    
    public final void setFormSchema(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.FormSchema p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRequestID() {
        return null;
    }
    
    public final void setRequestID(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWCustomPayload getCustomPayload() {
        return null;
    }
    
    public final void setCustomPayload(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCustomPayload p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFeedbackValue() {
        return null;
    }
    
    public final void setFeedbackValue(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final boolean isReviewEnabled() {
        return false;
    }
    
    public final void setReviewEnabled(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SurveyField getSurveyField() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAgentAvatar() {
        return null;
    }
    
    public final void setAgentAvatar(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final int getAttachmentIndex() {
        return 0;
    }
    
    public final void setAttachmentIndex(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> getMultipleSourceDetails() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWQuickReply component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    public final boolean component15() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component17() {
        return null;
    }
    
    public final boolean component18() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.FormSchema component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.NCWCustomPayload component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component23() {
        return null;
    }
    
    public final boolean component24() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SurveyField component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component26() {
        return null;
    }
    
    public final int component27() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> component28() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.MessageType component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.NCWMessage copy(@org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String videoUrl, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.MessageType type, @org.jetbrains.annotations.Nullable()
    java.lang.String id, long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String sender, @org.jetbrains.annotations.Nullable()
    java.lang.String largeImageUrl, @org.jetbrains.annotations.Nullable()
    java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> carouselItems, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReply quickReply, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWCarouselButton> buttons, boolean isSameTimeMessage, boolean isQuickReplyVisible, @org.jetbrains.annotations.Nullable()
    java.lang.String fileUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String fileSize, boolean isRetry, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> attachmentList, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.FormSchema formSchema, @org.jetbrains.annotations.Nullable()
    java.lang.String requestID, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCustomPayload customPayload, @org.jetbrains.annotations.Nullable()
    java.lang.String feedbackValue, boolean isReviewEnabled, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SurveyField surveyField, @org.jetbrains.annotations.Nullable()
    java.lang.String agentAvatar, int attachmentIndex, @org.jetbrains.annotations.NotNull()
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