package com.netomi.chat.awsiot

import android.util.Log
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.ui.viewmodel.SingleLiveEvent
import java.util.UUID

object NCWAwsIotManager {

    private lateinit var mqttManager: AWSIotMqttManager
    private lateinit var awsCredentialsProvider: AWSCredentialsProvider
    private var connectionStatus = 0
    private val connectionStatusLiveData = SingleLiveEvent<String>()

    /**
     * Initialize the IoT Manager with provided credentials.
     */
    fun initialize(
        accessKey: String,
        secretKey: String,
        sessionToken: String,
        iotEndpoint: String
    ) {
        // Initialize AWS IoT MQTT Manager with unique Client ID
        val clientId = UUID.randomUUID().toString()
        mqttManager = AWSIotMqttManager(clientId, iotEndpoint)

        // Set up credentials provider
        awsCredentialsProvider = object : AWSCredentialsProvider {
            override fun getCredentials(): BasicSessionCredentials {
                return BasicSessionCredentials(
                    accessKey,
                    secretKey,
                    sessionToken
                )
            }

            override fun refresh() {
                // Implement refresh logic if needed
            }
        }
    }

    /**
     * Connects to the AWS IoT Broker.
     */
    fun connect(chatViewModel: NCWChatViewModel, topic: String) {
        mqttManager.setKeepAlive(60)
        mqttManager.isAutoReconnect = true

        mqttManager.connect(awsCredentialsProvider) { status, throwable ->
            when (status) {
                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connecting ->{
                    connectionStatus = 0
                    Log.d("IoT", "Connecting to AWS IoT...")
                    connectionStatusLiveData.postValue(ConnectionStatus.CONNECTING.toString())
                }


                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected -> {
                    connectionStatus = 1
                    Log.d("IoT", "Connected to AWS IoT")
                    connectionStatusLiveData.postValue(ConnectionStatus.CONNECTED.toString())
                    subscribeToTopic(topic, chatViewModel)
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost -> {
                    connectionStatus = 2
                    Log.e("IoT", "Connection lost", throwable)
                    connectionStatusLiveData.postValue(ConnectionStatus.CONNECTION_LOST.toString())
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting ->{
                    connectionStatus = 3
                    Log.d("IoT", "Reconnecting to AWS IoT...")
                    connectionStatusLiveData.postValue(ConnectionStatus.RE_CONNECTED.toString())
                    chatViewModel.getAWSMQTTCredentials("")
                }

                else -> {
                    Log.e("IoT", "Unknown connection status: $status")
                    connectionStatusLiveData.postValue(ConnectionStatus.UNKNOWN.toString())
                }
            }
        }
    }

    /**
     * Subscribes to a topic to receive messages.
     * @param topic Topic to subscribe to.
     */
    private fun subscribeToTopic(topic: String, chatViewModel: NCWChatViewModel) {
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

    // Observe connection status from ViewModel or Activity
    fun getConnectionStatusLiveData(): SingleLiveEvent<String> = connectionStatusLiveData


}