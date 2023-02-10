/**
 * Ratings of a Gravatar user image.
 * Gravatar allows users to self-rate their images so that they can indicate if an image is appropriate
 * for a certain audience. By default, only 'G' rated images are displayed unless
 * you indicate that you would like to see higher ratings.
 */
public enum GravatarRating {
    /**
     * The G rating for a Gravatar user image.
     * Suitable for display on all websites with any audience type.
     */
    G,

    /**
     * The PG rating for a Gravatar user image.
     * May contain rude gestures, provocatively dressed individuals, the lesser swear words, or mild violence.
     */
    PG,

    /**
     * The R rating for a Gravatar user image.
     * May contain such things as harsh profanity, intense violence, nudity, or hard drug use.
     */
    R,

    /**
     * The X rating for a Gravatar user image.
     * May contain hardcore sexual imagery or extremely disturbing violence.
     */
    X;

    /**
     * Returns the url parameter for this {@link GravatarRating}.
     *
     * @return the url parameter for this {@link GravatarRating}
     */
    public String getUrlParameter() {
        return this.name().toLowerCase();
    }
}
