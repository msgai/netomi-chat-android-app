package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.QuickReplyOption
import com.netomi.chat.utils.ThemeUtils

class ChipAdapter(private val items: List<QuickReplyOption>, private val onQuickReply: (QuickReplyOption?) -> Unit,) : RecyclerView.Adapter<ChipAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOption: TextView = itemView.findViewById(R.id.tvOption)
       // val constChip: ConstraintLayout = itemView.findViewById(R.id.constChip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chip_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvOption.text = item.label
        // will change from bot json
        val cornerRadii = floatArrayOf(
            0f, 0f,  // Top-left
            15f, 15f,    // Top-right
            15f, 15f,  // Bottom-right
            15f, 15f   // Bottom-left
        )
        // Apply the background with theme color and custom corners
        ThemeUtils.applyChipBackgroundWithCorners(holder.tvOption,  cornerRadii)

        holder.tvOption.setOnClickListener {
            onQuickReply(item)
        }
    }

    override fun getItemCount() = items.size
}