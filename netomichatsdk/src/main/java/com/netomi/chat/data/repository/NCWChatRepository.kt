package com.netomi.chat.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.data.network.NCWBaseService
import com.netomi.chat.data.network.NCWRetrofitClient
import com.netomi.chat.model.GetConversationPayload
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.auth.LoginResponse
import com.netomi.chat.model.auth.LogoutResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEndChatResponse
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackResponse
import com.netomi.chat.model.language.LanguageResponse
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.model.survey_rule.SurveyRuleResponse
import com.netomi.chat.model.transcript.NCWEmailRequest
import com.netomi.chat.survey.SubmitSurveyRequest
import com.netomi.chat.utils.NCWRoutes
import com.netomi.chat.utils.NCWState
import com.netomi.chat.utils.NCWAppUtils.createRequestBody
import com.netomi.chat.utils.NCWAppUtils.getFileContentType
import com.netomi.chat.utils.NCWAppUtils.prepareFilePart
import java.io.File
import java.io.IOException

/**
 * Repository responsible for managing chat-related data operations.
 *
 * This class acts as an abstraction layer between the ViewModel and the data sources (e.g., APIs).
 * It provides methods to fetch chat history and send new messages using a Retrofit API interface.
 * The repository ensures that data operations are encapsulated and managed in a consistent way.
 *
 * ## Responsibilities:
 * Fetch chat history from the server using the API.
 * Send new messages to the MQTT Broker/server.
 * Handle communication between the **ViewModel** and the **API**.
 *
 * ## Usage:
 * The **`NCWChatRepository`** is used by the **`NCWChatViewModel`** to perform data operations
 * such as loading chat history and sending new messages.
 */


class NCWChatRepository(private val context: Context) : NCWBaseService() {

    private val apiInterface =
        NCWRetrofitClient.getInstance(context).create(NCWApiInterface::class.java)

