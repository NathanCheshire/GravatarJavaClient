package com.github.natche.gravatarjavaclient.profile

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import com.google.common.base.Preconditions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.IllegalArgumentException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * Tests for the [GravatarProfileRequestHandler].
 */
class GravatarProfileRequestHandlerTest {
    /**
     * Tests to ensure a non-existent hash returns the error message from the API.
     */
    @Test
    fun testInvalidHashReturnsErrorMessage() {
        val exception = assertThrows(GravatarJavaClientException::class.java) {
            GravatarProfileRequestHandler.INSTANCE.getProfile(ByteArray(0), "hash")
        }

        assertEquals("API error: Profile not found", exception.message)
    }

    /**
     * Tests for the [Preconditions] on the getProfile method.
     */
    @Test
    fun testPreconditions() {
        assertThrows(NullPointerException::class.java)
        { GravatarProfileRequestHandler.INSTANCE.getProfile(ByteArray(0), null) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarProfileRequestHandler.INSTANCE.getProfile(ByteArray(0), "") }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarProfileRequestHandler.INSTANCE.getProfile(ByteArray(0), "  ") }
    }

    /**
     * Tests to ensure any header chunks are skipped.
     */
    @Test
    fun testHeadersAreSkipped() {
        val bearerToken = "sampleBearerToken".toByteArray()
        val nameOrHash = "nathanvcheshire"

        // Mock SSLSocket and input stream for the response
        val socketMock = mock(SSLSocket::class.java)
        val factoryMock = mock(SSLSocketFactory::class.java)
        val inputStreamMock = mock(InputStream::class.java)
        val outputStreamMock = ByteArrayOutputStream()

        // Simulate headers followed by the response body
        val simulatedResponse = """
            HTTP/1.1 200 OK
            Date: Mon, 11 Sep 2024 12:00:00 GMT
            Content-Type: application/json
            Transfer-Encoding: chunked

            1A
            {"profile": "sampleProfileData"}
            0

        """.trimIndent()

        // Create input stream to simulate the response
        `when`(inputStreamMock.read(any(ByteArray::class.java), anyInt(), anyInt())).thenAnswer { invocation ->
            val buffer = invocation.arguments[0] as ByteArray
            val offset = invocation.arguments[1] as Int
            val length = invocation.arguments[2] as Int
            val responseBytes = simulatedResponse.toByteArray()
            val bytesToRead = minOf(length, responseBytes.size)
            System.arraycopy(responseBytes, 0, buffer, offset, bytesToRead)
            bytesToRead
        }

        // Set up SSLSocket to return the mocked input and output streams
        `when`(socketMock.inputStream).thenReturn(inputStreamMock)
        `when`(socketMock.outputStream).thenReturn(outputStreamMock)

        // Set up SSLSocketFactory to return the mocked socket
        `when`(factoryMock.createSocket(anyString(), anyInt())).thenReturn(socketMock)

        val profile = GravatarProfileRequestHandler.INSTANCE.getProfile(bearerToken, nameOrHash)

        assertTrue( profile.toString()
            .contains("c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168"))
    }
}