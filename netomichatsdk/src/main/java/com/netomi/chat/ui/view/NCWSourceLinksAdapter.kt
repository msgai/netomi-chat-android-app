package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.netomi.chat.R
import com.netomi.chat.model.messages.MultipleSourceDetail
import com.netomi.chat.utils.NCWThemeUtils

class NCWSourceLinksAdapter(
    private val items: ArrayList<MultipleSourceDetail>,
     val imgBot: String?,
    private val multipleSourceDetail: (MultipleSourceDetail?) -> Unit
) : RecyclerView.Adapter<NCWSourceLinksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constRow: ConstraintLayout = itemView.findViewById(R.id.constRow)
        val tvIndex: TextView = itemView.findViewById(R.id.tvIndex)
        val tvSourceLint: TextView = itemView.findViewById(R.id.tvSourceLink)
        val imgSource: ImageView = itemView.findViewById(R.id.imgSource)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_source_link, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvSourceLint.text = item.text
        holder.tvIndex.text = "${item.index}."


        Glide.with(holder.itemView.context)
            .load(imgBot ?: R.drawable.ic_bot_profile)
            .placeholder(R.drawable.ic_bot_profile)
            .into(holder.imgSource)
        NCWThemeUtils.setBotTextColor(holder.tvIndex)
        NCWThemeUtils.setQuickReply(holder.constRow,holder.tvSourceLint)
        holder.constRow.setOnClickListener {
            multipleSourceDetail(item)
        }


    }

    override fun getItemCount() = items.size
}