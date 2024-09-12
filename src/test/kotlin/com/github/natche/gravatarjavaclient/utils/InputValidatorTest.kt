package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.TestingImageUrls
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for the [InputValidator].
 */
internal class InputValidatorTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java) { InputValidator.from(null) }
        assertThrows(IllegalArgumentException::class.java) { InputValidator.from("") }
        assertThrows(IllegalArgumentException::class.java) { InputValidator.from("  ") }

        assertDoesNotThrow { InputValidator.from("invalid.email.address") }
        assertDoesNotThrow { InputValidator.from("https://bad-image-url.png") }
    }

    /**
     *
     */
    @Test
    fun testIsValidEmailAddress() {
        assertFalse(InputValidator.from("invalid.email.address").isValidEmailAddress)
        assertTrue(InputValidator.from("nate.cheshire@me.com").isValidEmailAddress)
        assertTrue(InputValidator.from("lady.gaga@proton.me").isValidEmailAddress)
    }

    /**
     *
     */
    @Test
    fun testIsValidDefaultUrl() {
        assertFalse(InputValidator.from("https://google.com").isValidImageUrl)
        assertFalse(InputValidator.from("https://google.com/image.png").isValidImageUrl)
        assertTrue(InputValidator.from(TestingImageUrls.foreignImageUrl).isValidImageUrl)
        assertTrue(InputValidator.from(TestingImageUrls.anotherForeignImageUrl).isValidImageUrl)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        assertEquals("InputValidator{input=\"me\"}", InputValidator.from("me").toString())
        assertEquals(
            "InputValidator{input=\"other input\"}",
            InputValidator.from("other input").toString()
        )
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val first = InputValidator.from("input")
        val equal = InputValidator.from("input")
        val notEqual = InputValidator.from("other input")

        assertEquals(-1183866506, first.hashCode())
        assertEquals(-1183866506, equal.hashCode())
        assertEquals(586840198, notEqual.hashCode())
        assertEquals(first.hashCode(), equal.hashCode())
        assertNotEquals(first.hashCode(), notEqual.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val first = InputValidator.from("input")
        val equal = InputValidator.from("input")
        val notEqual = InputValidator.from("other input")

        assertEquals(first, first)
        assertEquals(first, equal)
        assertNotEquals(first, notEqual)
        assertNotEquals(first, Object())
    }
}