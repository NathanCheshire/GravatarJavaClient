package com.github.natche.gravatarjavaclient.utils;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

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
     * The invalid filename characters for Windows and Unix based systems.
     */
    private static final ImmutableList<Character> INVALID_FILENAME_CHARS = ImmutableList.of(
            '<', '>', ':', '\\', '|', '?', '*', '/', '\'', '"', '\u0000'
    );

    /**
     * The hashing algorithm used to transform a user email address into a Gravatar UUID
     * for obtaining Avatar images.
     */
    private static final String AVATAR_HASHING_ALGORITHM = "MD5";

    /**
     * The hashing algorithm used for the profiles API.
     */
    private static final String PROFILES_HASHING_ALGORITHM = "SHA-256";

    /**
     * The buffer size used by the {@link BufferedReader} when reading the contents of a URL.
     */
    private static final int READ_URL_BUFFER_SIZE = 1024;

    /**
     * The radix in the hexadecimal number system.
     */
    public static final int HEX_BASE = 16;

    /**
     * Suppress default constructor.
     *
     * @throws AssertionError if invoked
     */
    private GeneralUtils() {
        throw new AssertionError("Cannot create instances of GeneralUtils");
    }

    /**
     * Returns a new {@link BufferedImage} read from the provided URL.
     *
     * @param url the URL
     * @return the image read from the provided URL
     * @throws NullPointerException        if the provided URL is null
     * @throws IllegalArgumentException    if the provided URL is empty
     * @throws GravatarJavaClientException if an image cannot be read from the provided URL
     */
    public static BufferedImage readBufferedImage(String url) {
        Preconditions.checkNotNull(url);
        Preconditions.checkArgument(!url.isEmpty());

        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to get image from url: "
                    + url + ", error: " + e.getMessage());
        }
    }

    /**
     * Reads from the provided URL and returns the response data.
     *
     * @param url the URL to query and retrieve data from
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

            char[] chars = new char[READ_URL_BUFFER_SIZE];
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
     * Returns whether the provided filename is valid for this operating system.
     *
     * @param filename the filename
     * @return whether the provided filename is valid for this operating system
     * @throws NullPointerException     if the provided filename is null
     * @throws IllegalArgumentException if the provided filename is empty
     */
    public static boolean isValidFilename(String filename) {
        Preconditions.checkNotNull(filename);
        Preconditions.checkArgument(!filename.isEmpty());

        for (char c : filename.toCharArray()) {
            if (INVALID_FILENAME_CHARS.contains(c)) return false;
        }

        return true;
    }

    /**
     * Hashes the provided email address to obtain the MD5 hash
     * corresponding to a Gravatar Avatar linked to an email address.
     * <p>
     * Algorithm steps:
     * <ul>
     *     <li>Trim leading and trailing whitespace</li>
     *     <li>Force all characters to be lower-case</li>
     *     <li>MD5 hash the final string</li>
     * </ul>
     *
     * Note, while Google email addresses such as "email.address@gmail.com" and "emailaddress@gmail.com"
     * are equal in Google's eyes, they result in different profiles on Gravatar's side due to the hashes
     * being different. If using this method results in a problem as such, you will likely want to create
     * a decorator or wrapper method to ensure periods are stripped from the username.
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

        return hashInput(emailAddress.trim().toLowerCase(), AVATAR_HASHING_ALGORITHM);
    }

    /**
     * Hashes the provided email address and returns the SHA256 hash.
     *
     * @param emailAddress the email address
     * @return the hashed email address
     * @throws NullPointerException if the provided address is null
     * @throws IllegalArgumentException if the provided address is empty or not a valid address
     */
    public static String emailAddressToProfilesApiHash(String emailAddress) {
        Preconditions.checkNotNull(emailAddress);
        Preconditions.checkArgument(!emailAddress.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(emailAddress));

        return hashInput(emailAddress.trim().toLowerCase(), PROFILES_HASHING_ALGORITHM);
    }

    /**
     * Hashes the provided input using the provided algorithm.
     *
     * @param input            the input to hash
     * @param hashingAlgorithm the hashing algorithm to use
     * @return the hash for the provided input and hashing algorithm
     * @throws NullPointerException     if the provided input or algorithm is null
     * @throws IllegalArgumentException if the provided algorithm is empty
     * @throws GravatarJavaClientException if the provided hashing algorithm does not exist
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
        return number.toString(HEX_BASE);
    }

    /**
     * Skips any headers in the provided stream.
     *
     * @param stream the stream
     * @throws NullPointerException if the provided stream is null
     * @throws IOException if an IO exception occurs
     */
    public static void skipHeaders(BufferedReader stream) throws IOException {
        String line;
        while ((line = stream.readLine()) != null) if (line.isEmpty()) break;
    }

    /**
     * Reads a chunked body from the provided BufferedReader.
     *
     * @param reader the BufferedReader to read from
     * @return the complete response body
     * @throws NullPointerException if the provided reader is null
     * @throws IOException if an IO exception occurs
     * @throws NumberFormatException if the chunk size cannot be parsed
     */
    public static String readChunkedBody(BufferedReader reader) throws IOException {
        Preconditions.checkNotNull(reader);

        StringBuilder responseBody = new StringBuilder();

        while (true) {
            String chunkSizeLine = reader.readLine();
            if (chunkSizeLine == null || chunkSizeLine.isEmpty()) break;

            int chunkSize = Integer.parseInt(chunkSizeLine.trim(), HEX_BASE);
            if (chunkSize == 0) break; // End of chunks

            char[] chunk = new char[chunkSize];
            int totalBytesRead = 0;

            while (totalBytesRead < chunkSize) {
                int bytesRead = reader.read(chunk, totalBytesRead, chunkSize - totalBytesRead);
                if (bytesRead == -1) break; // End of stream
                totalBytesRead += bytesRead;
            }

            responseBody.append(chunk, 0, totalBytesRead);
            reader.readLine(); // Skip trailing CRLF after chunk
        }

        return responseBody.toString();
    }

}
