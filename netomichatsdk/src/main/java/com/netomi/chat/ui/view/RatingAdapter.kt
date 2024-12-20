package com.netomi.chat.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R

class RatingAdapter(
    private val context: Context,
    private val lowerBound: Int,
    private val upperBound: Int,
    private val onRatingSelected: (Int) -> Unit
) : RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {

    private var selectedRating: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rating, parent, false)
        return RatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        holder.bind(position + 1, position < selectedRating)
        holder.itemView.setOnClickListener {
            selectedRating = position + 1
            onRatingSelected(selectedRating)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return upperBound - lowerBound + 1
    }

    inner class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingText: TextView = itemView.findViewById(R.id.ratingText)

        fun bind(rating: Int, isSelected: Boolean) {
            ratingText.text = rating.toString()
          //  ratingText.setBackgroundResource(if (isSelected) R.drawable.rating_selected_background else R.drawable.rating_default_background)
        }
    }
}