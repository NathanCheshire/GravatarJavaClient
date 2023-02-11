package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * The request handler for sending out {@link GravatarImageRequest}s and parsing the returned response.
 */
public final class GravatarImageRequestHandler {
    /**
     * The hyper text transfer protocol string.
     */
    private static final String http = "http";

    /**
     * The safe hyper text transfer protocol string.
     */
    private static final String https = "https";

    /**
     * The base url for the image request API without the protocol prefix.
     */
    private static final String imageRequestBaseUrl = "://www.gravatar.com/avatar/";

    /**
     * The character to separate the email hash from the timestamp
     * when saving an image when a save file was not provided.
     */
    private static final String emailHashTimestampSeparator = "_";

    /**
     * The default rating to use if none is provided.
     */
    private static final GravatarRating defaultRating = GravatarRating.G;

    /**
     * The JPG extension appended to the end of an email hash if shouldAppendJpgSuffix is true.
     */
    private static final String jpgExtension = ".jpg";

    /**
     * The string to accompany {@link GravatarUrlParameter#FORCE_DEFAULT} to indicate the default url should be used.
     */
    private static final String forceDefaultUrlTrueString = "y";

    /**
     * Suppress default constructor.
     *
     * @throws AssertionError if invoked
     */
    private GravatarImageRequestHandler() {
        throw new AssertionError("Cannot create instances of GravatarImageRequestHandler");
    }


    public static String buildUrl(GravatarImageRequest gravatarImageRequest) {
        boolean fullParameters = gravatarImageRequest.shouldUseFullUrlParameterNames();
        boolean useHttps = gravatarImageRequest.shouldUseHttps();

        StringBuilder urlBuilder = new StringBuilder(useHttps ? https : http);
        urlBuilder.append(imageRequestBaseUrl);
        urlBuilder.append(gravatarImageRequest.getGravatarUserEmailHash());

        if (gravatarImageRequest.shouldAppendJpgSuffix()) {
            urlBuilder.append(jpgExtension);
        }

        String size = String.valueOf(gravatarImageRequest.getSize());
        urlBuilder.append(GravatarUrlParameter.SIZE
                .constructUrlParameterWithValue(size, true, fullParameters));

        ImmutableList<GravatarRating> ratings = ImmutableList.copyOf(gravatarImageRequest.getRatings());
        if (ratings.isEmpty()) ratings = ImmutableList.of(defaultRating);
        StringBuilder ratingsBuilder = new StringBuilder();
        ratings.forEach(rating -> ratingsBuilder.append(rating.getUrlParameter()));
        urlBuilder.append(GravatarUrlParameter.RATING
                .constructUrlParameterWithValue(ratingsBuilder.toString(), fullParameters));

        GravatarDefaultImageType defaultImageType = gravatarImageRequest.getDefaultImageType();
        String defaultImageUrl = gravatarImageRequest.getDefaultImageUrl();

        if (defaultImageType != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_TYPE
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), fullParameters));
        } else if (defaultImageUrl != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_URL
                    .constructUrlParameterWithValue(defaultImageUrl, fullParameters));
        }

        if (gravatarImageRequest.shouldForceDefaultImage()) {
            urlBuilder.append(GravatarUrlParameter.FORCE_DEFAULT
                    .constructUrlParameterWithValue(forceDefaultUrlTrueString, fullParameters));
        }

        return urlBuilder.toString();
    }

    /**
     * Returns a buffered image of the Gravatar image represented by the state of the provided request builder.
     *
     * @return the Gravatar url
     * @throws NullPointerException if the provided Gravatar image request is null
     * @throws GravatarJavaClientException if an exception reading from the built url occurs
     */
    public static BufferedImage getImage(GravatarImageRequest gravatarImageRequest) {
        Preconditions.checkNotNull(gravatarImageRequest);

        String url = buildUrl(gravatarImageRequest);

        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to get image from url: "
                    + url + ", error: " + e.getMessage());
        }
    }

    /**
     * Saves the buffered image returned by {@link #getImage(GravatarImageRequest)} to a file named using
     * the user email and the current timestamp.
     *
     * @throws NullPointerException if the provided Gravatar image request is null
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     * @throws IllegalArgumentException    if the file the image will be saved to already exists
     */
    public static File saveImage(GravatarImageRequest gravatarImageRequest) throws IOException {
        Preconditions.checkNotNull(gravatarImageRequest);

        String filename = gravatarImageRequest.getGravatarUserEmailHash()
                + emailHashTimestampSeparator + "user email hash_timestamp";
        File saveToFile = new File(filename);
        saveImage(gravatarImageRequest, saveToFile);
        return saveToFile;
    }

    /**
     * Saves the buffered image returned by {@link #getImage(GravatarImageRequest)} to the provided file.
     *
     * @param saveToFile the file to save the image to
     * @throws NullPointerException        if the provided image request or file is null
     * @throws IllegalArgumentException    if the file the image will be saved to already exists or is not a file
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     */
    public static void saveImage(GravatarImageRequest gravatarImageRequest, File saveToFile) throws IOException {
        Preconditions.checkNotNull(gravatarImageRequest);
        Preconditions.checkNotNull(saveToFile);
        Preconditions.checkArgument(saveToFile.isFile());
        Preconditions.checkArgument(!saveToFile.exists());

        ImageIO.write(getImage(gravatarImageRequest), "jpg", saveToFile);
    }
}
