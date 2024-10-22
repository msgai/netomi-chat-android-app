package com.netomi.chat.data.network

import okhttp3.OkHttpClient
import okhttp3.Response
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

    // OkHttpClient with custom timeout configuration and added AuthInterceptor
    /**
     * Configured OkHttpClient with connection and read timeouts,
     * and a `HttpLoggingInterceptor` for logging network activity.
     *
     * - **Connection Timeout:** 30 seconds.
     * - **Read Timeout:** 30 seconds.
     * - **Logging:** Logs request and response details at the `BODY` level.
     */




    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(context))  // Adding the interceptor
            .build()
    }

    /**
     * Provides the singleton instance of the Retrofit client.
     *
     * This method returns the initialized Retrofit instance, ensuring that the same instance
     * is used throughout the Chat SDK for network operations.
     *
     * @return The singleton Retrofit instance.
     */
    /**
     * Lazy-initialized Retrofit instance.
     *
     * The Retrofit instance is created only when `getInstance()` is called
     * for the first time, ensuring efficient resource usage.
     */
    fun getInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Add Authorization header
    class AuthInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val sessionToken = AppSharedPreferences(context).getString(NCWAppConstant.SESSION_TOKEN)

            // Start building the request
            val requestBuilder = chain.request().newBuilder()
                .header("Content-Type", "application/json")
            if (sessionToken.isNotEmpty()) {
                requestBuilder.header("Authorization", "$HEADER_BEARER $sessionToken")
            }
            // Build the request after all headers are set
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }



}