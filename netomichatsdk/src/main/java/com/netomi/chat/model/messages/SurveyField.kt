package com.netomi.chat.model.messages



data class SurveyField(
    val surveyId: String?=null,
    val eventType: String?=null,
    val payload: Payload?=null,
    val triggerType: String?=null
)

data class Payload(
    val feedbackMessage: FeedbackMessage?=null,
    val additionalFeedback: AdditionalFeedback?=null,
    val resolutionQuestion: ResolutionQuestion?=null,
    val reasonOfRating: ReasonOfRating?=null,
    val isSkipEnabled: Boolean=true,
    val positiveSuggestionMap: SuggestionMap?=null,
    val negativeSuggestionMap: SuggestionMap?=null,
    val ratingTypeEnabled: String?=null,
    val surveyRatingTypeEnabledInfo: SurveyRatingTypeEnabledInfo?=null
)

data class FeedbackMessage(
    val enabled: Boolean=true,
    val text: String?=null
)

data class AdditionalFeedback(
    val enabled: Boolean?=null,
    val text: String?=null
)

data class ResolutionQuestion(
    val enabled: Boolean=true,
    val text: String?=null
)

data class ReasonOfRating(
    val enabled: Boolean=true
)

data class SuggestionMap(
    val title: String?=null,
    val options: List<String>?=null
)

data class SurveyRatingTypeEnabledInfo(
    val lowerBound: Int?=null,
    val upperBound: Int?=null,
    val criteria: Int?=null
)
