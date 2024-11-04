package com.netomi.chat.model.theme

data class PersistentSurveyConfig(
    val additionalFeedback: AdditionalFeedback,
    val feedbackMessage: FeedbackMessage,
    val isPersistentSurveyEnabled: Boolean,
    val isSkipEnabled: Boolean,
    val negativeSuggestionMap: NegativeSuggestionMap,
    val persistentSurveySettings: PersistentSurveySettings,
    val positiveSuggestionMap: PositiveSuggestionMap,
    val ratingTypeEnabled: String,
    val reasonOfRating: ReasonOfRating,
    val resolutionQuestion: ResolutionQuestion,
    val surveyRatingTypeEnabledInfo: SurveyRatingTypeEnabledInfo
)