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
    private val accessKeyId = "ASIA34SGY5SBLM2UE7LC"  // Replace with your access key
    private val secretAccessKey = "QvmaKlfFaFKNlb4xK4R07dO9ciRxnTV33Hh8iuwW"  // Replace with your secret key
    private val sessionToken = "IQoJb3JpZ2luX2VjEOT//////////wEaCXVzLWVhc3QtMSJHMEUCICBM85cy883AyjSFPACz90IJYgfPj9rM4qfi09KwojPGAiEAlV7cVgnfg9Ktalx5ITnGjKeWWcG0MEQGtDncqxgtstEqmAIIXBADGgw4MTcyNjU5NjIxMTQiDNHGa2VcQwmvR6tKxCr1AVh4M9zKf4H3ojjogmzgIAtoiO4k3rghu3cb0yrBI8wuGgDPMVXfzInWX10obwQHmlqn9s10awizyIZiNiM67mBuzG6b2jG7DNLcpwCctvDahvT4AVykBf5sXVRmFvysHQw+H985Le+oT2bSq1CovrrYTKqWsQqHtKrly++lK3EyNpc63D7pH2aYccgKl4hFYF5QfrKJK3DlR20qGeHcxbkXfd4CXJQId6ljGvhkPXbrjycXf8Xt50fchC8MPAGhD/W3N5w1psg4t3Uv1J8BqTbaevyVixyuhkr13azvbge+u/wZCMkjlttKFWKf3enBIpBMFaO4MMqHg7kGOp0BlBfhHqlAGf2SG19JrIAwu9XDu8p7UxqUXdP9Z0kuXifzkYmmahNjFSVDoxdRq9LbXR4ynV1QIclcbsjjOG3lT5gVZQKUaNagaowZNqJQJMoU/ZZQ0Grdi0ux6cRkFjs4Y02f+pzs92DjkYWN92tvFzBkg5f+oLNjadrxfDR03JiDqmG5x52BDmHJdKFTcbm0T358Pw4DqvVvfrYy7Q=="  // Replace with your session token
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
        mqttManager.setKeepAlive(10)  // Optional: Set keep-alive interval

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