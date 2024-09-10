package com.github.natche.gravatarjavaclient.avatar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the {@link GravatarAvatarRequestImageSaver}.
 */
public class GravatarAvatarRequestImageSaverTest {
    /**
     * Sets the SUPPORTED_IMAGE_FORMATS to an ArrayList containing a blank string which will cause
     * the image write to fail. This is performed for testing coverage purposes.
     */
    @SuppressWarnings("unchecked") /* Reflection; expected */
    @BeforeEach
    public void injectBlankFormat() {
        try {
            Field formatsField = GravatarAvatarRequestImageSaver.class
                    .getDeclaredField("SUPPORTED_IMAGE_FORMATS");
            formatsField.setAccessible(true);
            ImmutableList<String> originalList
                    = (ImmutableList<String>) formatsField.get(GravatarAvatarRequestImageSaver.INSTANCE);
            List<String> mutableList = new ArrayList<>(originalList);
            mutableList.add("");
            formatsField.set(GravatarAvatarRequestImageSaver.INSTANCE, ImmutableList.copyOf(mutableList));
        } catch (Exception e) {
            // Swallow
        }
    }

    /**
     * Tests for the save to method.
     */
    @Test
    void testSaveTo() {
        assertThrows(NullPointerException.class,
                () -> GravatarAvatarRequestImageSaver.INSTANCE.saveTo(null, null, null));

        BufferedImage mockImage = mock(BufferedImage.class);
        assertThrows(NullPointerException.class,
                () -> GravatarAvatarRequestImageSaver.INSTANCE.saveTo(mockImage, null, null));

        File mockFile = mock(File.class);
        assertThrows(NullPointerException.class,
                () -> GravatarAvatarRequestImageSaver.INSTANCE.saveTo(mockImage, mockFile, null));
        assertThrows(IllegalArgumentException.class,
                () -> GravatarAvatarRequestImageSaver.INSTANCE.saveTo(mockImage, new File("."), null));

        assertThrows(GravatarJavaClientException.class,
                () -> GravatarAvatarRequestImageSaver.INSTANCE.saveTo(mockImage, mockFile, ""));
    }
}
