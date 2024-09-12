package com.github.natche.gravatarjavaclient.profile

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import com.google.common.base.Preconditions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

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
}