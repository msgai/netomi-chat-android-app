package com.netomi.chat.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\nH\u0002J\u001a\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\nH\u0002J\b\u0010\u0013\u001a\u00020\u000eH\u0002J\u0012\u0010\u0014\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\nH\u0002J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\nH\u0002J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\nH\u0002J\b\u0010\u001d\u001a\u00020\u000eH\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\nH\u0002J\u0014\u0010 \u001a\u00020\u000e*\u00020\b2\u0006\u0010!\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/netomi/chat/ui/view/NCWFullScreenMediaActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "ivBack", "Landroid/widget/ImageView;", "progressBar", "Landroid/widget/ProgressBar;", "webView", "Landroid/webkit/WebView;", "getRealPathFromURI", "", "uri", "Landroid/net/Uri;", "handleMedia", "", "mediaUrl", "mediaType", "handleMediaFileUri", "fileUri", "initializeViews", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showFileInWebView", "fileUrl", "showImage", "imageUrl", "showImageURI", "imageUri", "showUnsupportedMediaType", "showVideo", "videoUrl", "loadHtmlContent", "content", "netomichatsdk_debug"})
public final class NCWFullScreenMediaActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.ImageView ivBack;
    private android.webkit.WebView webView;
    private android.widget.ProgressBar progressBar;
    
    public NCWFullScreenMediaActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeViews() {
    }
    
    private final void handleMedia(java.lang.String mediaUrl, java.lang.String mediaType) {
    }
    
    private final void handleMediaFileUri(java.lang.String fileUri, java.lang.String mediaType) {
    }
    
    private final void showImageURI(java.lang.String imageUri) {
    }
    
    private final java.lang.String getRealPathFromURI(android.net.Uri uri) {
        return null;
    }
    
    private final void showImage(java.lang.String imageUrl) {
    }
    
    private final void showVideo(java.lang.String videoUrl) {
    }
    
    private final void showFileInWebView(java.lang.String fileUrl) {
    }
    
    private final void showUnsupportedMediaType() {
    }
    
    private final void loadHtmlContent(android.webkit.WebView $this$loadHtmlContent, java.lang.String content) {
    }
}