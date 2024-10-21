package com.netomi.chat.utils

/**
 * State Management for UI & Data.
 */
sealed class State<T> {
    class Loading<T>(val apiConstant: String,val enum: Enum<LoadingType>?=LoadingType.LOADER) : State<T>()

    data class Success<T>(val data: T) : State<T>()

    data class Error<T>(val message: String, val code: Int? = null) : State<T>()

    data class UnAuthorized<T>(val message: String) : State<T>()
    enum class LoadingType{PAGINATION,REFRESH,LOADER}

    companion object {

        /**
         * Returns [State.Loading] instance.
         */
        fun <T> loading(apiConstant: String, loadingType: LoadingType?) = Loading<T>(apiConstant,loadingType)

        /**
         * Returns [State.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T) =
            Success(data)

        /**
         * Returns [State.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error(message: String, code: Int? = null) =
            Error<T>(message, code)

        fun <T> unAuthorized(message: String) =
            UnAuthorized<T>(message)

        /**
         * Returns [State] from [Resource]
         */
        fun <T> fromResource(resource: Resource<T>,apiConstant: String): State<T> = when (resource) {
            is Resource.Success -> success(resource.data)
            is Resource.Failed -> error(resource.message, -1)
            is Resource.UnAuthorized -> unAuthorized(resource.message)
        }

      /*  fun <T : Any> getBaseResponse(response:State<T>): State<ResponseBody<T>> {
            return ( T as com.example.data.model.BaseResponse)
        }
        */
    }

    sealed class Resource<T> {
        class Success<T>(val data: T) : Resource<T>()
        class Failed<T>(val message: String) : Resource<T>()
        class UnAuthorized<T>(val message: String) : Resource<T>()
    }
}
