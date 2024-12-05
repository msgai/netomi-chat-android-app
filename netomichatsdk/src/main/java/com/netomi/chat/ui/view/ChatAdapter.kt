package com.netomi.chat.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.ChipGroup
import com.netomi.chat.R
import com.netomi.chat.model.MessageType
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.theme.ThemeResponse
import com.netomi.chat.utils.ChatActionCallback
import com.netomi.chat.utils.NCWAppConstant
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
        private const val VIEW_TYPE_INDICATOR = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].sender) {
            NCWAppConstant.TYPE_REQUEST -> VIEW_TYPE_REQUEST
            NCWAppConstant.TYPE_INDICATOR -> VIEW_TYPE_INDICATOR
            else -> VIEW_TYPE_RESPONSE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            VIEW_TYPE_REQUEST -> inflater.inflate(R.layout.layout_request, parent, false)
            VIEW_TYPE_INDICATOR -> inflater.inflate(R.layout.layout_add_loader, parent, false)
            else -> inflater.inflate(R.layout.layout_response, parent, false)
        }
        return when (viewType) {
            VIEW_TYPE_REQUEST -> RequestViewHolder(view, themeData,actionCallback)
            VIEW_TYPE_INDICATOR -> InitialViewHolder(view)
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

  class RequestViewHolder(
      itemView: View,
      private val themeData: ThemeResponse?,
      private val chatActionCallback: ChatActionCallback
  ) : RecyclerView.ViewHolder(itemView) {

      private val messageText: TextView = itemView.findViewById(R.id.tvSenderMessage)
      private val imageView: ImageView = itemView.findViewById(R.id.senderImage)
      private val videoView: ImageView = itemView.findViewById(R.id.senderVideo)
      private val senderImageCard: CardView = itemView.findViewById(R.id.senderImageCard)
      private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
      private val senderVideoCard: CardView = itemView.findViewById(R.id.senderVideoCard)
      private val requestDocCard: ConstraintLayout = itemView.findViewById(R.id.docCard)
      private val tvDocName: TextView = itemView.findViewById(R.id.tvDocName)
      private val tvDocType: TextView = itemView.findViewById(R.id.tvDocType)



      fun bind(message: NCWMessage) {
          tvTime.text = NCWAppUtils.formatTimestampToTime(message.timestamp)
          ThemeUtils.setTimeStampColor(tvTime)
          // Reset visibility to GONE for all views initially
          resetVisibility()

          when (message.type) {
              MessageType.TEXT -> {
                  setupTextMessage(message)
              }
              MessageType.IMAGE -> {
                  setupImageMessage(message)
              }
              MessageType.VIDEO -> {
                  setupVideoMessage(message)
              }
              MessageType.FILE -> {
                  setupFileMessage(message)
              }

              else -> {}
          }

          // Handle media click actions
          imageView.setOnClickListener { chatActionCallback.onMediaClick(message) }
          senderVideoCard.setOnClickListener { chatActionCallback.onMediaClick(message) }
          requestDocCard.setOnClickListener { chatActionCallback.onMediaClick(message) }
      }

      private fun setupFileMessage(message: NCWMessage) {
          requestDocCard.visibility=View.VISIBLE
          val cornerRadii = floatArrayOf(15f, 15f, 0f, 0f, 15f, 15f, 15f, 15f)
          ThemeUtils.applyBackgroundWithCorners(requestDocCard, themeData?.botResponseBubbleColor, cornerRadii)
          tvDocName.text=message.title
          if (message.fileSize!=null)
          tvDocType.text= NCWAppUtils.formatFileSize(message.fileSize.toLong())
          ThemeUtils.setUserConfig(tvDocName)
          ThemeUtils.setUserConfig(tvDocType)

      }

      private fun resetVisibility() {
          messageText.visibility = View.GONE
          imageView.visibility = View.GONE
          videoView.visibility = View.GONE
          senderImageCard.visibility = View.GONE
          senderVideoCard.visibility = View.GONE
          requestDocCard.visibility = View.GONE
      }

      private fun setupTextMessage(message: NCWMessage) {
          messageText.visibility = View.VISIBLE
          messageText.text = message.message

          ThemeUtils.setUserConfig(messageText)

      }

      private fun setupImageMessage(message: NCWMessage) {
          imageView.visibility = View.VISIBLE
          senderImageCard.visibility = View.VISIBLE
          Glide.with(itemView.context).load(message.largeImageUrl ?: Uri.parse(message.message)).into(imageView)
      }

      private fun setupVideoMessage(message: NCWMessage) {
          senderVideoCard.visibility = View.VISIBLE
          videoView.visibility = View.VISIBLE

          Glide.with(itemView.context)
              .load(message.thumbnailUrl ?: message.message)
              .apply(RequestOptions().frame(1000))  // Show first frame for preview
              .into(videoView)
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
        private val cardViewCard: ConstraintLayout = itemView.findViewById(R.id.cardViewCard)
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
            ThemeUtils.setTimeStampColor(tvTime)
            imgBot.visibility = if (message.isSameTimeMessage) View.VISIBLE else View.INVISIBLE
            tvTime.visibility = if (message.isSameTimeMessage) View.VISIBLE else View.GONE

            when (message.type) {
                MessageType.TEXT -> {
                    message.message?.let {
                      messageText.text=NCWAppUtils.setHtmText(it)
                        messageText.visibility = View.VISIBLE
                        ThemeUtils.setBotConfig(messageText)
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
                    ThemeUtils.setBotConfig(cardViewCard)
                    cardTitle.text=message.message
                    cardText.text=message.title
                    buttonRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                    val carouselAdapter = CarouselButtonAdapter(message.buttons){
                        chatActionCallback.carouselButtonAction(it)
                    }
                    buttonRecyclerView.adapter = carouselAdapter

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
