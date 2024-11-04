package com.netomi.chat.data.repository

import android.content.Context
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.data.network.NCWBaseService
import com.netomi.chat.data.network.NCWRetrofitClient
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes
import com.netomi.chat.utils.State

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


class NCWChatRepository(private val context: Context) :NCWBaseService() {

    private val apiInterface = NCWRetrofitClient.getInstance(context).create(NCWApiInterface::class.java)

    // Fetch chat history
    fun getChatHistory(): State<NCWBaseResponse<ArrayList<NCWMessage>>> {
        val response = apiInterface.fetchChatHistory()
        return if (response.isSuccessful && response.body() != null) {
            State.success(data = response.body()!!, Routes.ROUTE_GET_CHAT)
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                State.error(parseError(errorBody), code = response.code())
            } else {
                State.error(mapApiException(response.code()), code = response.code())
            }
        }
    }

    // Send a new message
    fun sendMessage(message: NCWMessage): State<NCWBaseResponse<Boolean>> {
        val response = apiInterface.sendMessage(message)
        return if (response.isSuccessful && response.body() != null) {
            State.success(data = response.body()!!, Routes.ROUTE_SEND_CHAT)
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                State.error(parseError(errorBody), code = response.code())
            } else {
                State.error(mapApiException(response.code()), code = response.code())
            }
        }
    }

    // API to get ConversationId
    suspend fun getConversationId(botRef: String?): State<GetConversationIdResponse> {
        return try {
            val response = apiInterface.getConversationId(botRef)
            if (response.isSuccessful && response.body() != null) {
                State.success(data = response.body()!!, Routes.ROUTE_GET_CONVERSATION_ID)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    State.error(parseError(errorBody), code = response.code())
                } else {
                    State.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: Exception) {
            State.error(e.message.toString(), code =500)
        }
    }

}


