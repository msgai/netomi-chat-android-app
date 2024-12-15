package com.netomi.chat.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R

import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWThemeUtils

class NCWFormFilesAdapter(private val items: List<FileUploadData>, private val onQuickReply: (FileUploadData?) -> Unit) : RecyclerView.Adapter<NCWFormFilesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val requestDocCard: ConstraintLayout = itemView.findViewById(R.id.docCard)
        val tvDocName: TextView = itemView.findViewById(R.id.tvDocName)
        val tvDocType: TextView = itemView.findViewById(R.id.tvDocType)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ncw_form_file_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        NCWThemeUtils.setUserConfig(holder.requestDocCard)
        NCWThemeUtils.setUserConfigTextColor(holder.tvDocName)
        NCWThemeUtils.setTimeStampColor(holder.tvDocType)

        holder.tvDocName.text=item.title
        if (item.fileSize!=null)
            holder.tvDocType.text= NCWAppUtils.formatFileSize(item.fileSize!!.toLong())
    }

    override fun getItemCount() = items.size
}