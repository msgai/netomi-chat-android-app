package com.netomi.chat.utils

import android.content.Context
import android.os.Build
import com.netomi.chat.BuildConfig
import java.util.Locale

object DeviceInfoUtil {
    fun getDeviceInfo(context: Context): DeviceInfo {
        val displayMetrics = context.resources.displayMetrics

        return DeviceInfo(
            deviceId = Build.ID,
            manufacturer = Build.MANUFACTURER,
            modelName = Build.MODEL,
            version = Build.VERSION.RELEASE,
            os = "Android",
            isRooted = NCWRootChecker.isRooted(),
            trackingSdkVersion = Build.VERSION.SDK_INT,
            debugMode = BuildConfig.DEBUG,
            locale = Locale.getDefault().language,
            hostPackageId = context.packageName,
            hostVersionNumber = context.packageManager.getPackageInfo(context.packageName, 0).versionName,
            hostBuildNumber = context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toString(),
            deviceWidth = displayMetrics.widthPixels,
            deviceHeight = displayMetrics.heightPixels,
            scale = displayMetrics.density,
            ipAddress=NCWAppUtils.getIPAddress(context)
        )
    }

   /* private fun isDeviceRooted(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        return paths.any { path -> File(path).exists() }
    }*/
}

data class DeviceInfo(
    val deviceId: String,
    val manufacturer: String,
    val modelName: String,
    val version: String,
    val os: String,
    val isRooted: Boolean,
    val trackingSdkVersion: Int,
    val debugMode: Boolean,
    val locale: String,
    val hostPackageId: String,
    val hostVersionNumber: String,
    val hostBuildNumber: String,
    val deviceWidth: Int,
    val deviceHeight: Int,
    val scale: Float,
    val ipAddress :String
)
