package com.netomi.chat.utils

import com.netomi.chat.model.messages.NCWCustomAttribute
import com.netomi.chat.survey.NCWSignInUserDetails

fun DeviceInfo.toNCWCustomAttributes(): ArrayList<NCWCustomAttribute> {
    return arrayListOf(
        NCWCustomAttribute(type = "TEXT", name = "deviceId", value = deviceId, scope = "LIFE_TIME"),
        NCWCustomAttribute(
            type = "TEXT",
            name = "manufacturer",
            value = manufacturer,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "modelName",
            value = modelName,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(type = "TEXT", name = "version", value = version, scope = "LIFE_TIME"),
        NCWCustomAttribute(type = "TEXT", name = "os", value = os, scope = "LIFE_TIME"),
        NCWCustomAttribute(
            type = "TEXT",
            name = "isRooted",
            value = isRooted.toString(),
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "trackingSdkVersion",
            value = trackingSdkVersion.toString(),
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "debugMode",
            value = debugMode.toString(),
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(type = "TEXT", name = "locale", value = locale, scope = "LIFE_TIME"),
        NCWCustomAttribute(
            type = "TEXT",
            name = "hostPackageId",
            value = hostPackageId,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "hostVersionNumber",
            value = hostVersionNumber,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "hostBuildNumber",
            value = hostBuildNumber,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "deviceWidth",
            value = deviceWidth.toString(),
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "deviceHeight",
            value = deviceHeight.toString(),
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "scale",
            value = scale.toString(),
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "ip_address",
            value = ipAddress,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "m_device_token",
            value = deviceToken,
            scope = "LIFE_TIME"
        ),
        NCWCustomAttribute(
            type = "TEXT",
            name = "current_sdk",
            value = "mobile",
            scope = "LIFE_TIME"
        ),

        )


}

fun NCWSignInUserDetails.toNCWUserDetailAttribute(): ArrayList<NCWCustomAttribute> {
    return arrayListOf(

        NCWCustomAttribute(type = "TEXT", name = "name", value = name, scope = "LIFE_TIME"),
        NCWCustomAttribute(type = "TEXT", name = "email", value = email, scope = "LIFE_TIME"),
        NCWCustomAttribute(type = "TEXT", name = "userId", value = userId, scope = "LIFE_TIME"),
        NCWCustomAttribute(
            type = "TEXT",
            name = "isAuth",
            value = true.toString(),
            scope = "LIFE_TIME"
        )
    )


}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}