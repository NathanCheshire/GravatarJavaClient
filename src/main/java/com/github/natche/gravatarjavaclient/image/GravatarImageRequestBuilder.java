package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;

import java.util.Collection;

/**
 * An interface for classes to implement which provide an API for building Gravatar image requests.
 */
public interface GravatarImageRequestBuilder {
    /**
     * Returns the Gravatar user email hash.
     *
     * @return the Gravatar user email hash
     */
    String getGravatarUserEmailHash();

    /**
     * Sets whether the JPG suffix should be appended to the user email hash when constructing the image request url.
     *
     * @param shouldAppendJpgSuffix whether the JPG suffix should be appended to the
     *                        user email hash when constructing the image request url.
     * @return this builder
     */
    GravatarImageRequestBuilder setShouldAppendJpgSuffix(boolean shouldAppendJpgSuffix);

    /**
     * Returns whether the JPG suffix should be appended to the user email hash when constructing the image request url.
     *
     * @return whether the JPG suffix should be appended to the user email hash when constructing the image request url
     */
    boolean shouldAppendJpgSuffix();

    /**
     * Sets the size for the image to be returned by this image request.
     *
     * @param size the requested size
     * @return this builder
     */
    GravatarImageRequestBuilder setSize(int size);

    /**
     * Returns the size for the image to be returned by this image request.
     *
     * @return the size for the image to be returned by this image request
     */
    int getSize();

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
     * Returns the list of ratings acceptable for this image request.
     *
     * @return the list of ratings acceptable for this image request
     */
    Collection<GravatarRating> getRatings();

    /**
     * Sets whether to force the default image to be returned from this image request,
     * regardless of whether the a Gravatar for the user email is valid.
     *
     * @param forceDefaultImage whether to force the default image to be returned
     * @return this builder
     */
    GravatarImageRequestBuilder setForceDefaultImage(boolean forceDefaultImage);

    /**
     * Returns whether to force the default image to be returned from this image request.
     *
     * @return whether to force the default image to be returned from this image request
     */
    boolean shouldForceDefaultImage();

    /**
     * Sets the default image type to the provided type.
     * Note, this removes the value set by {@link #setDefaultImageUrl(String)}} if set.
     *
     * @param defaultImageType the default image type
     * @return this builder
     */
    GravatarImageRequestBuilder setDefaultImageType(GravatarDefaultImageType defaultImageType);

    /**
     * Returns the default image type.
     *
     * @return the default image type
     */
    GravatarDefaultImageType getDefaultImageType();

    /**
     * Sets the default image url.
     * Note, this removes the value set by {@link #setDefaultImageType(GravatarDefaultImageType)} if set.
     *
     * @param defaultImageUrl the default image url
     * @return this builder
     */
    GravatarImageRequestBuilder setDefaultImageUrl(String defaultImageUrl);

    /**
     * Returns the default image url.
     *
     * @return the default image url
     */
    String getDefaultImageUrl();

    /**
     * Sets whether to use https as the protocol for Gravatar image requests.
     * Http will be used if false is provided.
     *
     * @param useHttps whether to use https as the protocol
     * @return this builder
     */
    GravatarImageRequestBuilder setUseHttps(boolean useHttps);

    /**
     * Returns whether to use https as the protocol.
     *
     * @return whether to use https as the protocol
     */
    boolean shouldUseHttps();

    /**
     * Sets whether the full URL parameter names should be used in the request as opposed to the shorthand versions.
     * For example, instead of appending "&d=default-url-here", "&default=default-url-here" would be used.
     *
     * @param useFullUrlParameterNames whether the full URL parameter names should be used
     * @return this builder
     */
    GravatarImageRequestBuilderImpl setUseFullUrlParameterNames(boolean useFullUrlParameterNames);

    /**
     * Returns whether the full URL parameter names should be used in the request.
     *
     * @return whether the full URL parameter names should be used in the request
     */
    boolean shouldUseFullUrlParameterNames();
}