package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.TestingConstants;
import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GravatarImageRequestHandler}.
 */
class GravatarImageRequestHandlerTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    GravatarImageRequestHandlerTest() {}

    @BeforeEach
    void beforeEach() {
        //noinspection ResultOfMethodCallIgnored
        new File("tmp_file.png").delete();
    }

    /**
     * Tests to ensure reflection is guarded against.
     * Also helps reach 100% code coverage for testing.
     */
    @Test
    void testCreation() {
        try {
            Constructor<GravatarImageRequestHandler> constructor =
                    GravatarImageRequestHandler.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (Exception e) {
            assertTrue(e instanceof InvocationTargetException);
            Throwable target = ((InvocationTargetException) e).getTargetException();
            assertTrue(target instanceof AssertionError);
            assertEquals("Cannot create instances of GravatarImageRequestHandler", target.getMessage());
        }
    }

    /**
     * Tests for the setter methods of the handler.
     */
    @Test
    void testSetters() {
        assertThrows(NullPointerException.class, () -> GravatarImageRequestHandler.setSaveFileFormatter(null));
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setSaveFileFormatter(new SimpleDateFormat("")));
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setSaveFileFormatter(new SimpleDateFormat("mm")));

        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.setEmailHashTimestampSeparator(null));
        assertThrows(IllegalArgumentException.class,
                () -> GravatarImageRequestHandler.setEmailHashTimestampSeparator(""));
        assertThrows(IllegalArgumentException.class,
                () -> GravatarImageRequestHandler.setEmailHashTimestampSeparator("<>"));
        assertThrows(IllegalArgumentException.class,
                () -> GravatarImageRequestHandler.setEmailHashTimestampSeparator("%:^&"));
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setEmailHashTimestampSeparator("_separator_"));

        assertDoesNotThrow(() -> GravatarImageRequestHandler.setAppendJpgSuffixByDefault(false));
        assertFalse(GravatarImageRequestHandler.shouldAppendJpgSuffixByDefault());
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setAppendJpgSuffixByDefault(true));
        assertTrue(GravatarImageRequestHandler.shouldAppendJpgSuffixByDefault());

        assertDoesNotThrow(() -> GravatarImageRequestHandler.setDefaultRating(GravatarRating.X));
        assertEquals(GravatarRating.X, GravatarImageRequestHandler.getDefaultRating());
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setDefaultRating(GravatarRating.R));
        assertEquals(GravatarRating.R, GravatarImageRequestHandler.getDefaultRating());

        assertDoesNotThrow(() -> GravatarImageRequestHandler.setUseHttpsByDefault(false));
        assertFalse(GravatarImageRequestHandler.shouldUseHttpsByDefault());
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setUseHttpsByDefault(true));
        assertTrue(GravatarImageRequestHandler.shouldUseHttpsByDefault());

        assertDoesNotThrow(() -> GravatarImageRequestHandler.setUseFullParameterNamesByDefault(false));
        assertFalse(GravatarImageRequestHandler.shouldUseFullParameterNamesByDefault());
        assertDoesNotThrow(() -> GravatarImageRequestHandler.setUseFullParameterNamesByDefault(true));
        assertTrue(GravatarImageRequestHandler.shouldUseFullParameterNamesByDefault());
    }

    /**
     * Tests for the build url method.
     */
    @Test
    void testBuildUrl() {
        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.buildUrl(null));

        GravatarImageRequestBuilderImpl minimalImpl =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com");
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g",
                GravatarImageRequestHandler.buildUrl(minimalImpl));

        GravatarImageRequestBuilderImpl sizeImpl =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com").setSize(500);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=500&r=g",
                GravatarImageRequestHandler.buildUrl(sizeImpl));

        GravatarImageRequestBuilderImpl withoutJpgExtension =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setSize(500)
                        .setShouldAppendJpgSuffix(false);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7?s=500&r=g",
                GravatarImageRequestHandler.buildUrl(withoutJpgExtension));

        GravatarImageRequestBuilderImpl withoutHttps =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setSize(500)
                        .setShouldAppendJpgSuffix(false)
                        .setUseHttps(false);
        assertEquals("http://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7?s=500&r=g",
                GravatarImageRequestHandler.buildUrl(withoutHttps));

        GravatarImageRequestBuilderImpl gRating =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setRating(GravatarRating.G);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g",
                GravatarImageRequestHandler.buildUrl(gRating));

        GravatarImageRequestBuilderImpl pgRating =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setRating(GravatarRating.PG);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=pg",
                GravatarImageRequestHandler.buildUrl(pgRating));

        GravatarImageRequestBuilderImpl rRating =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setRating(GravatarRating.R);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=r",
                GravatarImageRequestHandler.buildUrl(rRating));

        GravatarImageRequestBuilderImpl xRating =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setRating(GravatarRating.X);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=x",
                GravatarImageRequestHandler.buildUrl(xRating));

        GravatarImageRequestBuilderImpl _404ImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType._404);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=404",
                GravatarImageRequestHandler.buildUrl(_404ImageType));

        GravatarImageRequestBuilderImpl mysteryPersonImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.MYSTERY_PERSON);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=mp",
                GravatarImageRequestHandler.buildUrl(mysteryPersonImageType));

        GravatarImageRequestBuilderImpl identIconImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.IDENT_ICON);
        assertEquals(
                "https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=identicon",
                GravatarImageRequestHandler.buildUrl(identIconImageType));

        GravatarImageRequestBuilderImpl monsterIdImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.MONSTER_ID);
        assertEquals(
                "https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=monsterid",
                GravatarImageRequestHandler.buildUrl(monsterIdImageType));

        GravatarImageRequestBuilderImpl wavatarImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.WAVATAR);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=wavatar",
                GravatarImageRequestHandler.buildUrl(wavatarImageType));

        GravatarImageRequestBuilderImpl retroImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.RETRO);
        assertEquals(
                "https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=retro",
                GravatarImageRequestHandler.buildUrl(retroImageType));

        GravatarImageRequestBuilderImpl roboHashImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH);
        assertEquals(
                "https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=robohash",
                GravatarImageRequestHandler.buildUrl(roboHashImageType));

        GravatarImageRequestBuilderImpl blankImageType =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.BLANK);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=blank",
                GravatarImageRequestHandler.buildUrl(blankImageType));

        GravatarImageRequestBuilderImpl defaultImageUrl =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageUrl(TestingConstants.foreignImageUrl);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&"
                        + "d=" + TestingConstants.foreignImageUrl,
                GravatarImageRequestHandler.buildUrl(defaultImageUrl));

        GravatarImageRequestBuilderImpl defaultImageUrlOverridden =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageUrl(TestingConstants.foreignImageUrl);
        defaultImageUrlOverridden.setDefaultImageType(GravatarDefaultImageType.RETRO);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=retro",
                GravatarImageRequestHandler.buildUrl(defaultImageUrlOverridden));

        GravatarImageRequestBuilderImpl defaultImageTypeOverridden =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.RETRO);
        defaultImageTypeOverridden.setDefaultImageUrl(
                TestingConstants.foreignImageUrl);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7"
                        + ".jpg?s=80&r=g&d=" + TestingConstants.foreignImageUrl,
                GravatarImageRequestHandler.buildUrl(defaultImageTypeOverridden));

        GravatarImageRequestBuilderImpl forceDefaultImage =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageUrl(TestingConstants.foreignImageUrl)
                        .setForceDefaultImage(true);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg"
                        + "?s=80&r=g&d=" + TestingConstants.foreignImageUrl + "&f=y",
                GravatarImageRequestHandler.buildUrl(forceDefaultImage));

        GravatarImageRequestBuilderImpl forceDefaultImageWithNoUrl =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setForceDefaultImage(true);
        assertThrows(GravatarJavaClientException.class,
                () -> GravatarImageRequestHandler.buildUrl(forceDefaultImageWithNoUrl));

        GravatarImageRequestBuilderImpl fullUrlParameters =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setSize(690)
                        .setUseFullUrlParameterNames(true)
                        .setRating(GravatarRating.X)
                        .setUseHttps(false)
                        .setShouldAppendJpgSuffix(true)
                        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH);
        assertEquals("http://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7"
                + ".jpg?size=690&rating=x&default=robohash", GravatarImageRequestHandler.buildUrl(fullUrlParameters));
    }

    /**
     * Tests for the get image method.
     */
    @Test
    void testGetImage() {
        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.getImage(null));

        GravatarImageRequestBuilderImpl forceDefaultImage =
                new GravatarImageRequestBuilderImpl("email@domain.com")
                        .setDefaultImageUrl(TestingConstants.meAtTheZoo)
                        .setForceDefaultImage(true);
        AtomicReference<BufferedImage> bi = new AtomicReference<>();
        assertDoesNotThrow(() -> bi.set(GravatarImageRequestHandler.getImage(forceDefaultImage)));
        assertEquals(480, bi.get().getWidth());
        assertEquals(360, bi.get().getHeight());
    }

    /**
     * Tests for the saveImage which save directly to a provided pointer file returns true.
     */
    @Test
    void testSaveImageReturnsTrue() {
        String userEmail = "nathan.vincent.2.718@gmail.com";
        GravatarImageRequestBuilderImpl builder = new GravatarImageRequestBuilderImpl(userEmail)
                .setDefaultImageUrl(TestingConstants.meAtTheZoo)
                .setForceDefaultImage(true);
        File tmpFile = new File("tmp_file.png");
        assertFalse(tmpFile.exists());

        try {
            assertTrue(GravatarImageRequestHandler.saveImage(builder, tmpFile));
        } catch (Exception ignored) {
            // do nothing
        }
    }

    /**
     * Tests for the save image method.
     */
    @Test
    void testSaveImage() {
        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.saveImage(null));
        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.saveImage(null, null));
        String userEmail = "email@domain.com";
        GravatarImageRequestBuilderImpl builder = new GravatarImageRequestBuilderImpl(userEmail)
                .setDefaultImageUrl(TestingConstants.meAtTheZoo)
                .setForceDefaultImage(true);
        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.saveImage(builder, null));
        assertThrows(IllegalStateException.class,
                () -> GravatarImageRequestHandler.saveImage(builder, new File(".")));
        File tmpFile = new File("tmp_file.png");
        try {
            assertTrue(tmpFile.createNewFile());
        } catch (IOException ignored) {
            // Don't care for unit test
        }
        assertTrue(tmpFile.exists());
        assertThrows(IllegalStateException.class,
                () -> GravatarImageRequestHandler.saveImage(builder, tmpFile));
        assertTrue(tmpFile.delete());
        assertFalse(tmpFile.exists());

        File builderImageFile = new File("Builder.png");
        assertDoesNotThrow(() -> GravatarImageRequestHandler.saveImage(builder, builderImageFile));
        assertTrue(builderImageFile.exists());
        assertTrue(builderImageFile.delete());
        assertFalse(builderImageFile.exists());

        AtomicReference<File> savedToFile = new AtomicReference<>();
        assertDoesNotThrow(() -> savedToFile.set(GravatarImageRequestHandler.saveImage(builder)));
        assertTrue(savedToFile.get().exists());

        String fileName = savedToFile.get().getName();
        String[] parts = fileName.split("-");
        assertEquals(GeneralUtils.emailAddressToGravatarHash(userEmail), parts[0]);
        assertTrue(savedToFile.get().delete());
        assertFalse(savedToFile.get().exists());

        String sep = "_sep_";
        GravatarImageRequestHandler.setEmailHashTimestampSeparator(sep);
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        GravatarImageRequestHandler.setSaveFileFormatter(formatter);
        assertDoesNotThrow(() -> savedToFile.set(GravatarImageRequestHandler.saveImage(builder)));
        assertTrue(savedToFile.get().exists());

        fileName = savedToFile.get().getName();
        parts = fileName.split(sep);
        assertEquals(GeneralUtils.emailAddressToGravatarHash(userEmail), parts[0]);
        assertEquals(formatter.format(new Date()) + ".jpg", parts[1]);
        assertTrue(savedToFile.get().delete());
        assertFalse(savedToFile.get().exists());
    }
}
