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
import com.netomi.chat.ui.init.NCWChatSdk
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
}