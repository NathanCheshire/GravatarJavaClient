package com.github.natche.gravatarjavaclient.utils;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * General utility methods used throughout the GravatarJavaClient.
 */
public final class GeneralUtils {
    /**
     * The hashing algorithm used to transform a user email address into their gravatar UUID.
     */
    private static final String hashingAlgorithm = "MD5";

    /**
     * The buffer size used by the {@link BufferedReader} when reading the contents of a URL.
     */
    private static final int readUrlBufferSize = 1024;

    /**
     * The number of digits in the hexadecimal base system.
     */
    private static final int hexBase = 16;

    /**
     * Suppress default constructor.
     *
     * @throws AssertionError if invoked
     */
    private GeneralUtils() {
        throw new AssertionError("Cannot create instances of GeneralUtils");
    }

    /**
     * Returns a buffered image read from the provided URL.
     *
     * @param url the URL
     * @return the URL from the provided image
     * @throws NullPointerException        if the provided URL is null
     * @throws IllegalArgumentException    if the provided URL is empty
     * @throws GravatarJavaClientException if an image cannot be read from the provided URL
     */
    public static BufferedImage readBufferedImage(String url) {
        Preconditions.checkNotNull(url);
        Preconditions.checkArgument(!url.isEmpty());

        try {
            return ImageIO.read(new URL(url));
        } catch (Exception e) {
            throw new GravatarJavaClientException("Failed to get image from url: "
                    + url + ", error: " + e.getMessage());
        }
    }

    /**
     * Reads from the provided URL and returns the response data.
     *
     * @param url the URL to ping and read data from
     * @return the contents of the provided URL
     * @throws NullPointerException        if the provided URL is null
     * @throws IllegalArgumentException    if the provided URL is empty
     * @throws GravatarJavaClientException if an exception occurs when reading from the provided URL
     */
    public static String readUrl(String url) {
        Preconditions.checkNotNull(url);
        Preconditions.checkArgument(!url.isEmpty());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            StringBuilder builder = new StringBuilder();

            char[] chars = new char[readUrlBufferSize];
            int read;
            while ((read = reader.read(chars)) != -1) {
                builder.append(chars, 0, read);
            }

            return builder.toString();
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to read contents of URL: " + url);
        }
    }

    /**
     * Hashes the provided email address to obtain the MD5 hash
     * corresponding to the Gravatar icon linked to the email address.
     * <p>
     * Algorithm steps:
     * <ul>
     *     <li>Trim leading and trailing whitespace</li>
     *     <li>Force all characters to be lower-case</li>
     *     <li>MD5 hash the final string</li>
     * </ul>
     *
     * @param emailAddress the email address to hash
     * @return the MD5 hash for the provided email address
     * @throws NullPointerException     if the provided email address is null
     * @throws IllegalArgumentException if the provided email address is empty or invalid
     */
    public static String emailAddressToGravatarHash(String emailAddress) {
        Preconditions.checkNotNull(emailAddress);
        Preconditions.checkArgument(!emailAddress.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(emailAddress));

        return hashInput(emailAddress.trim().toLowerCase(), hashingAlgorithm);
    }

    /**
     * Hashes the provided input using the provided algorithm.
     *
     * @param input            the input to hash
     * @param hashingAlgorithm the hashing algorithm to use
     * @return the hash for the provided input and hashing algorithm
     * @throws NullPointerException     if the provided input or algorithm is null
     * @throws IllegalArgumentException if the provided algorithm is empty
     */
    static String hashInput(String input, String hashingAlgorithm) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(hashingAlgorithm);
        Preconditions.checkArgument(!hashingAlgorithm.isEmpty());

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(hashingAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new GravatarJavaClientException(e.getMessage());
        }

        CharBuffer charBuffer = CharBuffer.wrap(input.trim().toLowerCase().toCharArray());
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);

        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        byte[] digest = messageDigest.digest(bytes);

        BigInteger number = new BigInteger(1, digest);
        return number.toString(hexBase);
    }
}
