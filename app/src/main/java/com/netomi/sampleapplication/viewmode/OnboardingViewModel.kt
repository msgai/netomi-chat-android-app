package com.netomi.sampleapplication.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.netomi.sampleapplication.data.repository.AppRepository

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


}
