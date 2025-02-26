package com.netomi.sampleapplication.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.netomi.sampleapplication.R

class DeepLinkingActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var logoImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_linking)

        window.apply {
            statusBarColor = ContextCompat.getColor(this@DeepLinkingActivity, R.color.deep_link_header)
        }

        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        logoImage= findViewById(R.id.logoImageView)

        val data: Uri? = intent?.data
        if (data != null) {
            val path = data.path

            // Universal Link (https://demo.netomi.com/web/)
            if (data.scheme == "https" && data.host == "demo.netomi.com" && path?.startsWith("/web/") == true) {

                val screen = path.substringAfterLast("/")
                tvTitle.text = screen
                val imageUrl = when {
                    screen.contains("screen1") -> "https://demo.netomi.com/images/deeplink1.png"
                    else -> "https://demo.netomi.com/images/deeplink2.png"
                }
                Glide.with(this).load(imageUrl).into(logoImage)
            }
            // Custom Deep Link (netomisampleapp://app/)
            else if (data.scheme == "netomisampleapp" && data.host == "app") {

                val screen = path?.substringAfter("/")
                tvTitle.text = screen
                val imageUrl = when {
                    screen?.contains("screen1") == true -> "https://demo.netomi.com/images/deeplink1.png"
                    else -> "https://demo.netomi.com/images/deeplink2.png"
                }
                Glide.with(this).load(imageUrl).into(logoImage)
            }
        }

        ivBack.setOnClickListener {
            finish()
        }
    }
}