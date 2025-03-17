package com.netomi.chat.model.theme;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003JB\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0002\u0010 J\u0013\u0010!\u001a\u00020\u00052\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\b\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006&"}, d2 = {"Lcom/netomi/chat/model/theme/NCWSound;", "", "bot", "Lcom/netomi/chat/model/theme/NCWBot;", "defaultSound", "", "status", "", "isSound", "user", "Lcom/netomi/chat/model/theme/NCWUser;", "(Lcom/netomi/chat/model/theme/NCWBot;ZLjava/lang/String;Ljava/lang/Boolean;Lcom/netomi/chat/model/theme/NCWUser;)V", "getBot", "()Lcom/netomi/chat/model/theme/NCWBot;", "getDefaultSound", "()Z", "setDefaultSound", "(Z)V", "()Ljava/lang/Boolean;", "setSound", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getStatus", "()Ljava/lang/String;", "getUser", "()Lcom/netomi/chat/model/theme/NCWUser;", "component1", "component2", "component3", "component4", "component5", "copy", "(Lcom/netomi/chat/model/theme/NCWBot;ZLjava/lang/String;Ljava/lang/Boolean;Lcom/netomi/chat/model/theme/NCWUser;)Lcom/netomi/chat/model/theme/NCWSound;", "equals", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class NCWSound {
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.model.theme.NCWBot bot = null;
    private boolean defaultSound;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean isSound;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.model.theme.NCWUser user = null;
    
    public NCWSound(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.theme.NCWBot bot, boolean defaultSound, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isSound, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.theme.NCWUser user) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWBot getBot() {
        return null;
    }
    
    public final boolean getDefaultSound() {
        return false;
    }
    
    public final void setDefaultSound(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean isSound() {
        return null;
    }
    
    public final void setSound(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWUser getUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWBot component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWUser component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWSound copy(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.theme.NCWBot bot, boolean defaultSound, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isSound, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.theme.NCWUser user) {
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