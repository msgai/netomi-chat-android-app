package com.netomi.chat.ui.view

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils

class FullScreenMediaActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView
    private lateinit var ivBack: ImageView
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_full_screen_image)

        // Initialize views
        imageView = findViewById(R.id.fullScreenImageView)
        videoView = findViewById(R.id.videoView)
        ivBack = findViewById(R.id.ivBack)
        progressBar = findViewById(R.id.progressBar)
        webView = findViewById(R.id.webView)

        // Get media data
        val mediaUrl = intent.getStringExtra(NCWAppConstant.ARG_IMAGE_URL)
        val mediaType = intent.getStringExtra(NCWAppConstant.MEDIA_TYPE)
        imageView.visibility = View.GONE
        videoView.visibility = View.GONE

        // Handle media based on type
        if (!mediaUrl.isNullOrEmpty() && !mediaType.isNullOrEmpty()) {
            when (mediaType) {
                MessageType.IMAGE.name -> {
                    webView.visibility = View.VISIBLE
                    webView.settings.javaScriptEnabled = true
                    // webView.loadUrl(mediaUrl)
                    val imageHtml = """
    <html>
        <body style="margin:0;padding:0;">
            <img src="$mediaUrl" style="width:100%;height:auto;" />
        </body>
    </html>
"""
                    webView.loadData(imageHtml, "text/html", "UTF-8")
                    //    Glide.with(this).load(mediaUrl).into(imageView)
                }

                MessageType.VIDEO.name -> {

                    webView.visibility = View.VISIBLE
                    //  setupVideoPlayer(mediaUrl)


                    val videoHtml = """
    <html>
        <body style="margin:0;padding:0;">
            <video controls style="width:100%;height:auto;">
                <source src="$mediaUrl" type="video/mp4" />
                Your browser does not support the video tag.
            </video>
        </body>
    </html>
"""
                    webView.loadData(videoHtml, "text/html", "UTF-8")
                }

                MessageType.FILE.name -> {
                    webView.visibility = View.VISIBLE
                    webView.settings.javaScriptEnabled = true
                    webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$mediaUrl")

                }

                else -> {
                    NCWAppUtils.showToast(this, "Unsupported media type!")
                }
            }
        } else {
            NCWAppUtils.showToast(this, "Media data is missing!")
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
            NCWAppUtils.showToast(this, "Error playing video (Error code: $what, $extra)")
            true
        }

        videoView.setOnCompletionListener {
            // Optional: Handle completion, e.g., replay or exit
            videoView.start() // Restart for looping effect
        }
    }


}
