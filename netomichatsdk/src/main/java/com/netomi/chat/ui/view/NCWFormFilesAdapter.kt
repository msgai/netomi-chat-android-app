package com.netomi.chat.ui.view

import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
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
import com.netomi.chat.utils.NCWAppUtils.formatFileSize
import com.netomi.chat.utils.NCWParsingUtils.getFileSizeFromUrl
import com.netomi.chat.utils.NCWThemeUtils

class NCWFormFilesAdapter(
    private val items: ArrayList<FileUploadData>,
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
        val tvConnectionError: TextView = itemView.findViewById(R.id.tvConnectionError)
        val tvRetry: TextView = itemView.findViewById(R.id.tvRetry)


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
        NCWThemeUtils.createStrokeDrawable(holder.txtCancel)
        val retryText = NCWThemeUtils.getThemeData()?.otherlocalized?.retry
            ?: holder.itemView.context.getString(R.string.retry)
        val spannableString = SpannableString(retryText)
        spannableString.setSpan(UnderlineSpan(), 0, retryText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        holder.tvRetry.text = spannableString



        holder.tvConnectionError.text=NCWThemeUtils.getThemeData()?.otherlocalized?.connection_error ?:holder.itemView.context.getString(R.string.connection_error)
        holder.txtCancel.text=NCWThemeUtils.getThemeData()?.otherlocalized?.cancel ?:holder.itemView.context.getString(R.string.cancel)

        with(holder) {
            if (item.fileUrl != null) {
                icDelete.visibility = if (isClickable) View.VISIBLE else View.GONE
                progressBar.visibility = View.GONE
                txtCancel.visibility = View.GONE
            } else {
                icDelete.visibility = View.GONE
                progressBar.visibility = if (item.isRetry) View.GONE else View.VISIBLE
                txtCancel.visibility = if (item.isRetry) View.GONE else View.VISIBLE
            }

            constRetry.visibility = if (item.isRetry) View.VISIBLE else View.GONE
        }

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
       /* else
            item.fileUrl?.let {
                getFileSizeFromUrl(it) { fileSize ->
                    if (fileSize != null) {
                        holder.tvDocType.text = formatFileSize(fileSize)
                    } else {
                        Log.e("File", "Failed to retrieve file size.")
                    }
                }
            }*/



        holder.icDelete.setOnClickListener {
            onDelete(item)
        }
        holder.constRetry.setOnClickListener {
            onRetry(item)
            holder.constRetry.visibility=View.GONE
            holder.progressBar.visibility=View.VISIBLE
            holder.icDelete.visibility=View.GONE
            holder.txtCancel.visibility=View.VISIBLE
        }
        holder.txtCancel.setOnClickListener {
           item.isCancelled=true
            onDelete(item)
        }
    }

    override fun getItemCount() = items.size

    fun itemRemoved(position: Int) {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        } else {
            Log.e("Error", "Invalid position: $position, size: ${items.size}")
        }
    }
}