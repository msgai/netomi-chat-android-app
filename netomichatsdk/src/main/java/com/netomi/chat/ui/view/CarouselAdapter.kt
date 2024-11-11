package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.netomi.chat.R
import com.netomi.chat.model.messages.CarouselElement

class CarouselAdapter(private val items: List<CarouselElement>) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgCarousel: ImageView = itemView.findViewById(R.id.imgCarousel)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val tvDetail: TextView = itemView.findViewById(R.id.tvDetail)
        val recyclerViewCarouselButton: RecyclerView = itemView.findViewById(R.id.recyclerViewCarouselButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        holder.tvHeading.text = item.title
        holder.tvDetail.text = item.description
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imgCarousel)
        holder.recyclerViewCarouselButton.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        val carouselAdapter = CarouselButtonAdapter(item.buttons ?: emptyList())
        holder.recyclerViewCarouselButton.adapter = carouselAdapter

    }

    override fun getItemCount() = items.size
}
