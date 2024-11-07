package com.netomi.chat.ui.init

import android.content.Context
import android.content.Intent
import android.util.Log
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.ui.view.NCWChatActivity
import com.netomi.chat.utils.NCWAppConstant


object NCWChatSdk {


    private var themeData: ThemeResponse? = null


    /**
     * Launches the NCW chat activity with a specified bot reference ID, initializing the chat theme.
     *
     * @param context The context from which the activity is launched.
     * @param botRefId The bot reference ID for initializing the chat.
     *
     * This function first initializes the chat theme by calling `NCWChatTheme`, which fetches theme data asynchronously.
     * - If the theme data is successfully retrieved (`onThemeReceived` callback), it updates the `themeData` variable with the theme details,
     *   and sets up the chat UI with the received theme by calling `setupChatWithTheme`.
     */
    fun launch(context: Context,botRefId:String) {
        NCWChatTheme(
            context,
            botRefId,
            onThemeReceived = { themeResponse ->
                themeData = themeResponse
                Log.e("NCWChatSdk", "Theme data received: $themeData")
                setupChatWithTheme(themeData)
                val intent=Intent(context, NCWChatActivity::class.java)
                intent.putExtra(NCWAppConstant.BOT_REFERENCE_ID,botRefId)
                context.startActivity(intent)
            },
            onError = { message ->
                Log.e("NCWChatSdk", "Error fetching theme: $message")
                // Handle the error appropriately
            }
        )
    }

     private fun setupChatWithTheme(theme: ThemeResponse?) {
        themeData=theme
    }

    fun getThemeData():ThemeResponse?{
        return themeData
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