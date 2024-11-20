package com.netomi.chat.ui.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.netomi.chat.R
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials
import com.netomi.chat.model.messages.Attachment
import com.netomi.chat.model.messages.GenericChannelResponse
import com.netomi.chat.model.messages.MessagePayload
import com.netomi.chat.model.messages.QuickReply
import com.netomi.chat.model.messages.QuickReplyOption
import com.netomi.chat.model.messages.RequestBody
import com.netomi.chat.model.messages.WebhookPayload
import com.netomi.chat.model.mqtt.Credentials
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.ui.viewmodel.NCWAwsCredentialsViewModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.ChatActionCallback
import com.netomi.chat.utils.NCWAppConstant.ARG_IMAGE_URL
import com.netomi.chat.utils.NCWAppConstant.BOT
import com.netomi.chat.utils.NCWAppConstant.BOT_REFERENCE_ID
import com.netomi.chat.utils.NCWAppConstant.CHAT_WIDGET
import com.netomi.chat.utils.NCWAppConstant.INITIAL
import com.netomi.chat.utils.NCWAppConstant.USER
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.Routes
import com.netomi.chat.utils.State
import com.netomi.chat.utils.ThemeUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

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
class NCWChatActivity : AppCompatActivity(), ChatActionCallback {

    private val chatViewModel: NCWChatViewModel by viewModels()
    private val ncwAwsCredentialsViewModel: NCWAwsCredentialsViewModel by viewModels()

    private lateinit var messageInputField: EditText
    private lateinit var sendMessageIcon: ImageView
    private lateinit var logoIcon: ImageView
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

    private var conversationID : String? = null
    private var botRefId : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize views
        initViews()
        // Load theme and config
        themeData = ThemeUtils.getThemeData()
        ncwSdkConfig = NCWChatSdk.getConfig()

        // Set up message adapter and recycler view
        setupMessageList()

        applyTheme(themeData)
        observeChatMessages()
        loadInitialMessages()

        botRefId = intent.getStringExtra(BOT_REFERENCE_ID)
        chatViewModel.getConversationId(botRefId)

        sendMessageIcon.setOnClickListener {
            if (!NCWAppUtils.isNetworkAvailable(this)) {
                NCWAppUtils.showToast(this, "Please check your network and try again.")
                return@setOnClickListener
            }
            sendMessage()
        }

