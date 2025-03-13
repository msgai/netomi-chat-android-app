package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\nJ\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\nJ\u000e\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u000eJ\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\bH\u0002J\u0006\u0010!\u001a\u00020\u000eJ\u000e\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u000eJ\u000e\u0010$\u001a\u00020%2\u0006\u0010\u0014\u001a\u00020&J\'\u0010\'\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0010\u001a\u0004\u0018\u00010\n2\b\u0010(\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001bJ\u000e\u0010.\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u00020\u000eJ\u000e\u00101\u001a\u00020\b2\u0006\u00100\u001a\u00020\u000eJ\u000e\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\u000eJ\u000e\u00104\u001a\u0002052\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u00106\u001a\u00020%2\u0006\u00107\u001a\u0002082\u0006\u00100\u001a\u00020\u000eJ*\u00109\u001a\u00020%2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010:\u001a\b\u0012\u0004\u0012\u00020%0;2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020%0;J \u0010=\u001a\u00020%2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010>\u001a\u00020\u000e2\b\b\u0002\u0010?\u001a\u00020@J\u001c\u0010A\u001a\u00020\b2\u0006\u0010+\u001a\u00020,2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020D0CR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006E"}, d2 = {"Lcom/netomi/chat/utils/NCWAppUtils;", "", "()V", "emailPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "urlPattern", "areAWSCredentialsExpired", "", "expireTime", "", "createRequestBody", "Lokhttp3/RequestBody;", "value", "", "formatFileSize", "fileSizeInBytes", "formatTimestampToTime", "timestamp", "getAppVersion", "context", "Landroid/content/Context;", "getDeviceName", "getDomainOutOfURL", "url", "getFileContentType", "file", "Ljava/io/File;", "getFileFromUri", "uri", "Landroid/net/Uri;", "getIPAddress", "useIPv4", "getOSVersion", "getTypeFromContent", "type", "hideKeyboard", "", "Landroid/app/Activity;", "isFileSizeValid", "maxFileSizeInBytes", "(Landroid/content/Context;Ljava/lang/Long;Ljava/lang/Long;)Z", "isFormSizeValid", "formComponent", "Lcom/netomi/chat/model/messages/Component;", "fileSend", "isNetworkAvailable", "isValidEmail", "input", "isValidUrl", "parseIdleTimeFromExpression", "expression", "prepareFilePart", "Lokhttp3/MultipartBody$Part;", "setHtmlText", "textView", "Landroid/widget/TextView;", "showMediaOptionDialog", "imageClick", "Lkotlin/Function0;", "videoClick", "showToast", "message", "duration", "", "validateMultipleFormAttachment", "filesToSend", "", "Lcom/netomi/chat/model/media_payload/MultiFileModel;", "netomichatsdk_debug"})
public final class NCWAppUtils {
    private static final java.util.regex.Pattern emailPattern = null;
    private static final java.util.regex.Pattern urlPattern = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.NCWAppUtils INSTANCE = null;
    
    private NCWAppUtils() {
        super();
    }
    
    public final boolean isValidEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String input) {
        return false;
    }
    
    public final boolean isValidUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String input) {
        return false;
    }
    
    public final void hideKeyboard(@org.jetbrains.annotations.NotNull()
    android.app.Activity context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatTimestampToTime(long timestamp) {
        return null;
    }
    
    public final void setHtmlText(@org.jetbrains.annotations.NotNull()
    android.widget.TextView textView, @org.jetbrains.annotations.NotNull()
    java.lang.String input) {
    }
    
    /**
     * function to check network availability
     */
    public final boolean isNetworkAvailable(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void showToast(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String message, int duration) {
    }
    
    public final void showMediaOptionDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> imageClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> videoClick) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.MultipartBody.Part prepareFilePart(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFileContentType(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTypeFromContent(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatFileSize(long fileSizeInBytes) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.RequestBody createRequestBody(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File getFileFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    public final boolean isFileSizeValid(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Long fileSizeInBytes, @org.jetbrains.annotations.Nullable()
    java.lang.Long maxFileSizeInBytes) {
        return false;
    }
    
    public final boolean isFormSizeValid(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.Component formComponent, @org.jetbrains.annotations.NotNull()
    java.io.File fileSend) {
        return false;
    }
    
    public final boolean validateMultipleFormAttachment(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.Component formComponent, @org.jetbrains.annotations.NotNull()
    java.util.List<com.netomi.chat.model.media_payload.MultiFileModel> filesToSend) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDomainOutOfURL(@org.jetbrains.annotations.Nullable()
    java.lang.String url) {
        return null;
    }
    
    public final long parseIdleTimeFromExpression(@org.jetbrains.annotations.NotNull()
    java.lang.String expression) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIPAddress(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    private final java.lang.String getIPAddress(boolean useIPv4) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOSVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAppVersion(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final boolean areAWSCredentialsExpired(long expireTime) {
        return false;
    }
}