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
    public static final String meAtTheZoo = "https://img.youtube.com/vi/jNQXAC9IVRw/0.jpg";

    /**
     * Suppress default constructor.
     */
    private TestingConstants() {
        throw new AssertionError("Cannot create instances of TestingConstants");
    }
}
