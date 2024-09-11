package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

/**
 * Tests for [GravatarProfileLanguage]s.
 */
class GravatarProfileLanguageTest {
    /**
     * Tests for construction.
     */
    @Test
    fun testConstruction() {
        assertThrows(NullPointerException::class.java)
        { GravatarProfileLanguage(null, null, false, 0) }
        assertThrows(NullPointerException::class.java)
        { GravatarProfileLanguage("", null, false, 0) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarProfileLanguage("", "", false, 0) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarProfileLanguage("en", "", false, 0) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarProfileLanguage("", "name", false, 0) }

        assertDoesNotThrow { GravatarProfileLanguage("en", "English", false, 0) }
    }

    /**
     * Tests for the accessors.
     */
    @Test
    fun testAccessors() {
        val profile = GravatarProfileLanguage("en", "English", true, 69)
        assertEquals("en", profile.code)
        assertEquals("English", profile.name)
        assertTrue(profile.isPrimary)
        assertEquals(69, profile.order)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val profile = GravatarProfileLanguage("en", "English", true, 69)
        val otherProfile = GravatarProfileLanguage("de", "German", false, 2)

        assertEquals("GravatarProfileLanguage{code=\"en\", name=\"English\","
                + " isPrimary=true, order=69}", profile.toString())
        assertEquals("GravatarProfileLanguage{code=\"de\", name=\"German\","
                + " isPrimary=false, order=2}", otherProfile.toString())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val profile = GravatarProfileLanguage("en", "English", true, 69)
        val equalProfile = GravatarProfileLanguage("en", "English", true, 69)
        val differentCode = GravatarProfileLanguage("de", "English", true, 69)
        val differentName = GravatarProfileLanguage("en", "German", true, 69)
        val differentPrimary = GravatarProfileLanguage("en", "English", false, 69)
        val differentOrder = GravatarProfileLanguage("en", "English", true, 2)

        assertEquals(profile, profile)
        assertEquals(profile, equalProfile)
        assertNotEquals(profile, differentCode)
        assertNotEquals(profile, differentName)
        assertNotEquals(profile, differentPrimary)
        assertNotEquals(profile, differentOrder)
        assertNotEquals(profile, Object())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashcode() {
        val profile = GravatarProfileLanguage("en", "English", true, 69)
        val equalProfile = GravatarProfileLanguage("en", "English", true, 69)
        val differentCode = GravatarProfileLanguage("de", "English", true, 69)

        assertEquals(-1512064419, profile.hashCode())
        assertEquals(-1512064419, equalProfile.hashCode())
        assertEquals(-1513256059, differentCode.hashCode())
        assertEquals(profile.hashCode(), equalProfile.hashCode())
        assertNotEquals(profile.hashCode(), differentCode.hashCode())
    }
}