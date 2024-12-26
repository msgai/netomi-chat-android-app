package com.netomi.sampleapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.ui.activity.HomeActivity
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.viewmodel.OnboardingViewModel

class ChangeAiAgentFragment : Fragment() {
    private lateinit var preferences:AppSharedPreferences
    private lateinit var adapter: ChangeAiAgentAdapter
    private lateinit var recyclerView: RecyclerView
    private var selectedBot: Bot? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_ai_agent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences= AppSharedPreferences(requireContext())
        recyclerView = view.findViewById(R.id.recyclerViewBots)
        Log.e("Change AI Agent",preferences.get<BotListingResponse>(SharePreferenceConstant.BOT_RESPONSE).toString())

        val botsResponse = preferences.get<BotListingResponse>(SharePreferenceConstant.BOT_RESPONSE)
        val bots = botsResponse?.bots ?: emptyList()
        var bot=preferences.getSelectedBot()
        adapter = ChangeAiAgentAdapter(bots,bot!!.botRefId) { bot ->
            // Handle bot item click
            selectedBot=bot
            preferences.saveSelectedBot(selectedBot!!)

            (activity as HomeActivity).loadFragment(HomeFragment())
          //  Toast.makeText(requireContext(), "Clicked: ${bot.botName}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}