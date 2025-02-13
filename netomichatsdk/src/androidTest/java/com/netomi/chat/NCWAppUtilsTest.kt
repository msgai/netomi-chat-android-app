package com.netomi.chat

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileConfig
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.utils.NCWAppUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.File

class NCWAppUtilsTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

    }
    @Test
    fun testIsValidEmail() {


        val result =NCWAppUtils.isValidEmail("test@example.com")
        assertTrue(result)
        assertFalse(NCWAppUtils.isValidEmail("invalid-email"))
    }

    @Test
    fun testValidURL() {
        assertTrue(NCWAppUtils.isValidUrl("https://www.google.com"))
        assertFalse(NCWAppUtils.isValidUrl("invalid-url"))
    }

    @Test
    fun `testFormatTimestampToTime`() {
        val timestamp = 1696080000000L // Example timestamp
        val formattedTime = NCWAppUtils.formatTimestampToTime(timestamp)
        Log.e("formattedTime","formattedTime "+formattedTime)
        assertNotNull(formattedTime)
        // Example: assertEquals("12:00 PM", formattedTime)
    }

    @Test
    fun testSetHtmlText() {
        val textView = TextView(context)
        NCWAppUtils.setHtmlText(textView, "<b>Bold</b>")
        assertEquals("Bold", textView.text.toString())
    }

    @Test
    fun testIsNetworkAvailable() {
        assertTrue(NCWAppUtils.isNetworkAvailable(context))
    }

    @Test
    fun testShowToast() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            NCWAppUtils.showToast(context, "Test message")
        }
    }

    @Test
    fun testPrepareFilePart() {
       val testFile = File(context.filesDir, "test_file.txt")
        val result = NCWAppUtils.prepareFilePart(testFile)
        assertNotNull(result)
    }

    @Test
    fun testGetFileContentType() {
        val testFile = File(context.filesDir, "test_file.txt")
        assertEquals("text/plain", NCWAppUtils.getFileContentType(testFile))
    }



    @Test
    fun testFileSizeFormatting() {
        assertEquals("1.00 MB", NCWAppUtils.formatFileSize(1048576L))
        assertEquals("512.00 KB", NCWAppUtils.formatFileSize(524288L))
        assertEquals("100 Bytes", NCWAppUtils.formatFileSize(100L))
    }

    @Test
    fun testFileContentTypeDetection() {
        assertEquals("image/png", NCWAppUtils.getFileContentType(File("test.png")))
        assertEquals("application/pdf", NCWAppUtils.getFileContentType(File("document.pdf")))
    }
    @Test
    fun testCreateRequestBody() {
        val result = NCWAppUtils.createRequestBody("value")
        assertNotNull(result)
    }

    @Test
    fun validfileuploadwithinlimit() {


        val formComponent = realComponent(maxSizeMB = 10L, previousSizeMB = 5.0)
        val file = mockFile(sizeMB = 3.0) //

        val result = NCWAppUtils.isFormSizeValid(formComponent, file)
        assertTrue(result)
    }

    @Test
    fun testGetDomainOutOfURL() {
        assertEquals("google-search", NCWAppUtils.getDomainOutOfURL("https://www.google.com/search?q=test"))
    }

    @Test
    fun testDomainExtractionFromURL() {
        assertEquals("google", NCWAppUtils.getDomainOutOfURL("https://www.google.com"))
    }


    @Test
    fun testValidExpression() {
        val expression = "idleTime == 3000"
        val result = NCWAppUtils.parseIdleTimeFromExpression(expression)
        assertEquals(3000L, result)
    }

    @Test
    fun testInvalidExpression() {
        val expression = "idleTime = 3000" // Incorrect format
        val result = NCWAppUtils.parseIdleTimeFromExpression(expression)
        assertEquals(0L, result)
    }

    @Test
    fun testEmptyExpression() {
        val expression = ""
        val result = NCWAppUtils.parseIdleTimeFromExpression(expression)
        assertEquals(0L, result)
    }

  /*  // Helper functions to mock components and files
    private fun mockComponent(maxSizeMB: Double, previousSizeMB: Double): Component {
        val component = Mockito.mock(Component::class.java)
        val config = Mockito.mock(FileConfig::class.java)
        Mockito.`when`(config.maxUploadSizeAllowed).thenReturn(maxSizeMB.toLong())

        val previousFiles = arrayListOf(mockFileUpload(previousSizeMB))
        Mockito.`when`(component.fileUpload).thenReturn(previousFiles)
        Mockito.`when`(component.config).thenReturn(config)

        return component
    }
*/
    private fun mockFile(sizeMB: Double): File {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.length()).thenReturn((sizeMB * 1024 * 1024).toLong()) // Convert MB to bytes
        return file
    }

    private fun mockFileUpload(sizeMB: Double): FileUploadData {
        val fileUpload = Mockito.mock(FileUploadData::class.java)
        Mockito.`when`(fileUpload.fileSize).thenReturn((sizeMB * 1024 * 1024).toLong())
        return fileUpload
    }

    private fun realComponent(maxSizeMB: Long, previousSizeMB: Double): Component {
        val config = FileConfig(maxUploadSizeAllowed = maxSizeMB.toLong())

        val previousFiles = arrayListOf(FileUploadData(fileSize = (previousSizeMB * 1024 * 1024).toLong()))

        return Component(config = config, fileUpload = previousFiles, additionalSettings = emptyMap(), children = 0, component = "", output="", componentName = "", dropdownSelection = "", id = "", dropDownSelections = emptyMap(), labels = null, optionList = null, type = "", variableType = "", validations = emptyList())
    }



}

