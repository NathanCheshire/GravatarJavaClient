package com.github.natche.gravatarjavaclient.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ValidationUtils}.
 */
public class ValidationUtilsTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    public ValidationUtilsTest() {}

    /**
     * Tests for ths is valid email address method.
     */
    @Test
    void testIsValidEmailAddress() {
        assertThrows(NullPointerException.class, ()-> ValidationUtils.isValidEmailAddress(null));
        assertThrows(IllegalArgumentException.class, ()-> ValidationUtils.isValidEmailAddress(""));

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
        assertThrows(NullPointerException.class, ()-> ValidationUtils.isValidDefaultUrl(null));
        assertThrows(IllegalArgumentException.class, ()-> ValidationUtils.isValidDefaultUrl(""));

        assertFalse(ValidationUtils.isValidDefaultUrl("email"));
        assertFalse(ValidationUtils.isValidDefaultUrl("url"));
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.google.com"));
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.youtube.com"));
        assertFalse(ValidationUtils.isValidDefaultUrl("https://www.dailymotion.com/video/x6tjilq"));
        assertTrue(ValidationUtils.isValidDefaultUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png"));
    }
}
