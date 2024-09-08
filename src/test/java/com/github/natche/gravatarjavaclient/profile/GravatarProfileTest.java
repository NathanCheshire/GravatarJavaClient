package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GravatarProfile}.
 */
@SuppressWarnings("SpellCheckingInspection") /* hashes */
class GravatarProfileTest {
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
     * The full JSON string with a different id to {@link #fullJson}.
     */
    private static final String differentIds = "{ \"entry\": [ { \"id\": \"2315612699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": {  }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Other Website\" } ] } ] }";

    /**
     * The full JSON string with a different hash to {@link #fullJson}.
     */
    private static final String differentHash = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19ccad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": {  }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Other Website\" } ] } ] }";

    /**
     * The full JSON string with a different request hash to {@link #fullJson}.
     */
    private static final String differentRequestHash = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1bba19bcad06a8e894d7373a4cfc7\","
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
     * The full JSON string with a different profile url to {@link #fullJson}.
     */
    private static final String differentProfileUrl = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathancheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://wwww.github.com\", \"title\": \"GitHubs\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different preferred username to {@link #fullJson}.
     */
    private static final String differentPreferredUsername = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathancheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different thumbnail url to {@link #fullJson}.
     */
    private static final String differentThumbnailUrl = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc4\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different photos list to {@link #fullJson}.
     */
    private static final String differentPhotos = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a5e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different given name to {@link #fullJson}.
     */
    private static final String differentGivenName = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nate\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different family name to {@link #fullJson}.
     */
    private static final String differentFamilyName = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Chesh\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different display name to {@link #fullJson}.
     */
    private static final String differentDisplayName = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Chesh\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different pronouns to {@link #fullJson}.
     */
    private static final String differentPronouns = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"who/cares\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different about me to {@link #fullJson}.
     */
    private static final String differentAboutMe = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I do\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with a different current location to {@link #fullJson}.
     */
    private static final String differentCurrentLocation = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Home\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Website\" } ] } ] }";

    /**
     * The full JSON string with different urls to {@link #fullJson}.
     */
    private static final String differentUrls = "{ \"entry\": [ { \"id\": \"231564699\","
            + " \"hash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\", \"requestHash\": \"2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"profileUrl\": \"http://gravatar.com/nathanvcheshire\", \"preferredUsername\": \"nathanvcheshire\","
            + " \"thumbnailUrl\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"photos\": [ { \"value\": \"https://secure.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7\","
            + " \"type\": \"thumbnail\" } ], \"name\": { \"givenName\": \"Nathan\", \"familyName\": \"Cheshire\","
            + " \"formatted\": \"Nathan Cheshire\" }, \"displayName\": \"Nathan Cheshire\","
            + " \"pronouns\": \"Time/Lord\", \"aboutMe\": \"I make the computer go beep boop and then"
            + " people ask me to fix their printer.\", \"currentLocation\": \"Gallifrey\", \"urls\":"
            + " [ { \"value\": \"https://www.github.com\", \"title\": \"GitHub\" }, { \"value\":"
            + " \"https://www.nathancheshire.com\", \"title\": \"Personal Websiter\" } ] } ] }";

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
        assertThrows(NullPointerException.class, () -> GravatarProfile.fromJson(null));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfile.fromJson(""));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfile.fromJson("    "));

        assertDoesNotThrow(() -> GravatarProfile.fromJson(fullJson));
        assertDoesNotThrow(() -> GravatarProfile.fromJson(minimalJson));
    }

    /**
     * Tests for the accessor and mutator methods.
     */
    @Test
    void testAccessorsMutators() {
        GravatarProfile minimal = GravatarProfile.fromJson(minimalJson);
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

        GravatarProfile full = GravatarProfile.fromJson(fullJson);
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
        GravatarProfile full = GravatarProfile.fromJson(fullJson);
        assertEquals("GravatarProfile{hash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
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

        GravatarProfile minimal = GravatarProfile.fromJson(minimalJson);
        assertEquals("GravatarProfile{hash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " requestHash=\"2bf1b7a19bcad06a8e894d7373a4cfc7\","
                + " profileUrl=\"http://gravatar.com/nathanvcheshire\", preferredUsername=\"null\","
                + " thumbnailUrl=\"null\", profilePhotos=[], givenName=\"\", familyName=\"\","
                + " displayName=\"null\", pronouns=\"null\", aboutMe=\"null\", currentLocation=\"null\","
                + " profileUrls=[]}", minimal.toString());
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {
        GravatarProfile full = GravatarProfile.fromJson(fullJson);
        GravatarProfile equalToFull = GravatarProfile.fromJson(fullJson);
        GravatarProfile nonEqualToFull = GravatarProfile.fromJson(minimalJson);

        assertEquals(974991455, full.hashCode());
        assertEquals(974991455, equalToFull.hashCode());
        assertEquals(1483453763, nonEqualToFull.hashCode());
        assertEquals(full.hashCode(), equalToFull.hashCode());
        assertNotEquals(equalToFull.hashCode(), nonEqualToFull.hashCode());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfile full = GravatarProfile.fromJson(fullJson);
        GravatarProfile equalToFull = GravatarProfile.fromJson(fullJson);
        GravatarProfile nonEqualToFull = GravatarProfile.fromJson(minimalJson);

        assertEquals(full, full);
        assertEquals(full, equalToFull);
        assertNotEquals(equalToFull, nonEqualToFull);
        assertNotEquals(equalToFull, new Object());
        assertNotEquals(full,  GravatarProfile.fromJson(differentUrls));
        assertNotEquals(full,  GravatarProfile.fromJson(differentIds));
        assertNotEquals(full,  GravatarProfile.fromJson(differentHash));
        assertNotEquals(full,  GravatarProfile.fromJson(differentRequestHash));
        assertNotEquals(full,  GravatarProfile.fromJson(differentProfileUrl));
        assertNotEquals(full,  GravatarProfile.fromJson(differentPreferredUsername));
        assertNotEquals(full,  GravatarProfile.fromJson(differentThumbnailUrl));
        assertNotEquals(full,  GravatarProfile.fromJson(differentPhotos));
        assertNotEquals(full,  GravatarProfile.fromJson(differentGivenName));
        assertNotEquals(full,  GravatarProfile.fromJson(differentFamilyName));
        assertNotEquals(full,  GravatarProfile.fromJson(differentDisplayName));
        assertNotEquals(full,  GravatarProfile.fromJson(differentPronouns));
        assertNotEquals(full,  GravatarProfile.fromJson(differentAboutMe));
        assertNotEquals(full,  GravatarProfile.fromJson(differentCurrentLocation));
    }
}
