package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.TestingImageUrls
import org.junit.jupiter.api.Assertions
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
            Assertions.assertTrue(e is InvocationTargetException)
            val target = (e as InvocationTargetException).targetException
            Assertions.assertTrue(target is AssertionError)
            Assertions.assertEquals("Cannot create instances of ValidationUtils", target.message)
        }
    }

    /**
     * Tests for ths is valid email address method.
     */
    @Test
    fun testIsValidEmailAddress() {
        Assertions.assertThrows(NullPointerException::class.java) { ValidationUtils.isValidEmailAddress(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java) { ValidationUtils.isValidEmailAddress("") }
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("a"))
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("email"))
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("something@"))
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("@"))
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("@gmail.com"))
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("gmail.com"))
        Assertions.assertFalse(ValidationUtils.isValidEmailAddress("invalid at gmail.com"))
        Assertions.assertTrue(ValidationUtils.isValidEmailAddress("valid@domain.com"))
    }

    /**
     * Tests for the is valid default url method.
     */
    @Test
    fun testIsValidDefaultUrl() {
        Assertions.assertThrows(NullPointerException::class.java) { ValidationUtils.isValidDefaultUrl(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java) { ValidationUtils.isValidDefaultUrl("") }
        Assertions.assertFalse(ValidationUtils.isValidDefaultUrl("email"))
        Assertions.assertFalse(ValidationUtils.isValidDefaultUrl("url"))
        Assertions.assertFalse(ValidationUtils.isValidDefaultUrl("https://www.google.com"))
        Assertions.assertFalse(ValidationUtils.isValidDefaultUrl("https://www.youtube.com"))
        Assertions.assertFalse(ValidationUtils.isValidDefaultUrl("https://www.dailymotion.com/video/some-id"))
        Assertions.assertTrue(ValidationUtils.isValidDefaultUrl(TestingImageUrls.foreignImageUrl))
    }
}