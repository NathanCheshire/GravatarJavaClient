package com.github.natche.gravatarjavaclient.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.BufferedReader
import java.io.Reader
import java.io.StringReader

/**
 * Tests for [ResourceReader]s.
 */
class ResourceReaderTest {
    /**
     * Tests for construction.
     */
    @Test
    fun testConstruction() {
        assertThrows(NullPointerException::class.java) { ResourceReader.from(null) }

        val br = mock(BufferedReader::class.java)
        assertDoesNotThrow { ResourceReader.from(br) }
    }

    /**
     * Test for the is valid filename method.
     */
    @Test
    fun testIsValidFilename() {
        assertThrows(NullPointerException::class.java) { ResourceReader.isValidFilename(null) }
        assertThrows(IllegalArgumentException::class.java) { ResourceReader.isValidFilename("") }
        assertDoesNotThrow { ResourceReader.isValidFilename("filename") }
        assertTrue(ResourceReader.isValidFilename("filename"))
        assertTrue(ResourceReader.isValidFilename("my_filename_123456789"))
        assertTrue(ResourceReader.isValidFilename("my_filename_123456789.txt"))
        assertTrue(ResourceReader.isValidFilename("my_filename_123456789...txt"))
        assertFalse(ResourceReader.isValidFilename("$%^&*()"))
        assertFalse(ResourceReader.isValidFilename("1>:/"))
    }

    /**
     * Tests for the skip headers method.
     */
    @Test
    fun testSkipHeaders() {
        val emptyStream = BufferedReader(StringReader(""))
        assertDoesNotThrow { ResourceReader.from(emptyStream).skipHeaders() }

        val headerStream = BufferedReader(StringReader("Header1\nHeader2\n\nBody"))
        assertDoesNotThrow { ResourceReader.from(headerStream).skipHeaders() }

        val noBodyStream = BufferedReader(StringReader("Header1\nHeader2\nHeader3"))
        assertDoesNotThrow { ResourceReader.from(noBodyStream).skipHeaders() }

        val singleEmptyLineStream = BufferedReader(StringReader("\n"))
        assertDoesNotThrow { ResourceReader.from(singleEmptyLineStream).skipHeaders() }
    }

    /**
     * Tests for the read chunked body method.
     */
    @Test
    fun testReadChunkedBody() {
        val emptyStream = BufferedReader(StringReader("\r\n"))
        assertDoesNotThrow { ResourceReader.from(emptyStream).readChunkedBody() }

        val trulyEmptyStream = BufferedReader(StringReader(""))
        assertDoesNotThrow { ResourceReader.from(trulyEmptyStream).readChunkedBody() }

        val nullChunkSizeStream = BufferedReader(object : Reader() {
            override fun read(charArray: CharArray, off: Int, len: Int): Int {
                return -1
            }

            override fun close() {}
        })
        assertDoesNotThrow { ResourceReader.from(nullChunkSizeStream).readChunkedBody() }

        val singleChunkStream = BufferedReader(StringReader("5\r\nHello\r\n0\r\n\r\n"))
        assertDoesNotThrow { ResourceReader.from(singleChunkStream).readChunkedBody() }

        val multiChunkStream = BufferedReader(StringReader("4\r\nTest\r\n3\r\n123\r\n0\r\n\r\n"))
        assertDoesNotThrow { ResourceReader.from(multiChunkStream).readChunkedBody() }

        val chunkWithTrailingCrlf = BufferedReader(StringReader("4\r\nData\r\n0\r\n\r\n"))
        assertDoesNotThrow { ResourceReader.from(chunkWithTrailingCrlf).readChunkedBody() }

        val endOfStreamMidChunk = BufferedReader(StringReader("5\r\nHel"))
        assertDoesNotThrow { ResourceReader.from(endOfStreamMidChunk).readChunkedBody() }

        val invalidChunkSizeStream = BufferedReader(StringReader("Z\r\nInvalid\r\n0\r\n\r\n"))
        assertThrows(NumberFormatException::class.java)
        { ResourceReader.from(invalidChunkSizeStream).readChunkedBody() }
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val br = mock(BufferedReader::class.java)
        Mockito.`when`(br.toString()).thenReturn("to-string-of-br")
        val reader = ResourceReader.from(br)
        assertEquals("ResourceReader{resourceReader=to-string-of-br}", reader.toString())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val br1 = mock(BufferedReader::class.java)
        val br2 = mock(BufferedReader::class.java)

        val reader1 = ResourceReader.from(br1)
        val equal = ResourceReader.from(br1)
        val notEqual = ResourceReader.from(br2)

        assertEquals(reader1.hashCode(), equal.hashCode())
        assertNotEquals(reader1.hashCode(), notEqual.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val br1 = mock(BufferedReader::class.java)
        val br2 = mock(BufferedReader::class.java)

        val reader1 = ResourceReader.from(br1)
        val equal = ResourceReader.from(br1)
        val notEqual = ResourceReader.from(br2)

        assertEquals(reader1, reader1)
        assertEquals(reader1, equal)
        assertNotEquals(reader1, notEqual)
        assertNotEquals(reader1, Object())
    }
}