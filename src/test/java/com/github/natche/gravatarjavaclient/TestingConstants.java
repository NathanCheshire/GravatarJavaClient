package com.github.natche.gravatarjavaclient;

/**
 * Constants related to testing.
 */
public final class TestingConstants {
    /**
     * The image URL used for testing.
     */
    public static final String foreignImageUrl = "https://picsum.photos/seed/gravatar-java-client/200/300";
    public static final String otherForeignImageUrl = "https://picsum.photos/seed/gravatar-java-client/500/300";
    public static final String silverPreview = "https://nathancheshire.com/static/media/silverpreview.17963d7d61a9d67498c0.png";

    /**
     * Suppress default constructor.
     */
    private TestingConstants() {
        throw new AssertionError("Cannot create instances of TestingConstants");
    }
}
