package com.netomi.chat.model.theme

data class NCWPersistentSurveyConfig(
    val additionalFeedback: NCWAdditionalFeedback,
    val feedbackMessage: NCWFeedbackMessage,
    val isPersistentSurveyEnabled: Boolean,
    val isSkipEnabled: Boolean,
    val negativeSuggestionMap: NCWNegativeSuggestionMap,
    val persistentSurveySettings: NCWPersistentSurveySettings,
    val positiveSuggestionMap: NCWPositiveSuggestionMap,
    val ratingTypeEnabled: String,
    val reasonOfRating: NCWReasonOfRating,
    val resolutionQuestion: NCWResolutionQuestion,
    val surveyRatingTypeEnabledInfo: NCWSurveyRatingTypeEnabledInfo
)