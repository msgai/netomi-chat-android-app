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
import java.util.Locale

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

    // Send a single custom parameter
    private fun sendDummyCustomParameter() {
        val name = "user_id"                         // User ID key
        val value = "12345"                          // user ID value
        NCWChatSdk.sendCustomParameter(name, value)
    }

    // Set multiple custom parameters
    private fun setDummyCustomParameters() {
        val userParams = mutableMapOf(
            "user_id" to "12345",                  // Unique user identifier
            "user_name" to "John Doe",             // Full name of the user
            "membership_level" to "gold",          // Membership tier (e.g., gold, silver)
            "app_version" to "7.2.0"               // App version for targeting or debugging
        )
        NCWChatSdk.setCustomParameter(userParams)
    }

    // Set FCM Token (push notifications)
    private fun setFCMToken() {
        NCWChatSdk.setFCMToken("your-fcm-token")     // Dummy FCM token
    }

    // Header Configuration
    private fun updateHeaderConfigWithDummyData() {
        val dummyConfig = NCWHeaderConfig(
            backgroundColor = "#FF5722",             // Header background color
            gradientColors = listOf("#123456", "#654321"), // Gradient colors
            gradientDirection = 4,                   // Gradient direction
            iconBackgroundColor = "#EFEFEF",         // Icon background color
            isBackPressPopupEnabed = false,          // Disable back confirmation
            isGradientAppied = true,                 // Enable gradient
            logoImage = "https://example.com/logo.png", // Custom logo
            tintColor = "#FFFFFF"                    // Header tint color
        )
        NCWChatSdk.updateHeaderConfiguration(dummyConfig)
    }

    // Footer Configuration
    private fun updateFooterConfigWithDummyData() {
        val dummyConfig = NCWFooterConfig(
            backgroundColor = "#EEEEEE",             // Footer background color
            inputBoxBackgroundColor = "#FAFAFA",     // Chat input field background
            inputBoxTextColor = "#333333",           // Chat input text color
            isFooterHidden = false,                  // Footer visibility
            isNetomiBrandingEnabled = false,         // Show/hide Netomi branding
            netomiBrandingText = "Chat powered by Netomi", // Branding text
            netomiBrandingTextColor = "#FF5722",     // Branding text color
            tintColor = "#FF5722",                   // Footer icon tint
            iconBackgroundColor = "#DDDDDD",         // Icon background color
            sendButtonBackgroundColor = "#FF5722",   // Send button background
            sendButtonColor = "#FFFFFF"              // Send icon color
        )
        NCWChatSdk.updateFooterConfiguration(dummyConfig)
    }

    // Bot Bubble Configuration
    private fun updateBotConfigWithDummyData() {
        val dummyConfig = NCWBotConfig(
            backgroundColor = "#E0F7FA",             // Bot message bubble background
            botImage = "https://example.com/bot-avatar.png", // Bot avatar image
            quickReplyBackgroundColor = "#4CAF50",   // Quick reply button background
            quickReplyBorderColor = "#388E3C",       // Quick reply border color
            quickReplyTextColor = "#FFFFFF",         // Quick reply text color
            textColor = "#212121"                    // Bot message text color
        )
        NCWChatSdk.updateBotConfiguration(dummyConfig)
    }

    // User Bubble Configuration
    private fun updateUserConfigWithDummyData() {
        val dummyConfig = NCWUserConfig(
            backgroundColor = "#FFD700",             // User message bubble background (gold)
            textColor = "#1A237E"                    // User message text color (indigo)
        )
        NCWChatSdk.updateUserConfiguration(dummyConfig)
    }

    // Chat Bubble Appearance
    private fun updateBubbleConfigWithDummyData() {
        val dummyConfig = NCWBubbleConfig(
            borderRadius = "25f",                    // Message bubble corner radius
            timeStampColor = "#FF5722"               // Timestamp text color
        )
        NCWChatSdk.updateBubbleConfiguration(dummyConfig)
    }

    // Chat Window Background
    private fun updateChatWindowConfigWithDummyData() {
        val dummyConfig = NCWChatWindowConfig(
            chatWindowBackgroundColor = "#FFF8E1"    // Chat screen background color
        )
        NCWChatSdk.updateChatWindowConfiguration(dummyConfig)
    }

    // Miscellaneous Info Section Styling
    private fun updateOtherConfigWithDummyData() {
        val dummyConfig = NCWOtherConfig(
            titleColor = "#1E88E5",                  // Info title text color
            descriptionColor = "#757575",            // Description/subtext color
            backgroundColor = "#FFFDE7"              // Info section background
        )
        NCWChatSdk.updateOtherConfiguration(dummyConfig)
    }

    // Custom API Header Configuration
    private fun updateApiHeaderConfigurationWithDummyData() {
        val customHeaders = mapOf(
            "X-App-Version" to "7.2.0",                         // Current app version
            "X-Device-ID" to "device-98765",                    // Unique device identifier
            "X-Platform" to "android",                          // OS platform
            "X-User-Type" to "beta_tester",                     // User group/segment
            "X-Experiment-Variant" to "A",                      // A/B test group
            "X-Locale" to Locale.getDefault().toString()        // User locale (e.g., en_US)
        )
        NCWChatSdk.updateApiHeaderConfiguration(customHeaders)
    }


}