    // Fetch chat history
    suspend fun <T> getChatHistory(
        payload: NCWGetChatHistoryPayload?, liveData: MutableLiveData<NCWState<T>>,
        loadingType: NCWState.LoadingType? = NCWState.LoadingType.LOADER
    ): NCWState<NCWGetChatHistoryResponse> {
        liveData.postValue(NCWState.loading(NCWRoutes.ROUTE_GET_CHAT, loadingType))
        val response = apiInterface.fetchChatHistory(payload)
        return if (response.isSuccessful && response.body() != null) {
            NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_CHAT)
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                NCWState.error(parseError(errorBody), code = response.code())
            } else {
                NCWState.error(mapApiException(response.code()), code = response.code())
            }
        }
    }

    // Send a new message
    suspend fun sendMessage(message: NCWWebhookPayload): NCWState<NCWSendMessageResponse> {
        return try {
            val response = apiInterface.sendMessage(message)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_SEND_CHAT)
              //  State.sendMessageError("Teststts", code = response.code(),message)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                   // State.error(parseError(errorBody), code = response.code())
                    NCWState.sendMessageError(parseError(errorBody), code = response.code(), payload = message)

                } else {
                    //State.error(mapApiException(response.code()), code = response.code())
                    NCWState.sendMessageError(mapApiException(response.code()), code = response.code(),payload = message)
                }
            }
        } catch (e: Exception) {
           // State.error(e.message.toString(), code = 500)
            NCWState.sendMessageError(e.message.toString(), code =500,payload = message)
        }
    }


    // API to get ConversationId
    suspend fun getConversationId(
        payload: GetConversationPayload,
        onRestart: Boolean?,
    ): NCWState<NCWGetConversationIdResponse> {
        return try {
            //    liveData.value = State.loading(Routes.ROUTE_GET_CONVERSATION_ID, loadingType)
            val response = apiInterface.getConversationId(onRestart,payload)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_CONVERSATION_ID)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }

    // Hit API to get AWS MQTT Credentials
    suspend fun getAWSMQTTCredentials(botRef: String?): NCWState<MQTTCredentialsResponse> {
        return try {
            val response = apiInterface.getAWSMQTTCredentials(botRef)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_MQTT_CREDENTIALS)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }
    suspend fun getPreSignedUrl(payload: NCWSignedUrlPayload): NCWState<NCWGetPreSignedUrl> {
        return try {
            val response = apiInterface.getPreSignedUrl(payload)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_PRESIGNED_URL)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.sendMessageError(
                        parseError(errorBody),
                        code = response.code(),
                        payload = payload
                    )
                } else {
                    NCWState.sendMessageError(
                        mapApiException(response.code()),
                        code = response.code(),
                        payload = payload
                    )
                }
            }
        } catch (e: IOException) {
            NCWState.sendMessageError("Network error", code = 0, payload = payload)
        } catch (e: Exception) {
            Log.e("getPreSignedUrl", "Exception occurred during API call: ${e.message}")
            NCWState.sendMessageError("API call failed", code = 500, payload = payload)
        }
    }




    suspend fun uploadFile(
        mediaFile: File?,
        preSignedUrl: NCWGetPreSignedUrl
    ): NCWState<NCWGetMediaUploadUrl> {
        if (mediaFile == null) return NCWState.error("Media URI is null", code = 400)
        Log.e("MedianName","Beforee q"+mediaFile.name )
        // Prepare file part
        val filePart = prepareFilePart(mediaFile)

        // Extract fields from preSignedUrl
        val fields = preSignedUrl.preSignedUrl?.fields
        val key = fields?.key?.let { createRequestBody(it) }
        val bucket = fields?.bucket?.let { createRequestBody(it) }
        val amzAlgorithm = fields?.XAmzAlgorithm?.let { createRequestBody(it) }
        val amzCredential = fields?.XAmzCredential?.let { createRequestBody(it) }
        val amzDate = fields?.XAmzDate?.let { createRequestBody(it) }
        val policy = fields?.policy?.let { createRequestBody(it) }
        val amzSignature = fields?.XAmzSignature?.let { createRequestBody(it) }

        // Static fields
        val acl = createRequestBody("public-read")

        // Determine the content type
        val mediaType = getFileContentType(mediaFile)
        Log.e("MediaTupe","SAASAS"+mediaType)
        val contentType = createRequestBody(mediaType)

        // Ensure URL exists
        val url = preSignedUrl.preSignedUrl?.url
        if (url == null) return NCWState.error("Pre-signed URL is null", code = 400)

        // Perform the upload
        return try {
            val response = apiInterface.uploadFile(
                url,
                key,
                bucket,
                amzAlgorithm,
                amzCredential,
                amzDate,
                policy,
                amzSignature,
                acl,
                contentType,
                filePart
            )

            if (response.isSuccessful) {
                val locationHeader = response.headers()["Location"]
                if (locationHeader != null) {

                    val title = mediaFile.name
                    val fileSize = mediaFile.length()
                    val uploadResult = NCWGetMediaUploadUrl(locationHeader, mediaType,title, fileSize = fileSize)
                    Log.d("UploadFile", "File uploaded successfully. Location: $locationHeader")
                    NCWState.success(data = uploadResult, NCWRoutes.ROUTE_UPLOAD_MEDIA)
                } else {
                    Log.e("UploadFile", "Location header is missing")
                    NCWState.error("Location header is missing in the response", code = 500)
                }
            } else {
                val payload=NCWSignedUrlPayload(mediaType,mediaFile.name)
                NCWState.sendMessageError("File upload failed", code = response.code(), payload = payload)
            }
        } catch (e: Exception) {
            Log.e("UploadFile", "Exception occurred during upload: ${e.message}")
            val payload=NCWSignedUrlPayload(mediaType,mediaFile.name)
            NCWState.sendMessageError("File upload failed", code =500, payload = payload)
        }
    }




    // Hit API to get AWS MQTT Credentials
    suspend fun hitEndChatAPI(payload: NCWEndChatRequest): NCWState<NCWEndChatResponse> {
        return try {
            val response = apiInterface.hitEndChatAPI(payload = payload)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_END_CHAT)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }

    /** Hit Feedback API*/
    suspend fun hitFeedbackAPI(payload: NCWFeedbackRequest): NCWState<NCWFeedbackResponse> {
        return try {
            val response = apiInterface.hitFeedbackAPI(payload)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.WEBHOOK_EVENT)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }
    suspend fun hitSubmitSurveyRequestAPI(payload: SubmitSurveyRequest): NCWState<NCWEndChatResponse> {
        return try {
            val response = apiInterface.hitSubmitSurveyRequestAPI(payload)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_SURVEY)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }

    suspend fun hitAuthenticateUserApi(jwtToken:String, botRefID:String,authEnabled:String): NCWState<LoginResponse> {
        return try {
            val response = apiInterface.hitAuthenticateUserApi(botRefId = botRefID, authToken = jwtToken, authEnabled = "true")
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.LOGIN)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }

    suspend fun hitLogoutApi(jwtToken:String, botRefID:String,authEnabled:String): NCWState<LogoutResponse> {
        return try {
            val response = apiInterface.hitLogoutAPI(botRefId = botRefID, authToken = jwtToken, authEnabled = "true")
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.LOGOUT)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }



    suspend fun getSurveyRule(botRefID:String): NCWState<SurveyRuleResponse> {
        return try {
            val response = apiInterface.getSurveyRule(botRefID)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_SURVEY_RULE)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)
        }
    }

    suspend fun sendTranscript(payload: NCWEmailRequest): NCWState<NCWSendMessageResponse> {
        return try {
            val response = apiInterface.sendTranscript(payload)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_SEND_TRANSCRIPT)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())


                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())

                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)

        }
    }

    suspend fun getLanguageStrings(botRefId: String,code:String): NCWState<LanguageResponse> {
        return try {
            val response = apiInterface.getLanguageStrings(botRefId,code)
            if (response.isSuccessful && response.body() != null) {
                NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_LANGUAGE)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())


                } else {
                    NCWState.error(mapApiException(response.code()), code = response.code())

                }
            }
        } catch (e: Exception) {
            NCWState.error(e.message.toString(), code = 500)

        }
    }


}


