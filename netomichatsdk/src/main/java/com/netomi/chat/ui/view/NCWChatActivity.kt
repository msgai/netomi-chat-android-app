package com.netomi.chat.ui.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.netomi.chat.R
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.Routes
import com.netomi.chat.utils.State
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Activity responsible for displaying the chat interface and handling user interactions.
 *
 * This activity is part of the Chat SDK and serves as the entry point for users to interact
 * with the chat. It uses **ViewModel** to manage UI-related data in a lifecycle-aware manner.
 *
 * ## Responsibilities:
 * Display chat messages and update the chat log in real-time.
 * Allow the user to send new messages through a UI form.
 * Observe the **`NCWChatViewModel`** for LiveData updates to keep the UI synchronized.
 *
 * ## Architecture:
 * **View Layer (Activity):** Displays the UI and handles user events.
 *
 * ## Usage:
 * This activity is intended to be launched by the host application or as part of the Chat SDK.
 *
 */
class NCWChatActivity : AppCompatActivity() {
    private val chatViewModel: NCWChatViewModel by viewModels()

    private lateinit var inputField: EditText
    private lateinit var sendButton: Button

    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messages: MutableList<NCWMessage>
    private lateinit var recyclerView: RecyclerView
    private lateinit var uploadButton: ImageView
    private var photoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        inputField = findViewById(R.id.inputField)
        sendButton = findViewById(R.id.btnSend)
        recyclerView = findViewById(R.id.recyclerView)
        uploadButton = findViewById(R.id.imgAttach)

        // Initialize the message list and adapter
        messages = mutableListOf()
        messageAdapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter


        sendButton.setOnClickListener {
            val messageContent = inputField.text.toString()
            if (messageContent.isNotEmpty()) {
                chatViewModel.sendMessage(messageContent)
                inputField.text.clear()
            }
        }

        // Set up the upload button to show options for camera or gallery
        uploadButton.setOnClickListener {

            requestPermissionsAndShowMediaOptions()
        }

        observeChatMessages()

        chatViewModel.getAppConfig()

         getDummyChat()

        val suggestions = listOf("Yes", "No", "Maybe", "More Info")
        populateSuggestionChips(suggestions)
    }
    private fun populateSuggestionChips(suggestions: List<String>) {
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupSuggestions)
        chipGroup.removeAllViews() // Clear previous chips if any

        suggestions.forEach { suggestion ->
            val chip = Chip(this).apply {
                text = suggestion
                isClickable = true
                isCheckable = false
                setChipBackgroundColorResource( android.R.color.darker_gray)
                setTextColor(ContextCompat.getColor(this@NCWChatActivity, android.R.color.white))
                setOnClickListener {
                    // Handle suggestion chip click
                    handleChipClick(suggestion)
                }
            }
            chipGroup.addView(chip)
        }
    }

    private fun handleChipClick(suggestion: String) {
        // Send suggestion text as a chat message or perform the corresponding action
    }

    private fun getDummyChat() {
        messages.add(NCWMessage(
            sender = "User",
            type = MessageType.TEXT,
            message = "Hello!",
            timestamp = System.currentTimeMillis(),
        ))
        messages.add(NCWMessage( sender = "BOT", type = MessageType.TEXT, message = "Hi, how are you?", timestamp = System.currentTimeMillis()))
        messageAdapter.notifyDataSetChanged()
    }

    private fun observeChatMessages() {
        // Observe the chat messages LiveData from the ViewModel
        chatViewModel.chatMessages.observe(this, Observer { messages ->
            handleApiCallback(messages as State<NCWBaseResponse<Any>>)
        })

        chatViewModel.sendMessages.observe(this, Observer { message ->
            messages.add(message)
            messageAdapter.notifyDataSetChanged()

        })

        // Observe app configuration changes
        chatViewModel.appAppConfiguration.observe(this, Observer {

            handleApiCallback(it as State<NCWBaseResponse<Any>>)
        })

    }
    private fun requestPermissionsAndShowMediaOptions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
            requestPermissionLauncher.launch(permissions)
        } else {
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            requestPermissionLauncher.launch(permissions)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            showMediaOptions()
        } else {
            Toast.makeText(this, "Permissions are required to upload media", Toast.LENGTH_SHORT).show()
        }
    }



    // Function to show camera and gallery options
    private fun showMediaOptions() {
        NCWAppUtils.showMediaOptionDialog(this, {
            openCamera()
        },
        {
            openGallery()
        }
        )
    }


    private fun openCamera() {
        val imageFile =createImageFile()
       photoUri = imageFile?.let {
           FileProvider.getUriForFile(this, "${applicationContext.packageName}.fileprovider",
               it
           )
       }
        cameraLauncherMain.launch(photoUri)
    }


    private val cameraLauncherMain = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            photoUri?.let { uri ->
                addMediaMessage(uri, MessageType.IMAGE)
            }
        } else {
            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }


    // Open the gallery to select an image or video
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/* video/*"
        galleryLauncher.launch(galleryIntent)
    }


    // Handle the result of the gallery selection
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val selectedMediaUri: Uri? = result.data?.data
            val type = contentResolver.getType(selectedMediaUri!!)
            if (type!!.startsWith("image/")) {
                addMediaMessage(selectedMediaUri, MessageType.IMAGE)
            } else if (type.startsWith("video/")) {
                addMediaMessage(selectedMediaUri, MessageType.VIDEO)
            }
        }
    }

    // Add media message (image or video) to the chat
    private fun addMediaMessage(uri: Uri, type: MessageType) {
        val newMessage = NCWMessage(sender = "User",type = type, message = uri.toString(),timestamp = System.currentTimeMillis())
        messages.add(newMessage)
        messageAdapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1) // Scroll to the latest message
    }

    private fun handleApiCallback(response: State<NCWBaseResponse<Any>>) {
        when (response) {
            is State.Loading -> {
                Toast.makeText(this, "Loading..", Toast.LENGTH_SHORT).show()
            }

            is State.Success -> {
                //Toast.makeText(this, "Success..", Toast.LENGTH_SHORT).show()
                // chatLog.text=response.data.data.toString()
                onApiSucess(response.data, response.apiConstant)
            }

            is State.Error -> {
                Toast.makeText(this, "Error..", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Else..", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun onApiSucess(apiResponse: NCWBaseResponse<Any>, apiConstant: String) {

        when (apiConstant) {
            Routes.ROUTE_APP_CONFIG -> {

                apiResponse as NCWBaseResponse<AppConfigurationResponseModel>

               Log.d("Response", "ttetetee "+apiResponse.data.config)
            }


        }

    }
}