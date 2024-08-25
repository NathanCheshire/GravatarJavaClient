package com.github.natche.gravatarjavaclient.utils;

import com.github.natche.gravatarjavaclient.TestingConstants;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GeneralUtils}.
 */
class GeneralUtilsTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    GeneralUtilsTest() {}

    /**
     * Tests to ensure reflection is guarded against.
     * Also helps reach 100% code coverage for testing.
     */
    @Test
    void testCreation() {
        try {
            Constructor<GeneralUtils> constructor =
                    GeneralUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (Exception e) {
            assertTrue(e instanceof InvocationTargetException);
            Throwable target = ((InvocationTargetException) e).getTargetException();
            assertTrue(target instanceof AssertionError);
            assertEquals("Cannot create instances of GeneralUtils", target.getMessage());
        }
    }

    /**
     * Tests for the read buffered image method.
     */
    @Test
    void testReadBufferedImage() {
        assertThrows(NullPointerException.class, () -> GeneralUtils.readBufferedImage(null));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.readBufferedImage(""));
        assertThrows(GravatarJavaClientException.class, () -> GeneralUtils.readBufferedImage("url"));

        AtomicReference<BufferedImage> bi = new AtomicReference<>();
        assertDoesNotThrow(() -> bi.set(GeneralUtils.readBufferedImage(TestingConstants.foreignImageUrl)));
        assertEquals(200, bi.get().getWidth());
        assertEquals(300, bi.get().getHeight());
    }

    /**
     * Tests for the email address to gravatar hash method.
     */
    @Test
    void testEmailAddressToGravatarHash() {
        assertThrows(NullPointerException.class, () -> GeneralUtils.emailAddressToGravatarHash(null));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash(""));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash("asdf"));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash("invalid"));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.emailAddressToGravatarHash("invalid@asdf"));

        assertDoesNotThrow(() -> GeneralUtils.emailAddressToGravatarHash("valid@domain.com"));
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7",
                GeneralUtils.emailAddressToGravatarHash("nathan.vincent.2.718@gmail.com"));
        assertEquals("21bce028aab22e8c9ec03d66305a50dc",
                GeneralUtils.emailAddressToGravatarHash("nathan.cheshire.ctr@nrlssc.navy.mil"));
        assertEquals("e11e6bd8201d3bd6f22c4b206a863a2c",
                GeneralUtils.emailAddressToGravatarHash("ncheshire@camgian.com"));
    }

    /**
     * Tests for the hash input method.
     */
    @Test
    void testHashInput() {
        assertThrows(NullPointerException.class, () -> GeneralUtils.hashInput(null, null));
        assertThrows(NullPointerException.class, () -> GeneralUtils.hashInput("", null));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.hashInput("", ""));
        assertThrows(GravatarJavaClientException.class,
                () -> GeneralUtils.hashInput("", "asdf"));
        assertEquals("d41d8cd98f00b204e9800998ecf8427e",
                GeneralUtils.hashInput("", "MD5"));
        assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709",
                GeneralUtils.hashInput("", "SHA1"));
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                GeneralUtils.hashInput("", "SHA256"));
        assertEquals("2bf1b7a19bcad06a8e894d7373a4cfc7",
                GeneralUtils.hashInput("nathan.vincent.2.718@gmail.com", "MD5"));
        assertEquals("f84099a5bbef987de2f69b2caf96536d2c76fbf0",
                GeneralUtils.hashInput("nathan.vincent.2.718@gmail.com", "SHA1"));
        assertEquals("c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
                GeneralUtils.hashInput("nathan.vincent.2.718@gmail.com", "SHA256"));
    }

    /**
     * Tests for the read url method.
     */
    @Test
    void testReadUrl() {
        assertThrows(NullPointerException.class, () -> GeneralUtils.readUrl(null));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.readUrl(""));
        assertThrows(GravatarJavaClientException.class, () -> GeneralUtils.readUrl("invalid url"));

        assertDoesNotThrow(() -> GeneralUtils.readUrl("https://www.google.com"));
    }

    /**
     * Tests for the is valid filename method.
     */
    @Test
    void testIsValidFilename() {
        assertThrows(NullPointerException.class, () -> GeneralUtils.isValidFilename(null));
        assertThrows(IllegalArgumentException.class, () -> GeneralUtils.isValidFilename(""));
        assertDoesNotThrow(() -> GeneralUtils.isValidFilename("filename"));

        assertTrue(GeneralUtils.isValidFilename("filename"));
        assertTrue(GeneralUtils.isValidFilename("my_filename_123456789"));
        assertTrue(GeneralUtils.isValidFilename("my_filename_123456789.txt"));
        assertTrue(GeneralUtils.isValidFilename("my_filename_123456789...txt"));

        assertFalse(GeneralUtils.isValidFilename("$%^&*()"));
        assertFalse(GeneralUtils.isValidFilename("<>:/"));
    }
}
