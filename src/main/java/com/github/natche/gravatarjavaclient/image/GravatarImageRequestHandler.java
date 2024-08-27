package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
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
 * The request handler for sending out {@link GravatarImageRequestBuilder}s and parsing the returned image data.
 */
public final class GravatarImageRequestHandler {
    /**
     * The date formatter for image files saved to disc.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static SimpleDateFormat saveFileFormatter = new SimpleDateFormat("yyMMdd_HHmmss");

    /**
     * The character to separate the email hash from the timestamp
     * when saving an image if a file pointer to save the image to disc was not provided.
     */
    private static String emailHashTimestampSeparator = "-";

    /**
     * The hyper text transfer protocol string.
     */
    private static final String HTTP = "http";

    /**
     * The safe hyper text transfer protocol string.
     */
    private static final String HTTPS = "https";

    /**
     * The base URL for the image request API without a protocol prefix.
     */
    private static final String IMAGE_REQUEST_BASE_URL = "://www.gravatar.com/avatar/";

    /**
     * The jpg extension without the leading period.
     */
    private static final String JPG_EXTENSION_WITHOUT_PERIOD = "jpg";

    /**
     * The JPG extension to append to the end of an email hash if
     * {@link GravatarImageRequestBuilder#shouldAppendJpgSuffix()} is true.
     */
    private static final String JPG_EXTENSION = "." + JPG_EXTENSION_WITHOUT_PERIOD;

    /**
     * The string to accompany {@link GravatarUrlParameter#FORCE_DEFAULT} to indicate the default URL
     * should be used regardless of the validity of the user account.
     */
    private static final String FORCE_DEFAULT_URL_TRUE_STRING = "y";

    /**
     * The default state new instances of {@link GravatarImageRequestBuilderImpl} should use for
     * the "append jpg suffix" member.
     */
    private static boolean appendJpgSuffixByDefault = true;

    /**
     * The default state new instances of {@link GravatarImageRequestBuilderImpl} should use for
     * the rating member.
     */
    private static GravatarRating defaultRating = GravatarRating.G;

    /**
     * The default state new instances of {@link GravatarImageRequestBuilderImpl} should use for
     * the "use https" member.
     */
    private static boolean useHttpsByDefault = true;

    /**
     * The default state new instances of {@link GravatarImageRequestBuilderImpl} should use for
     * the "use full parameter names" member.
     */
    private static boolean useFullParameterNamesByDefault = false;

    /**
     * Suppress default constructor.
     *
     * @throws AssertionError if invoked
     */
    private GravatarImageRequestHandler() {
        throw new AssertionError("Cannot create instances of GravatarImageRequestHandler");
    }

    /**
     * Sets the simple date formatter to use when saving images to files.
     *
     * @param fileDateFormat the simple date format to use
     * @throws NullPointerException if the provided formatter is null
     */
    public static void setSaveFileFormatter(SimpleDateFormat fileDateFormat) {
        Preconditions.checkNotNull(fileDateFormat);
        GravatarImageRequestHandler.saveFileFormatter = fileDateFormat;
    }

    /**
     * Sets the separating string between the email hash and the timestamp when saving images to files.
     *
     * @param emailHashTimestampSeparator the separating string to use
     * @throws NullPointerException     if the provided separator is null
     * @throws IllegalArgumentException if the provided separator is empty
     */
    public static void setEmailHashTimestampSeparator(String emailHashTimestampSeparator) {
        Preconditions.checkNotNull(emailHashTimestampSeparator);
        Preconditions.checkArgument(!emailHashTimestampSeparator.isEmpty());
        Preconditions.checkArgument(GeneralUtils.isValidFilename(emailHashTimestampSeparator));

        GravatarImageRequestHandler.emailHashTimestampSeparator = emailHashTimestampSeparator;
    }

