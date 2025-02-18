package com.netomi.sampleapplication.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

            // Universal Link (https://demo.netomi.com/web/)
            if (data.scheme == "https" && data.host == "demo.netomi.com" && path?.startsWith("/web/") == true) {

                val screen = path.substringAfterLast("/")
                tvTitle.text = screen
            }

            // Custom Deep Link (netomisampleapp://app/)
            else if (data.scheme == "netomisampleapp" && data.host == "app") {

                val screen = path?.substringAfter("/")
                tvTitle.text = screen
            }
        }


        ivBack.setOnClickListener {
            finish()
        }
    }
}