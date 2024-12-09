package com.netomi.chat.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.widget.AppCompatTextView
import com.netomi.chat.R

object NCWSingleAlertDialog {

    fun showSingleButtonDialog(context: Context,
                          title: String,
                          subtitle: String,
                          yesText: String="OK",
                          onYesClick: () -> Unit ){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.alert_dialog_sdk)
        val tvTitle: AppCompatTextView = dialog.findViewById(R.id.tvTitle)

        val tvSubtitle: AppCompatTextView = dialog.findViewById(R.id.tvSubtitle)
        val tvOk: AppCompatTextView = dialog.findViewById(R.id.tvOk)

        tvTitle.text = title
        tvSubtitle.text = subtitle
        tvOk.text=yesText

        NCWThemeUtils.createRoundedDrawable(tvOk)

        tvOk.setOnClickListener {
            onYesClick.invoke() // Trigger the Yes callback
            dialog.dismiss()
        }


        dialog.show()


    }
}