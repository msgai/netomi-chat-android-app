package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J;\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\rH\u0002\u00a2\u0006\u0002\u0010\u000eJ;\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\rH\u0002\u00a2\u0006\u0002\u0010\u000eJ\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a8\u0006\u001b"}, d2 = {"Lcom/netomi/chat/utils/NCWFilePath;", "", "()V", "copyFileToInternalStorage", "", "uri", "Landroid/net/Uri;", "newDirName", "context", "Landroid/content/Context;", "getDataColumn", "selection", "selectionArgs", "", "(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getDataVideoColumn", "getDriveFilePath", "getFilePathForWhatsApp", "getPath", "getVideoPath", "isDownloadsDocument", "", "isExternalStorageDocument", "isGoogleDriveUri", "isGooglePhotosUri", "isMediaDocument", "isWhatsAppFile", "netomichatsdk_debug"})
public final class NCWFilePath {
    
    public NCWFilePath() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPath(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getVideoPath(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private final java.lang.String getDataColumn(android.content.Context context, android.net.Uri uri, java.lang.String selection, java.lang.String[] selectionArgs) {
        return null;
    }
    
    private final java.lang.String getDataVideoColumn(android.content.Context context, android.net.Uri uri, java.lang.String selection, java.lang.String[] selectionArgs) {
        return null;
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private final boolean isExternalStorageDocument(android.net.Uri uri) {
        return false;
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private final boolean isDownloadsDocument(android.net.Uri uri) {
        return false;
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private final boolean isMediaDocument(android.net.Uri uri) {
        return false;
    }
    
    private final boolean isGooglePhotosUri(android.net.Uri uri) {
        return false;
    }
    
    private final java.lang.String getDriveFilePath(android.net.Uri uri, android.content.Context context) {
        return null;
    }
    
    private final boolean isWhatsAppFile(android.net.Uri uri) {
        return false;
    }
    
    private final boolean isGoogleDriveUri(android.net.Uri uri) {
        return false;
    }
    
    private final java.lang.String getFilePathForWhatsApp(android.net.Uri uri, android.content.Context context) {
        return null;
    }
    
    private final java.lang.String copyFileToInternalStorage(android.net.Uri uri, java.lang.String newDirName, android.content.Context context) {
        return null;
    }
}