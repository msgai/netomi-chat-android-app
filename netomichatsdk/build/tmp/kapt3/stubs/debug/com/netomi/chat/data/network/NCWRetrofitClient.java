package com.netomi.chat.data.network;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/netomi/chat/data/network/NCWRetrofitClient;", "", "()V", "BASE_URL", "", "logging", "Lokhttp3/logging/HttpLoggingInterceptor;", "getInstance", "Lretrofit2/Retrofit;", "context", "Landroid/content/Context;", "getOkHttpClient", "Lokhttp3/OkHttpClient;", "AuthInterceptor", "netomichatsdk_debug"})
public final class NCWRetrofitClient {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "https://chatapps-qa.netomi.com";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor logging = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.data.network.NCWRetrofitClient INSTANCE = null;
    
    private NCWRetrofitClient() {
        super();
    }
    
    /**
     * Configured OkHttpClient with connection and read timeouts,
     * and a `HttpLoggingInterceptor` for logging network activity.
     *
     * - **Connection Timeout:** 30 seconds.
     * - **Read Timeout:** 30 seconds.
     * - **Logging:** Logs request and response details at the `BODY` level.
     * - **Authorization:** Handles adding an Authorization header through `AuthInterceptor`.
     */
    private final okhttp3.OkHttpClient getOkHttpClient(android.content.Context context) {
        return null;
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
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit getInstance(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Interceptor for adding Authorization headers to network requests.
     *
     * This interceptor retrieves the session token from shared preferences
     * and adds it to the Authorization header if available.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/netomi/chat/data/network/NCWRetrofitClient$AuthInterceptor;", "Lokhttp3/Interceptor;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "netomichatsdk_debug"})
    public static final class AuthInterceptor implements okhttp3.Interceptor {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Context context = null;
        
        public AuthInterceptor(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
        okhttp3.Interceptor.Chain chain) {
            return null;
        }
    }
}