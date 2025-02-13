package com.netomi.chat.model.language

import com.netomi.chat.model.theme.NCWInitialFlows
import com.netomi.chat.model.theme.NCWQuickMenuOption
import com.netomi.chat.model.theme.NCWRestartChat

data class LanguageResponse(
    val agentDeskForm: AgentDeskForm,
    val calloutBubble: List<CalloutBubble>,
    val endChat: EndChat,
    val inactiveOverlay: InactiveOverlay,
    val initialFlows: NCWInitialFlows,
    val otherlocalized: NCWOtherLocalized,
    val quickMenuOptions: List<NCWQuickMenuOption>,
    val restartChat: NCWRestartChat,
    val subtitle: String,
    val supportText: String,
    val title: String
)