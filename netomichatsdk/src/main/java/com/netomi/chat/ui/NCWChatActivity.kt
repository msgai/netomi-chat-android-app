package com.netomi.chat.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.netomi.chat.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NCWChatActivity : AppCompatActivity() {
    //private val chatViewModel: NCWChatViewModel by viewModels()

    private lateinit var chatLog: TextView
    private lateinit var inputField: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatLog = findViewById(R.id.chatLog)
        inputField = findViewById(R.id.inputField)
        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val message = inputField.text.toString()
            if (message.isNotEmpty()) {
                //chatViewModel.sendMessage(message)
                inputField.text.clear()
            }
        }

        observeChatMessages()
    }

    // Observe chat messages from the ViewModel
    private fun observeChatMessages() {
        lifecycleScope.launch {
          /*  chatViewModel.chatMessages.collect { messages ->
                chatLog.text = messages.joinToString("\n") { "${it.sender}: ${it.content}" }
            }*/
        }
    }
}