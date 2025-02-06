package com.netomi.chat.utils

import android.util.Log
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.ui.view.FormData
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NCWParsingUtils {

   /* fun parsePayloadToFormData(payload: String): ArrayList<FormData>? {

        if (!payload.contains("event://;LEARN_ATTRIBUTE_EVENT;")) {
            return null // Return null if the substring is not found
        }
        val cleanedPayload = payload.substringAfter("event://;LEARN_ATTRIBUTE_EVENT;")
        val keyValuePairs = cleanedPayload.split("&")

        val formDataList = ArrayList<FormData>()

        keyValuePairs.forEach { pair ->
            val (key, value) = pair.split("::value=").let {
                it[0] to it.getOrNull(1)
            }

            val formData = FormData()

            when (key) {
                "DEFAULT_OUTPUT_KEY_INPUT" -> {
                    formData.textInput = value
                }
                "DEFAULT_OUTPUT_KEY_TEXTAREA" -> {
                    formData.textAreaInput = value
                }
                "DEFAULT_OUTPUT_KEY_SELECT" -> {
                    formData.selectedRadio = value
                }
                "DEFAULT_OUTPUT_KEY_PILL" -> {
                    val fileUrls = value?.split(",")?.map { FileUploadData(fileUrl = it) } ?: emptyList()

                    formData.fileUpload = ArrayList(fileUrls)
                }
            }
            formDataList.add(formData)
        }

        return formDataList
    }*/


    fun parseDate(dateString: String): Date? {
        return try {
            //SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateString)
            SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }



    fun getFileSizeFromUrl(fileUrl: String, callback: (Long?) -> Unit) {
        Thread {
            var connection: HttpURLConnection? = null
            try {
                val url = URL(fileUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "HEAD" // Set request method to HEAD
                connection.connect()

                // Get the content length from the headers
                val fileSize = connection.contentLengthLong
                callback(fileSize) // Return the file size through the callback
            } catch (e: Exception) {
                e.printStackTrace()
                callback(null) // Return null in case of an error
            } finally {
                connection?.disconnect()
            }
        }.start()
    }
}