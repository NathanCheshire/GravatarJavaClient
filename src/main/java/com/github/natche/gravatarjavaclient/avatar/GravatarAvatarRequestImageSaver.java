package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

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
     * The Gravatar Avatar request image saver instance.
     */
    INSTANCE;

    /**
     * The image formats the current system supports saving to via {@link ImageIO}.
     */
    private final ImmutableList<String> SUPPORTED_IMAGE_FORMATS = ImmutableList.copyOf(
            Arrays.stream(ImageIO.getWriterFormatNames())
                    .map(String::toLowerCase)
                    .distinct()
                    .toList()
    );

    /**
     * Saves the image to the given file in the specified format.
     *
     * @param file   The file to save the image to
     * @param format The format in which to save the image (must be a valid format)
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if the provided file is a directory or if the provided format
     *                                  is not supported by the current file system.
     * @throws GravatarJavaClientException if the file write fails
     */
    @CanIgnoreReturnValue
    public boolean saveTo(BufferedImage image, File file, String format) {
        Preconditions.checkNotNull(image);
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(!file.isDirectory());
        Preconditions.checkNotNull(format);
        Preconditions.checkArgument(SUPPORTED_IMAGE_FORMATS.contains(format.toLowerCase()));

        try {
            return ImageIO.write(image, format.toLowerCase(), file);
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to write image to file system");
        }
    }
}
