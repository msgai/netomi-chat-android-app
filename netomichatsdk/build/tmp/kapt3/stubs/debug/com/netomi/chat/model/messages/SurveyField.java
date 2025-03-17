package com.netomi.chat.model.messages;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u000bH\u00c6\u0003JO\u0010 \u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010!\u001a\u00020\u000b2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000e\u00a8\u0006&"}, d2 = {"Lcom/netomi/chat/model/messages/SurveyField;", "", "surveyId", "", "eventType", "payload", "Lcom/netomi/chat/model/messages/Payload;", "triggerType", "submitSurveyInfo", "Lcom/netomi/chat/survey/SubmitSurveyInfo;", "isSurveySkipped", "", "(Ljava/lang/String;Ljava/lang/String;Lcom/netomi/chat/model/messages/Payload;Ljava/lang/String;Lcom/netomi/chat/survey/SubmitSurveyInfo;Z)V", "getEventType", "()Ljava/lang/String;", "()Z", "setSurveySkipped", "(Z)V", "getPayload", "()Lcom/netomi/chat/model/messages/Payload;", "getSubmitSurveyInfo", "()Lcom/netomi/chat/survey/SubmitSurveyInfo;", "setSubmitSurveyInfo", "(Lcom/netomi/chat/survey/SubmitSurveyInfo;)V", "getSurveyId", "getTriggerType", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class SurveyField {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String surveyId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String eventType = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.Payload payload = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String triggerType = null;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.survey.SubmitSurveyInfo submitSurveyInfo;
    private boolean isSurveySkipped;
    
    public SurveyField(@org.jetbrains.annotations.Nullable()
    java.lang.String surveyId, @org.jetbrains.annotations.Nullable()
    java.lang.String eventType, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.Payload payload, @org.jetbrains.annotations.Nullable()
    java.lang.String triggerType, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.SubmitSurveyInfo submitSurveyInfo, boolean isSurveySkipped) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSurveyId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEventType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.Payload getPayload() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTriggerType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.survey.SubmitSurveyInfo getSubmitSurveyInfo() {
        return null;
    }
    
    public final void setSubmitSurveyInfo(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.SubmitSurveyInfo p0) {
    }
    
    public final boolean isSurveySkipped() {
        return false;
    }
    
    public final void setSurveySkipped(boolean p0) {
    }
    
    public SurveyField() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.Payload component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.survey.SubmitSurveyInfo component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.SurveyField copy(@org.jetbrains.annotations.Nullable()
    java.lang.String surveyId, @org.jetbrains.annotations.Nullable()
    java.lang.String eventType, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.Payload payload, @org.jetbrains.annotations.Nullable()
    java.lang.String triggerType, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.survey.SubmitSurveyInfo submitSurveyInfo, boolean isSurveySkipped) {
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