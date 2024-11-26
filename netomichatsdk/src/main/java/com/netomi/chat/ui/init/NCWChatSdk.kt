package com.netomi.chat.ui.init

import android.content.Context
import android.content.Intent
import android.util.Log
import com.netomi.chat.config.NCWSdkConfig
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

    // Store the NCW SDK configuration provided by the host app
    private var ncwSdkConfig: NCWSdkConfig? = null


    /**
     * Initializes the Chat SDK with the given configuration.
     *
     * @param config The configuration object containing customizable properties.
     * This should be called by the host application before using any Chat SDK components.
     */
    fun initialize(config: NCWSdkConfig) {
        ncwSdkConfig = config
    }
    /**
     * Retrieves the current Chat SDK configuration.
     *
     * @return The configuration object provided by the host app or a default one if not set.
     */
    fun getConfig(): NCWSdkConfig {
        return ncwSdkConfig ?: NCWSdkConfig()  // Provide default config if not initialized
    }




}