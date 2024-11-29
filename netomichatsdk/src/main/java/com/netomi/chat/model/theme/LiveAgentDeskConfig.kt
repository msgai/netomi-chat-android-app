package com.netomi.chat.model.theme

data class LiveAgentDeskConfig(
    val agentDeskAuthKey: String,
    val agentDeskChannelName: String,
    val customHandoffConfig: CustomHandoffConfig,
    val enableZendeskConsoleLog: Boolean,
    val enableZendeskErrorLog: Boolean,
    val enableZendeskSentryLog: Boolean,
    val handoffDepartment: String
)