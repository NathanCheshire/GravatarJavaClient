package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Test for [GravatarProfileContactInfo] objects.
 */
class GravatarProfileContactInfoTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java) {
            GravatarProfileContactInfo(
                null, null, null, null, null, null
            )
        }
        assertThrows(NullPointerException::class.java) {
            GravatarProfileContactInfo(
                "", null, null, null, null, null
            )
        }
        assertThrows(NullPointerException::class.java) {
            GravatarProfileContactInfo(
                "", "", null, null, null, null
            )
        }
        assertThrows(NullPointerException::class.java) {
            GravatarProfileContactInfo(
                "", "", "", null, null, null
            )
        }
        assertThrows(NullPointerException::class.java) {
            GravatarProfileContactInfo(
                "", "", "", "", null, null
            )
        }
        assertThrows(NullPointerException::class.java) {
            GravatarProfileContactInfo(
                "", "", "", "", "", null
            )
        }
        assertDoesNotThrow {
            GravatarProfileContactInfo(
                "", "", "", "", "", ""
            )
        }
    }

    /**
     *
     */
    @Test
    fun testAccessors() {
        val contactInfo = GravatarProfileContactInfo(
            "homePhone", "workPhone",
            "cellPhone", "email", "contactForm", "calendar"
        )
        assertEquals("homePhone", contactInfo.homePhone)
        assertEquals("workPhone", contactInfo.workPhone)
        assertEquals("cellPhone", contactInfo.cellPhone)
        assertEquals("email", contactInfo.email)
        assertEquals("contactForm", contactInfo.contactForm)
        assertEquals("calendar", contactInfo.calendar)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val contactInfo = GravatarProfileContactInfo(
            "homePhone", "workPhone",
            "cellPhone", "email", "contactForm", "calendar"
        )
        val otherContactInfo = GravatarProfileContactInfo(
            "5150", "143",
            "443", "mgk", "hollyWoodMethod", "doNotEvenTryItKid"
        )

        assertEquals(
            "GravatarProfileContactInfo{homePhone=\"homePhone\", workPhone=\"workPhone\","
                    + " cellPhone=\"cellPhone\", email=\"email\", contactForm=\"contactForm\","
                    + " calendar=\"calendar\"}", contactInfo.toString()
        )
        assertEquals(
            "GravatarProfileContactInfo{homePhone=\"5150\", workPhone=\"143\","
                    + " cellPhone=\"443\", email=\"mgk\", contactForm=\"hollyWoodMethod\","
                    + " calendar=\"doNotEvenTryItKid\"}", otherContactInfo.toString()
        )
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val contactInfo = GravatarProfileContactInfo(
            "homePhone", "workPhone",
            "cellPhone", "email", "contactForm", "calendar"
        )
        val equal = GravatarProfileContactInfo(
            "homePhone", "workPhone",
            "cellPhone", "email", "contactForm", "calendar"
        )
        val notEqual = GravatarProfileContactInfo(
            "5150", "143",
            "443", "mgk", "hollyWoodMethod", "doNotEvenTryItKid"
        )

        assertEquals(contactInfo, contactInfo)
        assertEquals(contactInfo, equal)
        assertNotEquals(contactInfo, notEqual)
        assertNotEquals(contactInfo, Object())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val contactInfo = GravatarProfileContactInfo(
            "homePhone", "workPhone",
            "cellPhone", "email", "contactForm", "calendar"
        )
        val equal = GravatarProfileContactInfo(
            "homePhone", "workPhone",
            "cellPhone", "email", "contactForm", "calendar"
        )
        val notEqual = GravatarProfileContactInfo(
            "5150", "143",
            "443", "mgk", "hollyWoodMethod", "doNotEvenTryItKid"
        )

        assertEquals(-138872168, contactInfo.hashCode())
        assertEquals(-138872168, equal.hashCode())
        assertEquals(-776969223, notEqual.hashCode())
        assertEquals(contactInfo.hashCode(), equal.hashCode())
        assertNotEquals(contactInfo.hashCode(), notEqual.hashCode())
    }
}