package com.netomi.chat.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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

        handleMedia(mediaUrl,mediaType)

        // Back button action
        ivBack.setOnClickListener { finish() }
    }

    private fun handleMedia(mediaUrl: String?, mediaType: String?) {
        if (mediaUrl.isNullOrEmpty() || mediaType.isNullOrEmpty()) {
            NCWAppUtils.showToast(this, "Media data is missing!")
            return
        }

        // Show loader while the page initializes
        progressBar.visibility = View.VISIBLE

        webView.apply {
            visibility = View.VISIBLE
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE // Ensure fresh content
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // Hide loader once the content is fully loaded
                    progressBar.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    Log.e("WebViewError", "Error $errorCode: $description at $failingUrl")
                    // Retry loading in case of error
                    if (mediaType == MessageType.FILE.name) {
                        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$mediaUrl")
                    }
                }
            }
        }

        when (mediaType) {
            MessageType.IMAGE.name -> {
                val imageHtml = """
                <html>
                    <body style="margin:0;padding:0;">
                        <img src="$mediaUrl" style="width:100%;height:auto;" />
                    </body>
                </html>
            """
                webView.loadData(imageHtml, "text/html", "UTF-8")
            }

            MessageType.VIDEO.name -> {
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
               webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$mediaUrl")
            }

            else -> {
                NCWAppUtils.showToast(this, "Unsupported media type!")
                progressBar.visibility = View.GONE // Hide loader if unsupported
            }
        }
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
