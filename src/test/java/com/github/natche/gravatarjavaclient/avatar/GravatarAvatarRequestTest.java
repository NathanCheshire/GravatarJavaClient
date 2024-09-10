package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.TestingImageUrls;
import com.github.natche.gravatarjavaclient.enums.*;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

/**
 * Tests for {@link GravatarAvatarRequest}s.
 */
@ExtendWith(MockitoExtension.class)
public class GravatarAvatarRequestTest {
    /**
     * A GravatarAvatarRequest, spied on by Mockito, used to ensure a {@link GravatarJavaClientException} is
     * thrown when the {@link GravatarAvatarRequest#getImageIcon()} or {@link GravatarAvatarRequest#getBufferedImage()}
     * fails to retrieve the image.
     */
    @Spy
    private GravatarAvatarRequest spiedRequest = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5");

    /**
     * Creates a new instance of this for testing purposes.
     */
    GravatarAvatarRequestTest() {}

    /**
     * Tests for creation from an email address.
     */
    @Test
    void testCreationFromEmail() {
        assertThrows(NullPointerException.class, () -> GravatarAvatarRequest.fromEmail(null));
        assertThrows(IllegalArgumentException.class, () -> GravatarAvatarRequest.fromEmail(""));
        assertThrows(IllegalArgumentException.class, () -> GravatarAvatarRequest.fromEmail("    "));
        assertThrows(IllegalArgumentException.class, () -> GravatarAvatarRequest.fromEmail("invalid.email"));
        assertThrows(IllegalArgumentException.class, () -> GravatarAvatarRequest.fromEmail("invalid.email@email"));

        assertDoesNotThrow(() -> GravatarAvatarRequest.fromEmail("valid.email@email.com"));
    }

    /**
     * Tests for creation from a hash.
     */
    @Test
    void testCreationFromHash() {
        assertThrows(NullPointerException.class, () -> GravatarAvatarRequest.fromHash(null));
        assertThrows(IllegalArgumentException.class, () -> GravatarAvatarRequest.fromHash(""));
        assertThrows(IllegalArgumentException.class, () -> GravatarAvatarRequest.fromHash("    "));

        assertDoesNotThrow(() -> GravatarAvatarRequest.fromHash("some hash"));
    }

