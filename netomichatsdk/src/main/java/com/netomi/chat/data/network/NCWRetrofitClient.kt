package com.netomi.chat.data.network

import android.content.Context
import android.os.Build
import android.util.Log
import com.netomi.chat.data.apiconstant.NCWApiConstant.HEADER_BEARER
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.utils.NCWAppSharedPreferences
import com.netomi.chat.utils.NCWAppConstant
import com.netomi.chat.utils.NCWAppUtils.getAppVersion
import com.netomi.chat.utils.NCWAppUtils.getDeviceName
import com.netomi.chat.utils.NCWAppUtils.getOSVersion
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
object NCWRetrofitClient {

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
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(context))  // Adding the interceptor
            .addInterceptor(logging)
            .build()
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
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
            .baseUrl(NCWChatSdk.getBaseUrl())
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
           // val sessionToken = NCWAppSharedPreferences(context).getString(NCWAppConstant.SESSION_TOKEN)

            val headers = mapOf(
                "App-Version" to  getAppVersion(context),
                "Content-Type" to "application/json",
                "Platform" to "android",
                "Device" to getDeviceName(),
                "OS" to getOSVersion()
            )
            val requestBuilder = chain.request().newBuilder()
            for ((key, value) in headers) {
                requestBuilder.header(key, value)
            }
            val request = requestBuilder.build()
            Log.e("AuthInterceptor", "Final Headers: ${request.headers}")
            return chain.proceed(request)
        }
    }




}