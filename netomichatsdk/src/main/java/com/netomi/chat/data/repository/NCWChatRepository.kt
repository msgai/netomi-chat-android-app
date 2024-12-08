package com.netomi.chat.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.data.network.NCWBaseService
import com.netomi.chat.data.network.NCWRetrofitClient
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEndChatResponse
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.utils.NCWRoutes
import com.netomi.chat.utils.NCWState
import com.netomi.chat.utils.NCWAppUtils.createRequestBody
import com.netomi.chat.utils.NCWAppUtils.getFileContentType
import com.netomi.chat.utils.NCWAppUtils.prepareFilePart
import java.io.File

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
                    NCWState.sendMessageError(parseError(errorBody), code = response.code(),message)

                } else {
                    //State.error(mapApiException(response.code()), code = response.code())
                    NCWState.sendMessageError(mapApiException(response.code()), code = response.code(),message)
                }
            }
        } catch (e: Exception) {
           // State.error(e.message.toString(), code = 500)
            NCWState.sendMessageError(e.message.toString(), code =500,message)
        }
    }


    // API to get ConversationId
    suspend fun getConversationId(
        botRef: String?,
    ): NCWState<NCWGetConversationIdResponse> {
        return try {
            //    liveData.value = State.loading(Routes.ROUTE_GET_CONVERSATION_ID, loadingType)
            val response = apiInterface.getConversationId(botRef)
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
        val response = apiInterface.getPreSignedUrl(payload)
        return if (response.isSuccessful && response.body() != null) {
            NCWState.success(data = response.body()!!, NCWRoutes.ROUTE_GET_PRESIGNED_URL)
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                NCWState.error(parseError(errorBody), code = response.code())
            } else {
                NCWState.error(mapApiException(response.code()), code = response.code())
            }
        }
    }


    suspend fun uploadFile(
        mediaFile: File?,
        preSignedUrl: NCWGetPreSignedUrl
    ): NCWState<NCWGetMediaUploadUrl> {
        if (mediaFile == null) return NCWState.error("Media URI is null", code = 400)

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

                    val title = mediaFile.name // File name with extension, e.g., "example.png"
                    val fileSize = mediaFile.length() // File size in bytes
                    // Log or print the details
                    println("Title: $title")
                    println("File Size: $fileSize bytes")
                    val uploadResult = NCWGetMediaUploadUrl(locationHeader, mediaType,title, fileSize = fileSize)
                    Log.d("UploadFile", "File uploaded successfully. Location: $locationHeader")
                    NCWState.success(data = uploadResult, NCWRoutes.ROUTE_UPLOAD_MEDIA)
                } else {
                    Log.e("UploadFile", "Location header is missing")
                    NCWState.error("Location header is missing in the response", code = 500)
                }
            } else {
                Log.e("UploadFile", "Error occurred: ${response.code()} - ${response.message()}")
               NCWState.error("File upload failed", code = response.code())


            }
        } catch (e: Exception) {
            Log.e("UploadFile", "Exception occurred during upload: ${e.message}")
            NCWState.error("File upload failed due to an exception", code = 500)
        }
    }


    /*    suspend fun uploadFile(
            mediaUri: File?,
            preSignedUrl: GetPreSignedUrl
        ): State<GetMediaUploadUrl>? {
            val filePart = mediaUri?.let { prepareFilePart(it) }

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

            val mediaType = mediaUri?.let { getFileContentType(it) }
            Log.e("sajnaskmasmklsa ", "ss " + mediaType)
            val contentType =
                mediaUri?.let { getFileContentType(it) }
                    ?.let { createRequestBody(it) } // Replace with actual file type

            return mediaUri?.let {
                val response = preSignedUrl.preSignedUrl?.url?.let { url ->
                    filePart?.let { file ->
                        apiInterface.uploadFile(
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
                            file
                        )
                    }
                }

                response?.let {
                    if (it.isSuccessful) {
                        val locationHeader = it.headers()["Location"]
                        if (locationHeader != null) {
                            val mObj = GetMediaUploadUrl(locationHeader, mediaType)

                            State.success(data = mObj, Routes.ROUTE_GET_PRESIGNED_URL)
                            // contentType
                            Log.d("UploadFile", "File uploaded successfully. Location: $locationHeader")
                        } else {
                            Log.e("UploadFile", "Location header is missing")
                            State.error("File upload failed", code = 500)

                        }
                    } else {
                        State.error("File upload failed", code = 500)
                        Log.e("UploadFile", "Error occurred: ${it.code()} - ${it.message()}")
                    }
                } ?: run {
                    Log.e("UploadFile", "Response is null")
                    State.error("File upload failed", code = 500)
                }

            }


        }*/

    // Hit API to get AWS MQTT Credentials
    suspend fun hitEndChatAPI(payload: NCWEndChatRequest): NCWState<NCWEndChatResponse> {
        return try {
            val response = apiInterface.hitEndChatAPI(payload)
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
}


