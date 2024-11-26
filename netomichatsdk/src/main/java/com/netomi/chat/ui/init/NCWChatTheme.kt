package com.netomi.chat.ui.init

import android.content.Context
import android.util.Log
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.data.network.NCWRetrofitClient
import com.netomi.chat.model.theme.ThemeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NCWChatTheme(
    val context: Context,
    private val botReference: String,
    private val onThemeReceived: (ThemeResponse?) -> Unit,
    private val onError: (String) -> Unit
) {
    private val apiInterface =
        NCWRetrofitClient.getInstance(context).create(NCWApiInterface::class.java)

    init {
        fetchSdkTheme()
    }

    private fun fetchSdkTheme() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiInterface.getSdkTheme(botReference)
                if (response.isSuccessful) {
                    val themeResponse = response.body()
                    withContext(Dispatchers.Main) {
                        onThemeReceived(themeResponse)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Error: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("Exceptoo","aassaas"+e.printStackTrace())
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Unknown error")
                }
            }
        }
    }
}














