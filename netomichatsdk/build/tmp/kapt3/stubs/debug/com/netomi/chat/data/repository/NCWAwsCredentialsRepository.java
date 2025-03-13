package com.netomi.chat.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/netomi/chat/data/repository/NCWAwsCredentialsRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "clearCredentials", "", "getAWSCredentials", "Lcom/netomi/chat/model/awsmqtt/NCWAwsCredentials;", "getExpireTime", "", "saveAWSCredentials", "credentials", "Companion", "netomichatsdk_debug"})
public final class NCWAwsCredentialsRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.netomi.chat.data.repository.NCWAwsCredentialsRepository INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.data.repository.NCWAwsCredentialsRepository.Companion Companion = null;
    
    private NCWAwsCredentialsRepository(android.content.Context context) {
        super();
    }
    
    public final void saveAWSCredentials(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.awsmqtt.NCWAwsCredentials credentials) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.awsmqtt.NCWAwsCredentials getAWSCredentials() {
        return null;
    }
    
    public final void clearCredentials() {
    }
    
    public final long getExpireTime() {
        return 0L;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/netomi/chat/data/repository/NCWAwsCredentialsRepository$Companion;", "", "()V", "INSTANCE", "Lcom/netomi/chat/data/repository/NCWAwsCredentialsRepository;", "getInstance", "context", "Landroid/content/Context;", "netomichatsdk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.netomi.chat.data.repository.NCWAwsCredentialsRepository getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}