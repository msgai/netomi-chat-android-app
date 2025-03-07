package com.netomi.chat.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.netomi.chat.model.media_payload.MultiFileModel
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
import java.net.InetAddress
import java.net.MalformedURLException
import java.net.NetworkInterface
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Enumeration
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

    fun formatTimestampToTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat(TIME_AM_PM, Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date).uppercase()
    }

    fun setHtmlText(textView: TextView, input: String) {
        val htmlText = input.replace("\\\"", "'")
      //  val formattedHtml = htmlText.replace("<a class=", "&emsp;<a class=")
       // val formattedText = HtmlCompat.fromHtml(formattedHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val formattedText = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
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

  fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    fun getFileContentType(file: File): String {
        return when (file.extension.lowercase()) {
            "png" -> "image/png"
            "jpg", "jpeg" -> "image/jpeg"
            "gif" -> "image/gif"
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
            "text/plain","txt" -> "text/plain"
            else -> "application/octet-stream"
        }
    }
    fun getTypeFromContent(type: String): String {
        return when (type) {
            "image/gif","image/png", "image/jpeg" -> TYPE_IMAGE
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
        // val maxFileSizeInMB = maxFileSizeInBytes?.toDouble()?.div(1024 * 1024) ?: 0.0
        if (fileSizeInBytes != null && maxFileSizeInBytes != null && fileSizeInBytes > maxFileSizeInBytes) {
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
    fun validateMultipleFormAttachment(formComponent: Component, filesToSend: List<MultiFileModel>): Boolean {
        val maxUploadSizeAllowedMB = formComponent?.config?.maxUploadSizeAllowed ?: 0 // Default to 0 if null, unit is MB

        // Sum up the sizes of previously uploaded files (in MB)
        val previousFileInMB = formComponent?.fileUpload
            ?.filter { it.fileSize != null }
            ?.sumOf { it.fileSize!!.toDouble() / (1024 * 1024) }
            ?: 0.0

        // Sum up the sizes of the new files to send (in MB)
        val currentFilesSizeMB = filesToSend.sumOf { it.file.length().toDouble() / (1024 * 1024) }

        val allSize = previousFileInMB + currentFilesSizeMB

        return allSize <= maxUploadSizeAllowedMB
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

     fun parseIdleTimeFromExpression(expression: String): Long {
        // Extract the idleTime value from the expression using regex
        val regex = Regex("idleTime == (\\d+)")
        val matchResult = regex.find(expression)
        return matchResult?.groupValues?.get(1)?.toLongOrNull() ?: 0L
    }

    fun getIPAddress(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return ""
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return ""

            return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            ) {
                getIPAddress(true)
            } else {
                ""
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null) {
                return getIPAddress(true)
            }


        }
        return ""
    }

    private fun getIPAddress(useIPv4: Boolean): String {
        try {
            val interfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface: NetworkInterface = interfaces.nextElement()
                val addresses: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val inetAddress: InetAddress = addresses.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val ip = inetAddress.hostAddress ?: continue
                        val isIPv4 = ip.indexOf(':') < 0

                        if (useIPv4) {
                            if (isIPv4) return ip
                        } else {
                            if (!isIPv4) {
                                val index = ip.indexOf('%')
                                return if (index < 0) ip.uppercase(Locale.getDefault()) else ip.substring(0, index)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

     fun getDeviceName(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

     fun getOSVersion(): String {
        return Build.VERSION.RELEASE
    }

     fun getAppVersion(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "1.0"
        } catch (e: Exception) {
            "1.0"
        }
    }

    fun areAWSCredentialsExpired(expireTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val expireThreshold = 1 * 60 * 1000L // Ensure it’s a Long

        val thresholdTime = currentTime + expireThreshold

        Log.e("credentials + expireThreshold", "currentTime + expireThreshold: $thresholdTime")
        Log.e("credentials expireTime", "expireTime: $expireTime")

        return expireTime <= thresholdTime
    }



}