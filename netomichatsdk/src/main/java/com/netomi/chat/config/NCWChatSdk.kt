package com.netomi.chat.config
/**
 * NCWChatSdk class to manage the initialization and configuration of the NCW SDK.
 *
 * This class exposes methods to initialize the SDK with a configuration object
 * and retrieve the current configuration throughout the SDK.
 */
object NCWChatSdk {

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