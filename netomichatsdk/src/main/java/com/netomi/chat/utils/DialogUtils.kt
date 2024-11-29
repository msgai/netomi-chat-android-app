package com.netomi.chat.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.netomi.chat.R

object DialogUtils {
    fun showCustomDialog(
        context: Context,
        title: String,
        subtitle: String,
        yesText: String,
        noText: String,
        titleColor: Int = Color.BLACK,
        subtitleColor: Int = Color.GRAY,
        backgroundColor: Int = Color.WHITE,
        onYesClick: () -> Unit,
        onNoClick: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)

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
        dialogLayout.setBackgroundColor(backgroundColor)
        yesButton.text = yesText
        noButton.text = noText

        // Set click listeners
        val dialog = builder.setView(dialogView).create()
        yesButton.setOnClickListener {
            onYesClick()
            dialog.dismiss()
        }
        noButton.setOnClickListener {
            onNoClick()
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }
}
