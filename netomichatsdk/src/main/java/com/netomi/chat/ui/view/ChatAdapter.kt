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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.utils.ChatActionCallback
import com.netomi.chat.utils.NCWAppConstant.INITIAL
import com.netomi.chat.utils.NCWAppConstant.USER
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.ThemeUtils

class ChatAdapter(
    private val messages: MutableList<NCWMessage>,
    private val themeData: ThemeResponse?,
    private val actionCallback: ChatActionCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
        private const val VIEW_INITIAL = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].sender) {
            USER -> VIEW_TYPE_SENDER
            INITIAL -> VIEW_INITIAL
            else -> VIEW_TYPE_RECEIVER
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            VIEW_TYPE_SENDER -> inflater.inflate(R.layout.item_sender, parent, false)
            VIEW_INITIAL -> inflater.inflate(R.layout.layout_initial, parent, false)
            else -> inflater.inflate(R.layout.item_receiver, parent, false)
        }
        return when (viewType) {
            VIEW_TYPE_SENDER -> SenderViewHolder(view, themeData)
            VIEW_INITIAL -> InitialViewHolder(view, themeData,actionCallback)
            else -> ReceiverViewHolder(view,themeData,actionCallback)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is SenderViewHolder -> holder.bind(message)
            is ReceiverViewHolder -> holder.bind(message,position)
            is InitialViewHolder -> holder.bind(message,position)
        }
    }

    override fun getItemCount(): Int = messages.size

    class SenderViewHolder(itemView: View, private val themeData: ThemeResponse?) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tvSenderMessage)
        private val imageView: ImageView = itemView.findViewById(R.id.senderImage)
        private val videoView: VideoView = itemView.findViewById(R.id.senderVideo)
        private val senderImageCard: CardView = itemView.findViewById(R.id.senderImageCard)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)


        fun bind(message: NCWMessage) {
            // Show or hide views based on the message type
            tvTime.text=NCWAppUtils.formatTimestampToTime(message.timestamp)
            if (message.type == MessageType.TEXT) {
                messageText.visibility = View.VISIBLE
                messageText.text = message.message
                imageView.visibility = View.GONE
                videoView.visibility = View.GONE
                senderImageCard.visibility = View.GONE
                // Define corner radii for each corner: top-left, top-right, bottom-right, bottom-left
                val cornerRadii = floatArrayOf(
                    15f, 15f,  // Top-left
                    0f, 0f,    // Top-right
                    15f, 15f,  // Bottom-right
                    15f, 15f   // Bottom-left
                )
                // Apply the background with theme color and custom corners
                ThemeUtils.applyBackgroundWithCorners(messageText, themeData?.botResponseBubbleColor, cornerRadii)
                ThemeUtils.applyTheme(messageText)

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

    class InitialViewHolder(
        itemView: View,
        themeData: ThemeResponse?,
        private val actionCallback: ChatActionCallback
    ) : RecyclerView.ViewHolder(itemView) {
        private val chipContainer: LinearLayout = itemView.findViewById(R.id.initialReplyChipContainer)
        private val strokeColor = Color.parseColor(themeData?.botResponseBubbleColor ?: "000000")
        private val whiteColor = Color.parseColor("#FFFFFF")

        fun bind(message: NCWMessage, position: Int) {
            chipContainer.removeAllViews() // Clear any existing chips
            chipContainer.visibility = View.VISIBLE

            message.quickReply?.options?.let { options ->
                options.forEach { option ->
                    val chip = Chip(itemView.context).apply {
                        text = option.label
                        isClickable = true
                        isCheckable = false
                        setOnClickListener {
                            Log.e("option.metadata", "" + option.metadata)
                            actionCallback.onQuickReply(option,position)
                        }
                        chipBackgroundColor = ColorStateList.valueOf(whiteColor)
                        setTextColor(strokeColor)
                        chipStrokeWidth = 1f
                        chipCornerRadius = 10f
                        chipStrokeColor = ColorStateList.valueOf(strokeColor)
                    }
                    chipContainer.addView(chip) // Add chips directly to the LinearLayout
                }
            }
        }
    }

    class ReceiverViewHolder(
        itemView: View,
        val themeData: ThemeResponse?,
        private val chatActionCallback:ChatActionCallback
    ) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tvReceiverMessage)
        private val imageView: ImageView = itemView.findViewById(R.id.receiverImage)
        private val videoView: VideoView = itemView.findViewById(R.id.receiverVideo)
        private val imgBot: ImageView = itemView.findViewById(R.id.img_bot)
        private val carouselRecyclerView: RecyclerView = itemView.findViewById(R.id.carouselRecyclerView)
        private val chipRecyclerViewGroup: RecyclerView = itemView.findViewById(R.id.quickReplyRecyclerView)
        private val receiverImageCard: CardView = itemView.findViewById(R.id.receiverImageCard)
        private val chipGroup: ChipGroup = itemView.findViewById(R.id.quickReplyChipGroup)
        private val cardViewCard: CardView = itemView.findViewById(R.id.cardViewCard)
        private val cardTitle: TextView = itemView.findViewById(R.id.cardTitle)
        private val cardText: TextView = itemView.findViewById(R.id.cardText)
        private val buttonRecyclerView: RecyclerView = itemView.findViewById(R.id.buttonRecyclerView)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val cardVideo: CardView = itemView.findViewById(R.id.cardVideo)

        fun bind(message: NCWMessage,position: Int) {
            // Hide all views initially to avoid redundant visibility changes
            messageText.visibility = View.GONE
            imageView.visibility = View.GONE
            videoView.visibility = View.GONE
            carouselRecyclerView.visibility = View.GONE
            receiverImageCard.visibility = View.GONE
            chipGroup.visibility = View.GONE
            cardViewCard.visibility = View.GONE
            cardVideo.visibility = View.GONE
            chipRecyclerViewGroup.visibility = View.GONE
            tvTime.text=NCWAppUtils.formatTimestampToTime(message.timestamp)
            imgBot.visibility = if (message.isSameTimeMessage) View.VISIBLE else View.INVISIBLE
            tvTime.visibility = if (message.isSameTimeMessage) View.VISIBLE else View.GONE

            when (message.type) {
                MessageType.TEXT -> {
                    message.message?.let { NCWAppUtils.setPlainText(it, messageText) }
                    messageText.visibility = View.VISIBLE
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
                    cardVideo.visibility =View.VISIBLE
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

           /* message.quickReply?.options?.let { options ->
                chipGroup.removeAllViews()
                chipGroup.visibility = View.VISIBLE

                options.forEach { option ->
                    val chip = Chip(itemView.context).apply {
                        text = option.label
                        isClickable = true
                        isCheckable = false
                        setOnClickListener {
                            Log.e("option.metadata", "" + option.metadata)

                            onQuickReply(option)
                        }
                        chipBackgroundColor = ColorStateList.valueOf(whiteColor)
                        setTextColor(blackColor)
                        chipStrokeWidth = 1f
                        chipCornerRadius = 10f
                        chipStrokeColor = ColorStateList.valueOf(blackColor)
                    }
                    chipGroup.addView(chip)
                }
            }*/
            message.quickReply?.options?.let {

                if (chipRecyclerViewGroup.adapter == null) {
                    chipRecyclerViewGroup.layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                }
                val chipAdapter = ChipAdapter(message.quickReply.options){
                    chatActionCallback.onQuickReply(it,position)

                }
                chipRecyclerViewGroup.adapter = chipAdapter
                chipRecyclerViewGroup.visibility = View.VISIBLE


            }

            receiverImageCard.setOnClickListener {
                message.largeImageUrl?.let { it1 -> chatActionCallback.onImageClick(it1) }
            }
        }
    }

}
