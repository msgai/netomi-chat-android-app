package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.CarouselButton
import com.netomi.chat.model.messages.QuickReplyOption

class CarouselButtonAdapter(private val items: List<CarouselButton>,private val carouselButton: (CarouselButton?) -> Unit) : RecyclerView.Adapter<CarouselButtonAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val carouselButton: TextView = itemView.findViewById(R.id.btnCoursel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coursel_button, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.carouselButton.text = item.title

        holder.carouselButton.setOnClickListener {
            carouselButton(item)
        }


    }

    override fun getItemCount() = items.size
}