package com.netomi.sampleapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.netomi.sampleapplication.data.repository.AppRepository
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.model.FetchJwtTokenResponse
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

    private val _jwtToken = SingleEvent<State<FetchJwtTokenResponse>>()
    val jwtToken get() = _jwtToken


    private val _botList = MutableLiveData<Bot>()
    val botList: LiveData<Bot> get() = _botList



    fun getBotListing(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = onboardingRepository.getBotListing(_botListing,email)
            withContext(Dispatchers.Main) {
                Log.e("ConversationIdResponse", "response " + response)
                _botListing.value = response // Use setValue on the Main thread
            }
        }
    }

    fun fetchJwtToken(botRefId:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = onboardingRepository.getJwtToken(_jwtToken, botRefID = botRefId)
            withContext(Dispatchers.Main) {
                Log.e("JwtTokenResponse", "response $response")
                _jwtToken.value = response // Use setValue on the Main thread
            }
        }
    }

    // Function to update the bot list
    fun updateBotList(bots: Bot) {
        _botList.value = bots
    }

}
