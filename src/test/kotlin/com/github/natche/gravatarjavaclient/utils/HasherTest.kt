package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Tests for the [Hasher].
 */
class HasherTest {
    /**
     * Tests for the hash method.
     */
    @Test
    fun testHash() {
        assertEquals("be1803fb9747925629633675c707d9f87c5d02bcf8aa0fb91d92b98f0a0bfb8d", Hasher.SHA256.hash("GravatarJavaClient"))
    }
}