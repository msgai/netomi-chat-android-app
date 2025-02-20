package com.netomi.chat.ui.init

import android.content.Context
import android.content.Intent
import android.util.JsonToken
import android.util.Log
import com.netomi.chat.R
import com.netomi.chat.model.language.NCWOtherLocalized
import com.netomi.chat.model.theme.light_theme.NCWBotConfig
import com.netomi.chat.model.theme.light_theme.NCWBubbleConfig
import com.netomi.chat.model.theme.light_theme.NCWChatWindowConfig
import com.netomi.chat.model.theme.light_theme.NCWFooterConfig
import com.netomi.chat.model.theme.light_theme.NCWHeaderConfig
import com.netomi.chat.model.theme.light_theme.NCWOtherConfig
import com.netomi.chat.model.theme.light_theme.NCWUserConfig
import com.netomi.chat.ui.view.NCWChatActivity
import com.netomi.chat.utils.Environment
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWThemeUtils
import com.netomi.chat.utils.analyticsManager.AnalyticsEvents
import com.netomi.chat.utils.analyticsManager.EventTracker
import org.json.JSONObject


object NCWChatSdk {


    private var botRefId: String? = null
    private var currentEnvironment: Environment = Environment.qa

    /**
     * Initializes the NCWChat SDK with the provided bot reference ID.
     *
     * @param context The application context for initialization.
     * @param botRefId The bot reference ID required to fetch the chat theme.
     */
    fun initialize(context: Context, newBotRefId: String) {
        if (botRefId == newBotRefId) return

        botRefId = newBotRefId
        if (NCWAppUtils.isNetworkAvailable(context)) {
            setThemeData()
            fetchAndStoreTheme(context, newBotRefId)
            setConversationEmpty()
            EventTracker.apply {
                initMix(context , true)
                val properties = JSONObject().apply {
                    put(AnalyticsEvents.BOT_REF_ID, newBotRefId)
                }
                trackEvent(AnalyticsEvents.CHAT_SDK_INITIALIZED , properties)
            }

        } else {
            Log.e("NCWChatSdk", "No network available during initialization.")
        }
    }

    private fun setThemeData() {
        NCWThemeUtils.setThemeData(null)
    }

    private fun setConversationEmpty() {
        NCWThemeUtils.setConversationID(null)
    }

    /**
     * Launches the NCW chat activity.

     * @param context The context from which the activity is launched.
     *
     * This function ensures the theme data is fetched and available before launching the chat.
     * If the theme is already cached, it directly starts the chat activity.
     */
    fun launch(context: Context, jwtToken: String? = null, onError: ((String) -> Unit)? = null) {
        NCWThemeUtils.setJwtToken(jwtToken)
        if (!NCWAppUtils.isNetworkAvailable(context)) {
            val errorMsg = context.getString(R.string.please_check_your_network_and_try_again)
            onError?.invoke(errorMsg)
            return
        }
        if (NCWThemeUtils.getThemeData() != null) {
            startChatActivity(context)
        } else {
            botRefId?.let {
                fetchAndStoreTheme(
                    context, it, onComplete = { startChatActivity(context) },
                    onError = { errorMessage ->
                        onError?.invoke(errorMessage) // Pass error to host app
                    },
                )
            } ?: run {
                val errorMsg = "Bot reference ID is null. Initialization might be missing."
                Log.e("NCWChatSdk", errorMsg)
                onError?.invoke(errorMsg)
            }
        }
    }

    /**
     * Fetches and stores the theme data asynchronously.
     *
     * @param context The context used for theme retrieval.
     * @param botRefId The bot reference ID for which the theme is fetched.
     * @param onComplete Optional callback to execute after theme data is successfully fetched.
     */
    private fun fetchAndStoreTheme(
        context: Context,
        botRefId: String,
        onComplete: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        NCWChatTheme(
            context,
            botRefId,
            onThemeReceived = { themeResponse ->
                themeResponse?.let {
                    if (it.otherlocalized == null) {
                        it.otherlocalized = NCWOtherLocalized()
                    }
                    NCWThemeUtils.setThemeData(it)
                    Log.d("NCWChatSdk", "Theme data stored: $it")
                    onComplete?.invoke()
                } ?: run {
                    val errorMsg = "Received null theme data."
                    Log.e("NCWChatSdk", errorMsg)
                    onError?.invoke(errorMsg) // Notify the host app
                }
            },
            onError = { message ->
                Log.e("NCWChatSdk", "Error fetching theme: $message")
                onError?.invoke(message)
            }
        )
    }

