package com.netomi.chat.data.network
import com.netomi.chat.utils.BaseResponse
import com.netomi.chat.utils.State
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseService{

    protected suspend fun <T : Any>
            createCall(
        apiConstant: String,
        timeout: Long = 10000L, // 10 sec
        showLoader: Boolean = true,
        call: suspend () -> Response<T>
    ): State<T> {
        val response: Response<T>
        try {
            if (showLoader) {
                State.loading<T>(apiConstant, State.LoadingType.LOADER)
            }
            response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                return (response.body() as BaseResponse<*>).let {
                    when (it.code) {
                        in 100..199 -> {
                            State.success(response.body()!!,apiConstant)
                        }
                        in 200..299 -> {
                            State.success(response.body()!!,apiConstant)
                        }
                        in 300..399 -> {
                            State.success(response.body()!!,apiConstant)
                        }
                        in 400..499 -> {
                            State.error(it.message, it.code)
                        }
                        in 500..599 -> {
                            State.error(it.message, it.code)
                        }
                        else -> {
                            State.error(it.message, it.code)
                        }
                    }
                }
            } else {
                val errorBody = response.errorBody()
                return if (errorBody != null) {
                    State.error(parseError(errorBody), code = response.code())
                } else {
                    State.error(
                        mapApiException(response.code()),
                        code = response.code()
                    )
                }
            }
        } catch (t: Throwable) {
            return State.error(mapToNetworkError(t))
        }
    }

    open fun parseError(errorBody: ResponseBody?): String {
        return try {
            if (errorBody != null){
                JSONObject(errorBody.string()).getString("message")
            } else ""
        }catch (e:JSONException){
            "Invalid JSON object"
        }
    }

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

    open fun mapApiException(code: Int): String {
        return when (code) {
            HttpURLConnection.HTTP_NOT_FOUND -> "Not Found"
            HttpURLConnection.HTTP_UNAUTHORIZED -> "UnUthorized"
            else -> "Something went wrong"
        }
    }

    private fun mapToNetworkError(t: Throwable): String {
        return when (t) {
            is SocketTimeoutException -> "Connection time out"
            is UnknownHostException -> "No Internet Connection"
            else -> "Something went wrong"
        }
    }

}