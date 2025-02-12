package com.netomi.chat
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.NCWGetConversationIdResponse
import com.netomi.chat.ui.init.NCWApplication
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import com.netomi.chat.utils.NCWState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NCWChatViewModelTest {

    // Rule for Live data testing
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    // Test Dispatcher
    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockApplication: NCWApplication

    @Mock
    private lateinit var mockRepository: NCWChatRepository

    @Mock
    private lateinit var conversationIdObserver: Observer<NCWState<NCWGetConversationIdResponse>>

    private lateinit var viewModel: NCWChatViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        whenever(mockApplication.applicationContext).thenReturn(mock(Context::class.java))
        viewModel = NCWChatViewModel(mockApplication)
        viewModel.getConversationId.observeForever(conversationIdObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getConversationId success response`() = runTest {
        val botRef = "60e915d0-3eda-4fda-8c50-2da9dc036edf"
        val successData = NCWGetConversationIdResponse(
            message = "Success",
            statusCode = 200,
            conversationID = "conversationId123"
        )
        val successResponse = NCWState.success(successData, apiConstant = "api/conversationId")
        whenever(mockRepository.getConversationId(anyOrNull(),false)).thenReturn(successResponse)
        viewModel.getConversationId(botRef,"km@gmail.com",false)
        advanceUntilIdle()
        //runCurrent()

        // Capture the observer's argument
        argumentCaptor<NCWState<NCWGetConversationIdResponse>>().apply {
            verify(conversationIdObserver).onChanged(capture())
            val capturedValue = firstValue

            // Assert that the emitted value is the expected success response
            assertTrue(capturedValue is NCWState.Success && capturedValue.data == successData)
        }
    }

    @Test
    fun `test getConversationId error response`() = runTest {
        val botRef = "sampleBotRef"
        val errorMessage = "Error occurred"
        val errorCode = 500
        val errorResponse = NCWState.error<NCWGetConversationIdResponse>(errorMessage, errorCode)

        // Mock repository to return an error response
        whenever(mockRepository.getConversationId(anyOrNull(),false)).thenReturn(errorResponse)

        // Call getConversationId and advance until idle to allow all coroutines to complete
        viewModel.getConversationId(botRef,"",false)
        advanceUntilIdle()

        // Verify the observer was called with the error state
        verify(conversationIdObserver).onChanged(errorResponse)
    }


}