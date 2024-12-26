package com.netomi.chat.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.utils.NCWAppConstant.RatingType.EMOJI
import com.netomi.chat.utils.NCWAppConstant.RatingType.EMOJI_VALUE

import com.netomi.chat.utils.NCWAppConstant.RatingType.NUM_5
import com.netomi.chat.utils.NCWAppConstant.RatingType.STAR
import com.netomi.chat.utils.NCWAppConstant.RatingType.STAR_VALUE

import com.netomi.chat.utils.NCWAppConstant.RatingType.NUM_10
import com.netomi.chat.utils.NCWAppConstant.RatingType.NUM_VALUE
import com.netomi.chat.utils.NCWAppConstant.RatingType.THUMBS_UP_DOWN
import com.netomi.chat.utils.NCWAppConstant.RatingType.THUMBS_UP_DOWN_VALUE

import com.netomi.chat.utils.NCWThemeUtils

class NCWRatingAdapter(
    private val context: Context,
    private val upperBound: Int,
    private val ratingTypeEnabled: String,
    private val onRatingSelected: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     var selectedRating: Int = 0

    override fun getItemViewType(position: Int): Int {
       return when (ratingTypeEnabled) {
           STAR -> STAR_VALUE
           NUM_10, NUM_5 -> NUM_VALUE
            EMOJI -> EMOJI_VALUE
            THUMBS_UP_DOWN -> THUMBS_UP_DOWN_VALUE
            else -> STAR_VALUE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            STAR_VALUE -> StarViewHolder(inflater.inflate(R.layout.item_star_rating, parent, false))
            NUM_VALUE -> NumberViewHolder(inflater.inflate(R.layout.item_number_rating, parent, false))
            EMOJI_VALUE -> SmileyViewHolder(inflater.inflate(R.layout.item_smiley_rating, parent, false))
            THUMBS_UP_DOWN_VALUE -> ThumbsViewHolder(inflater.inflate(R.layout.item_smiley_rating, parent, false))
            else -> StarViewHolder(inflater.inflate(R.layout.item_star_rating, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rating = position + 1
        when (holder) {
            is StarViewHolder -> holder.bind(rating, rating <= selectedRating)
            is NumberViewHolder -> holder.bind(rating, rating <= selectedRating)
            is SmileyViewHolder -> holder.bind(rating, selectedRating)
            is ThumbsViewHolder -> holder.bind(rating, selectedRating)
        }

        holder.itemView.setOnClickListener {
            selectedRating = rating
            val finalRating = if (ratingTypeEnabled != "THUMBS_UP_DOWN") {
                selectedRating
            } else {
                if (selectedRating == 1) 2 else 1
            }
            onRatingSelected(finalRating)


            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return upperBound
    }

    inner class StarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val starIcon: ImageView = itemView.findViewById(R.id.starIcon)

        fun bind(rating: Int, isSelected: Boolean) {
            starIcon.setImageResource(
                if (isSelected) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )
        }
    }

    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberText: TextView = itemView.findViewById(R.id.numberText)

        fun bind(rating: Int, isSelected: Boolean) {
            numberText.text = rating.toString()
            numberText.setBackgroundResource(
                if (isSelected) R.drawable.bg_selected_rounded else R.drawable.bg_rounded_star
            )
        }
    }

    inner class SmileyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val smileyIcon: ImageView = itemView.findViewById(R.id.smileyIcon)
        private val constRow: ConstraintLayout = itemView.findViewById(R.id.constRow)

        fun bind(rating: Int, isSelected: Int) {
            val smileyRes = when (rating) {
                1 -> R.drawable.ic_smiley_very_bad
                2 -> R.drawable.ic_smiley_bad
                3 -> R.drawable.ic_smiley_neutral
                4 -> R.drawable.ic_smiley_good
                5 -> R.drawable.ic_smiley_very_good
                else -> R.drawable.ic_smiley_neutral
            }
            smileyIcon.setImageResource(smileyRes)
            if (isSelected == rating) {
                val tintList =
                    ColorStateList.valueOf(NCWThemeUtils.parseColor("#E3D076"))
                ImageViewCompat.setImageTintList(smileyIcon, tintList)
                constRow.setBackgroundResource(R.drawable.bg_stroke_smily_selected)
            } else {
                ImageViewCompat.setImageTintList(smileyIcon, null)
                smileyIcon.setBackgroundResource(0)
                constRow.setBackgroundResource(R.drawable.bg_rounded_star)
            }
        }
    }


    inner class ThumbsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val smileyIcon: ImageView = itemView.findViewById(R.id.smileyIcon)
        private val constRow: ConstraintLayout = itemView.findViewById(R.id.constRow)

        fun bind(rating: Int, isSelected: Int) {
            val smileyRes = when (rating) {
                1 -> R.drawable.ic_thumb_up_survey
                else -> R.drawable.ic_thumb_down_survey
            }
            smileyIcon.setImageResource(smileyRes)
            if (isSelected == rating) {
                val tintList =
                    ColorStateList.valueOf(NCWThemeUtils.parseColor("#E3D076"))
                ImageViewCompat.setImageTintList(smileyIcon, tintList)
                constRow.setBackgroundResource(R.drawable.bg_stroke_smily_selected)
            } else {
                ImageViewCompat.setImageTintList(smileyIcon, null)
                smileyIcon.setBackgroundResource(0)
                constRow.setBackgroundResource(R.drawable.bg_rounded_star)
            }
        }
    }
}
