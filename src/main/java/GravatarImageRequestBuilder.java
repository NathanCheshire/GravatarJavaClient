import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import enums.GravatarDefaultImageType;
import enums.GravatarRating;
import enums.GravatarUrlParameter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A builder for a Gravatar Image.
 * <a href="https://en.gravatar.com/site/implement/images/">API documentation</a>.
 */
public final class GravatarImageRequestBuilder {
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
    private ArrayList<GravatarRating> ratings = new ArrayList<>();

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
     * Constructs a new GravatarImageRequestBuilder.
     *
     * @param userEmail the user email for this Gravatar image request.
     * @throws NullPointerException        if the user email is null
     * @throws IllegalArgumentException    if the provided user email is empty or invalid
     * @throws GravatarJavaClientException if any other exception occurs
     */
    public GravatarImageRequestBuilder(String userEmail) throws GravatarJavaClientException {
        Preconditions.checkNotNull(userEmail, "User email cannot be null");
        Preconditions.checkArgument(!userEmail.isEmpty(), "User email cannot be empty");
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(userEmail), "Malformed email address");

        this.userEmail = userEmail;
        this.hash = GeneralUtils.emailAddressToGravatarHash(userEmail);
    }

    /**
     * Sets whether the JPG suffix should be appended to the {@link #hash} when constructing the image request url.
     *
     * @param appendJpgSuffix whether the JPG suffix should be appended to the
     *                        {@link #hash} when constructing the image request url.
     * @return this builder
     */
    public GravatarImageRequestBuilder setAppendJpgSuffix(boolean appendJpgSuffix) {
        this.appendJpgSuffix = appendJpgSuffix;
        return this;
    }

    /**
     * Sets the size for the image to be returned by this image request.
     *
     * @param size the requested size
     * @return this builder
     * @throws IllegalArgumentException if the requested size is invalid
     */
    public GravatarImageRequestBuilder setSize(int size) {
        Preconditions.checkArgument(sizeRange.contains(size), "Size must be in range ["
                + sizeRange.lowerEndpoint() + ", " + sizeRange.upperEndpoint() + "]");

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
    public GravatarImageRequestBuilder setRatings(Collection<GravatarRating> ratings) {
        Preconditions.checkNotNull(ratings, "Ratings cannot be null");
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
    public GravatarImageRequestBuilder setRating(GravatarRating rating) {
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
     * @throws NullPointerException if ratings is null or contains a null element
     */
    public GravatarImageRequestBuilder addRatings(Collection<GravatarRating> ratings) {
        Preconditions.checkNotNull(ratings, "Ratings cannot be null");
        ratings.forEach(Preconditions::checkNotNull);

        this.ratings.addAll(ratings);

        return this;
    }

    /**
     * Adds the rating to the list of ratings.
     *
     * @param rating the rating
     * @return this builder
     * @throws NullPointerException if the provided rating is null
     */
    public GravatarImageRequestBuilder addRating(GravatarRating rating) {
        Preconditions.checkNotNull(rating);

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
    public GravatarImageRequestBuilder setForceDefaultImage(boolean forceDefaultImage) {
        this.forceDefaultImage = forceDefaultImage;
        return this;
    }

    /**
     * Sets the default image type to the provided type.
     *
     * @param defaultImageType the default image type
     * @return this builder
     * @throws NullPointerException if the provided image type is null
     */
    public GravatarImageRequestBuilder setDefaultImageType(GravatarDefaultImageType defaultImageType) {
        Preconditions.checkNotNull(defaultImageType);

        this.defaultImageType = defaultImageType;
        return this;
    }

    /**
     * Sets the default image url.
     *
     * @param defaultImageUrl the default image url
     * @return this builder
     * @throws NullPointerException if the provided image url is null
     * @throws IllegalArgumentException if the provided image url is empty or invalid
     */
    public GravatarImageRequestBuilder setDefaultImageUrl(String defaultImageUrl) {
        Preconditions.checkNotNull(defaultImageUrl);
        Preconditions.checkArgument(!defaultImageUrl.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidDefaultUrl(defaultImageUrl));

        this.defaultImageUrl = defaultImageUrl;

        return this;
    }
}