    /**
     * Sets the default state of the "append jpg suffix" for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @param appendJpgSuffixByDefault the state of the "append jpg suffix" for
     *                                 new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static void setAppendJpgSuffixByDefault(boolean appendJpgSuffixByDefault) {
        GravatarImageRequestHandler.appendJpgSuffixByDefault = appendJpgSuffixByDefault;
    }

    /**
     * Returns the state of the "append jpg suffix" for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @return the state of the "append jpg suffix" for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static boolean shouldAppendJpgSuffixByDefault() {
        return appendJpgSuffixByDefault;
    }

    /**
     * Sets the initial state of the rating for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @param defaultRating the initial state of the rating for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static void setDefaultRating(GravatarRating defaultRating) {
        Preconditions.checkNotNull(defaultRating);
        GravatarImageRequestHandler.defaultRating = defaultRating;
    }

    /**
     * Returns the initial state of the rating for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @return the initial state of the rating for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static GravatarRating getDefaultRating() {
        return defaultRating;
    }

    /**
     * Sets the initial state of the "use https" member for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @param useHttpsByDefault the initial state of the "use https"
     *                          member for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static void setUseHttpsByDefault(boolean useHttpsByDefault) {
        GravatarImageRequestHandler.useHttpsByDefault = useHttpsByDefault;
    }

    /**
     * Returns the initial state of the "use https" member for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @return the initial state of the "use https" member for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static boolean shouldUseHttpsByDefault() {
        return useHttpsByDefault;
    }

    /**
     * Sets the initial state of the "use full parameter names" member
     * for new instances of {@link GravatarImageRequestBuilderImpl}.
     *
     * @param useFullParameterNamesByDefault the initial state of the "use full parameter names" member
     *                                       for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static void setUseFullParameterNamesByDefault(boolean useFullParameterNamesByDefault) {
        GravatarImageRequestHandler.useFullParameterNamesByDefault = useFullParameterNamesByDefault;
    }

    /**
     * Returns the initial state of the "use full parameter names"
     * member for new instances of {@link GravatarImageRequestBuilderImpl}
     *
     * @return the initial state of the "use full parameter names"
     * member for new instances of {@link GravatarImageRequestBuilderImpl}
     */
    public static boolean shouldUseFullParameterNamesByDefault() {
        return useFullParameterNamesByDefault;
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

        StringBuilder urlBuilder = new StringBuilder(useHttps ? HTTPS : HTTP);
        urlBuilder.append(IMAGE_REQUEST_BASE_URL);
        urlBuilder.append(gravatarImageRequestBuilder.getGravatarUserEmailHash());

        if (gravatarImageRequestBuilder.shouldAppendJpgSuffix()) {
            urlBuilder.append(JPG_EXTENSION);
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
                throw new GravatarJavaClientException("Must provide default URL if force default is enabled");
            }

            urlBuilder.append(GravatarUrlParameter.FORCE_DEFAULT
                    .constructUrlParameterWithValue(FORCE_DEFAULT_URL_TRUE_STRING, fullParameters));
        }

        return urlBuilder.toString();
    }

    /**
     * Returns a new {@link BufferedImage} of the Gravatar image represented
     * by the state of the provided {@link GravatarImageRequestBuilder}.
     *
     * @param gravatarImageRequestBuilder the image request builder
     * @return the Gravatar URL
     * @throws NullPointerException        if the provided Gravatar image request is null
     * @throws GravatarJavaClientException if an exception reading from the built URL occurs
     */
    public static BufferedImage getImage(GravatarImageRequestBuilder gravatarImageRequestBuilder) {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);

        return GeneralUtils.readBufferedImage(buildUrl(gravatarImageRequestBuilder));
    }

    /**
     * Saves an image returned by {@link #getImage(GravatarImageRequestBuilder)} to a file named using
     * the user email and the current timestamp.
     * Filename format: "emailHash-timestamp" where timestamp is in the format {@link #saveFileFormatter}.
     *
     * @param gravatarImageRequestBuilder the image request builder
     * @return the file pointer to the saved image
     * @throws NullPointerException if the provided request builder is null
     */
    @CanIgnoreReturnValue
    public static File saveImage(GravatarImageRequestBuilder gravatarImageRequestBuilder) throws IOException {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);

        String filename = gravatarImageRequestBuilder.getGravatarUserEmailHash()
                + emailHashTimestampSeparator + saveFileFormatter.format(new Date()) + JPG_EXTENSION;
        File saveToFile = new File(filename);
        saveImage(gravatarImageRequestBuilder, saveToFile);
        return saveToFile;
    }

    /**
     * Saves an image returned by {@link #getImage(GravatarImageRequestBuilder)} to the provided file.
     *
     * @param gravatarImageRequestBuilder the image request builder
     * @param saveToFile                  the file to save the image to
     * @return whether the provided saveToFile exists
     * @throws NullPointerException        if the provided image request or file is null
     * @throws IllegalStateException       if the file already exists
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     */
    @CanIgnoreReturnValue
    public static boolean saveImage(GravatarImageRequestBuilder gravatarImageRequestBuilder,
                                    File saveToFile) throws IOException {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);
        Preconditions.checkNotNull(saveToFile);
        Preconditions.checkState(!saveToFile.exists());

        ImageIO.write(getImage(gravatarImageRequestBuilder), JPG_EXTENSION_WITHOUT_PERIOD, saveToFile);
        return saveToFile.exists();
    }
}
