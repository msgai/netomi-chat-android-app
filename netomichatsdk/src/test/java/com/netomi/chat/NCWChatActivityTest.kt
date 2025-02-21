package com.netomi.chat

import NCWIdleTimeoutManager
import android.os.Handler
import android.util.Log
import com.netomi.chat.model.theme.NCWLanguage
import com.netomi.chat.model.theme.NCWMultilingual
import com.netomi.chat.model.theme.NCWQuickMenuOption
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.ui.view.NCWChatActivity
import com.netomi.chat.ui.viewmodel.NCWChatViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class NCWChatActivityTest {
    @Mock
    private lateinit var chatViewModel: NCWChatViewModel

    @Mock
    private lateinit var handler: Handler

    @Mock
    private lateinit var themeData: NCWThemeResponse

    private lateinit var activity: NCWChatActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        activity = Robolectric.buildActivity(NCWChatActivity::class.java).create().get()

        activity.handler = handler
        activity.themeData = themeData
    }

    @Test
    fun testActivityCreation() {
        val activity = Robolectric.buildActivity(NCWChatActivity::class.java).setup().get()
        assert(activity != null)
    }

    @Test
    fun testidletimeoutmanagerinitializescorrectly() {
        // Mock theme data
        val idleTimeoutMillis = 30000L
        Log.e("themeData","themeData "+themeData)
        `when`(themeData.endChat?.idleTimeout).thenReturn(idleTimeoutMillis)

        activity.idleTimeoutManager = NCWIdleTimeoutManager(
            idleTimeoutMillis = idleTimeoutMillis,
            onTimeout = {
                activity.handleSessionTimeout(
                    "Session Timeout",
                    "Your session has expired",
                    "OK",
                    "SESSION"
                )
            }
        )

        assertNotNull(activity.idleTimeoutManager)
        assertEquals(idleTimeoutMillis, activity.idleTimeoutManager.idleTimeoutMillis)
    }

    @Test
    fun testquickreplyinvokessendMessageToBot() {
        val options = NCWQuickMenuOption("Hello", "Greeting")
        val payload = activity.createPayload(options.text, options.label, System.currentTimeMillis())

        activity.chatViewModel.sendMessage(options.label, System.currentTimeMillis())
        activity.sendMessageToBot(payload)

        verify(chatViewModel).sendMessage(eq("Greeting"), any())
    }

    @Test
    fun testlanguageselectionupdatesthemeData() {
        val options = NCWLanguage("EN", "English","English")
     //   `when`(themeData.multilingual).thenReturn(NCWMultilingual())

        activity.setUpLanguageOption()
        themeData.multilingual.selectedCode = options.code

        assertEquals("EN", themeData.multilingual.selectedCode)
        verify(chatViewModel).getLanguageStrings(any(), eq("EN"))
    }

    @Test
    fun testmediapermissions() {
        `when`(activity.arePermissionsGranted()).thenReturn(false)

        activity.showMedia()

        verify(activity).requestPermissionsAndShowMediaOptions()
    }

    @Test
    fun testbackClicked() {
        activity.backClicked()

        // Verify if chat is ended
        verify(activity).hitEndChatAPI()
    }


}