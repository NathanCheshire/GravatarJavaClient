package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GravatarImageRequestHandler}.
 */
public class GravatarImageRequestHandlerTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    public GravatarImageRequestHandlerTest() {}

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
                        .setDefaultImageUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png");
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&"
                        + "d=https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png",
                GravatarImageRequestHandler.buildUrl(defaultImageUrl));

        GravatarImageRequestBuilderImpl defaultImageUrlOverridden =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png");
        defaultImageUrlOverridden.setDefaultImageType(GravatarDefaultImageType.RETRO);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg?s=80&r=g&d=retro",
                GravatarImageRequestHandler.buildUrl(defaultImageUrlOverridden));

        GravatarImageRequestBuilderImpl defaultImageTypeOverridden =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageType(GravatarDefaultImageType.RETRO);
        defaultImageTypeOverridden.setDefaultImageUrl(
                "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png");
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7"
                        + ".jpg?s=80&r=g&d=https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png",
                GravatarImageRequestHandler.buildUrl(defaultImageTypeOverridden));

        GravatarImageRequestBuilderImpl forceDefaultImage =
                new GravatarImageRequestBuilderImpl("nathan.vincent.2.718@gmail.com")
                        .setDefaultImageUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png")
                        .setForceDefaultImage(true);
        assertEquals("https://www.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7.jpg"
                        + "?s=80&r=g&d=https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png&f=y",
                GravatarImageRequestHandler.buildUrl(forceDefaultImage));
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
                        .setDefaultImageUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png")
                        .setForceDefaultImage(true);
        AtomicReference<BufferedImage> bi = new AtomicReference<>();
        assertDoesNotThrow(() -> bi.set(GravatarImageRequestHandler.getImage(forceDefaultImage)));
        assertEquals(255, bi.get().getWidth());
        assertEquals(383, bi.get().getHeight());
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
        GravatarImageRequestBuilderImpl minecraftBuilder = new GravatarImageRequestBuilderImpl(userEmail)
                .setDefaultImageUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png")
                .setForceDefaultImage(true);
        assertThrows(NullPointerException.class,
                () -> GravatarImageRequestHandler.saveImage(minecraftBuilder, null));
        assertThrows(IllegalArgumentException.class,
                () -> GravatarImageRequestHandler.saveImage(minecraftBuilder, new File(".")));
        File tmpFile = new File("tmp_file.png");
        try {
            assertTrue(tmpFile.createNewFile());
        } catch (IOException ignored) {
            // Don't care for unit test
        }
        assertTrue(tmpFile.exists());
        assertThrows(IllegalArgumentException.class,
                () -> GravatarImageRequestHandler.saveImage(minecraftBuilder, tmpFile));
        assertTrue(tmpFile.delete());
        assertFalse(tmpFile.exists());

        File minecraftImageFile = new File("Minecraft.png");
        assertDoesNotThrow(() -> GravatarImageRequestHandler.saveImage(minecraftBuilder, minecraftImageFile));
        assertTrue(minecraftImageFile.exists());
        assertTrue(minecraftImageFile.delete());
        assertFalse(minecraftImageFile.exists());

        AtomicReference<File> savedToFile = new AtomicReference<>();
        assertDoesNotThrow(() -> savedToFile.set(GravatarImageRequestHandler.saveImage(minecraftBuilder)));
        assertTrue(savedToFile.get().exists());

        String fileName = savedToFile.get().getName();
        String[] parts = fileName.split("-");
        assertEquals(GeneralUtils.emailAddressToGravatarHash(userEmail), parts[0]);
        assertTrue(savedToFile.get().delete());
        assertFalse(savedToFile.get().exists());
    }
}
