package com.github.natche.gravatarjavaclient.profile;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GravatarProfileTokenProvider}s.
 */
public class GravatarProfileTokenProviderTest {
    /**
     * Creates a new instance for testing purposes.
     */
    GravatarProfileTokenProviderTest() {}

    /**
     *
     */
    @Test
    void testCreation() {
        assertThrows(NullPointerException.class,
                () -> new GravatarProfileTokenProvider(null, null));
        assertThrows(NullPointerException.class,
                () -> new GravatarProfileTokenProvider(() -> new byte[0], null));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarProfileTokenProvider(() -> new byte[0], ""));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarProfileTokenProvider(() -> new byte[0], "    "));
        assertDoesNotThrow(
                () -> new GravatarProfileTokenProvider(() -> new byte[0], "source"));
    }

    /**
     *
     */
    @Test
    void testGetToken() {
        byte[] bytes = "PostMalone".getBytes(StandardCharsets.US_ASCII);
        GravatarProfileTokenProvider provider = new GravatarProfileTokenProvider(() -> bytes, "source");
        assertEquals(bytes, provider.getToken());
    }

    /**
     *
     */
    @Test
    void testToString() {
        byte[] bytes = "PostMalone".getBytes(StandardCharsets.US_ASCII);
        GravatarProfileTokenProvider provider = new GravatarProfileTokenProvider(() -> bytes, "source");
        GravatarProfileTokenProvider nonEqualProvider
                = new GravatarProfileTokenProvider(() -> new byte[0], "another source");

        assertEquals("GravatarProfileTokenProvider{source=\"source\"}", provider.toString());
        assertEquals("GravatarProfileTokenProvider{source=\"another source\"}", nonEqualProvider.toString());
    }

    /**
     *
     */
    @Test
    void testEquals() {
        byte[] bytes = "PostMalone".getBytes(StandardCharsets.US_ASCII);
        GravatarProfileTokenProvider provider = new GravatarProfileTokenProvider(() -> bytes, "source");
        GravatarProfileTokenProvider equalProvider = new GravatarProfileTokenProvider(() -> bytes, "source");
        GravatarProfileTokenProvider nonEqualProvider
                = new GravatarProfileTokenProvider(() -> new byte[0], "another source");

        assertEquals(provider, provider);
        assertEquals(provider, equalProvider);
        assertNotEquals(provider, nonEqualProvider);
        assertNotEquals(provider, new Object());
    }

    /**
     *
     */
    @Test
    void testHashCode() {
        byte[] bytes = "PostMalone".getBytes(StandardCharsets.US_ASCII);
        GravatarProfileTokenProvider provider = new GravatarProfileTokenProvider(() -> bytes, "source");
        GravatarProfileTokenProvider equalProvider = new GravatarProfileTokenProvider(() -> bytes, "source");
        GravatarProfileTokenProvider nonEqualProvider
                = new GravatarProfileTokenProvider(() -> new byte[0], "another source");

        assertEquals(-896505829, provider.hashCode());
        assertEquals(-896505829, equalProvider.hashCode());
        assertEquals(1165062104, nonEqualProvider.hashCode());
        assertNotEquals(equalProvider.hashCode(), nonEqualProvider.hashCode());
    }
}