    /**
     * Tests for the accessors and mutators.
     */
    @Test
    void testAccessorsAndMutators() {
        // Hash
        GravatarAvatarRequest request = GravatarAvatarRequest.fromEmail("valid.email@email.com");
        assertEquals("1f60940c52efc3c031b29d6e87a01ed9", request.getHash());

        // Default image URL
        assertThrows(NullPointerException.class, () -> request.setDefaultImageUrl(null));
        assertThrows(IllegalArgumentException.class, () -> request.setDefaultImageUrl(""));
        assertThrows(IllegalArgumentException.class, () -> request.setDefaultImageUrl("   "));
        assertThrows(IllegalArgumentException.class, () -> request.setDefaultImageUrl("invalid.url"));
        assertThrows(IllegalArgumentException.class, () -> request.setDefaultImageUrl("https://not.valid.url.com"));
        assertDoesNotThrow(() -> request.setDefaultImageUrl(TestingImageUrls.foreignImageUrl));
        assertEquals(TestingImageUrls.foreignImageUrl, request.getDefaultImageUrl());

        // Full URL parameters
        assertThrows(NullPointerException.class, () -> request.setUseFullUrlParameters(null));
        assertDoesNotThrow(() -> request.setUseFullUrlParameters(GravatarUseFullUrlParameters.False));
        assertEquals(GravatarUseFullUrlParameters.False, request.getUseFullUrlParameters());
        assertDoesNotThrow(() -> request.setUseFullUrlParameters(GravatarUseFullUrlParameters.True));
        assertEquals(GravatarUseFullUrlParameters.True, request.getUseFullUrlParameters());

        // Protocol
        assertThrows(NullPointerException.class, () -> request.setProtocol(null));
        assertDoesNotThrow(() -> request.setProtocol(GravatarProtocol.HTTP));
        assertEquals(GravatarProtocol.HTTP, request.getProtocol());
        assertDoesNotThrow(() -> request.setProtocol(GravatarProtocol.HTTPS));
        assertEquals(GravatarProtocol.HTTPS, request.getProtocol());

        // Default image type
        assertThrows(NullPointerException.class, () -> request.setDefaultImageType(null));
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType._404));
        assertEquals(GravatarDefaultImageType._404, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.BLANK));
        assertEquals(GravatarDefaultImageType.BLANK, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.IDENT_ICON));
        assertEquals(GravatarDefaultImageType.IDENT_ICON, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.MONSTER_ID));
        assertEquals(GravatarDefaultImageType.MONSTER_ID, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.MYSTERY_PERSON));
        assertEquals(GravatarDefaultImageType.MYSTERY_PERSON, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.RETRO));
        assertEquals(GravatarDefaultImageType.RETRO, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.ROBO_HASH));
        assertEquals(GravatarDefaultImageType.ROBO_HASH, request.getDefaultImageType());
        assertDoesNotThrow(() -> request.setDefaultImageType(GravatarDefaultImageType.WAVATAR));
        assertEquals(GravatarDefaultImageType.WAVATAR, request.getDefaultImageType());

        // Force default image
        assertThrows(NullPointerException.class, () -> request.setForceDefaultImage(null));
        assertDoesNotThrow(() -> request.setForceDefaultImage(GravatarForceDefaultImage.Force));
        assertEquals(GravatarForceDefaultImage.Force, request.shouldForceDefaultImage());
        assertDoesNotThrow(() -> request.setForceDefaultImage(GravatarForceDefaultImage.DoNotForce));
        assertEquals(GravatarForceDefaultImage.DoNotForce, request.shouldForceDefaultImage());

        // Rating
        assertThrows(NullPointerException.class, () -> request.setRating(null));
        assertDoesNotThrow(() -> request.setRating(GravatarRating.G));
        assertEquals(GravatarRating.G, request.getRating());
        assertDoesNotThrow(() -> request.setRating(GravatarRating.PG));
        assertEquals(GravatarRating.PG, request.getRating());
        assertDoesNotThrow(() -> request.setRating(GravatarRating.R));
        assertEquals(GravatarRating.R, request.getRating());
        assertDoesNotThrow(() -> request.setRating(GravatarRating.X));
        assertEquals(GravatarRating.X, request.getRating());

        // Append JPG suffix
        assertThrows(NullPointerException.class, () -> request.setShouldAppendJpgSuffix(null));
        assertDoesNotThrow(() -> request.setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True));
        assertEquals(GravatarUseJpgSuffix.True, request.getShouldAppendJpgSuffix());
        assertDoesNotThrow(() -> request.setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False));
        assertEquals(GravatarUseJpgSuffix.False, request.getShouldAppendJpgSuffix());

        // Size
        assertThrows(IllegalArgumentException.class, () -> request.setSize(-10));
        assertThrows(IllegalArgumentException.class, () -> request.setSize(0));
        assertThrows(IllegalArgumentException.class, () -> request.setSize(2049));
        assertThrows(IllegalArgumentException.class, () -> request.setSize(2080));
        assertThrows(IllegalArgumentException.class, () -> request.setSize(4000));
        assertDoesNotThrow(() -> request.setSize(1));
        assertDoesNotThrow(() -> request.setSize(200));
        assertDoesNotThrow(() -> request.setSize(2048));
    }

    /**
     * Tests for the get request URL method.
     */
    @Test
    void testGetRequestUrl() {
        String fromEmailUrl = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
                .getRequestUrl();
        assertEquals("http://www.gravatar.com/avatar/80c44e7f3f5082023ede351d396844f5.jpg"
                + "?s=2000&r=x&d=https://picsum.photos/seed/gravatar-java-client/200/300&f=y", fromEmailUrl);

        String fromHashUrl = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
                .getRequestUrl();
        assertEquals("https://www.gravatar.com/avatar/80c44e7f3f5082023ede351d396844f5"
                + "?size=1776&rating=r&default=wavatar", fromHashUrl);

        GravatarAvatarRequest invalidDefaultUrl = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);
        assertThrows(GravatarJavaClientException.class, invalidDefaultUrl::getRequestUrl);
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {
        GravatarAvatarRequest fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);
        GravatarAvatarRequest equalToFromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);

        GravatarAvatarRequest fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True);

        assertEquals(fromEmail.hashCode(), equalToFromEmail.hashCode());
        assertNotEquals(fromEmail.hashCode(), fromHash.hashCode());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarAvatarRequest fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);
        GravatarAvatarRequest equalToFromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);

        GravatarAvatarRequest fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True);

        assertEquals(fromEmail, fromEmail);
        assertEquals(fromEmail, equalToFromEmail);
        assertNotEquals(fromEmail, fromHash);
        assertNotEquals(fromEmail, new Object());

        // Tests for Codecov to report 100%

        GravatarAvatarRequest hashOne = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest hashTwo = GravatarAvatarRequest.fromHash("two");
        assertNotEquals(hashOne, hashTwo);

        GravatarAvatarRequest jpgSuffix = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest differentJpgSuffix = GravatarAvatarRequest.fromHash("one");
        jpgSuffix.setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True);
        differentJpgSuffix.setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False);
        assertNotEquals(jpgSuffix, differentJpgSuffix);

        GravatarAvatarRequest size = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest differentSize = GravatarAvatarRequest.fromHash("one");
        size.setSize(100);
        differentSize.setSize(101);
        assertNotEquals(size, differentSize);

        GravatarAvatarRequest force = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest doNotForce = GravatarAvatarRequest.fromHash("one");
        force.setForceDefaultImage(GravatarForceDefaultImage.Force);
        doNotForce.setForceDefaultImage(GravatarForceDefaultImage.DoNotForce);
        assertNotEquals(force, doNotForce);

        GravatarAvatarRequest roboHash = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest wavatar = GravatarAvatarRequest.fromHash("one");
        roboHash.setDefaultImageType(GravatarDefaultImageType.ROBO_HASH);
        wavatar.setDefaultImageType(GravatarDefaultImageType.WAVATAR);
        assertNotEquals(roboHash, wavatar);

        GravatarAvatarRequest http = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest https = GravatarAvatarRequest.fromHash("one");
        http.setProtocol(GravatarProtocol.HTTP);
        https.setProtocol(GravatarProtocol.HTTPS);
        assertNotEquals(http, https);

        GravatarAvatarRequest notFullParams = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest fullParams = GravatarAvatarRequest.fromHash("one");
        notFullParams.setUseFullUrlParameters(GravatarUseFullUrlParameters.False);
        fullParams.setUseFullUrlParameters(GravatarUseFullUrlParameters.True);
        assertNotEquals(notFullParams, fullParams);

        GravatarAvatarRequest foreignImage = GravatarAvatarRequest.fromHash("one");
        GravatarAvatarRequest otherForeignImage = GravatarAvatarRequest.fromHash("one");
        foreignImage.setDefaultImageUrl(TestingImageUrls.foreignImageUrl);
        otherForeignImage.setDefaultImageUrl(TestingImageUrls.anotherForeignImageUrl);
        assertNotEquals(foreignImage, otherForeignImage);
    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {
        GravatarAvatarRequest fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);
        GravatarAvatarRequest fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True);

        assertEquals("GravatarAvatarRequest{hash=\"80c44e7f3f5082023ede351d396844f5\", size=2000,"
                        + " rating=X, forceDefaultImage=Force, defaultImageType=null, protocol=HTTP,"
                        + " useFullUrlParameters=False,"
                        + " defaultImageUrl=\"https://picsum.photos/seed/gravatar-java-client/200/300\", }",
                fromEmail.toString());
        assertEquals("GravatarAvatarRequest{hash=\"80c44e7f3f5082023ede351d396844f5\", size=1776,"
                + " rating=R, forceDefaultImage=DoNotForce, defaultImageType=WAVATAR, protocol=HTTPS,"
                + " useFullUrlParameters=True, defaultImageUrl=\"null\", }", fromHash.toString());
    }

    /**
     * Tests for the get image icon method.
     */
    @Test
    void testGetImageIcon() {
        GravatarAvatarRequest fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
                .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
                .setForceDefaultImage(GravatarForceDefaultImage.Force)
                .setRating(GravatarRating.X)
                .setSize(2000)
                .setProtocol(GravatarProtocol.HTTP)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.False);
        GravatarAvatarRequest fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True);

        assertDoesNotThrow(fromEmail::getImageIcon);
        assertNotNull(fromEmail.getImageIcon());
        assertDoesNotThrow(fromHash::getImageIcon);
        assertNotNull(fromHash.getImageIcon());


        doReturn("invalid://url").when(spiedRequest).getRequestUrl();
        assertThrows(GravatarJavaClientException.class, spiedRequest::getImageIcon);
    }

    /**
     * Tests for the get buffered image method.
     */
    @Test
    void testGetBufferedImage() {
        GravatarAvatarRequest fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True);
        assertDoesNotThrow(fromHash::getBufferedImage);
        assertNotNull(fromHash.getBufferedImage());

        doReturn("invalid://url").when(spiedRequest).getRequestUrl();
        assertThrows(GravatarJavaClientException.class, spiedRequest::getBufferedImage);
    }

    /**
     * Tests for the save to method.
     */
    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    void testSaveTo() {
        GravatarAvatarRequest fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
                .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
                .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
                .setRating(GravatarRating.R)
                .setSize(1776)
                .setProtocol(GravatarProtocol.HTTPS)
                .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
                .setUseFullUrlParameters(GravatarUseFullUrlParameters.True);

        assertThrows(NullPointerException.class, () -> fromHash.saveTo(null, null));
        assertThrows(IllegalArgumentException.class, () -> fromHash.saveTo(new File("."), "png"));
        assertThrows(NullPointerException.class, () -> fromHash.saveTo(new File("file.png"), null));
        assertThrows(IllegalArgumentException.class, () -> fromHash.saveTo(new File("file.png"), ""));
        assertThrows(IllegalArgumentException.class, () -> fromHash.saveTo(new File("file.png"), " "));

        File saveToOutput = new File("./save_to_output");
        //noinspection ResultOfMethodCallIgnored
        saveToOutput.delete();
        assertFalse(saveToOutput.exists());
        boolean created = saveToOutput.mkdir();
        assertTrue(created);

        File saveFromHashTo = new File("./save_to_output/FromHash.png");
        boolean saved = fromHash.saveTo(saveFromHashTo, "png");
        assertTrue(saved);
        assertTrue(saveFromHashTo.exists());

        try {
            //noinspection ResultOfMethodCallIgnored
            saveFromHashTo.delete();
            //noinspection ResultOfMethodCallIgnored
            saveToOutput.delete();
            Thread.sleep(500);
        } catch (Exception e) {
            // Swallow possible thread exception
        }
        assertFalse(saveToOutput.exists());
    }
}
