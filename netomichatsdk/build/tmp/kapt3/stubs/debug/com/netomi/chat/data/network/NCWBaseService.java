package com.netomi.chat.data.network;

/**
 * Abstract base service class that provides a standardized way to handle API calls and errors.
 *
 * This class serves as a reusable component for managing network operations across the NCW SDK.
 * It centralizes error handling, API response parsing, and state management, ensuring consistent
 * behavior across all API interactions. It supports different HTTP status codes and handles network
 * exceptions such as timeouts and connectivity issues.
 *
 * ## Responsibilities:
 * Execute API calls and return results in a standardized **State** object.
 * Handle **HTTP status codes** and map them to appropriate error or success states.
 * Parse API error messages and error codes from the **response body**.
 * Map **network-related exceptions** (e.g., timeout, no internet) to human-readable error messages.
 *
 * ## Usage:
 * This class should be extended by any service class that interacts with the network.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J^\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\"\u0010\f\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00010\rH\u0084@\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010\u001a\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016\u00a8\u0006\u001b"}, d2 = {"Lcom/netomi/chat/data/network/NCWBaseService;", "", "()V", "createCall", "Lcom/netomi/chat/utils/NCWState;", "T", "apiConstant", "", "timeout", "", "showLoader", "", "call", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "Lretrofit2/Response;", "(Ljava/lang/String;JZLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mapApiException", "code", "", "mapToNetworkError", "t", "", "parseError", "errorBody", "Lokhttp3/ResponseBody;", "parseErrorCode", "netomichatsdk_debug"})
public abstract class NCWBaseService {
    
    public NCWBaseService() {
        super();
    }
    
    /**
     * Executes the given API call and returns a **State** object representing the result.
     *
     * This method ensures that API calls are wrapped in a try-catch block to handle exceptions.
     * It manages the loading state, success state, and error state. Additionally, it categorizes
     * HTTP status codes to map appropriate responses.
     *
     * @param T The type of the response body.
     * @param apiConstant A string representing the API name or endpoint for logging purposes.
     * @param timeout The timeout duration for the API call (default: 10 seconds).
     * @param showLoader Boolean flag to indicate whether to show a loader (default: true).
     * @param call A suspend function representing the API call.
     * @return A **State** object representing the result of the API call.
     */
    @org.jetbrains.annotations.Nullable()
    protected final <T extends java.lang.Object>java.lang.Object createCall(@org.jetbrains.annotations.NotNull()
    java.lang.String apiConstant, long timeout, boolean showLoader, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super retrofit2.Response<T>>, ? extends java.lang.Object> call, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.netomi.chat.utils.NCWState<T>> $completion) {
        return null;
    }
    
    /**
     * Parses the error message from the given response body.
     *
     * @param errorBody The response body containing the error message.
     * @return A string representing the error message, or an empty string if parsing fails.
     */
    @org.jetbrains.annotations.NotNull()
    public java.lang.String parseError(@org.jetbrains.annotations.Nullable()
    okhttp3.ResponseBody errorBody) {
        return null;
    }
    
    /**
     * Parses the error code from the given response body.
     *
     * @param errorBody The response body containing the error code.
     * @return An integer representing the error code, or 0 if parsing fails.
     */
    public int parseErrorCode(@org.jetbrains.annotations.Nullable()
    okhttp3.ResponseBody errorBody) {
        return 0;
    }
    
    /**
     * Maps the HTTP status code to a user-friendly error message.
     *
     * @param code The HTTP status code.
     * @return A string representing the mapped error message.
     */
    @org.jetbrains.annotations.NotNull()
    public java.lang.String mapApiException(int code) {
        return null;
    }
    
    /**
     * Maps a network-related exception to a user-friendly error message.
     *
     * This method handles exceptions such as **SocketTimeoutException** and **UnknownHostException**.
     *
     * @param t The throwable representing the exception.
     * @return A string representing the mapped network error message.
     */
    private final java.lang.String mapToNetworkError(java.lang.Throwable t) {
        return null;
    }
}