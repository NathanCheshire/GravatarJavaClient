package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the implementation of the Gravatar image request builder.
 */
class GravatarImageRequestBuilderImplTest {
    /**
     * Creates a new instance of this class for testing purposes.
     */
    GravatarImageRequestBuilderImplTest() {}

    /**
     * Tests for creation of Gravatar image request builder impls.
     */
    @Test
    void testCreation() {
        assertThrows(NullPointerException.class, () -> new GravatarImageRequestBuilderImpl(null));
        assertThrows(IllegalArgumentException.class, () -> new GravatarImageRequestBuilderImpl(""));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarImageRequestBuilderImpl("invalid email"));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarImageRequestBuilderImpl("invalid@email"));
        assertDoesNotThrow(() -> new GravatarImageRequestBuilderImpl("valid@email.com"));
    }

    /**
     * Tests for the accessor and mutator methods.
     */
    @Test
    void testAccessorsMutators() {
        GravatarImageRequestBuilderImpl impl = new GravatarImageRequestBuilderImpl("valid@email.com");
        assertEquals("cb7529c9a7c3297760ec76e41bf77d0a", impl.getGravatarUserEmailHash());

        assertDoesNotThrow(() -> impl.setShouldAppendJpgSuffix(false));
        assertFalse(impl.shouldAppendJpgSuffix());
        assertDoesNotThrow(() -> impl.setShouldAppendJpgSuffix(true));
        assertTrue(impl.shouldAppendJpgSuffix());

        assertThrows(IllegalArgumentException.class, () -> impl.setSize(-1));
        assertThrows(IllegalArgumentException.class, () -> impl.setSize(0));
        assertThrows(IllegalArgumentException.class, () -> impl.setSize(2049));
        assertThrows(IllegalArgumentException.class, () -> impl.setSize(2050));
        assertDoesNotThrow(() -> impl.setSize(1));
        assertEquals(1, impl.getSize());
        assertDoesNotThrow(() -> impl.setSize(2));
        assertEquals(2, impl.getSize());
        assertDoesNotThrow(() -> impl.setSize(100));
        assertEquals(100, impl.getSize());
        assertDoesNotThrow(() -> impl.setSize(2048));
        assertEquals(2048, impl.getSize());

        assertThrows(NullPointerException.class, () -> impl.setRating(null));
        assertDoesNotThrow(() -> impl.setRating(GravatarRating.G));
        assertEquals(GravatarRating.G, impl.getRating());
        assertDoesNotThrow(() -> impl.setRating(GravatarRating.PG));
        assertEquals(GravatarRating.PG, impl.getRating());
        assertDoesNotThrow(() -> impl.setRating(GravatarRating.R));
        assertEquals(GravatarRating.R, impl.getRating());
        assertDoesNotThrow(() -> impl.setRating(GravatarRating.X));
        assertEquals(GravatarRating.X, impl.getRating());

        assertDoesNotThrow(() -> impl.setForceDefaultImage(true));
        assertTrue(impl.shouldForceDefaultImage());
        assertDoesNotThrow(() -> impl.setForceDefaultImage(false));
        assertFalse(impl.shouldForceDefaultImage());

        assertThrows(NullPointerException.class, () -> impl.setDefaultImageType(null));
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType._404));
        assertEquals(GravatarDefaultImageType._404, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.MYSTERY_PERSON));
        assertEquals(GravatarDefaultImageType.MYSTERY_PERSON, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.IDENT_ICON));
        assertEquals(GravatarDefaultImageType.IDENT_ICON, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.MONSTER_ID));
        assertEquals(GravatarDefaultImageType.MONSTER_ID, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.WAVATAR));
        assertEquals(GravatarDefaultImageType.WAVATAR, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.RETRO));
        assertEquals(GravatarDefaultImageType.RETRO, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.ROBO_HASH));
        assertEquals(GravatarDefaultImageType.ROBO_HASH, impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.BLANK));
        assertEquals(GravatarDefaultImageType.BLANK, impl.getDefaultImageType());

        assertThrows(NullPointerException.class, () -> impl.setDefaultImageUrl(null));
        assertThrows(IllegalArgumentException.class, () -> impl.setDefaultImageUrl(""));
        assertThrows(IllegalArgumentException.class, () -> impl.setDefaultImageUrl("invalid url"));
        assertDoesNotThrow(() -> impl.setDefaultImageUrl(
                "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png"));
        assertEquals("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png",
                impl.getDefaultImageUrl());
        assertNull(impl.getDefaultImageType());
        assertDoesNotThrow(() -> impl.setDefaultImageType(GravatarDefaultImageType.ROBO_HASH));
        assertNull(impl.getDefaultImageUrl());

        assertDoesNotThrow(() -> impl.setUseHttps(true));
        assertTrue(impl.shouldUseHttps());
        assertDoesNotThrow(() -> impl.setUseHttps(false));
        assertFalse(impl.shouldUseHttps());

        assertDoesNotThrow(() -> impl.setUseFullUrlParameterNames(true));
        assertTrue(impl.shouldUseFullUrlParameterNames());
        assertDoesNotThrow(() -> impl.setUseFullUrlParameterNames(false));
        assertFalse(impl.shouldUseFullUrlParameterNames());
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    public void testHashCode() {
        GravatarImageRequestBuilderImpl impl1 = new GravatarImageRequestBuilderImpl("nathan@email.com");
        GravatarImageRequestBuilderImpl equalImpl1 = new GravatarImageRequestBuilderImpl("nathan@email.com");
        GravatarImageRequestBuilderImpl nonEqualImpl1 = new GravatarImageRequestBuilderImpl("e@email.com");

        assertEquals(-1014743006, impl1.hashCode());
        assertEquals(-1014743006, equalImpl1.hashCode());
        assertEquals(-1249353989, nonEqualImpl1.hashCode());
        assertEquals(impl1.hashCode(), equalImpl1.hashCode());
        assertNotEquals(equalImpl1.hashCode(), nonEqualImpl1.hashCode());
        assertNotEquals(equalImpl1.hashCode(), new Object().hashCode());
    }

    /**
     * Tests for the to string method.
     */
    @Test
    public void testToString() {
        GravatarImageRequestBuilderImpl impl1 = new GravatarImageRequestBuilderImpl("nathan@email.com");
        assertEquals("GravatarImageRequestBuilder{hash=\"9d4806832c56ee86c6aae26889c53c67\","
                + " shouldAppendJpgSuffix=true, size=80, rating=G, forceDefaultImage=false, defaultImageType=null,"
                + " defaultImageUrl=\"null\", useHttps=true, useFullUrlParameterNames=false}", impl1.toString());

        GravatarImageRequestBuilderImpl impl2 = new GravatarImageRequestBuilderImpl("nathan@email.com")
                .setUseHttps(false)
                .setDefaultImageType(GravatarDefaultImageType.RETRO)
                .setRating(GravatarRating.G)
                .setForceDefaultImage(true)
                .setUseFullUrlParameterNames(true)
                .setShouldAppendJpgSuffix(false);
        assertEquals("GravatarImageRequestBuilder{hash=\"9d4806832c56ee86c6aae26889c53c67\","
                + " shouldAppendJpgSuffix=false, size=80, rating=G, forceDefaultImage=true,"
                + " defaultImageType=RETRO, defaultImageUrl=\"null\", useHttps=false,"
                + " useFullUrlParameterNames=true}", impl2.toString());

        GravatarImageRequestBuilderImpl allSetters = new GravatarImageRequestBuilderImpl("nathan@email.com")
                .setUseHttps(false)
                .setDefaultImageType(GravatarDefaultImageType.RETRO)
                .setRating(GravatarRating.X)
                .setForceDefaultImage(true)
                .setDefaultImageUrl("https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png")
                .setUseFullUrlParameterNames(true)
                .setShouldAppendJpgSuffix(false);
        assertEquals("GravatarImageRequestBuilder{hash=\"9d4806832c56ee86c6aae26889c53c67\","
                + " shouldAppendJpgSuffix=false, size=80, rating=X, forceDefaultImage=true, defaultImageType=null,"
                + " defaultImageUrl=\"https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png\","
                + " useHttps=false, useFullUrlParameterNames=true}", allSetters.toString());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    public void testEquals() {
        GravatarImageRequestBuilderImpl impl1 = new GravatarImageRequestBuilderImpl("nathan@email.com");
        GravatarImageRequestBuilderImpl equalImpl1 = new GravatarImageRequestBuilderImpl("nathan@email.com");
        GravatarImageRequestBuilderImpl nonEqualImpl1 = new GravatarImageRequestBuilderImpl("e@email.com");

        assertEquals(impl1, impl1);
        assertEquals(impl1, equalImpl1);
        assertNotEquals(equalImpl1, nonEqualImpl1);
        assertNotEquals(equalImpl1, new Object());

        GravatarImageRequestBuilderImpl truthBuilder = new GravatarImageRequestBuilderImpl("n@email.com");
        assertEquals(truthBuilder, new GravatarImageRequestBuilderImpl("n@email.com")
                .setShouldAppendJpgSuffix(false));
        assertNotEquals(truthBuilder, new GravatarImageRequestBuilderImpl("something@email.com")
                .setShouldAppendJpgSuffix(true));
    }
}
