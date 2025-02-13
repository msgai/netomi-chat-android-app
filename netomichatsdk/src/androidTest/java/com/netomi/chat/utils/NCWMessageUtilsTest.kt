package com.netomi.chat.utils


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.model.messages.Component
import com.netomi.chat.model.messages.FileConfig
import com.netomi.chat.model.messages.FileUploadData
import com.netomi.chat.utils.NCWMessageUtils.mergeChunks
import com.netomi.chat.utils.NCWMessageUtils.validateFileAttachment
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.File

class NCWMessageUtilsTest {

    private lateinit var context: Context


    @Before
    fun setUp() {
        // Manually mock final class
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun splitIntoChunks_whenTextIsMultipleOfWordsPerChunk_returnsEqualChunks() {
        val text = "This is a test message split into equal parts"
        val expected = listOf("This is a test message ", "split into equal parts")
        val actual = NCWMessageUtils.splitIntoChunks(text, 5)
        assertEquals(expected, actual)
    }

    @Test
    fun splitIntoChunks_whenTextHasRemainderWords_returnsLastChunkWithRemainingWords() {
        val text = "This is a test message that has more than eight words"
        val expected = listOf("This is a test message ", "that has more than eight ", "words")
        val actual = NCWMessageUtils.splitIntoChunks(text, 5)
        assertEquals(expected, actual)
    }

    @Test
    fun splitIntoChunks_whenTextHasLessThanWordsPerChunk_returnsSingleChunk() {
        val text = "Short message"
        val expected = listOf("Short message")
        val actual = NCWMessageUtils.splitIntoChunks(text, 5)
        assertEquals(expected, actual)
    }

    @Test
    fun splitIntoChunks_whenTextHasExactlyWordsPerChunk_returnsSingleChunk() {
        val text = "One two three four five"
        val expected = listOf("One two three four five")
        val actual = NCWMessageUtils.splitIntoChunks(text, 5)
        assertEquals(expected, actual)
    }

    @Test
    fun splitIntoChunks_whenTextIsEmpty_returnsEmptyChunk() {
        val text = ""
        val expected = listOf("")
        val actual = NCWMessageUtils.splitIntoChunks(text, 5)
        assertEquals(expected, actual)
    }

    @Test
    fun splitIntoChunks_whenTextIsSingleWord_returnsSingleChunk() {
        val text = "Word"
        val expected = listOf("Word")
        val actual = NCWMessageUtils.splitIntoChunks(text, 5)
        assertEquals(expected, actual)
    }


    @Test
    @Throws(Exception::class)
    fun testValidateFileAttachment() {

        val file = mockFile("dummy.png", sizeMB = 3.0) //
        val formComponent = realComponent(maxSizeMB = 10L, previousSizeMB = 5.0)
        val result = validateFileAttachment(formComponent,
            file,
            listOf("png"),
            context,
            onValidationFailed = { message, description ->

            })
        Assert.assertEquals(true, result)
    }


    @Test
    fun mergeChunksWithMltipleMessages() {
        val lis1 = listOf(
            NCWMessage(message = "Hello, world!", timestamp = 8754837584L),
            NCWMessage(message = " How are you?", timestamp = 87548377484L)
        )
        val result = mergeChunks(lis1)
        assertEquals("Hello, world! How are you?", result.message)

    }


    private fun mockFile(sizeMB: Double): File {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.length())
            .thenReturn((sizeMB * 1024 * 1024).toLong()) // Convert MB to bytes
        return file
    }


    private fun realComponent(maxSizeMB: Long, previousSizeMB: Double): Component {
        val config = FileConfig(maxUploadSizeAllowed = maxSizeMB.toLong())

        val previousFiles =
            arrayListOf(FileUploadData(fileSize = (previousSizeMB * 1024 * 1024).toLong()))

        return Component(
            config = config,
            fileUpload = previousFiles,
            additionalSettings = emptyMap(),
            children = 0,
            component = "",
            output = "",
            componentName = "",
            dropdownSelection = "",
            id = "",
            dropDownSelections = emptyMap(),
            labels = null,
            optionList = null,
            type = "",
            variableType = "",
            validations = emptyList()
        )
    }


    fun mockFile(fileName: String, sizeMB: Double): File {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.name).thenReturn(fileName)
        Mockito.`when`(file.length())
            .thenReturn((sizeMB * 1024 * 1024).toLong()) // Convert MB to bytes
        return file
    }


}
