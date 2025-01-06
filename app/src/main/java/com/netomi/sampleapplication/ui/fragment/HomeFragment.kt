package com.netomi.sampleapplication.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.viewmodel.OnboardingViewModel

class HomeFragment : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()
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
        onboardingViewModel.botList.observe(viewLifecycleOwner) {
            Log.e("SelectedBot local","Server")
           updateBot(it)
        }

        imgButton.setOnClickListener {
            activity?.let { activityContext ->
                if(isButtonClickable) {
                    avoidDoubleClick()
                    val name = preferences.getString(SharePreferenceConstant.NAME)
                    val email = preferences.getString(SharePreferenceConstant.NAME)
                    NCWChatSdk.launch(activityContext,name,email)
                }
            }
        }
    }

    private fun updateBot(bot: Bot) {
        tvBotName.text=bot.botName
        Glide.with(requireContext()).load(bot.logo).into(imgButton)
        imgButton.setBackgroundResource(R.drawable.float_button_gradient)
        try {
            NCWChatSdk.setEnvironment(bot.env)
            bot.botRefId.let { NCWChatSdk.initialize(requireContext(), it) }
        }catch (e:Exception){
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