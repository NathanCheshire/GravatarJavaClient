import enums.GravatarDefaultImageType;
import enums.GravatarRating;
import exceptions.GravatarJavaClientException;

import java.util.Collection;

/**
 * An interface for classes to implement which provide an API for Gravatar image requests.
 */
public interface GravatarImageRequest {
    /**
     * Sets whether the JPG suffix should be appended to the user email hash when constructing the image request url.
     *
     * @param appendJpgSuffix whether the JPG suffix should be appended to the
     *                       user email hash when constructing the image request url.
     * @return this builder
     */
    GravatarImageRequestBuilder setAppendJpgSuffix(boolean appendJpgSuffix);

    /**
     * Sets the size for the image to be returned by this image request.
     *
     * @param size the requested size
     * @return this builder
     */
    GravatarImageRequestBuilder setSize(int size);

    /**
     * Sets the ratings to the provided collection of ratings.
     *
     * @param ratings the collection of ratings
     * @return this builder
     */
    GravatarImageRequestBuilder setRatings(Collection<GravatarRating> ratings);

    /**
     * Sets the ratings to the provided singular rating.
     *
     * @param rating the rating
     * @return this builder
     */
    GravatarImageRequestBuilder setRating(GravatarRating rating);

    /**
     * Adds the ratings to the provided collection of ratings.
     *
     * @param ratings the collection of ratings to add
     * @return this builder
     */
    GravatarImageRequestBuilder addRatings(Collection<GravatarRating> ratings);

    /**
     * Adds the rating to the list of ratings.
     *
     * @param rating the rating to add
     * @return this builder
     */
    GravatarImageRequestBuilder addRating(GravatarRating rating);

    /**
     * Sets whether to force the default image to be returned from this image request,
     * regardless of whether the a Gravatar for the user email is valid.
     *
     * @param forceDefaultImage whether to force the default image to be returned
     * @return this builder
     */
    GravatarImageRequestBuilder setForceDefaultImage(boolean forceDefaultImage);

    /**
     * Sets the default image type to the provided type.
     * Note, this removes the value set by {@link #setDefaultImageUrl(String)}} if set.
     *
     * @param defaultImageType the default image type
     * @return this builder
     */
    GravatarImageRequestBuilder setDefaultImageType(GravatarDefaultImageType defaultImageType);

    /**
     * Sets the default image url.
     * Note, this removes the value set by {@link #setDefaultImageType(GravatarDefaultImageType)} if set.
     *
     * @param defaultImageUrl the default image url
     * @return this builder
     */
    GravatarImageRequestBuilder setDefaultImageUrl(String defaultImageUrl);

    /**
     * Builds the Gravatar image request url represented by the current state of this builder.
     *
     * @return the Gravatar image request url
     * @throws GravatarJavaClientException if an exception occurs
     */
    String buildUrl();
}