    /**
     * Starts the NCW chat activity.
     *
     * @param context The context from which the activity is started.
     */
    private fun startChatActivity(context: Context) {
        Log.e("Testingggg", "startChatActivity")
        val intent = Intent(context, NCWChatActivity::class.java).apply {
            putExtra(NCWAppConstant.BOT_REFERENCE_ID, botRefId)
        }
        context.startActivity(intent)
    }

    /**
     * Store the NCW SDK configuration provided by the host app
     */
    private var headerConfig: NCWHeaderConfig? = null
    private var footerConfig: NCWFooterConfig? = null
    private var botConfig: NCWBotConfig? = null
    private var userConfig: NCWUserConfig? = null
    private var bubbleConfig: NCWBubbleConfig? = null
    private var chatWindowConfig: NCWChatWindowConfig? = null
    private var otherConfig: NCWOtherConfig? = null


    /**
     * Initializes the Chat SDK with the given configuration.
     *
     * @param config The configuration object containing customizable properties.
     * This should be called by the host application before using any Chat SDK components.
     */
    /*  fun initialize(config: HeaderConfig) {
          ncwSdkConfig = config
      }*/
    /**
     * Retrieves the current Chat SDK configuration.
     *
     * @return The configuration object provided by the host app or a default one if not set.
     */
    /*  fun getConfig(): HeaderConfig {
          return ncwSdkConfig ?: HeaderConfig()  // Provide default config if not initialized
      }*/

    /**
     * Sets the environment based on the key.
     * @param key The key to decide the environment (e.g., "prod", "stage", "dev").
     */
    fun setEnvironment(key: String) {
        currentEnvironment = when (key.lowercase()) {
            "us" -> Environment.us
            "sg" -> Environment.sg
            "eu" -> Environment.eu
            "qa" -> Environment.qa
            "qaint" -> Environment.qaint
            "dev" -> Environment.dev
            else -> throw IllegalArgumentException("Invalid environment key: $key")
        }
    }

    /**
     * Retrieves the current base URL.
     */
    fun getBaseUrl(): String {
        return currentEnvironment.baseUrl
    }

