package com.netomi.chat.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.netomi.chat.R
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.BaseResponse
import com.netomi.chat.utils.State

class NCWChatActivity : AppCompatActivity() {
    private val chatViewModel: NCWChatViewModel by viewModels()

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
            val messageContent = inputField.text.toString()
            if (messageContent.isNotEmpty()) {
                chatViewModel.sendMessage(messageContent)
                inputField.text.clear()
            }
        }

        observeChatMessages()
    }

    private fun observeChatMessages() {
        chatViewModel.chatMessages.observe(this, Observer { messages ->
            handleApiCallback(messages as State<BaseResponse<Any>>)
        })

    }

    private fun handleApiCallback(response: State<BaseResponse<Any>>) {
        when(response){
            is State.Loading->{
               Toast.makeText(this,"Loading..",Toast.LENGTH_SHORT).show()
            }
            is State.Success->{
                Toast.makeText(this,"Success..",Toast.LENGTH_SHORT).show()
                chatLog.text=response.data.data.toString()
            }
            is State.Error->{
                Toast.makeText(this,"Error..",Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this,"Else..",Toast.LENGTH_SHORT).show()
            }
        }

    }


}