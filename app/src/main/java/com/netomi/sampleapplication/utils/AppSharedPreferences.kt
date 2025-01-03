package com.netomi.sampleapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse

class AppSharedPreferences (context: Context) {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "APP_SAMPLE",
        Context.MODE_PRIVATE
    )

    fun saveBotsListingResponse(key: String, botResponse: BotListingResponse) {
        with(sharedPreferences.edit()) {
            val gson = Gson()
            val json = gson.toJson(botResponse)
            putString(key, json)
            apply()
        }
    }

    fun getBotsResponseFromSharedPreferences(key: String): BotListingResponse? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null) // Retrieve JSON string
        return if (json != null) {
            gson.fromJson(json, BotListingResponse::class.java) // Convert JSON string to BotResponse object
        } else {
            null
        }
    }

    // Save the selected bot to SharedPreferences
    fun saveSelectedBot(bot: Bot) {
        val gson = Gson()
        val botJson = gson.toJson(bot)  // Convert Bot object to JSON string
        sharedPreferences.edit().putString("selectedBot", botJson).apply()
    }

    // Get the selected bot from SharedPreferences
    fun getSelectedBot(): Bot? {
        val gson = Gson()
        val botJson = sharedPreferences.getString("selectedBot", null)
        return if (botJson != null) {
            gson.fromJson(botJson, Bot::class.java)  // Convert JSON string back to Bot object
        } else {
            null  // Return null if no bot is saved
        }
    }

    fun <T> put(key: String, `object`: T) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = sharedPreferences.getString(key, "")
        return GsonBuilder().create().fromJson(value, T::class.java)
    }


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

