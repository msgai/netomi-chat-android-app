package com.netomi.chat.model.theme

data class EndChat(
    val downloadTranscript: Boolean,
    val endChatButtonCTA: String,
    val endChatCancelButtonCTA: String,
    val endChatGreetingDelay: String,
    val endChatGreeting_: String,
    val endChatQuery_: String,
    val idleTimeout: Int,
    val isSendTranscriptEnabled: Boolean,
    val sendTranscriptCTA: String
)