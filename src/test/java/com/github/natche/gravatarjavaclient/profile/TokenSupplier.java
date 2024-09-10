package com.github.natche.gravatarjavaclient.profile;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/**
 * A token supplier for grabbing the Profile API key from the system environment.
 */
public final class TokenSupplier {
    private static final String KEY_NAME = "GRAVATAR_JAVA_CLIENT_GITHUB_API_KEY";

    private static final Supplier<byte[]> tokenSupplier = () -> {
        try {
            return System.getenv(KEY_NAME).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return new byte[0];
        }
    };

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
    public static Supplier<byte[]> getTokenSupplier() {
        return tokenSupplier;
    }
}
