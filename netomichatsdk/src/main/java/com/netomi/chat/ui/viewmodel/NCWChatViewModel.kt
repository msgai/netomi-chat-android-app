package com.netomi.chat.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.GetChatHistoryResponse
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.chat_history.GetChatHistoryPayload
import com.netomi.chat.model.media_payload.SignedUrlPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.messages.WebhookPayload
import com.netomi.chat.model.presigned_url.GetPreSignedUrl
import com.netomi.chat.utils.NCWAppConstant
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
class NCWChatViewModel(application: Application) : AndroidViewModel(application) {
    private val chatRepository = NCWChatRepository(application.applicationContext)

    private val _chatMessages = SingleLiveEvent<State<NCWBaseResponse<ArrayList<NCWMessage>>>>()
    val chatMessages get() = _chatMessages

    private val _sendMessages = SingleLiveEvent<NCWMessage>()
    val sendMessages get() = _sendMessages


    private val _sendMessage = SingleLiveEvent<State<SendMessageResponse>>()
    val sendMessage get() = _sendMessage


   /* private var _getConversationId =
        SingleLiveEvent<State<GetConversationIdResponse>>()
    val getConversationId get() = _getConversationId*/
   private val _getConversationId = SingleLiveEvent<State<GetConversationIdResponse>>()
    val getConversationId get() = _getConversationId


    private var _getAWSMQTTCredentials= SingleLiveEvent<State<MQTTCredentialsResponse>>()
    val getAWSMQTTCredentials get()= _getAWSMQTTCredentials




    private var _awsMessage = SingleLiveEvent<String>()
    val awsMessage get() = _awsMessage

    private var _getChatHistory= SingleLiveEvent<State<GetChatHistoryResponse>>()
    val getChatHistory get()= _getChatHistory

    private var _getSignedUrl= SingleLiveEvent<State<GetPreSignedUrl>>()
    val getSignedUrl get()= _getSignedUrl
    init {
        loadChatHistory()
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
            sender = NCWAppConstant.TYPE_REQUEST,
        )
        // val response = chatRepository.sendMessage(newMessage)
        _sendMessages.value = newMessage
    }

    fun sendMessageAPI(message: WebhookPayload) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.sendMessage(message)

            withContext(Dispatchers.Main) {
                Log.e("sendMessageAPI", "response " + response)
                _sendMessage.value = response
            }
        }


    }

    fun getConversationId(botRef: String?) {
        Log.e("ConversationIdResponse", "botRef " + botRef)
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.getConversationId(botRef)

            withContext(Dispatchers.Main) {
                Log.e("ConversationIdResponse", "response " + response)
                _getConversationId.value = response // Use setValue on the Main thread
            }
        }
    }

    fun getAWSMQTTCredentials(botRef: String?) {
        if (!botRef.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = chatRepository.getAWSMQTTCredentials(botRef)

                withContext(Dispatchers.Main) {
                    _getAWSMQTTCredentials.value = response // Use setValue on the Main thread
                }
            }
        }
    }

    fun getChatHistory(payload: GetChatHistoryPayload?) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.getChatHistory(payload,_getChatHistory)
Log.e("DataaResposne","response"+response)
            withContext(Dispatchers.Main) {
                _getChatHistory.value = response // Use setValue on the Main thread
            }
        }

    }

    fun getPreSignedUrl(payload: SignedUrlPayload) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.getPreSignedUrl(payload)
            Log.e("DataaResposne","response"+response)
            withContext(Dispatchers.Main) {
                _getSignedUrl.value = response // Use setValue on the Main thread
            }
        }
    }

}
