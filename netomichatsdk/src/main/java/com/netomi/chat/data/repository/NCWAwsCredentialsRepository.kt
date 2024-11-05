package com.netomi.chat.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials
import com.netomi.chat.utils.NCWAppConstant.AWS_ACCESS_KEY
import com.netomi.chat.utils.NCWAppConstant.AWS_IOT_ENDPOINT
import com.netomi.chat.utils.NCWAppConstant.AWS_SECRET_KEY
import com.netomi.chat.utils.NCWAppConstant.AWS_SESSION_TOKEN
import com.netomi.chat.utils.NCWAppConstant.SECURE_CREDENTIAL

class NCWAwsCredentialsRepository private constructor(context: Context) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        SECURE_CREDENTIAL,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Save credentials to secure storage
    fun saveAWSCredentials(credentials: NCWAwsCredentials) {
        sharedPreferences.edit()
            .putString(AWS_ACCESS_KEY, credentials.accessKey)
            .putString(AWS_SECRET_KEY, credentials.secretKey)
            .putString(AWS_SESSION_TOKEN, credentials.sessionToken)
            .putString(AWS_IOT_ENDPOINT, credentials.iotEndpoint)
            .apply()
    }

    // Fetch credentials (assuming they are already stored)
    fun getAWSCredentials(): NCWAwsCredentials {
        val accessKey = sharedPreferences.getString(AWS_ACCESS_KEY, null) ?: ""
        val secretKey = sharedPreferences.getString(AWS_SECRET_KEY, null) ?: ""
        val sessionToken = sharedPreferences.getString(AWS_SESSION_TOKEN, null) ?: ""
        val iotEndpoint = sharedPreferences.getString(AWS_IOT_ENDPOINT, null) ?: ""

        return NCWAwsCredentials(accessKey, secretKey, sessionToken, iotEndpoint)
    }

    fun clearCredentials() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        @Volatile
        private var INSTANCE: NCWAwsCredentialsRepository? = null

        fun getInstance(context: Context): NCWAwsCredentialsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NCWAwsCredentialsRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}