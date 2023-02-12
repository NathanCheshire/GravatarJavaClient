package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
     * The character to separate the email hash from the timestamp
     * when saving an image when a save file was not provided.
     */
    private static final String emailHashTimestampSeparator = "-";

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
     * The jpg extension without the leading period.
     */
    private static final String jpgExtensionWithoutPeriod = "jpg";

    /**
     * The JPG extension appended to the end of an email hash if shouldAppendJpgSuffix is true.
     */
    private static final String jpgExtension = "." + jpgExtensionWithoutPeriod;

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

    /**
     * Builds the provided Gravatar image request into a URL representing the state of the builder.
     *
     * @param gravatarImageRequestBuilder the builder to construct the URL from
     * @return the built URL
     */
    public static String buildUrl(GravatarImageRequestBuilder gravatarImageRequestBuilder) {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);

        boolean fullParameters = gravatarImageRequestBuilder.shouldUseFullUrlParameterNames();
        boolean useHttps = gravatarImageRequestBuilder.shouldUseHttps();

        StringBuilder urlBuilder = new StringBuilder(useHttps ? https : http);
        urlBuilder.append(imageRequestBaseUrl);
        urlBuilder.append(gravatarImageRequestBuilder.getGravatarUserEmailHash());

        if (gravatarImageRequestBuilder.shouldAppendJpgSuffix()) {
            urlBuilder.append(jpgExtension);
        }

        String size = String.valueOf(gravatarImageRequestBuilder.getSize());
        String sizeParameter = GravatarUrlParameter.SIZE.constructUrlParameterWithValue(size, true, fullParameters);
        urlBuilder.append(sizeParameter);

        String ratingsParameter = GravatarUrlParameter.RATING.constructUrlParameterWithValue(
                gravatarImageRequestBuilder.getRating().getUrlParameter(), fullParameters);
        urlBuilder.append(ratingsParameter);

        GravatarDefaultImageType defaultImageType = gravatarImageRequestBuilder.getDefaultImageType();
        String defaultImageUrl = gravatarImageRequestBuilder.getDefaultImageUrl();

        if (defaultImageType != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_TYPE
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), fullParameters));
        } else if (defaultImageUrl != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_URL
                    .constructUrlParameterWithValue(defaultImageUrl, fullParameters));
        }

        if (gravatarImageRequestBuilder.shouldForceDefaultImage()) {
            if (defaultImageUrl == null) {
                throw new GravatarJavaClientException("Must provide default url if force default is enabled");
            }

            urlBuilder.append(GravatarUrlParameter.FORCE_DEFAULT
                    .constructUrlParameterWithValue(forceDefaultUrlTrueString, fullParameters));
        }

        return urlBuilder.toString();
    }

    /**
     * Returns a buffered image of the Gravatar image represented by the state of the provided request builder.
     *
     * @return the Gravatar url
     * @throws NullPointerException        if the provided Gravatar image request is null
     * @throws GravatarJavaClientException if an exception reading from the built url occurs
     */
    public static BufferedImage getImage(GravatarImageRequestBuilder gravatarImageRequestBuilder) {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);

        return GeneralUtils.readBufferedImage(buildUrl(gravatarImageRequestBuilder));
    }

    /**
     * Saves the buffered image returned by {@link #getImage(GravatarImageRequestBuilder)} to a file named using
     * the user email and the current timestamp.
     *
     * @throws NullPointerException        if the provided Gravatar image request is null
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     * @throws IllegalArgumentException    if the file the image will be saved to already exists
     */
    @CanIgnoreReturnValue
    public static File saveImage(GravatarImageRequestBuilder gravatarImageRequestBuilder) throws IOException {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);

        String filename = gravatarImageRequestBuilder.getGravatarUserEmailHash()
                + emailHashTimestampSeparator + saveFileFormatter.format(new Date()) + jpgExtension;
        File saveToFile = new File(filename);
        saveImage(gravatarImageRequestBuilder, saveToFile);
        return saveToFile;
    }

    /**
     * Saves the buffered image returned by {@link #getImage(GravatarImageRequestBuilder)} to the provided file.
     *
     * @param saveToFile the file to save the image to
     * @throws NullPointerException        if the provided image request or file is null
     * @throws IllegalArgumentException    if the file the image will be saved to already exists
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     */
    public static void saveImage(GravatarImageRequestBuilder gravatarImageRequestBuilder,
                                 File saveToFile) throws IOException {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);
        Preconditions.checkNotNull(saveToFile);
        Preconditions.checkArgument(!saveToFile.exists());

        ImageIO.write(getImage(gravatarImageRequestBuilder), jpgExtensionWithoutPeriod, saveToFile);
    }
}