    fun updateBotConfiguration(config: NCWBotConfig) {
        val currentConfig = botConfig ?: NCWBotConfig()
        botConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedBotConfiguration(): NCWBotConfig {
        return botConfig ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.botConfig
        ?: NCWBotConfig()
    }

    fun updateHeaderConfiguration(config: NCWHeaderConfig) {
        val currentConfig = headerConfig ?: NCWHeaderConfig()
        headerConfig = currentConfig.mergeWith(config)
    }

    fun getUpdateHeaderConfiguration(): NCWHeaderConfig {
        return headerConfig ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.headerConfig
        ?: NCWHeaderConfig()
    }

    fun updateFooterConfiguration(config: NCWFooterConfig) {
        val currentConfig = footerConfig ?: NCWFooterConfig()
        footerConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedFooterConfiguration(): NCWFooterConfig {
        return footerConfig ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.footerConfig
        ?: NCWFooterConfig()
    }

    fun updateUserConfiguration(config: NCWUserConfig) {
        val currentConfig = userConfig ?: NCWUserConfig()
        userConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedUserConfiguration(): NCWUserConfig {
        return userConfig ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.userConfig
        ?: NCWUserConfig()
    }

    fun updateBubbleConfiguration(config: NCWBubbleConfig) {
        val currentConfig = bubbleConfig ?: NCWBubbleConfig()
        bubbleConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedBubbleConfiguration(): NCWBubbleConfig {
        return bubbleConfig ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.bubbleConfig
        ?: NCWBubbleConfig()
    }

    fun updateChatWindowConfiguration(config: NCWChatWindowConfig) {
        val currentConfig = chatWindowConfig ?: NCWChatWindowConfig()
        chatWindowConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedChatWindowConfiguration(): NCWChatWindowConfig {
        return chatWindowConfig
            ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.chatWindowConfig
            ?: NCWChatWindowConfig()
    }

    fun updateOtherConfiguration(config: NCWOtherConfig) {
        val currentConfig = otherConfig ?: NCWOtherConfig()
        otherConfig = currentConfig?.mergeWith(config)
    }

    fun getUpdatedOtherConfiguration(): NCWOtherConfig {
        return otherConfig ?: NCWThemeUtils.getThemeData()?.mobileConfig?.lightTheme?.otherConfig
        ?: NCWOtherConfig()
    }

    private fun NCWHeaderConfig.mergeWith(newConfig: NCWHeaderConfig): NCWHeaderConfig {
        return NCWHeaderConfig(
            backgroundColor = newConfig.backgroundColor ?: this.backgroundColor,
            gradientColors = newConfig.gradientColors ?: this.gradientColors,
            gradientDirection = newConfig.gradientDirection ?: this.gradientDirection,
            iconBackgroundColor = newConfig.iconBackgroundColor ?: this.iconBackgroundColor,
            isBackPressPopupEnabed = newConfig.isBackPressPopupEnabed
                ?: this.isBackPressPopupEnabed,
            isGradientAppied = newConfig.isGradientAppied ?: this.isGradientAppied,
            logoImage = newConfig.logoImage ?: this.logoImage,
            tintColor = newConfig.tintColor ?: this.tintColor
        )
    }

    private fun NCWFooterConfig.mergeWith(newConfig: NCWFooterConfig): NCWFooterConfig {
        return NCWFooterConfig(
            backgroundColor = newConfig.backgroundColor ?: this.backgroundColor,
            inputBoxTextColor = newConfig.inputBoxTextColor ?: this.inputBoxTextColor,
            isFooterHidden = newConfig.isFooterHidden ?: this.isFooterHidden,
            isNetomiBrandingEnabled = newConfig.isNetomiBrandingEnabled
                ?: this.isNetomiBrandingEnabled,
            netomiBrandingText = newConfig.netomiBrandingText ?: this.netomiBrandingText,
            netomiBrandingTextColor = newConfig.netomiBrandingTextColor
                ?: this.netomiBrandingTextColor,
            tintColor = newConfig.tintColor ?: this.tintColor
        )
    }

    private fun NCWBotConfig.mergeWith(newConfig: NCWBotConfig): NCWBotConfig {
        return NCWBotConfig(
            backgroundColor = newConfig.backgroundColor ?: this.backgroundColor,
            botImage = newConfig.botImage ?: this.botImage,
            quickReplyBackgroundColor = newConfig.quickReplyBackgroundColor
                ?: this.quickReplyBackgroundColor,
            quickReplyBorderColor = newConfig.quickReplyBorderColor ?: this.quickReplyBorderColor,
            quickReplyTextColor = newConfig.quickReplyTextColor ?: this.quickReplyTextColor,
            textColor = newConfig.textColor ?: this.textColor,
        )
    }

    private fun NCWUserConfig.mergeWith(newConfig: NCWUserConfig): NCWUserConfig {
        return NCWUserConfig(
            backgroundColor = newConfig.backgroundColor ?: this.backgroundColor,
            textColor = newConfig.textColor ?: this.textColor,
        )
    }

    private fun NCWBubbleConfig.mergeWith(newConfig: NCWBubbleConfig): NCWBubbleConfig {
        return NCWBubbleConfig(
            borderRadius = newConfig.borderRadius ?: this.borderRadius,
            timeStampColor = newConfig.timeStampColor ?: this.timeStampColor,
        )
    }

    private fun NCWChatWindowConfig.mergeWith(newConfig: NCWChatWindowConfig): NCWChatWindowConfig {
        return NCWChatWindowConfig(
            chatWindowBackgroundColor = newConfig.chatWindowBackgroundColor
                ?: this.chatWindowBackgroundColor,
        )
    }

    private fun NCWOtherConfig.mergeWith(newConfig: NCWOtherConfig): NCWOtherConfig {
        return NCWOtherConfig(
            backgroundColor = newConfig.backgroundColor ?: this.backgroundColor,
            titleColor = newConfig.titleColor ?: this.titleColor,
            descriptionColor = newConfig.descriptionColor ?: this.descriptionColor,
        )
    }

    fun setDeviceToken(fireBaseToken: String){
        NCWThemeUtils.setDeviceToken(fireBaseToken)
    }


}