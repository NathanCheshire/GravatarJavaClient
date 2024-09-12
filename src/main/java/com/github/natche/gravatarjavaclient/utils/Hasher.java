package com.github.natche.gravatarjavaclient.utils;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * A class used for hashing inputs using certain algorithms.
 */
public final class Hasher {
    /**
     * The SHA256 algorithm.
     */
    private static final String SHA_256 = "SHA-256";
    private static final int HEX_BASE = 16;

    /**
     * The encapsulated algorithm ID to use when hashing.
     */
    private final String algorithm;

    private Hasher(String algorithm) {

        this.algorithm = algorithm;
    }

    /**
     * Returns a new Hasher instance using the SHA256 algorithm.
     *
     * @return a new Hasher instance using the SHA256 algorithm
     */
    public static Hasher fromSha256() {
        return new Hasher(SHA_256);
    }

    /**
     * Returns a new Hasher instance using the provided algorithm.
     *
     * @param algorithm the algorithm
     * @return a new Hasher instance using the provided algorithm
     * @throws NullPointerException if the provided algorithm is null
     * @throws IllegalArgumentException if the provided algorithm is empty
     */
    public static Hasher fromAlgorithm(String algorithm) {
        Preconditions.checkNotNull(algorithm);
        Preconditions.checkArgument(!algorithm.trim().isEmpty());

        return new Hasher(algorithm);
    }

    /**
     * Hashes the provided input using the encapsulated algorithm.
     * Note: prior to hashing, the input is trimmed and converted to lower case.
     *
     * @param input the input to hash
     * @return the hashed input
     * @throws NullPointerException if the provided input is null
     */
    public String hash(String input) {
        Preconditions.checkNotNull(input);

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new GravatarJavaClientException(e.getMessage());
        }

        CharBuffer charBuffer = CharBuffer.wrap(input.trim().toLowerCase().toCharArray());
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);

        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        byte[] digest = messageDigest.digest(bytes);

        BigInteger number = new BigInteger(1, digest);
        return number.toString(HEX_BASE);
    }

    /**
     * Returns whether the provided object equals this object.
     *
     * @param o the other object
     * @return whether the provided object equals this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof Hasher)) return false;

        Hasher other = (Hasher) o;
        return this.algorithm.equals(other.algorithm);
    }

    /**
     * Returns a hash code of this object.
     *
     * @return a hash code of this object
     */
    @Override
    public int hashCode() {
        return 31 * algorithm.hashCode();
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "Hasher{algorithm=\"" + algorithm + "\"}";
    }
}
