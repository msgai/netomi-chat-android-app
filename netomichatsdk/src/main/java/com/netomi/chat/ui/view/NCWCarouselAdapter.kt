package com.netomi.chat.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.netomi.chat.R
import com.netomi.chat.model.messages.NCWCarouselButton
import com.netomi.chat.model.messages.NCWCarouselElement
import com.netomi.chat.utils.NCWThemeUtils

class NCWCarouselAdapter(private val items: List<NCWCarouselElement>, private val carouselButton: (NCWCarouselButton?) -> Unit) : RecyclerView.Adapter<NCWCarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val rootLayout: ConstraintLayout = itemView.findViewById(R.id.rootLayout)
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
        Log.e("subtitle ","sss "+item.subtitle)
        holder.tvDetail.text = item.subtitle?.trim() ?: ""
        NCWThemeUtils.setBotConfig(holder.rootLayout)
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imgCarousel)
      holder.recyclerViewCarouselButton.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)

        val carouselAdapter = NCWCarouselButtonAdapter(item.buttons ?: emptyList()){
            carouselButton(it)
        }
        holder.recyclerViewCarouselButton.adapter = carouselAdapter
        holder.recyclerViewCarouselButton.setHasFixedSize(true)


    }

    override fun getItemCount() = items.size
}
