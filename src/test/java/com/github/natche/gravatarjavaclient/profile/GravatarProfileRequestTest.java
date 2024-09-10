package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        try {
            File outputDirectory = new File("./save_to_output");
            GravatarProfileRequest authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
                    .setTokenSupplier(TokenSupplier.getTokenSupplier());

            assertThrows(NullPointerException.class, () -> authenticatedRequest.writeToFile(null, null));
            assertThrows(NullPointerException.class, () -> authenticatedRequest.writeToFile(new Gson(), null));
            assertThrows(IllegalArgumentException.class,
                    () -> authenticatedRequest.writeToFile(new Gson(), new File(".")));
            assertThrows(IllegalArgumentException.class,
                    () -> authenticatedRequest.writeToFile(new Gson(), new File("non_existent_file.json")));

            File tempFile = new File(outputDirectory, "test.json");
            //noinspection ResultOfMethodCallIgnored
            tempFile.createNewFile();

            Thread.sleep(100);
            assertTrue(authenticatedRequest.writeToFile(tempFile));
            assertTrue(tempFile.exists());
            assertTrue(tempFile.length() > 0);

            File mockFile = mock(File.class);
            when(mockFile.isDirectory()).thenReturn(false);
            when(mockFile.getAbsolutePath()).thenReturn(tempFile.getAbsolutePath());
            when(mockFile.exists()).thenReturn(true);
            doThrow(new IOException("Test exception")).when(mockFile).getCanonicalFile();
            assertThrows(GravatarJavaClientException.class, () -> authenticatedRequest.writeToFile(mockFile));

            Gson customSerializer = new Gson();
            assertTrue(authenticatedRequest.writeToFile(customSerializer, tempFile));
            assertTrue(tempFile.exists());
            assertTrue(tempFile.length() > 0);

            authenticatedRequest.writeToFile(tempFile);
            String content = new String(Files.readAllBytes(tempFile.toPath()));
            assertNotNull(content);
            assertTrue(content.contains("\"registration_date\":"));  // Authenticated field

            //noinspection ResultOfMethodCallIgnored
            tempFile.delete();
            Thread.sleep(50);
            //noinspection ResultOfMethodCallIgnored
            outputDirectory.delete();
            Thread.sleep(50);
            assertFalse(tempFile.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        GravatarProfileRequest authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
                .setTokenSupplier(TokenSupplier.getTokenSupplier());
        GravatarProfileRequest unauthenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire");

        assertEquals(998975623, authenticatedRequest.hashCode());
        assertEquals(2065170537, unauthenticatedRequest.hashCode());
        assertNotEquals(authenticatedRequest.hashCode(), unauthenticatedRequest.hashCode());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarProfileRequest authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
                .setTokenSupplier(TokenSupplier.getTokenSupplier());
        GravatarProfileRequest equal = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
                .setTokenSupplier(TokenSupplier.getTokenSupplier());
        GravatarProfileRequest notEqual = GravatarProfileRequest.fromHashOrId("nathanvcheshire");

        assertEquals(authenticatedRequest, authenticatedRequest);
        assertEquals(authenticatedRequest, equal);
        assertNotEquals(authenticatedRequest, notEqual);
        assertNotEquals(authenticatedRequest, new Object());
    }
}
