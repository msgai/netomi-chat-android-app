package com.netomi.chat.awsiot

import android.util.Log
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent
import java.util.UUID

object NCWAwsIotManager {

    private lateinit var mqttManager: AWSIotMqttManager
    private lateinit var awsCredentialsProvider: AWSCredentialsProvider
    private var connectionStatus = 0
    private val connectionStatusLiveData = NCWSingleLiveEvent<String>()

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
                    Log.e("mqttManager","\"Connecting to AWS IoT...")
                    connectionStatusLiveData.postValue(NCWConnectionStatus.CONNECTING.toString())
                }


                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected -> {
                    connectionStatus = 1
                    Log.d("IoT", "Connected to AWS IoT")
                    Log.e("mqttManager","Connected to AWS IoT")
                    connectionStatusLiveData.postValue(NCWConnectionStatus.CONNECTED.toString())
                    subscribeToTopic(topic, chatViewModel)
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost -> {
                    connectionStatus = 2
                    Log.e("IoT", "Connection lost", throwable)
                    Log.e("mqttManager","Connection lost")
                    connectionStatusLiveData.postValue(NCWConnectionStatus.CONNECTION_LOST.toString())
                    callBackConnectLost(true)
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting ->{
                    connectionStatus = 3
                    Log.d("IoT", "Reconnecting to AWS IoT...")
                    Log.e("mqttManager","Reconnecting to AWS IoT")
                    connectionStatusLiveData.postValue(NCWConnectionStatus.RE_CONNECTED.toString())
                    //chatViewModel.getAWSMQTTCredentials("")
                }

                else -> {
                    Log.e("IoT", "Unknown connection status: $status")
                    Log.e("mqttManager","Unknown connection status")
                    connectionStatusLiveData.postValue(NCWConnectionStatus.UNKNOWN.toString())
                }
            }
        }
    }

     fun callBackConnectLost(connectionStatus: Boolean = false): Boolean {
              return connectionStatus
    }

    /**
     * Unsubscribes from the current topic and subscribes to a new one.
     */
    fun switchTopic(oldTopic: String, newTopic: String, chatViewModel: NCWChatViewModel) {
        if (connectionStatus == 1) { // Ensure MQTT is connected before switching topics
            Log.d("IoT", "Switching from topic [$oldTopic] to [$newTopic]")

            // Unsubscribe from the old topic
            unsubscribeToTopic(oldTopic)

            // Subscribe to the new topic
            subscribeToTopic(newTopic, chatViewModel)
        } else {
            Log.e("IoT", "Cannot switch topics. MQTT is not connected.")
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
           /* if (chatViewModel.awsMessage.hasActiveObservers()) {
                Log.d("IoT", "Active observers found for awsMessage")
            } else {
                Log.d("IoT", "No active observers for awsMessage")
            }*/

            chatViewModel.updateAwsMessage(message)
        }
    }


     fun unsubscribeRestart(topic: String) {
         unsubscribeToTopic(topic)
    }
    /**
     * Subscribes to a topic to receive messages.
     * @param topic Topic to subscribe to.
     */
    private fun unsubscribeToTopic(topic: String) {
        try {
            mqttManager.unsubscribeTopic(topic)
            Log.d("IoT", "Unsubscribed from topic: $topic")
        } catch (e: Exception) {
            Log.e("IoT", "Error unsubscribing from topic $topic", e)
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
    fun getConnectionStatusLiveData(): NCWSingleLiveEvent<String> = connectionStatusLiveData


    fun getConnectionStatus(): Int {

        return connectionStatus
    }
}