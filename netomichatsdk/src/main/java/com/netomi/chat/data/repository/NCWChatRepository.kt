package com.netomi.chat.data.repository
import android.content.Context
import android.util.Log
import com.netomi.chat.data.network.BaseService
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.data.network.NCWRetrofitClient
import com.netomi.chat.model.AppConfigurationResponseModel
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.utils.BaseResponse
import com.netomi.chat.utils.Routes
import com.netomi.chat.utils.State


class NCWChatRepository(private val context: Context) :BaseService() {

    private val apiInterface = NCWRetrofitClient.getInstance(context).create(NCWApiInterface::class.java)


    // Fetch chat history
    fun getChatHistory(): State<BaseResponse<ArrayList<NCWMessage>>> {
            val response = apiInterface.fetchChatHistory()
            return if (response.isSuccessful && response.body()!=null) {
                State.success(data = response.body()!!,Routes.ROUTE_GET_CHAT)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    State.error(parseError(errorBody), code = response.code())
                } else {
                    State.error(mapApiException(response.code()), code = response.code())
                }
            }
    }

    // Send a new message
     fun sendMessage(message: NCWMessage): State<BaseResponse<Boolean>>  {
            val response = apiInterface.sendMessage(message)
           return if (response.isSuccessful && response.body()!=null) {
               State.success(data = response.body()!!,Routes.ROUTE_SEND_CHAT)
            } else {
               val errorBody = response.errorBody()
               if (errorBody != null) {
                   State.error(parseError(errorBody), code = response.code())
               } else {
                   State.error(mapApiException(response.code()), code = response.code())
               }
           }
    }



 // Test App config API
   suspend  fun getAppConfiguration(): State<BaseResponse<AppConfigurationResponseModel>> {
        val response =  apiInterface.getAppConfiguration()
        return if(response.isSuccessful && response.body()!=null) {
            State.success(data = response.body()!!, Routes.ROUTE_APP_CONFIG)
        }else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                State.error(parseError(errorBody), code = response.code())
            } else {
                State.error(mapApiException(response.code()), code = response.code())
            }
        }

    }
    }
