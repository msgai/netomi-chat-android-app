package com.netomi.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.NCWChatRepository
import com.netomi.chat.model.NCWMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NCWChatViewModel @Inject constructor(
    private val ncwChatRepository: NCWChatRepository
) : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<NCWMessage>>(emptyList())
    val chatMessages: StateFlow<List<NCWMessage>> get() = _chatMessages

    init {
        loadChatHistory()
        observeMessages()
    }

    // Load existing chat history
    private fun loadChatHistory() {
        _chatMessages.value = ncwChatRepository.fetchChatHistory()
    }

    // Observe new messages from the repository
    private fun observeMessages() {
        viewModelScope.launch {
            ncwChatRepository.messages.collect { messages ->
                _chatMessages.value = messages
            }
        }
    }

    // Send a new message
    fun sendMessage(content: String) {
        val message = NCWMessage(
            id = System.currentTimeMillis().toString(),
            sender = "User",
            content = content,
            timestamp = System.currentTimeMillis()
        )
        ncwChatRepository.sendMessage(message)
    }
}