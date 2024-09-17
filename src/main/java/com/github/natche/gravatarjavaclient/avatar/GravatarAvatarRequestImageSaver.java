package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.ResourceReader;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * The singleton responsible for saving images to the local file system.
 */
enum GravatarAvatarRequestImageSaver {
    /**
     * The Gravatar Avatar request image saver singleton instance.
     */
    INSTANCE;

    /**
     * The image formats the current system supports saving to via {@link ImageIO}.
     * See {@link ImageIO#getWriterFormatNames()}.
     */
    private final ImmutableList<String> SUPPORTED_IMAGE_FORMATS = ImmutableList.copyOf(
            Arrays.stream(ImageIO.getWriterFormatNames())
                    .map(String::toLowerCase)
                    .distinct()
                    .toList()
    );

    /**
     * Saves the image to the provided file in the specified format.
     *
     * @param file   The file to save the image to
     * @param format The format in which to save the image (must be a valid format)
     * @throws NullPointerException        if any parameter is null
     * @throws IllegalArgumentException    if the provided file is a directory, if the provided format
     *                                     is not supported by the current file system, or if the file name
     *                                     is invalid
     * @throws GravatarJavaClientException if the file write fails
     */
    public boolean saveTo(BufferedImage image, File file, String format) {
        Preconditions.checkNotNull(image);
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(!file.isDirectory());
        Preconditions.checkNotNull(format);
        Preconditions.checkArgument(SUPPORTED_IMAGE_FORMATS.contains(format.toLowerCase()));
        Preconditions.checkArgument(ResourceReader.isValidFilename(file.getName()));

        try {
            if (!ImageIO.write(image, format.toLowerCase(), file)) throw new IOException("Failed");
            return true;
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to write image to file system");
        }
    }
}
