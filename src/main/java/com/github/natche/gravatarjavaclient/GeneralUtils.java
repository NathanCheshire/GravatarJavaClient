package com.github.natche.gravatarjavaclient;

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
 * General utility methods used throughout the GravatarJavaClient API.
 */
final class GeneralUtils {
    /**
     * The hashing algorithm used to transform a user email address into their gravatar UUID.
     */
    private static final String hashingAlgorithm = "MD5";

    /**
     * Suppress default constructor.
     *
     * @throws AssertionError if invoked
     */
    private GeneralUtils() {
        throw new AssertionError("Cannot create instances of GeneralUtils");
    }

    /**
     * Hashes the provided email address to obtain the MD5 hash
     * corresponding to the Gravatar icon linked to the provided email address.
     *
     * Algorithm steps:
     * <ul>
     *     <li>Trim leading and trailing whitespace</li>
     *     <li>Force all characters to be lower-case</li>
     *     <li>MD5 hash the final string</li>
     * </ul>
     *
     * @param emailAddress the email address to hash
     * @return the MD5 hash for the provided email address
     * @throws NullPointerException if the provided email address is null
     * @throws IllegalArgumentException if the provided email address is empty or invalid
     * @throws GravatarJavaClientException if any other exception occurs
     */
    static String emailAddressToGravatarHash(String emailAddress) throws GravatarJavaClientException {
        Preconditions.checkNotNull(emailAddress, "email address cannot be null");
        Preconditions.checkArgument(!emailAddress.isEmpty(), "email address cannot be empty");
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(emailAddress), "Malformed email address");

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(hashingAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new GravatarJavaClientException(e.getMessage());
        }

        CharBuffer charBuffer = CharBuffer.wrap(emailAddress.trim().toLowerCase().toCharArray());
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());

        byte[] digest = messageDigest.digest(bytes);

        BigInteger number = new BigInteger(1, digest);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
