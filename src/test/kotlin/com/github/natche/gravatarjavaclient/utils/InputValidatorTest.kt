package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.ImagesForTests
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for the [InputValidator].
 */
internal class InputValidatorTest {
    /**
     * Tests for the is valid email address method.
     */
    @Test
    fun testIsValidEmailAddress() {
        assertFalse(InputValidator.isValidEmailAddress(null))
        assertFalse(InputValidator.isValidEmailAddress(""))
        assertFalse(InputValidator.isValidEmailAddress("    "))

        assertFalse(InputValidator.isValidEmailAddress("invalid.email.address"))
        assertTrue(InputValidator.isValidEmailAddress("nate.cheshire@me.com"))
        assertTrue(InputValidator.isValidEmailAddress("lady.gaga@proton.me"))
    }

    /**
     * Tests for the is valid default URL method.
     */
    @Test
    fun testIsValidDefaultUrl() {
        assertFalse(InputValidator.isValidImageUrl(null))
        assertFalse(InputValidator.isValidImageUrl(""))
        assertFalse(InputValidator.isValidImageUrl("    "))

        assertTrue(InputValidator.isValidImageUrl("https://google.com"))
        assertTrue(InputValidator.isValidImageUrl("https://google.com/image.png"))
        assertTrue(InputValidator.isValidImageUrl(ImagesForTests.foreignImageUrl))
        assertTrue(InputValidator.isValidImageUrl(ImagesForTests.anotherForeignImageUrl))
    }

    /**
     * Test for the is valid filename method.
     */
    @Test
    fun testIsValidFilename() {
        assertFalse(InputValidator.isValidFilename(null))
        assertFalse(InputValidator.isValidFilename(""))
        assertFalse(InputValidator.isValidFilename("    "))

        assertTrue(InputValidator.isValidFilename("filename"))
        assertTrue(InputValidator.isValidFilename("my_filename_123456789"))
        assertTrue(InputValidator.isValidFilename("my_filename_123456789.txt"))
        assertTrue(InputValidator.isValidFilename("my_filename_123456789...txt"))
        assertFalse(InputValidator.isValidFilename("$%^&*()"))
        assertFalse(InputValidator.isValidFilename("1>:/"))
    }
}