package com.netomi.chat.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils

class FullScreenMediaActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView
    private lateinit var ivBack: ImageView

    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_full_screen_image)

        // Initialize views
        imageView = findViewById(R.id.fullScreenImageView)
        videoView = findViewById(R.id.videoView)
        ivBack = findViewById(R.id.ivBack)
         progressBar = findViewById(R.id.progressBar)

        // Get media data
        val mediaUrl = intent.getStringExtra(NCWAppConstant.ARG_IMAGE_URL)
        val mediaType = intent.getStringExtra(NCWAppConstant.MEDIA_TYPE)

        // Handle media based on type
        if (!mediaUrl.isNullOrEmpty() && !mediaType.isNullOrEmpty()) {
            when (mediaType) {
                MessageType.IMAGE.name -> {
                    imageView.visibility = View.VISIBLE
                    videoView.visibility = View.GONE
                    Glide.with(this)
                        .load(mediaUrl)
                        .into(imageView)
                }
                MessageType.VIDEO.name-> {
                    imageView.visibility = View.GONE
                    videoView.visibility = View.VISIBLE
                    setupVideoPlayer(mediaUrl)
                }
                else -> {
                    NCWAppUtils.showToast(this,"Unsupported media type!")
                }
            }
        } else {
            NCWAppUtils.showToast(this,"Media data is missing!")
        }

        // Back button action
        ivBack.setOnClickListener { finish() }
    }

    /**
     * Configures the VideoView to play the video.
     */
    private fun setupVideoPlayer(videoUrl: String) {
        progressBar.visibility = View.VISIBLE
        // Set video URL
        videoView.setVideoPath(videoUrl)

        // Add listeners for playback
        videoView.setOnPreparedListener {
            it.isLooping = true // Optional: Loop the video
            videoView.start() // Auto-start playback when prepared
            progressBar.visibility = View.GONE
        }

        videoView.setOnErrorListener { _, what, extra ->
            progressBar.visibility = View.GONE
            NCWAppUtils.showToast(this,"Error playing video (Error code: $what, $extra)")
            true
        }

        videoView.setOnCompletionListener {
            // Optional: Handle completion, e.g., replay or exit
            videoView.start() // Restart for looping effect
        }
    }


}
