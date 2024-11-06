package com.netomi.chat.utils

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.netomi.chat.model.theme.ThemeResponse

object ThemeUtils {

    fun applyTheme(themeResponse: ThemeResponse?, view: View) {
        themeResponse?.let { theme ->

            when (view) {
                is ConstraintLayout -> {
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
                    // Add more Button-specific theme settings here
                }

                is ImageView -> {
                    theme.logoImage?.let { logoUrl ->
                        loadImageIntoView(view, logoUrl)
                    }
                    // Add more ImageView-specific theme settings here
                }

                // Handle other view types as needed
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
