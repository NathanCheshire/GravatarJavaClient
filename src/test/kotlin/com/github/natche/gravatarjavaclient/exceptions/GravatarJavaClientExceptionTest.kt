package com.github.natche.gravatarjavaclient.exceptions

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.IOException

/**
 * Tests for [GravatarJavaClientException]s.
 */
internal class GravatarJavaClientExceptionTest {
    /**
     * Tests for creation of exceptions.
     */
    @Test
    fun testCreation() {
        assertDoesNotThrow { GravatarJavaClientException("Exception") }
        assertDoesNotThrow { GravatarJavaClientException(Exception("Exception")) }
        assertThrows(GravatarJavaClientException::class.java) { throw GravatarJavaClientException("Exception") }
        assertThrows(GravatarJavaClientException::class.java) {
            throw GravatarJavaClientException(
                IOException(
                    "Exception"
                )
            )
        }
    }
}