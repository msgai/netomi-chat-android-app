package com.netomi.chat.data
import com.netomi.chat.model.NCWMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class NCWChatRepository {
    // StateFlow to emit chat messages
    private val _messages = MutableStateFlow<List<NCWMessage>>(emptyList())
    val messages: StateFlow<List<NCWMessage>> get() = _messages

    // Function to send a message (Mock WebSocket send operation)
    fun sendMessage(message: NCWMessage) {
        val currentMessages = _messages.value.toMutableList()
        currentMessages.add(message)
        _messages.value = currentMessages
    }

    // Function to fetch chat history (Mock API or local data)
    fun fetchChatHistory(): List<NCWMessage> {
        return listOf(
            NCWMessage("1", "User", "Hello!", System.currentTimeMillis()),
            NCWMessage("2", "Bot", "Hi there!", System.currentTimeMillis())
        )
    }
}