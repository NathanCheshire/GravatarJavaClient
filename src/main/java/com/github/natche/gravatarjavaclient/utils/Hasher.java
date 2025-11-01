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
 * A singleton for hashing inputs.
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Hasher {
    /**
     * The SHA256 hasher.
     */
    SHA256("SHA-256");

    private static final int HEX_BASE = 16;

    /**
     * The encapsulated algorithm ID to use when hashing.
     */
    private final String algorithm;

    Hasher(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Hashes the provided input using the encapsulated algorithm.
     * Note: prior to hashing, the input is trimmed and converted to lower case.
     *
     * @param input the input to hash
     * @return the hashed input
     * @throws NullPointerException if the provided input is null
     * @throws GravatarJavaClientException if a {@link MessageDigest} instance cannot be obtained
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
}
