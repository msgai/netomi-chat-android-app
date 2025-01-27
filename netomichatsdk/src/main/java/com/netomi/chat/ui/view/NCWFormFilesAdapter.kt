package com.netomi.chat.ui.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R

import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWAppUtils.formatFileSize
import com.netomi.chat.utils.NCWParsingUtils.getFileSizeFromUrl
import com.netomi.chat.utils.NCWThemeUtils

class NCWFormFilesAdapter(
    private val items: List<FileUploadData>,
    val isClickable: Boolean,
    private val onDelete: (FileUploadData?) -> Unit,
    private val onRetry: (FileUploadData?) -> Unit,
) : RecyclerView.Adapter<NCWFormFilesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val requestDocCard: ConstraintLayout = itemView.findViewById(R.id.docCard)
        val tvDocName: TextView = itemView.findViewById(R.id.tvDocName)
        val tvDocType: TextView = itemView.findViewById(R.id.tvDocType)
        val icDelete: ImageView = itemView.findViewById(R.id.icDelete)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_loader)
        val constRetry: ConstraintLayout = itemView.findViewById(R.id.constRetry)
        val txtCancel: TextView = itemView.findViewById(R.id.txtCancel)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ncw_form_file_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Log.e("item", "item " + item)
        // NCWThemeUtils.setUserConfig(holder.requestDocCard)
        NCWThemeUtils.setUserConfigTextColor(holder.tvDocName)
        NCWThemeUtils.setTimeStampColor(holder.tvDocType)
        if (item.fileUrl!=null){

            holder.icDelete.visibility=View.VISIBLE
            holder.progressBar.visibility=View.GONE
            holder.txtCancel.visibility=View.GONE
        }
        else
        {
            holder.icDelete.visibility=View.GONE
            holder.progressBar.visibility=View.VISIBLE
            holder.txtCancel.visibility=View.VISIBLE
        }
        if (item.isRetry){
            holder.constRetry.visibility=View.VISIBLE
            holder.progressBar.visibility=View.GONE
        }
        else
        {
            holder.constRetry.visibility=View.GONE
        }
        NCWThemeUtils.createStrokeDrawable(holder.txtCancel)
        // holder.icDelete.visibility = if (isClickable) View.VISIBLE else View.GONE
       /* if (isClickable)
            holder.icDelete.visibility=View.GONE*/

        item.title?.let {
            holder.tvDocName.text = it
        } ?: run {
            item.fileUrl?.let { fileUrl ->
                val fileName = fileUrl.substringAfterLast("/")
                holder.tvDocName.text = fileName
            }
        }

        if (item.fileSize != null)
            holder.tvDocType.text = formatFileSize(item.fileSize!!.toLong())
        else
            item.fileUrl?.let {
                getFileSizeFromUrl(it) { fileSize ->
                    if (fileSize != null) {
                        holder.tvDocType.text = formatFileSize(fileSize)
                    } else {
                        Log.e("File", "Failed to retrieve file size.")
                    }
                }
            }



        holder.icDelete.setOnClickListener {
            onDelete(item)
            notifyItemChanged(position)
        }
        holder.constRetry.setOnClickListener {
            onRetry(item)
        }


        holder.txtCancel.setOnClickListener {
          Log.e("Clcikkk","saasasas")
        //   item.isCancelled=true
            onDelete(item)
            notifyItemChanged(position)
        }


    }

    override fun getItemCount() = items.size
}