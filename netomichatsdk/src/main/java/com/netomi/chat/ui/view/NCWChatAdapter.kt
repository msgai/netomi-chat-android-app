package com.netomi.chat.ui.view

import android.net.Uri
import android.util.Log
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
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWChatActionCallback
import com.netomi.chat.utils.NCWFeedbackActionCallback
import com.netomi.chat.utils.NCWThemeUtils

class NCWChatAdapter(
    private val messages: MutableList<NCWMessage>,
    private val themeData: NCWThemeResponse?,
    private val actionCallback: NCWChatActionCallback,
    private val feedbackActionCallBack:NCWFeedbackActionCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_REQUEST = 1
        private const val VIEW_TYPE_RESPONSE = 2
        private const val VIEW_TYPE_INDICATOR = 3
        private const val VIEW_TYPE_FORM = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].sender) {
            NCWAppConstant.TYPE_REQUEST -> VIEW_TYPE_REQUEST
            NCWAppConstant.TYPE_INDICATOR -> VIEW_TYPE_INDICATOR
            NCWAppConstant.TYPE_FORM-> VIEW_TYPE_FORM
            else -> VIEW_TYPE_RESPONSE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            VIEW_TYPE_REQUEST -> inflater.inflate(R.layout.layout_request, parent, false)
            VIEW_TYPE_INDICATOR -> inflater.inflate(R.layout.layout_add_loader, parent, false)
            VIEW_TYPE_FORM -> inflater.inflate(R.layout.layout_form, parent, false)
            else -> inflater.inflate(R.layout.layout_response, parent, false)
        }
        return when (viewType) {
            VIEW_TYPE_REQUEST -> RequestViewHolder(view, themeData,actionCallback)
            VIEW_TYPE_INDICATOR -> InitialViewHolder(view)
            VIEW_TYPE_FORM -> FormViewHolder(view)
            else -> ResponseViewHolder(view,themeData,actionCallback,feedbackActionCallBack)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is RequestViewHolder -> holder.bind(message)
            is ResponseViewHolder -> holder.bind(message,position)
            is FormViewHolder->holder.bind(message)
           // is InitialViewHolder -> holder.bind(message,position)
        }
    }

    override fun getItemCount(): Int = messages.size

  class RequestViewHolder(
      itemView: View,
      private val themeData: NCWThemeResponse?,
      private val chatActionCallback: NCWChatActionCallback
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
      private val tvRetry: TextView = itemView.findViewById(R.id.tvRetry)



      fun bind(message: NCWMessage) {
          tvTime.text = NCWAppUtils.formatTimestampToTime(message.timestamp)
          NCWThemeUtils.setTimeStampColor(tvTime)
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
          tvRetry.visibility=if(message.isRetry) View.VISIBLE else View.GONE
          tvRetry.setOnClickListener { chatActionCallback.onRetryClicked(message) }

      }

      private fun setupFileMessage(message: NCWMessage) {
          requestDocCard.visibility=View.VISIBLE
          val cornerRadii = floatArrayOf(15f, 15f, 0f, 0f, 15f, 15f, 15f, 15f)
          NCWThemeUtils.applyBackgroundWithCorners(requestDocCard, themeData?.botResponseBubbleColor, cornerRadii)
          tvDocName.text=message.title
          if (message.fileSize!=null)
          tvDocType.text= NCWAppUtils.formatFileSize(message.fileSize.toLong())
          NCWThemeUtils.setUserConfig(tvDocName)
          NCWThemeUtils.setUserConfig(tvDocType)

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

          NCWThemeUtils.setUserConfig(messageText)

      }

      private fun setupImageMessage(message: NCWMessage) {
          imageView.visibility = View.VISIBLE
          senderImageCard.visibility = View.VISIBLE
          val image= if (message.message!=null) Uri.parse(message.message) else message.largeImageUrl
          Glide.with(itemView.context).load(image).placeholder(R.drawable.ic_place_holder).into(imageView)
      }

      private fun setupVideoMessage(message: NCWMessage) {
          senderVideoCard.visibility = View.VISIBLE
          videoView.visibility = View.VISIBLE

          Glide.with(itemView.context)
              .load(message.message ?: message.thumbnailUrl)
              .apply(RequestOptions().frame(1000))  // Show first frame for preview
              .placeholder(R.drawable.ic_place_holder)
              .into(videoView)
      }
  }




    class FormViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val rootLayout: ConstraintLayout = itemView.findViewById(R.id.constRow)
        private val tvFormTitle: TextView = itemView.findViewById(R.id.tvFormTitle)
        private val recyclerViewForm: RecyclerView = itemView.findViewById(R.id.formRecyclerView)
        private val tvFormDesc: TextView = itemView.findViewById(R.id.tvFormDesc)

        fun bind(message: NCWMessage) {
            NCWThemeUtils.setBotConfig(rootLayout)
            NCWThemeUtils.setBotTextColor(tvFormTitle)
            NCWThemeUtils.setTimeStampColor(tvFormTitle)
            tvFormTitle.text= message.formSchema?.properties?.question ?: ""
            if (message.formSchema?.properties?.description.isNullOrEmpty())
                tvFormDesc.visibility=View.GONE
            else
            {
                tvFormDesc.visibility=View.VISIBLE
                tvFormDesc.text= message.formSchema?.properties?.description ?: ""
            }

            recyclerViewForm.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            val formAdapter = NCWFormAdapter(message.formSchema?.schema ?: emptyList()){callBack->
                Log.e("SelectedComponent","iiit "+callBack)
                if (callBack != null) {
                    Log.e("SelectedComponent","iiit "+callBack.config)
                }
            }
            recyclerViewForm.adapter = formAdapter

        }

    }

    class InitialViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val LoaderContainer: ConstraintLayout = itemView.findViewById(R.id.constRow)

    }

    class ResponseViewHolder(
        itemView: View,
        val themeData: NCWThemeResponse?,
        private val chatActionCallback:NCWChatActionCallback,
        private val feedbackActionCallBack: NCWFeedbackActionCallback
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
        private val thumbUpImageButton: ImageView = itemView.findViewById(R.id.thumbUpButton)
        private val thumbDownImageButton: ImageView = itemView.findViewById(R.id.thumbDownButton)

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
            NCWThemeUtils.setTimeStampColor(tvTime)
            imgBot.visibility = if (message.isSameTimeMessage) View.VISIBLE else View.INVISIBLE
            tvTime.visibility = if (message.isSameTimeMessage) View.VISIBLE else View.GONE
            NCWThemeUtils.setBotConfig(thumbUpImageButton)
            NCWThemeUtils.setBotConfig(thumbDownImageButton)
            if(message.likeSelected){
                thumbUpImageButton.setImageResource(R.drawable.thumps_up_selected)
                thumbDownImageButton.setImageResource(R.drawable.thumps_down_unselected)
            }else if (message.dislikeSelected){
                thumbUpImageButton.setImageResource(R.drawable.thumps_up_unselected)
                thumbDownImageButton.setImageResource(R.drawable.thumps_down_selected)
            }else{
                thumbUpImageButton.setImageResource(R.drawable.thumps_down_unselected)
                thumbDownImageButton.setImageResource(R.drawable.thumps_up_unselected)
            }

            when (message.type) {
                MessageType.TEXT -> {
                    message.message?.let {
                      //messageText.text=NCWAppUtils.setHtmText(it)
                        NCWAppUtils.setHtmlText(messageText,it)
                        messageText.visibility = View.VISIBLE
                        NCWThemeUtils.setBotConfig(messageText)
                    } ?: run {
                        messageText.visibility = View.GONE
                        tvTime.visibility = View.GONE
                        imgBot.visibility= View.INVISIBLE
                    }

                }

                MessageType.IMAGE -> {
                    Glide.with(itemView.context).load(message.largeImageUrl).placeholder(R.drawable.ic_place_holder).into(imageView)
                    imageView.visibility = View.VISIBLE
                    receiverImageCard.visibility = View.VISIBLE
                }

                MessageType.CAROUSEL -> {
                    if (carouselRecyclerView.adapter == null) {
                        carouselRecyclerView.layoutManager =
                            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    }

                    val carouselAdapter = NCWCarouselAdapter(message.carouselItems ?: emptyList()){
                        chatActionCallback.carouselButtonAction(it)
                    }
                    carouselRecyclerView.adapter = carouselAdapter
                    carouselRecyclerView.visibility = View.VISIBLE
                }

                MessageType.VIDEO -> {
                    Glide.with(itemView.context)
                        .load(message.thumbnailUrl)
                        .apply(RequestOptions().frame(1000))
                        .placeholder(R.drawable.ic_place_holder)
                        .into(videoView)

                    videoView.visibility = View.VISIBLE
                    cardVideo.visibility =View.VISIBLE
                }

                MessageType.CARD -> {
                    cardViewCard.visibility = View.VISIBLE
                    NCWThemeUtils.setBotConfig(cardViewCard)
                    cardTitle.text=message.message
                    cardText.text=message.title
                    buttonRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                    val carouselAdapter = NCWCarouselButtonAdapter(message.buttons){
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
                val chipAdapter = NCWChipAdapter(message.quickReply.options) { selectedOption ->
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
            // Thumbs-up click listener
           thumbUpImageButton.setOnClickListener {
                message.likeSelected = !message.likeSelected
                message.dislikeSelected = false // Ensure only one can be selected
                feedbackActionCallBack.onThumbUpClick()
            }

            // Thumbs-down click listener
            thumbDownImageButton.setOnClickListener {
                message.dislikeSelected = !message.dislikeSelected
                message.likeSelected = false // Ensure only one can be selected
                feedbackActionCallBack.onThumbDownClick()
            }
        }
    }

}
