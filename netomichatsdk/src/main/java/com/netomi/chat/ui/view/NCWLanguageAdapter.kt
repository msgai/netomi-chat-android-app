package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWLanguage
import com.netomi.chat.utils.NCWThemeUtils

class NCWLanguageAdapter(
    private val items: List<NCWLanguage>,
    private val options: (NCWLanguage?) -> Unit
) : RecyclerView.Adapter<NCWLanguageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val radio: RadioButton = itemView.findViewById(R.id.radio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_quick_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.label
        holder.radio.visibility = View.VISIBLE
        holder.radio.isChecked = (position == 0)
        NCWThemeUtils.setRadioButtonColor(holder.radio)
        NCWThemeUtils.setTitleColor(holder.tvTitle)
        holder.itemView.setOnClickListener {
            options(item)
        }
        holder.radio.setOnClickListener {
            holder.itemView.performClick()
        }



    }

    override fun getItemCount() = items.size
}