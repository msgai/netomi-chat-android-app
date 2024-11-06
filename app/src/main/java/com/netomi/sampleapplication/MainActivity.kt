package com.netomi.sampleapplication

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.ui.init.NCWChatSdk

class MainActivity :AppCompatActivity(), View.OnClickListener {
    private lateinit var btnChat:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnChat=findViewById(R.id.btn_chat)
        btnChat.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_chat->{
               // "ee63a9ce-319a-4761-9390-4e7b76c942f9" Appinbevt Bot 2
              //  "b23963e4-56c5-4d8f-929e-2b0f1155b1f8"
                NCWChatSdk.launch(this,"53e804cd-12ba-45cb-b41d-52413ead4dd4")
               // startActivity((Intent(this, NCWChatActivity::class.java)))
//                initializeChatSdk()
//                startActivity((Intent(this, NCWChatActivity::class.java)))
            }
        }
    }

   private fun initializeChatSdk() {
        val config = NCWSdkConfig(
            chatLogStyle = NCWSdkConfig.TextStyleConfig(
                textColor = Color.DKGRAY,
                fontFamily = Typeface.MONOSPACE,
                fontSize = 18f
            ),
            inputFieldStyle = NCWSdkConfig.TextStyleConfig(
                textColor = Color.BLACK,
                fontFamily = Typeface.SANS_SERIF,
                fontSize = 16f
            ),
            sendButtonStyle = NCWSdkConfig.ButtonStyleConfig(
                backgroundColor = Color.parseColor("#f11c0e"),
                textColor = Color.WHITE,
                fontFamily = Typeface.SERIF,
                fontSize = 16f
            )
        )

        // Initialize the Chat SDK with the custom configuration
        NCWChatSdk.initialize(config)
    }
}