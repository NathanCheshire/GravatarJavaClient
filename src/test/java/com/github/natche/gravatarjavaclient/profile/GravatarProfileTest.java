package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the {@link GravatarProfile}.
 */
class GravatarProfileTest {
    /**
     * The JSON string missing required keys to test for throwing.
     */
    private static final String missingKeyJson = "{ \"entry\": [ { \"id\": \"231564699\" } ] }";

    /**
     * The full JSON string to parse a Gravatar profile from.
     */
    private static final String fullJson = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The minimal JSON string to parse a Gravatar profile from.
     */
    private static final String minimalJson = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\" } ] }";

    /**
     * Creates a new instance of this class for testing purposes.
     */
    public GravatarProfileTest() {}

    /**
     * Tests for creation of profiles via the from user email method.
     */
    @Test
    void testFromUserEmail() {
        assertThrows(NullPointerException.class, () -> GravatarProfile.fromUserEmail(null));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfile.fromUserEmail(""));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfile.fromUserEmail("invalid email"));
        assertThrows(GravatarJavaClientException.class, () -> GravatarProfile.fromUserEmail("valid.email@domain.com"));

        assertDoesNotThrow(() -> GravatarProfile.fromUserEmail("nathan.vincent.2.718@gmail.com"));
    }

    /**
     * Tests for creation of profiles via the constructor.
     */
    @Test
    void testCreation() {
        assertThrows(NullPointerException.class, () -> new GravatarProfile(null));
        assertThrows(IllegalArgumentException.class, () -> new GravatarProfile(""));
        assertThrows(JSONException.class, () -> new GravatarProfile("{}"));
        assertThrows(JSONException.class, () -> new GravatarProfile(missingKeyJson));

        assertDoesNotThrow(() -> new GravatarProfile(fullJson));
        assertDoesNotThrow(() -> new GravatarProfile(minimalJson));
    }

    /**
     * Tests for the accessor and mutator methods.
     */
    @Test
    void testAccessorsMutators() {

    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {

    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {

    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {

    }
}
