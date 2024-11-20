package com.netomi.chat.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Html
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.netomi.chat.utils.NCWAppConstant.TIME_AM_PM
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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

    fun formatTimestampToTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat(TIME_AM_PM, Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
    fun setHtmText(html:String,txtView:TextView){

        txtView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(html)
        }
    }
    fun setPlainText(html: String, txtView: TextView) {
        val plainText = html
            .replace("<p>", "")
            .replace("</p>", "")
            .trim()

        txtView.text = plainText
    }



    /**
     * function to check network availability
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    fun showToast(context: Context,message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
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