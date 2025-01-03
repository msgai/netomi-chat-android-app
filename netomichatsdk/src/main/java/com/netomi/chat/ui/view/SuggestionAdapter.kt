package com.netomi.chat.ui.view

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.utils.NCWThemeUtils

class SuggestionAdapter(
    private val options: List<String>
) : RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>() {

     val selectedOptions = mutableSetOf<Int>()

    inner class SuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.optionText)

        fun bind(option: String, position: Int) {
            textView.text = option

            if (selectedOptions.contains(position)) {
                NCWThemeUtils.createSelectedRoundedDrawable(textView)
                textView.setTypeface(textView.typeface, Typeface.BOLD)
            } else {
                NCWThemeUtils.createUnSelectedRoundedDrawable(textView)
                textView.setTypeface(textView.typeface, Typeface.NORMAL)
            }

            itemView.setOnClickListener {
                if (selectedOptions.contains(position)) {
                    selectedOptions.remove(position) // Deselect
                } else {
                    selectedOptions.add(position) // Select
                }
                notifyItemChanged(position) // Refresh the item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(options[position], position)
    }

    override fun getItemCount(): Int = options.size

    fun getSelectedOptions(): List<String> {
        return selectedOptions.map { options[it] }
    }
}
