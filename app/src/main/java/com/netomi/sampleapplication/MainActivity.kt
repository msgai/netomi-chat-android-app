package com.netomi.sampleapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.netomi.chat.ui.view.NCWChatActivity

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
                startActivity((Intent(this, NCWChatActivity::class.java)))
            }
        }
    }
}