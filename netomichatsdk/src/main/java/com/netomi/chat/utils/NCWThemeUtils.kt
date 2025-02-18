package com.netomi.chat.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.survey.NCWSignInUserDetails
import com.netomi.chat.ui.init.NCWChatSdk

object NCWThemeUtils
{

    private var themeData: NCWThemeResponse? = null

    // Set theme data globally
    fun setThemeData(themeResponse: NCWThemeResponse?) {
        themeData = themeResponse
    }
    fun getThemeData():NCWThemeResponse?{
        return themeData
    }

    private var conversationID : String? = null
    private var jwtToken : String? = null
    var userDetails: NCWSignInUserDetails? = null
    private var deviceToken : String? = null

    fun setConversationID(id: String?) {
        conversationID = id
    }
    fun getConversationID():String?{
        return conversationID
    }

    fun setJwtToken(token: String?) {
        jwtToken = token
    }
    fun getJwtToken():String?{
        return jwtToken
    }
    fun setSignInUserDetails(signInUserDetails: NCWSignInUserDetails?) {
        userDetails = signInUserDetails
    }

    fun getSignInUserDetails():NCWSignInUserDetails? {
       return userDetails
    }



    fun configureHeader(
        headerContainer: ConstraintLayout,
        ivMenu: ImageView,
        closeIcon: ImageView,
        headerTextView: TextView,
        window: Window,
        rootLayout: View,
        context: Context,
        progressBar: ProgressBar,
        logoIcon: ImageView
    ) {
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
            // Apply gradient or background color
            if (headerConfig.isGradientAppied) {
                createGradientDrawable()?.let { gradientDrawable ->
                    applyGradient(headerContainer, rootLayout, window, gradientDrawable)
                } ?: applyBackgroundColor(headerConfig.backgroundColor, headerContainer, window, context)
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

            headerConfig.logoImage?.takeIf { it.isNotEmpty() }?.let { logoUrl ->
                Glide.with(context)
                    .load(logoUrl)
                    .placeholder(R.drawable.ic_support)
                    .into(logoIcon)
            }


        }
    }

