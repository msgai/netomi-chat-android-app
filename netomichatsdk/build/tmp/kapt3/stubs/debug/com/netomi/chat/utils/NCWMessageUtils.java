package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bJl\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00062\u0006\u0010\u0013\u001a\u00020\u001428\u0010\u0015\u001a4\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0015\u0012\u0013\u0018\u00010\b\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u001b0\u0016Jo\u0010\f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0013\u001a\u00020\u001428\u0010\u0015\u001a4\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0015\u0012\u0013\u0018\u00010\b\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u001b0\u0016\u00a2\u0006\u0002\u0010\u001eJG\u0010\u001f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00062\u0006\u0010\u0013\u001a\u00020\u00142!\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001b0\"\u00a8\u0006#"}, d2 = {"Lcom/netomi/chat/utils/NCWMessageUtils;", "", "()V", "mergeChunks", "Lcom/netomi/chat/model/NCWMessage;", "chunkList", "", "splitIntoChunks", "", "text", "wordsPerChunk", "", "validateFileAttachment", "", "formComponent", "Lcom/netomi/chat/model/messages/Component;", "file", "Ljava/io/File;", "supportedExtensions", "context", "Landroid/content/Context;", "onValidationFailed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "message", "description", "", "maxFileSize", "", "(Ljava/io/File;Ljava/util/List;Ljava/lang/Long;Landroid/content/Context;Lkotlin/jvm/functions/Function2;)Z", "validateFormAttachment", "mMultipleFile", "Lcom/netomi/chat/model/media_payload/MultiFileModel;", "Lkotlin/Function1;", "netomichatsdk_debug"})
public final class NCWMessageUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.NCWMessageUtils INSTANCE = null;
    
    private NCWMessageUtils() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> splitIntoChunks(@org.jetbrains.annotations.NotNull()
    java.lang.String text, int wordsPerChunk) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.NCWMessage mergeChunks(@org.jetbrains.annotations.NotNull()
    java.util.List<com.netomi.chat.model.NCWMessage> chunkList) {
        return null;
    }
    
    public final boolean validateFileAttachment(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.Component formComponent, @org.jetbrains.annotations.Nullable()
    java.io.File file, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> supportedExtensions, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onValidationFailed) {
        return false;
    }
    
    public final boolean validateFileAttachment(@org.jetbrains.annotations.Nullable()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> supportedExtensions, @org.jetbrains.annotations.Nullable()
    java.lang.Long maxFileSize, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onValidationFailed) {
        return false;
    }
    
    public final boolean validateFormAttachment(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.Component formComponent, @org.jetbrains.annotations.NotNull()
    java.util.List<com.netomi.chat.model.media_payload.MultiFileModel> mMultipleFile, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValidationFailed) {
        return false;
    }
}