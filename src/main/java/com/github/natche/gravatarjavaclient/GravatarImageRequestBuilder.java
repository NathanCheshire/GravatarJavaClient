package com.github.natche.gravatarjavaclient;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A builder for a Gravatar Image.
 * <a href="https://en.gravatar.com/site/implement/images/">API documentation</a>.
 */
public final class GravatarImageRequestBuilder implements GravatarImageRequest {
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
     * The default length for an image request.
     */
    private static final int defaultImageLength = 80;

    /**
     * The range a {@link GravatarUrlParameter#SIZE} parameter must fall within.
     */
    private static final Range<Integer> sizeRange = Range.closed(1, 2048);

    /**
     * Whether the JPG suffix should be appended to the user email hash in the request url.
     */
    private static final boolean requireJpgExtensionSuffixByDefault = true;

    /**
     * The default rating to use if none is provided.
     */
    private static final GravatarRating defaultRating = GravatarRating.G;

    /**
     * The JPG extension appended to the end of the {@link #hash} if {@link #appendJpgSuffix} is true.
     */
    private static final String jpgExtension = ".jpg";

    /**
     * The string to accompany {@link GravatarUrlParameter#FORCE_DEFAULT} to indicate the default url should be used.
     */
    private static final String forceDefaultUrlTrueString = "y";

    /**
     * The email address for this builder.
     */
    private final String userEmail;

    /**
     * The hash computed from {@link #userEmail} for this builder.
     */
    private final String hash;

    /**
     * Whether the JPG suffix should be appended to the {@link #hash} when constructing the image request url.
     */
    private boolean appendJpgSuffix = requireJpgExtensionSuffixByDefault;

    /**
     * The size for the image returned by this builder.
     */
    private int size = defaultImageLength;

    /**
     * The ratings allowable for this image request.
     */
    private final ArrayList<GravatarRating> ratings = new ArrayList<>();

    /**
     * Whether to force the default image to be returned.
     */
    private boolean forceDefaultImage = false;

    /**
     * The default image type.
     */
    private GravatarDefaultImageType defaultImageType = null;

    /**
     * The default image url.
     */
    private String defaultImageUrl = null;

    /**
     * Whether to use https as the protocol for Gravatar image requests.
     */
    private boolean useHttps = true;

    /**
     * Whether the full URL parameter names should be used in the request as opposed to the shorthand versions.
     */
    private boolean useFullUrlParameterNames = false;

    /**
     * Constructs a new GravatarImageRequestBuilder.
     *
     * @param userEmail the user email for this Gravatar image request.
     * @throws NullPointerException        if the user email is null
     * @throws IllegalArgumentException    if the provided user email is empty or invalid
     * @throws GravatarJavaClientException if any other exception occurs
     */
    public GravatarImageRequestBuilder(String userEmail) throws GravatarJavaClientException {
        Preconditions.checkNotNull(userEmail);
        Preconditions.checkArgument(!userEmail.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(userEmail));

        this.userEmail = userEmail;
        this.hash = GeneralUtils.emailAddressToGravatarHash(userEmail);
    }

