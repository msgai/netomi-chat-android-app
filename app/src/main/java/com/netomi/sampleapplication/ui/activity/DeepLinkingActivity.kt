package com.netomi.sampleapplication.ui.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.netomi.sampleapplication.R

class DeepLinkingActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_linking)

        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)

        val data: Uri? = intent?.data
        if (data != null) {
            val path = data.path
            val screen = path?.substringAfterLast("/")
            tvTitle.text=screen

        }

        ivBack.setOnClickListener {
            finish()
        }
    }
}