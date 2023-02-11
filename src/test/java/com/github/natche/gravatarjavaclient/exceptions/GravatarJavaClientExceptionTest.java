package com.github.natche.gravatarjavaclient.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for {@link GravatarJavaClientException}s.
 */
public class GravatarJavaClientExceptionTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    public GravatarJavaClientExceptionTest() {}

    /**
     * Tests for creation of exceptions.
     */
    @Test
    void testCreation() {
        assertDoesNotThrow(() -> new GravatarJavaClientException("Exception"));
        assertDoesNotThrow(() -> new GravatarJavaClientException(new Exception("Exception")));
    }
}
