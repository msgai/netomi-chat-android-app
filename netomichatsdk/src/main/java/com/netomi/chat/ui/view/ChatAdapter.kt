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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.utils.ChatActionCallback
import com.netomi.chat.utils.NCWAppConstant
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
        private const val VIEW_TYPE_REQUEST = 1
        private const val VIEW_TYPE_RESPONSE = 2
        private const val VIEW_INDICATOR = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].sender) {
            NCWAppConstant.TYPE_REQUEST -> VIEW_TYPE_REQUEST
            INITIAL -> VIEW_INDICATOR
            else -> VIEW_TYPE_RESPONSE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            VIEW_TYPE_REQUEST -> inflater.inflate(R.layout.item_sender, parent, false)
            VIEW_INDICATOR -> inflater.inflate(R.layout.layout_add_loader, parent, false)
            else -> inflater.inflate(R.layout.item_receiver, parent, false)
        }
        return when (viewType) {
            VIEW_TYPE_REQUEST -> RequestViewHolder(view, themeData)
            VIEW_INDICATOR -> InitialViewHolder(view)
            else -> ResponseViewHolder(view,themeData,actionCallback)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is RequestViewHolder -> holder.bind(message)
            is ResponseViewHolder -> holder.bind(message,position)
           // is InitialViewHolder -> holder.bind(message,position)
        }
    }

    override fun getItemCount(): Int = messages.size

    class RequestViewHolder(itemView: View, private val themeData: ThemeResponse?) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tvSenderMessage)
        private val imageView: ImageView = itemView.findViewById(R.id.senderImage)
        private val videoView: ImageView = itemView.findViewById(R.id.senderVideo)
        private val senderImageCard: CardView = itemView.findViewById(R.id.senderImageCard)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val senderVideoCard: CardView = itemView.findViewById(R.id.senderVideoCard)



        fun bind(message: NCWMessage) {
            // Show or hide views based on the message type
            tvTime.text=NCWAppUtils.formatTimestampToTime(message.timestamp)
            if (message.type == MessageType.TEXT) {
                senderVideoCard.visibility = View.GONE
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
                senderVideoCard.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                imageView.setImageURI(Uri.parse(message.message))
                // Glide.with(itemView.context).load(message.content).into(imageView)
                messageText.visibility = View.GONE
                videoView.visibility = View.GONE
                senderImageCard.visibility = View.VISIBLE
            } else if (message.type == MessageType.VIDEO) {
                senderVideoCard.visibility = View.VISIBLE
                Log.e("Dataqtat",""+message.message)
                videoView.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(message.message)
                    .apply(RequestOptions().frame(1000)) 
                    .into(videoView)
                messageText.visibility = View.GONE
                imageView.visibility = View.GONE
                senderImageCard.visibility = View.GONE
            }
        }
    }

    class InitialViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val LoaderContainer: ConstraintLayout = itemView.findViewById(R.id.constRow)

    }

    class ResponseViewHolder(
        itemView: View,
        val themeData: ThemeResponse?,
        private val chatActionCallback:ChatActionCallback
    ) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tvReceiverMessage)
        private val imageView: ImageView = itemView.findViewById(R.id.receiverImage)
        private val videoView: ImageView = itemView.findViewById(R.id.receiverVideo)
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
                    message.message?.let {
                      messageText.text=NCWAppUtils.setHtmText(it)
                        messageText.visibility = View.VISIBLE
                    } ?: run {
                        messageText.visibility = View.GONE
                        tvTime.visibility = View.GONE
                        imgBot.visibility= View.INVISIBLE
                    }

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

                    val carouselAdapter = CarouselAdapter(message.carouselItems ?: emptyList()){
                        chatActionCallback.carouselButtonAction(it)
                    }
                    carouselRecyclerView.adapter = carouselAdapter
                    carouselRecyclerView.visibility = View.VISIBLE
                }

                MessageType.VIDEO -> {
                    Glide.with(itemView.context)
                        .load(message.thumbnailUrl)
                        .apply(RequestOptions().frame(1000)) 
                        .into(videoView)

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

            // Quick Reply Handling
            if (message.isQuickReplyVisible && message.quickReply?.options != null) {
                if (chipRecyclerViewGroup.adapter == null) {
                    chipRecyclerViewGroup.layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                }
                val chipAdapter = ChipAdapter(message.quickReply.options) { selectedOption ->
                    chatActionCallback.onQuickReply(selectedOption, position)
                }
                chipRecyclerViewGroup.adapter = chipAdapter
                chipRecyclerViewGroup.visibility = View.VISIBLE
            } else {
                chipRecyclerViewGroup.visibility = View.GONE
            }

       /*     message.quickReply?.options?.let {

                if (chipRecyclerViewGroup.adapter == null) {
                    chipRecyclerViewGroup.layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                }
                val chipAdapter = ChipAdapter(message.quickReply.options){
                    chatActionCallback.onQuickReply(it,position)

                }
                chipRecyclerViewGroup.adapter = chipAdapter
                chipRecyclerViewGroup.visibility = View.VISIBLE


            }*/

            receiverImageCard.setOnClickListener {
                chatActionCallback.onMediaClick(message)
            }
            cardVideo.setOnClickListener {
                chatActionCallback.onMediaClick(message)
            }
        }
    }

}
