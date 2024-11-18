package com.netomi.chat.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
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
        holder.tvDetail.text = item.subtitle
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imgCarousel)
      holder.recyclerViewCarouselButton.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
       /* holder.recyclerViewCarouselButton.layoutManager = LinearLayoutManager(holder.itemView.context).apply {
            stackFromEnd = true
           // reverseLayout = true
        }*/
        val carouselAdapter = CarouselButtonAdapter(item.buttons ?: emptyList())
        holder.recyclerViewCarouselButton.adapter = carouselAdapter
        holder.recyclerViewCarouselButton.setHasFixedSize(true)
       /* val colorHex = "#E6E6E6"  // replace with your dynamic color as needed
        val colorDrawable = ColorDrawable(Color.parseColor(colorHex))


        // Apply the custom divider only once
        if (holder.recyclerViewCarouselButton.itemDecorationCount == 0) {
            val customDivider = CustomDividerItemDecoration(colorDrawable)
            holder.recyclerViewCarouselButton.addItemDecoration(customDivider)
        }*/
//        // Measure and set minimum height based on 3 buttons
//        val maxButtonsHeight = calculateMaxButtonsHeight(holder.itemView.context)
//        holder.itemView.minimumHeight = maxButtonsHeight

    }
    private fun calculateMaxButtonsHeight(context: Context): Int {
        // Here we estimate the height of the layout if it has 3 buttons
        val buttonHeight = context.resources.getDimensionPixelSize(R.dimen.button_height) // Set this dimension in `dimens.xml`
        val padding = context.resources.getDimensionPixelSize(R.dimen.button_padding)
        return buttonHeight * 3 + padding * 3 // Adjust padding as needed
    }

    override fun getItemCount() = items.size
}
