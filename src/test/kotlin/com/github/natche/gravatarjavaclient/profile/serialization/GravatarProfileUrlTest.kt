package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for [GravatarProfileUrl]s.
 */
class GravatarProfileUrlTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java) { GravatarProfileUrl(null, null) }
        assertThrows(NullPointerException::class.java) { GravatarProfileUrl("", null) }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileUrl("", "") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileUrl("   ", "") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileUrl("label", "") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileUrl("label", "   ") }
        assertDoesNotThrow { GravatarProfileUrl("label", "url") }
    }

    /**
     * Tests for the accessors.
     */
    @Test
    fun testAccessors() {
        val url = GravatarProfileUrl("label", "url")
        assertEquals("label", url.label)
        assertEquals("url", url.url)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val url = GravatarProfileUrl("label", "url")
        assertEquals("GravatarProfileUrl{label=\"label\", url=\"url\"}", url.toString())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val url = GravatarProfileUrl("label", "url")
        val equal = GravatarProfileUrl("label", "url")
        val notEqual = GravatarProfileUrl("other label", "url")

        assertEquals(-1110301445, url.hashCode())
        assertEquals(-1110301445, equal.hashCode())
        assertEquals(660405259, notEqual.hashCode())
        assertEquals(url.hashCode(), equal.hashCode())
        assertNotEquals(url.hashCode(), notEqual.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val url = GravatarProfileUrl("label", "url")
        val equal = GravatarProfileUrl("label", "url")
        val differentUrl = GravatarProfileUrl("label", "other url")
        val differentLabel = GravatarProfileUrl("other label", "url")

        assertEquals(url, url)
        assertEquals(url, equal)
        assertNotEquals(url, differentUrl)
        assertNotEquals(url, differentLabel)
        assertNotEquals(url, Object())
    }
}