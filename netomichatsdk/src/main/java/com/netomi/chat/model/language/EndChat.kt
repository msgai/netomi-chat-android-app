package com.netomi.chat.model.language

data class EndChat(
    val _endChatGreeting: String,
    val endChatButtonCTA: String,
    val endChatQuery: String,
    val sendTranscriptCTA: String
)