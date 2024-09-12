package com.github.natche.gravatarjavaclient.profile

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets

/**
 * Tests for [GravatarProfileTokenProvider]s.
 */
class GravatarProfileTokenProviderTest internal constructor() {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(
            NullPointerException::class.java
        ) { GravatarProfileTokenProvider(null, null) }
        assertThrows(
            NullPointerException::class.java
        ) { GravatarProfileTokenProvider({ ByteArray(0) }, null) }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarProfileTokenProvider({ ByteArray(0) }, "") }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarProfileTokenProvider({ ByteArray(0) }, "    ") }
        assertDoesNotThrow {
            GravatarProfileTokenProvider(
                { ByteArray(0) }, "source"
            )
        }
    }

    /**
     * Tests for the get token method.
     */
    @Test
    fun testGetToken() {
        val bytes = "PostMalone".toByteArray(StandardCharsets.US_ASCII)
        val provider = GravatarProfileTokenProvider({ bytes }, "source")
        assertEquals(bytes, provider.token)
    }

    /**
     * Test for the to string method.
     */
    @Test
    fun testToString() {
        val bytes = "PostMalone".toByteArray(StandardCharsets.US_ASCII)
        val provider = GravatarProfileTokenProvider({ bytes }, "source")
        val nonEqualProvider = GravatarProfileTokenProvider({ ByteArray(0) }, "another source")
        assertEquals("GravatarProfileTokenProvider{source=\"source\"}", provider.toString())
        assertEquals(
            "GravatarProfileTokenProvider{source=\"another source\"}",
            nonEqualProvider.toString()
        )
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val bytes = "PostMalone".toByteArray(StandardCharsets.US_ASCII)
        val provider = GravatarProfileTokenProvider({ bytes }, "source")
        val equalProvider = GravatarProfileTokenProvider({ bytes }, "source")
        val nonEqualProvider = GravatarProfileTokenProvider({ ByteArray(0) }, "another source")
        assertEquals(provider, provider)
        assertEquals(provider, equalProvider)
        assertNotEquals(provider, nonEqualProvider)
        assertNotEquals(provider, Any())
    }

    /**
     * Tests for the hashcode method.
     */
    @Test
    fun testHashCode() {
        val bytes = "PostMalone".toByteArray(StandardCharsets.US_ASCII)
        val provider = GravatarProfileTokenProvider({ bytes }, "source")
        val equalProvider = GravatarProfileTokenProvider({ bytes }, "source")
        val nonEqualProvider = GravatarProfileTokenProvider({ ByteArray(0) }, "another source")
        assertEquals(-896505829, provider.hashCode())
        assertEquals(-896505829, equalProvider.hashCode())
        assertEquals(1165062104, nonEqualProvider.hashCode())
        assertNotEquals(equalProvider.hashCode(), nonEqualProvider.hashCode())
    }
}