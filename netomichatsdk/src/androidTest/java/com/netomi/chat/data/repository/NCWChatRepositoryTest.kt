package com.netomi.chat.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.netomi.chat.data.network.NCWApiInterface
import com.netomi.chat.model.GetConversationPayload
import com.netomi.chat.model.NCWGetChatHistoryResponse
import com.netomi.chat.model.auth.LoginResponse
import com.netomi.chat.model.chat_history.NCWGetChatHistoryPayload
import com.netomi.chat.model.chat_history.NCWHistoryRequestBody
import com.netomi.chat.model.endchat.NCWEndChatRequest
import com.netomi.chat.model.feedback.feedbackrequest.NCWEventData
import com.netomi.chat.model.feedback.feedbackrequest.NCWEventInfo
import com.netomi.chat.model.feedback.feedbackrequest.NCWFeedbackRequest
import com.netomi.chat.model.feedback.feedbackresponse.NCWRequestBody
import com.netomi.chat.model.media_payload.NCWSignedUrlPayload
import com.netomi.chat.model.presigned_url.NCWFields
import com.netomi.chat.model.presigned_url.NCWGetPreSignedUrl
import com.netomi.chat.model.presigned_url.NCWPreSignedUrl
import com.netomi.chat.survey.EventData
import com.netomi.chat.survey.EventInfo
import com.netomi.chat.survey.NCWSignInUserDetails
import com.netomi.chat.survey.RequestBody
import com.netomi.chat.survey.SubmitSurveyInfo
import com.netomi.chat.survey.SubmitSurveyRequest
import com.netomi.chat.utils.NCWState
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.File

@RunWith(AndroidJUnit4::class)
class NCWChatRepositoryTest {

    // Declare variables for mock context, API interface, and repository
    private lateinit var mockContext: Context
    private lateinit var apiInterface: NCWApiInterface
    private lateinit var nCWChatRepository: NCWChatRepository

