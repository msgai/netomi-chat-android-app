package com.netomi.chat.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.netomi.chat.R

object NCWDialogUtils {
    fun showCustomDialog(
        context: Context,
        title: String,
        subtitle: String,
        yesText: String,
        noText: String,
        titleColor: Int = Color.BLACK,
        subtitleColor: Int = Color.GRAY,
        backgroundColor: Int = Color.WHITE,
        yesButtonBackgroundColor: Int=Color.RED,
        yesTextColor:Int=Color.BLACK,
        noButtonBackgroundColor: Int=Color.GRAY,
        noTextColor:Int=Color.BLACK,

        onYesClick: () -> Unit,
        onNoClick: () -> Unit
    ) {
       /* val builder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)*/

        val dialogView = Dialog(context)
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogView.setCancelable(true)
        dialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.setContentView(R.layout.dialog_custom)
        // Set the dialog window's width to match the screen width
        val layoutParams = dialogView.window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT // Adjust height as needed
        dialogView.window?.attributes = layoutParams
        // Set up custom view
        val titleTextView = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val subtitleTextView = dialogView.findViewById<TextView>(R.id.dialogSubtitle)
        val yesButton = dialogView.findViewById<Button>(R.id.yesButton)
        val noButton = dialogView.findViewById<Button>(R.id.noButton)
        val dialogLayout = dialogView.findViewById<View>(R.id.dialogLayout)

        // Customize dialog properties
        titleTextView.text = title
        titleTextView.setTextColor(titleColor)
        subtitleTextView.text = subtitle
        subtitleTextView.setTextColor(subtitleColor)
       // dialogLayout.setBackgroundColor(backgroundColor)
        yesButton.text = yesText
        noButton.text = noText


        // Apply button colors dynamically
        yesButton.background = createRoundedDrawable(resolveColor(context, yesButtonBackgroundColor))
        yesButton.setTextColor(resolveColor(context, yesTextColor))
        noButton.background = createRoundedDrawable(resolveColor(context, noButtonBackgroundColor))
        noButton.setTextColor(resolveColor(context, noTextColor))

        // Set click listeners
       // val dialog = builder.setView(dialogView).create()
        yesButton.setOnClickListener {
            onYesClick()
            dialogView.dismiss()
        }
        noButton.setOnClickListener {
            onNoClick()
            dialogView.dismiss()
        }

        // Show the dialog
        dialogView.show()
    }

    /**
     * Creates a rounded drawable with the specified background color.
     */
    private fun createRoundedDrawable(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f
            setColor(color)
        }
    }


    /**
     * Resolves a color value, whether it's a resource ID or a raw color.
     */
    private fun resolveColor(context: Context, color: Int): Int {
        return try {
            // Try resolving as a resource ID
            ContextCompat.getColor(context, color)
        } catch (e: Exception) {
            // If it fails, assume it's a raw color value
            color
        }
    }





}
