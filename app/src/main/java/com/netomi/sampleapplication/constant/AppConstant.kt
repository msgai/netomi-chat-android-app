package com.netomi.sampleapplication.constant

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
object AppConstant {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
    val phonePattern = "^\\+?[0-9]{10,13}\$" // Example for international phone numbers




}