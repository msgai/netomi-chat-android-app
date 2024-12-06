package com.netomi.chat.ui.view

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
        initializeViews()

        // Set WebViewClient only once in onCreate method
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE  // Hide the progress bar when page finishes loading
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                NCWAppUtils.showToast(this@FullScreenMediaActivity,"Error loading file!")
                progressBar.visibility = View.GONE  // Hide progress bar on error
            }
        }

        // Get intent data
        val mediaType = intent.getStringExtra(NCWAppConstant.MEDIA_TYPE)
        val mediaUrl = intent.getStringExtra(NCWAppConstant.ARG_MEDIA_URL)
        val fileUri = intent.getStringExtra(NCWAppConstant.ARG_FILE_URI)

        // Handle media display
        when {
            mediaUrl != null -> handleMedia(mediaUrl, mediaType)
            fileUri != null -> handleMediaFileUri(fileUri, mediaType)
            else -> {
                NCWAppUtils.showToast(this,"Media data is missing!")
                finish()
            }
        }
    }

    private fun initializeViews() {
        imageView = findViewById(R.id.fullScreenImageView)
        videoView = findViewById(R.id.videoView)
        ivBack = findViewById(R.id.ivBack)
        progressBar = findViewById(R.id.progressBar)
        webView = findViewById(R.id.webView)

        ivBack.setOnClickListener { finish() }
        imageView.visibility = View.GONE
        videoView.visibility = View.GONE
        webView.visibility = View.GONE
    }

    private fun handleMedia(mediaUrl: String, mediaType: String?) {
        progressBar.visibility = View.VISIBLE
        when (mediaType) {
            MessageType.IMAGE.name -> showImage(mediaUrl)
            MessageType.VIDEO.name -> showVideo(mediaUrl)
            MessageType.FILE.name -> showFileInWebView(mediaUrl)
            else -> showUnsupportedMediaType()
        }
    }

    private fun handleMediaFileUri(fileUri: String, mediaType: String?) {
        progressBar.visibility = View.VISIBLE
        val uri = Uri.parse(fileUri)
        Log.e("WebView","sssxxc"+fileUri)
        when (mediaType) {
            MessageType.IMAGE.name -> showImageURI(fileUri)
            MessageType.VIDEO.name -> showVideo(uri.toString())
            MessageType.FILE.name -> showFileInWebView(uri.toString())
            else -> showUnsupportedMediaType()
        }
    }

    private fun showImageURI(imageUri: String) {
        val uri = Uri.parse(imageUri)
        val filePath = getRealPathFromURI(uri)

        filePath?.let {
            webView.apply {
                visibility = View.VISIBLE
                loadHtmlContent("<img src=\"file://$filePath\" style=\"width:100%;height:auto;\" />")
            }
        } ?: run {
            NCWAppUtils.showToast(this@FullScreenMediaActivity,"Unable to load image")
        }
        progressBar.visibility = View.GONE
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = cursor.getString(columnIndex)
            }
        }
        return path
    }

    private fun showImage(imageUrl: String) {
        webView.apply {
            visibility = View.VISIBLE
            loadHtmlContent("<img src=\"$imageUrl\" style=\"width:100%;height:auto;\" />")
        }
    }

    private fun showVideo(videoUrl: String) {
        webView.apply {
            visibility = View.VISIBLE
            loadHtmlContent("""
                <video controls style="width:100%;height:auto;">
                    <source src="$videoUrl" type="video/mp4" />
                    Your browser does not support the video tag.
                </video>
            """.trimIndent())
        }
    }

    private fun showFileInWebView(fileUrl: String) {
        webView.apply {
            visibility = View.VISIBLE
            loadUrl("https://docs.google.com/gview?embedded=true&url=$fileUrl")
        }
    }

    private fun showUnsupportedMediaType() {
        NCWAppUtils.showToast(this,"Unsupported media type!")
        progressBar.visibility = View.GONE
    }

    private fun WebView.loadHtmlContent(content: String) {
        Log.e("WebView","assas "+content)
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        loadData(content, "text/html", "UTF-8")
    }
}
