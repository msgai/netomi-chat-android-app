package com.netomi.chat.ui.init

import android.content.Context
import android.content.Intent
import android.util.Log
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.theme.light_theme.BotConfig
import com.netomi.chat.model.theme.light_theme.BubbleConfig
import com.netomi.chat.model.theme.light_theme.ChatWindowConfig
import com.netomi.chat.model.theme.light_theme.FooterConfig
import com.netomi.chat.model.theme.light_theme.HeaderConfig
import com.netomi.chat.model.theme.light_theme.UserConfig
import com.netomi.chat.ui.view.NCWChatActivity
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.ThemeUtils


object NCWChatSdk {


    private var botRefId: String? = null

    /**
     * Initializes the NCWChat SDK with the provided bot reference ID.
     *
     * @param context The application context for initialization.
     * @param botRefId The bot reference ID required to fetch the chat theme.
     */
    fun initialize(context: Context, botRefId: String) {
        this.botRefId = botRefId
        if (NCWAppUtils.isNetworkAvailable(context)) {
            fetchAndStoreTheme(context, botRefId)
        } else {
            Log.e("NCWChatSdk", "No network available during initialization.")
        }
    }


    /**
     * Launches the NCW chat activity.
     *
     * @param context The context from which the activity is launched.
     *
     * This function ensures the theme data is fetched and available before launching the chat.
     * If the theme is already cached, it directly starts the chat activity.
     */
    fun launch(context: Context) {
        if (!NCWAppUtils.isNetworkAvailable(context)) {
            NCWAppUtils.showToast(
                context,
                "Unable to load chat at the moment. Please check your network and try again."
            )
            return
        }
        if (ThemeUtils.getThemeData() != null) {
            startChatActivity(context)
        } else {
            botRefId?.let {
                fetchAndStoreTheme(context, it, onComplete = { startChatActivity(context) })
            } ?: run {
                Log.e("NCWChatSdk", "Bot reference ID is null. Initialization might be missing.")
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
        onComplete: (() -> Unit)? = null
    ) {
        NCWChatTheme(
            context,
            botRefId,
            onThemeReceived = { themeResponse ->
                themeResponse?.let {
                    ThemeUtils.setThemeData(it)
                    Log.d("NCWChatSdk", "Theme data stored: $it")
                    onComplete?.invoke()
                } ?: Log.e("NCWChatSdk", "Received null theme data.")
            },
            onError = { message ->
                Log.e("NCWChatSdk", "Error fetching theme: $message")
            }
        )
    }

    /**
     * Starts the NCW chat activity.
     *
     * @param context The context from which the activity is started.
     */
    private fun startChatActivity(context: Context) {
        Log.e("Testingggg","startChatActivity")
        val intent = Intent(context, NCWChatActivity::class.java).apply {
            putExtra(NCWAppConstant.BOT_REFERENCE_ID, botRefId)
        }
        context.startActivity(intent)
    }

    /**
     * Store the NCW SDK configuration provided by the host app
     */
    private var headerConfig: HeaderConfig?=null
    private var footerConfig: FooterConfig?=null
    private var botConfig:BotConfig?=null
    private var userConfig:UserConfig?=null
    private var bubbleConfig:BubbleConfig?=null
    private var chatWindowConfig:ChatWindowConfig?=null


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


    fun updateBotConfiguration(config: BotConfig){
        val currentConfig = botConfig ?: BotConfig()
        botConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedBotConfiguration():BotConfig{
        return botConfig?:BotConfig()
    }

    fun updateHeaderConfiguration(config: HeaderConfig){
        val currentConfig = headerConfig ?: HeaderConfig()
        headerConfig = currentConfig.mergeWith(config)
    }

    fun getUpdateHeaderConfiguration():HeaderConfig{
        return headerConfig?: HeaderConfig()
    }

    fun updateFooterConfiguration(config: FooterConfig){
      val currentConfig = footerConfig ?: FooterConfig()
        footerConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedFooterConfiguration():FooterConfig{
        return footerConfig?: FooterConfig()
    }

    fun updateUserConfiguration(config:UserConfig){
        val currentConfig = userConfig ?: UserConfig()
        userConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedUserConfiguration(): UserConfig{
        return userConfig?: UserConfig()
    }

    fun updateBubbleConfiguration(config: BubbleConfig){
        val currentConfig = bubbleConfig ?: BubbleConfig()
        bubbleConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedBubbleConfiguration():BubbleConfig{
        return bubbleConfig?: BubbleConfig()
    }

    fun updateChatWindowConfiguration(config: ChatWindowConfig){
        val currentConfig = chatWindowConfig ?: ChatWindowConfig()
        chatWindowConfig = currentConfig.mergeWith(config)
    }

    fun getUpdatedChatWindowConfiguration():ChatWindowConfig{
        return chatWindowConfig?: ChatWindowConfig()
    }

    private fun HeaderConfig.mergeWith(newConfig: HeaderConfig): HeaderConfig {
        return HeaderConfig(
            backgroundColor = newConfig.backgroundColor ?: this.backgroundColor,
            gradientColors = newConfig.gradientColors ?: this.gradientColors,
            gradientDirection = newConfig.gradientDirection ?: this.gradientDirection,
            iconBackgroundColor = newConfig.iconBackgroundColor ?: this.iconBackgroundColor,
            isBackPressPopupEnabed = newConfig.isBackPressPopupEnabed ?: this.isBackPressPopupEnabed,
            isGradientAppied = newConfig.isGradientAppied ?: this.isGradientAppied,
            logoImage = newConfig.logoImage ?: this.logoImage,
            tintColor = newConfig.tintColor ?: this.tintColor
        )
    }

    private fun FooterConfig.mergeWith(newConfig: FooterConfig):FooterConfig{
        return FooterConfig(
            backgroundColor = newConfig.backgroundColor?: this.backgroundColor,
            inputBoxTextColor = newConfig.inputBoxTextColor?: this.inputBoxTextColor,
            isFooterHidden = newConfig.isFooterHidden?: this.isFooterHidden,
            isNetomiBrandingEnabled = newConfig.isNetomiBrandingEnabled?: this.isNetomiBrandingEnabled,
            netomiBrandingText = newConfig.netomiBrandingText?: this.netomiBrandingText,
            netomiBrandingTextColor = newConfig.netomiBrandingTextColor?: this.netomiBrandingTextColor,
            tintColor= newConfig.tintColor?: this.tintColor
        )
    }

    private fun BotConfig.mergeWith(newConfig: BotConfig):BotConfig{
        return BotConfig(
            backgroundColor = newConfig.backgroundColor?:this.backgroundColor,
            botImage = newConfig.botImage?:this.botImage,
            quickReplyBackgroundColor = newConfig.quickReplyBackgroundColor?: this.quickReplyBackgroundColor,
            quickReplyBorderColor = newConfig.quickReplyBorderColor?: this.quickReplyBorderColor,
            quickReplyTextColor = newConfig.quickReplyTextColor?: this.quickReplyTextColor,
            textColor = newConfig.textColor?: this.textColor,
        )
    }

    private fun UserConfig.mergeWith(newConfig: UserConfig):UserConfig{
        return UserConfig(
            backgroundColor = newConfig.backgroundColor?:this.backgroundColor,
            textColor = newConfig.textColor?: this.textColor,
        )
    }

    private fun BubbleConfig.mergeWith(newConfig: BubbleConfig):BubbleConfig{
        return BubbleConfig(
            borderRadius = newConfig.borderRadius?:this.borderRadius,
            timeStampColor = newConfig.timeStampColor?: this.timeStampColor,
        )
    }

    private fun ChatWindowConfig.mergeWith(newConfig: ChatWindowConfig):ChatWindowConfig{
        return ChatWindowConfig(
            chatWindowBackgroundColor = newConfig.chatWindowBackgroundColor?:this.chatWindowBackgroundColor,
        )
    }






}