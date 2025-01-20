package com.netomi.chat.model.survey_rule

data class SurveyRule(
    val actionId: Int,
    val botId: String,
    val channel: String,
    val channelId: String,
    val conversationTriggerType: String,
    val env: String,
    val expectedAttribute: String,
    val expression: String,
    val id: String,
    val priority: Int,
    val responseName: String,
    val responseTemplateId: String,
    val ruleId: String,
    val ruleName: String,
    val ruleResponseType: String
)