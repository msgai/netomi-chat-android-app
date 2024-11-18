package com.netomi.sampleapplication.data.repository

import android.content.Context
import com.netomi.sampleapplication.data.network.AppApiInterface
import com.netomi.sampleapplication.data.network.AppBaseService
import com.netomi.sampleapplication.data.network.AppRetrofitClient
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.messages.WebhookPayload
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


class AppRepository(private val context: Context) : AppBaseService() {

    private val apiInterface =
        AppRetrofitClient.getInstance(context).create(AppApiInterface::class.java)



}


