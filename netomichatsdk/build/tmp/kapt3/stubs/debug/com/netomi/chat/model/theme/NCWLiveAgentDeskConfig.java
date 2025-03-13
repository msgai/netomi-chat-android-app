package com.netomi.chat.model.theme;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003JO\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\b2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e\u00a8\u0006$"}, d2 = {"Lcom/netomi/chat/model/theme/NCWLiveAgentDeskConfig;", "", "agentDeskAuthKey", "", "agentDeskChannelName", "customHandoffConfig", "Lcom/netomi/chat/model/theme/NCWCustomHandoffConfig;", "enableZendeskConsoleLog", "", "enableZendeskErrorLog", "enableZendeskSentryLog", "handoffDepartment", "(Ljava/lang/String;Ljava/lang/String;Lcom/netomi/chat/model/theme/NCWCustomHandoffConfig;ZZZLjava/lang/String;)V", "getAgentDeskAuthKey", "()Ljava/lang/String;", "getAgentDeskChannelName", "getCustomHandoffConfig", "()Lcom/netomi/chat/model/theme/NCWCustomHandoffConfig;", "getEnableZendeskConsoleLog", "()Z", "getEnableZendeskErrorLog", "getEnableZendeskSentryLog", "getHandoffDepartment", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class NCWLiveAgentDeskConfig {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String agentDeskAuthKey = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String agentDeskChannelName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.model.theme.NCWCustomHandoffConfig customHandoffConfig = null;
    private final boolean enableZendeskConsoleLog = false;
    private final boolean enableZendeskErrorLog = false;
    private final boolean enableZendeskSentryLog = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String handoffDepartment = null;
    
    public NCWLiveAgentDeskConfig(@org.jetbrains.annotations.NotNull()
    java.lang.String agentDeskAuthKey, @org.jetbrains.annotations.NotNull()
    java.lang.String agentDeskChannelName, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.theme.NCWCustomHandoffConfig customHandoffConfig, boolean enableZendeskConsoleLog, boolean enableZendeskErrorLog, boolean enableZendeskSentryLog, @org.jetbrains.annotations.NotNull()
    java.lang.String handoffDepartment) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAgentDeskAuthKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAgentDeskChannelName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWCustomHandoffConfig getCustomHandoffConfig() {
        return null;
    }
    
    public final boolean getEnableZendeskConsoleLog() {
        return false;
    }
    
    public final boolean getEnableZendeskErrorLog() {
        return false;
    }
    
    public final boolean getEnableZendeskSentryLog() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHandoffDepartment() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWCustomHandoffConfig component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.theme.NCWLiveAgentDeskConfig copy(@org.jetbrains.annotations.NotNull()
    java.lang.String agentDeskAuthKey, @org.jetbrains.annotations.NotNull()
    java.lang.String agentDeskChannelName, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.theme.NCWCustomHandoffConfig customHandoffConfig, boolean enableZendeskConsoleLog, boolean enableZendeskErrorLog, boolean enableZendeskSentryLog, @org.jetbrains.annotations.NotNull()
    java.lang.String handoffDepartment) {
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