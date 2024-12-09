package com.netomi.chat.data.network

import com.netomi.chat.utils.NCWBaseResponse
import com.netomi.chat.utils.NCWState
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
 *
 */
abstract class NCWBaseService{
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
    protected suspend fun <T : Any>
            createCall(
        apiConstant: String,
        timeout: Long = 10000L, // 10 sec
        showLoader: Boolean = true,
        call: suspend () -> Response<T>
    ): NCWState<T> {
        val response: Response<T>
        try {
            if (showLoader) {
                NCWState.loading<T>(apiConstant, NCWState.LoadingType.LOADER)
            }
            response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                return (response.body() as NCWBaseResponse<*>).let {
                    when (it.code) {
                        in 100..199 -> {
                            NCWState.success(response.body()!!,apiConstant)
                        }
                        in 200..299 -> {
                            NCWState.success(response.body()!!,apiConstant)
                        }
                        in 300..399 -> {
                            NCWState.success(response.body()!!,apiConstant)
                        }
                        in 400..499 -> {
                            NCWState.error(it.message, it.code)
                        }
                        in 500..599 -> {
                            NCWState.error(it.message, it.code)
                        }
                        else -> {
                            NCWState.error(it.message, it.code)
                        }
                    }
                }
            } else {
                val errorBody = response.errorBody()
                return if (errorBody != null) {
                    NCWState.error(parseError(errorBody), code = response.code())
                } else {
                    NCWState.error(
                        mapApiException(response.code()),
                        code = response.code()
                    )
                }
            }
        } catch (t: Throwable) {
            return NCWState.error(mapToNetworkError(t))
        }
    }

    /**
     * Parses the error message from the given response body.
     *
     * @param errorBody The response body containing the error message.
     * @return A string representing the error message, or an empty string if parsing fails.

     */
    open fun parseError(errorBody: ResponseBody?): String {
        return try {
            if (errorBody != null){
                JSONObject(errorBody.string()).getString("message")
            } else ""
        }catch (e:JSONException){
            "Invalid JSON object"
        }
    }
    /**
     * Parses the error code from the given response body.
     *
     * @param errorBody The response body containing the error code.
     * @return An integer representing the error code, or 0 if parsing fails.
     */
    open fun parseErrorCode(errorBody: ResponseBody?): Int {
        return try {
            if (errorBody != null){
                JSONObject(errorBody.string()).getInt("errorCode")
            } else {
                0
            }
        }catch (e:JSONException){
            0
        }
    }
    /**
     * Maps the HTTP status code to a user-friendly error message.
     *
     * @param code The HTTP status code.
     * @return A string representing the mapped error message.
     */
    open fun mapApiException(code: Int): String {
        return when (code) {
            HttpURLConnection.HTTP_NOT_FOUND -> "Not Found"
            HttpURLConnection.HTTP_UNAUTHORIZED -> "UnUthorized"
            else -> "Something went wrong"
        }
    }
    /**
     * Maps a network-related exception to a user-friendly error message.
     *
     * This method handles exceptions such as **SocketTimeoutException** and **UnknownHostException**.
     *
     * @param t The throwable representing the exception.
     * @return A string representing the mapped network error message.
     */
    private fun mapToNetworkError(t: Throwable): String {
        return when (t) {
            is SocketTimeoutException -> "Connection time out"
            is UnknownHostException -> "No Internet Connection"
            else -> "Something went wrong"
        }
    }

}
