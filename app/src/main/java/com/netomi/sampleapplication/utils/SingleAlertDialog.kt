package com.netomi.sampleapplication.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import com.netomi.chat.R

object SingleAlertDialog {

    fun showSingleButtonDialog(context: Context,
                          title: String,
                          subtitle: String,
                          yesText: String="OK",
                          onYesClick: () -> Unit ){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(com.netomi.sampleapplication.R.layout.alert_dialog)

        val layoutParams = dialog.window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams



        val tvTitle: AppCompatTextView = dialog.findViewById(R.id.tvTitle)

        val tvSubtitle: AppCompatTextView = dialog.findViewById(R.id.tvSubtitle)
        val tvOk: AppCompatTextView = dialog.findViewById(R.id.tvOk)

        tvTitle.text = title
        tvSubtitle.text = subtitle
        tvOk.text=yesText

        tvOk.setOnClickListener {
            onYesClick.invoke() // Trigger the Yes callback
            dialog.dismiss()
        }


        dialog.show()


    }
}