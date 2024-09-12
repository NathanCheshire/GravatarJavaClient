package com.github.natche.gravatarjavaclient.utils;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
     * The buffer size used by a {@link BufferedReader} when reading the contents of a URL.
     */
    private static final int READ_URL_BUFFER_SIZE = 1024;

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
     * Skips any headers in the provided stream.
     *
     * @param stream the stream
     * @throws NullPointerException if the provided stream is null
     * @throws IOException          if an IO exception occurs
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
     * @throws NullPointerException  if the provided reader is null
     * @throws IOException           if an IO exception occurs
     * @throws NumberFormatException if the chunk size cannot be parsed
     */
    public static String readChunkedBody(BufferedReader reader) throws IOException {
        Preconditions.checkNotNull(reader);

        StringBuilder responseBody = new StringBuilder();

        while (true) {
            String chunkSizeLine = reader.readLine();
            if (chunkSizeLine == null || chunkSizeLine.isEmpty()) break;

            int chunkSize = Integer.parseInt(chunkSizeLine.trim(), 16);
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
