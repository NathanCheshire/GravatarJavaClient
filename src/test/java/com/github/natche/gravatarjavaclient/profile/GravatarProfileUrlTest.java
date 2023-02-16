package com.github.natche.gravatarjavaclient.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GravatarProfileUrl}s.
 */
class GravatarProfileUrlTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    GravatarProfileUrlTest() {}

    /**
     * Tests for creation of Gravatar profile urls.
     */
    @Test
    void testCreation() {
        assertThrows(NullPointerException.class, () -> new GravatarProfileUrl(null, null));
        assertThrows(NullPointerException.class, () -> new GravatarProfileUrl("", null));
        assertThrows(IllegalArgumentException.class, () -> new GravatarProfileUrl("", ""));
        assertThrows(IllegalArgumentException.class, () -> new GravatarProfileUrl("plank", ""));
        assertDoesNotThrow(() -> new GravatarProfileUrl("plank", "plank"));
    }

    /**
     * Tests for the accessor and mutator methods.
     */
    @Test
    void testAccessorsMutators() {
        GravatarProfileUrl url = new GravatarProfileUrl("name", "link");
        assertEquals("name", url.getName());
        assertEquals("link", url.getLink());
    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {
        GravatarProfileUrl url = new GravatarProfileUrl("name", "link");
        assertEquals("GravatarProfileUrl{name=\"name\", link=\"link\"}", url.toString());

        GravatarProfileUrl myGitHub =
                new GravatarProfileUrl("GitHub", "https://www.github.com/nathancheshire");
        assertEquals("GravatarProfileUrl{name=\"GitHub\", link=\"https://www.github.com/nathancheshire\"}",
                myGitHub.toString());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfileUrl url = new GravatarProfileUrl("name", "link");
        GravatarProfileUrl equalToUrl = new GravatarProfileUrl("name", "link");
        GravatarProfileUrl notEqualToUrl = new GravatarProfileUrl("other name", "link");
        GravatarProfileUrl notEqualToUrlEither = new GravatarProfileUrl("name", "other link");

        assertEquals(url, url);
        assertEquals(url, equalToUrl);
        assertNotEquals(equalToUrl, notEqualToUrl);
        assertNotEquals(equalToUrl, notEqualToUrlEither);
        assertNotEquals(url, new Object());
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {
        GravatarProfileUrl url = new GravatarProfileUrl("name", "link");
        GravatarProfileUrl equalToUrl = new GravatarProfileUrl("name", "link");
        GravatarProfileUrl notEqualToUrl = new GravatarProfileUrl("other name", "other link");

        assertEquals(107906767, url.hashCode());
        assertEquals(107906767, equalToUrl.hashCode());
        assertEquals(890890447, notEqualToUrl.hashCode());
        assertEquals(url.hashCode(), equalToUrl.hashCode());
        assertNotEquals(equalToUrl.hashCode(), notEqualToUrl.hashCode());
    }
}
