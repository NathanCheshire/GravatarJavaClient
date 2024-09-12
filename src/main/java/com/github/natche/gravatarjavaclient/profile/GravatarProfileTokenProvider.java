package com.github.natche.gravatarjavaclient.profile;

import com.google.common.base.Preconditions;

import java.util.function.Supplier;

/**
 * An authentication token provider for Gravatar API requests.
 */
public final class GravatarProfileTokenProvider {
    private final Supplier<byte[]> tokenProvider;

    /**
     * The source/key, this is used for hash and equal comparisons.
     */
    private final String source;

    /**
     * Constructs a new token provider.
     *
     * @param tokenProvider the encapsulated provider
     * @param source the source/key, this is used for hash and equals comparisons
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if source is empty
     */
    public GravatarProfileTokenProvider(Supplier<byte[]> tokenProvider, String source) {
        Preconditions.checkNotNull(tokenProvider);
        Preconditions.checkNotNull(source);
        Preconditions.checkArgument(!source.trim().isEmpty());

        this.tokenProvider = tokenProvider;
        this.source = source;
    }

    /**
     * Returns the token.
     *
     * @return the token
     */
    public byte[] getToken() {
        return tokenProvider.get();
    }

    /**
     * Returns a hashcode representation of this object.
     *
     * @return a hashcode representation of this object
     */
    @Override
    public int hashCode() {
        return source.hashCode();
    }

    /**
     * Returns whether the provided object equals this.
     *
     * @param o the other object
     * @return whether the provided object equals this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof GravatarProfileTokenProvider)) return false;

        GravatarProfileTokenProvider other = (GravatarProfileTokenProvider) o;
        return source.equals(other.source);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileTokenProvider{source=\"" + source + "\"}";
    }
}
