package com.netomi.chat.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object NCWAppUtils {
    fun hideKeyboard(context: Activity) {
        val inputManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            context.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun showKeyboard(context: Activity) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(context.currentFocus, InputMethodManager.SHOW_IMPLICIT)
    }



    fun showMediaOptionDialog(
        context: Context,
        cameraClickCallback: () -> Unit,
        galleryClickCallback: () -> Unit
    ) {
        val items = arrayOf("Camera", "Gallery")
        MaterialAlertDialogBuilder(context)
            .setItems(items) { dialog, which ->
                when (which) {
                    0 -> {
                        cameraClickCallback.invoke()
                        dialog.dismiss()
                    }
                    1 -> {
                        galleryClickCallback.invoke()
                        dialog.dismiss()
                    }
                }
            }.show()
    }



}