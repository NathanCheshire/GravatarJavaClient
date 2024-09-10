package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GravatarProfileRequest}s.
 */
public class GravatarProfileRequestTest {
    /**
     * Constructs a new instance for testing purposes.
     */
    GravatarProfileRequestTest() {}

    /**
     * Tests for the "from hash or ID" method.
     */
    @Test
    void fromHashOrId() {
        assertThrows(NullPointerException.class, () -> GravatarProfileRequest.fromHashOrId(null));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfileRequest.fromHashOrId(""));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfileRequest.fromHashOrId("   "));

        assertDoesNotThrow(() -> GravatarProfileRequest.fromHashOrId("hash"));
        assertDoesNotThrow(() -> GravatarProfileRequest.fromHashOrId("nathanvcheshire"));
    }

    /**
     * Tests for the "from email" method.
     */
    @Test
    void testFromEmail() {
        assertThrows(NullPointerException.class, () -> GravatarProfileRequest.fromEmail(null));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfileRequest.fromEmail(""));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfileRequest.fromEmail("   "));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfileRequest.fromEmail("email.address"));
        assertThrows(IllegalArgumentException.class, () -> GravatarProfileRequest.fromEmail("email.address@gmail"));

        assertDoesNotThrow(() -> GravatarProfileRequest.fromEmail("email.address@gmail.com"));
    }

    /**
     * Tests for the set token supplier method.
     */
    @Test
    void testSetTokenSupplier() {
        assertThrows(NullPointerException.class,
                () -> GravatarProfileRequest.fromHashOrId("hash").setTokenSupplier(null));
        assertDoesNotThrow(
                () -> GravatarProfileRequest.fromHashOrId("hash").setTokenSupplier(TokenSupplier.getTokenSupplier()));
    }

    /**
     * Tests for the get hash or ID method.
     */
    @Test
    void testGetHashOrId() {
        GravatarProfileRequest request = GravatarProfileRequest.fromHashOrId("hash");
        assertDoesNotThrow(request::getHashOrId);
        assertEquals("hash", request.getHashOrId());
        assertEquals("c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
                GravatarProfileRequest.fromEmail("nathan.vincent.2.718@gmail.com").getHashOrId());
    }

    /**
     * Tests for the get profile method.
     */
    @Test
    void testGetProfile() {
        GravatarProfileRequest authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
                .setTokenSupplier(TokenSupplier.getTokenSupplier());
        GravatarProfileRequest unauthenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire");

        assertDoesNotThrow(authenticatedRequest::getProfile);
        assertDoesNotThrow(unauthenticatedRequest::getProfile);

        GravatarProfile authenticatedProfile = authenticatedRequest.getProfile();
        GravatarProfile unauthenticatedProfile = unauthenticatedRequest.getProfile();

        assertFalse(authenticatedProfile.getLinks().isEmpty());
        assertTrue(authenticatedProfile.getLastProfileEdit().isPresent());
        assertTrue(authenticatedProfile.getRegistrationDate().isPresent());

        assertTrue(unauthenticatedProfile.getLinks().isEmpty());
        assertTrue(unauthenticatedProfile.getLastProfileEdit().isEmpty());
        assertTrue(unauthenticatedProfile.getRegistrationDate().isEmpty());

        assertEquals(2, GravatarProfileRequestHandler.INSTANCE.getAuthenticatedRequestResults().size());
        assertEquals(2, GravatarProfileRequestHandler.INSTANCE.getUnauthenticatedRequestResults().size());
    }

    /**
     * Tests for the write to file method.
     */
    @Test
    void testWriteToFile() {

    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {
        GravatarProfileRequest authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
                .setTokenSupplier(TokenSupplier.getTokenSupplier());
        GravatarProfileRequest unauthenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire");

        assertEquals("GravatarProfileRequest{hash=\"nathanvcheshire\","
                + " tokenSupplier=GravatarProfileTokenProvider{source=\"TokenSupplier class\"}}",
                authenticatedRequest.toString());
        assertEquals("GravatarProfileRequest{hash=\"nathanvcheshire\", tokenSupplier=null}",
                unauthenticatedRequest.toString());
    }

    /**
     * Tests for the hashcode method.
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
