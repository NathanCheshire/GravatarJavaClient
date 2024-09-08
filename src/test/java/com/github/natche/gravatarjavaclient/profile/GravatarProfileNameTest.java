package com.github.natche.gravatarjavaclient.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GravatarProfilePhoto}s.
 */
public class GravatarProfileNameTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    GravatarProfileNameTest() {}

    /**
     * Tests for creation.
     */
    @Test
    void testCreation() {
        assertDoesNotThrow(() -> new GravatarProfileName("", "", ""));
        assertDoesNotThrow(() -> new GravatarProfileName(null, null, null));
        assertDoesNotThrow(() -> new GravatarProfileName("name", "family name", null));
    }

    /**
     * Tests for public accessor methods.
     */
    @Test
    void testAccessors() {
        GravatarProfileName name = new GravatarProfileName("first", "last", "formatted");
        assertEquals("first", name.getGivenName());
        assertEquals("last", name.getFamilyName());
    }

    /**
     * Tests for the hashcode method.
     */
    @Test
    void testHashCode() {
        GravatarProfileName name = new GravatarProfileName("first", "last", "formatted");
        GravatarProfileName other = new GravatarProfileName("first", "other last", "");

        assertEquals(-1270999578, name.hashCode());
        assertEquals(-1514966794, other.hashCode());
        assertNotEquals(name.hashCode(), other.hashCode());
    }

    /**
     * Tests for the toString method.
     */
    @Test
    void testToString() {
        GravatarProfileName name = new GravatarProfileName("first", "last", "formatted");

        assertEquals("GravatarProfileName{givenName=\"first\", familyName=\"last\"}", name.toString());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfileName name = new GravatarProfileName("first", "last", "formatted");
        GravatarProfileName equal = new GravatarProfileName("first", "last", "formatted");
        GravatarProfileName notEqual = new GravatarProfileName("first", "other", "formatted");
        GravatarProfileName alsoNotEqual = new GravatarProfileName("diff", "other", "diff");

        assertEquals(name, name);
        assertEquals(name, equal);
        assertNotEquals(name, notEqual);
        assertNotEquals(notEqual, alsoNotEqual);
        assertNotEquals(name, new Object());
    }
}
