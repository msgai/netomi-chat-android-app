package com.netomi.chat.utils

import android.content.Context
import android.content.SharedPreferences

class NCWAppSharedPreferences (context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "NETOMI_SDK",
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
    fun getString(key: String,defValue:String?): String? {
        return sharedPreferences.getString(key, defValue)
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


    fun setInt(key: String, value: Int)
    {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }

    }


    fun getInt(key: String):Int{
        return sharedPreferences.getInt(key, -1)
    }

    fun clearSharedPreference() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

