package com.github.natche.gravatarjavaclient.image;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarRating;

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
     * Sets whether the JPG suffix should be appended to the user email hash when constructing the image request URL.
     *
     * @param shouldAppendJpgSuffix whether the JPG suffix should be appended to the
     *                              user email hash when constructing the image request URL
     * @return this builder
     */
    GravatarImageRequestBuilder setShouldAppendJpgSuffix(boolean shouldAppendJpgSuffix);

    /**
     * Returns whether the JPG suffix should be appended to the user email hash when constructing the image request URL.
     *
     * @return whether the JPG suffix should be appended to the user email hash when constructing the image request URL
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
     * Sets the maximum acceptable rating to the provided rating.
     *
     * @param rating the rating
     * @return this builder
     */
    GravatarImageRequestBuilder setRating(GravatarRating rating);

    /**
     * Returns the maximum acceptable rating for this image request.
     *
     * @return the maximum acceptable rating for this image request
     */
    GravatarRating getRating();

    /**
     * Sets whether to force the default image to be returned from this image request,
     * regardless of whether a Gravatar for the user email is valid.
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
     * Returns the default image URL.
     *
     * @return the default image URL
     */
    String getDefaultImageUrl();

    /**
     * Sets the default image URL.
     * Note, this removes the value set by {@link #setDefaultImageType(GravatarDefaultImageType)} if set.
     *
     * @param defaultImageUrl the default image URL
     * @return this builder
     */
    GravatarImageRequestBuilder setDefaultImageUrl(String defaultImageUrl);

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
     * For example, instead of appending "&amp;d=your-url", "&amp;default=your-url" would be used.
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
