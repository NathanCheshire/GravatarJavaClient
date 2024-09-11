package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.TestingImageUrls
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.reflect.InvocationTargetException

/**
 * Tests for the [ValidationUtils].
 */
internal class ValidationUtilsTest {
    /**
     * Tests to ensure reflection is guarded against.
     * Also helps reach 100% code coverage for testing.
     */
    @Test
    fun testCreation() {
        try {
            val constructor = ValidationUtils::class.java.getDeclaredConstructor()
            constructor.isAccessible = true
            constructor.newInstance()
        } catch (e: Exception) {
            assertTrue(e is InvocationTargetException)
            val target = (e as InvocationTargetException).targetException
            assertTrue(target is AssertionError)
            assertEquals("Cannot create instances of ValidationUtils", target.message)
        }
    }

    /**
     * Tests for ths is valid email address method.
     */
    @Test
    fun testIsValidEmailAddress() {
        assertThrows(NullPointerException::class.java) { ValidationUtils.isValidEmailAddress(null) }
        assertThrows(IllegalArgumentException::class.java) { ValidationUtils.isValidEmailAddress("") }
        assertFalse(ValidationUtils.isValidEmailAddress("a"))
        assertFalse(ValidationUtils.isValidEmailAddress("email"))
        assertFalse(ValidationUtils.isValidEmailAddress("something@"))
        assertFalse(ValidationUtils.isValidEmailAddress("@"))
        assertFalse(ValidationUtils.isValidEmailAddress("@gmail.com"))
        assertFalse(ValidationUtils.isValidEmailAddress("gmail.com"))
        assertFalse(ValidationUtils.isValidEmailAddress("invalid at gmail.com"))
        assertTrue(ValidationUtils.isValidEmailAddress("valid@domain.com"))
    }

    /**
     * Tests for the is valid default url method.
     */
    @Test
    fun testIsValidDefaultUrl() {
        assertThrows(NullPointerException::class.java) { ValidationUtils.isValidDefaultUrl(null) }
        assertThrows(IllegalArgumentException::class.java) { ValidationUtils.isValidDefaultUrl("") }
        assertFalse(ValidationUtils.isValidDefaultUrl("email"))
        assertFalse(ValidationUtils.isValidDefaultUrl("url"))
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.google.com"))
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.youtube.com"))
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.dailymotion.com/video/some-id"))
        assertTrue(ValidationUtils.isValidDefaultUrl(TestingImageUrls.foreignImageUrl))
    }
}