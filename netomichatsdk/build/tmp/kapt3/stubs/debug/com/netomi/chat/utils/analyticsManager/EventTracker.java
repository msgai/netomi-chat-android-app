package com.netomi.chat.utils.analyticsManager;

/**
 * EventTracker is a utility class responsible for managing and tracking analytics events
 * using the Mixpanel API. It provides functions to initialize the Mixpanel instance,
 * track events with optional properties, and create standardized JSON objects for event data.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/netomi/chat/utils/analyticsManager/EventTracker;", "", "()V", "Companion", "netomichatsdk_debug"})
public final class EventTracker {
    @org.jetbrains.annotations.Nullable()
    private static com.mixpanel.android.mpmetrics.MixpanelAPI mixpanel;
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.analyticsManager.EventTracker.Companion Companion = null;
    
    public EventTracker() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JS\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2.\u0010\u000b\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\r0\f\"\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\bJ\u001a\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\b2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0006R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/netomi/chat/utils/analyticsManager/EventTracker$Companion;", "", "()V", "mixpanel", "Lcom/mixpanel/android/mpmetrics/MixpanelAPI;", "createJsonObject", "Lorg/json/JSONObject;", "conversationID", "", "botRefId", "selectedLanguage", "pairs", "", "Lkotlin/Pair;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Lkotlin/Pair;)Lorg/json/JSONObject;", "initMix", "", "context", "Landroid/content/Context;", "isTrackEvent", "", "trackEvent", "eventName", "properties", "netomichatsdk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Tracks an event with optional properties.
         *
         * @param eventName The name of the event to track.
         * @param properties Optional JSON properties for the event. Defaults to null.
         */
        public final void trackEvent(@org.jetbrains.annotations.NotNull()
        java.lang.String eventName, @org.jetbrains.annotations.Nullable()
        org.json.JSONObject properties) {
        }
        
        /**
         * Tracks an event without additional properties.
         *
         * @param eventName The name of the event to track.
         */
        public final void trackEvent(@org.jetbrains.annotations.NotNull()
        java.lang.String eventName) {
        }
        
        /**
         * Creates a standardized JSON object containing event properties.
         *
         * @param conversationID The unique identifier for the conversation.
         * @param botRefId The reference ID for the bot involved in the conversation.
         * @param pairs Additional key-value pairs to be included in the JSON object.
         * @return A JSONObject containing the provided properties.
         */
        @org.jetbrains.annotations.NotNull()
        public final org.json.JSONObject createJsonObject(@org.jetbrains.annotations.NotNull()
        java.lang.String conversationID, @org.jetbrains.annotations.NotNull()
        java.lang.String botRefId, @org.jetbrains.annotations.NotNull()
        java.lang.String selectedLanguage, @org.jetbrains.annotations.NotNull()
        kotlin.Pair<java.lang.String, ? extends java.lang.Object>... pairs) {
            return null;
        }
        
        /**
         * Initializes the Mixpanel instance for event tracking.
         *
         * @param context The context used to initialize Mixpanel.
         * @param isTrackEvent A boolean flag indicating whether to initialize tracking.
         */
        public final void initMix(@org.jetbrains.annotations.NotNull()
        android.content.Context context, boolean isTrackEvent) {
        }
    }
}