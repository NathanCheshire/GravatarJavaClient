package com.github.natche.gravatarjavaclient.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GravatarProfilePhoto}s.
 */
class GravatarProfilePhotoTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    public GravatarProfilePhotoTest() {}

    /**
     * Tests for creation of Gravatar profile photos.
     */
    @Test
    void testCreation() {
        assertThrows(NullPointerException.class, () -> new GravatarProfilePhoto(null, null));
        assertThrows(NullPointerException.class, () -> new GravatarProfilePhoto("", null));
        assertThrows(IllegalArgumentException.class, () -> new GravatarProfilePhoto("", ""));
        assertThrows(IllegalArgumentException.class, () -> new GravatarProfilePhoto("type", ""));
        assertDoesNotThrow(() -> new GravatarProfilePhoto("type", "link"));
    }

    /**
     * Tests for the accessor and mutator methods.
     */
    @Test
    void testAccessorsMutators() {
        GravatarProfilePhoto photo = new GravatarProfilePhoto("type", "link");
        assertEquals("type", photo.getType());
        assertEquals("link", photo.getLink());
    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {
        GravatarProfilePhoto photo = new GravatarProfilePhoto("type", "link");
        assertEquals("GravatarProfilePhoto{type=\"type\", link=\"link\"}", photo.toString());

        GravatarProfilePhoto myGitHub = new GravatarProfilePhoto("thumbnail", "link");
        assertEquals("GravatarProfilePhoto{type=\"thumbnail\", link=\"link\"}", myGitHub.toString());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfilePhoto url = new GravatarProfilePhoto("thumbnail", "link");
        GravatarProfilePhoto equalToUrl = new GravatarProfilePhoto("thumbnail", "link");
        GravatarProfilePhoto noteEqualToUrl = new GravatarProfilePhoto("other thumbnail", "link");

        assertEquals(url, url);
        assertEquals(url, equalToUrl);
        assertNotEquals(equalToUrl, noteEqualToUrl);
        assertNotEquals(url, new Object());
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {
        GravatarProfilePhoto url = new GravatarProfilePhoto("thumbnail", "link");
        GravatarProfilePhoto equalToUrl = new GravatarProfilePhoto("thumbnail", "link");
        GravatarProfilePhoto noteEqualToUrl = new GravatarProfilePhoto("other thumbnail", "link");

        assertEquals(-1699840882, url.hashCode());
        assertEquals(-1699840882, equalToUrl.hashCode());
        assertEquals(97995678, noteEqualToUrl.hashCode());
        assertEquals(url.hashCode(), equalToUrl.hashCode());
        assertNotEquals(equalToUrl.hashCode(), noteEqualToUrl.hashCode());
    }
}