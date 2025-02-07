package com.netomi.chat.ui.view


import NCWIdleTimeoutManager
import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
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
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.netomi.chat.R
import com.netomi.chat.awsiot.NCWAwsIotManager
import com.netomi.chat.awsiot.NCWConnectionStatus
import com.netomi.chat.model.CarouselButtonType
import com.netomi.chat.model.CustomFieldName
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.NCWSendMessageResponse
import com.netomi.chat.model.auth.LoginResponse
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.chat_history.NCWHistoryRequestBody
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.endchat.NCWEventData
import com.netomi.chat.model.feedback.feedbackrequest.NCWEventInfo
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest
import com.netomi.chat.model.media_payload.MultiFileModel
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.model.messages.FormSchema
import com.netomi.chat.model.messages.MultipleSourceDetail
import com.netomi.chat.model.messages.NCWAdditionalAttributes
import com.netomi.chat.model.messages.NCWAttachment
import com.netomi.chat.model.messages.NCWAttachmentList
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.model.messages.NCWCustomAttribute
import com.netomi.chat.model.messages.NCWCustomPayload
import com.netomi.chat.model.messages.NCWGenericChannelResponse
import com.netomi.chat.model.messages.NCWMessagePayload
import com.netomi.chat.model.messages.NCWQuickReply
import com.netomi.chat.model.messages.NCWQuickReplyOption
import com.netomi.chat.model.messages.NCWRequestBody
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.model.messages.SurveyField
import com.netomi.chat.model.mqtt.MQTTCredentialsResponse
import com.netomi.chat.model.mqtt.NCWCredentials
import com.netomi.chat.model.presigned_url.NCWGetMediaUploadUrl
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.model.survey_rule.SurveyRule
import com.netomi.chat.model.survey_rule.SurveyRuleResponse
import com.netomi.chat.model.theme.NCWShowWarning
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.model.theme.light_theme.NCWHeaderConfig
import com.netomi.chat.model.transcript.NCWEmailRequest
import com.netomi.chat.survey.EventData
import com.netomi.chat.survey.SubmitSurveyRequest
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.ui.viewmodel.NCWAwsCredentialsViewModel
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.DeviceInfoUtil
import com.netomi.chat.utils.MessageSoundPlayer
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppConstant.ARG_MEDIA_URL
import com.netomi.chat.utils.NCWAppConstant.BOT_REFERENCE_ID
import com.netomi.chat.utils.NCWAppConstant.CHANNEL_ID
import com.netomi.chat.utils.NCWAppConstant.CHAT_WIDGET
import com.netomi.chat.utils.NCWAppConstant.DATE_FORMAT
import com.netomi.chat.utils.NCWAppConstant.EVENT_WIDGET
import com.netomi.chat.utils.NCWAppConstant.INFO_EVENT
import com.netomi.chat.utils.NCWAppConstant.LOGOUT
import com.netomi.chat.utils.NCWAppConstant.MEDIA_TYPE
import com.netomi.chat.utils.NCWAppConstant.MESSAGE_BACK_TO_BOT
import com.netomi.chat.utils.NCWAppConstant.NETOMI
import com.netomi.chat.utils.NCWAppConstant.OAUTH
import com.netomi.chat.utils.NCWAppConstant.PROACTIVE_GREETING
import com.netomi.chat.utils.NCWAppConstant.RULE_EVENT_CHAT_END
import com.netomi.chat.utils.NCWAppConstant.RULE_EVENT_IDLE_USER
import com.netomi.chat.utils.NCWAppConstant.SESSION
import com.netomi.chat.utils.NCWAppConstant.SIZE_LIMIT
import com.netomi.chat.utils.NCWAppConstant.SKIP_LABEL
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPE_IDLE_USER
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPE_JOIN
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPE_LEAVE
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPE_OAUTH
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPE_TRANSFER
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPE_WAIT
import com.netomi.chat.utils.NCWAppConstant.SUB_TYPING
import com.netomi.chat.utils.NCWAppConstant.TYPE_AGENT
import com.netomi.chat.utils.NCWAppConstant.TYPE_AGENT_EVENT
import com.netomi.chat.utils.NCWAppConstant.TYPE_ATTACHMENT
import com.netomi.chat.utils.NCWAppConstant.TYPE_BOT
import com.netomi.chat.utils.NCWAppConstant.TYPE_EVENT
import com.netomi.chat.utils.NCWAppConstant.TYPE_FILE
import com.netomi.chat.utils.NCWAppConstant.TYPE_FORM
import com.netomi.chat.utils.NCWAppConstant.TYPE_FORM_ATTACHMENT
import com.netomi.chat.utils.NCWAppConstant.TYPE_IMAGE
import com.netomi.chat.utils.NCWAppConstant.TYPE_INDICATOR
import com.netomi.chat.utils.NCWAppConstant.TYPE_INITIAL
import com.netomi.chat.utils.NCWAppConstant.TYPE_PILLS
import com.netomi.chat.utils.NCWAppConstant.TYPE_QUEUE_POSITION
import com.netomi.chat.utils.NCWAppConstant.TYPE_REQUEST
import com.netomi.chat.utils.NCWAppConstant.TYPE_RESPONSE
import com.netomi.chat.utils.NCWAppConstant.TYPE_SHOW_SURVEY
import com.netomi.chat.utils.NCWAppConstant.TYPE_SUBMITTED_SURVEY
import com.netomi.chat.utils.NCWAppConstant.TYPE_VIDEO
import com.netomi.chat.utils.NCWAppConstant.UPLOAD_FILE_MULTIPLE
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWAppUtils.hideKeyboard
import com.netomi.chat.utils.NCWAppUtils.isFileSizeValid
import com.netomi.chat.utils.NCWAppUtils.isFormSizeValid
import com.netomi.chat.utils.NCWChatActionCallback
import com.netomi.chat.utils.NCWDialogUtils
import com.netomi.chat.utils.NCWFeedbackActionCallback
import com.netomi.chat.utils.NCWFilePath
import com.netomi.chat.utils.NCWImageUtils
import com.netomi.chat.utils.NCWRoutes
import com.netomi.chat.utils.NCWSingleAlertDialog
import com.netomi.chat.utils.NCWState
import com.netomi.chat.utils.NCWThemeUtils
import com.netomi.chat.utils.toNCWCustomAttributes
import com.netomi.chat.utils.toNCWUserDetailAttribute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
class NCWChatActivity : AppCompatActivity(), NCWChatActionCallback, NCWFeedbackActionCallback {

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
    private lateinit var cardToday: ConstraintLayout
    private var photoUri: Uri? = null
    private var ncwSdkConfig: NCWHeaderConfig? = null
    private var themeData: NCWThemeResponse? = null
    private lateinit var cardViewInputBox: CardView
    private lateinit var customTabsIntent: CustomTabsIntent
    private lateinit var topic: String

    private var conversationID: String? = null
    private var externalId: String? = null
    private var botRefId: String? = null
    private lateinit var idleTimeoutManager: NCWIdleTimeoutManager
    private var fileSend: File? = null

    private var loaderAddedTime: Long = 0
    private var isLoaderActive: Boolean = false
    private lateinit var tvBrandName: TextView

    private var formComponent: Component? = null

    private var attachmentType: String? = TYPE_ATTACHMENT


    private var deviceInfo: ArrayList<NCWCustomAttribute>? = null


    private var connectionStatus: String? = ""

    private var agentName: String? = ""
    private var agentAvatar: String? = null


    private var ownerType: String? = "BOT"

    private var isHistoryDisableInput: Boolean = true
    private var isDisableInput: Boolean = true

    private var mSurveyRule: List<SurveyRule>? = null
    private var isSurveyRule: Boolean = false

    private var idleTimeInMillis: Long = 0L
    private val handler = Handler(Looper.getMainLooper())
    private val messageChunksMap = mutableMapOf<String, MutableList<NCWMessage>>()

    private var messageSoundPlayer: MessageSoundPlayer? = null
    private var onRestart: Boolean = false
    private var isIdle: Boolean = false
    private var isHistoryChatAvialbale: Boolean = false
    private var isMultipleFile: Boolean = false
    private var mMultipleFile: ArrayList<MultiFileModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initViews()

        // Load theme and config
        themeData = NCWThemeUtils.getThemeData()
        agentAvatar = themeData?.mobileConfig?.lightTheme?.botConfig?.botImage
        NCWChatSdk.getUpdateHeaderConfiguration()
        NCWChatSdk.getUpdatedFooterConfiguration()
        NCWChatSdk.getUpdatedBotConfiguration()
        NCWChatSdk.getUpdatedUserConfiguration()
        NCWChatSdk.getUpdatedChatWindowConfiguration()
        NCWChatSdk.getUpdatedBubbleConfiguration()
        NCWChatSdk.getUpdatedOtherConfiguration()


        // Set up message adapter and recycler view
        setupMessageList()

        applyTheme(themeData)
        observeChatMessages()

