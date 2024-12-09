package com.netomi.chat.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.netomi.chat.model.theme.ThemeResponse

object ThemeUtils
{

    private var themeData: ThemeResponse? = null

    // Set theme data globally
    fun setThemeData(themeResponse: ThemeResponse) {
        themeData = themeResponse
    }
    fun getThemeData():ThemeResponse?{
        return themeData
    }

    private var conversationID : String? = null

    fun setConversationID(id: String?) {
        conversationID = id
    }
    fun getConversationID():String?{
        return conversationID
    }

    fun configureHeader(
        headerContainer: ConstraintLayout,
        ivMenu: ImageView,
        closeIcon: ImageView,
        headerTextView: TextView,
        window: Window,
        rootLayout: View,
        context: Context,
        progressBar: ProgressBar
    ) {
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
            // Apply gradient or background color
            if (headerConfig.isGradientAppied) {
                applyGradient(headerContainer, rootLayout, window)
            } else {
                applyBackgroundColor(headerConfig.backgroundColor, headerContainer, window, context)
            }

            // Set header text
            headerTextView.text = themeData?.title ?: ""

            headerConfig.tintColor?.let { setTextColor(headerTextView, it) }
            // Apply icon styles
            headerConfig.iconBackgroundColor?.let { bgColor ->
              val tintColor = headerConfig.tintColor
                styleIcon(ivMenu, bgColor, tintColor)
                styleIcon(closeIcon, bgColor, tintColor)
            }
            val progressBarColor= headerConfig.backgroundColor?.let { parseColor(it) }
            progressBar.indeterminateDrawable.colorFilter =
                progressBarColor?.let { PorterDuffColorFilter(it, PorterDuff.Mode.SRC_IN) }
        }
    }

    fun configureFooter(
        footerContainer: ConstraintLayout,
        ivMenuOption: ImageView,
        messageInputField: EditText,
        attachmentIcon: ImageView,
        sendMessageIcon: ImageView
    ) {
        themeData?.mobileConfig?.lightTheme?.footerConfig?.let { footerConfig ->
            // Set background color
            footerConfig.backgroundColor?.let { color ->
                parseColor(color).let { footerContainer.setBackgroundColor(it) }
            }

            // Set icon styles and tints
            footerConfig.tintColor?.let { tintColor ->
                footerConfig.backgroundColor?.let { bgColor ->
                    styleIcon(ivMenuOption, bgColor, tintColor)
                }
            //    setTint(ivMenuOption, tintColor)
                setTint(attachmentIcon, tintColor)
              //  setTint(sendMessageIcon, tintColor)
            }

            // Set message input field text color
            footerConfig.inputBoxTextColor?.let { textColor ->
                parseColor(textColor)?.let { messageInputField.setTextColor(it) }
            }
        }
    }

    fun setUserConfig(messageText: TextView) {
        themeData?.mobileConfig?.lightTheme?.userConfig?.let { userConfig ->
            val bubbleConfig = themeData?.mobileConfig?.lightTheme?.bubbleConfig
            val borderRadius = bubbleConfig?.borderRadius?.toFloat() ?: 0f
            val cornerRadii = floatArrayOf(borderRadius, borderRadius, 0f, 0f, borderRadius, borderRadius, borderRadius, borderRadius)

            // Apply background with corner radii
            applyBackgroundWithCorners(messageText, userConfig.backgroundColor, cornerRadii)

            // Set text color
            userConfig.textColor?.let { color -> setTextColor(messageText, color) }
        }
    }

    fun setBotConfig(messageText: View) {
        themeData?.mobileConfig?.lightTheme?.botConfig?.let { botConfig ->
            val bubbleConfig = themeData?.mobileConfig?.lightTheme?.bubbleConfig
            val borderRadius = bubbleConfig?.borderRadius?.toFloat() ?: 0f
            val cornerRadii = floatArrayOf(0f, 0f, borderRadius, borderRadius, borderRadius, borderRadius, borderRadius, borderRadius)
            // Apply background with corner radii
            applyBackgroundWithCorners(messageText, botConfig.backgroundColor, cornerRadii)

            // Set text color
            if (messageText is TextView)
            botConfig.textColor?.let { color -> setTextColor(messageText, color) }
        }
    }

    /**
     * Applies a customizable GradientDrawable background to a Chip.
     *
     * @param textView The TextView to apply the background to.
     * @param cornerRadii Array of corner radii in the order of top-left, top-right, bottom-right, and bottom-left.
     */
    fun setQuickReply(quickReplyView: View,quickReplyTextView: TextView) {
        themeData?.mobileConfig?.lightTheme?.botConfig?.let { botConfig ->
            val borderRadius = themeData?.mobileConfig?.lightTheme?.bubbleConfig?.borderRadius?.toFloat() ?: 10f
            val cornerRadii = floatArrayOf(
                0f, 0f, borderRadius, borderRadius,
                borderRadius, borderRadius, borderRadius, borderRadius
            )

            // Apply background and border
            applyBorderWithCorners(
                quickReplyView,
                botConfig.quickReplyBackgroundColor,
                botConfig.quickReplyBorderColor,
                cornerRadii
            )

            // Set text color
            botConfig.quickReplyTextColor?.let { textColor ->
                setTextColor(quickReplyTextView, textColor)
            }
        }
    }




    fun applyBorderWithCorners(
        view: View,
        backgroundColor: String?,
        border: String?,
        cornerRadii: FloatArray = floatArrayOf(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f)
    ) {
        val parsedColor = Color.parseColor(backgroundColor ?: "#374E57") // Default color if null
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadii = cornerRadii
            setColor(parsedColor)
            border?.let { setStroke(2, parseColor(it))}

        }

        view.background = backgroundDrawable
    }

    /**
     * Creates a rounded drawable with the specified background color.
     */
     fun createRoundedDrawable(view: View) {

        themeData?.mobileConfig?.lightTheme?.headerConfig?.let {
            val parsedColor = Color.parseColor(it.backgroundColor ?: "#374E57")
            val backgroundDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f
                setColor(parsedColor)
            }
            view.background = backgroundDrawable
        }

    }




    fun setTimeStampColor(tvTimeStamp: TextView){
         themeData?.mobileConfig?.lightTheme?.bubbleConfig?.let { bubbleConfig ->
             bubbleConfig.timeStampColor?.let { color -> setTextColor(tvTimeStamp, color) }
         }
     }

    private fun setTextColor(textView: TextView, color: String){
        textView.setTextColor(parseColor(color))
    }


    /**
     * Set tint color for an ImageView.
     *
     * @param imageView The ImageView to apply the tint to.
     * @param tintColor The tint color as a string.
     */
    private fun setTint(imageView: ImageView, tintColor: String?) {
        tintColor?.let { color ->
            parseColor(color)?.let { imageView.imageTintList = ColorStateList.valueOf(it) }
        }
    }




    private fun applyGradient(
        headerContainer: ConstraintLayout,
        rootLayout: View,
        window: Window
    ) {
        val gradientDrawable = createGradientDrawable()
        headerContainer.background = gradientDrawable
        rootLayout.background = gradientDrawable
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun applyBackgroundColor(
        color: String?,
        headerContainer: ConstraintLayout,
        window: Window,
        context: Context
    ) {
        val parsedColor = color?.let { parseColor(it) } ?: ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        headerContainer.setBackgroundColor(parsedColor)
        window.statusBarColor = parsedColor
    }

    private fun styleIcon(imageView: ImageView, backgroundColor: String, tintColor: String?) {
        val bgParsedColor = parseColor(backgroundColor)
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(bgParsedColor)
            cornerRadius = 15f
        }
        imageView.background = drawable
     //   imageView.imageTintList = tintColor?.let { ColorStateList.valueOf(parseColor(it)) }

        tintColor?.let {
            val tintList = ColorStateList.valueOf(parseColor(it))
            ImageViewCompat.setImageTintList(imageView, tintList)
        } ?: ImageViewCompat.setImageTintList(imageView, null)
    }

    // Utility function to parse color strings (e.g., "#FF5733")
     fun parseColor(colorString: String): Int {
        return try {
            Color.parseColor(colorString)
        } catch (e: IllegalArgumentException) {
            Color.BLACK // default fallback color
        }
    }

    /**
     * Creates a gradient drawable based on the theme configuration.
     * @param theme The theme settings with gradient configuration.
     * @return A GradientDrawable with specified direction and colors.
     */
    private fun createGradientDrawable(): GradientDrawable? {
        val direction = GradientDrawable.Orientation.values()
            .getOrElse(themeData?.mobileConfig?.lightTheme?.headerConfig?.gradientDirection ?: 0) { GradientDrawable.Orientation.LEFT_RIGHT }
        val gradientColors = themeData?.mobileConfig?.lightTheme?.headerConfig?.gradientColors?.map { Color.parseColor(it) }?.toIntArray()
        return gradientColors?.let { GradientDrawable(direction, it) }
    }




    /**
     * Set dynamic tint color and background for an ImageView.
     *
     * @param imageView The ImageView to apply the tint and background to.
     * @param color The color to set for the tint and background.
     * @param isCircularBackground Boolean flag to determine if the background should be circular.
     */
    fun applyBackgroundAndTint(imageView: ImageView, color: String, tintColor: String?) {

        val parsedColor = Color.parseColor(color)

            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.RECTANGLE
            drawable.setColor(parsedColor)  // Set the background color
            drawable.cornerRadius = 15f  // Adjust the corner radius for rounded effect
            imageView.background = drawable

            imageView.imageTintList = ColorStateList.valueOf( Color.parseColor(tintColor))

    }

    /**
     * Set dynamic tint color and background for an ImageView.
     *
     * @param imageView The ImageView to apply the tint and background to.
     * @param color The color to set for the tint and background.
     * @param isCircularBackground Boolean flag to determine if the background should be circular.
     */
    fun applyCircularBackgroundAndTint(imageView: ImageView, color: String, isCircularBackground: Boolean) {

        val parsedColor = Color.parseColor(color)

        if (isCircularBackground) {
            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.OVAL  // Circular shape
            drawable.setColor(parsedColor)  // Set the background color
            drawable.cornerRadius = 50f  // Adjust the corner radius for rounded effect
            imageView.background = drawable
        } else {
            imageView.imageTintList = ColorStateList.valueOf(parsedColor)
        }
    }

    /**
     * Applies a customizable GradientDrawable background to a TextView.
     *
     * @param textView The TextView to apply the background to.
     * @param color The background color in hex format (e.g., "#4D39E9"). Defaults to "#4D39E9" if null.
     * @param cornerRadii Array of corner radii in the order of top-left, top-right, bottom-right, and bottom-left.
     */
    fun applyBackgroundWithCorners(
        view: View,
        color: String?,
        cornerRadii: FloatArray = floatArrayOf(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f)
    ) {
        val parsedColor = Color.parseColor(color ?: "#374E57") // Default color if null
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadii = cornerRadii
            setColor(parsedColor)
        }

        view.background = backgroundDrawable
    }

    /**
     * Applies a customizable GradientDrawable background to a Chip.
     *
     * @param textView The TextView to apply the background to.
     * @param cornerRadii Array of corner radii in the order of top-left, top-right, bottom-right, and bottom-left.
     */
    fun applyChipBackgroundWithCorners(
        textView: View,
        cornerRadii: FloatArray = floatArrayOf(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f),
        opacity: Int = 128
    ) {
        val baseColor = Color.parseColor(themeData?.theme?.color ?: "#374E57") // Default color if null

        val colorWithOpacity = Color.argb(opacity, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))

        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadii = cornerRadii
            setColor(colorWithOpacity)
        }
        textView.background = backgroundDrawable
    }

    /**
     * Applies a customizable GradientDrawable background to a Chip.
     *
     * @param textView The TextView to apply the background to.
     * @param cornerRadii Array of corner radii in the order of top-left, top-right, bottom-right, and bottom-left.
     */
    fun applyChipBackgroundWithCorners(
        textView: TextView,
        cornerRadii: FloatArray = floatArrayOf(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f),
        opacity: Int = 128
    ) {
        val baseColor = Color.parseColor(themeData?.theme?.color ?: "#374E57") // Default color if null

        val colorWithOpacity = Color.argb(opacity, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))

        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadii = cornerRadii
            setColor(colorWithOpacity)
        }
        textView.background = backgroundDrawable
    }


    // Function to load an image into ImageView (e.g., using Glide or Coil)
    private fun loadImageIntoView(imageView: ImageView, imageUrl: String) {
        // Example using Glide library to load images
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }





}
