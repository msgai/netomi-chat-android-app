package com.netomi.chat.data.network

import android.content.Context
import com.netomi.chat.data.apiconstant.NCWApiConstant.HEADER_BEARER
import com.netomi.chat.utils.AppSharedPreferences
import com.netomi.chat.utils.NCWAppConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NCWRetrofitClient {

   // private val BASE_URL = "https://api.example.com/"  // Replace with your base URL

    private val BASE_URL = "https://pickmedevapi.appskeeper.in"  // Replace with your base URL

    // OkHttpClient with custom timeout configuration and added AuthInterceptor
    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(context))  // Adding the interceptor
            .build()
    }

   // Public method to expose the Retrofit instance
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