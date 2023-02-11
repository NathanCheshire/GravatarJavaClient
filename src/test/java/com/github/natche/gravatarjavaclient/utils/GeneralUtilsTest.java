package com.github.natche.gravatarjavaclient.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GeneralUtils}.
 */
public class GeneralUtilsTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    public GeneralUtilsTest() {}

    /**
     * Tests for the email address to gravatar hash method.
     */
    @Test
    void testEmailAddressToGravatarHash() {
        assertThrows(NullPointerException.class, () -> GeneralUtils.emailAddressToGravatarHash(null));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash(""));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash("asdf"));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash("invalid"));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash("invalid@asdf"));

        assertDoesNotThrow(() -> GeneralUtils.emailAddressToGravatarHash("valid@domain.com"));
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7",
                GeneralUtils.emailAddressToGravatarHash("nathan.vincent.2.718@gmail.com"));
        assertEquals("21bce028aab22e8c9ec03d66305a50dc",
                GeneralUtils.emailAddressToGravatarHash("nathan.cheshire.ctr@nrlssc.navy.mil"));
        assertEquals("e11e6bd8201d3bd6f22c4b206a863a2c",
                GeneralUtils.emailAddressToGravatarHash("ncheshire@camgian.com"));
    }
}
