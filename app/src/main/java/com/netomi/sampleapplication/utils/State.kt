package com.netomi.sampleapplication.utils


sealed class State<T> {
        /**
         * Represents a loading state during an operation.
         *
         * @param apiConstant A string constant representing the API request type.
         * @param enum An optional [LoadingType] enum value indicating the type of loading.
         */
        class Loading<T>(val apiConstant: String,val enum: Enum<LoadingType>?=LoadingType.LOADER) : State<T>()
        /**
         * Represents a successful operation with data.
         *
         * @param data The data returned from the successful operation.
         * @param apiConstant A string constant representing the API request type.
         */
        data class Success<T>(val data: T,val apiConstant:String) : State<T>()
        /**
         * Represents an error that occurred during an operation.
         *
         * @param message A description of the error.
         * @param code An optional error code associated with the error.
         */
        data class Error<T>(val message: String, val code: Int? = null) : State<T>()

        //data class SendMessageError<T>(val message: String, val code: Int? = null,val payload: NCWWebhookPayload) : State<T>()

        data class SendMessageError<T, P>(val message: String, val code: Int? = null, val payload: P) : State<T>()
        /**
         * Represents an unauthorized access state.
         *
         * @param message A description of the unauthorized access.
         */
        data class UnAuthorized<T>(val message: String) : State<T>()
        /**
         * An enum class representing different types of loading.
         */
        enum class LoadingType{PAGINATION,REFRESH,LOADER}

        companion object {

            /**
             * Creates and returns a [State.Loading] instance.
             *
             * @param apiConstant A string constant representing the API request type.
             * @param loadingType An optional [LoadingType] enum value indicating the type of loading.
             * @return A [State.Loading] instance.
             */
            fun <T> loading(apiConstant: String, loadingType: LoadingType?) = Loading<T>(apiConstant,loadingType)

            /**
             * Creates and returns a [State.Success] instance.
             *
             * @param data The data to emit with status.
             * @param apiConstant A string constant representing the API request type.
             * @return A [State.Success] instance.
             */
            fun <T> success(data: T,apiConstant: String) =
                Success(data,apiConstant)

            /**
             * Creates and returns a [State.Error] instance.
             *
             * @param message A description of the error.
             * @param code An optional error code associated with the error.
             * @return A [State.Error] instance.
             */
            fun <T> error(message: String, code: Int? = null) =
                Error<T>(message, code)

            /*  fun <T> sendMessageError(message: String, code: Int? = null,payload: NCWWebhookPayload) =
                  SendMessageError<T>(message, code,payload)*/

            fun <T, P> sendMessageError(message: String, code: Int? = null, payload: P) =
                SendMessageError<T, P>(message, code, payload)

            /**
             * Creates and returns a [State.UnAuthorized] instance.
             *
             * @param message A description of the unauthorized access.
             * @return A [State.UnAuthorized] instance.
             */
            fun <T> unAuthorized(message: String) =
                UnAuthorized<T>(message)

            /**
             * Creates a [State] from a [Resource].
             *
             * @param resource The resource representing the state.
             * @param apiConstant A string constant representing the API request type.
             * @return A corresponding [State] based on the provided resource.
             */
            fun <T> fromResource(resource: Resource<T>,apiConstant: String): State<T> = when (resource) {
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
