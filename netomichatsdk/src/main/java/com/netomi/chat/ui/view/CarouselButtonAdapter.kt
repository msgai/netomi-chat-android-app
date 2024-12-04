package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.CarouselButton
import com.netomi.chat.model.messages.QuickReplyOption
import com.netomi.chat.utils.ThemeUtils

class CarouselButtonAdapter(private val items: List<CarouselButton>,private val carouselButton: (CarouselButton?) -> Unit) : RecyclerView.Adapter<CarouselButtonAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constRow: ConstraintLayout = itemView.findViewById(R.id.constRow)
        val carouselButton: TextView = itemView.findViewById(R.id.btnCoursel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coursel_button, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.carouselButton.text = item.title
        val cornerRadii = floatArrayOf(
            0f, 0f,  // Top-left
            15f, 15f,    // Top-right
            15f, 15f,  // Bottom-right
            15f, 15f   // Bottom-left
        )
        // Apply the background with theme color and custom corners
        ThemeUtils.applyChipBackgroundWithCorners(holder.constRow,  cornerRadii)
        holder.constRow.setOnClickListener {
            carouselButton(item)
        }


    }

    override fun getItemCount() = items.size
}