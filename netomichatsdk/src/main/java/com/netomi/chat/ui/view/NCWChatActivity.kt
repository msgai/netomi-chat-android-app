package com.netomi.chat.ui.view


import NCWIdleTimeoutManager
import android.Manifest
import android.app.Activity
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
import com.netomi.chat.awsiot.NCWConnectionStatus
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.chat_history.NCWHistoryRequestBody
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEventData
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.NCWAdditionalAttributes
import com.netomi.chat.model.messages.NCWAttachment
import com.netomi.chat.model.messages.NCWAttachmentList
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.model.messages.NCWGenericChannelResponse
import com.netomi.chat.model.messages.NCWMessagePayload
import com.netomi.chat.model.messages.NCWQuickReply
import com.netomi.chat.model.messages.NCWQuickReplyOption
import com.netomi.chat.model.messages.NCWRequestBody
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.mqtt.NCWCredentials
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.model.theme.light_theme.NCWHeaderConfig
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.ui.viewmodel.NCWAwsCredentialsViewModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.NCWChatActionCallback
import com.netomi.chat.utils.DeviceInfo
import com.netomi.chat.utils.DeviceInfoUtil
import com.netomi.chat.utils.NCWDialogUtils
import com.netomi.chat.utils.NCWFilePath
import com.netomi.chat.utils.NCWImageUtils
import com.netomi.chat.utils.NCWAppConstant.ARG_MEDIA_URL
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
import com.netomi.chat.utils.NCWRoutes
import com.netomi.chat.utils.NCWSingleAlertDialog
import com.netomi.chat.utils.NCWState
import com.netomi.chat.utils.NCWThemeUtils
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
class NCWChatActivity : AppCompatActivity(), NCWChatActionCallback {

    private val chatViewModel: NCWChatViewModel by viewModels()
    private val ncwAwsCredentialsViewModel: NCWAwsCredentialsViewModel by viewModels()

    private lateinit var messageInputField: EditText
    private lateinit var sendMessageIcon: ImageView
    private lateinit var logoIcon: ImageView
    private lateinit var headerTextView: TextView
    private lateinit var closeIcon: ImageView
    private lateinit var messageAdapter: NCWChatAdapter
    private lateinit var messageList: MutableList<NCWMessage>
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var attachmentIcon: ImageView
    private lateinit var headerContainer: ConstraintLayout
    private lateinit var footerContainer: ConstraintLayout
    private lateinit var constProgressBar: ConstraintLayout
    private lateinit var ivMenuOption: AppCompatImageView
    private lateinit var ivMenu: ImageView
    private lateinit var connectionHeader: TextView
    private lateinit var progressBar: ProgressBar
    private var photoUri: Uri? = null
    private var ncwSdkConfig: NCWHeaderConfig? = null
    private var themeData: NCWThemeResponse? = null

    private var conversationID: String? = null
    private var botRefId: String? = null
    private lateinit var idleTimeoutManager: NCWIdleTimeoutManager
    private var fileSend: File? = null

    private var loaderAddedTime: Long = 0
    private var isLoaderActive: Boolean = false
    private lateinit var tvBrandName: TextView


    private var deviceInfo: ArrayList<DeviceInfo> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initViews()
        // Load theme and config
        themeData = NCWThemeUtils.getThemeData()

        NCWChatSdk.getUpdateHeaderConfiguration()
        NCWChatSdk.getUpdatedFooterConfiguration()
        NCWChatSdk.getUpdatedBotConfiguration()
        NCWChatSdk.getUpdatedUserConfiguration()
        NCWChatSdk.getUpdatedChatWindowConfiguration()
        NCWChatSdk.getUpdatedBubbleConfiguration()

        // Set up message adapter and recycler view
        setupMessageList()

        applyTheme(themeData)
        observeChatMessages()
        //  loadInitialMessages()


        botRefId = intent.getStringExtra(BOT_REFERENCE_ID)
        val device = DeviceInfoUtil.getDeviceInfo(this)
        deviceInfo.add(device)
        Log.d("DeviceInfo", "Device Info: $deviceInfo")

