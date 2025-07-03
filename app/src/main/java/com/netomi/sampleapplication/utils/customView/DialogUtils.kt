package com.netomi.sampleapplication.utils.customView

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.netomi.sampleapplication.R

class DialogUtils(context: Context) {
    private val builder = AlertDialog.Builder(context)

    lateinit var dialog: Dialog
    fun showCustomDialog(dialog: Dialog, dialogModel: DialogModel) {
        this.dialog =dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvTitle: AppCompatTextView = dialog.findViewById(R.id.tvTitle)
        val tvDesc: AppCompatTextView = dialog.findViewById(R.id.tvDesc)
        val yesBtn: AppCompatTextView = dialog.findViewById(R.id.btnAction)
        val noBtn: AppCompatTextView = dialog.findViewById(R.id.btnCancel)


        tvTitle.text = dialogModel.title

        tvDesc.text = dialogModel.desc

        if (dialogModel.btnPositive.isNullOrEmpty()){
            yesBtn.visibility = View.GONE
        }else{
            yesBtn.text = dialogModel.btnPositive
        }

        if (dialogModel.btnNegative.isNullOrEmpty()){
            noBtn.visibility = View.GONE
        }else{
            noBtn.text = dialogModel.btnNegative
        }

        yesBtn.setOnClickListener {
            dialogModel.listener?.onDialogAction(dialogModel.from)
            dialog.dismiss()
        }

        noBtn.setOnClickListener {
            dialogModel.listener?.onDialogCancel(dialogModel.from)
            dialog.dismiss()
        }

        dialog.show()

    }

    fun dismissDialog() {

        dialog.dismiss()
    }


    interface DialogListener {
        fun onDialogCancel(action: Int)
        fun onDialogAction(action: Int)
    }

    data class DialogModel(
        var from: Int = 0,
        var title: String? = null,
        var desc: String? = null,
        var btnPositive: String? = null,
        var btnNegative: String? = null,
        var btnCentre: String? = "",
        var img: Drawable? = null,
        var isCancelable: Boolean = false,
        var listener: DialogListener? = null,
        var data: Any? = null
    )
}










