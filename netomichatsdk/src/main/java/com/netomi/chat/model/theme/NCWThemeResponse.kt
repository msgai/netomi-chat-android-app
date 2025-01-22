package com.netomi.chat.model.theme

data class NCWThemeResponse(
    val `$LAST_TIME_SAVED`: String,
    val _botResponseBubbleColor: String,
    val agentDeskForm: NCWAgentDeskForm,
    val aiAgentName: String,
    val borderRadius: String,
    val botImage: String? = null,
    val botResponseBubbleColor: String,
    val calloutBubble: List<NCWCalloutBubble>,
    val chatHistory: NCWChatHistory,
    val chatWidget: NCWChatWidget,
    val curatedColors: Boolean,
    val customForms: NCWCustomForms,
    val endChat: NCWEndChat,
    val fallback_: NCWFallback,
    val fileSharing: NCWFileSharing,
    val footerStatus: String,
    val hideContextMenu: Boolean,
    val hideMinMax: Boolean,
    val initialFlows: NCWInitialFlows,
    val isDefaultFullScreen: Boolean,
    val isDefaultOpen: Boolean,
    val isFooterHidden: Boolean=false,
    val isNetomiBrandingEnabled: Boolean,
    val isRestartConversationEnabled: Boolean,
    val knowledgeAI: NCWKnowledgeAI,
    val launchButton_: NCWLaunchButtonX,
    val liveAgentDeskConfig: NCWLiveAgentDeskConfig,
    val logo: String,
    val logoImage: String,
    val mobileConfig: NCWMobileConfig,
    val multilingual: NCWMultilingual,
    val nbaConfig_: NCWNbaConfig,
    val persistentSurveyConfig: NCWPersistentSurveyConfig,
    val proactiveTriggerType: String?=null,
    val quickMenuOptions: List<NCWQuickMenuOption>,
    val realTimeChatService: NCWRealTimeChatService,
    val restartChat: NCWRestartChat,
    val scrollToBottom: NCWScrollToBottom,
    val sendTranscriptEmailSetup: NCWSendTranscriptEmailSetup,
    val sound: NCWSound,
    val subtitle: String,
    val supportIcon: Any,
    val supportIconConfig: NCWSupportIconConfig,
    val supportText: String,
    val textColor: String,
    val theme: NCWTheme,
    val timeDelayBetweenMessageBubbles: Int,
    val title: String,
    val typingIndicator: NCWTypingIndicator,
    val whitelisting: NCWWhitelisting,
    val OAUTH2: NCWOauth,
    var proActiveGreetings: ArrayList<String> = arrayListOf(),
    var isProActiveGreetings:Boolean=false,

)
