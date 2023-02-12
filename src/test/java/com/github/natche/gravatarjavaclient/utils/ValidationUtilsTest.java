package com.github.natche.gravatarjavaclient.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ValidationUtils}.
 */
class ValidationUtilsTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    ValidationUtilsTest() {}

    /**
     * Tests to ensure reflection is guarded against.
     * Also helps reach 100% code coverage for testing.
     */
    @Test
    void testCreation() {
        try {
            Constructor<ValidationUtils> constructor =
                    ValidationUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (Exception e) {
            assertTrue(e instanceof InvocationTargetException);
            Throwable target = ((InvocationTargetException) e).getTargetException();
            assertTrue(target instanceof AssertionError);
            assertEquals("Cannot create instances of ValidationUtils", target.getMessage());
        }
    }

    /**
     * Tests for ths is valid email address method.
     */
    @Test
    void testIsValidEmailAddress() {
        assertThrows(NullPointerException.class, () -> ValidationUtils.isValidEmailAddress(null));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.isValidEmailAddress(""));

        assertFalse(ValidationUtils.isValidEmailAddress("a"));
        assertFalse(ValidationUtils.isValidEmailAddress("email"));
        assertFalse(ValidationUtils.isValidEmailAddress("something@"));
        assertFalse(ValidationUtils.isValidEmailAddress("@"));
        assertFalse(ValidationUtils.isValidEmailAddress("@gmail.com"));
        assertFalse(ValidationUtils.isValidEmailAddress("gmail.com"));
        assertFalse(ValidationUtils.isValidEmailAddress("invalid at gmail.com"));

        assertTrue(ValidationUtils.isValidEmailAddress("valid@domain.com"));
    }

    /**
     * Tests for the is valid default url method.
     */
    @Test
    void testIsValidDefaultUrl() {
        assertThrows(NullPointerException.class, () -> ValidationUtils.isValidDefaultUrl(null));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.isValidDefaultUrl(""));

        assertFalse(ValidationUtils.isValidDefaultUrl("email"));
        assertFalse(ValidationUtils.isValidDefaultUrl("url"));
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.google.com"));
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.youtube.com"));
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.dailymotion.com/video/x6tjilq"));
        assertTrue(ValidationUtils.isValidDefaultUrl(
                "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png"));
    }
}
