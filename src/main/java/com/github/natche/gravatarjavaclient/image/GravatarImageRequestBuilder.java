package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A builder for a Gravatar image request.
 * <a href="https://en.gravatar.com/site/implement/images/">Image Request API Documentation</a>.
 */
public final class GravatarImageRequestBuilder implements GravatarImageRequest {
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
     * The hash computed from the user email for this builder.
     */
    private final String hash;

    /**
     * Whether the JPG suffix should be appended to the {@link #hash} when constructing the image request url.
     */
    private boolean shouldAppendJpgSuffix = requireJpgExtensionSuffixByDefault;

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
    public GravatarImageRequestBuilder(String userEmail) {
        Preconditions.checkNotNull(userEmail);
        Preconditions.checkArgument(!userEmail.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(userEmail));

        this.hash = GeneralUtils.emailAddressToGravatarHash(userEmail);
    }

    /**
     * {@inheritDoc}
     */
    public synchronized String getGravatarUserEmailHash() {
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setShouldAppendJpgSuffix(boolean shouldAppendJpgSuffix) {
        this.shouldAppendJpgSuffix = shouldAppendJpgSuffix;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized boolean shouldAppendJpgSuffix() {
        return shouldAppendJpgSuffix;
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
     * {@inheritDoc}
     */
    public synchronized int getSize() {
        return size;
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
     * {@inheritDoc}
     */
    public synchronized ImmutableList<GravatarRating> getRatings() {
        return ImmutableList.copyOf(ratings);
    }

    /**
     * {@inheritDoc}
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setForceDefaultImage(boolean forceDefaultImage) {
        this.forceDefaultImage = forceDefaultImage;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized boolean shouldForceDefaultImage() {
        return forceDefaultImage;
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
     * {@inheritDoc}
     */
    public synchronized GravatarDefaultImageType getDefaultImageType() {
        return defaultImageType;
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
     * {@inheritDoc}
     */
    public synchronized String getDefaultImageUrl() {
        return defaultImageUrl;
    }

    /**
     * {@inheritDoc}
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setUseHttps(boolean useHttps) {
        this.useHttps = useHttps;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized boolean shouldUseHttps() {
        return useHttps;
    }

    /**
     * {@inheritDoc}
     */
    @CanIgnoreReturnValue
    public synchronized GravatarImageRequestBuilder setUseFullUrlParameterNames(boolean useFullUrlParameterNames) {
        this.useFullUrlParameterNames = useFullUrlParameterNames;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized boolean shouldUseFullUrlParameterNames() {
        return useFullUrlParameterNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof GravatarImageRequestBuilder)) {
            return false;
        }

        GravatarImageRequestBuilder other = (GravatarImageRequestBuilder) o;
        return hash.equals(other.hash)
                && shouldAppendJpgSuffix == other.shouldAppendJpgSuffix
                && size == other.size
                && ratings.equals(other.ratings)
                && forceDefaultImage == other.forceDefaultImage
                && defaultImageType == other.defaultImageType
                && defaultImageUrl.equals(other.defaultImageUrl)
                && useHttps == other.useHttps
                && useFullUrlParameterNames == other.useFullUrlParameterNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int ret = hash.hashCode();
        ret = 31 * ret + Boolean.hashCode(shouldAppendJpgSuffix);
        ret = 31 * ret + Integer.hashCode(size);
        ret = 31 * ret + ratings.hashCode();
        ret = 31 * ret + Boolean.hashCode(forceDefaultImage);
        ret = 31 * ret + defaultImageType.hashCode();
        ret = 31 * ret + defaultImageUrl.hashCode();
        ret = 31 * ret + Boolean.hashCode(useHttps);
        ret = 31 * ret + Boolean.hashCode(useFullUrlParameterNames);
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GravatarImageRequestBuilder{"
                + "hash=\"" + hash + "\""
                + ", shouldAppendJpgSuffix=" + shouldAppendJpgSuffix
                + ", size=" + size
                + ", ratings=" + ratings
                + ", forceDefaultImage=" + forceDefaultImage
                + ", defaultImageType=" + defaultImageType
                + ", defaultImageUrl=\"" + defaultImageUrl + "\""
                + ", useHttps=" + useHttps
                + ", useFullUrlParameterNames=" + useFullUrlParameterNames
                + "}";
    }
}
