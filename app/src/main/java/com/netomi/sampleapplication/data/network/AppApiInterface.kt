package com.netomi.sampleapplication.data.network

import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.messages.WebhookPayload
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes.ROUTE_GET_CONVERSATION_ID
import com.netomi.chat.utils.Routes.ROUTE_GET_MQTT_CREDENTIALS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for defining network endpoints in the NCW SDK.
 *
 * This interface provides methods to interact with the NCW server,
 * including fetching chat history and sending new messages.
 * Each method is annotated with the appropriate **HTTP method** (GET, POST)
 * and defines the corresponding API endpoint.
 */

interface AppApiInterface {



}