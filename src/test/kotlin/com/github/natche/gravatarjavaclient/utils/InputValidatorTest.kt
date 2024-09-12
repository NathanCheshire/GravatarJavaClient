package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.TestingImageUrls
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

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

        assertDoesNotThrow {InputValidator.from("invalid.email.address")}
        assertDoesNotThrow {InputValidator.from("https://bad-image-url.png")}
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
}