        if (NCWThemeUtils.getConversationID() == null) {
            loadInitialMessages()
            chatViewModel.getConversationId(botRefId)
        } else {
            conversationID = NCWThemeUtils.getConversationID()
            chatViewModel.getAWSMQTTCredentials(botRefId)
            getChatHistory()
        }
        sendMessageIcon.setOnClickListener {

            val messageContent = messageInputField.text.toString()
            sendMessage(messageContent)
        }

        attachmentIcon.setOnClickListener {
            if (arePermissionsGranted())
                showMediaOptions()
            else
                requestPermissionsAndShowMediaOptions()


        }

        ivMenuOption.setOnClickListener {
            Toast.makeText(this, R.string.under_development, Toast.LENGTH_SHORT).show()
        }
        ivMenu.setOnClickListener {
            Toast.makeText(this, R.string.under_development, Toast.LENGTH_SHORT).show()
        }

        closeIcon.setOnClickListener {
            if (themeData?.mobileConfig?.lightTheme?.headerConfig?.isBackPressPopupEnabed == true)
                backClicked() else finish()
        }
        // Initialize IdleTimeoutManager with a timeout and a callback for session timeout
        themeData?.endChat?.idleTimeout?.let {
            idleTimeoutManager = NCWIdleTimeoutManager(
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
        NCWDialogUtils.showCustomDialog(
            context = this,
            title = getString(R.string.end_chat_ques),
            subtitle = getString(R.string.Are_you_sure_you_want_to_exit_from_this_chat),
            yesText = getString(R.string.return_later),
            noText = getString(R.string.end_chat),
            titleColor = Color.BLACK,
            subtitleColor = Color.DKGRAY,
            backgroundColor = Color.LTGRAY,
            noButtonBackgroundColor = themeData?.mobileConfig?.lightTheme?.headerConfig?.backgroundColor?.let {
                NCWThemeUtils.parseColor(
                    it
                )
            } ?: Color.WHITE,
            yesButtonBackgroundColor = themeData?.mobileConfig?.lightTheme?.botConfig?.backgroundColor?.let {
                NCWThemeUtils.parseColor(
                    it
                )
            } ?: Color.WHITE,
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
            NCWEndChatRequest(
                botRefId = botRefId!!, com.netomi.chat.model.endchat.NCWRequestBody(
                    botReferenceId = botRefId!!,
                    channelId = "NETOMI_WEB_WIDGET",
                    conversationId = conversationID!!,
                    NCWEventData = NCWEventData(
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

        NCWSingleAlertDialog.showSingleButtonDialog(
            context = this,
            title = "Session Timeout",
            subtitle = "Your session has expired due to inactivity.",
            yesText = "OK",
            onYesClick = {
                finish()
            },
        )
    }

    private fun getPreSignedUrl(type: String?, uploadKeyPrefix: String) {
        constProgressBar.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        val mediaUpload = NCWSignedUrlPayload(
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
    private fun sendMessage(messageContent: String) {
        if (!NCWAppUtils.isNetworkAvailable(this)) {
            NCWAppUtils.showToast(this, "Please check your network and try again.")
            return
        }
        if (messageContent.isNotEmpty()) {
            val timeStamp = System.currentTimeMillis()
            val payload = createPayload(messageContent, messageContent, timeStamp)
            checkForPreviousQuickReply()
            chatViewModel.sendMessage(messageContent, timeStamp)
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
        timeStamp: Long? = System.currentTimeMillis(),
        attachmentList: ArrayList<NCWAttachmentList>? = null
    ): NCWWebhookPayload {
        val messageId = UUID.randomUUID().toString()

        val attributes = NCWAdditionalAttributes().apply {
            CUSTOM_ATTRIBUTES.addAll(deviceInfo)
        }
        return NCWWebhookPayload(
            botRefId = botRefId,
            requestBody = NCWRequestBody(
                conversationId = conversationID,
                messagePayload = NCWMessagePayload(
                    text = messageContent,
                    label = label,
                    messageId = messageId,
                    timestamp = timeStamp

                ),
                attachmentList = attachmentList,
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
                NCWQuickReplyOption().apply {
                    label = initialData.label
                    metadata = initialData.name
                }
            }

            messageList.add(
                NCWMessage(
                    sender = TYPE_RESPONSE,
                    timestamp = System.currentTimeMillis(),
                    quickReply = NCWQuickReply(options = ArrayList(options))
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
    private fun applyTheme(themeData: NCWThemeResponse?) {
        // Configure header and footer
        val rootLayout = findViewById<View>(R.id.rootLayout)
        NCWThemeUtils.configureHeader(
            headerContainer,
            ivMenu,
            closeIcon,
            headerTextView,
            window,
            rootLayout,
            this,
            progressBar
        )
        NCWThemeUtils.configureFooter(
            footerContainer,
            ivMenuOption,
            messageInputField,
            attachmentIcon,
            sendMessageIcon
        )

        // Set attachment icon visibility
        attachmentIcon.visibility =
            if (themeData?.fileSharing?.isEnabled == true) View.VISIBLE else View.GONE

        // Configure footer branding
        themeData?.mobileConfig?.lightTheme?.footerConfig?.let { footerConfig ->
            if (footerConfig.isFooterHidden) {
                tvBrandName.apply {
                    visibility = View.VISIBLE
                    text = footerConfig.netomiBrandingText.orEmpty()
                    footerConfig.netomiBrandingTextColor?.let {
                        setTextColor(
                            NCWThemeUtils.parseColor(
                                it
                            )
                        )
                    }
                }
            } else {
                tvBrandName.visibility = View.GONE
            }
        }


    }

    /**
     * Initializes and binds UI components in the chat activity layout.
     */
    private fun initViews() {

        headerContainer = findViewById(R.id.constHeader)
        footerContainer = findViewById(R.id.constFooter)
        messageInputField = findViewById(R.id.edtMessage)
        sendMessageIcon = findViewById(R.id.ivSend)
        chatRecyclerView = findViewById(R.id.rvChat)
        attachmentIcon = findViewById(R.id.ivAttachment)
        headerTextView = findViewById(R.id.tvHeader)
        closeIcon = findViewById(R.id.ivClose)
        logoIcon = findViewById(R.id.ivLogo)
        ivMenuOption = findViewById(R.id.ivMenuOption)
        ivMenu = findViewById(R.id.ivMenu)
        connectionHeader = findViewById(R.id.connection_status_header)
        tvBrandName = findViewById(R.id.tvBrand)
        constProgressBar = findViewById(R.id.constLoader)
        progressBar = findViewById(R.id.progress_loader)



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
        messageAdapter = NCWChatAdapter(messageList, themeData, this)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter
    }

    override fun onQuickReply(option: NCWQuickReplyOption?, position: Int) {
        messageList[position].isQuickReplyVisible = false
        //   messageList.removeAt(position)
        onQuickReplyClicked(option)

    }

    override fun onMediaClick(message: NCWMessage) {
        /*val intent = Intent(this, FullScreenMediaActivity::class.java).apply {
            val mediaUrl = when (message.type) {
                MessageType.VIDEO -> message.thumbnailUrl
                MessageType.IMAGE -> message.largeImageUrl
                MessageType.FILE -> message.fileUrl
                else -> null
            }
            putExtra(MEDIA_TYPE, message.type.name)

            if (mediaUrl != null) {
                putExtra(ARG_MEDIA_URL, mediaUrl)
            } else {
                putExtra(ARG_FILE_URI, message.message) // Pass file URI directly
            }
        }
        startActivity(intent)*/

        val mediaUrl = when (message.type) {
            MessageType.VIDEO -> message.thumbnailUrl
            MessageType.IMAGE -> message.largeImageUrl
            MessageType.FILE -> message.fileUrl
            else -> null
        }

        mediaUrl?.let {
            val intent = Intent(this, NCWFullScreenMediaActivity::class.java).apply {
                putExtra(ARG_MEDIA_URL, mediaUrl)
                putExtra(MEDIA_TYPE, message.type.name)

            }
            startActivity(intent)
        } ?: run {

            val uri = Uri.parse(message.message)
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


    override fun onRetryClicked(message: NCWMessage) {
        when (message.type) {
            MessageType.TEXT -> message.message?.let {
                messageList.remove(message)
                sendMessage(it)
            }

            else -> {
                messageList.remove(message)
                val selectedMediaUri:Uri=Uri.parse(message.message)
                val type = contentResolver.getType(selectedMediaUri)
                handleFileSelection(selectedMediaUri, type)
            }
        }
    }


    override fun carouselButtonAction(it: NCWCarouselButton?) {

        Log.e("CarouselButton", "CarouselButton $it")
    }


    /**
     * Handles quick reply option click by sending a message based on the option selected.
     *
     * @param option The selected quick reply option.
     */
    private fun onQuickReplyClicked(option: NCWQuickReplyOption?) {
        option?.label?.takeIf { it.isNotEmpty() }?.let { label ->
            val timeStamp = System.currentTimeMillis()
            val payload = option.metadata?.let { createPayload(it, label, timeStamp) }
            chatViewModel.sendMessage(label, timeStamp)
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
            handleApiCallback(messages as NCWState<Any>)
        }

        chatViewModel.getConversationId.observe(this) { conversationId ->
            handleApiCallback(conversationId as NCWState<Any>)
        }

        chatViewModel.getAWSMQTTCredentials.observe(this) { credentials ->
            handleApiCallback(credentials as NCWState<Any>)
        }

        chatViewModel.sendMessages.observe(this) { message ->
            updateMessageList(message)
        }

        chatViewModel.getChatHistory.observe(this) { messages ->
            handleApiCallback(messages as NCWState<Any>)

        }
        chatViewModel.getSignedUrl.observe(this) { signedUrl ->
            handleApiCallback(signedUrl as NCWState<Any>)

        }
        chatViewModel.getUploadedMediaUrl.observe(this) { media ->
            handleApiCallback(media as NCWState<Any>)

        }




        chatViewModel.awsMessage.observe(this) { jsonMessage ->
            try {
                val response = Gson().fromJson(jsonMessage, NCWGenericChannelResponse::class.java)
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

        ncwAwsCredentialsViewModel.credentials.observe(this) {
            val topic = "$CHAT_WIDGET/$botRefId/$conversationID"
            Log.e("Topic", "Topic Name " + topic)
            ncwAwsCredentialsViewModel.initializeAwsIotManager(chatViewModel, topic)
        }


        ncwAwsCredentialsViewModel.connectionStatus.observe(this) { status ->
            when (status) {
                NCWConnectionStatus.CONNECTING.toString() -> {
                    connectionHeader.text = getString(R.string.connecting)
                    connectionHeader.setBackgroundColor(Color.YELLOW)
                    connectionHeader.setTextColor(Color.BLACK)
                    connectionHeader.visibility = View.VISIBLE
                }

                NCWConnectionStatus.CONNECTED.toString() -> {
                    connectionHeader.text = getString(R.string.connected)
                    connectionHeader.setBackgroundColor(Color.GREEN)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE

                    // Hide header after 2 seconds when connected
                    connectionHeader.postDelayed({
                        connectionHeader.visibility = View.GONE
                    }, 2000)
                }

                NCWConnectionStatus.CONNECTION_LOST.toString() -> {
                    connectionHeader.text = getString(R.string.connection_lost)
                    connectionHeader.setBackgroundColor(Color.RED)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE
                }

                NCWConnectionStatus.RE_CONNECTED.toString() -> {
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
            handleApiCallback(messages as NCWState<Any>)
        }
    }

    // Helper function to map attachments to NCWMessage
    private fun mapAttachmentToMessage(attachment: NCWAttachment): NCWMessage? {
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
                    Log.e("CalllActive", "Time Passes")
                    removeLoader()
                    messageAdapter.notifyDataSetChanged()
                }
            }, it)
        }


    }

    private fun handleApiCallback(response: NCWState<Any>) {
        when (response) {
            is NCWState.Loading -> {
                progressBar.visibility = View.VISIBLE
                constProgressBar.visibility = View.VISIBLE
            }

            is NCWState.Success -> {
                onApiSuccess(response.data, response.apiConstant)
            }

            is NCWState.Error -> {
                Toast.makeText(this, "Error..", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                constProgressBar.visibility = View.GONE
            }

            is NCWState.SendMessageError -> {
                val payload = response.payload
                val targetTimestamp =
                    payload.requestBody?.messagePayload?.timestamp ?: System.currentTimeMillis()
                // Flag to track if any message was updated
                val updated = messageList.any { message ->
                    when {
                        // Check for a text message and matching timestamp
                        message.type == MessageType.TEXT && message.timestamp == targetTimestamp -> {
                            message.isRetry = true
                            true
                        }

                        else -> {
                            payload.requestBody?.attachmentList?.any { attachment ->
                                if (attachment.title == message.title) {
                                    message.isRetry = true  // Update retry flag for matching attachment
                                    true
                                } else {
                                    false
                                }
                            } ?: false
                        }
                    }
                }
                if (updated) {
                    messageAdapter.notifyDataSetChanged()
                }
            }

            else -> {
                Toast.makeText(this, "Else..", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun requestPermissionsAndShowMediaOptions() {
        val permissions: Array<String> =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
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

        val bottomSheet = NCWMediaOptionsBottomSheet(
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

                fileSend = photoUri?.let { NCWAppUtils.getFileFromUri(this, it) }

                if (!isFileSizeValid(this, fileSend?.length(), themeData?.fileSharing?.fileSize)) {
                    return@registerForActivityResult
                }
                photoUri?.let { uri ->
                    addMediaMessage(fileSend,uri, MessageType.IMAGE)
                }

                val type =
                    photoUri?.let { fileSend?.let { it1 -> NCWAppUtils.getFileContentType(it1) } }
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
        val fileExtension =
            file?.extension?.lowercase()?.let { if (!it.startsWith(".")) ".$it" else it }

        if (fileExtension !in supportedExtensions) {
            NCWAppUtils.showToast(
                this,
                "Unsupported file type selected. Supported types: ${
                    supportedExtensions.joinToString(
                        ", "
                    )
                }"
            )
            return false
        }

        return true
    }

    // Common function to handle file result
    private fun handleFileSelection(
        selectedMediaUri: Uri?,
        mimeType: String?,
        isGallery: Boolean = false
    ) {

        if (selectedMediaUri == null) {
            Log.e("FileSelection", "No media selected")
            return
        }

// Get the file from the URI
        fileSend = if (isGallery) {
            NCWFilePath().getPath(this, selectedMediaUri)?.let { File(it) }
        } else {
            NCWImageUtils.getFileFromUri(this, selectedMediaUri)
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
            mimeType.startsWith("image/") -> addMediaMessage(fileSend,selectedMediaUri, MessageType.IMAGE)
            mimeType.startsWith("video/") -> addMediaMessage(fileSend,selectedMediaUri, MessageType.VIDEO)
            else -> addDocMessage(fileSend, selectedMediaUri, MessageType.FILE)
        }

        fileSend?.let {
            getPreSignedUrl(mimeType, it.path)

        }
    }

    // Gallery selection
    private fun openGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
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
                val selectedMediaUri: Uri? = result.data?.data!!
                handleFileSelection(selectedMediaUri, type, isGallery = true)
            }
        }

    // Handle the result of file selection (PDF, DOC, etc.)
    private val fileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val type = contentResolver.getType(result.data?.data!!)
                val selectedMediaUri: Uri? = result.data?.data!!
                handleFileSelection(selectedMediaUri, type)
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
    private fun addMediaMessage(file: File?,uri: Uri, type: MessageType) {
        val newMessage = NCWMessage(
            sender = TYPE_REQUEST,
            type = type,
            message = uri.toString(),
            timestamp = System.currentTimeMillis(),
            title = file?.name,
            fileSize = fileSend?.length().toString()
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
            NCWRoutes.ROUTE_GET_CONVERSATION_ID -> {
                val response = apiResponse as NCWGetConversationIdResponse
                // Use conversationID as needed
                conversationID = response.conversationID
                chatViewModel.getAWSMQTTCredentials(botRefId)
                conversationID?.let { NCWThemeUtils.setConversationID(it) }
                Log.d(
                    "ConversationID",
                    "Fetched conversation ID: $conversationID"
                )

            }

            NCWRoutes.ROUTE_GET_MQTT_CREDENTIALS -> {
                val mqttsCredentials = apiResponse as MQTTCredentialsResponse
                saveAwsCredentials(mqttsCredentials.credentials)
            }

            NCWRoutes.ROUTE_SEND_CHAT -> {
                val sendMessageResponse = apiResponse as NCWSendMessageResponse
                progressBar.visibility = View.GONE
                constProgressBar.visibility = View.GONE
            }

            NCWRoutes.ROUTE_GET_CHAT -> {
                val response = apiResponse as NCWGetChatHistoryResponse
                if (response != null && response.responses.size > 0) {
                    parseHistoryItems(response.responses)
                }
                progressBar.visibility = View.GONE
                constProgressBar.visibility = View.GONE
            }

            NCWRoutes.ROUTE_GET_PRESIGNED_URL -> {
                val response = apiResponse as NCWGetPreSignedUrl
                chatViewModel.uploadFile(fileSend, response)
            }

            NCWRoutes.ROUTE_UPLOAD_MEDIA -> {
                val response = apiResponse as NCWGetMediaUploadUrl
                Log.d("ROUTE_UPLOAD_MEDIA", "Fetched ROUTE_UPLOAD_MEDIA: $response")

                val mediaType = response.type?.let { NCWAppUtils.getTypeFromContent(it) }
                Log.e("MediaType", "Determined media type: $mediaType")

                val attachmentList = arrayListOf(
                    NCWAttachmentList().apply {
                        type = mediaType
                        actualType = mediaType
                        attachmentId = UUID.randomUUID().toString()
                        percentage = 10
                        fileType = response.type
                        title = response.title
                        fileSize = response.fileSize
                        largeImageUrl = if (mediaType == TYPE_IMAGE) response.url else null
                        thumbnailUrl = if (mediaType == TYPE_VIDEO) response.url else null
                        fileURL = if (mediaType == TYPE_FILE) response.url else null
                    }
                )

                Log.e("attachmentList", "attachmentList" + attachmentList)

                val timeStamp = System.currentTimeMillis()
                val payload = createPayload(
                    "event://;LEARN_ATTRIBUTE_EVENT;ATTACHMENT::value=Media has been uploaded",
                    "Attachment has been uploaded",
                    timeStamp,
                    attachmentList
                )
                chatViewModel.sendMessageAPI(payload)


            }

            NCWRoutes.ROUTE_END_CHAT -> {
                NCWThemeUtils.setConversationID(null)
                finish()
            }
        }

    }

    private fun parseHistoryItems(responses: ArrayList<NCWGenericChannelResponse>) {

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
            } else {
                response.requestPayload?.attachmentList?.takeIf { it.isNotEmpty() }
                    ?.forEach { attachmentListRequest ->
                        val messageType =
                            attachmentListRequest.type?.let { MessageType.fromTypeName(it) }

                        messageType?.let {
                            val newMessage = NCWMessage(
                                message = attachmentListRequest.title,
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
        chatRecyclerView.scrollToPosition(messageList.size - 1)


    }

    private fun getChatHistory() {
        val payload = conversationID?.let {
            botRefId?.let { it1 ->
                NCWGetChatHistoryPayload(
                    conversationId = it,
                    requestBody = NCWHistoryRequestBody(
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
    private fun saveAwsCredentials(credentials: NCWCredentials) {
// Save new credentials (example usage)
        val newCredentials = NCWAwsCredentials(
            accessKey = credentials.accessKeyId,
            secretKey = credentials.secretAccessKey,
            sessionToken = credentials.SessionToken,
            iotEndpoint = credentials.IoTHostEndPoint,
            expiresIn = credentials.expiresIn
        )
        ncwAwsCredentialsViewModel.saveAwsCredentials(newCredentials)

        /*  val topic = "$CHAT_WIDGET/$botRefId/$conversationID"
        Log.e("Topic","Topic Name "+topic)
        ncwAwsCredentialsViewModel.initializeAwsIotManager(chatViewModel, topic)*/

    }
}