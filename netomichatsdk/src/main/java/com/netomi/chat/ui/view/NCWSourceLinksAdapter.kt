package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.utils.NCWThemeUtils

class NCWSourceLinksAdapter(private val items: List<NCWCarouselButton>, private val carouselButton: (NCWCarouselButton?) -> Unit) : RecyclerView.Adapter<NCWSourceLinksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constRow: ConstraintLayout = itemView.findViewById(R.id.constRow)
        val carouselButton: TextView = itemView.findViewById(R.id.btnCoursel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_source_link, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.carouselButton.text = item.title
        NCWThemeUtils.setQuickReply(holder.constRow,holder.carouselButton)
        holder.constRow.setOnClickListener {
            carouselButton(item)
        }


    }

    override fun getItemCount() = items.size
}