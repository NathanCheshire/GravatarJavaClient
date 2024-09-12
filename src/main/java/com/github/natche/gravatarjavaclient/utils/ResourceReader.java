package com.github.natche.gravatarjavaclient.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A class for reading resources such as files or URLs as data, images, etc.
 */
public final class ResourceReader {
    /**
     * The invalid filename characters for Windows and Unix based systems.
     */
    private static final ImmutableList<Character> INVALID_FILENAME_CHARS = ImmutableList.of(
            '<', '>', ':', '\\', '|', '?', '*', '/', '\'', '"', '\u0000'
    );

    /**
     * The path to the resource such as a file path or a URL.
     */
    private final BufferedReader resourceReader;

    private ResourceReader(BufferedReader resourceReader) {
        this.resourceReader = resourceReader;
    }

    /**
     * Constructs and returns a new ResourceReader from the provided reader.
     *
     * @param resourceReader the buffered reader to read the resource
     * @return a new ResourceReader object
     * @throws NullPointerException if the provided resourceReader is null
     */
    public static ResourceReader from(BufferedReader resourceReader) {
        Preconditions.checkNotNull(resourceReader);

        return new ResourceReader(resourceReader);
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
     * Skips any headers of the encapsulated reader.
     *
     * @throws IOException          if an IO exception occurs
     * @return this reader
     */
    public ResourceReader skipHeaders() throws IOException {
        String line;
        while ((line = this.resourceReader.readLine()) != null) if (line.isEmpty()) break;
        return this;
    }

    /**
     * Reads a chunked body from encapsulated reader.
     *
     * @return the complete response body
     * @throws IOException           if an IO exception occurs
     * @throws NumberFormatException if the chunk size cannot be parsed
     */
    public String readChunkedBody() throws IOException {
        StringBuilder responseBody = new StringBuilder();

        while (true) {
            String chunkSizeLine = this.resourceReader.readLine();
            if (chunkSizeLine == null || chunkSizeLine.isEmpty()) break;

            int chunkSize = Integer.parseInt(chunkSizeLine.trim(), 16);
            if (chunkSize == 0) break; // End of chunks

            char[] chunk = new char[chunkSize];
            int totalBytesRead = 0;

            while (totalBytesRead < chunkSize) {
                int bytesRead = this.resourceReader.read(chunk, totalBytesRead, chunkSize - totalBytesRead);
                if (bytesRead == -1) break; // End of stream
                totalBytesRead += bytesRead;
            }

            responseBody.append(chunk, 0, totalBytesRead);
            this.resourceReader.readLine(); // Skip trailing CRLF after chunk
        }

        return responseBody.toString();
    }

    /**
     * Returns a hashcode representation of this object.
     *
     * @return a hashcode representation of this object
     */
    @Override
    public int hashCode() {
        return 31 * this.resourceReader.hashCode();
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "ResourceReader{resourceReader=" + this.resourceReader + "}";
    }

    /**
     * Returns whether the provided object is equal to this.
     *
     * @param o the other object
     * @return whether the provided object is equal to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof ResourceReader)) return false;

        ResourceReader other = (ResourceReader) o;
        return other.resourceReader.equals(resourceReader);
    }
}
