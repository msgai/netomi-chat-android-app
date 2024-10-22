package com.netomi.chat.model
import com.google.gson.annotations.SerializedName

data class AppConfigurationResponseModel(
        @SerializedName("config")
        val config: Config,
        @SerializedName("tutorials")
        val tutorials: List<Tutorial>
    ) {
         data class Config(
            @SerializedName("localisation")
            val localisation: String,
            @SerializedName("otpAttempts")
            val otpAttempts: Int,
            @SerializedName("otpExpiryTime")
            val otpExpiryTime: Int,
            @SerializedName("otpTimer")
            val otpTimer: Int,
            @SerializedName("socialLogin")
            val socialLogin: List<String>,
            @SerializedName("supportEmail")
            val supportEmail: SupportEmail,
            @SerializedName("supportPhone")
            val supportPhone: SupportPhone,
            @SerializedName("movement")
            val movement: Movement,
            @SerializedName("theme")
            val theme: String
        ) {
             data class SupportEmail(
                @SerializedName("enabled")
                val enabled: Boolean,
                @SerializedName("value")
                val value: String
            )

             data class SupportPhone(
                @SerializedName("enabled")
                val enabled: Boolean,
                @SerializedName("value")
                val value: String
            )
             data class Movement(
                 @SerializedName("driver")
                 val driver: Int,
                 @SerializedName("customer")
                 val customer: Int
             )
        }

         data class Tutorial(
            @SerializedName("description")
            val description: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("userType")
            val userType: Int)

}