    // Setup method to initialize the mocks and objects before each test
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)  // Initialize mock annotations

        apiInterface = Mockito.mock(NCWApiInterface::class.java)  // Mock the API interface
        mockContext = Mockito.mock(Context::class.java)  // Mock the context

        val context: Context = ApplicationProvider.getApplicationContext()  // Get the application context
        nCWChatRepository = NCWChatRepository(context)  // Instantiate the repository

        // Access SharedPreferences (add mock setup for SharedPreferences if required here)
    }

    // Test case to check the successful fetching of AWS MQTT credentials
    @Test
    fun getAWSMQTTCredentials() = runBlocking {
        // Call the function with a test parameter
        val result = nCWChatRepository.getAWSMQTTCredentials("7b45faa2-19e7-4277-9c2d-f2a261a3af66111")

        // Assertions to check that the result is successful and the status code is 200
        Assert.assertTrue(result is NCWState.Success)  // Ensure result is Success type
        val successResult = result as NCWState.Success  // Cast result to Success type
        Assert.assertEquals(200, successResult.data.statusCode)  // Assert status code is 200
    }


    // Test case to check the failure scenario while fetching AWS MQTT credentials
    @Test
    fun getAWSMQTTCredentialsFail() = runBlocking {
        // Call the function with a test parameter
        val result = nCWChatRepository.getAWSMQTTCredentials("7b45faa2-19e7-4277-9c2d-f2a261a3af66111")

        // Assertions to check that the result is successful and the status code is 500
        Assert.assertTrue(result is NCWState.Success)  // Ensure result is Success type
        val successResult = result as NCWState.Success  // Cast result to Success type
        Assert.assertEquals(500, successResult.data.statusCode)  // Assert status code is 500
    }

    // Test case to check the successful fetching of the conversation ID

    @Test
    fun getConversationId() = runBlocking {
        // Call the function with a test parameter
        val mockConversationPayload = GetConversationPayload(
            botRefId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            externalId = "885e5934-a8dc-4200-9eed-b30aec096fd7"
        )

        val result = nCWChatRepository.getConversationId(mockConversationPayload, true)

        // Assertions to check that the result is successful and the status code is 200
        Assert.assertTrue(result is NCWState.Success)  // Ensure result is Success type
        val successResult = result as NCWState.Success  // Cast result to Success type
        Assert.assertEquals(200, successResult.data.statusCode)  // Assert status code is 200
    }

    // Test case to check the failure scenario while fetching the conversation ID
    @Test
    fun getConversationIdFail() = runBlocking {
        val mockConversationPayload = GetConversationPayload(
            botRefId = "",
            externalId = ""
        )

        // Call the function with a test parameter
        val result = nCWChatRepository.getConversationId(mockConversationPayload, false)

        // Assertions to check that the result is successful and the status code is 500
        Assert.assertTrue(result is NCWState.Error)  // Ensure result is Success type
        val successResult = result as NCWState.Error  // Cast result to Success type
        Assert.assertEquals(500, successResult.code)  // Assert status code is 500
    }




    @Test
    fun getSurveyRule_Success() = runBlocking {
        val result = nCWChatRepository.getSurveyRule("fbb95902-3a6d-4727-8fd2-56d89e185f86")
        Assert.assertTrue(result is NCWState.Success)
        val successResult = result as NCWState.Success
        Assert.assertEquals(200, successResult.data.statusCode)
    }


    @Test
    fun getSurveyRule_Failure_500() = runBlocking {
        val result = nCWChatRepository.getSurveyRule("server-error-bot-id")
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(500, errorResult.code)
    }


    @Test
    fun hitLogoutApi_Failure_404() = runBlocking {
        val result = nCWChatRepository.hitLogoutApi("valid-token", "server-error-bot-id", "true")
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(404, errorResult.code)
    }

    @Test
    fun hitLogoutApi_Failure_500() = runBlocking {
        val result = nCWChatRepository.hitLogoutApi("valid-token", "server-error-bot-id", "true")
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(500, errorResult.code)
    }


    @Test
    fun hitLogoutApi_Success_200() = runBlocking {
        val result = nCWChatRepository.hitLogoutApi("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiR3Vlc3QgVXNlciIsImV4dGVybmFsSWQiOiJ0ZXN0cmFtQHlvcG1hb2wueHltIiwiaWF0IjoxNzM5MzU5NzYwLCJleHAiOjE3MzkzNjMzNjB9.oVZw9LjChVnVEwxJE46AHwXEsKBRlllSmxWiTVoJbYk", "60e915d0-3eda-4fda-8c50-2da9dc036edf", "true")
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.statusCode)
    }



    @Test
    fun hitAuthenticateUserApi_Success_200() = runBlocking {
        val result = nCWChatRepository.hitAuthenticateUserApi("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiR3Vlc3QgVXNlciIsImV4dGVybmFsSWQiOiJ0ZXN0cmFtQHlvcG1hb2wueHltIiwiaWF0IjoxNzM5MzU5NzYwLCJleHAiOjE3MzkzNjMzNjB9.oVZw9LjChVnVEwxJE46AHwXEsKBRlllSmxWiTVoJbYk",
            "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            "true")
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.statusCode)
    }

    @Test
    fun hitAuthenticateUserApi_Success_401() = runBlocking {
        val result = nCWChatRepository.hitAuthenticateUserApi("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiR3Vlc3QgVXNlciIsImV4dGVybmFsSWQiOiJ0ZXN0cmFtQHlvcG1hb2wueHltIiwiaWF0IjoxNzM5MzU5NzYwLCJleHAiOjE3MzkzNjMzNjB9.oVZw9LjChVnVEwxJE46AHwXEsKBRlllSmxWiTVoJbYk",
            "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            "true")
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(401, errorResult.code)
    }

    @Test
    fun hitAuthenticateUserApi_Success_404() = runBlocking {
        val result = nCWChatRepository.hitAuthenticateUserApi("valid-token", "valid-bot-id", "true")
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(404, errorResult.code)
    }



    @Test
    fun hitSubmitSurveyRequestAPI_Success_500() = runBlocking {
        val mockPayload = SubmitSurveyRequest(
            botRefId = "sample-bot-id",
            requestBody = RequestBody(
                triggerType = "EVENT",
                eventData = EventData(
                    eventType = "SURVEY",
                    subType = "SUBMIT",
                    eventInfo = EventInfo(
                        surveyId = "survey-123",
                        feedbackValue = "Good",
                        requestId = "req-456",
                        submitSurveyInfo = SubmitSurveyInfo(
                            rating = 5,
                            suggestions = listOf("More features", "Better UI"),
                            suggestionTitle = "Suggestions",
                            issueResolved = true,
                            additionalFeedback = "Great experience!",
                            triggerType = "USER"
                        ),
                        queuePosition = "1",
                        estimatedWaitTime = "5 mins",
                        agentId = "agent-789",
                        agentName = "John Doe",
                        agentAvatar = "https://example.com/avatar.png"
                    ),
                    authenticatedConversationId = "auth-conv-111",
                    userdetails = NCWSignInUserDetails(
                        name = "Alice",
                        email = "alice@example.com",
                        userId = "user-222"
                    )
                ),
                conversationId = "conv-333",
                botReferenceId = "botRef-444",
                requestType = "NETOMI",
                channelId = "NETOMI_WEB_WIDGET",
                eventName = "FEEDBACK",
                timestamp = System.currentTimeMillis(),
                isPublishToMQTT = false
            )
        )

        val result = nCWChatRepository.hitSubmitSurveyRequestAPI(mockPayload)
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(500, errorResult.code)
    }




    @Test
    fun hitSubmitSurveyRequestAPI_Success_200() = runBlocking {
        val mockPayload = SubmitSurveyRequest(
            botRefId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            requestBody = RequestBody(
                triggerType = "EVENT",
                eventData = EventData(
                    eventType = "SURVEY",
                    subType = "SUBMIT",
                    eventInfo = EventInfo(
                        surveyId = "f1377172-5719-41d2-97f8-f1bd3bc10811",
                        feedbackValue = "",
                        requestId = "aa2eefbd-9ccf-49c5-83f1-fb4dab851f29",
                        submitSurveyInfo = SubmitSurveyInfo(
                            rating = 10,
                            suggestions = emptyList(),
                            suggestionTitle = "What went wrong?",
                            issueResolved = true,
                            additionalFeedback = "",
                            triggerType = "WIDGET_EVENT_IDLE_USER"
                        ),
                        queuePosition = null,
                        estimatedWaitTime = null,
                        agentId = null,
                        agentName = null,
                        agentAvatar = null
                    ),
                    authenticatedConversationId = null,
                    userdetails = null
                ),
                conversationId = "885e5934-a8dc-4200-9eed-b30aec096fd7",
                botReferenceId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
                requestType = "NETOMI",
                channelId = "NETOMI_WEB_WIDGET",
                eventName = "FEEDBACK",
                timestamp = 1739359179936,
                isPublishToMQTT = false
            )
        )


        val result = nCWChatRepository.hitSubmitSurveyRequestAPI(mockPayload)
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.status)
    }



    @Test
    fun hitFeedbackAPI_Failure_500() = runBlocking {

        val mockFeedbackRequest = NCWFeedbackRequest(
            botRefId = "12345-bot-ref",
            requestBody = NCWRequestBody(
                botReferenceId = "12345-bot-ref",
                channelId = "NETOMI_WEB_WIDGET",
                conversationId = "conv-98765",
                eventData = NCWEventData(
                    eventType = "FEEDBACK",
                    subType = "USER_RESPONSE",
                    eventInfo = NCWEventInfo(
                        attachmentIndex = 1,
                        feedbackValue = "Great service!",
                        requestId = "req-54321",
                        idleTime = 3000L
                    )
                ),
                eventName = "USER_FEEDBACK",
                isPublishToMQTT = false,
                requestType = "NETOMI",
                timestamp = System.currentTimeMillis(),
                triggerType = "EVENT"
            )
        )


        val result = nCWChatRepository.hitFeedbackAPI(mockFeedbackRequest)
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(500, errorResult.code)
    }


    @Test
    fun hitFeedbackAPI_Success_200() = runBlocking {

        val mockFeedbackRequest = NCWFeedbackRequest(
            botRefId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            requestBody = NCWRequestBody(
                botReferenceId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
                channelId = "NETOMI_WEB_WIDGET",
                conversationId = "885e5934-a8dc-4200-9eed-b30aec096fd7",
                eventData = NCWEventData(
                    eventType = "FEEDBACK",
                    subType = "REVIEW",
                    eventInfo = NCWEventInfo(
                        attachmentIndex = 7,
                        feedbackValue = "POSITIVE",
                        requestId = "dc1c6fcb-653a-4b94-88ae-c99d75d40608",
                        idleTime = null
                    )
                ),
                eventName = "FEEDBACK",
                isPublishToMQTT = false,
                requestType = "NETOMI",
                timestamp = 1739359497733,
                triggerType = "EVENT"
            )
        )


        val result = nCWChatRepository.hitFeedbackAPI(mockFeedbackRequest)
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.status)
    }



    @Test
    fun hitEndChatAPI500() = runBlocking {
        val mockEndChatRequest = NCWEndChatRequest(
            botRefId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            requestBody = com.netomi.chat.model.endchat.NCWRequestBody(
                botReferenceId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
                channelId = "NETOMI_WEB_WIDGET",
                conversationId = "885e5934-a8dc-4200-9eed-b30aec096fd7",
                eventData = com.netomi.chat.model.endchat.NCWEventData(
                    eventType = "END_CHAT",
                    subType = "USER_REQUEST",
                    userdetails = NCWSignInUserDetails(
                        name = "John Doe",
                        email = "johndoe@example.com",
                        userId = "user-12345"
                    )
                ),
                eventName = "END_CHAT",
                isPublishToMQTT = false,
                requestType = "NETOMI",
                timestamp = System.currentTimeMillis(),
                triggerType = "EVENT"
            )
        )

        val result = nCWChatRepository.hitEndChatAPI(mockEndChatRequest)
        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(500, errorResult.code)
    }




    @Test
    fun hitEndChatAPI200() = runBlocking {
        val mockEndChatRequest = NCWEndChatRequest(
            botRefId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            requestBody = com.netomi.chat.model.endchat.NCWRequestBody(
                botReferenceId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
                channelId = "NETOMI_WEB_WIDGET",
                conversationId = "8eb6ae46-f846-402d-9d7f-653432f62a13",
                eventData = com.netomi.chat.model.endchat.NCWEventData(
                    eventType = "WIDGET_EVENT",
                    subType = "CHAT_END"
                ),
                eventName = "INFO_PILL",
                isPublishToMQTT = false,
                requestType = "NETOMI",
                timestamp = 1739362911386,
                triggerType = "EVENT"
            )
        )

        val result = nCWChatRepository.hitEndChatAPI(mockEndChatRequest)
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.status)
    }


    @Test
    fun getPreSignedUrl200() = runBlocking {
        val mockPayload = NCWSignedUrlPayload(
            fileType = "image/gif",
            uploadKeyPrefix = "snow-16130_256.gif"
        )

        val result = nCWChatRepository.getPreSignedUrl(mockPayload)
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.statusCode)
    }



    @Test
    fun `uploadFileSuccess`() = runBlocking {
        val mediaFile = File("test_file.jpg")
        val preSignedUrl = NCWGetPreSignedUrl(
            preSignedUrl = NCWPreSignedUrl(
                url = "https://mock-presigned-url.com",
                fields = NCWFields(
                    key = "mock-key",
                    bucket = "mock-bucket",
                    XAmzAlgorithm = "mock-algorithm",
                    XAmzCredential = "mock-credential",
                    XAmzDate = "mock-date",
                    policy = "mock-policy",
                    XAmzSignature = "mock-signature"
                )
            )
        )
        val result = nCWChatRepository.uploadFile(mediaFile, preSignedUrl)

        Assert.assertTrue(result is NCWState.Error)
        val errorResult = result as NCWState.Error
        Assert.assertEquals(500, errorResult.code)
    }




    @Test
    fun getChatHistory200() = runBlocking {
        // Arrange
        val chatHistoryPayload = NCWGetChatHistoryPayload(
            conversationId = "576e6a4a-8a92-4856-ab91-dfef77dbe78d",
            botRefId = "60e915d0-3eda-4fda-8c50-2da9dc036edf",
            requestBody = NCWHistoryRequestBody(
                numberOfMessages = 100,
                numberOfDays = 10
            )
        )
        val liveData = MutableLiveData<NCWState<NCWGetChatHistoryResponse>>()


        val result = nCWChatRepository.getChatHistory(chatHistoryPayload, liveData)

        // Assert
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.status)
    }


    @Test
    fun getChatHistoryFail() = runBlocking {
        // Arrange
        val chatHistoryPayload = NCWGetChatHistoryPayload(
            conversationId = "576e6dbe78d",
            botRefId = "60e915d0-3ed6edf",
            requestBody = NCWHistoryRequestBody(
                numberOfMessages = 100,
                numberOfDays = 10
            )
        )
        val liveData = MutableLiveData<NCWState<NCWGetChatHistoryResponse>>()


        val result = nCWChatRepository.getChatHistory(chatHistoryPayload, liveData)

        // Assert
        Assert.assertTrue(result is NCWState.Success)
        val errorResult = result as NCWState.Success
        Assert.assertEquals(200, errorResult.data.status)
    }

}
