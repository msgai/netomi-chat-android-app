package com.netomi.chat.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEndChatResponse
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.NCWState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

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

    private val _chatMessages = NCWSingleLiveEvent<NCWState<NCWBaseResponse<ArrayList<NCWMessage>>>>()
    val chatMessages get() = _chatMessages

    private val _sendMessages = NCWSingleLiveEvent<NCWMessage>()
    val sendMessages get() = _sendMessages


    private val _sendMessage = NCWSingleLiveEvent<NCWState<NCWSendMessageResponse>>()
    val sendMessage get() = _sendMessage

    private val _NCW_endChatResponse=NCWSingleLiveEvent<NCWState<NCWEndChatResponse>>()
    val endChatResponse get()=_NCW_endChatResponse

    private val _feedbackResponse=NCWSingleLiveEvent<NCWState<NCWFeedbackResponse>>()
    val feedbackResponse get()=_feedbackResponse


   /* private var _getConversationId =
        SingleLiveEvent<State<GetConversationIdResponse>>()
    val getConversationId get() = _getConversationId*/
   private val _getConversationId = NCWSingleLiveEvent<NCWState<NCWGetConversationIdResponse>>()
    val getConversationId get() = _getConversationId


    private var _getAWSMQTTCredentials= NCWSingleLiveEvent<NCWState<MQTTCredentialsResponse>>()
    val getAWSMQTTCredentials get()= _getAWSMQTTCredentials

    private var _awsMessage = NCWSingleLiveEvent<String>()
    val awsMessage get() = _awsMessage

    private var _getChatHistory= NCWSingleLiveEvent<NCWState<NCWGetChatHistoryResponse>>()
    val getChatHistory get()= _getChatHistory

    private var _getSignedUrl= NCWSingleLiveEvent<NCWState<NCWGetPreSignedUrl>>()
    val getSignedUrl get()= _getSignedUrl

    private var _getUploadedMediaUrl= NCWSingleLiveEvent<NCWState<NCWGetMediaUploadUrl>>()
    val getUploadedMediaUrl get()= _getUploadedMediaUrl
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
    fun sendMessage(content: String, timestamp: Long) {
        val newMessage = NCWMessage(
            id = System.currentTimeMillis().toString(),
            message = content,
            timestamp =timestamp,
            type = MessageType.TEXT,
            sender = NCWAppConstant.TYPE_REQUEST,
        )
        // val response = chatRepository.sendMessage(newMessage)
        _sendMessages.value = newMessage
    }

    fun sendMessageAPI(message: NCWWebhookPayload) {

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

    fun getChatHistory(payload: NCWGetChatHistoryPayload?) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.getChatHistory(payload,_getChatHistory)
Log.e("DataaResposne","response"+response)
            withContext(Dispatchers.Main) {
                _getChatHistory.value = response // Use setValue on the Main thread
            }
        }

    }

    fun getPreSignedUrl(payload: NCWSignedUrlPayload) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.getPreSignedUrl(payload)
            Log.e("DataaResposne","response"+response)
            withContext(Dispatchers.Main) {
                _getSignedUrl.value = response // Use setValue on the Main thread
            }
        }
    }

    fun uploadFile(mediaUri: File?, response: NCWGetPreSignedUrl) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.uploadFile(mediaUri,response)
            Log.e("uploadFile","response"+response)
            withContext(Dispatchers.Main) {
                _getUploadedMediaUrl.value = response // Use setValue on the Main thread
            }
        }
    }

    fun hitEndChatAPI(message: NCWEndChatRequest) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.hitEndChatAPI(message)

            withContext(Dispatchers.Main) {
                Log.e("sendMessageAPI", "response " + response)
                _NCW_endChatResponse.value = response
            }
        }

    }

    fun hitFeedbackAPI(message: NCWFeedbackRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.hitFeedbackAPI(message)

            withContext(Dispatchers.Main) {
                Log.e("Feedback Response", "response " + response)
                _feedbackResponse.value = response
            }
        }

    }

}