        attachmentIcon.setOnClickListener { requestPermissionsAndShowMediaOptions() }
        closeIcon.setOnClickListener { finish() }

    }

    /**
     * Sends a user message in the chat.
     * Triggered when the user presses the send icon. Retrieves the current input
     * from `messageInputField`, clears the input field, and posts the message.
     */
    private fun sendMessage() {
        val messageContent = messageInputField.text.toString()
        if (messageContent.isNotEmpty()) {
            val payload = createPayload(messageContent, messageContent)
            checkForPreviousQuickReply()
            chatViewModel.sendMessage(messageContent)
            chatViewModel.sendMessageAPI(payload)
            messageInputField.text.clear()
        }
    }

     /**
     * Checks the last message in the list to determine if it has visible quick replies.
     * If quick replies are visible, it updates the visibility flag to `false`.
     */
    private fun checkForPreviousQuickReply() {
        val lastIndex = messageList.lastIndex
        if (lastIndex >= 0) {
            val lastMessage = messageList[lastIndex]
            if (lastMessage.isQuickReplyVisible && !lastMessage.quickReply?.options.isNullOrEmpty()) {
                lastMessage.isQuickReplyVisible = false
            }
        }
    }

    /**
     * Creates a WebhookPayload for sending messages.
     *
     * @param messageContent The content of the message to be sent.
     * @param label Optional label for the message, default is null.
     * @return The constructed WebhookPayload.
     */
    private fun createPayload(messageContent: String, label: String? = null): WebhookPayload {
        val messageId = UUID.randomUUID().toString()
        return WebhookPayload(
            botRefId = botRefId,
            requestBody = RequestBody(
                conversationId = conversationID,
                messagePayload = MessagePayload(
                    text = messageContent,
                    label = label,
                    messageId = messageId
                )
            )
        )
    }



    /**
     * This function adds a predefined message to the chat, such as the bot’s
     * initial greeting or information, allowing the user to see context when
     * they first open the chat.
     */
    private fun loadInitialMessages() {
        // Add the initial bot message
        themeData?.initialFlows?.header?.let { header ->
            messageList.add(
                NCWMessage(
                    sender = "BOT",
                    type = MessageType.TEXT,
                    message = header,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Add quick reply options if available
        val flows = themeData?.initialFlows?.flows.orEmpty()
        if (flows.isNotEmpty()) {
            val options = flows.map { initialData ->
                QuickReplyOption().apply {
                    label = initialData.label
                    metadata = initialData.name
                }
            }

            messageList.add(
                NCWMessage(
                    sender = BOT,
                    timestamp = System.currentTimeMillis(),
                    quickReply = QuickReply(options = ArrayList(options))
                )
            )
        }

        messageAdapter.notifyDataSetChanged()
    }

    /**
     * Applies theme styling to the chat UI based on the provided theme data.
     * @param themeData The theme settings to be applied, containing gradient
     * configuration, colors, and title.
     */
    private fun applyTheme(themeData: ThemeResponse?) {
        themeData?.let { theme ->
            // Apply gradient or default color to headerContainer
            if (theme.theme?.gradient == true) {
                // Set status bar to transparent to allow gradient visibility
                window.apply {
                    statusBarColor = Color.TRANSPARENT
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                val gradientDrawable = ThemeUtils.createGradientDrawable(theme)
                headerContainer.background = gradientDrawable
                val rootLayout = findViewById<View>(R.id.rootLayout)
                rootLayout.background = gradientDrawable
            } else {
                theme.theme?.color?.takeIf { it.isNotEmpty() }?.let { color ->
                    window.statusBarColor = Color.parseColor(color)
                } ?: run {
                    window.statusBarColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
                }
                ThemeUtils.applyTheme(headerContainer)
            }
            ThemeUtils.applyTheme(headerTextView)
            headerTextView.text = theme.title

         /*  // Example usage for attachmentIcon (without rounded background) and sendMessageIcon (with circular background)
            theme.theme?.color?.takeIf { it.isNotEmpty() }?.let { color ->
                Log.e("Enterrr","Enter herer")
                ThemeUtils.applyBackgroundAndTint(sendMessageIcon, color, isCircularBackground = true)
                ThemeUtils.applyBackgroundAndTint(attachmentIcon,
                    color, isCircularBackground = false)
            }
*/


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
        logoIcon = findViewById(R.id.ivLogo)

        messageInputField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                chatRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)
            }
        }
    }

    /**
     * Sets up the message list in the chat UI by initializing the adapter and layout manager.
     */
    private fun setupMessageList() {
        messageList = mutableListOf()
        messageAdapter = ChatAdapter(messageList, themeData, this)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter
    }
    override fun onQuickReply(option: QuickReplyOption?, position: Int) {
        messageList[position].isQuickReplyVisible = false
     //   messageList.removeAt(position)
        onQuickReplyClicked(option)

    }

    override fun onImageClick(imageUrl: String) {
        onFullScreenView(imageUrl)
    }

    private fun onFullScreenView(imageUrl: String) {
        val intent = Intent(this, FullScreenImageActivity::class.java).apply {
            putExtra(ARG_IMAGE_URL, imageUrl)
        }
        startActivity(intent)


    }

    /**
     * Handles quick reply option click by sending a message based on the option selected.
     *
     * @param option The selected quick reply option.
     */
    private fun onQuickReplyClicked(option: QuickReplyOption?) {
        option?.label?.takeIf { it.isNotEmpty() }?.let { label ->
            val payload = option.metadata?.let { createPayload(it, label) }
            chatViewModel.sendMessage(label)
            if (payload != null) {
                chatViewModel.sendMessageAPI(payload)
            }
            messageInputField.text.clear()
        }

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
        // Observe the chat messages LiveData from the ViewModel
        chatViewModel.sendMessage.observe(this, Observer { messages ->
            // handleApiCallback(messages as State<NCWBaseResponse<Any>>)
            handleApiCallback(messages as State<Any>)
        })

        chatViewModel.getConversationId.observe(this, Observer { conversationId ->
            handleApiCallback(conversationId as State<Any>)
        })

        chatViewModel.getAWSMQTTCredentials.observe(this) { credentials ->
            handleApiCallback(credentials as State<Any>)
        }

        chatViewModel.sendMessages.observe(this, Observer { message ->
            updateMessageList(message)
        })

        chatViewModel.awsMessage.observe(this, Observer { jsonMessage ->
            try {
                val response = Gson().fromJson(jsonMessage, GenericChannelResponse::class.java)
                val newMessages =
                    response.attachments?.mapNotNull { mapAttachmentToMessage(it) } ?: emptyList()

                if (newMessages.isNotEmpty()) {
                    newMessages.forEachIndexed { index, message ->
                        message.isSameTimeMessage = index == 0
                    }
                    updateMessageList(newMessages)
                }

            } catch (e: Exception) {
                Log.e("ParsingError", "Failed to parse JSON: ${e.localizedMessage}")
            }
        })
    }

        // Helper function to map attachments to NCWMessage
        private fun mapAttachmentToMessage(attachment: Attachment): NCWMessage? {
            val attach = attachment.attachment ?: return null
            val messageType = attach.type?.let { MessageType.fromTypeName(it) } ?: return null

            return NCWMessage(
                sender = "BOT",
                type = messageType,
                timestamp = attach.timestamp ?: System.currentTimeMillis(),
                message = attach.text,
                carouselItems = if (messageType == MessageType.CAROUSEL) attach.elements else null,
                thumbnailUrl = if (messageType == MessageType.VIDEO) attach.thumbnailUrl else null,
                title = if (messageType == MessageType.CARD) attach.title else null,
                buttons = if (messageType == MessageType.CARD) attach.buttons else arrayListOf(),
                largeImageUrl = if (messageType == MessageType.IMAGE) attach.largeImageUrl else null,
                quickReply = attach.quickReply
            )
        }



    // Helper function to update the adapter and scroll to the latest message
    private fun updateMessageList(newMessages: List<NCWMessage>) {
        messageList.addAll(newMessages)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size - 1)
    }

    // Overloaded helper function for a single message
    private fun updateMessageList(newMessage: NCWMessage) {
        messageList.add(newMessage)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size - 1)
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
                   // addMediaMessage(selectedMediaUri, MessageType.VIDEO)
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
                val response = apiResponse as GetConversationIdResponse
                if (response != null) {
                    // Use conversationID as needed
                    conversationID=response.conversationID
                    chatViewModel.getAWSMQTTCredentials(botRefId)
                    Log.d(
                        "ConversationID",
                        "Fetched conversation ID: $conversationID"
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
                saveAwsCredentials(mqttsCredentials.credentials)
            }

            Routes.ROUTE_SEND_CHAT -> {
                val sendMessageResponse = apiResponse as SendMessageResponse
                Log.d(
                    "MQTTCredentialsResponse",
                    "Fetched MQTTCredentialsResponse: ${sendMessageResponse}"
                )
            }


        }

    }

    /**
     * Save Aws Credentials in data store
     */
    private fun saveAwsCredentials(mqttsCredentials: Credentials) {
        // Save new credentials (example usage)
        val newCredentials = NCWAwsCredentials(
            accessKey = mqttsCredentials.accessKeyId,
            secretKey = mqttsCredentials.secretAccessKey,
            sessionToken = mqttsCredentials.SessionToken,
            iotEndpoint = mqttsCredentials.IoTHostEndPoint
        )
        ncwAwsCredentialsViewModel.saveAwsCredentials(newCredentials)

        val topic = "$CHAT_WIDGET/$botRefId/$conversationID"
        ncwAwsCredentialsViewModel.initializeAwsIotManager(chatViewModel, topic)

    }
}