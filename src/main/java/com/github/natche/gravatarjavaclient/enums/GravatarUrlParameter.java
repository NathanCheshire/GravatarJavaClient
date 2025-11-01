package com.github.natche.gravatarjavaclient.enums;

import com.google.common.base.Preconditions;

/**
 * A URL parameter for a Gravatar Avatar request.
 */
public enum GravatarUrlParameter {
    /**
     * The size of the image to be returned by a Gravatar request.
     */
    Size("size"),

    /**
     * The URL of the default image to return in the case a user email hash is invalid or results in no valid images.
     */
    DefaultImageUrl("default"),

    /**
     * Whether to force the default URL regardless of the validity of
     * the user account hash. See {@link GravatarForceDefaultImage}.
     */
    ForceDefault("forcedefault"),

    /**
     * The {@link GravatarDefaultImageType} type. Used to return a random custom
     * avatar if a user's email hash cannot be located.
     */
    DefaultImageType("default"),

    /**
     * The {@link GravatarRating} for a Gravatar request.
     */
    Rating("rating");

    /**
     * The character prefix for the initial URL parameter directly following the path.
     * Sometimes called the query string separator.
     */
    private static final String INITIAL_URL_PARAMETER_PREFIX = "?";

    /**
     * The character prefix for any additional URL parameters following the initial URL parameter.
     */
    private static final String NON_INITIAL_URL_PARAMETER_PREFIX = "&";

    /**
     * The short URL parameter for this Gravatar URL parameter.
     */
    private final String shortUrlParameter;

    /**
     * The full URL parameter for this Gravatar URL parameter.
     */
    private final String fullUrlParameter;

    GravatarUrlParameter(String fullUrlParameter) {
        this.shortUrlParameter = fullUrlParameter.substring(0, 1);
        this.fullUrlParameter = fullUrlParameter;
    }

    /**
     * Constructs a string for this URL parameter with the provided value.
     * It is assumed this is NOT the first URL parameter after the path in the URL.
     *
     * @param value               the value
     * @param useFullUrlParameter whether to use the full URL parameter
     * @param <T>                 the type of value
     * @return the string for this URL parameter and value
     * @throws NullPointerException if any provided parameter is null
     */
    public <T> String constructUrlParameterWithValue(T value, GravatarUseFullUrlParameters useFullUrlParameter) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(useFullUrlParameter);

        return constructUrlParameterWithValue(value, false, useFullUrlParameter);
    }

    /**
     * Constructs a string for this URL parameter with the provided value.
     * If this is the first parameter after the query, a "?" character is the leading character.
     * Otherwise, a "&amp;" character is the leading character.
     *
     * @param value               the value
     * @param <T>                 the type of value
     * @param firstParameter      whether this URL parameter is the first after the query
     * @param useFullUrlParameter whether to use the full URL parameter
     * @return the string for this URL parameter and value
     * @throws NullPointerException if any provided parameter is null
     */
    public <T> String constructUrlParameterWithValue(T value,
                                                     boolean firstParameter,
                                                     GravatarUseFullUrlParameters useFullUrlParameter) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(useFullUrlParameter);

        String prefix = firstParameter ? INITIAL_URL_PARAMETER_PREFIX : NON_INITIAL_URL_PARAMETER_PREFIX;
        return prefix + (useFullUrlParameter.shouldUseFullParams()
                ? fullUrlParameter : shortUrlParameter) + "=" + value;
    }
}
