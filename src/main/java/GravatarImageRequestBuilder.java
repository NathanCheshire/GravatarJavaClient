import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import enums.GravatarDefaultImageType;
import enums.GravatarRating;
import enums.GravatarUrlParameter;
import exceptions.GravatarJavaClientException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A builder for a Gravatar Image.
 * <a href="https://en.gravatar.com/site/implement/images/">API documentation</a>.
 */
public final class GravatarImageRequestBuilder {
    /**
     * The base url for the image request API.
     */
    private static final String imageRequestBaseUrl = "https://www.gravatar.com/avatar/";

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
     * The email address for this builder.
     */
    private final String userEmail; // todo going to use for save function

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
    public synchronized GravatarImageRequestBuilder setAppendJpgSuffix(boolean appendJpgSuffix) {
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
    public synchronized GravatarImageRequestBuilder setSize(int size) {
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
    public synchronized GravatarImageRequestBuilder setRatings(Collection<GravatarRating> ratings) {
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
     * @throws NullPointerException if ratings is null or contains a null element
     * @throws IllegalArgumentException if ratings already contains one of the ratings
     */
    public synchronized GravatarImageRequestBuilder addRatings(Collection<GravatarRating> ratings) {
        Preconditions.checkNotNull(ratings, "Ratings cannot be null");
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
     * @throws NullPointerException if the provided rating is null
     * @throws IllegalArgumentException if ratings already contains this rating
     */
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
    public synchronized GravatarImageRequestBuilder setForceDefaultImage(boolean forceDefaultImage) {
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
    public synchronized GravatarImageRequestBuilder setDefaultImageType(GravatarDefaultImageType defaultImageType) {
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
    public synchronized GravatarImageRequestBuilder setDefaultImageUrl(String defaultImageUrl) {
        Preconditions.checkNotNull(defaultImageUrl);
        Preconditions.checkArgument(!defaultImageUrl.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidDefaultUrl(defaultImageUrl));

        this.defaultImageUrl = defaultImageUrl;

        return this;
    }

    /**
     * Builds the Gravatar image request url represented by the current state of this builder.
     *
     * @return the Gravatar image request url
     * @throws GravatarJavaClientException if an exception occurs
     */
    public synchronized String buildUrl() throws GravatarJavaClientException {
        StringBuilder urlBuilder = new StringBuilder(imageRequestBaseUrl);
        urlBuilder.append(hash);

        if (appendJpgSuffix) urlBuilder.append(jpgExtension);

        String sizeString = String.valueOf(size);
        if (sizeString.isEmpty()) throw new GravatarJavaClientException("Failed to convert size to a valid string");
        urlBuilder.append(GravatarUrlParameter.SIZE.constructUrlParameterWithValue(sizeString, true));

        StringBuilder ratingsBuilder = new StringBuilder();
        if (ratings.isEmpty()) ratings.add(GravatarRating.G);
        ratings.forEach(rating -> ratingsBuilder.append(rating.getUrlParameter()));
        urlBuilder.append(GravatarUrlParameter.RATING.constructUrlParameterWithValue(ratingsBuilder.toString()));

        return urlBuilder.toString();
    }
}
