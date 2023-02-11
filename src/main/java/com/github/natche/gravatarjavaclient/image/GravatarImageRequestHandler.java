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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The request handler for sending out {@link GravatarImageRequestBuilder}s and parsing the returned response.
 */
public final class GravatarImageRequestHandler {
    /**
     * The date formatter for image save files.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static final SimpleDateFormat saveFileFormatter = new SimpleDateFormat("yyMMdd_HHmmss");

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
    private static final String emailHashTimestampSeparator = "-";

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


    public static String buildUrl(GravatarImageRequestBuilder GravatarImageRequestBuilder) {
        boolean fullParameters = GravatarImageRequestBuilder.shouldUseFullUrlParameterNames();
        boolean useHttps = GravatarImageRequestBuilder.shouldUseHttps();

        StringBuilder urlBuilder = new StringBuilder(useHttps ? https : http);
        urlBuilder.append(imageRequestBaseUrl);
        urlBuilder.append(GravatarImageRequestBuilder.getGravatarUserEmailHash());

        if (GravatarImageRequestBuilder.shouldAppendJpgSuffix()) {
            urlBuilder.append(jpgExtension);
        }

        String size = String.valueOf(GravatarImageRequestBuilder.getSize());
        String sizeParameter = GravatarUrlParameter.SIZE.constructUrlParameterWithValue(size, true, fullParameters);
        urlBuilder.append(sizeParameter);

        ImmutableList<GravatarRating> ratings = ImmutableList.copyOf(GravatarImageRequestBuilder.getRatings());
        if (ratings.isEmpty()) ratings = ImmutableList.of(defaultRating);
        StringBuilder ratingsBuilder = new StringBuilder();
        ratings.forEach(rating -> ratingsBuilder.append(rating .getUrlParameter()));
        String ratingsParameter = GravatarUrlParameter.RATING
                .constructUrlParameterWithValue(ratingsBuilder.toString(), fullParameters);
        urlBuilder.append(ratingsParameter);

        GravatarDefaultImageType defaultImageType = GravatarImageRequestBuilder.getDefaultImageType();
        String defaultImageUrl = GravatarImageRequestBuilder.getDefaultImageUrl();

        if (defaultImageType != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_TYPE
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), fullParameters));
        } else if (defaultImageUrl != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_URL
                    .constructUrlParameterWithValue(defaultImageUrl, fullParameters));
        }

        if (GravatarImageRequestBuilder.shouldForceDefaultImage()) {
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
    public static BufferedImage getImage(GravatarImageRequestBuilder GravatarImageRequestBuilder) {
        Preconditions.checkNotNull(GravatarImageRequestBuilder);

        String url = buildUrl(GravatarImageRequestBuilder);

        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to get image from url: "
                    + url + ", error: " + e.getMessage());
        }
    }

    /**
     * Saves the buffered image returned by {@link #getImage(GravatarImageRequestBuilder)} to a file named using
     * the user email and the current timestamp.
     *
     * @throws NullPointerException if the provided Gravatar image request is null
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     * @throws IllegalArgumentException    if the file the image will be saved to already exists
     */
    public static File saveImage(GravatarImageRequestBuilder GravatarImageRequestBuilder) throws IOException {
        Preconditions.checkNotNull(GravatarImageRequestBuilder);

        String filename = GravatarImageRequestBuilder.getGravatarUserEmailHash()
                + emailHashTimestampSeparator + saveFileFormatter.format(new Date());
        File saveToFile = new File(filename);
        saveImage(GravatarImageRequestBuilder, saveToFile);
        return saveToFile;
    }

    /**
     * Saves the buffered image returned by {@link #getImage(GravatarImageRequestBuilder)} to the provided file.
     *
     * @param saveToFile the file to save the image to
     * @throws NullPointerException        if the provided image request or file is null
     * @throws IllegalArgumentException    if the file the image will be saved to already exists or is not a file
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     */
    public static void saveImage(GravatarImageRequestBuilder GravatarImageRequestBuilder, File saveToFile) throws IOException {
        Preconditions.checkNotNull(GravatarImageRequestBuilder);
        Preconditions.checkNotNull(saveToFile);
        Preconditions.checkArgument(saveToFile.isFile());
        Preconditions.checkArgument(!saveToFile.exists());

        ImageIO.write(getImage(GravatarImageRequestBuilder), "jpg", saveToFile);
    }
}
