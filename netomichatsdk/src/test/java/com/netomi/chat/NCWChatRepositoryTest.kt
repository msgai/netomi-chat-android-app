package com.netomi.chat

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.data.repository.NCWChatRepository
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.chat_history.NCWHistoryRequestBody
import com.netomi.chat.model.messages.NCWWebhookPayload
import com.netomi.chat.utils.NCWState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
@RunWith(RobolectricTestRunner::class)
class NCWChatRepositoryTest {
    private lateinit var repository: NCWChatRepository
    private lateinit var apiInterface: NCWApiInterface
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        apiInterface = mock(NCWApiInterface::class.java)
        repository = NCWChatRepository(context)
    }

    private fun mockSuccessResponse(): Response<NCWGetChatHistoryResponse> {
        val mockResponse = NCWGetChatHistoryResponse(
            botReferenceId = "bot-456",
            conversationId = "test-convo-123",
            statusMessage = "Success",
            status = 200
        )
        return Response.success(mockResponse)
    }

    @Test
    fun `test getChatHistory successful response`() = runBlocking {
        val liveData = MutableLiveData<NCWState<NCWGetChatHistoryResponse>>()
        val payload = NCWGetChatHistoryPayload(
            conversationId = "test-convo-123",
            requestBody = NCWHistoryRequestBody(
                numberOfMessages = 5,
                numberOfDays = 3
            ),
            botRefId = "bot-456"
        )


        // Mock API response (Success Case)
        `when`(apiInterface.fetchChatHistory(payload)).thenReturn(mockSuccessResponse())

        val result = repository.getChatHistory(payload, liveData)

        assertNotNull(result)
        assertTrue(result is NCWState.Success)
        val successResponse = (result as NCWState.Success).data
        assertEquals("test-convo-123", successResponse.conversationId)
        assertEquals("bot-456", successResponse.botReferenceId)
        assertEquals(200, successResponse.status)  // Assuming 200 is a success status
        assertEquals("Success", successResponse.statusMessage)
        //assertTrue(successResponse.responses.isNotEmpty())
    }

    /*@Test
    fun `test sendMessage success`() = runBlocking {
        val message = NCWWebhookPayload()
        `when`(apiInterface.sendMessage(message)).thenReturn(mockSuccessResponse())

        val result = repository.sendMessage(message)

        assertNotNull(result)
        assertTrue(result is NCWState.Success)
    }*/


}