package com.netomi.chat.ui.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.config.NCWChatSdk
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.Routes
import com.netomi.chat.utils.State
import com.netomi.chat.utils.ThemeUtils
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
    private lateinit var sendButton: ImageView

    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messageList: MutableList<NCWMessage>
    private lateinit var recyclerView: RecyclerView
    private lateinit var uploadButton: ImageView
    private var photoUri: Uri? = null
    private lateinit var headerView: ConstraintLayout

    private lateinit var sendButton: Button
    private var ncwSdkConfig:NCWSdkConfig?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        headerView = findViewById(R.id.header)
        inputField = findViewById(R.id.inputField)
        sendButton = findViewById(R.id.imgSend)
        recyclerView = findViewById(R.id.recyclerView)
        uploadButton = findViewById(R.id.imgAttachment)

        // Initialize the message list and adapter
        messageList = mutableListOf()
        messageAdapter = ChatAdapter(messageList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter

        val botRef=intent.getStringExtra("botRefId")
     //   chatViewModel.getConversationId(botRef)

        val  themeData=NCWChatSdk.getThemeData()
        if (themeData != null) {
            if (themeData.theme?.gradient == true) {
                val directionIndex = themeData.theme.gradientDirection.coerceIn(0, GradientDrawable.Orientation.values().size - 1)

                val gradient = GradientDrawable(
                    GradientDrawable.Orientation.values()[directionIndex],
                    themeData.theme?.gradientColors?.map { Color.parseColor(it) }?.toIntArray() ?: null
                )
                headerView.background = gradient
            }
            else{
                ThemeUtils.applyTheme(themeResponse = themeData, headerView)
            }
        }

        ncwSdkConfig= NCWChatSdk.getConfig()
        applyConfig()



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

       // chatViewModel.getAppConfig()

         getDummyChat()





    }


    private fun getDummyChat() {
        messageList.add(NCWMessage(
            sender = "User",
            type = MessageType.TEXT,
            message = "Hello!",
            timestamp = System.currentTimeMillis(),
        ))
        messageList.add(NCWMessage( sender = "BOT", type = MessageType.TEXT, message = "Hi, how are you?", timestamp = System.currentTimeMillis()))
        messageAdapter.notifyDataSetChanged()
    }

    private fun applyConfig() {
        ncwSdkConfig?.let {
            val sendButtonStyle= it.sendButtonStyle
            sendButton.setBackgroundColor(sendButtonStyle.backgroundColor)
            sendButton.setTextColor(sendButtonStyle.textColor)
            sendButton.textSize = sendButtonStyle.fontSize
        }
    }

    private fun observeChatMessages() {
        // Observe the chat messages LiveData from the ViewModel
        chatViewModel.chatMessages.observe(this, Observer { messages ->
           // handleApiCallback(messages as State<NCWBaseResponse<Any>>)
        })

        chatViewModel.getConversationId.observe(this, Observer { it ->
           handleApiCallback(it as State<Any>)
        })

        chatViewModel.sendMessages.observe(this, Observer { message ->
            messageList.add(message)
            messageAdapter.notifyDataSetChanged()
        })

        // Observe app configuration changes
        chatViewModel.appAppConfiguration.observe(this, Observer {

           // handleApiCallback(it as State<NCWBaseResponse<Any>>)
        })

    }

    private fun handleApiCallback(response: State<Any>) {
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
        messageList.add(newMessage)
        messageAdapter.notifyItemInserted(messageList.size - 1)
        recyclerView.scrollToPosition(messageList.size - 1) // Scroll to the latest message
    }



    private fun onApiSucess(apiResponse: Any, apiConstant: String) {

        when (apiConstant) {
            Routes.ROUTE_GET_CONVERSATION_ID -> {


                val conversationID = apiResponse as GetConversationIdResponse
                if (conversationID != null) {
                    // Use conversationID as needed
                    Log.d("ConversationID", "Fetched conversation ID: ${conversationID.conversationID}")
                } else {
                    // Handle the case where conversationID is null
                    Log.d("ConversationID", "Conversation ID is null")
                }
            }


        }

    }
}