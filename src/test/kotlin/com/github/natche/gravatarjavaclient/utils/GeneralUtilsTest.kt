package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.TestingImageUrls
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import com.github.natche.gravatarjavaclient.utils.GeneralUtils.readChunkedBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.Reader
import java.io.StringReader
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.atomic.AtomicReference

/**
 * Tests for the [GeneralUtils].
 */
internal class GeneralUtilsTest {
    /**
     * Tests to ensure reflection is guarded against.
     * Also helps reach 100% code coverage for testing.
     */
    @Test
    fun testCreation() {
        try {
            val constructor = GeneralUtils::class.java.getDeclaredConstructor()
            constructor.isAccessible = true
            constructor.newInstance()
        } catch (e: Exception) {
            assertTrue(e is InvocationTargetException)
            val target = (e as InvocationTargetException).targetException
            assertTrue(target is AssertionError)
            assertEquals("Cannot create instances of GeneralUtils", target.message)
        }
    }

    /**
     * Tests for the read buffered image method.
     */
    @Test
    fun testReadBufferedImage() {
        assertThrows(NullPointerException::class.java) { GeneralUtils.readBufferedImage(null) }
        assertThrows(IllegalArgumentException::class.java) { GeneralUtils.readBufferedImage("") }
        assertThrows(GravatarJavaClientException::class.java) { GeneralUtils.readBufferedImage("url") }
        val bi = AtomicReference<BufferedImage>()
        assertDoesNotThrow { bi.set(GeneralUtils.readBufferedImage(TestingImageUrls.foreignImageUrl)) }
        assertEquals(200, bi.get().width)
        assertEquals(300, bi.get().height)
    }

    /**
     * Tests for the read url method.
     */
    @Test
    fun testReadUrl() {
        assertThrows(NullPointerException::class.java)
        { GeneralUtils.readUrl(null) }
        assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.readUrl("") }
        assertThrows(GravatarJavaClientException::class.java)
        { GeneralUtils.readUrl("invalid url") }
        assertDoesNotThrow { GeneralUtils.readUrl("https://www.google.com") }
    }

    /**
     * Tests for the is valid filename method.
     */
    @Test
    fun testIsValidFilename() {
        assertThrows(NullPointerException::class.java) { GeneralUtils.isValidFilename(null) }
        assertThrows(IllegalArgumentException::class.java) { GeneralUtils.isValidFilename("") }
        assertDoesNotThrow { GeneralUtils.isValidFilename("filename") }
        assertTrue(GeneralUtils.isValidFilename("filename"))
        assertTrue(GeneralUtils.isValidFilename("my_filename_123456789"))
        assertTrue(GeneralUtils.isValidFilename("my_filename_123456789.txt"))
        assertTrue(GeneralUtils.isValidFilename("my_filename_123456789...txt"))
        assertFalse(GeneralUtils.isValidFilename("$%^&*()"))
        assertFalse(GeneralUtils.isValidFilename("1>:/"))
    }

    /**
     * Tests for the skip headers method.
     */
    @Test
    fun testSkipHeaders() {
        assertThrows(NullPointerException::class.java) { GeneralUtils.skipHeaders(null) }

        val emptyStream = BufferedReader(StringReader(""))
        assertDoesNotThrow { GeneralUtils.skipHeaders(emptyStream) }

        val headerStream = BufferedReader(StringReader("Header1\nHeader2\n\nBody"))
        assertDoesNotThrow { GeneralUtils.skipHeaders(headerStream) }
        assertEquals("Body", headerStream.readLine())

        val noBodyStream = BufferedReader(StringReader("Header1\nHeader2\nHeader3"))
        assertDoesNotThrow { GeneralUtils.skipHeaders(noBodyStream) }
        assertNull(noBodyStream.readLine())

        val singleEmptyLineStream = BufferedReader(StringReader("\n"))
        assertDoesNotThrow { GeneralUtils.skipHeaders(singleEmptyLineStream) }
        assertNull(singleEmptyLineStream.readLine())
    }

    /**
     * Tests for the read chunked body method.
     */
    @Test
    fun testReadChunkedBody() {
        val emptyStream = BufferedReader(StringReader("\r\n"))
        assertEquals("", readChunkedBody(emptyStream))

        val trulyEmptyStream = BufferedReader(StringReader(""))
        assertEquals("", readChunkedBody(trulyEmptyStream))

        val nullChunkSizeStream = BufferedReader(object : Reader() {
            override fun read(charArray: CharArray, off: Int, len: Int): Int {
                return -1
            }

            override fun close() {}
        })
        assertEquals("", readChunkedBody(BufferedReader(nullChunkSizeStream)))

        val singleChunkStream = BufferedReader(StringReader("5\r\nHello\r\n0\r\n\r\n"))
        assertEquals("Hello", readChunkedBody(singleChunkStream))

        val multiChunkStream = BufferedReader(StringReader("4\r\nTest\r\n3\r\n123\r\n0\r\n\r\n"))
        assertEquals("Test123", readChunkedBody(multiChunkStream))

        val chunkWithTrailingCrlf = BufferedReader(StringReader("4\r\nData\r\n0\r\n\r\n"))
        assertEquals("Data", readChunkedBody(chunkWithTrailingCrlf))

        val endOfStreamMidChunk = BufferedReader(StringReader("5\r\nHel"))
        assertEquals("Hel", readChunkedBody(endOfStreamMidChunk))

        val invalidChunkSizeStream = BufferedReader(StringReader("Z\r\nInvalid\r\n0\r\n\r\n"))
        assertThrows(NumberFormatException::class.java) { readChunkedBody(invalidChunkSizeStream) }
    }
}