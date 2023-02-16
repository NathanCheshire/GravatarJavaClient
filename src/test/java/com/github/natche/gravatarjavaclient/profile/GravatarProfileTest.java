package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.collect.ImmutableList;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
     * The full JSON string to parse a Gravatar profile from
     * with the last field different than that of {@link #fullJson}.
     */
    private static final String differentFullJson = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Other Website\" } ] } ] }";

    /**
     * The minimal JSON string to parse a Gravatar profile from.
     */
    private static final String minimalJson = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\" } ] }";

    /**
     * Creates a new instance of this class for testing purposes.
     */
    GravatarProfileTest() {}

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
        GravatarProfile minimal = new GravatarProfile(minimalJson);
        assertEquals("231564699", minimal.getUserId());
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7", minimal.getHash());
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7", minimal.getRequestHash());
        assertEquals("http://gravatar.com/nathanvcheshire", minimal.getProfileUrl());
        assertTrue(minimal.getPreferredUsername().isEmpty());
        assertTrue(minimal.getThumbnailUrl().isEmpty());
        assertTrue(minimal.getProfilePhotos().isEmpty());
        assertTrue(minimal.getGivenName().isEmpty());
        assertTrue(minimal.getFamilyName().isEmpty());
        assertTrue(minimal.getDisplayName().isEmpty());
        assertTrue(minimal.getPronouns().isEmpty());
        assertTrue(minimal.getAboutMe().isEmpty());
        assertTrue(minimal.getCurrentLocation().isEmpty());
        assertTrue(minimal.getProfileUrls().isEmpty());

        GravatarProfile full = new GravatarProfile(fullJson);
        assertEquals("231564699", full.getUserId());
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7", full.getHash());
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7", full.getRequestHash());
        assertEquals("http://gravatar.com/nathanvcheshire", full.getProfileUrl());
        assertTrue(full.getPreferredUsername().isPresent());
        assertEquals("nathanvcheshire", full.getPreferredUsername().get());
        assertTrue(full.getThumbnailUrl().isPresent());
        assertEquals("https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7",
                full.getThumbnailUrl().get());
        assertFalse(full.getProfilePhotos().isEmpty());
        assertEquals(ImmutableList.of(new GravatarProfilePhoto("thumbnail",
                "https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7")), full.getProfilePhotos());
        assertTrue(full.getGivenName().isPresent());
        assertEquals("Nathan", full.getGivenName().get());
        assertTrue(full.getFamilyName().isPresent());
        assertEquals("Cheshire", full.getFamilyName().get());
        assertTrue(full.getDisplayName().isPresent());
        assertEquals("Nathan Cheshire", full.getDisplayName().get());
        assertTrue(full.getPronouns().isPresent());
        assertEquals("Time/Lord", full.getPronouns().get());
        assertTrue(full.getAboutMe().isPresent());
        assertEquals("I make the computer go beep boop and then people ask me to fix their printer.",
                full.getAboutMe().get());
        assertTrue(full.getCurrentLocation().isPresent());
        assertEquals("Gallifrey", full.getCurrentLocation().get());
        assertFalse(full.getProfileUrls().isEmpty());
        assertEquals(ImmutableList.of(new GravatarProfileUrl("GitHub", "https://www.github.com"),
                        new GravatarProfileUrl("Personal Website", "https://www.nathancheshire.com")),
                full.getProfileUrls());
    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {
        GravatarProfile full = new GravatarProfile(fullJson);
        assertEquals("GravatarProfile{, id=\"231564699\", hash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " requestHash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " profileUrl=\"http://gravatar.com/nathanvcheshire\", preferredUsername=\"nathanvcheshire\","
                + " thumbnailUrl=\"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " profilePhotos=[GravatarProfilePhoto{type=\"thumbnail\","
                + " link=\"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\"}],"
                + " givenName=\"Nathan\", familyName=\"Cheshire\", displayName=\"Nathan Cheshire\","
                + " pronouns=\"Time/Lord\", aboutMe=\"I make the computer go beep boop and then people"
                + " ask me to fix their printer.\", currentLocation=\"Gallifrey\","
                + " profileUrls=[GravatarProfileUrl{name=\"GitHub\", link=\"https://www.github.com\"},"
                + " GravatarProfileUrl{name=\"Personal Website\", link=\"https://www.nathancheshire.com\"}]}",
                full.toString());

        GravatarProfile minimal = new GravatarProfile(minimalJson);
        assertEquals("GravatarProfile{, id=\"231564699\", hash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " requestHash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " profileUrl=\"http://gravatar.com/nathanvcheshire\", preferredUsername=\"null\","
                + " thumbnailUrl=\"null\", profilePhotos=[], givenName=\"null\", familyName=\"null\","
                + " displayName=\"null\", pronouns=\"null\", aboutMe=\"null\", currentLocation=\"null\","
                + " profileUrls=[]}", minimal.toString());
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {
        GravatarProfile full = new GravatarProfile(fullJson);
        GravatarProfile equalToFull = new GravatarProfile(fullJson);
        GravatarProfile nonEqualToFull = new GravatarProfile(minimalJson);

        assertEquals(-1489177108, full.hashCode());
        assertEquals(-1489177108, equalToFull.hashCode());
        assertEquals(-980714800, nonEqualToFull.hashCode());
        assertEquals(full.hashCode(), equalToFull.hashCode());
        assertNotEquals(equalToFull.hashCode(), nonEqualToFull.hashCode());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfile full = new GravatarProfile(fullJson);
        GravatarProfile equalToFull = new GravatarProfile(fullJson);
        GravatarProfile nonEqualToFull = new GravatarProfile(minimalJson);
        GravatarProfile differentToFull = new GravatarProfile(differentFullJson);

        assertEquals(full, full);
        assertEquals(full, equalToFull);
        assertNotEquals(equalToFull, nonEqualToFull);
        assertNotEquals(full, differentToFull);
        assertNotEquals(equalToFull, new Object());
    }
}
