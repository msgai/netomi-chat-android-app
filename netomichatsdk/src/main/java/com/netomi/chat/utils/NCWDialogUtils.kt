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
        confirm: String,
        onYesClick: () -> Unit
    ) {

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
        val tvTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.dialogSubtitle)
        val btnConfirm = dialogView.findViewById<TextView>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<TextView>(R.id.btnCancel)

        NCWThemeUtils.setTitleColor(tvTitle)
        NCWThemeUtils.setDescriptionColor(tvSubtitle)
        tvTitle.text = title
        tvSubtitle.text = subtitle
        btnConfirm.text = confirm


        NCWThemeUtils.createRoundedDrawable(btnConfirm)
        NCWThemeUtils.createRoundedDrawableClose(btnCancel)


        btnConfirm.setOnClickListener {
            onYesClick()
            dialogView.dismiss()
        }
        btnCancel.setOnClickListener {
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







}
