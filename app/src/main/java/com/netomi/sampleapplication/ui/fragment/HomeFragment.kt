package com.netomi.sampleapplication.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.netomi.chat.model.theme.light_theme.NCWBotConfig
import com.netomi.chat.model.theme.light_theme.NCWBubbleConfig
import com.netomi.chat.model.theme.light_theme.NCWChatWindowConfig
import com.netomi.chat.model.theme.light_theme.NCWFooterConfig
import com.netomi.chat.model.theme.light_theme.NCWHeaderConfig
import com.netomi.chat.model.theme.light_theme.NCWOtherConfig
import com.netomi.chat.model.theme.light_theme.NCWUserConfig
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.ui.init.NCWChatSdk.setCustomParameter
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.utils.AppSharedPreferences

class HomeFragment : Fragment() {

    private lateinit var preferences: AppSharedPreferences
    private lateinit var tvBotName: TextView
    private var isButtonClickable = true
    private lateinit var imgButton: AppCompatImageButton
    private lateinit var progressOverlay: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = AppSharedPreferences(requireContext())
        imgButton = view.findViewById(R.id.img_chat)
        tvBotName = view.findViewById(R.id.tv_botName)
        progressOverlay = view.findViewById(R.id.progress_overlay)


        try {
            NCWChatSdk.initialize(
                requireContext(),
                requireContext().getString(R.string.bot_ref_id),
                requireContext().getString(R.string.app_environment)
            )
        } catch (_: Exception) { }

        imgButton.setOnClickListener {
            activity?.let { activityContext ->
                if (isButtonClickable) {
                    avoidDoubleClick()
                    NCWChatSdk.launch(activityContext)
                }
            }
        }
    }

    /**
     * Prevents the button from being clicked multiple times.
     */
    private fun avoidDoubleClick() {
        isButtonClickable = false
        imgButton.isEnabled = false


        Handler(Looper.getMainLooper()).postDelayed({
            isButtonClickable = true
            imgButton.isEnabled = true
        }, 2000)
    }

    // Launch chat with the current query from the search bar
    private fun launchWithQuery() {
        NCWChatSdk.launchWithQuery(requireContext(), "text")
    }

    // Send a single custom parameter (dummy user ID)
    private fun sendDummyCustomParameter() {
        val name = "user_id"
        val value = "12345"
        NCWChatSdk.sendCustomParameter(name, value)
    }

    // Set multiple custom parameters using a dummy map
    private fun setDummyCustomParameters() {
        val dummyParams = mutableMapOf(
            "session_id" to "abc123",
            "user_type" to "guest",
            "region" to "IN"
        )
        NCWChatSdk.setCustomParameter(dummyParams)
    }

    // Set a dummy Firebase Cloud Messaging token
    private fun setFCMToken() {
        NCWChatSdk.setFCMToken("firebase token")
    }

    // Update the header configuration using dummy data
    private fun updateHeaderConfigWithDummyData() {
        val dummyConfig = NCWHeaderConfig(
            backgroundColor = "#FF5722",
            gradientColors = listOf("#123456", "#654321"),
            gradientDirection = 4,
            iconBackgroundColor = "#EFEFEF",
            isBackPressPopupEnabed = false,
            isGradientAppied = true,
            logoImage = "https://example.com/logo.png",
            tintColor = "#FFFFFF"
        )
        NCWChatSdk.updateHeaderConfiguration(dummyConfig)
    }

    // Update the footer configuration using dummy data
    private fun updateFooterConfigWithDummyData() {
        val dummyConfig = NCWFooterConfig(
            backgroundColor = "#EEEEEE",
            inputBoxBackgroundColor = "#FAFAFA",
            inputBoxTextColor = "#333333",
            isFooterHidden = false,
            isNetomiBrandingEnabled = false,
            netomiBrandingText = "Chat powered by Netomi",
            netomiBrandingTextColor = "#FF5722",
            tintColor = "#FF5722",
            iconBackgroundColor = "#DDDDDD",
            sendButtonBackgroundColor = "#FF5722",
            sendButtonColor = "#FFFFFF"
        )
        NCWChatSdk.updateFooterConfiguration(dummyConfig)
    }

    // Update the bot configuration using dummy data
    private fun updateBotConfigWithDummyData() {
        val dummyConfig = NCWBotConfig(
            backgroundColor = "#E0F7FA",
            botImage = "https://example.com/bot-avatar.png",
            quickReplyBackgroundColor = "#4CAF50",
            quickReplyBorderColor = "#388E3C",
            quickReplyTextColor = "#FFFFFF",
            textColor = "#212121"
        )
        NCWChatSdk.updateBotConfiguration(dummyConfig)
    }

    // Update the user configuration using dummy data
    private fun updateUserConfigWithDummyData() {
        val dummyConfig = NCWUserConfig(
            backgroundColor = "#FFD700",  // Gold color
            textColor = "#1A237E"         // Indigo
        )
        NCWChatSdk.updateUserConfiguration(dummyConfig)


    }

    // Update the message bubble configuration using dummy data
    private fun updateBubbleConfigWithDummyData() {
        val dummyConfig = NCWBubbleConfig(
            borderRadius = "25f",
            timeStampColor = "#FF5722" // Deep Orange
        )
        NCWChatSdk.updateBubbleConfiguration(dummyConfig)
    }

    // Update the chat window configuration using dummy data
    private fun updateChatWindowConfigWithDummyData() {
        val dummyConfig = NCWChatWindowConfig(
            chatWindowBackgroundColor = "#FFF8E1" // Light yellow tone
        )
        NCWChatSdk.updateChatWindowConfiguration(dummyConfig)
    }

    // Update other visual/chat options using dummy data
    private fun updateOtherConfigWithDummyData() {
        val dummyConfig = NCWOtherConfig(
            titleColor = "#1E88E5",                  // Blue
            descriptionColor = "#757575",            // Grey
            backgroundColor = "#FFFDE7",             // Light Yellow
        )
        NCWChatSdk.updateOtherConfiguration(dummyConfig)
    }

    // Update API header configuration using dummy headers (including extra headers)
    private fun updateApiHeaderConfigurationWithDummyData() {
        val dummyHeaderMap = mapOf(
            "Content-Type" to "application/json",
            "x-platform" to "android",
            "x-custom-token" to "ABC123",        // Extra (not a known key)
            "x-session-id" to "SESSION_9999"     // Extra (not a known key)
        )
        NCWChatSdk.updateApiHeaderConfiguration(dummyHeaderMap)
    }

}