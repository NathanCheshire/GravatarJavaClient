package com.github.natche.gravatarjavaclient;

/**
 * Constants related to testing.
 */
public final class TestingImageUrls {
    public static final String foreignImageUrl = "https://picsum.photos/seed/gravatar-java-client/200/300";
    public static final String anotherForeignImageUrl = "https://picsum.photos/seed/gravatar-java-client/300/300";

    /**
     * Suppress default constructor.
     */
    private TestingImageUrls() {
        throw new AssertionError("Cannot create instances of TestingConstants");
    }
}
