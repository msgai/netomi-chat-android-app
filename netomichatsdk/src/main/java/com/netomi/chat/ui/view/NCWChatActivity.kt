package com.netomi.chat.ui.view


import IdleTimeoutManager
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.netomi.chat.R
import com.netomi.chat.awsiot.ConnectionStatus
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.GetChatHistoryResponse
import com.netomi.chat.model.GetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.SendMessageResponse
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials
import com.netomi.chat.model.chat_history.GetChatHistoryPayload
import com.netomi.chat.model.chat_history.HistoryRequestBody
import com.netomi.chat.model.endchat.EndChatRequest
import com.netomi.chat.model.endchat.EndChatResponse
import com.netomi.chat.model.endchat.EventData
import com.netomi.chat.model.media_payload.SignedUrlPayload
import com.netomi.chat.model.messages.AdditionalAttributes
import com.netomi.chat.model.messages.Attachment
import com.netomi.chat.model.messages.AttachmentList
import com.netomi.chat.model.messages.CarouselButton
import com.netomi.chat.model.messages.GenericChannelResponse
import com.netomi.chat.model.messages.MessagePayload
import com.netomi.chat.model.messages.QuickReply
import com.netomi.chat.model.messages.QuickReplyOption
import com.netomi.chat.model.messages.RequestBody
import com.netomi.chat.model.messages.WebhookPayload
import com.netomi.chat.model.mqtt.Credentials
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.GetMediaUploadUrl
import com.netomi.chat.model.presigned_url.GetPreSignedUrl
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.ui.viewmodel.NCWAwsCredentialsViewModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.ChatActionCallback
import com.netomi.chat.utils.DeviceInfo
import com.netomi.chat.utils.DeviceInfoUtil
import com.netomi.chat.utils.DialogUtils
import com.netomi.chat.utils.FilePath
import com.netomi.chat.utils.ImageUtils
import com.netomi.chat.utils.NCWAppConstant.ARG_IMAGE_URL
import com.netomi.chat.utils.NCWAppConstant.BOT_REFERENCE_ID
import com.netomi.chat.utils.NCWAppConstant.CHAT_WIDGET
import com.netomi.chat.utils.NCWAppConstant.DATE_FORMAT
import com.netomi.chat.utils.NCWAppConstant.MEDIA_TYPE
import com.netomi.chat.utils.NCWAppConstant.TYPE_FILE
import com.netomi.chat.utils.NCWAppConstant.TYPE_IMAGE
import com.netomi.chat.utils.NCWAppConstant.TYPE_INDICATOR
import com.netomi.chat.utils.NCWAppConstant.TYPE_REQUEST
import com.netomi.chat.utils.NCWAppConstant.TYPE_RESPONSE
import com.netomi.chat.utils.NCWAppConstant.TYPE_VIDEO
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWAppUtils.isFileSizeValid
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
    private lateinit var ivMenuOption: AppCompatImageView
    private lateinit var ivMenu: ImageView
    private lateinit var connectionHeader: TextView
    private lateinit var progressBar: ProgressBar
    private var photoUri: Uri? = null
    private var ncwSdkConfig: NCWSdkConfig? = null
    private var themeData: ThemeResponse? = null

    private var conversationID: String? = null
    private var botRefId: String? = null
    private lateinit var idleTimeoutManager: IdleTimeoutManager
    private var fileSend: File? = null

    private var loaderAddedTime: Long = 0
    private var isLoaderActive: Boolean = false

    private var deviceInfo:ArrayList<DeviceInfo> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initViews()
        // Load theme and config
        themeData = ThemeUtils.getThemeData()
        ncwSdkConfig = NCWChatSdk.getConfig()

        // Set up message adapter and recycler view
        setupMessageList()

        applyTheme(themeData)
        observeChatMessages()
      //  loadInitialMessages()


        botRefId = intent.getStringExtra(BOT_REFERENCE_ID)
        val device = DeviceInfoUtil.getDeviceInfo(this)
        deviceInfo.add(device)
        Log.d("DeviceInfo", "Device Info: $deviceInfo")

       if(ThemeUtils.getConversationID()==null) {
           loadInitialMessages()
            chatViewModel.getConversationId(botRefId)
        }else
        {
            conversationID=ThemeUtils.getConversationID()
            chatViewModel.getAWSMQTTCredentials(botRefId)
            getChatHistory()
        }
        sendMessageIcon.setOnClickListener {
            if (!NCWAppUtils.isNetworkAvailable(this)) {
                NCWAppUtils.showToast(this, "Please check your network and try again.")
                return@setOnClickListener
            }
            sendMessage()
        }

        attachmentIcon.setOnClickListener {
            if (arePermissionsGranted())
                showMediaOptions()
                else
            requestPermissionsAndShowMediaOptions()


        }

        ivMenuOption.setOnClickListener{
            Toast.makeText(this,R.string.under_development,Toast.LENGTH_SHORT).show()
        }
        ivMenu.setOnClickListener {
            Toast.makeText(this,R.string.under_development,Toast.LENGTH_SHORT).show()
        }

        closeIcon.setOnClickListener {
            backClicked()
           // finish()
        }

    //

        // Initialize IdleTimeoutManager with a timeout and a callback for session timeout
        themeData?.endChat?.idleTimeout?.let {
            idleTimeoutManager = IdleTimeoutManager(
                idleTimeoutMillis = it,
                onTimeout = { handleSessionTimeout() }
            )

        }

        // Add a custom back press callback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backClicked()
            }
        })
    }

    private fun backClicked() {
        DialogUtils.showCustomDialog(
            context = this,
            title = getString(R.string.end_chat_ques),
            subtitle = getString(R.string.Are_you_sure_you_want_to_exit_from_this_chat),
            yesText = getString(R.string.return_later),
            noText = getString(R.string.end_chat),
            titleColor = Color.BLACK,
            subtitleColor = Color.DKGRAY,
            backgroundColor = Color.LTGRAY,
            noButtonBackgroundColor = ThemeUtils.parseColor(themeData!!.theme!!.color),
            yesButtonBackgroundColor = Color.GRAY,
            onYesClick = {
                finish()
            },
            onNoClick = {
                hitEndChatAPI()
            }
        )

    }

    private fun hitEndChatAPI() {
        chatViewModel.hitEndChatAPI(
            EndChatRequest(
                botRefId = botRefId!!, com.netomi.chat.model.endchat.RequestBody(
                    botReferenceId = botRefId!!,
                    channelId = "NETOMI_WEB_WIDGET",
                    conversationId = conversationID!!,
                    eventData = EventData(
                        eventType = "WIDGET_EVENT",
                        subType = "CHAT_END"
                    ),
                    eventName = "INFO_PILL",
                    isPublishToMQTT = false,
                    requestType = "NETOMI",
                    timestamp = System.currentTimeMillis(),
                    triggerType = "EVENT"

                )
            )
        )
    }

    override fun onResume() {
        super.onResume()
        // Check for timeout whenever the activity is resumed
        idleTimeoutManager.checkForTimeout()
    }


    override fun onUserInteraction() {
        super.onUserInteraction()
        // Update last active time on any interaction
        idleTimeoutManager.updateLastActiveTime()
        // Check if session has timed out
        idleTimeoutManager.checkForTimeout()
    }

    /**
     * Handles the session timeout logic.
     */
    private fun handleSessionTimeout() {
        // Show a timeout dialog or perform other actions
        AlertDialog.Builder(this)
            .setTitle("Session Timeout")
            .setMessage("Your session has expired due to inactivity.")
            .setPositiveButton("OK") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun getPreSignedUrl(type: String?, uploadKeyPrefix: String) {
        progressBar.visibility=View.VISIBLE
        val mediaUpload = SignedUrlPayload(
            fileType = type,
            uploadKeyPrefix = uploadKeyPrefix
        )
        chatViewModel.getPreSignedUrl(mediaUpload)
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
        idleTimeoutManager.checkForTimeout()
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
    private fun createPayload(
        messageContent: String,
        label: String? = null,
        attachmentList: ArrayList<AttachmentList>? = null
    ): WebhookPayload {
        val messageId = UUID.randomUUID().toString()

        val attributes = AdditionalAttributes().apply {
            CUSTOM_ATTRIBUTES.addAll(deviceInfo)
        }
        return WebhookPayload(
            botRefId = botRefId,
            requestBody = RequestBody(
                conversationId = conversationID,
                messagePayload = MessagePayload(
                    text = messageContent,
                    label = label,
                    messageId = messageId,

                ),
                attachmentList=attachmentList,
               additionalAttributes = attributes

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
                    sender = TYPE_RESPONSE,
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
                    sender = TYPE_RESPONSE,
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
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                val gradientDrawable = ThemeUtils.createGradientDrawable(theme)
                headerContainer.background = gradientDrawable
                val rootLayout = findViewById<View>(R.id.rootLayout)
                rootLayout.background = gradientDrawable
            } else {
                theme.theme?.color?.takeIf { it.isNotEmpty() }?.let { color ->
                    window.statusBarColor = Color.parseColor(color)
                } ?: run {
                    window.statusBarColor =
                        ContextCompat.getColor(this, android.R.color.holo_blue_dark)
                }
                ThemeUtils.applyTheme(headerContainer)
            }
            //ThemeUtils.applyTheme(headerTextView)
            headerTextView.text = theme.title

            attachmentIcon.visibility = if (themeData.fileSharing?.isEnabled == true) {
                View.VISIBLE
            } else {
                View.GONE
            }


            /*  // Example usage for attachmentIcon (without rounded background) and sendMessageIcon (with circular background)
               theme.theme?.color?.takeIf { it.isNotEmpty() }?.let { color ->

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
         progressBar = findViewById(R.id.progressBar)
        headerContainer = findViewById(R.id.constHeader)
        messageInputField = findViewById(R.id.edtMessage)
        sendMessageIcon = findViewById(R.id.ivSend)
        chatRecyclerView = findViewById(R.id.rvChat)
        attachmentIcon = findViewById(R.id.ivAttachment)
        headerTextView = findViewById(R.id.tvHeader)
        closeIcon = findViewById(R.id.ivClose)
        logoIcon = findViewById(R.id.ivLogo)
        ivMenuOption=findViewById(R.id.ivMenuOption)
        ivMenu=findViewById(R.id.ivMenu)
        connectionHeader=findViewById(R.id.connection_status_header)

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

    override fun onMediaClick(message: NCWMessage) {
        val mediaUrl = when (message.type) {
            MessageType.VIDEO -> message.thumbnailUrl
            MessageType.IMAGE -> message.largeImageUrl
            MessageType.FILE -> message.fileUrl
            else -> null
        }
        mediaUrl?.let {
            val intent = Intent(this, FullScreenMediaActivity::class.java).apply {
                putExtra(ARG_IMAGE_URL, mediaUrl)
                putExtra(MEDIA_TYPE, message.type.name)
            }
            startActivity(intent)
        }?:run {
            val uri=Uri.parse(message.message)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION // Grant read permission
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                NCWAppUtils.showToast(this, "No application found to open this file")
            }
        }
    }

    override fun carouselButtonAction(it: CarouselButton?) {

        Log.e("CarouselButton", "CarouselButton $it")
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
        chatViewModel.sendMessage.observe(this) { messages ->
            handleApiCallback(messages as State<Any>)
        }

        chatViewModel.getConversationId.observe(this) { conversationId ->
            handleApiCallback(conversationId as State<Any>)
        }

        chatViewModel.getAWSMQTTCredentials.observe(this) { credentials ->
            handleApiCallback(credentials as State<Any>)
        }

        chatViewModel.sendMessages.observe(this) { message ->
            updateMessageList(message)
        }

        chatViewModel.getChatHistory.observe(this) { messages ->
            handleApiCallback(messages as State<Any>)

        }
        chatViewModel.getSignedUrl.observe(this) { signedUrl ->
            handleApiCallback(signedUrl as State<Any>)

        }
        chatViewModel.getUploadedMediaUrl.observe(this) { media ->
            handleApiCallback(media as State<Any>)

        }




        chatViewModel.awsMessage.observe(this) { jsonMessage ->
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
        }


        ncwAwsCredentialsViewModel.connectionStatus.observe(this) { status ->
            when (status) {
                ConnectionStatus.CONNECTING.toString() -> {
                    connectionHeader.text = getString(R.string.connecting)
                    connectionHeader.setBackgroundColor(Color.YELLOW)
                    connectionHeader.setTextColor(Color.BLACK)
                    connectionHeader.visibility = View.VISIBLE
                }

                ConnectionStatus.CONNECTED.toString() -> {
                    connectionHeader.text = getString(R.string.connected)
                    connectionHeader.setBackgroundColor(Color.GREEN)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE

                    // Hide header after 2 seconds when connected
                    connectionHeader.postDelayed({
                        connectionHeader.visibility = View.GONE
                    }, 2000)
                }

                ConnectionStatus.CONNECTION_LOST.toString() -> {
                    connectionHeader.text = getString(R.string.connection_lost)
                    connectionHeader.setBackgroundColor(Color.RED)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE
                }

                ConnectionStatus.RE_CONNECTED.toString() -> {
                    connectionHeader.text = getString(R.string.reconnecting)
                    connectionHeader.setBackgroundColor(Color.BLUE)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE
                }

                else -> {
                    // Unknown or other status
                    connectionHeader.text = getString(R.string.unknown)
                    connectionHeader.setBackgroundColor(Color.GRAY)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE
                }
            }
        }

        chatViewModel.endChatResponse.observe(this) { messages ->
            handleApiCallback(messages as State<Any>)
        }
    }

        // Helper function to map attachments to NCWMessage
        private fun mapAttachmentToMessage(attachment: Attachment): NCWMessage? {
            val attach = attachment.attachment ?: return null
            val messageType = attach.type?.let { MessageType.fromTypeName(it) } ?: return null

            return NCWMessage(
                sender = TYPE_RESPONSE,
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

    // Overloaded helper function for a single message
    private fun updateMessageList(newMessage: NCWMessage) {
        messageList.add(newMessage)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size)
        addLoader()
    }

    private fun updateMessageList(newMessages: List<NCWMessage>) {
        val typingIndicatorEnabled = themeData?.typingIndicator?.enabled ?: false

        if (!typingIndicatorEnabled) {
            addMessages(newMessages)
            return
        }

            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - loaderAddedTime

            // Ensure loader remains visible for at least minTime
            val minTime = themeData?.typingIndicator?.minTime ?: 1000L
            if (isLoaderActive && elapsedTime < minTime) {
                Log.e("CallActive", "Waiting for minTime: $minTime")
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isLoaderActive) { // Double-check if loader is still active
                        safelyRemoveLoader(newMessages)
                        Log.e("CallActive", "Removed after minTime")
                    }
                }, minTime - elapsedTime)
            } else if (isLoaderActive) { // Ensure loader is active before removing
                Log.e("CallActive", "Removed loader immediately")
                safelyRemoveLoader(newMessages)
            } else {
                Log.e("CallActive", "Loader already removed, updating messages only")
                addMessages(newMessages)
            }

    }
    // Helper function to add messages and scroll to the latest
    private fun addMessages(newMessages: List<NCWMessage>) {
        messageList.addAll(newMessages)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size - 1)
    }

    private fun safelyRemoveLoader(newMessages: List<NCWMessage>) {
        if (!isLoaderActive) return // Prevent redundant calls
        removeLoader()
        messageList.addAll(newMessages)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.post {
            chatRecyclerView.scrollToPosition(messageList.size - 1)
        }
    }

    private fun removeLoader() {
        isLoaderActive = false
        messageList.removeAll { it.sender == TYPE_INDICATOR }

    }

    private fun addLoader() {
        if (!themeData?.typingIndicator?.enabled!!) return

        loaderAddedTime = System.currentTimeMillis()
        isLoaderActive = true

        messageList.add(
            NCWMessage(
                sender = TYPE_INDICATOR,
                type = MessageType.TEXT,
                timestamp = loaderAddedTime
            )
        )

        // Schedule maxTime check
        themeData?.typingIndicator?.maxTime?.let {
            Handler(Looper.getMainLooper()).postDelayed({
                if (isLoaderActive) {
                    Log.e("CalllActive","Time Passes")
                    removeLoader()
                    messageAdapter.notifyDataSetChanged()
                }
            }, it)
        }



    }

    private fun handleApiCallback(response: State<Any>) {
        when (response) {
            is State.Loading -> {
                //Toast.makeText(this, "Loading..", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.VISIBLE
            }

            is State.Success -> {
                onApiSuccess(response.data, response.apiConstant)
            }

            is State.Error -> {
                Toast.makeText(this, "Error..", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }

            else -> {
                Toast.makeText(this, "Else..", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun requestPermissionsAndShowMediaOptions() {
        val permissions: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        requestPermissionLauncher.launch(permissions)
    }

    private fun arePermissionsGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
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

        val bottomSheet = MediaOptionsBottomSheet(
            onCameraClick = { openCamera() },
            onGalleryClick = { openGallery() },
            onFileClick = { openFile() }
        )
        bottomSheet.show(supportFragmentManager, "MediaOptionsBottomSheet")
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

                fileSend= photoUri?.let { NCWAppUtils.getFileFromUri(this, it) }

                if (!isFileSizeValid(this,fileSend?.length(), themeData?.fileSharing?.fileSize)) {
                    return@registerForActivityResult
                }
                photoUri?.let { uri ->
                    addMediaMessage(uri, MessageType.IMAGE)
                }

                val type= photoUri?.let { fileSend?.let { it1 -> NCWAppUtils.getFileContentType(it1) } }
                fileSend?.let {
                        getPreSignedUrl(type, it.path)

                }
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }


    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat(DATE_FORMAT).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    // Common function to validate file size and type
    private fun validateFile(file: File?, mimeType: String?): Boolean {
        // Validate file size
        if (!isFileSizeValid(this, file?.length(), themeData?.fileSharing?.fileSize)) {
            return false
        }

        // Validate file type
        val supportedExtensions = themeData?.fileSharing?.list ?: emptyList()
        val fileExtension = file?.extension?.lowercase()?.let { if (!it.startsWith(".")) ".$it" else it }

        if (fileExtension !in supportedExtensions) {
            NCWAppUtils.showToast(
                this,
                "Unsupported file type selected. Supported types: ${supportedExtensions.joinToString(", ")}"
            )
            return false
        }

        return true
    }

    // Common function to handle file result
    private fun handleFileSelection(resultData: Intent?, mimeType: String?, isGallery: Boolean = false) {
        val selectedMediaUri: Uri? = resultData?.data
        if (selectedMediaUri == null) {
            Log.e("FileSelection", "No media selected")
            return
        }

        // Get the file from the URI
       fileSend = if (isGallery) {
           FilePath().getPath(this, selectedMediaUri)?.let { File(it) }
       } else {
           ImageUtils.getFileFromUri(this, selectedMediaUri)
        }

        if (fileSend == null) {
            Log.e("FileSelection", "Failed to get file from URI")
            return
        }

        Log.e("FileSelection", "Type: $mimeType, File path: ${fileSend?.absolutePath}")

        // Validate file size and type
        if (!validateFile(fileSend, mimeType)) return

        checkForPreviousQuickReply()

        when {
            mimeType == null -> Log.e("FileSelection", "Unknown MIME type")
            mimeType.startsWith("image/") -> addMediaMessage(selectedMediaUri, MessageType.IMAGE)
            mimeType.startsWith("video/") -> addMediaMessage(selectedMediaUri, MessageType.VIDEO)
            else -> addDocMessage(fileSend, selectedMediaUri, MessageType.FILE)
        }

        fileSend?.let {
            getPreSignedUrl(mimeType, it.path)

        }
    }

    // Gallery selection
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/* video/*"
        }
        galleryLauncher.launch(galleryIntent)
    }

    // File selection (PDF, DOC, etc.)
    private fun openFile() {
        val fileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        fileLauncher.launch(fileIntent)
    }

    // Handle the result of the gallery selection
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val type = contentResolver.getType(result.data?.data!!)
                handleFileSelection(result.data, type, isGallery = true)
            }
        }

    // Handle the result of file selection (PDF, DOC, etc.)
    private val fileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val type = contentResolver.getType(result.data?.data!!)
                handleFileSelection(result.data, type)
            }
        }

    // Function to add document message

    private fun addDocMessage(file: File?, selectedMediaUri: Uri, type: MessageType) {
        val newMessage = NCWMessage(
            sender = TYPE_REQUEST,
            type = type,
            title = file?.name,
            message = selectedMediaUri.toString(),
            timestamp = System.currentTimeMillis(),
            fileSize = fileSend?.length().toString()
        )
        messageList.add(newMessage)
//        messageAdapter.notifyItemInserted(messageList.size - 1)
//        chatRecyclerView.scrollToPosition(messageList.size - 1) // Scroll to the latest message

        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size)
        addLoader()
    }

    // Add media message (image or video) to the chat
    private fun addMediaMessage(uri: Uri, type: MessageType) {
        val newMessage = NCWMessage(
            sender = TYPE_REQUEST,
            type = type,
            message = uri.toString(),
            timestamp = System.currentTimeMillis()
        )
        messageList.add(newMessage)
//        messageAdapter.notifyItemInserted(messageList.size - 1)
//        chatRecyclerView.scrollToPosition(messageList.size - 1) // Scroll to the latest message

        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size)
        addLoader()
    }


    private fun onApiSuccess(apiResponse: Any, apiConstant: String) {

        when (apiConstant) {
            Routes.ROUTE_GET_CONVERSATION_ID -> {
                val response = apiResponse as GetConversationIdResponse
                // Use conversationID as needed
                conversationID=response.conversationID
                chatViewModel.getAWSMQTTCredentials(botRefId)
                conversationID?.let { ThemeUtils.setConversationID(it) }
                Log.d(
                    "ConversationID",
                    "Fetched conversation ID: $conversationID"
                )

            }

            Routes.ROUTE_GET_MQTT_CREDENTIALS -> {
                val mqttsCredentials = apiResponse as MQTTCredentialsResponse
                saveAwsCredentials(mqttsCredentials.credentials)
            }

            Routes.ROUTE_SEND_CHAT -> {
                val sendMessageResponse = apiResponse as SendMessageResponse
                progressBar.visibility=View.GONE
            }

            Routes.ROUTE_GET_CHAT -> {
                val response = apiResponse as GetChatHistoryResponse
                if (response!=null && response.responses.size>0){
                    parseHistoryItems(response.responses)
                }
                progressBar.visibility = View.GONE
            }

            Routes.ROUTE_GET_PRESIGNED_URL -> {
                val response = apiResponse as GetPreSignedUrl
                chatViewModel.uploadFile(fileSend,response)
            }

            Routes.ROUTE_UPLOAD_MEDIA -> {
                val response = apiResponse as GetMediaUploadUrl
                Log.d("ROUTE_UPLOAD_MEDIA", "Fetched ROUTE_UPLOAD_MEDIA: $response")

                val mediaType = response.type?.let { NCWAppUtils.getTypeFromContent(it) }
                Log.e("MediaType", "Determined media type: $mediaType")

                val attachmentList = arrayListOf(
                    AttachmentList().apply {
                        type = mediaType
                        actualType = mediaType
                        attachmentId= UUID.randomUUID().toString()
                        percentage = 10
                        fileType = response.type
                        title = response.title
                        fileSize = response.fileSize
                        largeImageUrl =  if (mediaType == TYPE_IMAGE) response.url else null
                        thumbnailUrl =  if (mediaType == TYPE_VIDEO) response.url else null
                        fileURL =  if (mediaType == TYPE_FILE) response.url else null
                    }
                )

                Log.e("attachmentList","attachmentList"+attachmentList)


                val payload = createPayload(
                    "event://;LEARN_ATTRIBUTE_EVENT;ATTACHMENT::value=Media has been uploaded",
                    "Attachment has been uploaded",
                    attachmentList
                )
                chatViewModel.sendMessageAPI(payload)


            }

            Routes.ROUTE_END_CHAT -> {
                ThemeUtils.setConversationID(null)
                finish()
            }
        }

    }

    private fun parseHistoryItems(responses: ArrayList<GenericChannelResponse>) {

        responses.forEach { response ->

            if (response.triggerType == TYPE_RESPONSE) {
                val newMessages =
                    response.attachments?.mapNotNull { mapAttachmentToMessage(it) } ?: emptyList()
                if (newMessages.isNotEmpty()) {
                    newMessages.forEachIndexed { index, message ->
                        message.isSameTimeMessage = index == 0
                    }
                }
                messageList.addAll(newMessages)
            }
            else {
                response.requestPayload?.attachmentList?.takeIf { it.isNotEmpty() }?.forEach { attachmentListRequest ->
                    val messageType = attachmentListRequest.type?.let { MessageType.fromTypeName(it) }

                    messageType?.let {
                        val newMessage = NCWMessage(
                            message=attachmentListRequest.title,
                            sender = TYPE_REQUEST,
                            type = it,
                            timestamp = response.timestamp ?: System.currentTimeMillis(),
                            largeImageUrl = if (it == MessageType.IMAGE) attachmentListRequest.largeImageUrl else null,
                            thumbnailUrl = if (it == MessageType.VIDEO) attachmentListRequest.thumbnailUrl else null,
                            fileUrl = if (it == MessageType.FILE) attachmentListRequest.fileURL else null,
                            fileSize = attachmentListRequest.fileSize,
                            title = attachmentListRequest.title
                        )
                        messageList.add(newMessage)
                    }
                } ?: run {
                    val newMessage = NCWMessage(
                        id = System.currentTimeMillis().toString(),
                        message = response.requestPayload?.messagePayload?.text,
                        timestamp = response.timestamp ?: System.currentTimeMillis(),
                        type = MessageType.TEXT,
                        sender = TYPE_REQUEST,
                    )
                    messageList.add(newMessage)
                }

            }
        }


        messageList.forEach { it.isQuickReplyVisible = false }
        val lastMessage = messageList.lastOrNull()
        if (lastMessage?.sender == TYPE_RESPONSE && !lastMessage.quickReply?.options.isNullOrEmpty()) {
            lastMessage.isQuickReplyVisible = true
        }

        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.scrollToPosition(messageList.size-1)


    }

    private fun getChatHistory() {
        val payload = conversationID?.let {
            botRefId?.let { it1 ->
                GetChatHistoryPayload(
                    conversationId = it,
                    requestBody = HistoryRequestBody(
                        numberOfMessages = 100,
                        numberOfDays = 10
                    ),
                    botRefId = it1
                )
            }
        }
        chatViewModel.getChatHistory(payload)
    }

    /**
     * Save Aws Credentials in data store
     */
    private fun saveAwsCredentials(credentials: Credentials) {
        // Save new credentials (example usage)
        val newCredentials = NCWAwsCredentials(
            accessKey = credentials.accessKeyId,
            secretKey = credentials.secretAccessKey,
            sessionToken = credentials.SessionToken,
            iotEndpoint = credentials.IoTHostEndPoint,
            expiresIn = credentials.expiresIn

        )
        ncwAwsCredentialsViewModel.saveAwsCredentials(newCredentials)

        val topic = "$CHAT_WIDGET/$botRefId/$conversationID"
        ncwAwsCredentialsViewModel.initializeAwsIotManager(chatViewModel, topic)

    }
}