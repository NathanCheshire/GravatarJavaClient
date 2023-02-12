package com.github.natche.gravatarjavaclient.enums;

import com.google.common.base.Preconditions;

/**
 * A url parameter for a Gravatar request.
 */
public enum GravatarUrlParameter {
    /**
     * The size of the image to be returned by a Gravatar request.
     */
    SIZE("s", "size"),

    /**
     * The URL to the default image to return in the case a user email hash is invalid.
     */
    DEFAULT_IMAGE_URL("d", "default"),

    /**
     * Whether to force the default URL regardless of whether the user email hash is valid.
     */
    FORCE_DEFAULT("f", "forcedefault"),

    /**
     * The {@link GravatarDefaultImageType} type. Used to return a random custom
     * avatar if a user's email hash cannot be located.
     */
    DEFAULT_IMAGE_TYPE("d", "default"),

    /**
     * The {@link GravatarRating} for a Gravatar request.
     */
    RATING("r", "rating");

    /**
     * The character prefix for the initial url parameter directly following the path.
     * Sometimes called the query string separator.
     */
    private static final String initialUrlParameterPrefix = "?";

    /**
     * The character prefix for any additional url parameters following the initial url parameter.
     */
    private static final String nonInitialUrlParameterPrefix = "&";

    /**
     * The short url parameter for this Gravatar url parameter.
     */
    private final String shortUrlParameter;

    /**
     * The full url parameter for this Gravatar url parameter.
     */
    private final String fullUrlParameter;

    /**
     * Constructs a new GravatarUrlParameter.
     *
     * @param urlParameter the short url parameter prefix
     * @param fullUrlParameter the full url parameter prefix
     */
    GravatarUrlParameter(String urlParameter, String fullUrlParameter) {
        this.shortUrlParameter = urlParameter;
        this.fullUrlParameter = fullUrlParameter;
    }

    /**
     * Constructs a string for this url parameter with the provided value.
     * It is assumed this is not the first url parameter after the query.
     *
     * @param value the value
     * @param useFullUrlParameter whether to use the full url parameter
     * @param <T>   the type of value
     * @return the string for this url parameter and value
     * @throws NullPointerException if the provided value is null
     */
    public <T> String constructUrlParameterWithValue(T value, boolean useFullUrlParameter) {
        Preconditions.checkNotNull(value);

        return constructUrlParameterWithValue(value, false, useFullUrlParameter);
    }

    /**
     * Constructs a string for this url parameter with the provided value.
     * If this is the first parameter after the query, a "?" character is the leading character.
     * Otherwise a "&" character is the leading character.
     *
     * @param value          the value
     * @param <T>            the type of value
     * @param firstParameter whether this url parameter is the first after the query
     * @param useFullUrlParameter whether to use the full url parameter
     * @return the string for this url parameter and value
     * @throws NullPointerException if the provided value is null
     */
    public <T> String constructUrlParameterWithValue(T value, boolean firstParameter, boolean useFullUrlParameter) {
        Preconditions.checkNotNull(value);

        String prefix = firstParameter ? initialUrlParameterPrefix : nonInitialUrlParameterPrefix;
        String urlParameter = useFullUrlParameter ? fullUrlParameter : shortUrlParameter;

        return prefix + urlParameter + "=" + value;
    }
}
