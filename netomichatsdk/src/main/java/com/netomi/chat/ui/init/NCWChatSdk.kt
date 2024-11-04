package com.netomi.chat.ui.init

import android.content.Context
import android.content.Intent
import android.util.Log
import com.netomi.chat.config.NCWSdkConfig
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.ui.view.NCWChatActivity


object NCWChatSdk {


    private var themeData: ThemeResponse? = null

    fun launch(context: Context,botRefId:String="b23963e4-56c5-4d8f-929e-2b0f1155b1f8") {
        NCWChatTheme(
            context,
            "b23963e4-56c5-4d8f-929e-2b0f1155b1f8",
            onThemeReceived = { themeResponse ->
                themeData = themeResponse
                Log.e("NCWChatSdk", "Theme data received: $themeData")
                setupChatWithTheme(themeData)
                val intent=Intent(context, NCWChatActivity::class.java)
                intent.putExtra("botRefId",botRefId)
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