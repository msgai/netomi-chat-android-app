package com.netomi.chat.model.theme

data class NCWLiveAgentDeskConfig(
    val agentDeskAuthKey: String,
    val agentDeskChannelName: String,
    val customHandoffConfig: NCWCustomHandoffConfig,
    val enableZendeskConsoleLog: Boolean,
    val enableZendeskErrorLog: Boolean,
    val enableZendeskSentryLog: Boolean,
    val handoffDepartment: String
)