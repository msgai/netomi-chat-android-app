package com.netomi.chat.utils

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

    fun setConversationID(id: String) {
        conversationID = id
    }
    fun getConversationID():String?{
        return conversationID
    }


    fun applyTheme(view: View) {
        themeData?.let { theme ->

            when (view) {
                is ConstraintLayout -> {
                    Log.e("ThemeVolor","sasasas"+ theme.theme?.color)
                    theme.theme?.color?.takeIf { color -> color.isNotEmpty() }?.let { color ->
                        view.setBackgroundColor(parseColor(color))
                    }
                }
                is TextView -> {
                    theme.textColor?.let { color ->
                        view.setTextColor(parseColor(color))
                    }
                }

                is Button -> {
                    theme.curatedColors?.let { isCurated ->
                       // if (isCurated) view.setBackgroundColor(parseColor(theme.botResponseBubbleColor))
                    }
                    theme.title?.let { title ->
                        view.text = title
                    }

                }

                is ImageView -> {
                    theme.logoImage?.let { logoUrl ->
                        loadImageIntoView(view, logoUrl)
                    }
                }

                else -> {

                }
            }

            // Apply common properties to any view
           /* theme.borderRadius?.let { radius ->
                setCornerRadius(view, radius.toFloat())
            }*/



           /* theme.backgroundColor?.let { color ->
                view.setBackgroundColor(parseColor(color))
            }*/
        }
    }

    // Utility function to parse color strings (e.g., "#FF5733")
    private fun parseColor(colorString: String): Int {
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
    fun createGradientDrawable(theme: ThemeResponse): GradientDrawable? {
        val direction = GradientDrawable.Orientation.values()
            .getOrElse(theme.theme?.gradientDirection ?: 0) { GradientDrawable.Orientation.LEFT_RIGHT }
        val gradientColors = theme.theme?.gradientColors?.map { Color.parseColor(it) }?.toIntArray()
        return gradientColors?.let { GradientDrawable(direction, it) }
    }




    /**
     * Set dynamic tint color and background for an ImageView.
     *
     * @param imageView The ImageView to apply the tint and background to.
     * @param color The color to set for the tint and background.
     * @param isCircularBackground Boolean flag to determine if the background should be circular.
     */
    fun applyBackgroundAndTint(imageView: ImageView, color: String, isCircularBackground: Boolean) {

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
        textView: TextView,
        color: String?,
        cornerRadii: FloatArray = floatArrayOf(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f)
    ) {
        val parsedColor = Color.parseColor(color ?: "#374E57") // Default color if null
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadii = cornerRadii
            setColor(parsedColor)
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

    // Function to set corner radius if view is compatible
    private fun setCornerRadius(view: View, radius: Float) {
       /* if (view is MaterialCardView) {
            view.radius = radius
        }*/

    }
}
