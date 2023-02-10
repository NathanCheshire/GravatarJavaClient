/**
 * A url parameter for a Gravatar request.
 */
public enum GravatarUrlParameter {
    /**
     * The hash for a Gravatar request.
     */
    HASH(true),

    /**
     * The size of the image to be returned.
     */
    SIZE,

    /**
     * The URL to the default image to return in the case of a hash lookup failure.
     */
    DEFAULT_URL,

    /**
     * Whether to force the default url regardless of whether the hash can be located.
     */
    FORCE_DEFAULT,

    /*
     * The {@link GravatarDefaultPreset} type. Used to return a random custom avatar if a hash cannot be located.
     */
    DEFAULT_PRESET,

    /**
     * The default image URL a Gravatar request.
     */
    DEFAULT_IMAGE_URL,

    /*
     * The {@link GravatarRating} for a Gravatar request.
     */
    RATING;

    /**
     * Whether this URL parameter is required for a Gravatar request.
     */
    private final boolean required;

    /**
     * Constructs a new GravatarUrlParameter with the required value set to {@code false}
     */
    GravatarUrlParameter() {
        this(false);
    }

    /**
     * Constructs a new GravatarUrlParameter.
     *
     * @param required whether this url parameter is required for all gravatar requests.
     */
    GravatarUrlParameter(boolean required) {
        this.required = required;
    }

    /**
     * Returns whether this url parameter is required for all gravatar request.
     *
     * @return whether this url parameter is required for all gravatar request
     */
    public boolean isRequired() {
        return required;
    }
}
