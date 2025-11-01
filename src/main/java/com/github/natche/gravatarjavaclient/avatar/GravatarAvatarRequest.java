package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.enums.*;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.Hasher;
import com.github.natche.gravatarjavaclient.utils.InputValidator;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

/**
 * A class for building a Gravatar Avatar request, requesting the resource, and saving
 * the resource to a {@link java.io.File} or a {@link java.awt.image.BufferedImage}.
 * Read the official Avatar image request API docs <a href="https://docs.gravatar.com/api/avatars/images/">here</a>.
 */
public final class GravatarAvatarRequest {
    /**
     * The range a {@link GravatarUrlParameter#Size} parameter must fall within.
     */
    private static final Range<Integer> IMAGE_SIZE_RANGE = Range.closed(1, 2048);

    /**
     * The default image size for a request.
     */
    private static final int DEFAULT_IMAGE_SIZE = 80;

    /**
     * The hash either computed from a provided email address or provided
     * to the {@link #fromHash(String)} factory method.
     */
    private final String hash;

    /**
     * Whether the JPG suffix should be appended to the {@link #hash} when constructing the request URL.
     */
    private GravatarUseJpgSuffix shouldAppendJpgSuffix = GravatarUseJpgSuffix.False;

    /**
     * The size for the image returned by this request.
     */
    private int size = DEFAULT_IMAGE_SIZE;

    /**
     * The maximum rating allowable for this Avatar request.
     * Images rated above this rating will not be returned. Instead, the "default" image will be returned
     * as set by {@link #setDefaultImageType(GravatarDefaultImageType)} or {@link #setDefaultImageUrl(String)}.
     */
    private GravatarRating rating = GravatarRating.Pg;

    /**
     * Whether to force the default image to be returned for this request.
     */
    private GravatarForceDefaultImage forceDefaultImage = GravatarForceDefaultImage.DoNotForce;

    /**
     * The default image type.
     */
    private GravatarDefaultImageType defaultImageType = GravatarDefaultImageType.IdentIcon;

    /**
     * The application layer protocol to use when fetching the image from the URL.
     */
    private GravatarProtocol protocol = GravatarProtocol.Https;

    /**
     * Whether to use full URL parameter names in the request as opposed to short versions.
     */
    private GravatarUseFullUrlParameters useFullUrlParameters = GravatarUseFullUrlParameters.False;

    /**
     * The default image URL. This is used as a fallback and if the rating is above the set rating.
     */
    private String defaultImageUrl = null;

    /**
     * Constructs a new GravatarAvatarRequest from the provided hash.
     *
     * @param hash the hash, computed from a valid email address
     */
    private GravatarAvatarRequest(String hash) {
        this.hash = hash;
    }

    /**
     * Constructs and returns a new GravatarAvatarRequest.
     *
     * @param email the email for this request
     * @throws NullPointerException     if the provided email is null
     * @throws IllegalArgumentException if the provided email is empty or not a valid email address
     */
    public static GravatarAvatarRequest fromEmail(String email) {
        Preconditions.checkNotNull(email);
        Preconditions.checkArgument(!email.trim().isEmpty());
        Preconditions.checkArgument(InputValidator.isValidEmailAddress(email));

        String hash = Hasher.SHA256.hash(email);
        return new GravatarAvatarRequest(hash);
    }

    /**
     * Constructs and returns a new GravatarAvatarRequest.
     *
     * @param hash the hash for this request
     * @throws NullPointerException     if the provided hash is null
     * @throws IllegalArgumentException if the provided hash is empty
     */
    public static GravatarAvatarRequest fromHash(String hash) {
        Preconditions.checkNotNull(hash);
        Preconditions.checkArgument(!hash.trim().isEmpty());

        return new GravatarAvatarRequest(hash);
    }

    /**
     * Returns the hash for this request.
     *
     * @return the hash for this request
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * Sets the default image URL.
     * Note, {@link #defaultImageType} is set to {@code null} if this method is invoked.
     * <p>
     * Conditions which must be met for the Gravatar API endpoint to return a default image:
     * <ul>
     *     <li>The image must be publicly available (e.g. cannot be on an intranet, on a local development machine,
     *     behind HTTP Auth or some other firewall etc). Default images are passed through
     *     a security scan to avoid malicious content.</li>
     *     <li>The image must be accessible via HTTP or HTTPS on the standard ports, 80 and 443, respectively.</li>
     *     <li>The image must have a recognizable image extension (jpg, jpeg, gif, png, heic)</li>
     *     <li>The image must not include a query string (if it does, it will be ignored)</li>
     * </ul>
     *
     * @param defaultImageUrl the default image URL
     * @return this builder
     * @throws NullPointerException     if the provided image URL is null
     * @throws IllegalArgumentException if the provided image URL is empty or invalid
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setDefaultImageUrl(String defaultImageUrl) {
        Preconditions.checkNotNull(defaultImageUrl);
        Preconditions.checkArgument(!defaultImageUrl.trim().isEmpty());
        Preconditions.checkArgument(InputValidator.isValidImageUrl(defaultImageUrl));

        this.defaultImageUrl = defaultImageUrl;
        this.defaultImageType = null;

        return this;
    }

    /**
     * Returns the default image URL.
     *
     * @return the default image URL
     */
    public String getDefaultImageUrl() {
        return this.defaultImageUrl;
    }

