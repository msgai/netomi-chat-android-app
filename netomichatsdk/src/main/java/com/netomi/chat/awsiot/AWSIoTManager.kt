package com.netomi.chat.awsiot

import android.util.Log
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import java.util.UUID

object AWSIoTManager {
    // AWS IoT Credentials and Endpoint
    private val accessKeyId = "ASIA34SGY5SBM7JUANJL"  // Replace with your access key
    private val secretAccessKey = "kJJUxWLU6F5PrP5HN8zx/SSIBdtPJ1ikC6ejjHSe"  // Replace with your secret key
    private val sessionToken = "IQoJb3JpZ2luX2VjEPv//////////wEaCXVzLWVhc3QtMSJHMEUCIG0mDreh5RKVirTNc3TDUy2pjV8U06FQx7CU33nYc965AiEAy12IPeyhdYTJyOA9lXlEwe4jplWfBXDsYhqSs9oOt5QqmAIIdBADGgw4MTcyNjU5NjIxMTQiDCUIYKvhcJAJ4WSdASr1AS1zjSAeGF50claNpfUJ4YyIU7vamOnPFw7/NfYDApT92WrTAM79cAfwm2NOrH/aJcF79sSNRTL7i49HXLpd8i7367j1Ubj/PvsPZ+SMN9Y4aBPF5W8D3AcQsQYC2NkQOW9d+GuJiiDdnLVDbWbNVHQOdx37c8mGw0qBXTH4Cia8lKGMK/EV3HNTSZv5wUqOI0uhLrmV1NEfgFFmTqXDKy3upQ/UQm/oPy/MSHlBfT8bACUKNH+Z6t2H87I/orDQceyKMTDxlG0YJeCpkTnvvJVVNjffYnz7uB2+ndUEphe8J8gTI/HnGqG+0w/vqEbk9FBpvQ9HMO6eiLkGOp0BLI6CMix5bmpAmm37UfHC0iW54pmOhDvGsL50dKfzXZ8Ro+VasTDSq2QhFJlAFEunWVd5od10l3Z8X+VYOr/fU0P5f9YY02Xhe/XG/VlM+olDCd5iNNu/GJYTpOtnM+e0u+GSXsDpXNp2NeJf7+dV+Vyz8vo0RyXw8Zu42NvnyA/hRjguyhl2Be+KdEliqTC+SY5bvYSO9+DyjhvvJQ=="
    private val iotEndpoint = "a10jvu4u60lghw-ats.iot.us-east-1.amazonaws.com"  // Replace with your IoT endpoint

    private var connectionStatus = 0
    // Initialize AWS IoT MQTT Manager with unique Client ID
    private val clientId = UUID.randomUUID().toString()

    private val mqttManager = AWSIotMqttManager(clientId, iotEndpoint)

    /**
     * Connects to the AWS IoT Broker.
     */
    fun connect(chatViewModel: NCWChatViewModel) {
        // Optional: Set keep-alive interval
        mqttManager.setKeepAlive(60)  // 60

        mqttManager.isAutoReconnect = true  // Enable automatic reconnection

        val awsCredentialsProvider = object : AWSCredentialsProvider {
            override fun getCredentials(): BasicSessionCredentials {
                return BasicSessionCredentials(
                    accessKeyId,
                    secretAccessKey,
                    sessionToken
                )
            }

            override fun refresh() {
                // Add logic if you need to refresh credentials
            }
        }

        // Connect using the session credentials wrapped in an AWSCredentialsProvider
        mqttManager.connect(awsCredentialsProvider) { status, throwable ->
            when (status) {
                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connecting ->{
                    connectionStatus = 0
                    Log.d("IoT", "Connecting to AWS IoT...")
                }


                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected -> {
                    connectionStatus = 1
                    Log.d("IoT", "Connected to AWS IoT")
                    subscribeToTopic("topicOne", chatViewModel)
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost -> {
                    connectionStatus = 2
                    Log.e("IoT", "Connection lost", throwable)
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting ->{
                    connectionStatus = 3
                    Log.d("IoT", "Reconnecting to AWS IoT...")
                }

                else -> Log.e("IoT", "Unknown connection status: $status")
            }
        }
    }

    /**
     * Subscribes to a topic to receive messages.
     * @param topic Topic to subscribe to.
     */
    fun subscribeToTopic(topic: String, chatViewModel: NCWChatViewModel) {
        mqttManager.subscribeToTopic(topic, AWSIotMqttQos.QOS0) { topic, data ->
            val message = String(data, Charsets.UTF_8)
            Log.d("IoT", "Message received on topic [$topic]: $message")
            chatViewModel.awsMessage.postValue(message)
        }
    }

    /**
     * Publishes a message to the specified topic.
     * @param topic Topic to publish to.
     * @param message Message to send.
     */
    fun publishMessage(topic: String, message: String) {
        try {
            mqttManager.publishString(message, topic, AWSIotMqttQos.QOS1)
        } catch (e: Exception) {
            Log.e("LOG_TAG", "Publish error.", e)
        }
    }

}