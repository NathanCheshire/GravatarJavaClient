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

        // todo here
        GravatarProfilePhoto myGitHub =
                new GravatarProfilePhoto("GitHub", "https://www.github.com/nathancheshire");
        assertEquals("GravatarProfileUrl{name=\"GitHub\", link=\"https://www.github.com/nathancheshire\"}",
                myGitHub.toString());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfilePhoto url = new GravatarProfilePhoto("name", "link");
        GravatarProfilePhoto equalToUrl = new GravatarProfilePhoto("name", "link");
        GravatarProfilePhoto noteEqualToUrl = new GravatarProfilePhoto("other name", "link");

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
        GravatarProfilePhoto url = new GravatarProfilePhoto("name", "link");
        GravatarProfilePhoto equalToUrl = new GravatarProfilePhoto("name", "link");
        GravatarProfilePhoto noteEqualToUrl = new GravatarProfilePhoto("other name", "link");

        assertEquals(107906767, url.hashCode());
        assertEquals(107906767, equalToUrl.hashCode());
        assertEquals(1134857663, noteEqualToUrl.hashCode());
        assertEquals(url.hashCode(), equalToUrl.hashCode());
        assertNotEquals(equalToUrl.hashCode(), noteEqualToUrl.hashCode());
    }
}
