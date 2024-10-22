package com.netomi.chat.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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