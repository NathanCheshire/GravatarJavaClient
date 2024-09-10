package com.github.natche.gravatarjavaclient.profile;

import java.nio.charset.StandardCharsets;

/**
 * A token supplier for grabbing the Profile API key from the system environment.
 */
public final class TokenSupplier {
    private static final String KEY_NAME = "GRAVATAR_JAVA_CLIENT_GITHUB_API_KEY";

    private static final GravatarProfileTokenProvider tokenSupplier = new GravatarProfileTokenProvider(
            () -> System.getenv(KEY_NAME).getBytes(StandardCharsets.US_ASCII), "TokenSupplier class"
    );

    /**
     * Suppress default constructor
     */
    private TokenSupplier() {
        throw new AssertionError("Cannot create instances of TokenSupplier");
    }

    /**
     * Returns the token supplier to grab the API key from the system environment.
     *
     * @return the token supplier to grab the API key from the system environment.
     */
    public static GravatarProfileTokenProvider getTokenSupplier() {
        return tokenSupplier;
    }
}
