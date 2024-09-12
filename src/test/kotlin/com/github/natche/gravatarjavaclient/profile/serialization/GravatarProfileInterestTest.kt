package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

/**
 * Tests for [GravatarProfileInterest]s
 */
class GravatarProfileInterestTest {
    /**
     * Tests for construction.
     */
    @Test
    fun testConstruction() {
        assertThrows(NullPointerException::class.java) { GravatarProfileInterest(0, null) }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileInterest(0, "") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileInterest(0, "  ") }
        assertDoesNotThrow { GravatarProfileInterest(0, "name") }
    }

    /**
     * Tests for accessors.
     */
    @Test
    fun testAccessors() {
        val interest = GravatarProfileInterest(0, "name")
        assertEquals(0, interest.id)
        assertEquals("name", interest.name)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val interest = GravatarProfileInterest(0, "name")
        val otherInterest = GravatarProfileInterest(0, "React")

        assertEquals("GravatarProfileInterest{id=0, name=\"name\"}", interest.toString())
        assertEquals("GravatarProfileInterest{id=0, name=\"React\"}", otherInterest.toString())
    }

    /**
     *Tests for the to hash code method.
     */
    @Test
    fun testHashCode() {
        val interest = GravatarProfileInterest(0, "name")
        val equalInterest = GravatarProfileInterest(0, "name")
        val otherInterest = GravatarProfileInterest(0, "React")

        assertEquals(3373707, interest.hashCode())
        assertEquals(3373707, equalInterest.hashCode())
        assertEquals(78834015, otherInterest.hashCode())
        assertEquals(interest.hashCode(), equalInterest.hashCode())
        assertNotEquals(interest.hashCode(), otherInterest.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val interest = GravatarProfileInterest(0, "name")
        val equalInterest = GravatarProfileInterest(0, "name")
        val differentName = GravatarProfileInterest(0, "React")
        val differentId = GravatarProfileInterest(1, "name")

        assertEquals(interest, interest)
        assertEquals(interest, equalInterest)
        assertNotEquals(interest, differentName)
        assertNotEquals(interest, Object())
        assertNotEquals(interest, differentId)
    }
}