    /**
     * Sets whether this request should use full URL parameters.
     *
     * @param useFullUrlParameters whether this request should use full URL parameters
     * @return this builder
     * @throws NullPointerException if the provided value is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setUseFullUrlParameters(GravatarUseFullUrlParameters useFullUrlParameters) {
        Preconditions.checkNotNull(useFullUrlParameters);
        this.useFullUrlParameters = useFullUrlParameters;
        return this;
    }

    /**
     * Returns whether this request will use full URL parameters.
     *
     * @return whether this request will use full URL parameters
     */
    public GravatarUseFullUrlParameters getUseFullUrlParameters() {
        return this.useFullUrlParameters;
    }

    /**
     * Sets the protocol this request will use.
     *
     * @param protocol the protocol
     * @return this builder
     * @throws NullPointerException if the provided protocol is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setProtocol(GravatarProtocol protocol) {
        Preconditions.checkNotNull(protocol);
        this.protocol = protocol;
        return this;
    }

    /**
     * Returns the protocol this request will use.
     *
     * @return the protocol this request will use
     */
    public GravatarProtocol getProtocol() {
        return this.protocol;
    }

    /**
     * Sets the default image type this request will use.
     * Note {@link #defaultImageUrl} is set to {@code null} after setting the internal state of this request to the provided type.
     *
     * @param imageType the default image type this request will use
     * @return this builder
     * @throws NullPointerException if the provided image type is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setDefaultImageType(GravatarDefaultImageType imageType) {
        Preconditions.checkNotNull(imageType);
        this.defaultImageType = imageType;
        this.defaultImageUrl = null;
        return this;
    }

    /**
     * Returns the default image type this request will use.
     *
     * @return the default image type this request will use
     */
    public GravatarDefaultImageType getDefaultImageType() {
        return this.defaultImageType;
    }

    /**
     * Sets whether this request should force the default image.
     *
     * @param forceDefaultImage whether this request should force the default image
     * @return this builder
     * @throws NullPointerException if the provided value is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setForceDefaultImage(GravatarForceDefaultImage forceDefaultImage) {
        Preconditions.checkNotNull(forceDefaultImage);
        this.forceDefaultImage = forceDefaultImage;
        return this;
    }

    /**
     * Returns whether this request should force the default image.
     *
     * @return whether this request should force the default image
     */
    public GravatarForceDefaultImage shouldForceDefaultImage() {
        return this.forceDefaultImage;
    }

    /**
     * Sets the rating this request will use.
     * If the resulting image's rating is above this rating, the default image will be used.
     *
     * @param rating the rating this request will use
     * @return this builder
     * @throws NullPointerException if the provided rating is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setRating(GravatarRating rating) {
        Preconditions.checkNotNull(rating);
        this.rating = rating;
        return this;
    }

    /**
     * Returns the rating this request will use.
     *
     * @return the rating this request will use
     */
    public GravatarRating getRating() {
        return this.rating;
    }

    /**
     * Returns whether this request should append the jpg suffix to the hash.
     *
     * @param useJpgSuffix whether this request should append the jpg suffix to the hash
     * @return this builder
     * @throws NullPointerException if the provided value is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setShouldAppendJpgSuffix(GravatarUseJpgSuffix useJpgSuffix) {
        Preconditions.checkNotNull(useJpgSuffix);
        this.shouldAppendJpgSuffix = useJpgSuffix;
        return this;
    }

    /**
     * Returns whether this request should append the jpg suffix to the hash.
     *
     * @return whether this request should append the jpg suffix to the hash
     */
    public GravatarUseJpgSuffix getShouldAppendJpgSuffix() {
        return this.shouldAppendJpgSuffix;
    }

    /**
     * Sets the size of the image this request should return.
     *
     * @param imageSize the size of the image this request should return
     * @return this builder
     * @throws IllegalArgumentException if the provided image size is not in the range {@link #IMAGE_SIZE_RANGE}
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setSize(int imageSize) {
        Preconditions.checkArgument(IMAGE_SIZE_RANGE.contains(imageSize));
        this.size = imageSize;
        return this;
    }

    /**
     * Returns the size of the image this request should return.
     *
     * @return the size of the image this request should return
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the URL for requesting the Avatar based on the current state of this.
     *
     * @return the URL for requesting the Avatar based on the current state of this
     */
    public String getRequestUrl() {
        return GravatarAvatarRequestHandler.INSTANCE.buildUrl(this);
    }

