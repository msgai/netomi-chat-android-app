package com.netomi.chat.utils

import android.content.Context
import android.util.Log
import com.netomi.chat.R
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.media_payload.MultiFileModel
import com.netomi.chat.model.messages.Component
import com.netomi.chat.utils.NCWAppUtils.isFileSizeValid
import com.netomi.chat.utils.NCWAppUtils.isFormSizeValid
import java.io.File

object NCWMessageUtils {


    // Helper function to split a message into chunks of 8 words
    fun splitIntoChunks(text: String, wordsPerChunk: Int): List<String> {
        val words = text.split("(?<=\\s)".toRegex()) // Splitting while keeping spaces
        val chunks = mutableListOf<String>()
        val chunkBuilder = StringBuilder()
        var wordCount = 0

        for ((index, word) in words.withIndex()) {
            chunkBuilder.append(word) // Append word with its existing space
            wordCount++

            if (wordCount == wordsPerChunk || index == words.size - 1) {
                chunks.add(chunkBuilder.toString()) // Add chunk without trimming
                chunkBuilder.clear()
                wordCount = 0
            }
        }
        return chunks
    }


    fun mergeChunks(chunkList: List<NCWMessage>): NCWMessage {
        val mergedText = chunkList.joinToString("") { it.message ?: "" }
        val firstChunk = chunkList.first()
        val isReviewEnabled = chunkList.any { it.isReviewEnabled }
        return firstChunk.copy(message = mergedText, isReviewEnabled = isReviewEnabled)
    }

    fun validateFileAttachment(
        formComponent: Component?,
        file: File?,
        supportedExtensions: List<String>?,
        context: Context,
        onValidationFailed: (message: String, description: String?) -> Unit
    ): Boolean {
        Log.e("CheckValidation", "I am hereeee")

        // Normalize the file extension (remove the dot and convert to lowercase)
        val fileExtension = file?.extension?.lowercase()?.removePrefix(".")

        Log.e("validateFileAttachment", "Supported Extensions: $supportedExtensions")
        Log.e("validateFileAttachment", "File Extension: $fileExtension")

        // Check if the file extension is supported
        if (fileExtension == null || !supportedExtensions.orEmpty().contains(fileExtension)) {

            val message = NCWThemeUtils.getThemeData()?.otherlocalized?.unsupported_file_format ?:context.getString(R.string.unsupported_file)
            val messageTemplate = NCWThemeUtils.getThemeData()?.otherlocalized?.selected_files_type_is_not_supported
            val description = if (messageTemplate != null) {
                String.format(messageTemplate, supportedExtensions)
            } else {
                context.getString(R.string.selected_files_type_is_not_supported, supportedExtensions)
            }
            onValidationFailed(message, description)
            return false
        }


        val isSizeValid = file?.let { f ->
            formComponent?.let { component -> isFormSizeValid(component, f) }
        } ?: false

        if (!isSizeValid) {
            val maxUploadSizeAllowedMB = formComponent?.config?.maxUploadSizeAllowed?.let {
                "$it MB"
            } ?: "N/A"
            val messageIssue = context.getString(R.string.upload_file_max_size, maxUploadSizeAllowedMB)

            onValidationFailed(messageIssue, null)
            return false
        }

        return true
    }




    fun validateFileAttachment(
        file: File?,
        supportedExtensions: List<String>,
        maxFileSize: Long?,
        context: Context,
        onValidationFailed: (message: String, description: String?) -> Unit
    ): Boolean {
        Log.e("validateFileAttachment", "Supported Extensions: $supportedExtensions")

        // Normalize the file extension (ensure it starts with a dot)
        val fileExtension = file?.extension?.lowercase()?.let { if (!it.startsWith(".")) ".$it" else it }
        Log.e("validateFileAttachment", "File Extension: $fileExtension")

        // Check if the file extension is supported
        if (fileExtension == null || fileExtension !in supportedExtensions) {
            val message =NCWThemeUtils.getThemeData()?.otherlocalized?.unsupported_file_format ?:context.getString(R.string.unsupported_file)
           // val description = "The selected file type is not supported. Please choose a supported file type such as: $supportedExtensions."

            val messageTemplate = NCWThemeUtils.getThemeData()?.otherlocalized?.selected_files_type_is_not_supported
            val description = if (messageTemplate != null) {
                String.format(messageTemplate, supportedExtensions)
            } else {
                context.getString(R.string.selected_files_type_is_not_supported, supportedExtensions)
            }
            onValidationFailed(message, description)
            return false
        }

        // Validate file size safely
        val isSizeValid = file?.length()?.let { isFileSizeValid(context, it, maxFileSize) } ?: false

        if (!isSizeValid) {
            val maxSize = maxFileSize?.let(NCWAppUtils::formatFileSize) ?: "N/A"
            val messageIssue = context.getString(R.string.upload_file_max_size, maxSize)

            onValidationFailed(messageIssue, null)
            return false
        }

        return true
    }


    fun validateFormAttachment(
        formComponent: Component,
        mMultipleFile: List<MultiFileModel>,
        context: Context,
        onValidationFailed: (message: String) -> Unit
    ): Boolean {
        if (mMultipleFile.isNullOrEmpty()) return true

        val maxUploadSizeAllowedMB = formComponent?.config?.maxUploadSizeAllowed?.let { "$it MB" } ?: "N/A"

        val isValid =
            mMultipleFile.let { NCWAppUtils.validateMultipleFormAttachment(formComponent, it) }

        if (!isValid) {

            val fileSizeMessage = NCWThemeUtils.getThemeData()?.otherlocalized?.upload_files_maximum_size
            val messageIssue = if (fileSizeMessage != null) {
                String.format(fileSizeMessage, maxUploadSizeAllowedMB)
            } else {
                context.getString(R.string.upload_files_max_size, maxUploadSizeAllowedMB)
            }
            onValidationFailed(messageIssue)
            return false
        }

        return true
    }


}