        // Properly collect the streaming messages
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.awsMessage.collectLatest { message ->
                    message?.let {
                        Log.e("Streaming Chunk", it)
                        val response = Gson().fromJson(it, NCWGenericChannelResponse::class.java)
                        renderTheNormalMessage(response)
                    }
                }
            }
        }

        botRefId = intent.getStringExtra(BOT_REFERENCE_ID)
        val device = DeviceInfoUtil.getDeviceInfo(this)
        deviceInfo = device.toNCWCustomAttributes()

        val jwtToken = NCWThemeUtils.getJwtToken()
        if (jwtToken != null) {
            botRefId?.let { chatViewModel.hitAuthenticateUserApi(jwtToken, it) }
        } else {
            conversationID = NCWThemeUtils.getConversationID()
            if (conversationID == null) {
                loadInitialMessages()

                chatViewModel.getConversationId(botRefId, externalId)
            } else {
                chatViewModel.getAWSMQTTCredentials(botRefId)
                getChatHistory()
            }
        }
        sendMessageIcon.setOnClickListener {

            val messageContent = messageInputField.text.toString()
            sendMessage(messageContent)
        }

        attachmentIcon.setOnClickListener {
            attachmentType = TYPE_ATTACHMENT
            showMedia()



        }

        ivMenuOption.setOnClickListener {
            setUpQuickReplyOption()

        }
        ivMenu.setOnClickListener {
            setUpSettingOption()
        }

        closeIcon.setOnClickListener {
            if (themeData?.mobileConfig?.lightTheme?.headerConfig?.isBackPressPopupEnabed == true)
                backClicked() else finish()
        }
        // Initialize IdleTimeoutManager with a timeout and a callback for session timeout
        themeData?.endChat?.idleTimeout?.let {
            idleTimeoutManager = NCWIdleTimeoutManager(
                idleTimeoutMillis = it,
                onTimeout = {
                    handleSessionTimeout(
                        getString(R.string.session_timeout),
                        getString(R.string.your_session_has_expired_due_to_inactivity),
                        getString(R.string.ok),
                        SESSION
                    )
                }
            )

        }

        // Add a custom back press callback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backClicked()
            }
        })

        botRefId?.let { chatViewModel.getSurveyRule(it) }



    }

    private fun setUpQuickReplyOption() {
        handler.removeCallbacks(idleRunnable)
        val bottomSheet = themeData?.let {
            NCWQuickMenuBottomSheet(it.quickMenuOptions,{ options ->
                val timeStamp = System.currentTimeMillis()
                checkForInitialMessage()
                checkForPreviousQuickReply()
                val payload = createPayload(options.text, options.label, timeStamp)
                chatViewModel.sendMessage(options.label, timeStamp)
                if (payload != null) {
                    sendMessageToBot(payload)
                }
                messageInputField.text.clear()
            },{
                setIdealSurveyAgain()
            })
        }
        bottomSheet?.show(supportFragmentManager, "SurveyOptionsBottomSheet")
    }

    private fun setUpLanguageOption() {
        val bottomSheet = themeData?.let {
            NCWLanguageBottomSheet(it.multilingual.languages) { options ->

            }
        }
        bottomSheet?.show(supportFragmentManager, "SurveyOptionsBottomSheet")
    }

    private fun setUpSettingOption() {
        handler.removeCallbacks(idleRunnable)
        val bottomSheet = themeData?.let {
            NCWSettingBottomSheet(it ,{showWarning->
                if (showWarning!=null)
                showRestartPopUp(showWarning)
                else{
                    onRestartAction()
                }

            },{
                setUpLanguageOption() },{
                setIdealSurveyAgain()
            })
        }
        bottomSheet?.show(supportFragmentManager, "SurveyOptionsBottomSheet")
    }

    private fun setIdealSurveyAgain() {
        if (idleTimeInMillis > 0) {
            resetIdleTimer()
            isIdle = false
        }
    }

    private fun showRestartPopUp(ncwShowWarning: NCWShowWarning) {

        NCWDialogUtils.showCustomDialog(
            this,
            title = ncwShowWarning.restartButtonText?:getString(R.string.restart_chat),
            subtitle = ncwShowWarning.warningText?:getString(R.string.confirm_restart_chat),
            confirm = ncwShowWarning.restartButtonText?:getString(R.string.restart_chat),
            cancelButtonText = ncwShowWarning.cancelButtonText?:getString(R.string.cancel)
        ) {

            onRestartAction()

        }
    }

    private fun onRestartAction() {
        onRestart = true
        isHistoryChatAvialbale = false
        if (topic != null) {
            NCWAwsIotManager.unsubscribeRestart(topic)
        }
        callBackToBot()
        hitEndChatAPI()
    }


    private val idleRunnable = Runnable {
        if (idleTimeInMillis > 0) {
            hitIdealTimeOutEvent()
        }
    }

    private fun hitIdealTimeOutEvent() {
        val idleTime = idleTimeInMillis / 1000
        isIdle = true

        if (botRefId!=null && conversationID!=null) {
            chatViewModel.hitFeedbackAPI(
                NCWFeedbackRequest(
                    botRefId?:"", com.netomi.chat.model.feedback.feedbackresponse.NCWRequestBody(
                        botReferenceId = botRefId?:"",
                        channelId = CHANNEL_ID,
                        conversationId = conversationID?:"",
                        eventData = com.netomi.chat.model.feedback.feedbackrequest.NCWEventData(
                            eventInfo = NCWEventInfo(
                                idleTime = idleTime
                            ),
                            eventType = EVENT_WIDGET,
                            subType = SUB_TYPE_IDLE_USER
                        ),
                        eventName = INFO_EVENT,
                        isPublishToMQTT = false,
                        requestType = NETOMI,
                        timestamp = System.currentTimeMillis(),
                        triggerType = TYPE_EVENT
                    )
                )
            )
        }
    }


    private fun resetIdleTimer() {
        handler.removeCallbacks(idleRunnable)
        if (idleTimeInMillis > 0) {
            handler.postDelayed(idleRunnable, idleTimeInMillis)
        }
    }

    private fun showMedia() {
        if (arePermissionsGranted())
            showMediaOptions()
        else
            requestPermissionsAndShowMediaOptions()
    }

      /*if (NCWThemeUtils.getJwtToken() != null) {
                     hitLogoutAPI()
                 } else {
                     hitEndChatAPI()
                 }*/
    private fun backClicked() {
        val bottomSheet = NCWEndChatBottomSheet(
            themeData,
            { isEndChat ->
                if (isEndChat) {
                    callBackToBot()
                    hitEndChatAPI()
                } else {
                    NCWThemeUtils.setJwtToken(null)
                    finish()
                }
            },{ from,mail->
              sendTranscriptApI(from,mail)
                callBackToBot()
                hitEndChatAPI()

            })
        bottomSheet.show(supportFragmentManager, "EndChatBottomSheet")

    }

    private fun callBackToBot() {
        if (ownerType== TYPE_AGENT){
            val timeStamp = System.currentTimeMillis()
            val payload = createPayload(MESSAGE_BACK_TO_BOT, MESSAGE_BACK_TO_BOT, timeStamp)
            sendMessageToBot(payload)

        }
    }

    private fun hitLogoutAPI() {
        NCWThemeUtils.getJwtToken()
            ?.let { botRefId?.let { it1 -> chatViewModel.hitLogoutApi(it, botRefID = it1) } }
    }

    private fun hitEndChatAPI() {
        if (!NCWAppUtils.isNetworkAvailable(this)) {
            NCWAppUtils.showToast(this, getString(R.string.please_check_your_network_and_try_again))
            return
        }
        showProgressBar()
        handler.removeCallbacks(idleRunnable)
        chatViewModel.hitEndChatAPI(
            NCWEndChatRequest(
                botRefId = botRefId!!, com.netomi.chat.model.endchat.NCWRequestBody(
                    botReferenceId = botRefId!!,
                    channelId = "NETOMI_WEB_WIDGET",
                    conversationId = conversationID ?: "",
                    eventData = NCWEventData(
                        eventType = "WIDGET_EVENT",
                        subType = "CHAT_END"
                    ),
                    eventName = "INFO_PILL",
                    isPublishToMQTT = false,
                    requestType = "NETOMI",
                    timestamp = System.currentTimeMillis(),
                    triggerType = "EVENT"

                )
            ),

            )
    }


    private fun hitFeedbackAPI(requestId: String, feedbackValue: String, attachmentIndex: Int) {
        if (!NCWAppUtils.isNetworkAvailable(this)) {
            NCWAppUtils.showToast(this, getString(R.string.please_check_your_network_and_try_again))
            return
        }
        //showProgressBar()
        chatViewModel.hitFeedbackAPI(
            NCWFeedbackRequest(
                botRefId!!, com.netomi.chat.model.feedback.feedbackresponse.NCWRequestBody(
                    botReferenceId = botRefId!!,
                    channelId = "NETOMI_WEB_WIDGET",
                    conversationId = conversationID!!,
                    eventData = com.netomi.chat.model.feedback.feedbackrequest.NCWEventData(
                        eventInfo = NCWEventInfo(
                            attachmentIndex = attachmentIndex,
                            feedbackValue = feedbackValue,
                            requestId = requestId
                        ),
                        eventType = "FEEDBACK",
                        subType = "REVIEW"
                    ),
                    eventName = "FEEDBACK",
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
    private fun handleSessionTimeout(
        title: String,
        subtitle: String,
        submitText: String,
        from: String
    ) {

        NCWSingleAlertDialog.showSingleButtonDialog(
            context = this,
            title = title,
            subtitle = subtitle,
            yesText = submitText,
            onYesClick = {
                if (from == SESSION) {
                    NCWThemeUtils.setSignInUserDetails(null)
                    NCWThemeUtils.setConversationID(null)
                    themeData?.isProActiveGreetings = false
                    finish()
                }
                else if (from == LOGOUT) {
                    NCWThemeUtils.setSignInUserDetails(null)
                    hitEndChatAPI()
                }
            },
        )
    }

    private fun getPreSignedUrl(type: String?, uploadKeyPrefix: String) {
        showProgressBar()
        val mediaUpload = NCWSignedUrlPayload(
            fileType = type,
            uploadKeyPrefix = uploadKeyPrefix
        )
        chatViewModel.getPreSignedUrl(mediaUpload)
        checkForInitialMessage()
    }

    /**
     * Sends a user message in the chat.
     * Triggered when the user presses the send icon. Retrieves the current input
     * from `messageInputField`, clears the input field, and posts the message.
     */
    private fun sendMessage(messageContent: String) {
        if (!NCWAppUtils.isNetworkAvailable(this)) {
            NCWAppUtils.showToast(this, getString(R.string.please_check_your_network_and_try_again))
            return
        }

        if (checkForLogoutAction(messageContent)) return

        if (messageContent.trim().isNotEmpty()) {
            val timeStamp = System.currentTimeMillis()
            val payload = createPayload(messageContent, messageContent, timeStamp)
            checkForInitialMessage()
            checkForPreviousQuickReply()
            chatViewModel.sendMessage(messageContent, timeStamp)
            sendMessageToBot(payload)
        }
        idleTimeoutManager.checkForTimeout()
    }

    private fun sendMessageToBot(payload: NCWWebhookPayload) {
        playUserSound()
        chatViewModel.sendMessageAPI(payload)
        messageInputField.text.clear()

    }


    private fun checkForLogoutAction(content: String?): Boolean {
        Log.e(" themeData?.OAUTH2?.logoutActionKeys?"," themeData?.OAUTH2?.logoutActionKeys?"+ themeData?.OAUTH2?.logoutActionKeys)
        Log.e("Keyyss","ddd "+content)
        themeData?.OAUTH2?.logoutActionKeys?.let { logoutActionKeys ->
            if (logoutActionKeys.any { key -> content?.contains(key, ignoreCase = true) == true }) {
                handleSessionTimeout(
                    getString(R.string.logout),
                    getString(R.string.you_have_been_logged_out),
                    getString(R.string.ok),
                    LOGOUT
                )
                return true
            }
        }
        return false
    }


    private fun checkForInitialMessage() {
        messageList.removeIf { it.sender == TYPE_INITIAL }
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

        if (NCWThemeUtils.getSignInUserDetails() != null) {
            val userInfo = NCWThemeUtils.getSignInUserDetails()?.toNCWUserDetailAttribute()
            if (userInfo != null) {
                deviceInfo?.addAll(userInfo)
            }
        }

        val attributes = NCWAdditionalAttributes(
            CUSTOM_ATTRIBUTES = deviceInfo
        )
        return NCWWebhookPayload(
            botRefId = botRefId,
            requestBody = NCWRequestBody(
                conversationId = conversationID,
                ownerType = ownerType,
                messagePayload = NCWMessagePayload(
                    text = messageContent,
                    label = label,
                    messageId = messageId,
                    timestamp = timeStamp,
                    hideMessage = if (label == PROACTIVE_GREETING || label==SKIP_LABEL|| label== MESSAGE_BACK_TO_BOT) true else null

                ),
                attachmentList = attachmentList,
                additionalAttributes = attributes,
                userDetails = NCWThemeUtils.getSignInUserDetails()

            )
        )
    }


    /**
     * This function adds a predefined message to the chat, such as the bot’s
     * initial greeting or information, allowing the user to see context when
     * they first open the chat.
     */
    private fun loadInitialMessages() {
        if (!checkProactiveMessageAvailable()) {
            // Add the initial bot message
            themeData?.initialFlows?.header?.let { header ->
                messageList.add(
                    NCWMessage(
                        sender = TYPE_INITIAL,
                        type = MessageType.TEXT,
                        message = header,
                        timestamp = System.currentTimeMillis(),
                        agentAvatar = agentAvatar
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
                        sender = TYPE_INITIAL,
                        timestamp = System.currentTimeMillis(),
                        quickReply = NCWQuickReply(options = ArrayList(options)),
                        agentAvatar = agentAvatar
                    )
                )
            }
            messageAdapter.notifyDataSetChanged()
        }
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
            progressBar,
            logoIcon

        )
        NCWThemeUtils.configureFooter(
            footerContainer,
            ivMenuOption,
            messageInputField,
            attachmentIcon,
            sendMessageIcon,
            cardViewInputBox,
            tvBrandName
        )

        // Set attachment icon visibility
        attachmentIcon.visibility =
            if (themeData?.fileSharing?.isEnabled == true) View.VISIBLE else View.GONE

        // Configure footer branding
        themeData?.mobileConfig?.lightTheme?.footerConfig?.let { footerConfig ->
            if (footerConfig.isNetomiBrandingEnabled  && !footerConfig.isFooterHidden) {
                tvBrandName.apply {
                    visibility = View.VISIBLE
                    text = footerConfig.netomiBrandingText
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

        themeData?.mobileConfig?.lightTheme?.chatWindowConfig?.let {
            chatRecyclerView.setBackgroundColor(Color.parseColor(it.chatWindowBackgroundColor))
            cardToday.setBackgroundColor(Color.parseColor(it.chatWindowBackgroundColor))
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
        cardViewInputBox = findViewById(R.id.cardView)
        cardToday = findViewById(R.id.cardToday)

        messageInputField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                chatRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)
            }
        }
        setUIState(false)
        customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(Color.parseColor(NCWChatSdk.getUpdateHeaderConfiguration().backgroundColor)) // Set the toolbar color (optional)
            .build()

        messageSoundPlayer = MessageSoundPlayer(this)
    }

    /**
     * Sets up the message list in the chat UI by initializing the adapter and layout manager.
     */
    private fun setupMessageList() {
        messageList = mutableListOf()
        messageAdapter = NCWChatAdapter(messageList, themeData, this, this, { it ->
            if (it != null) {
                attachmentType = TYPE_FORM_ATTACHMENT
                formComponent = it
                mMultipleFile.clear()
                showMedia()
            }
        }, { payload, label, attachmentList ->
            val timeStamp = System.currentTimeMillis()
            Log.e("FormPayload ","payload "+payload)
            Log.e("FormPayload ","label "+label)
            val createPayload = payload?.let { createPayload(it, label, timeStamp, attachmentList) }
            if (createPayload != null) {
                Log.e("FormPayload","FormPayload  "+createPayload)
                chatViewModel.sendMessageAPI(createPayload)
                addLoader()
                playUserSound()
            }

        }, {
            if (it != null) {
                showSubmittedSurvey(it)
            }
        }, { component, fileUploadData ->
            val singleFile: ArrayList<MultiFileModel> = arrayListOf()
            val mObj =
                fileUploadData.file?.let { MultiFileModel(fileUploadData.mimeType!!, it, it.name) }
            if (mObj != null) {
                singleFile.add(mObj)
            }
            chatViewModel.uploadFilesSequentially(singleFile)

        })

// Set the layout manager and adapter for the RecyclerView
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter
    }

    private fun showSubmittedSurvey(ncwMessage: NCWMessage) {
        createAndShowSurveyBottomSheet(
            requestId = ncwMessage.requestID ?: "",
            surveyField = ncwMessage.surveyField,
            TYPE_SUBMITTED_SURVEY,
            onSubmit = {},
            onSkipSurvey = { _, _ ->
            }

        )
    }

    override fun onQuickReply(option: NCWQuickReplyOption?, position: Int) {

        // if (connectionStatus == NCWConnectionStatus.CONNECTED.toString()) {

        messageList[position].isQuickReplyVisible = false
        onQuickReplyClicked(option)
        // }

    }

    override fun onMediaClick(message: NCWMessage) {

        hideKeyboard(this)

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
        messageList.remove(message)

        when (message.type) {
            MessageType.TEXT -> {
                message.message?.let {
                    sendMessage(it)
                }
            }

            else -> {
                if (!message.attachmentList.isNullOrEmpty()) {
                    retryAttachmentMessage(message)
                } else {
                    handleMediaMessage(message)
                }
            }
        }
    }

    override fun onSourceClicked(multipleSourceDetail: MultipleSourceDetail) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(multipleSourceDetail.text))
            startActivity(intent)
        } catch (e: Exception) {
            NCWAppUtils.showToast(this, "Unable to open the link")
        }
    }

    override fun onScrollToPosition(isScrollToPosition: Boolean) {
        chatRecyclerView.post {
            chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
        }
    }

    private fun retryAttachmentMessage(message: NCWMessage) {
        message.isRetry = false
        messageList.add(message)
        messageAdapter.notifyDataSetChanged()

        val timeStamp = System.currentTimeMillis()
        val payload = createPayload(
            "event://;LEARN_ATTRIBUTE_EVENT;ATTACHMENT::value=Media has been uploaded",
            "Attachment has been uploaded",
            timeStamp,
            message.attachmentList
        )
        sendMessageToBot(payload)
    }

    private fun handleMediaMessage(message: NCWMessage) {
        val selectedMediaUri: Uri = Uri.parse(message.message)
        val type = contentResolver.getType(selectedMediaUri)

        handleFileSelection(selectedMediaUri, type)
    }

    private fun launchCustomChromeTab(url: String) {
        customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    override fun carouselButtonAction(it: NCWCarouselButton?) {

        when (CarouselButtonType.fromValue(it?.type)) {
            CarouselButtonType.WEB -> {
                if (it?.title.equals("Sign In", ignoreCase = false)) {
                    launchCustomChromeTab(it?.url.toString())
                } else {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it?.url))
                        startActivity(intent) // Directly start the activity
                    } catch (e: Exception) {
                        Log.e("OpenURL", "Failed to open URL: ${it?.url}", e)
                        NCWAppUtils.showToast(this, "Unable to open the link")
                    }
                    Log.e("WEB", it?.title.toString())
                }
            }

            CarouselButtonType.CALL -> {
                makePhoneCall(it?.payload)
            }

            CarouselButtonType.POST_BACK -> {
                it?.payload?.let { it1 -> sendMessage(it1) }
            }

            else -> {
                // Handle unknown type (optional)
                Log.e("Carousel", "Unknown button type: ${it?.type}")
            }
        }

    }

    private fun makePhoneCall(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }


    /**
     * Handles quick reply option click by sending a message based on the option selected.
     *
     * @param option The selected quick reply option.
     */
    private fun onQuickReplyClicked(option: NCWQuickReplyOption?) {

        val content = option?.metadata
        if (checkForLogoutAction(content)) return
        option?.label?.takeIf { it.isNotEmpty() }?.let { label ->
            val timeStamp = System.currentTimeMillis()
            val payload = option.metadata?.let {
                checkForInitialMessage()
                createPayload(it, label, timeStamp)
            }
            chatViewModel.sendMessage(label, timeStamp)
            if (payload != null) {
                sendMessageToBot(payload)
            }
            messageInputField.text.clear()
        }

    }

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

        chatViewModel.errorFile.observe(this) { errorFile ->
            val position = messageList.indexOfLast { it.sender == TYPE_FORM }
            val item = messageList[position]
            item.formSchema?.schema?.forEach { targetComponent ->
                if (targetComponent.id == formComponent?.id) {
                    if (targetComponent.fileUpload?.size!! > 0) {
                        Log.e("targetComponent", "targetComponent " + targetComponent.fileUpload)
                        targetComponent.fileUpload?.forEach { updateFile ->
                            if (updateFile.title == errorFile?.uploadKeyPrefix) {
                                updateFile.isRetry = true
                            }
                        }
                    }
                    val updatedSchema = item.formSchema?.schema ?: emptyList()
                    val viewHolder = chatRecyclerView.findViewHolderForAdapterPosition(position)
                    if (viewHolder is NCWChatAdapter.FormViewHolder) {
                        formComponent?.let { viewHolder.updateFormAdapterData(updatedSchema, it) }
                    } else {
                        messageAdapter.notifyItemChanged(position)
                    }
                }
            }
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

        chatViewModel.feedbackResponse.observe(this) { feedback ->
            when (feedback) {
                is NCWState.Loading -> {
                    showProgressBar()
                }

                is NCWState.Success -> {
                    //messageAdapter.notifyDataSetChanged()
                }

                is NCWState.Error -> {
                    hideProgressBar()
                }

                else -> {

                }
            }


        }

        chatViewModel.loginResponse.observe(this) {
            handleApiCallback(it as NCWState<Any>)
        }
        chatViewModel.surveyRuleResponse.observe(this) {
            handleApiCallback(it as NCWState<Any>)
        }

        chatViewModel.logoutResponse.observe(this) {
            handleApiCallback(it as NCWState<Any>)
        }

        //chatViewModel.awsMessage.observeForever(awsMessageObserver)

        ncwAwsCredentialsViewModel.credentials.observe(this) {
            topic = "$CHAT_WIDGET/$botRefId/$conversationID"
            Log.e("Topic", "Topic Name " + topic)
            ncwAwsCredentialsViewModel.initializeAwsIotManager(chatViewModel, topic)
        }


        ncwAwsCredentialsViewModel.connectionStatus.observe(this) { status ->
            connectionStatus = status
            Log.e("Connection", "sss " + connectionStatus)
            when (status) {

                NCWConnectionStatus.CONNECTING.toString() -> {
                    connectionHeader.text = getString(R.string.connecting)
                    connectionHeader.setBackgroundColor(Color.YELLOW)
                    connectionHeader.setTextColor(Color.BLACK)
                    connectionHeader.visibility = View.VISIBLE
                    setUIState(false)
                }

                NCWConnectionStatus.CONNECTED.toString() -> {
                    connectionHeader.text = getString(R.string.connected)
                    connectionHeader.setBackgroundColor(Color.GREEN)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE
                    setUIState(isHistoryDisableInput)
                    if (NCWThemeUtils.getConversationID() == null || themeData?.isProActiveGreetings == false) {

                        if (!isHistoryChatAvialbale) {
                            sendProactiveMessage()
                        }
                    }

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
                    setUIState(false)
                }

                NCWConnectionStatus.RE_CONNECTED.toString() -> {
                    //connectionHeader.text = getString(R.string.reconnecting)
                    //connectionHeader.setBackgroundColor(Color.BLUE)
                    //connectionHeader.setTextColor(Color.WHITE)
                    //connectionHeader.visibility = View.VISIBLE
                    setUIState(true)
                }

                else -> {
                    // Unknown or other status
                    connectionHeader.text = getString(R.string.unknown)
                    connectionHeader.setBackgroundColor(Color.GRAY)
                    connectionHeader.setTextColor(Color.WHITE)
                    connectionHeader.visibility = View.VISIBLE
                    setUIState(false)
                }
            }
        }

        chatViewModel.endChatResponse.observe(this) { messages ->
            handleApiCallback(messages as NCWState<Any>)
        }
        chatViewModel.surveyResponse.observe(this) { messages ->
            handleApiCallback(messages as NCWState<Any>)
        }

    }

    private fun sendProactiveMessage() {

        if (themeData?.isProActiveGreetings == false) {
            themeData?.isProActiveGreetings = true
            themeData?.proActiveGreetings?.takeIf { it.isNotEmpty() }?.let { greetings ->
                val timeStamp = System.currentTimeMillis()
                val text = greetings[0]
                val label = PROACTIVE_GREETING
                val payload = createPayload(text, label, timeStamp)
                chatViewModel.sendMessageAPI(payload)
            }
        }
    }

    private fun checkProactiveMessageAvailable(): Boolean {
        return themeData?.proActiveGreetings?.isNotEmpty() ?: false
    }

    private val awsMessageObserver = Observer<String> { jsonMessage ->
        try {

            val response = Gson().fromJson(jsonMessage, NCWGenericChannelResponse::class.java)
            playBotSound()
            if (response!=null) {
                renderTheNormalMessage(response)
            }
        } catch (e: Exception) {
            Log.e("ParsingError", "Failed to parse JSON: ${e.localizedMessage}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(idleRunnable)
        messageSoundPlayer?.release()
        // Remove the observer to prevent memory leaks
       // chatViewModel.awsMessage.removeObserver(awsMessageObserver)

    }

    private fun refreshChat(eventData: EventData) {
     //   messageList.clear()
        val oldTopic = topic
        Log.e("OAUTH", eventData.authenticatedConversationId.toString())
        conversationID = eventData.authenticatedConversationId
        NCWThemeUtils.setConversationID(conversationID)
        eventData.userdetails?.let { NCWThemeUtils.setSignInUserDetails(it) }

        topic = "$CHAT_WIDGET/$botRefId/${eventData.authenticatedConversationId}"
        NCWAwsIotManager.switchTopic(oldTopic, topic, chatViewModel)
        getChatHistory()
    }

    private fun renderPillsMessage(eventData: EventData?, timestamp: Long) {
        val messagePill = when {
            eventData?.eventType == TYPE_QUEUE_POSITION && eventData.subType == SUB_TYPE_WAIT -> {
                "Queue Position: ${eventData.eventInfo.queuePosition}"
            }

            eventData?.eventType == TYPE_AGENT_EVENT && eventData.subType == SUB_TYPE_JOIN -> {
                agentName = eventData.eventInfo?.agentName
                agentAvatar = eventData.eventInfo?.agentAvatar
                ownerType = TYPE_AGENT
                "$agentName has joined the chat"

            }

            eventData?.eventType == TYPE_AGENT_EVENT && eventData.subType == SUB_TYPE_LEAVE -> {
                agentAvatar = null
                ownerType = TYPE_BOT
                "$agentName has left the chat"
            }

            eventData?.eventType == OAUTH && eventData.subType == SUB_TYPE_OAUTH -> {
                "Successfully Signed In!"
            }

            else -> ""
        }

        if (messagePill.isNotEmpty()) {
            val message = createPillsMessage(messagePill, timestamp)
            messageList.add(message)
            messageAdapter.notifyDataSetChanged()
            chatRecyclerView.post {
                chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
            }

        }

        if (eventData?.eventType == TYPE_AGENT_EVENT && eventData.subType == SUB_TYPE_TRANSFER) {
            val newAgentName = eventData.eventInfo?.agentName
            val newAgentAvatar = eventData.eventInfo?.agentAvatar
            val message = createPillsMessage(
                "$agentName has transferred the chat to $newAgentName",
                timestamp
            )
            messageList.add(message)
            agentName = newAgentName
            agentAvatar = newAgentAvatar
            ownerType = TYPE_AGENT
            val joinMessage = createPillsMessage("$agentName has joined the chat", timestamp)
            messageList.add(joinMessage)
            messageAdapter.notifyDataSetChanged()
            chatRecyclerView.post {
                chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
            }
        }

    }

    private fun createPillsMessage(message: String, timestamp: Long): NCWMessage {
        return NCWMessage(
            sender = TYPE_PILLS,
            timestamp = timestamp,
            message = message
        )
    }

    private fun renderTheSurveyMessage(response: NCWGenericChannelResponse?) {
        val gson = Gson()
        response?.customFields?.forEach { customField ->
            if (!customField.values.isNullOrEmpty()) {
                val surveyField: SurveyField = gson.fromJson(
                    customField.values[0],
                    object : TypeToken<SurveyField>() {}.type
                )
                createAndShowSurveyBottomSheet(
                    requestId = response.requestId ?: "",
                    surveyField = surveyField,
                    TYPE_SHOW_SURVEY,
                    onSubmit = { submitSurvey ->

                        val submitSurveyInfo =
                            submitSurvey.requestBody.eventData.eventInfo.submitSurveyInfo
                        val newMessage = NCWMessage(
                            sender = TYPE_EVENT,
                            timestamp = response.timestamp ?: System.currentTimeMillis(),
                            surveyField = surveyField,
                            requestID = response.requestId

                        )
                        messageList.add(newMessage)

                        if (!isSurveyRule) {
                            if (!isIdle) {
                                addLoader()
                            }
                            surveyField.submitSurveyInfo = submitSurveyInfo
                            messageAdapter.notifyDataSetChanged()
                            onScrollToPosition(true)
                        }
                        chatViewModel.hitSubmitSurveyRequestAPI(submitSurvey)
                    }, { requestId, label ->
                        val timeStamp = System.currentTimeMillis()
                        if (isSurveyRule) {
                            NCWThemeUtils.setConversationID(null)
                            themeData?.isProActiveGreetings = false
                            finish()
                            return@createAndShowSurveyBottomSheet
                        }
                        val isSkipValue = !isIdle

                        val textSkip =
                            "event://;SKIP_EVENT;resumeWorkflow::value=${isSkipValue}^$^requestId::value=${requestId}"
                        val payload = createPayload(textSkip, label, timeStamp)
                       if(isSkipValue) { addLoader() }
                        sendMessageToBot(payload)
                    }
                )

            }

        }
    }


    private fun createAndShowSurveyBottomSheet(
        requestId: String,
        surveyField: SurveyField?,
        from: String,
        onSubmit: (SubmitSurveyRequest) -> Unit,
        onSkipSurvey: (String, String) -> Unit,
    ) {
        val bottomSheet = NCWSurveyBottomSheet(
            requestId,
            surveyField,
            conversationID ?: "",
            botRefId ?: "",
            from,
            onSubmit,
            onSkipSurvey
        )
        bottomSheet.show(supportFragmentManager, "SurveyOptionsBottomSheet")
    }


    private fun renderTheFormMessage(response: NCWGenericChannelResponse?) {
        val gson = Gson()

        response?.customFields?.forEach { customField ->
            if (customField.name == "FORM_SCHEMA" && !customField.values.isNullOrEmpty()) {
                val formSchemas: List<FormSchema> = gson.fromJson(
                    customField.values[0],
                    object : TypeToken<List<FormSchema>>() {}.type
                )
                val formSchemasModel = formSchemas[0]
                formSchemasModel.requestId=response.requestId
                val newMessages = NCWMessage(
                    sender = TYPE_FORM,
                    timestamp = System.currentTimeMillis(),
                    formSchema = formSchemasModel,
                    agentAvatar = agentAvatar
                )
                addSingleMessage(newMessages)

            }

        }
    }

    private fun renderTheNormalMessage(response: NCWGenericChannelResponse) {
        playBotSound()
        var type: String = ""
        if (response?.customPayload?.CHUNK_INDEX != null &&
            (
                    (response.customPayload.CHUNK_INDEX.toInt() == 0 && response.customPayload.CHUNK_STATUS == "IN-PROGRESS") ||
                            (response.customPayload.CHUNK_INDEX.toInt() > 0 &&
                                    (response.customPayload.CHUNK_STATUS == "SUCCESS" || response.customPayload.CHUNK_STATUS == "IN-PROGRESS"))
                    )
        ) {
            type = NCWAppConstant.STREAMING
            if (isLoaderActive)
                removeStreamLoader()
        } else {
            type = NCWAppConstant.NORMAL
        }
        val newMessages =
            response.attachments?.mapIndexedNotNull { index, attachment ->
                mapAttachmentToMessage(
                        attachment,
                        response.requestId?:"",
                        type,
                        index,
                        response.customPayload
                    )
            } ?: emptyList()

        if (newMessages.isNotEmpty()) {
            newMessages.forEachIndexed { index, message ->
                message.isSameTimeMessage = index == 0
            }
            val customPayload = response.customPayload
            if (customPayload?.CHUNK_INDEX != null && customPayload.CHUNK_INDEX.toInt() == 0 && customPayload.CHUNK_STATUS.equals(
                    "IN-PROGRESS")) {
                Log.e("Streaming Chunk", "Streaming Chunk")
                for (i in newMessages.indices) {
                    updateStreamMessage(
                        newMessages[i],
                        chunkIndex = customPayload.CHUNK_INDEX.toInt(),
                        chunkStatus = customPayload.CHUNK_STATUS ?: ""
                    )
                }
            } else if (customPayload?.CHUNK_INDEX != null && customPayload.CHUNK_INDEX.toInt() > 0 && (customPayload.CHUNK_STATUS.equals(
                    "SUCCESS"
                ) || customPayload.CHUNK_STATUS.equals("IN-PROGRESS"))
            ) {
                Log.e("Streaming Chunk", "Streaming Chunk")
                for (i in newMessages.indices) {
                    updateStreamMessage(
                        newMessages[i],
                        chunkIndex = customPayload.CHUNK_INDEX.toInt(),
                        chunkStatus = customPayload.CHUNK_STATUS ?: ""
                    )
                }
            } else if (customPayload?.CHUNK_INDEX != null && customPayload.CHUNK_INDEX.toInt() == 0 && customPayload.CHUNK_STATUS.equals(
                    "SUCCESS"
                )
            ) {
                Log.e("Streaming Chunk", "Streaming Chunk Not")
                CoroutineScope(Dispatchers.Main).launch {
                    updateMessageList(newMessages, response)
                }
            } else {
                Log.e("Streaming Chunk", "Streaming Chunk Not")
                CoroutineScope(Dispatchers.Main).launch {
                    updateMessageList(newMessages, response)
                }

            }

        }
        else{
            renderEventAndCustomFields(response)
        }

       /* if (response.triggerType == TYPE_EVENT) {
            val eventData = response.eventObject?.eventData
            renderPillsMessage(eventData, response.timestamp ?: System.currentTimeMillis())

            if (eventData?.eventType == TYPE_AGENT_EVENT && eventData.subType == SUB_TYPING) {
                if (!checkLoaderRunning()) {
                    addLoader()
                    messageAdapter.notifyDataSetChanged()
                    onScrollToPosition(true)
                }

            }
            else  if (eventData?.eventType == OAUTH && eventData.subType == SUB_TYPE_OAUTH) {
                refreshChat(response.eventObject.eventData)
            }
        }

        if (response.customFields?.isNotEmpty() == true) {
            for (customField in response.customFields) {
                when (CustomFieldName.fromValue(customField.name)) {
                    CustomFieldName.FORM_SCHEMA -> {
                        removeLoader()
                        renderTheFormMessage(response)
                    }

                    CustomFieldName.SURVEY_SCHEMA -> {
                        renderTheSurveyMessage(response)

                    }

                    CustomFieldName.DISABLE_INPUT_FIELD, CustomFieldName.DISABLE_CHAT_INPUT -> {
                        val isDisabled = customField.values?.get(0) == "true"
                        setUIState(!isDisabled)
                        if (CustomFieldName.fromValue(customField.name) == CustomFieldName.DISABLE_INPUT_FIELD) {
                            isDisableInput = !isDisabled
                        }
                    }


                    CustomFieldName.END_CHAT -> {

                    }

                    else -> {

                    }
                }
            }
        } else {
        }*/
    }

    private fun renderEventAndCustomFields(response: NCWGenericChannelResponse) {
        if (response.triggerType == TYPE_EVENT) {
            val eventData = response.eventObject?.eventData
            renderPillsMessage(eventData, response.timestamp ?: System.currentTimeMillis())

            if (eventData?.eventType == TYPE_AGENT_EVENT && eventData.subType == SUB_TYPING) {
                if (!checkLoaderRunning()) {
                    addLoader()
                    messageAdapter.notifyDataSetChanged()
                    onScrollToPosition(true)
                }

            }
            else  if (eventData?.eventType == OAUTH && eventData.subType == SUB_TYPE_OAUTH) {
                refreshChat(response.eventObject.eventData)
            }
        }

        if (response.customFields?.isNotEmpty() == true) {
            for (customField in response.customFields) {
                when (CustomFieldName.fromValue(customField.name)) {
                    CustomFieldName.FORM_SCHEMA -> {
                        removeLoader()
                        renderTheFormMessage(response)
                    }

                    CustomFieldName.SURVEY_SCHEMA -> {
                        handler.removeCallbacks(idleRunnable)
                        renderTheSurveyMessage(response)

                    }

                    CustomFieldName.DISABLE_INPUT_FIELD, CustomFieldName.DISABLE_CHAT_INPUT -> {
                        val isDisabled = customField.values?.get(0) == "true"
                        setUIState(!isDisabled)
                        if (CustomFieldName.fromValue(customField.name) == CustomFieldName.DISABLE_INPUT_FIELD) {
                            isDisableInput = !isDisabled
                        }
                    }


                    CustomFieldName.END_CHAT -> {

                    }

                    else -> {

                    }
                }
            }
        } else {
        }
    }

    private fun setUIState(enabled: Boolean) {

        sendMessageIcon.isEnabled = enabled
        messageInputField.isEnabled = enabled
        attachmentIcon.isEnabled = enabled
        ivMenuOption.isEnabled = enabled
        val alphaValue = if (enabled) 1f else 0.5f
        sendMessageIcon.alpha = alphaValue
        messageInputField.alpha = alphaValue
        attachmentIcon.alpha = alphaValue
        if (!enabled) {
            val colorFilter = PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
            sendMessageIcon.colorFilter = colorFilter
            attachmentIcon.colorFilter = colorFilter
            ivMenuOption.colorFilter = colorFilter
        } else {
            sendMessageIcon.clearColorFilter()
            attachmentIcon.clearColorFilter()
            ivMenuOption.clearColorFilter()
        }

    }


    // Helper function to map attachments to NCWMessage
    private fun mapAttachmentToMessage(
        attachment: NCWAttachment,
        requestId: String,
        type: String,
        index: Int,
        customPayload: NCWCustomPayload?
    ): NCWMessage? {
        val attach = attachment.attachment ?: return null
        val messageType = attach.type?.let { MessageType.fromTypeName(it) } ?: return null

        if (type == NCWAppConstant.NORMAL && messageType != MessageType.MULTISOURCE) {
            if (attach.text.isNullOrEmpty() &&
                attach.elements.isNullOrEmpty() &&
                attach.thumbnailUrl.isNullOrEmpty() &&
                attach.largeImageUrl.isNullOrEmpty() &&
                attach.quickReply == null && attach.multipleSourceDetails.isEmpty()
            ) {
                return null
            }
        }
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
            quickReply = attach.quickReply,
            requestID = requestId,
            feedbackValue = attach.feedbackValue,
            isReviewEnabled = attach.isReviewEnabled,
            agentAvatar = agentAvatar,
            attachmentIndex = index,
            id = attachment.attachment.id,
            multipleSourceDetails = if (messageType == MessageType.MULTISOURCE) attach.multipleSourceDetails else arrayListOf(),
            customPayload = customPayload
        )
    }


    // Overloaded helper function for a single message
    private fun updateMessageList(newMessage: NCWMessage) {
        addSingleMessage(newMessage)
        if (ownerType == TYPE_BOT)
            addLoader()
    }

    private fun addSingleMessage(newMessage: NCWMessage) {
        messageList.add(newMessage)
        messageAdapter.notifyDataSetChanged()
        //chatRecyclerView.scrollToPosition(messageList.size)
        chatRecyclerView.post {
            chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
        }

    }

    private fun updateStreamMessage(
        streamMessage: NCWMessage,
        chunkIndex: Int,
        chunkStatus: String
    ) {
        addStreamMessages(streamMessage, chunkIndex = chunkIndex, chunkStatus = chunkStatus)
    }

    private suspend fun updateMessageList(newMessages: List<NCWMessage>, response: NCWGenericChannelResponse) {

        if (NCWThemeUtils.getThemeData()?.streamOnChatWidget?.enabled == true) {
            removeStreamLoader()

            withContext(Dispatchers.Main) {
                for (message in newMessages) {
                    if (message.type == MessageType.TEXT) {
                        message.message?.let { fullMessage ->
                            val chunks = splitIntoChunks(
                                fullMessage,
                                NCWThemeUtils.getThemeData()?.streamOnChatWidget?.chunkSize ?: 8
                            )
                            for (chunk in chunks) {
                                val chunkMessage = message.copy(message = chunk)
                                messageAdapter.updateOrAppendMessage(chunkMessage, true)
                                delay(300) // This will wait before processing next chunk
                            }
                        }
                    } else {
                        messageList.addAll(listOf(message))
                        messageAdapter.notifyDataSetChanged()
                        delay(100)
                    }
                }
            }
        } else {
            val typingIndicatorEnabled = themeData?.typingIndicator?.enabled ?: false
            if (!typingIndicatorEnabled) {
                addMessages(newMessages)
                return
            }

            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - loaderAddedTime
            val minTime = themeData?.typingIndicator?.minTime ?: 1000L

            if (isLoaderActive && elapsedTime < minTime) {
                Log.e("CallActive", "Waiting for minTime: $minTime")
                withContext(Dispatchers.Main) {
                    delay(minTime - elapsedTime)
                    if (isLoaderActive) {
                        safelyRemoveLoader(newMessages)
                        Log.e("CallActive", "Removed after minTime")
                    }
                }
            } else if (isLoaderActive) {
                Log.e("CallActive", "Removed loader immediately")
                safelyRemoveLoader(newMessages)
            } else {
                Log.e("CallActive", "Loader already removed, updating messages only")
                addMessages(newMessages)
            }
        }
        renderEventAndCustomFields(response)
    }


    /*   private fun updateMessageList(newMessages: List<NCWMessage>,response: NCWGenericChannelResponse) {
        Log.e("NCWThemeUtils.getThemeData()?.streamOnChatWidget?.enabled ","s "+NCWThemeUtils.getThemeData()?.streamOnChatWidget?.enabled)
       //
        if ( NCWThemeUtils.getThemeData()?.streamOnChatWidget?.enabled == false) {
            Log.e("NCWThemeUtils.getThemeData()?.str ","ifffffffffff "+NCWThemeUtils.getThemeData()?.streamOnChatWidget?.enabled)
            removeStreamLoader()
            CoroutineScope(Dispatchers.Main).launch {

                for (message in newMessages) {
                    Log.e("NCWThemeUtils.getThemeData()?.str ","Start Message ")
                    if (message.type == MessageType.TEXT) {
                        Log.e("NCWThemeUtils.getThemeData()?.str ","Start TEXT ")
                        message.message?.let { fullMessage ->
                            val chunks = splitIntoChunks(
                                fullMessage,
                                NCWThemeUtils.getThemeData()?.streamOnChatWidget?.chunkSize ?: 8
                            )
                            for (chunk in chunks) {
                                val chunkMessage = message.copy(message = chunk)
                                messageAdapter.updateOrAppendMessage(chunkMessage, true)
                                delay(300)
                            }
                        }
                    } else {
                        Log.e("NCWThemeUtils.getThemeData()?.str ","Start Otherrr ")
                        messageList.addAll(listOf(message))
                        messageAdapter.notifyDataSetChanged()
                        delay(100)
                    }
                }
            }
        }

        else {

            Log.e("NCWThemeUtils.getThemeData()?.str ","elssssssss "+NCWThemeUtils.getThemeData()?.streamOnChatWidget?.enabled)
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

        Log.e("NCWThemeUtils.getThemeData()?.str ","Start Otside ")
        renderEventAndCustomFields(response)

    }
*/
    // Helper function to split a message into chunks of 8 words
    private fun splitIntoChunks(text: String, wordsPerChunk: Int): List<String> {
        val words = text.split("(?<=\\s)".toRegex()) // Splitting while keeping spaces
        val chunks = mutableListOf<String>()
        val chunkBuilder = StringBuilder()
        var wordCount = 0

        for ((index, word) in words.withIndex()) {
            chunkBuilder.append(word) // Append word with its existing space
            wordCount++

            if (wordCount == wordsPerChunk || index == words.size - 1) {
                chunks.add(chunkBuilder.toString()) // Add chunk without trimming
                chunkBuilder.clear()
                wordCount = 0
            }
        }
        return chunks
    }

    // Helper function to add messages and scroll to the latest
    private fun addMessages(newMessages: List<NCWMessage>) {
        messageList.addAll(newMessages)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.post {
            chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
        }

    }


    private fun addStreamMessages(newMessages: NCWMessage, chunkIndex: Int, chunkStatus: String) {
        Log.e("NCWMessage111111", "Received Message: ${newMessages.message}, CHUNK_INDEX: $chunkIndex, CHUNK_STATUS: $chunkStatus")
        val messageId = newMessages.requestID ?: return
        if (chunkIndex != -1 && newMessages.type == MessageType.TEXT) {
            val chunkList = messageChunksMap.getOrPut(messageId) { mutableListOf() }
            chunkList.add(newMessages)
            chunkList.sortBy { chunkIndex }
            // Create a partial merged message for streaming effect
            val partialMessage = mergeChunks(chunkList)
            // Update UI in real-time
            Handler(Looper.getMainLooper()).post {
                messageAdapter.updateOrAppendMessage(partialMessage, false)
            }

            // If the chunk is the last one (CHUNK_STATUS = "COMPLETED"), remove stored chunks
           /* if (chunkStatus == "SUCCESS") {
                messageChunksMap.remove(messageId)
            }*/
        }else{
            // If not a chunked message, update directly
            messageAdapter.updateOrAppendMessage(newMessages, false)
        }

        /* chatRecyclerView.post {
             chatRecyclerView.post(messageList.size - 1)
         }*/

    }

    private fun mergeChunks(chunkList: List<NCWMessage>): NCWMessage {
        val mergedText = chunkList.joinToString("") { it.message ?: "" }
        val firstChunk = chunkList.first()
        val isReviewEnabled = chunkList.any { it.isReviewEnabled }
        return firstChunk.copy(message = mergedText, isReviewEnabled = isReviewEnabled)
    }

    private fun safelyRemoveLoader(newMessages: List<NCWMessage>) {
        if (!isLoaderActive) return // Prevent redundant calls
        removeLoader()
        messageList.addAll(newMessages)
        messageAdapter.notifyDataSetChanged()
        chatRecyclerView.post {
            chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
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
                timestamp = loaderAddedTime,
                agentAvatar = agentAvatar
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

    private fun checkLoaderRunning(): Boolean {
        return messageList.isNotEmpty() && messageList.last().sender == TYPE_INDICATOR
    }

    private fun updateLoaderTime() {
        loaderAddedTime = System.currentTimeMillis()

    }

    private fun removeStreamLoader() {
        isLoaderActive = false
        hideKeyboard(this)
        if (messageList.isNotEmpty() && messageList.last().sender == TYPE_INDICATOR) {
            val lastIndex = messageList.lastIndex
            messageList.removeAt(lastIndex)
            messageAdapter.notifyItemRemoved(lastIndex)
        }
    }

    private fun handleApiCallback(response: NCWState<Any>) {
        when (response) {
            is NCWState.Loading -> {
                showProgressBar()
            }

            is NCWState.Success -> {
                onApiSuccess(response.data, response.apiConstant)

            }

            is NCWState.Error -> {
                hideProgressBar()
            }

            is NCWState.SendMessageError<*, *> -> {

                val payload = response.payload
                if (payload is NCWWebhookPayload) {
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
                                        message.isRetry =
                                            true  // Update retry flag for matching attachment
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
                } else if (payload is NCWSignedUrlPayload) {

                    val position = messageList.indexOfLast { it.title == payload.uploadKeyPrefix }
                    if (position != -1) {
                        val item = messageList[position]
                        item.isRetry = true
                        messageAdapter.notifyItemChanged(position)
                    }
                }
                hideProgressBar()
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

        checkNetworkAndExecute {
            val supportedExtensions = when (attachmentType) {
                TYPE_ATTACHMENT -> themeData?.fileSharing?.list?.map { it.removePrefix(".") }
                    ?: emptyList()

                else -> formComponent?.config?.attachmentTypes?.map { it.lowercase() }
                    ?: emptyList()
            }
            val bottomSheet = NCWMediaOptionsBottomSheet(
                onCameraClick = { showCameraVideoOption() },
                onGalleryClick = { openGallery() },
                onFileClick = { openFile() },
                supportedExtensions
            )
            bottomSheet.show(supportFragmentManager, "MediaOptionsBottomSheet")
        }
    }

    private fun showCameraVideoOption() {
        NCWAppUtils.showMediaOptionDialog(this,
            imageClick = { openCamera() },
            videoClick = { openVideoCamera() }
        )
    }

    private fun openVideoCamera() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (takeVideoIntent.resolveActivity(packageManager) != null) {
            videoLauncher.launch(takeVideoIntent)
        }
    }

    private val videoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            videoUri?.let { uri ->
                fileSend = NCWImageUtils.getVideoFileFromUri(this, uri)
                val type = fileSend?.let { NCWAppUtils.getFileContentType(it) }

                if (attachmentType == TYPE_ATTACHMENT) {
                    if (!validateFile(fileSend)) return@registerForActivityResult

                    addMediaMessage(fileSend, uri, MessageType.VIDEO)
                    fileSend?.let { getPreSignedUrl(type, it.name) }
                } else {
                    if (!validateFormAttachment(fileSend)) return@registerForActivityResult

                    val mObj = fileSend?.let { MultiFileModel(type ?: "", it, it.name) }
                    if (mObj != null) {
                        mMultipleFile.add(mObj)
                    }
                    updateFormSchema()
                    chatViewModel.uploadFilesSequentially(mMultipleFile)

                }
            }

        }
    }

    private fun openCamera() {
        val imageFile = createImageFile()
        photoUri = imageFile?.let {
            FileProvider.getUriForFile(
                this, "${applicationContext.packageName}.fileprovider",
                it
            )
        }
        photoUri?.let { cameraLauncherMain.launch(it) }
    }


    private val cameraLauncherMain =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (!success) {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            val file = photoUri?.let { NCWAppUtils.getFileFromUri(this, it) }
            fileSend = file ?: return@registerForActivityResult

            val fileType = fileSend?.let { NCWAppUtils.getFileContentType(it) }

            when (attachmentType) {
                TYPE_ATTACHMENT -> {
                    if (!validateFile(fileSend)) return@registerForActivityResult

                    photoUri?.let { uri ->
                        addMediaMessage(fileSend, uri, MessageType.IMAGE)
                    }
                    fileSend?.let { getPreSignedUrl(fileType, it.name) }
                }

                else -> {
                    if (!validateFormAttachment(fileSend)) return@registerForActivityResult


                    // handleFileSelection(uri, mimeType, isGallery = true)
                    val mObj = fileSend?.let { MultiFileModel(fileType ?: "", it, it.name) }
                    if (mObj != null) {
                        mMultipleFile.add(mObj)
                    }
                    updateFormSchema()
                    chatViewModel.uploadFilesSequentially(mMultipleFile)

                }
            }

        }


    private fun showLimitExceedPopup(messageIssue: String) {

        handleSessionTimeout(
            getString(R.string.limit_exceed),
            messageIssue,
            getString(R.string.okay),
            SIZE_LIMIT
        )
    }


    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat(DATE_FORMAT).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }


    // Common function to validate file size and type
    private fun validateFormAttachment(file: File?): Boolean {

        val supportedExtensions =
            formComponent?.config?.attachmentTypes?.map { it.lowercase() } ?: emptyList()
        Log.e("supportedExtensions", "supportedExtensions: $supportedExtensions")

// Normalize the file extension (remove the dot and convert to lowercase)
        val fileExtension = file?.extension?.lowercase()?.removePrefix(".")

        Log.e("fileExtension", "fileExtension: $fileExtension")

// Check if the file extension is supported (case insensitive and without the dot)
        if (!supportedExtensions.contains(fileExtension)) {
            handleSessionTimeout(
                getString(R.string.unsupported_file),
                "The selected file type is not supported. Please choose a supported file types such as: ${formComponent?.config?.attachmentTypes}.",
                getString(R.string.okay),
                SIZE_LIMIT
            )
            return false
        }
        // Validate file size
        if (!file?.let { formComponent?.let { it1 -> isFormSizeValid(it1, it) } }!!) {
            val maxUploadSizeAllowedMB = formComponent?.config?.maxUploadSizeAllowed?.let {
                "$it MB"
            } ?: "N/A"
            val messageIssue = getString(R.string.upload_file_max_size, maxUploadSizeAllowedMB)

            showLimitExceedPopup(messageIssue)
            return false
        }

        return true
    }

    // Common function to validate file size and type
    private fun validateFile(file: File?): Boolean {

        // Validate file type
        val supportedExtensions = themeData?.fileSharing?.list ?: emptyList()
        Log.e("deftt", "asasassa " + supportedExtensions)
        val fileExtension =
            file?.extension?.lowercase()?.let { if (!it.startsWith(".")) ".$it" else it }
        Log.e("deftt", "fileExtension " + fileExtension)
        if (fileExtension !in supportedExtensions) {
            handleSessionTimeout(
                getString(R.string.unsupported_file),
                "The selected file type is not supported. Please choose a supported file types such as: ${supportedExtensions}.",
                getString(R.string.okay),
                SIZE_LIMIT
            )

            return false
        }
        // Validate file size
        if (!isFileSizeValid(this, file?.length(), themeData?.fileSharing?.fileSize)) {
            val maxSize = themeData?.fileSharing?.fileSize?.let(NCWAppUtils::formatFileSize)

            val messageIssue = getString(R.string.upload_file_max_size, maxSize ?: "N/A")
            showLimitExceedPopup(messageIssue)
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
        if (!validateFile(fileSend)) return

        checkForPreviousQuickReply()

        when {
            mimeType == null -> Log.e("FileSelection", "Unknown MIME type")
            mimeType.startsWith("image/") -> addMediaMessage(
                fileSend,
                selectedMediaUri,
                MessageType.IMAGE
            )

            mimeType.startsWith("video/") -> addMediaMessage(
                fileSend,
                selectedMediaUri,
                MessageType.VIDEO
            )

            else -> addDocMessage(fileSend, selectedMediaUri, MessageType.FILE)
        }

        fileSend?.let {
            getPreSignedUrl(mimeType, it.name)

        }
    }

    // Gallery selection
    private fun openGallery() {
        val galleryIntent = if (attachmentType == TYPE_ATTACHMENT) {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/* video/*"
            }
        } else if (formComponent?.config?.fileUploadType == UPLOAD_FILE_MULTIPLE) {
            Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                type = "*/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
            }
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/* video/*"
            }
        }
Log.e("formComponent?.config?.fileUploadType","formComponent?.config?.fileUploadType "+formComponent?.config?.fileUploadType)
        if (attachmentType == TYPE_ATTACHMENT){
            galleryLauncher.launch(galleryIntent)
        } else {
            galleryMultipleLauncher.launch(galleryIntent)
        }
    }


    private val galleryMultipleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val clipData = result.data?.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        val mimeType = contentResolver.getType(uri)
                        val fileSend = NCWFilePath().getPath(this, uri)?.let { File(it) }

                        val mObj = fileSend?.let { MultiFileModel(mimeType!!, it, fileSend.name) }
                        if (mObj != null) {
                            mMultipleFile.add(mObj)
                        }
                    }
                    if (!validateFormAttachment(mMultipleFile)) {
                        mMultipleFile.clear()
                        return@registerForActivityResult
                    }
                    checkSupportedFilesForm()

                    if (mMultipleFile.size>0) {
                        updateFormSchema()
                        chatViewModel.uploadFilesSequentially(mMultipleFile)
                    }
                } else {
                    result.data?.data?.let { uri ->
                        val mimeType = contentResolver.getType(uri)
                        val fileSend = NCWFilePath().getPath(this, uri)?.let { File(it) }
                        isMultipleFile = true
                        // handleFileSelection(uri, mimeType, isGallery = true)
                        val mObj = fileSend?.let { MultiFileModel(mimeType!!, it, fileSend.name) }
                        if (mObj != null) {
                            mMultipleFile.add(mObj)
                        }
                    }
                    if (!validateFormAttachment(mMultipleFile)) {
                        mMultipleFile.clear()
                        return@registerForActivityResult
                    }
                    checkSupportedFilesForm()
                    if (mMultipleFile.size>0) {
                        updateFormSchema()
                        chatViewModel.uploadFilesSequentially(mMultipleFile)
                    }
                }
            }
        }


    private val fileMultipleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val clipData = result.data?.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        val mimeType = contentResolver.getType(uri)
                        // val fileSend =   NCWFilePath().getPath(this, uri)?.let { File(it) }
                        val fileSend = NCWImageUtils.getFileFromUri(this, uri)
                        val mObj = fileSend?.let { MultiFileModel(mimeType!!, it, fileSend.name) }
                        if (mObj != null) {
                            mMultipleFile.add(mObj)
                        }

                    }
                    if (!validateFormAttachment(mMultipleFile)) {
                        mMultipleFile.clear()
                        return@registerForActivityResult
                    }
                    checkSupportedFilesForm()
                    if (mMultipleFile.size>0) {
                        updateFormSchema()
                        chatViewModel.uploadFilesSequentially(mMultipleFile)
                    }


                } else {
                    // Handle single selected file
                    result.data?.data?.let { uri ->
                        val mimeType = contentResolver.getType(uri)
                        val fileSend = NCWImageUtils.getFileFromUri(this, uri)

                        isMultipleFile = true
                        val mObj = fileSend?.let { MultiFileModel(mimeType!!, it, fileSend.name) }
                        if (mObj != null) {
                            mMultipleFile.add(mObj)
                        }

                    }
                    if (!validateFormAttachment(mMultipleFile)) {
                        mMultipleFile.clear()
                        return@registerForActivityResult
                    }
                    checkSupportedFilesForm()
                    if (mMultipleFile.size>0) {
                        updateFormSchema()
                        chatViewModel.uploadFilesSequentially(mMultipleFile)
                    }
                }
            }
        }

    private fun checkSupportedFilesForm() {


        val supportedExtensions =
            formComponent?.config?.attachmentTypes?.map { it.lowercase() } ?: emptyList()

        val (supportedFiles, unsupportedFiles) = mMultipleFile.partition { multiFileModel ->
            val fileExtension = multiFileModel.file.extension.lowercase().removePrefix(".")
            supportedExtensions.contains(fileExtension)
        }
        mMultipleFile = ArrayList(supportedFiles)
        if (unsupportedFiles.isNotEmpty()) {
            handleSessionTimeout(
                getString(R.string.unsupported_file),
                "The selected file type(s) are not supported. Please choose supported file types such as: ${formComponent?.config?.attachmentTypes}.",
                getString(R.string.okay),
                SIZE_LIMIT
            )
        }
    }

    private fun validateFormAttachment(mMultipleFile: List<MultiFileModel>?): Boolean {

        val formComponent = formComponent ?: return true

        // Perform validation
        val isValid =
            mMultipleFile?.let { NCWAppUtils.validateMultipleFormAttachment(formComponent, it) }

        if (!isValid!!) {
            val maxUploadSizeAllowedMB =
                formComponent.config?.maxUploadSizeAllowed?.let { "$it MB" } ?: "N/A"

            // Construct the message for exceeding the limit
            val messageIssue = getString(R.string.upload_files_max_size, maxUploadSizeAllowedMB)

            // Show the popup with the limit exceeded message
            showLimitExceedPopup(messageIssue)
            return false
        }

        return true
    }


    private fun updateFormSchema() {
        isMultipleFile = true
        mMultipleFile.forEach { files ->
            val position = messageList.indexOfLast { it.sender == TYPE_FORM }
            val item = messageList.getOrNull(position) ?: return@forEach

            item.formSchema?.schema?.forEach { targetComponent ->
                if (targetComponent.id == formComponent?.id) {
                    targetComponent.fileUpload = targetComponent.fileUpload ?: ArrayList()
                    val fileUpload = FileUploadData(
                        null,
                        null,
                        files.fileName,
                        files.file.length(),
                        mimeType = files.mimeType,
                        file = files.file


                    )
                    targetComponent.fileUpload?.add(fileUpload)
                }
            }

            val updatedSchema = item.formSchema?.schema ?: emptyList()
            val viewHolder = chatRecyclerView.findViewHolderForAdapterPosition(position)
            if (viewHolder is NCWChatAdapter.FormViewHolder) {
                formComponent?.let { viewHolder.updateFormAdapterData(updatedSchema, it) }
            } else {
                messageAdapter.notifyItemChanged(position)
            }
        }
    }

    // File selection (PDF, DOC, etc.)
    private fun openFile() {

        if (attachmentType == TYPE_ATTACHMENT) {
            val fileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "*/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            fileLauncher.launch(fileIntent)
        } else {
            formComponent?.let { openFormPicker(component = it) }
        }
    }

    private fun openFormPicker(component: Component) {
        val attachmentTypes = component.config?.attachmentTypes.orEmpty()
        val mimeTypes = attachmentTypes.mapNotNull {
            when (it.uppercase()) {
                "PNG" -> "image/png"
                "JPG", "JPEG" -> "image/jpeg"
                "GIF" -> "image/gif"
                "MP4" -> "video/mp4"
                "PDF" -> "application/pdf"
                "TXT" -> "text/plain"
                else -> "image/png"
            }
        }.toMutableList()

        if (mimeTypes.isEmpty()) {
            mimeTypes.add("*/*")
        }
        //putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes.toTypedArray())
        Log.e("formComponent?.config?.fileUploadType","mime "+mimeTypes)
        Log.e("formComponent?.config?.fileUploadType","formComponent?.config?.fileUploadType "+formComponent?.config?.fileUploadType)
        val fileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)

            if (formComponent?.config?.fileUploadType == UPLOAD_FILE_MULTIPLE) {
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
        }

        fileMultipleLauncher.launch(fileIntent)
    }


    // Handle the result of the gallery selection
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val type = contentResolver.getType(result.data?.data!!)
                val selectedMediaUri: Uri? = result.data?.data!!
                if (attachmentType == TYPE_ATTACHMENT) {
                    handleFileSelection(selectedMediaUri, type, isGallery = true)
                } else {
                    handleFormFileSelection(selectedMediaUri, type, isGallery = true)
                }

            }
        }

    // Handle the result of file selection (PDF, DOC, etc.)
    private val fileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val type = contentResolver.getType(result.data?.data!!)
                val selectedMediaUri: Uri? = result.data?.data!!
                if (attachmentType == TYPE_ATTACHMENT) {
                    handleFileSelection(selectedMediaUri, type)
                } else {
                    handleFormFileSelection(selectedMediaUri, type)
                }
            }
        }

    private fun handleFormFileSelection(
        selectedMediaUri: Uri?,
        mimeType: String?,
        isGallery: Boolean = false
    ) {
        if (selectedMediaUri == null) {
            Log.e("FileSelection", "No media selected")
            return
        }
        fileSend = if (isGallery) {
            NCWFilePath().getPath(this, selectedMediaUri)?.let { File(it) }
        } else {
            NCWImageUtils.getFileFromUri(this, selectedMediaUri)
        }

        if (fileSend == null) {
            Log.e("FileSelection", "Failed to get file from URI")
            return
        }
        if (!validateFormAttachment(fileSend)) return
        fileSend?.let {
            getPreSignedUrl(mimeType, it.name)

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
    private fun addMediaMessage(file: File?, uri: Uri, type: MessageType) {

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
                hideProgressBar()
                if (!isDisableInput) {
                    isDisableInput = true
                    setUIState(true)
                }

                if (idleTimeInMillis > 0) {
                    resetIdleTimer()
                    isIdle = false
                }

            }

            NCWRoutes.ROUTE_GET_CHAT -> {
                val response = apiResponse as NCWGetChatHistoryResponse
                Log.e("ROUTE_GET_MQTT_CREDENTIALS", "ROUTE_GET_CHAT")
                if (response != null && response.responses.size > 0) {
                    isHistoryChatAvialbale = true
                    messageList.clear()
                    parseHistoryItems(response.responses)
                } else {
                    isHistoryChatAvialbale = false
                    loadInitialMessages()
                }
                hideProgressBar()
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

                if (attachmentType == TYPE_ATTACHMENT) {

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

                    val position = messageList.indexOfLast { it.title == response.title }
                    if (position != -1) {
                        val item = messageList[position]
                        item.attachmentList = attachmentList
                        when (mediaType) {
                            TYPE_IMAGE -> item.largeImageUrl = response.url
                            TYPE_VIDEO -> item.thumbnailUrl = response.url
                            TYPE_FILE -> item.fileUrl = response.url
                        }
                    }

                    Log.e("attachmentList", "attachmentList" + attachmentList)
                    val timeStamp = System.currentTimeMillis()
                    val payload = createPayload(
                        "event://;LEARN_ATTRIBUTE_EVENT;ATTACHMENT::value=Media has been uploaded",
                        "Attachment has been uploaded",
                        timeStamp,
                        attachmentList
                    )
                    sendMessageToBot(payload)

                } else {
                    hideProgressBar()
                    val position = messageList.indexOfLast { it.sender == TYPE_FORM }
                    val item = messageList[position]
                    var isUpdated = false
                    item.formSchema?.schema?.forEach { targetComponent ->
                        if (targetComponent.id == formComponent?.id) {
                            if (targetComponent.fileUpload?.size!! > 0) {
                                targetComponent.fileUpload?.forEach { updateFile ->
                                    if (updateFile.title == response.title) {
                                        updateFile.fileUrl = response.url
                                        updateFile.mediaType = mediaType
                                        updateFile.isRetry = false
                                        isUpdated=true
                                    }
                                }
                            }
if (isUpdated) {
    val updatedSchema = item.formSchema?.schema ?: emptyList()
    val viewHolder =
        chatRecyclerView.findViewHolderForAdapterPosition(position)
    if (viewHolder is NCWChatAdapter.FormViewHolder) {
        formComponent?.let {
            viewHolder.updateFormAdapterData(
                updatedSchema,
                it
            )
        }
    } else {
        messageAdapter.notifyItemChanged(position)
    }
}
                        }
                    }

                    /*else
                    {
                        item.formSchema?.schema?.forEach { targetComponent ->
                            if (targetComponent.id == formComponent?.id) {
                                targetComponent.fileUpload =
                                    targetComponent.fileUpload ?: ArrayList()
                                val fileUpload = FileUploadData(
                                    mediaType,
                                    response.url,
                                    response.title,
                                    response.fileSize
                                )
                                targetComponent.fileUpload?.add(fileUpload)
                                Log.e(
                                    "Debug",
                                    "schema[0] fileUpload size: ${targetComponent.fileUpload}"
                                )
                            }
                        }
                    val updatedSchema = item.formSchema?.schema ?: emptyList()
                    val viewHolder = chatRecyclerView.findViewHolderForAdapterPosition(position)
                    if (viewHolder is NCWChatAdapter.FormViewHolder) {
                        formComponent?.let { viewHolder.updateFormAdapterData(updatedSchema, it) }
                    } else {
                        messageAdapter.notifyItemChanged(position)
                    }
*/


                }

            }

            NCWRoutes.ROUTE_END_CHAT -> {
                hideProgressBar()

                if (onRestart) {

                    messageList.clear()
                    messageAdapter.notifyDataSetChanged()
                    loadInitialMessages()
                    themeData?.isProActiveGreetings = false
                    chatViewModel.getConversationId(botRefId, externalId, onRestart)
                    onRestart = false
                    return
                }
                if (mSurveyRule != null) {
                    if (mSurveyRule?.any { key -> key.conversationTriggerType == RULE_EVENT_CHAT_END } == true) {
                        isSurveyRule = true
                        return
                    }
                }
                NCWThemeUtils.setSignInUserDetails(null)
                NCWThemeUtils.setConversationID(null)
                themeData?.isProActiveGreetings = false
                finish()
            }

            NCWRoutes.ROUTE_SEND_TRANSCRIPT -> {
Log.e("ROUTE_SEND_TRANSCRIPT","Successss")

            }

            NCWRoutes.WEBHOOK_EVENT -> {
                if (idleTimeInMillis > 0 && isIdle) {
                    resetIdleTimer()
                    isIdle=false
                }
            }

            NCWRoutes.LOGIN -> {
                val response = apiResponse as LoginResponse
                Log.d(
                    "AuthConversationID",
                    "Fetched AuthConversationID: $response"
                )
                Log.e("ConversationID LOGIN", response.authenticatedConversationId)
                // Use conversationID as needed
                conversationID = response.authenticatedConversationId
                externalId = response.externalId

                chatViewModel.getAWSMQTTCredentials(botRefId)
                conversationID?.let { NCWThemeUtils.setConversationID(it) }
                getChatHistory()

                Log.d(
                    "AuthConversationID",
                    "Fetched AuthConversationID: $conversationID"
                )
            }

            NCWRoutes.LOGOUT -> {
                hitEndChatAPI()
            }

            NCWRoutes.ROUTE_SURVEY -> {
                if (isSurveyRule) {
                    NCWThemeUtils.setConversationID(null)
                    themeData?.isProActiveGreetings = false
                    finish()
                }
                else{
                    if (idleTimeInMillis > 0) {
                        resetIdleTimer()
                        isIdle = false
                    }
                }

            }

            NCWRoutes.ROUTE_GET_SURVEY_RULE -> {
                val surveyRule = apiResponse as SurveyRuleResponse
                mSurveyRule = surveyRule.payload

                if (mSurveyRule != null) {
                    val matchedRule = mSurveyRule?.firstOrNull { key ->
                        key.conversationTriggerType == RULE_EVENT_IDLE_USER
                    }

                    if (matchedRule != null) {
                       idleTimeInMillis = NCWAppUtils.parseIdleTimeFromExpression(matchedRule.expression) * 1000
                        resetIdleTimer()
                    }
                }


            }

            else -> {
                Toast.makeText(this, "Else..", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun parseHistoryItems(responses: ArrayList<NCWGenericChannelResponse>) {


        responses.forEachIndexed { index, response ->
            if (response.triggerType == TYPE_EVENT) {
                val eventData = response.eventObject?.eventData
                renderPillsMessage(eventData, response.timestamp ?: System.currentTimeMillis())
            }

            if (response.triggerType == TYPE_RESPONSE) {
                val gson = Gson()

                val newMessages = response.attachments?.mapIndexedNotNull { index, attachment ->
                    mapAttachmentToMessage(
                                attachment,
                        response.requestId?:"",
                        NCWAppConstant.NORMAL, index,
                                response.customPayload

                            )
                        }

                 ?: emptyList()

                if (newMessages.isNotEmpty()) {
                    newMessages.forEachIndexed { idx, message ->
                        message.isSameTimeMessage = idx == 0
                    }
                }
                messageList.addAll(newMessages)


                if (response.customFields?.isNotEmpty() == true) {
                    for (customField in response.customFields) {
                        when (CustomFieldName.fromValue(customField.name)) {

                    /*        CustomFieldName.FORM_SCHEMA -> {
                                val formSchemas: List<FormSchema> = gson.fromJson(
                                    customField.values?.get(0),
                                    object : TypeToken<List<FormSchema>>() {}.type
                                )
                                val formSchemasModel = formSchemas.firstOrNull()

                                formSchemasModel?.let { schema ->
                                    val nextMessagePayload = responses.getOrNull(index + 1)
                                        ?.requestPayload?.messagePayload?.text

                                    val formData =
                                        nextMessagePayload?.let { parsePayloadToFormData(it) }

                                    Log.e(
                                        "NextPayload",
                                        "formData: $formData, size: ${formData?.size}"
                                    )

                                    if (!formData.isNullOrEmpty()) {
                                        schema.formData = formData
                                    }

                                    // Create and add the new message
                                    addSingleMessage(
                                        NCWMessage(
                                            sender = TYPE_FORM,
                                            timestamp = System.currentTimeMillis(),
                                            formSchema = schema
                                        )
                                    )
                                }
                            }*/


                            CustomFieldName.FORM_SCHEMA -> {
                                val formSchemas: List<FormSchema> = gson.fromJson(
                                    customField.values?.get(0),
                                    object : TypeToken<List<FormSchema>>() {}.type
                                )
                                val formSchemasModel = formSchemas.firstOrNull()
                                formSchemasModel?.requestId=response.requestId
                                formSchemasModel?.let { schema ->
                                    val matchingResponse = responses
                                        .drop(index + 1)
                                        .firstOrNull { nextResponse ->
                                            val schemaRequestId = nextResponse.requestPayload?.attachmentList
                                                ?.firstOrNull()?.values?.schemaFormRequestId?.get(0)
                                            response.requestId == schemaRequestId
                                        }


                                   /* matchingResponse?.requestPayload?.messagePayload?.text?.let { nextMessagePayload ->
                                        val formData = parsePayloadToFormData(nextMessagePayload)

                                        if (!formData.isNullOrEmpty()) {
                                            schema.formData = formData
                                        }

                                    }*/

                                    matchingResponse?.requestPayload?.attachmentList?.let { nextMessagePayload ->
                                        val formData = nextMessagePayload[0]

                                        if (formData!=null) {
                                            schema.formValues = formData.values?.formValues
                                        }

                                    }
                                    messageList.add(NCWMessage(
                                        sender = TYPE_FORM,
                                        timestamp = System.currentTimeMillis(),
                                        formSchema = schema
                                    ))
                                           /* addSingleMessage(
                                                NCWMessage(
                                                    sender = TYPE_FORM,
                                                    timestamp = System.currentTimeMillis(),
                                                    formSchema = schema
                                                )
                                            )*/

                                    }

                            }



                            CustomFieldName.SURVEY_SCHEMA -> {


                                if (!customField.values.isNullOrEmpty()) {
                                    val surveyField: SurveyField = gson.fromJson(
                                        customField.values[0],
                                        object : TypeToken<SurveyField>() {}.type
                                    )
                                    if(!surveyField.isSurveySkipped) {
                                        val newMessage = NCWMessage(
                                            sender = TYPE_EVENT,
                                            timestamp = response.timestamp
                                                ?: System.currentTimeMillis(),
                                            surveyField = surveyField,
                                            requestID = response.requestId
                                        )
                                        messageList.add(newMessage)
                                    }
                                }

                            }

                            CustomFieldName.DISABLE_INPUT_FIELD, CustomFieldName.DISABLE_CHAT_INPUT -> {

                                val isDisabled = customField.values?.get(0) == "true"
                                setUIState(!isDisabled)
                                isHistoryDisableInput = !isDisabled
                                if (CustomFieldName.fromValue(customField.name) == CustomFieldName.DISABLE_INPUT_FIELD) {

                                    isDisableInput = !isDisabled

                                }
                            }
                            CustomFieldName.END_CHAT -> {
                                // Handle END_CHAT
                            }

                            else -> {
                                // Handle any other cases
                            }
                        }
                    }
                }
                // else {
                // Existing logic for attachments
                /* val newMessages = response.attachments?.mapNotNull {
                     mapAttachmentToMessage(it, response.requestId!!, NCWAppConstant.NORMAL)
                 } ?: emptyList()*/





            }

            // Type Request
            else if (response.triggerType == TYPE_REQUEST) {
                // Existing logic for request payloads
                response.requestPayload?.attachmentList?.takeIf { it.isNotEmpty() }
                    ?.forEach { attachmentListRequest ->
                        val messageType =
                            attachmentListRequest.type?.let { MessageType.fromTypeName(it) }
                        if (messageType != MessageType.MESSAGEINFO) {
                            messageType?.let {
                                val newMessage = NCWMessage(
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
                        }
                    } ?: run {
                    if (response.requestPayload?.messagePayload?.hideMessage != true) {
                        val newMessage = NCWMessage(
                            id = System.currentTimeMillis().toString(),
                            message = response.requestPayload?.messagePayload?.label,
                            timestamp = response.timestamp ?: System.currentTimeMillis(),
                            type = MessageType.TEXT,
                            sender = TYPE_REQUEST,
                        )
                        messageList.add(newMessage)
                    }
                }
            }
        }



        messageList.forEach { it.isQuickReplyVisible = false }
        val lastMessage = messageList.lastOrNull()
        if (lastMessage?.sender == TYPE_RESPONSE && !lastMessage.quickReply?.options.isNullOrEmpty()) {
            lastMessage.isQuickReplyVisible = true
        }

        messageAdapter.notifyDataSetChanged()
        onScrollToPosition(true)


    }

    private fun getChatHistory() {
        Log.e("ConversationID Fetch", conversationID.toString())
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

    private fun showProgressBar() {

        progressBar.visibility = View.VISIBLE
        constProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
        constProgressBar.visibility = View.GONE
    }

    override fun onThumbUpClick(requestId: String, position: Int, attachmentIndex: Int) {
        Log.e("RequestId ThumbUp", requestId)
        messageAdapter.notifyItemChanged(position)
        hitFeedbackAPI(requestId, "POSITIVE", attachmentIndex)
    }

    override fun onThumbDownClick(requestId: String, position: Int, attachmentIndex: Int) {
        Log.e("RequestId ThumbDown", requestId)
        messageAdapter.notifyItemChanged(position)
        hitFeedbackAPI(requestId, "NEGATIVE", attachmentIndex)
    }


    private fun playUserSound() {
        if (themeData?.sound?.defaultSound == true) {
            messageSoundPlayer?.playUserSound()
        }
    }

    private fun playBotSound() {

        if (themeData?.sound?.defaultSound == true) {
            messageSoundPlayer?.playBotSound()
        }
    }
    private fun checkNetworkAndExecute(action: () -> Unit) {
        if (!NCWAppUtils.isNetworkAvailable(this)) {
            NCWAppUtils.showToast(this, getString(R.string.please_check_your_network_and_try_again))
        } else {
            action.invoke()
        }
    }

    private fun sendTranscriptApI(from: String?,email: String) {
        val payload = NCWEmailRequest(
            botRefId = botRefId.orEmpty(),
            conversationId = conversationID.orEmpty(),
            mail = email,
            from = from
        )
        chatViewModel.sendTranscript(payload)
    }
}