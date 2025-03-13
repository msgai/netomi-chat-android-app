package com.netomi.chat.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u0014\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fJ\u0010\u0010\r\u001a\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006J\u0012\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0006\u0010\u0012\u001a\u00020\nJ\b\u0010\u0013\u001a\u00020\nH&R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/netomi/chat/ui/view/NCWBaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "arePermissionsGranted", "", "checkNetworkAndExecute", "", "action", "Lkotlin/Function0;", "makePhoneCall", "phoneNumber", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "requestPermissionsAndShowMediaOptions", "showMediaOptions", "netomichatsdk_debug"})
public abstract class NCWBaseActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    
    public NCWBaseActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final boolean arePermissionsGranted() {
        return false;
    }
    
    public final void checkNetworkAndExecute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> action) {
    }
    
    public final void makePhoneCall(@org.jetbrains.annotations.Nullable()
    java.lang.String phoneNumber) {
    }
    
    public final void requestPermissionsAndShowMediaOptions() {
    }
    
    public abstract void showMediaOptions();
}