    fun setHeader( headerContainer: ConstraintLayout,  window: Window,
                   rootLayout: View,context: Context,)
    {
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
            // Apply gradient or background color
            if (headerConfig.isGradientAppied) {
                createGradientDrawable()?.let { gradientDrawable ->
                    applyGradient(headerContainer, rootLayout, window, gradientDrawable)
                } ?: applyBackgroundColor(
                    headerConfig.backgroundColor,
                    headerContainer,
                    window,
                    context
                )
            } else {
                applyBackgroundColor(headerConfig.backgroundColor, headerContainer, window, context)
            }
        }
    }

    fun setLogoIcon(logoIcon: ImageView,context: Context){
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
            headerConfig.logoImage?.takeIf { it.isNotEmpty() }?.let { logoUrl ->
                Glide.with(context)
                    .load(logoUrl)
                    .placeholder(R.drawable.ic_support)
                    .into(logoIcon)
            }
        }
        }

    fun configureFooter(
        footerContainer: ConstraintLayout,
        ivMenuOption: ImageView,
        messageInputField: EditText,
        attachmentIcon: ImageView,
        sendMessageIcon: ImageView,
        cardViewInputBox: CardView,
        tvBrandName: TextView
    ) {
        footerContainer.visibility = if (themeData?.mobileConfig?.lightTheme?.footerConfig?.isFooterHidden == true) View.GONE else View.VISIBLE

        themeData?.mobileConfig?.lightTheme?.footerConfig?.let { footerConfig ->
            // Set background color
            footerConfig.backgroundColor?.let { color ->
                parseColor(color).let {
                    footerContainer.setBackgroundColor(it)
                    tvBrandName.setBackgroundColor(it)
                }
            }

            // Set icon styles and tints
            footerConfig.tintColor?.let { tintColor ->
                footerConfig.backgroundColor?.let { bgColor ->
                    styleIcon(ivMenuOption, bgColor, tintColor)
                }
                setTint(attachmentIcon, tintColor)
               setCircularBackgroundAndTint(sendMessageIcon,footerConfig.sendButtonBackgroundColor,footerConfig.sendButtonColor)

            }

            // Set message input field text color
            footerConfig.inputBoxTextColor?.let { textColor ->
                parseColor(textColor)?.let { messageInputField.setTextColor(it) }
            }
            footerConfig.inputBoxBackgroundColor?.let { inputBoxBackgroundColor ->
                parseColor(inputBoxBackgroundColor)?.let { cardViewInputBox.setCardBackgroundColor(it) }
            }
        }
    }

    fun setUserConfig(messageText: View) {
        themeData?.mobileConfig?.lightTheme?.userConfig?.let { userConfig ->
            val bubbleConfig = themeData?.mobileConfig?.lightTheme?.bubbleConfig
            val borderRadius = bubbleConfig?.borderRadius?.toFloat() ?: 0f
            val cornerRadii = floatArrayOf(borderRadius, borderRadius, 0f, 0f, borderRadius, borderRadius, borderRadius, borderRadius)

            // Apply background with corner radii
            applyBackgroundWithCorners(messageText, userConfig.backgroundColor, cornerRadii)
              if (messageText is TextView)
            userConfig.textColor?.let { color -> setTextColor(messageText, color) }
        }
    }

   fun setUserConfigTextColor(textView: TextView) {
       themeData?.mobileConfig?.lightTheme?.userConfig?.let { userConfig ->
           userConfig.textColor?.let { color ->
               setTextColor(textView,color)
           }
       }
   }

    fun setRadioButtonUserConfig(messageText: RadioButton) {
        messageText.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().titleColor))
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

    fun setBotTextColor(textView: TextView) {
        themeData?.mobileConfig?.lightTheme?.botConfig?.let { botConfig ->
                botConfig.textColor?.let { color -> setTextColor(textView, color) }
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




    private fun applyBorderWithCorners(
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

        themeData?.mobileConfig?.lightTheme?.otherConfig?.let {
            val parsedColor = Color.parseColor(it.backgroundColor)
            val backgroundDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(parsedColor)
            }
            view.background = backgroundDrawable
        }
        if (view is TextView)
        setTitleColor(view)

    }

    fun createRoundedDrawableSubmit(view: View) {
        themeData?.mobileConfig?.lightTheme?.otherConfig?.let { otherConfig ->
            val parsedColor = Color.parseColor( otherConfig.backgroundColor)
            val backgroundDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(parsedColor)
            }
            view.background = backgroundDrawable

            if (view is TextView)
                otherConfig.titleColor?.let { color -> setTextColor(view, color) }
        }
    }

    fun createRoundedDrawableClose(view: View) {
        themeData?.mobileConfig?.lightTheme?.botConfig?.let { userConfig ->
            val parsedColor = Color.parseColor( userConfig.backgroundColor)
            val backgroundDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(parsedColor)
            }
            view.background = backgroundDrawable

            if (view is TextView)
                setTitleColor(view)
        }
    }

    fun setTitleColor(textView: TextView){
        themeData?.mobileConfig?.lightTheme?.otherConfig?.let { otherConfig ->
            otherConfig.titleColor?.let { color -> setTextColor(textView, color) }
        }
    }

    fun setDescriptionColor(textView: TextView){
        themeData?.mobileConfig?.lightTheme?.otherConfig?.let { otherConfig ->
            otherConfig.descriptionColor?.let { color -> setTextColor(textView, color) }
        }
    }





    fun setTimeStampColor(tvTimeStamp: TextView){
         themeData?.mobileConfig?.lightTheme?.bubbleConfig?.let { bubbleConfig ->
             bubbleConfig.timeStampColor?.let { color -> setTextColor(tvTimeStamp, color) }
         }
     }

    private fun setTextColor(textView: TextView, color: String){
        Log.e("FinalSet","final Set "+color)
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
        window: Window,
        gradientDrawable: GradientDrawable?
    ) {
        if (gradientDrawable != null) {
            headerContainer.background = gradientDrawable
            rootLayout.background = gradientDrawable
            window.apply {
                statusBarColor = Color.TRANSPARENT
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
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
        try {
            Log.e("Hereee", "adddddd ${themeData?.mobileConfig?.lightTheme}")

            // Ensure a valid gradient direction
            val direction = GradientDrawable.Orientation.values()
                .getOrElse(themeData?.mobileConfig?.lightTheme?.headerConfig?.gradientDirection ?: 0) {
                    GradientDrawable.Orientation.LEFT_RIGHT
                }

            // Parse and validate gradient colors
            val gradientColors = themeData?.mobileConfig?.lightTheme?.headerConfig?.gradientColors
                ?.mapNotNull { color ->
                    try {
                        Color.parseColor(color)
                    } catch (e: IllegalArgumentException) {
                        Log.e("ColorParseError", "Invalid color: $color")
                        null // Ignore invalid color strings
                    }
                }?.toIntArray()

            // Ensure gradient colors are valid
            if (gradientColors == null || gradientColors.isEmpty() || gradientColors.size < 2) {
                Log.e("GradientError", "Gradient colors are null or empty")
                return null
            }

            // Create and return the GradientDrawable
            return GradientDrawable(direction, gradientColors)
        } catch (ex: Exception) {
            Log.e("Exception", "Error creating gradient drawable: $ex")
            return null
        }
    }

    /* private fun createGradientDrawable(): GradientDrawable? {
         try {
             Log.e("Hereee","adddddd "+themeData?.mobileConfig?.lightTheme)
             val direction = GradientDrawable.Orientation.values()
                 .getOrElse(themeData?.mobileConfig?.lightTheme?.headerConfig?.gradientDirection ?: 0) { GradientDrawable.Orientation.LEFT_RIGHT }
             val gradientColors = themeData?.mobileConfig?.lightTheme?.headerConfig?.gradientColors?.map { Color.parseColor(it) }?.toIntArray()
             return gradientColors?.let { GradientDrawable(direction, it) }

         }
         catch (ex:Exception){
             Log.e("Exception","saassa "+ex )
         }
         return null

     }*/




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

    private fun setCircularBackgroundAndTint(imageView: ImageView, color: String, tintColor: String) {

        val parsedColor = Color.parseColor(color)
        val tintColor = Color.parseColor(tintColor)

            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.OVAL
            drawable.setColor(parsedColor)
            drawable.cornerRadius = 50f
            imageView.background = drawable

            imageView.imageTintList = ColorStateList.valueOf(tintColor)

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

    fun applyButtonBackgroundWithCorners(
        button: Button,
        opacity: Int = 128,
        isSelected:Boolean=false
    ) {
        val baseColor = Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().backgroundColor) // Default color if null
        val colorWithOpacity= if(isSelected){
            Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().backgroundColor)
        }else{
            Color.argb(opacity, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))
        }

        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setColor(colorWithOpacity)
        }
        button.background = backgroundDrawable
    }


    // Function to load an image into ImageView (e.g., using Glide or Coil)
    private fun loadImageIntoView(imageView: ImageView, imageUrl: String) {
        // Example using Glide library to load images
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }




    fun createSelectedRoundedDrawable(view: View) {
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
           // val parsedColor = Color.parseColor(headerConfig.backgroundColor)
            val parsedColor = Color.parseColor("#000000")
            val backgroundDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f
                setColor(parseColor("#F6F7F7"))
                setStroke(2, parsedColor)
            }
            view.background = backgroundDrawable


        }
    }
    fun createUnSelectedRoundedDrawable(view: View) {
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f
            setColor(parseColor("#F6F7F7"))

        }
        view.background = backgroundDrawable
    }

    fun createStrokeDrawable(view: View) {
        themeData?.mobileConfig?.lightTheme?.otherConfig?.let { otherConfig ->
            // val parsedColor = Color.parseColor(headerConfig.backgroundColor)
            val parsedColor = Color.parseColor(otherConfig.backgroundColor)
            val backgroundDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f
                setStroke(2, parsedColor)
            }
            view.background = backgroundDrawable
            if (view is TextView)
         setTitleColor(view)

        }
    }


    fun setRadioButtonColor(radio: RadioButton) {
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
            try {
                val color = parseColor(headerConfig.backgroundColor)
                radio.buttonTintList = ColorStateList.valueOf(color)
            } catch (e: IllegalArgumentException) {
              e.printStackTrace()
            }
        }
    }

    fun setCheckBoxColor(checkBox: CheckBox) {
        themeData?.mobileConfig?.lightTheme?.headerConfig?.let { headerConfig ->
            try {
                val color = parseColor(headerConfig.backgroundColor)
                checkBox.buttonTintList = ColorStateList.valueOf(color)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

     fun createNormalDrawable(view: View) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 15f
            setStroke(1, Color.BLACK)
            setColor(Color.WHITE)
        }
        view.background = drawable

    }



 fun createErrorDrawable(view: View) {
    val drawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 15f
        setStroke(2, ContextCompat.getColor(view.context, R.color.error_color))
        setColor(Color.WHITE)
    }
    view.background = drawable

}

    fun setDeviceToken(fireBaseToken: String) {
         deviceToken=fireBaseToken
    }

    fun getDeviceToken():String?{
        return deviceToken
    }



}
