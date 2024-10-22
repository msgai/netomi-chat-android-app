package com.netomi.chat.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton class responsible for creating and providing a Retrofit instance.
 *
 * This class ensures that only **one Retrofit instance** is used throughout the Chat SDK.
 * It uses the **singleton pattern** to avoid unnecessary overhead of creating multiple
 * Retrofit instances. The Retrofit instance is configured with:
 * A **base URL** for the API endpoints.
 * An **OkHttpClient** for managing network connections and timeouts.
 * A **Gson converter factory** to handle JSON serialization/deserialization.
 * 'HttpLoggingInterceptor`** to log network requests and responses for debugging.
 * The Retrofit instance is configured with:
 *
 * ## Responsibilities:
 * Provide a **single Retrofit instance** for the entire Chat SDK.
 * Log network requests and responses using **HttpLoggingInterceptor**.
 * Configure the OkHttp client with appropriate **connection and read timeouts**.
 * Add support for **JSON serialization** via Gson.
 *
 * ## Usage:
 * The **`NCWRetrofitClient`** is used by repositories like `NCWChatRepository`
 * to interact with API services.
 */
object NCWRetrofitClient {

   // private val BASE_URL = "https://api.example.com/"  // Replace with your base URL

    private val BASE_URL = "https://pickmedevapi.appskeeper.in"  // Replace with your base URL

    // OkHttpClient with custom timeout configuration
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
         .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Lazy initialization of the Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Public method to expose the Retrofit instance
    fun getInstance(): Retrofit = retrofit

}