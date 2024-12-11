package com.netomi.chat.utils

import com.netomi.chat.model.messages.NCWWebhookPayload

/**
 * A sealed class representing the state of a UI or data operation.
 * It encapsulates different states that can occur during an operation,
 * including loading, success, error, and unauthorized access.
 *
 * @param T The type of data that the state may hold.
 */
sealed class NCWState<T> {
    /**
     * Represents a loading state during an operation.
     *
     * @param apiConstant A string constant representing the API request type.
     * @param enum An optional [LoadingType] enum value indicating the type of loading.
     */
    class Loading<T>(val apiConstant: String,val enum: Enum<LoadingType>?=LoadingType.LOADER) : NCWState<T>()
    /**
     * Represents a successful operation with data.
     *
     * @param data The data returned from the successful operation.
     * @param apiConstant A string constant representing the API request type.
     */
    data class Success<T>(val data: T,val apiConstant:String) : NCWState<T>()
    /**
     * Represents an error that occurred during an operation.
     *
     * @param message A description of the error.
     * @param code An optional error code associated with the error.
     */
    data class Error<T>(val message: String, val code: Int? = null) : NCWState<T>()

    //data class SendMessageError<T>(val message: String, val code: Int? = null,val payload: NCWWebhookPayload) : NCWState<T>()

    data class SendMessageError<T, P>(val message: String, val code: Int? = null, val payload: P) : NCWState<T>()
    /**
     * Represents an unauthorized access state.
     *
     * @param message A description of the unauthorized access.
     */
    data class UnAuthorized<T>(val message: String) : NCWState<T>()
    /**
     * An enum class representing different types of loading.
     */
    enum class LoadingType{PAGINATION,REFRESH,LOADER}

    companion object {

        /**
         * Creates and returns a [NCWState.Loading] instance.
         *
         * @param apiConstant A string constant representing the API request type.
         * @param loadingType An optional [LoadingType] enum value indicating the type of loading.
         * @return A [NCWState.Loading] instance.
         */
        fun <T> loading(apiConstant: String, loadingType: LoadingType?) = Loading<T>(apiConstant,loadingType)

        /**
         * Creates and returns a [NCWState.Success] instance.
         *
         * @param data The data to emit with status.
         * @param apiConstant A string constant representing the API request type.
         * @return A [NCWState.Success] instance.
         */
        fun <T> success(data: T,apiConstant: String) =
            Success(data,apiConstant)

        /**
         * Creates and returns a [NCWState.Error] instance.
         *
         * @param message A description of the error.
         * @param code An optional error code associated with the error.
         * @return A [NCWState.Error] instance.
         */
        fun <T> error(message: String, code: Int? = null) =
            Error<T>(message, code)

      /*  fun <T> sendMessageError(message: String, code: Int? = null,payload: NCWWebhookPayload) =
            SendMessageError<T>(message, code,payload)*/

        fun <T, P> sendMessageError(message: String, code: Int? = null, payload: P) =
            SendMessageError<T, P>(message, code, payload)

        /**
         * Creates and returns a [NCWState.UnAuthorized] instance.
         *
         * @param message A description of the unauthorized access.
         * @return A [NCWState.UnAuthorized] instance.
         */
        fun <T> unAuthorized(message: String) =
            UnAuthorized<T>(message)

        /**
         * Creates a [NCWState] from a [Resource].
         *
         * @param resource The resource representing the state.
         * @param apiConstant A string constant representing the API request type.
         * @return A corresponding [NCWState] based on the provided resource.
         */
        fun <T> fromResource(resource: Resource<T>,apiConstant: String): NCWState<T> = when (resource) {
            is Resource.Success -> success(resource.data,apiConstant)
            is Resource.Failed -> error(resource.message, -1)
            is Resource.UnAuthorized -> unAuthorized(resource.message)
        }
    }
    /**
     * A sealed class representing the result of a resource operation.
     * It can represent success, failure, or unauthorized access.
     *
     * @param T The type of data that the resource may hold.
     */
    sealed class Resource<T> {
        /**
         * Represents a successful resource operation.
         *
         * @param data The data returned from the successful resource operation.
         */
        class Success<T>(val data: T) : Resource<T>()
        /**
         * Represents a failed resource operation.
         *
         * @param message A description of the failure.
         */
        class Failed<T>(val message: String) : Resource<T>()
        /**
         * Represents an unauthorized access resource operation.
         *
         * @param message A description of the unauthorized access.
         */
        class UnAuthorized<T>(val message: String) : Resource<T>()
    }
}
