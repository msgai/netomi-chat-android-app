package com.netomi.sampleapplication.utils

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences (context: Context) {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "APP_SAMPLE",
        Context.MODE_PRIVATE
    )

    fun setString(key: String, value: String?) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearSharedPreference() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

