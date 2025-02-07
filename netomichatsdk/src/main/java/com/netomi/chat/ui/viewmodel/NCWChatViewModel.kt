package com.netomi.chat.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.GetConversationPayload
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.auth.LoginResponse
import com.netomi.chat.model.auth.LogoutResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEndChatResponse
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse
import com.netomi.chat.model.media_payload.MultiFileModel
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.model.survey_rule.SurveyRuleResponse
import com.netomi.chat.survey.SubmitSurveyRequest
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.NCWState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _NCW_Survey_RESPONSE=NCWSingleLiveEvent<NCWState<NCWEndChatResponse>>()
    val surveyResponse get()=_NCW_Survey_RESPONSE


    private val _feedbackResponse=NCWSingleLiveEvent<NCWState<NCWFeedbackResponse>>()
    val feedbackResponse get()=_feedbackResponse

    private val _loginResponse=NCWSingleLiveEvent<NCWState<LoginResponse>>()
    val loginResponse get()= _loginResponse

    private val _logoutResponse=NCWSingleLiveEvent<NCWState<LogoutResponse>>()
    val logoutResponse get()= _logoutResponse

    private val _surveyRuleResponse=NCWSingleLiveEvent<NCWState<SurveyRuleResponse>>()
    val surveyRuleResponse get()= _surveyRuleResponse


   /* private var _getConversationId =
        SingleLiveEvent<State<GetConversationIdResponse>>()
    val getConversationId get() = _getConversationId*/
   private val _getConversationId = NCWSingleLiveEvent<NCWState<NCWGetConversationIdResponse>>()
    val getConversationId get() = _getConversationId


    private var _getAWSMQTTCredentials= NCWSingleLiveEvent<NCWState<MQTTCredentialsResponse>>()
    val getAWSMQTTCredentials get()= _getAWSMQTTCredentials

   /* private val _awsMessage = MutableLiveData<String>()
    val awsMessage: LiveData<String> get() = _awsMessage*/

    private val _awsMessage = MutableStateFlow<String?>(null)
    val awsMessage = _awsMessage.asStateFlow()

    private var _getChatHistory= NCWSingleLiveEvent<NCWState<NCWGetChatHistoryResponse>>()
    val getChatHistory get()= _getChatHistory

    private var _getSignedUrl= NCWSingleLiveEvent<NCWState<NCWGetPreSignedUrl>>()
    val getSignedUrl get()= _getSignedUrl

    private var _getUploadedMediaUrl= NCWSingleLiveEvent<NCWState<NCWGetMediaUploadUrl>>()
    val getUploadedMediaUrl get()= _getUploadedMediaUrl

    private val _errorFile = NCWSingleLiveEvent<NCWSignedUrlPayload?>()
    val errorFile get() = _errorFile
    init {
        loadChatHistory()
    }

    // Function to update message safely
    fun updateAwsMessage(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _awsMessage.value = message
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

    fun getConversationId(botRef: String?, externalId: String?, onRestart: Boolean?=false) {
        viewModelScope.launch(Dispatchers.IO) {
            val  payload=GetConversationPayload(botRefId=botRef,externalId=externalId)
            val response = chatRepository.getConversationId(payload,onRestart)

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

    fun uploadFilesSequentially(mMultipleFile: MutableList<MultiFileModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (mMultipleFile.isNotEmpty()) {
                processNextFile(mMultipleFile)
            } else {
                Log.d("FileProcessing", "No files to upload.")
            }
        }
    }

    private suspend fun processNextFile(fileList: MutableList<MultiFileModel>) {
        if (fileList.isNotEmpty()) {

            val currentFile = fileList.first()

            val mediaUpload = NCWSignedUrlPayload(
                fileType = currentFile.mimeType,
                uploadKeyPrefix = currentFile.fileName
            )

            try {
                val response = chatRepository.getPreSignedUrl(mediaUpload)

                if (response is NCWState.Success) {
                    val responseData = response.data as NCWGetPreSignedUrl

                    val uploadResponse = chatRepository.uploadFile(currentFile.file, responseData)

                    if (uploadResponse is NCWState.SendMessageError<*, *>){

                        fileList.removeAt(0)
                        val payload = uploadResponse.payload
                        if (payload != null && payload is NCWSignedUrlPayload) {
                            withContext(Dispatchers.Main) {
                                _errorFile.value = payload

                            }
                            processNextFile(fileList)
                        }
                    }

                    else if (uploadResponse != null) {
                        fileList.removeAt(0)
                        Log.e("Counettee","sasaasas "+uploadResponse)
                        withContext(Dispatchers.Main) {
                           _getUploadedMediaUrl.value = uploadResponse

                        }
                        processNextFile(fileList)
                    } else {
                        Log.e("FileProcessing", "File upload failed for: ${currentFile.fileName}")
                    }
                }

                else if (response is NCWState.SendMessageError<*, *>){
                        fileList.removeAt(0)
                        val payload = response.payload
                        if (payload != null && payload is NCWSignedUrlPayload) {
                            withContext(Dispatchers.Main) {
                                _errorFile.value = payload

                            }
                            processNextFile(fileList)
                        }
                }

                else {
                    Log.e("FileProcessing", "Failed to get pre-signed URL for: ${currentFile.fileName}")
                }
            } catch (e: Exception) {
                Log.e("FileProcessing", "Error processing file: ${currentFile.fileName}", e)
            }
        } else {
            Log.d("FileProcessing", "All files processed.")
        }
    }




    /* fun uploadFilesSequentially(mMultipleFile: MutableList<MultiFileModel>) {
         viewModelScope.launch(Dispatchers.IO) {
         if (mMultipleFile.isNotEmpty()) {
             processNextFile(mMultipleFile)
         }
         }
     }

     private suspend fun processNextFile(
         fileList: MutableList<MultiFileModel>,
     ) {
         if (fileList.isNotEmpty()) {
             val currentFile = fileList.first()
             val mediaUpload = NCWSignedUrlPayload(
                 fileType = currentFile.mimeType,
                 uploadKeyPrefix = currentFile.fileName
             )

             val response = chatRepository.getPreSignedUrl(mediaUpload)
             if (response != null) {
                 when (response) {
                     is NCWState.Success -> {
                         val responseData = response.data as NCWGetPreSignedUrl
                         val uploadResponse =
                             chatRepository.uploadFile(currentFile.file, responseData)
                         if (uploadResponse != null) {
                             fileList.removeAt(0)
                             processNextFile(fileList)
                             withContext(Dispatchers.Main) {
                                 _getUploadedMediaUrl.value = uploadResponse
                             }
                         }
                     }

                     else -> {}
                 }

             }

         }
     }


 */




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
            val response = chatRepository.hitEndChatAPI(payload = message)
            withContext(Dispatchers.Main) {
                Log.e("sendMessageAPI", "response " + response)
                _NCW_endChatResponse.value = response
            }
        }

    }

    fun hitSubmitSurveyRequestAPI(message: SubmitSurveyRequest) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.hitSubmitSurveyRequestAPI(message)

            withContext(Dispatchers.Main) {
                Log.e("sendMessageAPI", "response " + response)
                _NCW_Survey_RESPONSE.value = response
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

    fun hitAuthenticateUserApi(jwtToken:String, botRefID:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.hitAuthenticateUserApi(jwtToken = jwtToken,botRefID = botRefID,authEnabled = "true")

            withContext(Dispatchers.Main) {
                Log.e("Auth Response", "response " + response)
                _loginResponse.value = response
            }
        }

    }

    fun hitLogoutApi(jwtToken:String, botRefID:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.hitLogoutApi(jwtToken = jwtToken,botRefID = botRefID,authEnabled = "true")

            withContext(Dispatchers.Main) {
                Log.e("Auth Response", "response " + response)
                _logoutResponse.value = response
            }
        }

    }

    fun getSurveyRule(botRefID:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chatRepository.getSurveyRule(botRefID)

            withContext(Dispatchers.Main) {
                _surveyRuleResponse.value = response
            }
        }

    }





}
