package com.netomi.chat.utils

import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.ui.view.FormData
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NCWParsingUtils {

    fun parsePayloadToFormData(payload: String): ArrayList<FormData> {
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
    }


    fun parseDate(dateString: String): Date? {
        return try {
            //SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateString)
            SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }
}