package com.netomi.chat.survey

data class SubmitSurveyRequest(
    val botRefId: String,
    val requestBody: RequestBody
)

data class RequestBody(
    val triggerType: String = "EVENT",
    val eventData: EventData,
    val conversationId: String,
    val botReferenceId: String,
    val requestType: String = "NETOMI",
    val channelId: String = "NETOMI_WEB_WIDGET",
    val eventName: String = "FEEDBACK",
    val timestamp: Long,
    val isPublishToMQTT: Boolean = false
)

data class EventData(
    val eventType: String = "SURVEY",
    val subType: String = "SUBMIT",
    val eventInfo: EventInfo
)

data class EventInfo(
    val surveyId: String,
    val feedbackValue: String,
    val requestId: String,
    val submitSurveyInfo: SubmitSurveyInfo
)

data class SubmitSurveyInfo(
    val rating: Int,
    val suggestions: List<String>?,
    val suggestionTitle: String?,
    val issueResolved: Boolean?,
    val additionalFeedback: String?,
    val triggerType: String?

)

