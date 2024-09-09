package com.github.natche.gravatarjavaclient.exceptions;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link GravatarJavaClientException}s.
 */
class GravatarJavaClientExceptionTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    GravatarJavaClientExceptionTest() {}

    /**
     * Tests for creation of exceptions.
     */
    @Test
    void testCreation() {
        assertDoesNotThrow(() -> new GravatarJavaClientException("Exception"));
        assertDoesNotThrow(() -> new GravatarJavaClientException(new Exception("Exception")));
        assertThrows(GravatarJavaClientException.class, () -> {
            throw new GravatarJavaClientException("Exception");
        });
        assertThrows(GravatarJavaClientException.class, () -> {
            throw new GravatarJavaClientException(new IOException("Exception"));
        });
    }
}
