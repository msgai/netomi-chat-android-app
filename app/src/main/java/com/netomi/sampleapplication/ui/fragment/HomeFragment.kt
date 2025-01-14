package com.netomi.sampleapplication.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.utils.NCWState
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.model.FetchJwtTokenResponse
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.utils.NetworkUtils
import com.netomi.sampleapplication.utils.State
import com.netomi.sampleapplication.viewmodel.OnboardingViewModel
import org.json.JSONObject

class HomeFragment : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()
    private lateinit var preferences: AppSharedPreferences
    private lateinit var tvBotName: TextView
    private var isButtonClickable = true
    private lateinit var imgButton: AppCompatImageButton
    private lateinit var progressOverlay: FrameLayout
    private var token: String? = null

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
        onboardingViewModel.botList.observe(viewLifecycleOwner) {
            updateBot(it)
        }

        onboardingViewModel.jwtToken.observe(viewLifecycleOwner) {
            handleApiCallback(it as State<Any>)
        }

        imgButton.setOnClickListener {
            activity?.let { activityContext ->
                if (isButtonClickable) {
                    avoidDoubleClick()
                    NCWChatSdk.launch(activityContext, token)
                }
            }
        }
    }

    private fun showLoader(show: Boolean) {
        progressOverlay.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun handleApiCallback(response: State<Any>) {
        when (response) {
            is State.Loading -> {
                showLoader(true)
            }

            is State.Success -> {
                val res = response.data as FetchJwtTokenResponse
                if (res.token.isNotEmpty())
                token = res.token

                showLoader(false)

            }

            is State.Error -> {
                showLoader(false)
            }

            else -> {
                showLoader(false)
                Toast.makeText(requireContext(), "Else..", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateBot(bot: Bot) {
        imgButton.visibility=View.VISIBLE
        tvBotName.text=bot.botName
        tvBotName.text = bot.botName
        Glide.with(requireContext()).load(bot.logo).into(imgButton)
        imgButton.setBackgroundResource(R.drawable.float_button_gradient)
        val name= preferences.getString(SharePreferenceConstant.NAME)
        val email=preferences.getString(SharePreferenceConstant.EMAIL)

        val jsonMap = mapOf(
            "name" to name,
            "externalId" to email
        )
        val jsonString = Gson().toJson(jsonMap)
        if (NetworkUtils.isNetworkAvailable(requireActivity()))
            bot.botRefId.let { onboardingViewModel.fetchJwtToken(bot.botRefId,jsonString) }
        else
            Toast.makeText(requireContext(),
                getString(R.string.please_check_your_network_and_try_again), Toast.LENGTH_SHORT).show()

        try {
            NCWChatSdk.setEnvironment(bot.env)
            bot.botRefId.let { NCWChatSdk.initialize(requireContext(), it) }
        } catch (e: Exception) {
            e.printStackTrace()
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
}