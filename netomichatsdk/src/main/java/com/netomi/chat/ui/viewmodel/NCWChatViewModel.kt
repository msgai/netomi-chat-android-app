package com.netomi.chat.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.AppConfigurationResponseModel
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
class NCWChatViewModel: ViewModel() {

    private val chatRepository = NCWChatRepository()

    private val _chatMessages = SingleLiveEvent<State<NCWBaseResponse<ArrayList<NCWMessage>>>>()
    val chatMessages get() = _chatMessages

    private val _sendMessages = SingleLiveEvent<State<NCWBaseResponse<Boolean>>>()
    val sendMessages get() = _sendMessages

    private var _appAppConfiguration = SingleLiveEvent<State<NCWBaseResponse<AppConfigurationResponseModel>>>()
    val appAppConfiguration get() = _appAppConfiguration

    init {
        loadChatHistory()
    }

    fun getAppConfig() {

        viewModelScope.launch(Dispatchers.IO) {
            val response=chatRepository.getAppConfiguration()
            withContext(Dispatchers.Main) {
                _appAppConfiguration.value = response // Use setValue on the Main thread
            }
        }
    }

    private fun loadChatHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val messages = listOf(
                NCWMessage("1", "User", "Hello!", System.currentTimeMillis()),
                NCWMessage("2", "Bot", "Hi there!", System.currentTimeMillis())
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
            sender = "User",
            content = content,
            timestamp = System.currentTimeMillis()
        )
        val response=chatRepository.sendMessage(newMessage)
        _sendMessages.value=response
    }
}
