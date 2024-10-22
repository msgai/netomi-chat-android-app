package com.netomi.chat.ui.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.BaseResponse
import com.netomi.chat.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NCWChatViewModel: ViewModel() {

    private val chatRepository = NCWChatRepository()

    private val _chatMessages = SingleLiveEvent<State<BaseResponse<ArrayList<NCWMessage>>>>()
    val chatMessages get() = _chatMessages

    private val _sendMessages = SingleLiveEvent<State<BaseResponse<Boolean>>>()
    val sendMessages get() = _sendMessages

    private var _appAppConfiguration = SingleLiveEvent<State<BaseResponse<AppConfigurationResponseModel>>>()
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

    // Send a new message using viewModelScope
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
