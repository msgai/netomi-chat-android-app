package com.netomi.chat.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.netomi.chat.R
import com.netomi.chat.utils.NCWAppConstant

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_full_screen_image)

        val imageView: ImageView = findViewById(R.id.fullScreenImageView)
        val ivBack: ImageView = findViewById(R.id.ivBack)
        val imageUrl = intent.getStringExtra(NCWAppConstant.ARG_IMAGE_URL)
        Glide.with(this).load(imageUrl).into(imageView)

        ivBack.setOnClickListener {
                 finish()
        }
    }
}