package com.netomi.chat.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.netomi.chat.model.messages.Component
import com.netomi.chat.utils.NCWAppConstant.TIME_AM_PM
import com.netomi.chat.utils.NCWAppConstant.TYPE_FILE
import com.netomi.chat.utils.NCWAppConstant.TYPE_IMAGE
import com.netomi.chat.utils.NCWAppConstant.TYPE_VIDEO
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object NCWAppUtils {

    private val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    private val urlPattern = android.util.Patterns.WEB_URL

    fun isValidEmail(input: String): Boolean {
        return emailPattern.matcher(input).matches()
    }

    fun isValidUrl(input: String): Boolean {
        return urlPattern.matcher(input).matches()
    }
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
        return dateFormat.format(date).uppercase()
    }


    fun setHtmText(input: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        } else {
            Html.fromHtml(input).toString().trim()
        }
    }

    fun setHtmlText(textView: TextView, input: String) {
        val formattedText = Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
        textView.text = formattedText.trim()

        textView.movementMethod= LinkMovementMethod.getInstance()
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
        imageClick: () -> Unit,
        videoClick: () -> Unit
    ) {
        val items = arrayOf("Capture Image",  "Capture Video")
        MaterialAlertDialogBuilder(context)
            .setItems(items) { dialog, which ->
                when (which) {
                    0 -> {
                        imageClick.invoke()
                        dialog.dismiss()
                    }
                    1 -> {
                        videoClick.invoke()
                        dialog.dismiss()
                    }
                }
            }.show()
    }

  /*  fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
*/
  fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    fun getFileContentType(file: File): String {
        return when (file.extension.lowercase()) {
            "png" -> "image/png"
            "jpg", "jpeg" -> "image/jpeg"
            "pdf" -> "application/pdf"
            "mp4" -> "video/mp4"
            "mov" -> "video/quicktime"
            "avi" -> "video/x-msvideo"
            "mkv" -> "video/x-matroska"
            "webm" -> "video/webm"
            "mp3" -> "audio/mpeg"
            "wav" -> "audio/wav"
            "aac" -> "audio/aac"
            "ogg" -> "audio/ogg"
            "doc" -> "application/msword"
            else -> "application/octet-stream"
        }
    }
    fun getTypeFromContent(type: String): String {
        return when (type) {
            "image/png", "image/jpeg" -> TYPE_IMAGE
            "video/quicktime",  "video/mp4" -> TYPE_VIDEO
            else -> TYPE_FILE
        }
    }
    fun formatFileSize(fileSizeInBytes: Long): String {
        return when {
            fileSizeInBytes >= 1_048_576 -> {
                // Convert to MB
                val sizeInMb = fileSizeInBytes / 1_048_576.0
                String.format("%.2f MB", sizeInMb)
            }
            fileSizeInBytes >= 1_024 -> {
                // Convert to KB
                val sizeInKb = fileSizeInBytes / 1_024.0
                String.format("%.2f KB", sizeInKb)
            }
            else -> {
                // Show bytes
                "$fileSizeInBytes Bytes"
            }
        }
    }


    fun createRequestBody(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value) // Create RequestBody for other fields
    }

    fun getPrefix(mediaPath: String): String {
        val parts = mediaPath.split("/") // Split the string by "/"
        return parts.take(parts.size - 1).joinToString("/") // Join all parts except the last one
    }


    fun getMimeType(context: Context, uri: Uri): String? {

        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        // Since `photoUri` is created from `createImageFile`, it already points to the file
        return if ("file" == uri.scheme) {
            File(uri.path!!)
        } else {
            // For safety, copy content to a cache file if the URI scheme isn't "file"
            val tempFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        }
    }

    fun isFileSizeValid(context: Context, fileSizeInBytes: Long?, maxFileSizeInBytes: Long?): Boolean {
        //val fileSizeInMB = fileSizeInBytes?.toDouble()?.div(1024 * 1024) ?: 0.0
        val maxFileSizeInMB = maxFileSizeInBytes?.toDouble()?.div(1024 * 1024) ?: 0.0

        if (fileSizeInBytes != null && maxFileSizeInBytes != null && fileSizeInBytes > maxFileSizeInBytes) {
            /*showToast(
                context = context,
                message = "File size should not be greater than ${String.format("%.2f", maxFileSizeInMB)} MB"
            )*/

            return false
        }
        return true
    }

    fun isFormSizeValid(formComponent: Component, fileSend: File): Boolean {
        val maxUploadSizeAllowedMB = formComponent?.config?.maxUploadSizeAllowed ?: 0 // Default to 0 if null, unit is MB
        val previousFileInMB = formComponent?.fileUpload
            ?.filter { it.fileSize != null } // Ensure fileSize is not null
            ?.sumOf {
                it.fileSize!!.toDouble() / (1024 * 1024) // Convert bytes to MB
            }
            ?: 0.0
        val currentFileSizeMB = fileSend?.length()?.toDouble()?.div(1024 * 1024) ?: 0.0
        val allSize = previousFileInMB + currentFileSizeMB

        if (allSize > maxUploadSizeAllowedMB) {

            return false
        }
        return true
    }

    fun getDomainOutOfURL(url: String?): String? {
        if (url.isNullOrBlank()) return null

        return try {
            val urlObj = URL(url)
            var domain = urlObj.host.removePrefix("www.")
            val lastDotIndex = domain.lastIndexOf(".")
            if (lastDotIndex > 0) {
                domain = domain.substring(0, lastDotIndex)
            }

            val path = urlObj.path

            val pageName = when {
                path.isNotEmpty() -> {
                    // Split by "/" and take the last part, then remove any trailing slashes
                    val pathSegments = path.trim('/').split("/")
                    val lastSegment = pathSegments.lastOrNull()

                    // Further process if there is a query or fragment
                    lastSegment?.split("?")?.firstOrNull()?.split("#")?.firstOrNull().orEmpty()
                }
                else -> ""
            }
            if (pageName.isNotEmpty()) "$domain-$pageName" else domain
        } catch (e: MalformedURLException) {
            null
        }
    }

}