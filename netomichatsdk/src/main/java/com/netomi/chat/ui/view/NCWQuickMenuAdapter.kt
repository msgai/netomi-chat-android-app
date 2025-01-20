package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWQuickMenuOption
import com.netomi.chat.utils.NCWThemeUtils

class NCWQuickMenuAdapter(
    private val items: List<NCWQuickMenuOption>,
    private val options: (NCWQuickMenuOption?) -> Unit
) : RecyclerView.Adapter<NCWQuickMenuAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_quick_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.label
        NCWThemeUtils.setTitleColor(holder.tvTitle)
        holder.itemView.setOnClickListener {
            options(item)
        }


    }

    override fun getItemCount() = items.size
}