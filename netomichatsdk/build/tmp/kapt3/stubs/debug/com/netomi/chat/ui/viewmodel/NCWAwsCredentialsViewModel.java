package com.netomi.chat.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0010\u001a\u00020\u0011J\r\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014J\u0006\u0010\u0015\u001a\u00020\u0011J\u0016\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\tJ\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u0007R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00068F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/netomi/chat/ui/viewmodel/NCWAwsCredentialsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_credentials", "Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent;", "Lcom/netomi/chat/model/awsmqtt/NCWAwsCredentials;", "connectionStatus", "", "getConnectionStatus", "()Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent;", "credentials", "getCredentials", "repository", "Lcom/netomi/chat/data/repository/NCWAwsCredentialsRepository;", "clearCredentials", "", "getAWSCredentialsExpiry", "", "()Ljava/lang/Long;", "getAwsCredentials", "initializeAwsIotManager", "chatViewModel", "Lcom/netomi/chat/ui/viewmodel/NCWChatViewModel;", "topic", "saveAwsCredentials", "netomichatsdk_debug"})
public final class NCWAwsCredentialsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.data.repository.NCWAwsCredentialsRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.model.awsmqtt.NCWAwsCredentials> _credentials = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<java.lang.String> connectionStatus = null;
    
    public NCWAwsCredentialsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<com.netomi.chat.model.awsmqtt.NCWAwsCredentials> getCredentials() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<java.lang.String> getConnectionStatus() {
        return null;
    }
    
    /**
     * Initialize the AWS IOT
     */
    public final void initializeAwsIotManager(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.ui.viewmodel.NCWChatViewModel chatViewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String topic) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getAWSCredentialsExpiry() {
        return null;
    }
    
    /**
     * Load AWS Credentials from repository
     */
    public final void getAwsCredentials() {
    }
    
    /**
     * Save credentials through repository
     */
    public final void saveAwsCredentials(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.awsmqtt.NCWAwsCredentials credentials) {
    }
    
    /**
     * Clear credentials from repository
     */
    public final void clearCredentials() {
    }
}