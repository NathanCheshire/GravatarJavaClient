package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.Instant

/**
 * Tests for [GravatarProfileRequestResult]s.
 */
class GravatarProfileRequestResultTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java)
        { GravatarProfileRequestResult(null, false) }
        assertDoesNotThrow { GravatarProfileRequestResult(Instant.now(), false) }
        assertDoesNotThrow { GravatarProfileRequestResult(Instant.now(), true) }
    }

    /**
     * Tests for accessors.
     */
    @Test
    fun testAccessors() {
        val firstInstant = Instant.now()
        val resultOne = GravatarProfileRequestResult(firstInstant, true)
        val resultTwo = GravatarProfileRequestResult(firstInstant, false)

        assertEquals(firstInstant, resultOne.requestInstant)
        assertEquals(firstInstant, resultTwo.requestInstant)
        assertTrue(resultOne.succeeded())
        assertFalse(resultOne.failed())
        assertFalse(resultTwo.succeeded())
        assertTrue(resultTwo.failed())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val firstInstant = Instant.now()
        val resultOne = GravatarProfileRequestResult(firstInstant, true)
        val equal = GravatarProfileRequestResult(firstInstant, true)
        val differentInstant = GravatarProfileRequestResult(Instant.now(), true)

        try {
            Thread.sleep(500)
        } catch (e: Exception) {
            // Swallow
        }

        val differentSucceeded = GravatarProfileRequestResult(firstInstant, false)

        assertEquals(resultOne, resultOne)
        assertEquals(resultOne, equal)
        assertNotEquals(resultOne, differentInstant)
        assertNotEquals(resultOne, differentSucceeded)
        assertNotEquals(resultOne, Object())
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val firstInstant = Instant.now()
        val resultOne = GravatarProfileRequestResult(firstInstant, true)
        val resultTwo = GravatarProfileRequestResult(firstInstant, false)

        assertEquals(
            "GravatarProfileRequestResult{requestInstant=$firstInstant,"
                    + " succeeded=true}", resultOne.toString()
        )
        assertEquals(
            "GravatarProfileRequestResult{requestInstant=$firstInstant,"
                    + " succeeded=false}", resultTwo.toString()
        )
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val firstInstant = Instant.now()
        val resultOne = GravatarProfileRequestResult(firstInstant, true)
        val equalToResultOne = GravatarProfileRequestResult(firstInstant, true)
        val resultTwo = GravatarProfileRequestResult(firstInstant, false)

        assertEquals(resultOne.hashCode(), equalToResultOne.hashCode())
        assertNotEquals(resultOne.hashCode(), resultTwo.hashCode())
    }
}