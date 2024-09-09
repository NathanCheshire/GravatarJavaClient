package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.enums.*;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * A class for building a Gravatar Image request, requesting the resource, and saving
 * to a {@link java.io.File} or a {@link java.awt.image.BufferedImage}.
 * <a href="https://docs.gravatar.com/api/avatars/images/">Image Request API Documentation</a>.
 */
public class GravatarAvatarRequest {
    /**
     * The range a {@link GravatarUrlParameter#SIZE} parameter must fall within.
     */
    private static final Range<Integer> IMAGE_SIZE_RANGE = Range.closed(1, 2048);

    /**
     * The default length for an image request.
     */
    private static final int DEFAULT_IMAGE_LENGTH = 80;

    /**
     * The hash computed from the user email for this builder.
     */
    private final String hash;

    /**
     * Whether the JPG suffix should be appended to the {@link #hash} when constructing the Avatar request URL.
     */
    private GravatarUseJpgSuffix shouldAppendJpgSuffix = GravatarUseJpgSuffix.False;

    /**
     * The size for the image returned by this builder.
     */
    private int size = DEFAULT_IMAGE_LENGTH;

    /**
     * The maximum rating allowable for this Avatar request.
     */
    private GravatarRating rating = GravatarRating.PG;

    /**
     * Whether to force the default image to be returned for this Avatar request.
     */
    private GravatarForceDefaultImage forceDefaultImage = GravatarForceDefaultImage.DoNotForce;

    /**
     * The default image type.
     */
    private GravatarDefaultImageType defaultImageType = GravatarDefaultImageType.IDENT_ICON;

    /**
     * The image protocol.
     */
    private GravatarProtocol protocol = GravatarProtocol.HTTPS;

    /**
     * Whether the full URL parameter names should be used in the request as opposed to the short versions.
     */
    private GravatarUseFullUrlParameters useFullUrlParameters = GravatarUseFullUrlParameters.False;

    /**
     * The default image URL to use in the event an Avatar cannot be found.
     */
    private String defaultImageUrl = null;

    /**
     * Constructs a new GravatarAvatarRequest from the provided hash.
     *
     * @param hash the MD5 hash pointing to an Avatar
     */
    private GravatarAvatarRequest(String hash) {
        this.hash = hash;
    }

    /**
     * Constructs and returns a new GravatarAvatarRequest.
     *
     * @param email the  email for this Gravatar Avatar request
     * @throws NullPointerException     if the email is null
     * @throws IllegalArgumentException if the email is empty or not a valid email address
     */
    public static GravatarAvatarRequest fromEmail(String email) {
        Preconditions.checkNotNull(email);
        Preconditions.checkArgument(!email.trim().isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(email));

        String hash = GeneralUtils.emailAddressToGravatarHash(email);
        return new GravatarAvatarRequest(hash);
    }

    /**
     * Constructs and returns a new GravatarAvatarRequest.
     *
     * @param hash the  hash for this Gravatar Avatar request
     * @throws NullPointerException     if the hash is null
     * @throws IllegalArgumentException if the hash is empty
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
     * Note, this resets {@link #defaultImageType} to {@code null} if this method is invoked.
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
        Preconditions.checkArgument(!defaultImageUrl.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidDefaultUrl(defaultImageUrl));

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
     *
     * @param imageType the default image type this request will use
     * @return this builder
     * @throws NullPointerException if the provided image type is null
     */
    @CanIgnoreReturnValue
    public GravatarAvatarRequest setDefaultImageType(GravatarDefaultImageType imageType) {
        Preconditions.checkNotNull(imageType);
        this.defaultImageType = imageType;
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
     * Sets the image length the image of this request should return.
     *
     * @param imageLength the image length the image of this request should return
     * @return this builder
     * @throws IllegalArgumentException if the provided image length is not in the range [1, 2048].
     */
    public GravatarAvatarRequest setSize(int imageLength) {
        Preconditions.checkArgument(IMAGE_SIZE_RANGE.contains(imageLength));
        this.size = imageLength;
        return this;
    }

    /**
     * Returns the image length the image of this request should return.
     *
     * @return the image length the image of this request should return
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
     * Reads and returns a {@link BufferedImage} using the URL constructed from the current state of this.
     *
     * @return a {@link BufferedImage} representing an Avatar
     * @throws GravatarJavaClientException if an exception occurs reading from the URL
     */
    public BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(new URL(getRequestUrl()));
        } catch (IOException e) {
            throw new GravatarJavaClientException(e);
        }
    }

    /**
     * Reads and returns an {@link ImageIcon} using the URL constructed from the current state of this.
     *
     * @return an {@link ImageIcon} representing an Avatar
     * @throws GravatarJavaClientException if an exception occurs reading from the URL
     */
    public ImageIcon getImageIcon() {
        try {
            return new ImageIcon(new URL(getRequestUrl()));
        } catch (IOException e) {
            throw new GravatarJavaClientException(e);
        }
    }

    /**
     * Saves an image obtained from the URL constructed from this to the provided file.
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
     * Returns a string representation of this GravatarAvatarRequest.
     *
     * @return a string representation of this GravatarAvatarRequest
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
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
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
     * Returns whether the provided object is equal to this.
     *
     * @param o the other object
     * @return whether the provided object is equal to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof GravatarAvatarRequest)) return false;

        GravatarAvatarRequest other = (GravatarAvatarRequest) o;
        return hash.equals(other.hash)
                && shouldAppendJpgSuffix.equals(other.shouldAppendJpgSuffix)
                && size == other.size
                && rating == other.rating
                && forceDefaultImage == other.forceDefaultImage
                && defaultImageType == other.defaultImageType
                && protocol == other.protocol
                && useFullUrlParameters == other.useFullUrlParameters
                && Objects.equals(defaultImageUrl, other.defaultImageUrl);
    }
}
