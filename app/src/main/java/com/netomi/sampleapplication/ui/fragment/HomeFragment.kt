package com.netomi.sampleapplication.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.utils.HostRoutes
import com.netomi.sampleapplication.utils.State
import com.netomi.sampleapplication.viewmodel.OnboardingViewModel

class HomeFragment : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()
    private lateinit var botList: MutableList<Bot>
    private lateinit var preferences : AppSharedPreferences
    private lateinit var tvBotName:TextView
    private var isButtonClickable = true
    private lateinit var imgButton:AppCompatImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = AppSharedPreferences(requireContext())

        // Access your views here using view.findViewById
        imgButton =view.findViewById(R.id.img_chat)
        tvBotName=view.findViewById(R.id.tv_botName)

        val bot=preferences.getSelectedBot()
        Log.e("Bot",bot.toString())
        tvBotName.text=bot?.botName
        botList = mutableListOf()
        Glide.with(context ?: return).load(bot?.logo).into(imgButton)

        NCWChatSdk.setEnvironment(bot!!.env)
        NCWChatSdk.setThemeData()
        bot.botRefId.let { NCWChatSdk.initialize(requireContext(), it) }

        imgButton.setOnClickListener {
            activity?.let { activityContext ->
                if(isButtonClickable) {
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

    private fun observeChatMessages() {
        onboardingViewModel.botListing.observe(viewLifecycleOwner) { messages ->
            handleApiCallback(messages as State<Any>)
        }
    }

    private fun handleApiCallback(response: State<Any>) {
        when (response) {
            is State.Loading -> {
                Log.e("Loading", "Loading")
            }

            is State.Success -> {
                //onApiSuccess(response.data, response.apiConstant)
                Log.e("Success", "Success")
                val response= response.data as BotListingResponse
                Log.e("Bot List", response.toString())
                botList.addAll(response.bots)
                preferences.saveSelectedBot(botList[0])
                preferences.put(SharePreferenceConstant.BOT_RESPONSE, response)

            }

            is State.Error -> {
                Log.e("Error", "Error")
            }

            else -> {

            }
        }
    }
}