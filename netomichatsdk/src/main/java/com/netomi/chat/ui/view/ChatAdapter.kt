package com.netomi.chat.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.NCWAppConstant.USER
import com.netomi.chat.utils.NCWAppUtils

class ChatAdapter(private val messages: List<NCWMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender.equals(USER))
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
        private val messageText: TextView = itemView.findViewById(R.id.tvSenderMessage)
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
            } /*else if (message.type == MessageType.VIDEO) {
                videoView.visibility = View.VISIBLE
                videoView.setVideoPath(message.message)
                videoView.start()
                messageText.visibility = View.GONE
                imageView.visibility = View.GONE
                senderImageCard.visibility = View.GONE
            }*/
        }
    }

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tvReceiverMessage)
        private val imageView: ImageView = itemView.findViewById(R.id.receiverImage)
        private val videoView: VideoView = itemView.findViewById(R.id.receiverVideo)
        private val imgBot: ImageView = itemView.findViewById(R.id.img_bot)
        private val carouselRecyclerView: RecyclerView = itemView.findViewById(R.id.carouselRecyclerView)
        private val receiverImageCard: CardView = itemView.findViewById(R.id.receiverImageCard)
        private val chipGroup: ChipGroup = itemView.findViewById(R.id.quickReplyChipGroup)
        private val cardViewCard: CardView = itemView.findViewById(R.id.cardViewCard)
        private val cardTitle: TextView = itemView.findViewById(R.id.cardTitle)
        private val cardText: TextView = itemView.findViewById(R.id.cardText)
        private val buttonRecyclerView: RecyclerView = itemView.findViewById(R.id.buttonRecyclerView)

        private val whiteColor = Color.parseColor("#FFFFFF")
        private val blackColor = Color.parseColor("#000000")

        fun bind(message: NCWMessage) {
            // Hide all views initially to avoid redundant visibility changes
            messageText.visibility = View.GONE
            imageView.visibility = View.GONE
            videoView.visibility = View.GONE
            imgBot.visibility = View.GONE
            carouselRecyclerView.visibility = View.GONE
            receiverImageCard.visibility = View.GONE
            chipGroup.visibility = View.GONE
            cardViewCard.visibility = View.GONE

            when (message.type) {
                MessageType.TEXT -> {
                    message.message?.let { NCWAppUtils.setHtmText(it, messageText) }
                    messageText.visibility = View.VISIBLE
                    imgBot.visibility = View.VISIBLE
                }

                MessageType.IMAGE -> {
                    Glide.with(itemView.context).load(message.largeImageUrl).into(imageView)
                    imageView.visibility = View.VISIBLE
                    receiverImageCard.visibility = View.VISIBLE
                }

                MessageType.CAROUSEL -> {
                    if (carouselRecyclerView.adapter == null) {
                        carouselRecyclerView.layoutManager =
                            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    }
                    val carouselAdapter = CarouselAdapter(message.carouselItems ?: emptyList())
                    carouselRecyclerView.adapter = carouselAdapter
                    carouselRecyclerView.visibility = View.VISIBLE
                }

                MessageType.VIDEO -> {
                    videoView.setVideoURI(Uri.parse(message.thumbnailUrl))
                    videoView.start()
                    videoView.visibility = View.VISIBLE
                }

                MessageType.CARD -> {
                    cardViewCard.visibility = View.VISIBLE
                    cardTitle.text=message.message
                    cardText.text=message.title
                    val buttonAdapter = ButtonAdapter(message.buttons)
                    buttonRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                    buttonRecyclerView.adapter = buttonAdapter
                    buttonRecyclerView.setHasFixedSize(true)
                    val colorHex = "#E6E6E6"  // replace with your dynamic color as needed
                    val colorDrawable = ColorDrawable(Color.parseColor(colorHex))
                    if (buttonRecyclerView.itemDecorationCount == 0) {
                        val customDivider = CustomDividerItemDecoration(colorDrawable)
                        buttonRecyclerView.addItemDecoration(customDivider)
                    }

                }

                else -> {}
            }

            message.quickReply?.options?.let { options ->
                chipGroup.removeAllViews()
                chipGroup.visibility = View.VISIBLE

                options.forEach { option ->
                    val chip = Chip(itemView.context).apply {
                        text = option.label
                        isClickable = true
                        isCheckable = false
                        setOnClickListener {
                            Log.e("option.metadata", "" + option.metadata)
                            // handleQuickReplyAction(option.metadata)
                        }
                        chipBackgroundColor = ColorStateList.valueOf(whiteColor)
                        setTextColor(blackColor)
                        chipStrokeWidth = 1f
                        chipCornerRadius = 10f
                        chipStrokeColor = ColorStateList.valueOf(blackColor)
                    }
                    chipGroup.addView(chip)
                }
            }
        }
    }

}
