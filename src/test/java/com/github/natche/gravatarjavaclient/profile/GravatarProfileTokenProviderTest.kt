package com.github.natche.gravatarjavaclient.profile

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets

/**
 * Tests for [GravatarProfileTokenProvider]s.
 */
class GravatarProfileTokenProviderTest
/**
 * Creates a new instance for testing purposes.
 */
internal constructor() {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        Assertions.assertThrows(
            NullPointerException::class.java
        ) { GravatarProfileTokenProvider(null, null) }
        Assertions.assertThrows(
            NullPointerException::class.java
        ) { GravatarProfileTokenProvider({ ByteArray(0) }, null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarProfileTokenProvider({ ByteArray(0) }, "") }
        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarProfileTokenProvider({ ByteArray(0) }, "    ") }
        Assertions.assertDoesNotThrow<GravatarProfileTokenProvider> {
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
        Assertions.assertEquals(bytes, provider.token)
    }

    /**
     * Test for the to string method.
     */
    @Test
    fun testToString() {
        val bytes = "PostMalone".toByteArray(StandardCharsets.US_ASCII)
        val provider = GravatarProfileTokenProvider({ bytes }, "source")
        val nonEqualProvider = GravatarProfileTokenProvider({ ByteArray(0) }, "another source")
        Assertions.assertEquals("GravatarProfileTokenProvider{source=\"source\"}", provider.toString())
        Assertions.assertEquals("GravatarProfileTokenProvider{source=\"another source\"}",
            nonEqualProvider.toString())
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
        Assertions.assertEquals(provider, provider)
        Assertions.assertEquals(provider, equalProvider)
        Assertions.assertNotEquals(provider, nonEqualProvider)
        Assertions.assertNotEquals(provider, Any())
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
        Assertions.assertEquals(-896505829, provider.hashCode())
        Assertions.assertEquals(-896505829, equalProvider.hashCode())
        Assertions.assertEquals(1165062104, nonEqualProvider.hashCode())
        Assertions.assertNotEquals(equalProvider.hashCode(), nonEqualProvider.hashCode())
    }
}