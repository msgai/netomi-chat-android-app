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
import android.widget.TextView
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
import com.netomi.chat.awsiot.NCWAwsIotManager
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials
import com.netomi.chat.model.mqtt.Credentials
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.ui.viewmodel.NCWAwsCredentialsViewModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.NCWAppConstant.BOT_REFERENCE_ID
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
    private val ncwAwsCredentialsViewModel: NCWAwsCredentialsViewModel by viewModels()

    private lateinit var messageInputField: EditText
    private lateinit var sendMessageIcon: ImageView
    private lateinit var headerTextView: TextView
    private lateinit var closeIcon: ImageView
    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messageList: MutableList<NCWMessage>
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var attachmentIcon: ImageView
    private lateinit var headerContainer: ConstraintLayout

    private var photoUri: Uri? = null
    private var ncwSdkConfig: NCWSdkConfig? = null
    private var themeData: ThemeResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize views
        initViews()

        // Set up message adapter and recycler view
        setupMessageList()

        // Load theme and config
        themeData = NCWChatSdk.getThemeData()
        ncwSdkConfig = NCWChatSdk.getConfig()

        applyTheme(themeData)
        observeChatMessages()
        loadInitialMessages()

        val botRefId = intent.getStringExtra(BOT_REFERENCE_ID)
        chatViewModel.getConversationId(botRefId)
        chatViewModel.getAWSMQTTCredentials(botRefId)

        sendMessageIcon.setOnClickListener { sendMessage() }
        attachmentIcon.setOnClickListener { requestPermissionsAndShowMediaOptions() }
        closeIcon.setOnClickListener { finish() }

    }

    /**
     * Sends a user message in the chat.
     * This function is triggered when the user presses the send icon. It retrieves
     * the current input from `messageInputField`, clears the input field, and
     * posts the message to the chat system.
     */
    private fun sendMessage() {
        val messageContent = messageInputField.text.toString()
        if (messageContent.isNotEmpty()) {
            chatViewModel.sendMessage(messageContent)
            messageInputField.text.clear()
            NCWAwsIotManager.publishMessage("topicOne", messageContent)
        }
    }

    /**
     * This function adds a predefined message to the chat, such as the bot’s
     * initial greeting or information, allowing the user to see context when
     * they first open the chat.
     */
    private fun loadInitialMessages() {
        messageList.add(
            NCWMessage(
                sender = "BOT",
                type = MessageType.TEXT,
                message = themeData?.initialFlows?.header,
                timestamp = System.currentTimeMillis()
            )
        )
        messageAdapter.notifyDataSetChanged()
    }

    /**
     * Applies theme styling to the chat UI based on the provided theme data.
     * @param themeData The theme settings to be applied, containing gradient
     * configuration, colors, and title.
     */
    private fun applyTheme(themeData: ThemeResponse?) {
        themeData?.let { theme ->
            // Apply gradient or default color to header
            if (theme.theme?.gradient == true) {
                val direction = GradientDrawable.Orientation.values()
                    .getOrElse(theme.theme.gradientDirection) { GradientDrawable.Orientation.LEFT_RIGHT }
                val gradientColors =
                    theme.theme.gradientColors?.map { Color.parseColor(it) }?.toIntArray()
                headerContainer.background = GradientDrawable(direction, gradientColors)
            } else {
                ThemeUtils.applyTheme(themeResponse = theme, headerTextView)
            }
            headerTextView.text = theme.title
        }
    }

    /**
     * Initializes and binds UI components in the chat activity layout.
     */
    private fun initViews() {
        headerContainer = findViewById(R.id.constHeader)
        messageInputField = findViewById(R.id.edtMessage)
        sendMessageIcon = findViewById(R.id.ivSend)
        chatRecyclerView = findViewById(R.id.rvChat)
        attachmentIcon = findViewById(R.id.ivAttachment)
        headerTextView = findViewById(R.id.tvHeader)
        closeIcon = findViewById(R.id.ivClose)
    }

    /**
     * Sets up the message list in the chat UI by initializing the adapter and layout manager.
     */
    private fun setupMessageList() {
        messageList = mutableListOf()
        messageAdapter = ChatAdapter(messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter
    }


    /*    private fun applyConfig() {
            ncwSdkConfig?.let {
                val sendButtonStyle= it.sendButtonStyle
                sendButton.setBackgroundColor(sendButtonStyle.backgroundColor)
                sendButton.setTextColor(sendButtonStyle.textColor)
                sendButton.textSize = sendButtonStyle.fontSize
            }
        }*/

    /**
     * Observes LiveData from the ViewModel for various chat-related events.
     * This function handles different chat message states, including new messages,
     * conversation ID, AWS credentials, and other chat-related updates.
     *
     * It ensures that the UI is updated accordingly whenever a change occurs
     * in the chat data or configuration.
     */
    private fun observeChatMessages() {

        chatViewModel.chatMessages.observe(this, Observer { messages ->
            // Handle the incoming chat messages (for example, update UI or handle error states)
            // handleApiCallback(messages as State<NCWBaseResponse<Any>>)
        })

        chatViewModel.getConversationId.observe(this, Observer { conversationId ->
            handleApiCallback(conversationId as State<Any>)
        })

        chatViewModel.getAWSMQTTCredentials.observe(this) { credentials ->
            handleApiCallback(credentials as State<Any>)
        }

        chatViewModel.sendMessages.observe(this, Observer { message ->
            messageList.add(message) // Add the new message to the list
            messageAdapter.notifyDataSetChanged() // Notify the adapter to refresh the view
            chatRecyclerView.scrollToPosition(messageList.size - 1) // Scroll to the latest message
        })


        chatViewModel.awsMessage.observe(this, Observer {
            // Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


    private fun handleApiCallback(response: State<Any>) {
        when (response) {
            is State.Loading -> {
                Toast.makeText(this, "Loading..", Toast.LENGTH_SHORT).show()
            }

            is State.Success -> {
                onApiSuccess(response.data, response.apiConstant)
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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.values.all { it }
            if (allGranted) {
                showMediaOptions()
            } else {
                Toast.makeText(this, "Permissions are required to upload media", Toast.LENGTH_SHORT)
                    .show()
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
        val imageFile = createImageFile()
        photoUri = imageFile?.let {
            FileProvider.getUriForFile(
                this, "${applicationContext.packageName}.fileprovider",
                it
            )
        }
        cameraLauncherMain.launch(photoUri)
    }


    private val cameraLauncherMain =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
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
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        val newMessage = NCWMessage(
            sender = "User",
            type = type,
            message = uri.toString(),
            timestamp = System.currentTimeMillis()
        )
        messageList.add(newMessage)
        messageAdapter.notifyItemInserted(messageList.size - 1)
        chatRecyclerView.scrollToPosition(messageList.size - 1) // Scroll to the latest message
    }


    private fun onApiSuccess(apiResponse: Any, apiConstant: String) {

        when (apiConstant) {
            Routes.ROUTE_GET_CONVERSATION_ID -> {
                val conversationID = apiResponse as GetConversationIdResponse
                if (conversationID != null) {
                    // Use conversationID as needed
                    Log.d(
                        "ConversationID",
                        "Fetched conversation ID: ${conversationID.conversationID}"
                    )
                } else {
                    // Handle the case where conversationID is null
                    Log.d("ConversationID", "Conversation ID is null")
                }
            }

            Routes.ROUTE_GET_MQTT_CREDENTIALS -> {
                val mqttsCredentials = apiResponse as MQTTCredentialsResponse
                Log.d(
                    "MQTTCredentialsResponse",
                    "Fetched MQTTCredentialsResponse: ${mqttsCredentials.credentials.accessKeyId}"
                )
                SaveAwsCredentials(mqttsCredentials.credentials)
            }


        }

    }

    /**
     * Save Aws Credentials in data store
     */
    private fun SaveAwsCredentials(mqttsCredentials: Credentials) {
        // Save new credentials (example usage)
        val newCredentials = NCWAwsCredentials(
            accessKey = mqttsCredentials.accessKeyId,
            secretKey = mqttsCredentials.secretAccessKey,
            sessionToken = mqttsCredentials.SessionToken,
            iotEndpoint = mqttsCredentials.IoTHostEndPoint
        )
        ncwAwsCredentialsViewModel.saveAwsCredentials(newCredentials)
        ncwAwsCredentialsViewModel.initializeAwsIotManager(chatViewModel)

    }
}