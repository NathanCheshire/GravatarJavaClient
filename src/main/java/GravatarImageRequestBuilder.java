import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;

/**
 * A builder for a Gravatar Image.
 * <a href="https://en.gravatar.com/site/implement/images/">API documentation</a>.
 */
public class GravatarImageRequestBuilder {
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

    private final String userEmail;
    private final String hash;
    private boolean appendJpgSuffix = requireJpgExtensionSuffixByDefault;
    private int size = defaultImageLength;
    private ImmutableList.Builder<GravatarRating> ratings = new ImmutableList.Builder<GravatarRating>()
            .add(GravatarRating.G);
    private boolean forceDefaultImage = false;
    private GravatarDefaultImageType defaultImageType = null;
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
        this.hash = ValidationUtils.emailAddressToGravatarHash(userEmail);
    }
}
