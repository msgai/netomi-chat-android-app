package com.netomi.chat.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for managing chat messages in the NCWChat application
 *
 * This class is responsible for holding and managing UI-related data for the chat activity.
 * It ensures that the chat data survives configuration changes, such as screen rotations.
 * Additionally, it provides methods to load chat history and send new messages.
 *
 * ## Responsibilities:
 * Load and manage the list of chat messages.
 * Expose chat messages through LiveData to be observed by the UI.
 * Provide a method to send new messages and update the message list.
 * Use `viewModelScope` to manage coroutines, ensuring proper lifecycle handling.
 *
 * ## Usage:
 * This ViewModel should be used in conjunction with the `ChatActivity`.
 * The UI observes the `chatMessages` LiveData to update the chat log in real-time.
 *
 */
class NCWChatViewModel(application: Application) : AndroidViewModel(application)
{
        private val chatRepository = NCWChatRepository(application.applicationContext)

        private val _chatMessages = SingleLiveEvent<State<NCWBaseResponse<ArrayList<NCWMessage>>>>()
        val chatMessages get() = _chatMessages

        private val _sendMessages = SingleLiveEvent<NCWMessage>()
        val sendMessages get() = _sendMessages

        private var _appAppConfiguration =
            SingleLiveEvent<State<NCWBaseResponse<AppConfigurationResponseModel>>>()
        val appAppConfiguration get() = _appAppConfiguration

        init {
            loadChatHistory()
        }

        fun getAppConfig() {

            viewModelScope.launch(Dispatchers.IO) {
                val response = chatRepository.getAppConfiguration()
                withContext(Dispatchers.Main) {
                    _appAppConfiguration.value = response // Use setValue on the Main thread
                }
            }
        }

        private fun loadChatHistory() {
            viewModelScope.launch(Dispatchers.IO) {
                val messages = listOf(
                    NCWMessage("1", "User", "Hello!", timestamp = System.currentTimeMillis()),
                    NCWMessage("2", "Bot", "Hi there!", timestamp = System.currentTimeMillis())
                )
                //_chatMessages.value=messages
            }
        }



        /**
         * @param content The content of the message to be sent.
         */
        fun sendMessage(content: String) {
            val newMessage = NCWMessage(
                id = System.currentTimeMillis().toString(),
                message = content,
                timestamp = System.currentTimeMillis(),
                type = MessageType.TEXT,
                sender = "User",
            )
           // val response = chatRepository.sendMessage(newMessage)
            _sendMessages.value = newMessage
        }

}
