package com.netomi.sampleapplication.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.netomi.chat.utils.NCWRoutes
import com.netomi.sampleapplication.data.network.AppApiInterface
import com.netomi.sampleapplication.data.network.AppBaseService
import com.netomi.sampleapplication.data.network.AppRetrofitClient
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.utils.State
import javax.net.ssl.SSLHandshakeException

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


class AppRepository(private val context: Context) : AppBaseService() {

    private val apiInterface =
        AppRetrofitClient.getInstance(context).create(AppApiInterface::class.java)

    // Fetch Bot List
    suspend fun <T> getBotListing(
        liveData: MutableLiveData<State<T>>,
        email: String,
        loadingType: State.LoadingType? = State.LoadingType.LOADER
    ): State<BotListingResponse> {
        liveData.postValue(State.loading(NCWRoutes.ROUTE_GET_CHAT, loadingType))

        return try {
            val response = apiInterface.getBotListing(email)
            if (response.isSuccessful && response.body() != null) {
                State.success(data = response.body()!!, NCWRoutes.ROUTE_GET_CHAT)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    State.error(parseError(errorBody), code = response.code())
                } else {
                    State.error(mapApiException(response.code()), code = response.code())
                }
            }
        } catch (e: SSLHandshakeException) {
            State.error("SSL Handshake failed. Check the server certificate.", code = -1)
        } catch (e: Exception) {
            State.error("An unexpected error occurred: ${e.localizedMessage}", code = -1)
        }
    }


}


