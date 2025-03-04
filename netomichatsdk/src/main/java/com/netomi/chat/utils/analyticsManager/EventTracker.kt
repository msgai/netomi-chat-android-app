package com.netomi.chat.utils.analyticsManager

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

/**
 * EventTracker is a utility class responsible for managing and tracking analytics events
 * using the Mixpanel API. It provides functions to initialize the Mixpanel instance,
 * track events with optional properties, and create standardized JSON objects for event data.
 */
class EventTracker {
    companion object {

        private var mixpanel: MixpanelAPI? = null

        /**
         * Tracks an event with optional properties.
         *
         * @param eventName The name of the event to track.
         * @param properties Optional JSON properties for the event. Defaults to null.
         */
        fun trackEvent(eventName: String, properties: JSONObject? = null) {
            mixpanel?.track(eventName, properties)
        }

        /**
         * Tracks an event without additional properties.
         *
         * @param eventName The name of the event to track.
         */
        fun trackEvent(eventName: String) {
            mixpanel?.track(eventName)
        }

        /**
         * Creates a standardized JSON object containing event properties.
         *
         * @param conversationID The unique identifier for the conversation.
         * @param botRefId The reference ID for the bot involved in the conversation.
         * @param pairs Additional key-value pairs to be included in the JSON object.
         * @return A JSONObject containing the provided properties.
         */
        fun createJsonObject(
            conversationID: String,
            botRefId: String,
            selectedLanguage : String,
            vararg pairs: Pair<String, Any?>
        ): JSONObject {
            return JSONObject().apply {
                put(AnalyticsEvents.CONVERSATION_ID, conversationID)
                put(AnalyticsEvents.BOT_REF_ID, botRefId)
                put(AnalyticsEvents.SELECTED_LANGUAGE, selectedLanguage)
                pairs.forEach { (key, value) ->
                    put(key, value)
                }
            }
        }

        /**
         * Initializes the Mixpanel instance for event tracking.
         *
         * @param context The context used to initialize Mixpanel.
         * @param isTrackEvent A boolean flag indicating whether to initialize tracking.
         */
        fun initMix(context: Context, isTrackEvent: Boolean) {
            if (isTrackEvent)
                mixpanel =
                    MixpanelAPI.getInstance(context, "2dddebeaf174bbf3d98301d2b428d63d", true)
        }
    }
}
