package com.netomi.chat.ui.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.netomi.chat.awsiot.NCWAwsIotManager
import com.netomi.chat.data.repository.NCWAwsCredentialsRepository
import com.netomi.chat.model.awsmqtt.NCWAwsCredentials

class NCWAwsCredentialsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NCWAwsCredentialsRepository.getInstance(application)

    private val _credentials = NCWSingleLiveEvent<NCWAwsCredentials?>()
    val credentials get() = _credentials

    val connectionStatus: NCWSingleLiveEvent<String> = NCWAwsIotManager.getConnectionStatusLiveData()



    /**
     * Initialize the AWS IOT
     */
    fun initializeAwsIotManager(chatViewModel: NCWChatViewModel, topic: String) {
        val credentials = repository.getAWSCredentials()
        if (credentials != null) {
            NCWAwsIotManager.initialize(
                accessKey = credentials.accessKey,
                secretKey = credentials.secretKey,
                sessionToken = credentials.sessionToken,
                iotEndpoint = credentials.iotEndpoint
            )

            /**
             * Now that it's initialized, connect to IoT
             */

            NCWAwsIotManager.connect(chatViewModel,topic)
        }

    }

    fun getAWSCredentialsExpiry(): Long? {
        val expireTime = repository.getExpireTime()
        return if (expireTime > 0) expireTime else null
    }



    /**
     * Load AWS Credentials from repository
     */
    fun getAwsCredentials() {
        _credentials.value = repository.getAWSCredentials()
    }

    /**
     * Save credentials through repository
     */
    fun saveAwsCredentials(credentials: NCWAwsCredentials) {
        repository.saveAWSCredentials(credentials)
        _credentials.value = credentials
    }

    /**
     * Clear credentials from repository
     */
    fun clearCredentials() {
        repository.clearCredentials()
        _credentials.value = null
    }
}