    /**
     * Reads from the URL constructed from the current state of this request using
     * {@link #getRequestUrl()}. The content is encoded into a new {@link BufferedImage} and returned.
     *
     * @return a new {@link BufferedImage}
     * @throws GravatarJavaClientException if an exception occurs reading from the URL
     */
    public BufferedImage getBufferedImage() {
        try {
            String requestUrl = getRequestUrl();
            return ImageIO.read(URI.create(requestUrl).toURL());
        } catch (IOException e) {
            throw new GravatarJavaClientException(e);
        }
    }

    /**
     * Saves the image obtained from the URL constructed from this to the provided file as a png.
     *
     * @param saveTo the file to save the image to
     * @return whether the save operation was successful
     * @throws NullPointerException     if the provided file is null
     * @throws IllegalArgumentException if the provided file is a directory
     */
    @CheckReturnValue
    public boolean saveAsPng(File saveTo) {
        Preconditions.checkNotNull(saveTo);
        Preconditions.checkArgument(!saveTo.isDirectory());

        return saveTo(saveTo, "png");
    }

    /**
     * Saves the image obtained from the URL constructed from this to the provided file as a jpg.
     *
     * @param saveTo the file to save the image to
     * @return whether the save operation was successful
     * @throws NullPointerException     if the provided file is null
     * @throws IllegalArgumentException if the provided file is a directory
     */
    @CheckReturnValue
    public boolean saveAsJpg(File saveTo) {
        Preconditions.checkNotNull(saveTo);
        Preconditions.checkArgument(!saveTo.isDirectory());

        return saveTo(saveTo, "jpg");
    }

    /**
     * Saves the image obtained from the URL constructed from this to the provided file.
     * Note, while the file could be named "Image.png", if "jpg" is provided as the format,
     * the image will be of jpg format yet still named your provided name of "Image.png".
     *
     * @param saveTo the file to save the image to
     * @param format the format to use; see {@link ImageIO#getWriterFormatNames()}
     * @return whether the save operation was successful
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if the provided format is empty or
     *                                  the provided file is a directory
     */
    @CheckReturnValue
    public boolean saveTo(File saveTo, String format) {
        Preconditions.checkNotNull(saveTo);
        Preconditions.checkNotNull(format);
        Preconditions.checkArgument(!format.trim().isEmpty());
        Preconditions.checkArgument(!saveTo.isDirectory());

        return GravatarAvatarRequestImageSaver.INSTANCE.saveTo(getBufferedImage(), saveTo, format);
    }

    /**
     * Returns a string representation of this request.
     *
     * @return a string representation of this request
     */
    @Override
    public String toString() {
        return "GravatarAvatarRequest{"
                + "hash=\"" + hash + "\", "
                + "size=" + size + ", "
                + "rating=" + rating + ", "
                + "forceDefaultImage=" + forceDefaultImage + ", "
                + "defaultImageType=" + defaultImageType + ", "
                + "protocol=" + protocol + ", "
                + "useFullUrlParameters=" + useFullUrlParameters + ", "
                + "defaultImageUrl=\"" + defaultImageUrl + "\", "
                + "}";
    }

    /**
     * Returns a hashcode for this request.
     *
     * @return a hashcode for this request
     */
    @Override
    public int hashCode() {
        int ret = hash.hashCode();
        ret = 31 * ret + shouldAppendJpgSuffix.hashCode();
        ret = 31 * ret + Integer.hashCode(size);
        ret = 31 * ret + rating.hashCode();
        ret = 31 * ret + forceDefaultImage.hashCode();
        ret = 31 * ret + Objects.hashCode(defaultImageType);
        ret = 31 * ret + protocol.hashCode();
        ret = 31 * ret + useFullUrlParameters.hashCode();
        ret = 31 * ret + Objects.hash(defaultImageUrl);
        return ret;
    }

    /**
     * Returns whether the provided object is equal to this request.
     *
     * @param o the other object
     * @return whether the provided object is equal to this request
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GravatarAvatarRequest other)) return false;

        return Objects.equals(hash, other.hash)
                && Objects.equals(shouldAppendJpgSuffix, other.shouldAppendJpgSuffix)
                && size == other.size
                && Objects.equals(rating, other.rating)
                && Objects.equals(forceDefaultImage, other.forceDefaultImage)
                && Objects.equals(defaultImageType, other.defaultImageType)
                && Objects.equals(protocol, other.protocol)
                && Objects.equals(useFullUrlParameters, other.useFullUrlParameters)
                && Objects.equals(defaultImageUrl, other.defaultImageUrl);
    }
}
