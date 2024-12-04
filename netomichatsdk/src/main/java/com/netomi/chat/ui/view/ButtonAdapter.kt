package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.messages.Buttons
import com.netomi.chat.utils.ThemeUtils

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
        private val constRow: ConstraintLayout = itemView.findViewById(R.id.constRow)
        fun bind(buttonModel: Buttons) {
            button.text = buttonModel.title
            val cornerRadii = floatArrayOf(
                0f, 0f,  // Top-left
                15f, 15f,    // Top-right
                15f, 15f,  // Bottom-right
                15f, 15f   // Bottom-left
            )
            // Apply the background with theme color and custom corners
            ThemeUtils.applyChipBackgroundWithCorners(constRow,  cornerRadii)

            button.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buttonModel.url))
//                it.context.startActivity(intent)
            }
        }
    }
}
