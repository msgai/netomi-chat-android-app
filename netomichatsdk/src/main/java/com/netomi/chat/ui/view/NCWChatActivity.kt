package com.netomi.chat.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.netomi.chat.R
import com.netomi.chat.awsiot.AWSIoTManager
import com.netomi.chat.config.NCWChatSdk
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes
import com.netomi.chat.utils.State

/**
 * Activity responsible for displaying the chat interface and handling user interactions.
 *
 * This activity is part of the Chat SDK and serves as the entry point for users to interact
 * with the chat. It uses **ViewModel** to manage UI-related data in a lifecycle-aware manner.
 *
 * ## Responsibilities:
 * Display chat messages and update the chat log in real-time.
 * Allow the user to send new messages through a UI form.
 * Observe the **`NCWChatViewModel`** for LiveData updates to keep the UI synchronized.
 *
 * ## Architecture:
 * **View Layer (Activity):** Displays the UI and handles user events.
 *
 * ## Usage:
 * This activity is intended to be launched by the host application or as part of the Chat SDK.
 *
 */
class NCWChatActivity : AppCompatActivity() {
    private val chatViewModel: NCWChatViewModel by viewModels()

    private lateinit var chatLog: TextView
    private lateinit var inputField: EditText
    private lateinit var sendButton: Button
    private var ncwSdkConfig:NCWSdkConfig?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatLog = findViewById(R.id.chatLog)

        inputField = findViewById(R.id.inputField)

        sendButton = findViewById(R.id.sendButton)

        ncwSdkConfig = NCWChatSdk.getConfig()

        applyConfig()

        AWSIoTManager.connect(chatViewModel)
        //AWSIoTManager.subscribeToTopic("chat_widget/b23963e4-56c5-4d8f-929e-2b0f1155b1f8/48fd2c5f-7e79-593b-bbef-9b1d7e450f86")

        sendButton.setOnClickListener {
            val messageContent = inputField.text.toString()
            if (messageContent.isNotEmpty()) {
               // chatViewModel.sendMessage(messageContent)
                inputField.text.clear()
                AWSIoTManager.publishMessage("topicOne",messageContent)
            }
        }

        observeChatMessages()

        chatViewModel.getAppConfig()
    }

    private fun applyConfig() {
        ncwSdkConfig?.let {
            val sendButtonStyle= it.sendButtonStyle
            sendButton.setBackgroundColor(sendButtonStyle.backgroundColor)
            sendButton.setTextColor(sendButtonStyle.textColor)
            sendButton.textSize = sendButtonStyle.fontSize
        }
    }

    private fun observeChatMessages() {
        // Observe the chat messages LiveData from the ViewModel
        chatViewModel.chatMessages.observe(this, Observer { messages ->
            handleApiCallback(messages as State<NCWBaseResponse<Any>>)
        })

        // Observe app configuration changes
        chatViewModel.appAppConfiguration.observe(this, Observer {
            handleApiCallback(it as State<NCWBaseResponse<Any>>)
        })

        chatViewModel.awsMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    private fun handleApiCallback(response: State<NCWBaseResponse<Any>>) {
        when (response) {
            is State.Loading -> {
                Toast.makeText(this, "Loading..", Toast.LENGTH_SHORT).show()
            }

            is State.Success -> {
                Toast.makeText(this, "Success..", Toast.LENGTH_SHORT).show()
                // chatLog.text=response.data.data.toString()
                onApiSucess(response.data, response.apiConstant)
            }

            is State.Error -> {
                Toast.makeText(this, "Error..", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Else..", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun onApiSucess(apiResponse: NCWBaseResponse<Any>, apiConstant: String) {

        when (apiConstant) {
            Routes.ROUTE_APP_CONFIG -> {

                apiResponse as NCWBaseResponse<AppConfigurationResponseModel>

               Log.d("Response", "ttetetee "+apiResponse.data.config)
            }


        }

    }
}