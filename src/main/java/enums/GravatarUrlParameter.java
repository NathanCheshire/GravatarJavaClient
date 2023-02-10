package enums;

import com.google.common.base.Preconditions;

/**
 * A url parameter for a Gravatar request.
 */
public enum GravatarUrlParameter {
    /**
     * The size of the image to be returned by a Gravatar request.
     */
    SIZE("s"),

    /**
     * The URL to the default image to return in the case of a user email hash being invalid.
     */
    DEFAULT_IMAGE_URL("d"),

    /**
     * Whether to force the default URL regardless of whether the user email hash is valid.
     */
    FORCE_DEFAULT("f"),

    /**
     * The {@link GravatarDefaultImageType} type. Used to return a random custom
     * avatar if a user's email hash cannot be located.
     */
    DEFAULT_IMAGE_TYPE("d"),

    /**
     * The {@link GravatarRating} for a Gravatar request.
     */
    RATING("r");

    private final String urlParameter;

    /**
     * Constructs a new GravatarUrlParameter.
     *
     * @param urlParameter the url parameter prefix
     */
    GravatarUrlParameter(String urlParameter) {
        this.urlParameter = urlParameter;
    }

    /**
     * Constructs a string for this url parameter with the provided value.
     * It is assumed this is not the first url parameter after the query.
     *
     * @param value the value
     * @param <T>   the type of value
     * @return the string for this url parameter and value
     * @throws NullPointerException if the provided value is null
     */
    public <T> String constructUrlParameterWithValue(T value) {
        Preconditions.checkNotNull(value);

        return constructUrlParameterWithValue(value, false);
    }

    /**
     * Constructs a string for this url parameter with the provided value.
     * If this is the first parameter after the query, a "?" character is the leading character.
     * Otherwise a "&" character is the leading character.
     *
     * @param value          the value
     * @param <T>            the type of value
     * @param firstParameter whether this url parameter is the first after the query
     * @return the string for this url parameter and value
     * @throws NullPointerException if the provided value is null
     */
    public <T> String constructUrlParameterWithValue(T value, boolean firstParameter) {
        Preconditions.checkNotNull(value);

        return firstParameter ? "?" : "&" + urlParameter + "=" + value;
    }
}
