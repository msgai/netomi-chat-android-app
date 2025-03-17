package com.netomi.chat.model.messages;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bo\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0013J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010(\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0012H\u00c6\u0003Js\u0010-\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00c6\u0001J\u0013\u0010.\u001a\u00020\u000b2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u000201H\u00d6\u0001J\t\u00102\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0018R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#\u00a8\u00063"}, d2 = {"Lcom/netomi/chat/model/messages/Payload;", "", "feedbackMessage", "Lcom/netomi/chat/model/messages/FeedbackMessage;", "additionalFeedback", "Lcom/netomi/chat/model/messages/AdditionalFeedback;", "resolutionQuestion", "Lcom/netomi/chat/model/messages/ResolutionQuestion;", "reasonOfRating", "Lcom/netomi/chat/model/messages/ReasonOfRating;", "isSkipEnabled", "", "positiveSuggestionMap", "Lcom/netomi/chat/model/messages/SuggestionMap;", "negativeSuggestionMap", "ratingTypeEnabled", "", "surveyRatingTypeEnabledInfo", "Lcom/netomi/chat/model/messages/SurveyRatingTypeEnabledInfo;", "(Lcom/netomi/chat/model/messages/FeedbackMessage;Lcom/netomi/chat/model/messages/AdditionalFeedback;Lcom/netomi/chat/model/messages/ResolutionQuestion;Lcom/netomi/chat/model/messages/ReasonOfRating;ZLcom/netomi/chat/model/messages/SuggestionMap;Lcom/netomi/chat/model/messages/SuggestionMap;Ljava/lang/String;Lcom/netomi/chat/model/messages/SurveyRatingTypeEnabledInfo;)V", "getAdditionalFeedback", "()Lcom/netomi/chat/model/messages/AdditionalFeedback;", "getFeedbackMessage", "()Lcom/netomi/chat/model/messages/FeedbackMessage;", "()Z", "getNegativeSuggestionMap", "()Lcom/netomi/chat/model/messages/SuggestionMap;", "getPositiveSuggestionMap", "getRatingTypeEnabled", "()Ljava/lang/String;", "getReasonOfRating", "()Lcom/netomi/chat/model/messages/ReasonOfRating;", "getResolutionQuestion", "()Lcom/netomi/chat/model/messages/ResolutionQuestion;", "getSurveyRatingTypeEnabledInfo", "()Lcom/netomi/chat/model/messages/SurveyRatingTypeEnabledInfo;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class Payload {
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.FeedbackMessage feedbackMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.AdditionalFeedback additionalFeedback = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.ResolutionQuestion resolutionQuestion = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.ReasonOfRating reasonOfRating = null;
    private final boolean isSkipEnabled = false;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.SuggestionMap positiveSuggestionMap = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.SuggestionMap negativeSuggestionMap = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ratingTypeEnabled = null;
    @org.jetbrains.annotations.Nullable()
    private final com.netomi.chat.model.messages.SurveyRatingTypeEnabledInfo surveyRatingTypeEnabledInfo = null;
    
    public Payload(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.FeedbackMessage feedbackMessage, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.AdditionalFeedback additionalFeedback, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.ResolutionQuestion resolutionQuestion, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.ReasonOfRating reasonOfRating, boolean isSkipEnabled, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SuggestionMap positiveSuggestionMap, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SuggestionMap negativeSuggestionMap, @org.jetbrains.annotations.Nullable()
    java.lang.String ratingTypeEnabled, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SurveyRatingTypeEnabledInfo surveyRatingTypeEnabledInfo) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.FeedbackMessage getFeedbackMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.AdditionalFeedback getAdditionalFeedback() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.ResolutionQuestion getResolutionQuestion() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.ReasonOfRating getReasonOfRating() {
        return null;
    }
    
    public final boolean isSkipEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SuggestionMap getPositiveSuggestionMap() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SuggestionMap getNegativeSuggestionMap() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRatingTypeEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SurveyRatingTypeEnabledInfo getSurveyRatingTypeEnabledInfo() {
        return null;
    }
    
    public Payload() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.FeedbackMessage component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.AdditionalFeedback component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.ResolutionQuestion component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.ReasonOfRating component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SuggestionMap component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SuggestionMap component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.messages.SurveyRatingTypeEnabledInfo component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.Payload copy(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.FeedbackMessage feedbackMessage, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.AdditionalFeedback additionalFeedback, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.ResolutionQuestion resolutionQuestion, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.ReasonOfRating reasonOfRating, boolean isSkipEnabled, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SuggestionMap positiveSuggestionMap, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SuggestionMap negativeSuggestionMap, @org.jetbrains.annotations.Nullable()
    java.lang.String ratingTypeEnabled, @org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.SurveyRatingTypeEnabledInfo surveyRatingTypeEnabledInfo) {
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