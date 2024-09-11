package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for [GravatarProfileVerifiedAccount]s.
 */
class GravatarProfileVerifiedAccountTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java)
        { GravatarProfileVerifiedAccount(null, null, null, null) }
        assertThrows(NullPointerException::class.java)
        { GravatarProfileVerifiedAccount("", null, null, null) }
        assertThrows(NullPointerException::class.java)
        { GravatarProfileVerifiedAccount("", "", null, null) }
        assertThrows(NullPointerException::class.java)
        { GravatarProfileVerifiedAccount("", "", "", null) }

        assertDoesNotThrow { GravatarProfileVerifiedAccount("", "", "", "") }
    }

    /**
     * Tests for the accessors.
     */
    @Test
    fun testAccessors() {
        val account = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "url")

        assertEquals("type", account.serviceType)
        assertEquals("label", account.serviceLabel)
        assertEquals("icon", account.serviceIcon)
        assertEquals("url", account.url)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val account = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "url")

        assertEquals("GravatarProfileVerifiedAccount{serviceType=\"type\", "
                + "serviceLabel=\"label\", serviceIcon=\"icon\", url=\"url\"}", account.toString())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val account = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "url")
        val equal = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "url")
        val differentType = GravatarProfileVerifiedAccount(
            "different type", "label", "icon", "url")

        assertEquals(-816244592, account.hashCode())
        assertEquals(-816244592, equal.hashCode())
        assertEquals(168868777, differentType.hashCode())
        assertEquals(account.hashCode(), equal.hashCode())
        assertNotEquals(account.hashCode(), differentType.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val account = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "url")
        val equal = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "url")
        val differentType = GravatarProfileVerifiedAccount(
            "different type", "label", "icon", "url")
        val differentLabel = GravatarProfileVerifiedAccount(
            "type", "different label", "icon", "url")
        val differentIcon = GravatarProfileVerifiedAccount(
            "type", "label", "different icon", "url")
        val differentUrl = GravatarProfileVerifiedAccount(
            "type", "label", "icon", "different url")

        assertEquals(account, account)
        assertEquals(account, equal)
        assertNotEquals(equal, differentType)
        assertNotEquals(equal, differentLabel)
        assertNotEquals(equal, differentIcon)
        assertNotEquals(equal, differentUrl)
        assertNotEquals(equal, Object())
    }
}