package com.netomi.chat.data.apiconstant

/**
 * Object class that holds constant values used in API requests.
 *
 * This class provides a centralized location for defining API-related constants, such as
 * user-type values. These constants ensure consistency across the codebase
 * and reduce the chances of hardcoding strings multiple times.
 *
 * ## Responsibilities:
 * - Define constant values used for API headers (e.g., Authorization, Bearer tokens).
 * - Provide values for user-related API headers, such as user type and tutorial status.
 * - Ensure that constants are easily accessible throughout the NCW SDK.
 *
 * ## Usage:
 * The **`NCWApiConstant`** object is accessed directly since it is declared as an `object`.
 */
object NCWApiConstant {
    const val HEADER_BEARER = "Bearer"
    const val HEADER_AUTH = "Authorization"
    const val X_BOT_REF_ID = "x-bot-ref-id"
    const val X_AUTH_ENABLE= "x-auth-enabled"
    const val X_AUTH_TOKEN= " x-auth-token"
    const val X_CONVERSATION_ID= "x-conversation-id"
    const val CODE = "code"


    const val BOT_REF_ID = "botRefId"



}