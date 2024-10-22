package com.netomi.chat.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * A generic data class that represents the base structure of an API response.
 *
 * This class wraps the common fields returned by the NCW server, such as status codes, error codes,
 * and messages. It also holds the **result data** of type `T`, which makes the class reusable
 * for various types of API responses. Using this class ensures consistency in parsing and handling
 * responses across the Chat SDK.
 *
 * @param T The type of the data object contained in the response.
 * @property code The **HTTP status code** returned by the server.
 * @property errorCode The specific **error code** provided by the API in case of a failure.
 * @property message A **message** from the NCW server, typically indicating success or error details.
 * @property data The **result object** of type `T` containing the API's response data.
 */
data class NCWBaseResponse<T>(
    @SerializedName("statusCode")
    @Expose
    var code: Int,
    @SerializedName("errorCode")
    @Expose
    var errorCode: Int,
    @SerializedName("message")
    @Expose
    var message: String,
    @SerializedName("result")
    @Expose
    var data: T
)