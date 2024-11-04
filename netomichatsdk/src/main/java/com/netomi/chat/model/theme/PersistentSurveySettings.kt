package com.netomi.chat.model.theme

data class PersistentSurveySettings(
    val description: String,
    val heading: String,
    val textAfterFeedbackRemoved: String,
    val textAfterFeedbackSent: String
)