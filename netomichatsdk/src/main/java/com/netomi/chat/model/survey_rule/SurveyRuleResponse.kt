package com.netomi.chat.model.survey_rule

data class SurveyRuleResponse(
    val message: String,
    val payload: List<SurveyRule>,
    val statusCode: Int
)