package com.netomi.sampleapplication.data.network

import android.content.Context
import com.netomi.sampleapplication.data.apiconstant.AppApiConstant.HEADER_BEARER
import com.netomi.chat.utils.AppSharedPreferences
import com.netomi.chat.utils.NCWAppConstant
import okhttp3.Interceptor
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
object AppRetrofitClient {

    private val BASE_URL = "https://chatapps-qa.netomi.com"



    /**
     * Configured OkHttpClient with connection and read timeouts,
     * and a `HttpLoggingInterceptor` for logging network activity.
     *
     * - **Connection Timeout:** 30 seconds.
     * - **Read Timeout:** 30 seconds.
     * - **Logging:** Logs request and response details at the `BODY` level.
     * - **Authorization:** Handles adding an Authorization header through `AuthInterceptor`.
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
     * @param context The context used for retrieving the session token.
     * @return The singleton Retrofit instance.
     */
    fun getInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Interceptor for adding Authorization headers to network requests.
     *
     * This interceptor retrieves the session token from shared preferences
     * and adds it to the Authorization header if available.
     */
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