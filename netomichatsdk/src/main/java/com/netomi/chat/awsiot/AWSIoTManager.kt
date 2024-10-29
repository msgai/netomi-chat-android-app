package com.netomi.chat.awsiot

import android.util.Log
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSSessionCredentials
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Regions
import java.util.UUID

object AWSIoTManager {

    // AWS IoT Credentials and Endpoint
    private val accessKeyId = "ASIA34SGY5SBK6T3Z2R4"  // Replace with your access key
    private val secretAccessKey = "Ei/zwYyKLOQJ0Jlz3EiOvWBDCQf6GRuqHgJVDh/s"  // Replace with your secret key
    private val sessionToken = "IQoJb3JpZ2luX2VjEIL//////////wEaCXVzLWVhc3QtMSJHMEUCIFonunGoGtalpCZJBwzS0FAopZ7DGOo9hCv3DUEzlQMwAiEA+A29NM9MFd+rwzb9qZznUroL7SCf0dMkHjWKTIgxaJUqoQII6///////////ARADGgw4MTcyNjU5NjIxMTQiDIYQ529Ia2Dji/X0JSr1ATijZXAWVlnY9yoVeqvYozJVqc+oy+92ubnFLvF/NTSnbpj0jgnDFFY0+1/8H5R+MvNLNc6q7CEbpPemb6wwcNYaFTBcS9sa+oVZi0Jd+QCHJkVid9Q4gO21h0aJxCC7GtGEDki1ce67jIZZmXt00bq5QbvjgEmVDPPWI88ntwEB/h4D9Quzxh1Or+EjdMlCRVL8oXqIk9eTEq5S5td/Ga4Wd6cJOZTPrzRWdwmOJ31J5gR4bv3vF/MKkvht8xcZvUT6eJcW/fdo06I2NYJeA6ViVYPhmrosUtOaSNdMM0MsJaij82oE1brPV2GxSFQ5QfwL5CJlMOLG7bgGOp0B1HyDnaa5wswCWorMWEHfDDH/52B9920UaNcSZWtxYMOWwxXO5s8Ogg/3XMHACKR6EVt2w3op+9f5VUsxhsjJ88KfCwcflc3GcgC24/b4fXsXv0M68mDpGX/XDRg6qU739LTSNZkoCVu4MZFpqtHR7Nh/6ZLrSKDo5xV5S/sb6vUaBdG6BdsDTJzEQkoyu1YkoK5g5hBLVRxoFGm/1g=="  // Replace with your session token
    private val iotEndpoint = "10jvu4u60lghw-ats.iot.us-east-1.amazonaws.com"  // Replace with your IoT endpoint
    private val region = Regions.US_EAST_1  // Adjust according to your region

    // Initialize AWS IoT MQTT Manager with unique Client ID
    private val clientId = UUID.randomUUID().toString()
    private val mqttManager = AWSIotMqttManager(clientId, iotEndpoint)

    // Set up session credentials
    private val sessionCredentials = BasicSessionCredentials(
        accessKeyId, secretAccessKey, sessionToken
    )

    /**
     * Connects to the AWS IoT Broker.
     */
    fun connect() {
        // Optional: Set keep-alive interval
        mqttManager.setKeepAlive(10)  // Optional: Set keep-alive interval
        mqttManager.isAutoReconnect = true  // Enable automatic reconnection

        // Create BasicSessionCredentials object using provided temporary credentials.
        val sessionCredentials: AWSSessionCredentials = BasicSessionCredentials(accessKeyId, secretAccessKey, sessionToken)
        val credentialsProvider = AWSStaticCredentialsProvider(sessionCredentials)


        // Connect using the session credentials wrapped in an AWSCredentialsProvider
        mqttManager.connect(sessionCredentials as AWSCredentialsProvider
        ) { status, throwable ->
            when (status) {
                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connecting ->
                    Log.d("IoT", "Connecting to AWS IoT...")

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected ->
                    Log.d("IoT", "Connected to AWS IoT")

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost -> {
                    Log.e("IoT", "Connection lost", throwable)
                }

                AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting ->
                    Log.d("IoT", "Reconnecting to AWS IoT...")

                else -> Log.e("IoT", "Unknown connection status: $status")
            }
        }
    }

    /**
     * Subscribes to a topic to receive messages.
     * @param topic Topic to subscribe to.
     */
    fun subscribeToTopic(topic: String) {
        mqttManager.subscribeToTopic(topic, AWSIotMqttQos.QOS0) { topic, data ->
            val message = String(data, Charsets.UTF_8)
            Log.d("IoT", "Message received on topic [$topic]: $message")
        }
    }

    /**
     * Publishes a message to the specified topic.
     * @param topic Topic to publish to.
     * @param message Message to send.
     */
    private fun publishMessage(topic: String, message: String) {
        // Use QoS level 0
        val qos = AWSIotMqttQos.QOS0

        try {
            mqttManager.publishString(message, topic, AWSIotMqttQos.QOS1)
        } catch (e: Exception) {
            Log.e("LOG_TAG", "Publish error.", e)
        }
    }
}