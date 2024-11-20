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


    /**
     * Launches the NCW chat activity with a specified bot reference ID, initializing the chat theme.
     *
     * @param context The context from which the activity is launched.
     * @param botRefId The bot reference ID for initializing the chat.
     *
     * This function initiates the chat theme setup by calling `NCWChatTheme`, which asynchronously fetches the theme data.
     * - If the theme data is successfully retrieved (`onThemeReceived` callback), it stores the theme in `ThemeUtils`
     *   for centralized access throughout the app.
     */
    fun launch(context: Context,botRefId:String) {

        if(NCWAppUtils.isNetworkAvailable(context)) {
            NCWChatTheme(
                context,
                botRefId,
                onThemeReceived = { themeResponse ->
                    Log.e("NCWChatSdk", "Theme data received: $themeResponse")
                    themeResponse?.let { ThemeUtils.setThemeData(it) }
                    val intent = Intent(context, NCWChatActivity::class.java)
                    intent.putExtra(NCWAppConstant.BOT_REFERENCE_ID, botRefId)
                    context.startActivity(intent)
                },
                onError = { message ->
                    Log.e("NCWChatSdk", "Error fetching theme: $message")
                    // Handle the error appropriately
                }
            )
        }
        else{
            NCWAppUtils.showToast(context,"Unable to load chat at the moment. Please check your network and try again.")
        }
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