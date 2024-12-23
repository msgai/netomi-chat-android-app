package com.netomi.sampleapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent
import com.netomi.chat.utils.NCWState
import com.netomi.sampleapplication.data.repository.AppRepository
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingRequest
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for managing chat messages in the NCWChat application
 *
 * This class is responsible for holding and managing UI-related data for the chat activity.
 * It ensures that the chat data survives configuration changes, such as screen rotations.
 * Additionally, it provides methods to load chat history and send new messages.
 *
 * ## Responsibilities:
 * Load and manage the list of chat messages.
 * Expose chat messages through LiveData to be observed by the UI.
 * Provide a method to send new messages and update the message list.
 * Use `viewModelScope` to manage coroutines, ensuring proper lifecycle handling.
 *
 * ## Usage:
 * This ViewModel should be used in conjunction with the `ChatActivity`.
 * The UI observes the `chatMessages` LiveData to update the chat log in real-time.
 *
 */
class OnboardingViewModel(application: Application) : AndroidViewModel(application) {
    private val onboardingRepository = AppRepository(application.applicationContext)

    private val _botListing = SingleEvent<State<BotListingResponse>>()
    val botListing get() = _botListing

    //val botList = MutableLiveData<List<Bot>>()



    fun getBotListing() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = onboardingRepository.getBotListing(_botListing)
            withContext(Dispatchers.Main) {
                Log.e("ConversationIdResponse", "response " + response)
                _botListing.value = response // Use setValue on the Main thread
            }
        }
    }

}
