package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Buttons

class ButtonAdapter(private val buttons: List<Buttons>) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coursel_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(buttons[position])
    }

    override fun getItemCount(): Int = buttons.size

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: TextView = itemView.findViewById(R.id.btnCoursel)

        fun bind(buttonModel: Buttons) {
            button.text = buttonModel.title
            button.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buttonModel.url))
//                it.context.startActivity(intent)
            }
        }
    }
}