    /**
     * {@inheritDoc}
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setAppendJpgSuffix(boolean appendJpgSuffix) {
        this.appendJpgSuffix = appendJpgSuffix;
        return this;
    }

    /**
     * Sets the size for the image to be returned by this image request.
     *
     * @param size the requested size
     * @return this builder
     * @throws IllegalArgumentException if the requested size is not in the range {@link #sizeRange}
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setSize(int size) {
        Preconditions.checkArgument(sizeRange.contains(size));

        this.size = size;
        return this;
    }

    /**
     * Sets the ratings to the provided collection of ratings.
     *
     * @param ratings the collection of ratings
     * @return this builder
     * @throws NullPointerException if ratings is null or contains a null element
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setRatings(Collection<GravatarRating> ratings) {
        Preconditions.checkNotNull(ratings);
        ratings.forEach(Preconditions::checkNotNull);

        this.ratings.clear();
        this.ratings.addAll(ratings);

        return this;
    }

    /**
     * Sets the ratings to the provided singular rating.
     *
     * @param rating the rating
     * @return this builder
     * @throws NullPointerException if the provided rating is null
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setRating(GravatarRating rating) {
        Preconditions.checkNotNull(rating);
        this.ratings.clear();
        this.ratings.add(rating);

        return this;
    }

    /**
     * Adds the ratings to the provided collection of ratings.
     *
     * @param ratings the collection of ratings
     * @return this builder
     * @throws NullPointerException     if ratings is null or contains a null element
     * @throws IllegalArgumentException if ratings already contains one of the provided ratings
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder addRatings(Collection<GravatarRating> ratings) {
        Preconditions.checkNotNull(ratings);
        ratings.forEach(Preconditions::checkNotNull);
        ratings.forEach(rating -> Preconditions.checkArgument(!this.ratings.contains(rating)));

        this.ratings.addAll(ratings);

        return this;
    }

    /**
     * Adds the rating to the list of ratings.
     *
     * @param rating the rating
     * @return this builder
     * @throws NullPointerException     if the provided rating is null
     * @throws IllegalArgumentException if ratings already contains this rating
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder addRating(GravatarRating rating) {
        Preconditions.checkNotNull(rating);
        Preconditions.checkArgument(!ratings.contains(rating));

        this.ratings.add(rating);

        return this;
    }

    /**
     * Sets whether to force the default image to be returned from this image request,
     * regardless of whether the a Gravatar for the user email is valid.
     *
     * @param forceDefaultImage whether to force the default image to be returned
     * @return this builder
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setForceDefaultImage(boolean forceDefaultImage) {
        this.forceDefaultImage = forceDefaultImage;
        return this;
    }

    /**
     * Sets the default image type to the provided type.
     * Note, this removes the {@link #defaultImageUrl} if set.
     *
     * @param defaultImageType the default image type
     * @return this builder
     * @throws NullPointerException if the provided image type is null
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setDefaultImageType(GravatarDefaultImageType defaultImageType) {
        Preconditions.checkNotNull(defaultImageType);

        this.defaultImageType = defaultImageType;
        this.defaultImageUrl = null;

        return this;
    }

    /**
     * Sets the default image url.
     * Note, this removes the {@link #defaultImageType} if set.
     * <p>
     * Conditions which must be met for the Gravatar API endpoint to return a default image:
     * <ul>
     *     <li>MUST be publicly available (e.g. cannot be on an intranet, on a local development machine,
     *     behind HTTP Auth or some other firewall etc). Default images are passed through
     *     a security scan to avoid malicious content.</li>
     *     <li>MUST be accessible via HTTP or HTTPS on the standard ports, 80 and 443, respectively.</li>
     *     <li>MUST have a recognizable image extension (jpg, jpeg, gif, png, heic)</li>
     *     <li>MUST NOT include a query string (if it does, it will be ignored)</li>
     * </ul>
     *
     * @param defaultImageUrl the default image url
     * @return this builder
     * @throws NullPointerException     if the provided image url is null
     * @throws IllegalArgumentException if the provided image url is empty or invalid
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setDefaultImageUrl(String defaultImageUrl) {
        Preconditions.checkNotNull(defaultImageUrl);
        Preconditions.checkArgument(!defaultImageUrl.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidDefaultUrl(defaultImageUrl));

        this.defaultImageUrl = defaultImageUrl;
        this.defaultImageType = null;

        return this;
    }

    /**
     * Sets whether to use https as the protocol for Gravatar image requests.
     * Http will be used if false is provided.
     *
     * @param useHttps whether to use https as the protocol
     * @return this builder
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setUseHttps(boolean useHttps) {
        this.useHttps = useHttps;
        return this;
    }

    /**
     * Sets whether the full URL parameter names should be used in the request as opposed to the shorthand versions.
     * For example, instead of appending "&d=default-url-here", "&default=default-url-here" would be used.
     *
     * @param useFullUrlParameterNames whether the full URL parameter names should be used
     * @return this builder
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setUseFullUrlParameterNames(boolean useFullUrlParameterNames) {
        this.useFullUrlParameterNames = useFullUrlParameterNames;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized String buildUrl() {
        StringBuilder urlBuilder = new StringBuilder(useHttps ? https : http);
        urlBuilder.append(imageRequestBaseUrl);
        urlBuilder.append(hash);

        if (appendJpgSuffix) {
            urlBuilder.append(jpgExtension);
        }

        urlBuilder.append(GravatarUrlParameter.SIZE
                .constructUrlParameterWithValue(String.valueOf(size), true, useFullUrlParameterNames));

        StringBuilder ratingsBuilder = new StringBuilder();
        if (ratings.isEmpty()){
            ratings.add(defaultRating);
        }
        ratings.forEach(rating -> ratingsBuilder.append(rating.getUrlParameter()));
        urlBuilder.append(GravatarUrlParameter.RATING
                .constructUrlParameterWithValue(ratingsBuilder.toString(), useFullUrlParameterNames));

        if (defaultImageType != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_TYPE
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), useFullUrlParameterNames));
        } else if (defaultImageUrl != null) {
            urlBuilder.append(GravatarUrlParameter.DEFAULT_IMAGE_URL
                    .constructUrlParameterWithValue(defaultImageUrl, useFullUrlParameterNames));
        }

        if (forceDefaultImage) {
            urlBuilder.append(GravatarUrlParameter.FORCE_DEFAULT
                    .constructUrlParameterWithValue(forceDefaultUrlTrueString, useFullUrlParameterNames));
        }

        return urlBuilder.toString();
    }

    /**
     * Returns a buffered image of the Gravatar image representing by the state of this builder.
     *
     * @return the Gravatar url
     * @throws GravatarJavaClientException if an exception reading from the build url occurs
     */
    public synchronized BufferedImage getImage() throws GravatarJavaClientException {
        String url = buildUrl();

        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            throw new GravatarJavaClientException("Failed to get image from url: "
                    + url + ", error: " + e.getMessage());
        }
    }

    /**
     * Saves the buffered image returned by {@link #getImage()} to a file named using
     * the user email and the current timestamp.
     *
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     * @throws IllegalArgumentException    if the file the image will be saved to already exists
     */
    public synchronized void saveToFile() throws GravatarJavaClientException, IOException {
        File saveFile = new File(userEmail + "todo time" + jpgExtension);
        saveToFile(saveFile);

    }

    /**
     * Saves the buffered image returned by {@link #getImage()} to the provided file.
     *
     * @param file the file to save the image to
     * @throws NullPointerException        if the provided file is null
     * @throws IllegalArgumentException    if the file the image will be saved to already exists or is not a file
     * @throws GravatarJavaClientException if the image cannot be read
     * @throws IOException                 if the image cannot be saved to the newly created file
     */
    public synchronized void saveToFile(File file) throws GravatarJavaClientException, IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.isFile());
        Preconditions.checkArgument(!file.exists());

        ImageIO.write(getImage(), "jpg", file);
    }
}
