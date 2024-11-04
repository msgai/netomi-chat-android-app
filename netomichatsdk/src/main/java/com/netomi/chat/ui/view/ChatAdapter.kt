package com.netomi.chat.ui.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage

class ChatAdapter(private val messages: List<NCWMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender.equals("User"))
            VIEW_TYPE_SENDER else VIEW_TYPE_RECEIVER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sender, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receiver, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is SenderViewHolder -> holder.bind(message)
            is ReceiverViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.senderMessageText)
        private val imageView: ImageView = itemView.findViewById(R.id.senderImage)
        private val videoView: VideoView = itemView.findViewById(R.id.senderVideo)
        private val senderImageCard: CardView = itemView.findViewById(R.id.senderImageCard)


        fun bind(message: NCWMessage) {
            // Show or hide views based on the message type
            if (message.type == MessageType.TEXT) {
                messageText.visibility = View.VISIBLE
                messageText.text = message.message
                imageView.visibility = View.GONE
                videoView.visibility = View.GONE
                senderImageCard.visibility = View.GONE
            } else if (message.type == MessageType.IMAGE) {
                imageView.visibility = View.VISIBLE
                imageView.setImageURI(Uri.parse(message.message))
                // Glide.with(itemView.context).load(message.content).into(imageView)
                messageText.visibility = View.GONE
                videoView.visibility = View.GONE
                senderImageCard.visibility = View.VISIBLE
            } else if (message.type == MessageType.VIDEO) {
                videoView.visibility = View.VISIBLE
                videoView.setVideoPath(message.message)
                videoView.start()
                messageText.visibility = View.GONE
                imageView.visibility = View.GONE
                senderImageCard.visibility = View.GONE
            }
        }
    }

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.receiverMessageText)
        private val imageView: ImageView = itemView.findViewById(R.id.receiverImage)
        private val videoView: VideoView = itemView.findViewById(R.id.receiverVideo)

        fun bind(message: NCWMessage) {
            // Show or hide views based on the message type
            if (message.type == MessageType.TEXT) {
                messageText.visibility = View.VISIBLE
                messageText.text = message.message
                imageView.visibility = View.GONE
                videoView.visibility = View.GONE
            } else if (message.type == MessageType.IMAGE) {
                imageView.visibility = View.VISIBLE
                imageView.setImageURI(Uri.parse(message.message))
             //   Glide.with(itemView.context).load(message.content).into(imageView)
                messageText.visibility = View.GONE
                videoView.visibility = View.GONE
            } else if (message.type == MessageType.VIDEO) {
                videoView.visibility = View.VISIBLE
                videoView.setVideoPath(message.message)
                videoView.start()
                messageText.visibility = View.GONE
                imageView.visibility = View.GONE
            }
        }
    }
}
