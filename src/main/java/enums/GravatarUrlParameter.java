package enums;

/**
 * A url parameter for a Gravatar request.
 */
public enum GravatarUrlParameter {
    /**
     * The hash for a Gravatar request.
     */
    HASH(true, ""),

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

    /**
     * Whether this URL parameter is required for a Gravatar request.
     */
    private final boolean required;

    /**
     * Constructs a new GravatarUrlParameter with the required value set to {@code false}
     *
     * @param urlParameter the url parameter prefix for the value associated with this parameter
     */
    GravatarUrlParameter(String urlParameter) {
        this(false, urlParameter);
    }

    /**
     * Constructs a new GravatarUrlParameter.
     *
     * @param required whether this url parameter is required for all gravatar requests.
     * @param urlParameter the url parameter prefix for the value associated with this parameter
     */
    GravatarUrlParameter(boolean required, String urlParameter) {
        this.required = required;
    }

    /**
     * Returns whether this URL parameter is required for all gravatar request.
     *
     * @return whether this URL parameter is required for all gravatar request
     */
    public boolean isRequired() {
        return required;
    }
}
