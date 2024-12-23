package com.netomi.sampleapplication.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.model.Bot

class ChangeAiAgentAdapter(
    private val bots: List<Bot>,
    private var selectedId: String,
    private val onItemClick: (Bot) -> Unit,
) : RecyclerView.Adapter<ChangeAiAgentAdapter.BotViewHolder>() {
    private var selectedPosition: Int = -1

    inner class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBotName: TextView = itemView.findViewById(R.id.tvBotName)
        private val tvBotId: TextView = itemView.findViewById(R.id.tvBotId)
        private val tvBotRefId: TextView = itemView.findViewById(R.id.tvBotRefId)
        private val tvVersion: TextView = itemView.findViewById(R.id.tvBotVersion)
        private val tvEnv: TextView = itemView.findViewById(R.id.tvBotEnv)
        private val rbBot:RadioButton=itemView.findViewById(R.id.rbBotSelect)


        fun bind(bot: Bot) {
            tvBotName.text = bot.botName
            tvBotId.text = "Bot ID: "+bot.botId
            tvBotRefId.text="Bot Ref ID: "+bot.botRefId
            tvVersion.text= "Version: "+bot.version
            tvEnv.text="Env: "+bot.env
            rbBot.isChecked=adapterPosition==selectedPosition

            // Set click listener for the item
            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                onItemClick(bot)
            }

            // Set the background color or selection state based on position
            if (bot.botRefId == selectedId) {
               rbBot.isChecked=true
            } else {
                rbBot.isChecked=false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bot, parent, false) // Inflate your item layout
        return BotViewHolder(view)
    }

    override fun onBindViewHolder(holder: BotViewHolder, position: Int) {
        holder.bind(bots[position])
    }

    override fun getItemCount(): Int = bots.size
}