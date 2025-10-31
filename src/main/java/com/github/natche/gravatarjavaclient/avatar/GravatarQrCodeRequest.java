package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.enums.GravatarQrImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarQrImageVersion;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.Hasher;
import com.github.natche.gravatarjavaclient.utils.InputValidator;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * A request for a user's profile QR code from Gravatar.
 */
public final class GravatarQrCodeRequest {
    /**
     * The base URL for requesting data from the
     */
    private static final String BASE_URL = "https://gravatar.com/";

    /**
     * The range of acceptable lengths for a QR code image returned by Gravatar.
     */
    private static final Range<Integer> IMAGE_LENGTH_RANGE = Range.closed(80, 1024);

    /**
     * The hash computed from the user email for this builder.
     */
    private final String hash;

    /**
     * The size for the image returned by this request.
     */
    private int size = IMAGE_LENGTH_RANGE.lowerEndpoint();

    /**
     * The QR image type for this request.
     */
    private GravatarQrImageType imageType = GravatarQrImageType.DEFAULT;

    /**
     * The QR code version for this request.
     */
    private GravatarQrImageVersion version = GravatarQrImageVersion.BLANK;

    private GravatarQrCodeRequest(String hash) {
        this.hash = hash;
    }

    /**
     * Constructs and returns a new GravatarQrCodeRequest.
     *
     * @param email the email for this Gravatar QR code request
     * @throws NullPointerException     if the email is null
     * @throws IllegalArgumentException if the email is empty or not a valid email address
     */
    public static GravatarQrCodeRequest fromEmail(String email) {
        Preconditions.checkNotNull(email);
        Preconditions.checkArgument(!email.trim().isEmpty());
        Preconditions.checkArgument(InputValidator.from(email).isValidEmailAddress());

        String hash = Hasher.SHA256_HASHER.hash(email);
        return new GravatarQrCodeRequest(hash);
    }

    /**
     * Constructs and returns a new GravatarQrCodeRequest.
     *
     * @param hash the hash for this Gravatar QR code request obtained by SHA256 hashing a valid email address
     * @throws NullPointerException     if the hash is null
     * @throws IllegalArgumentException if the hash is empty
     */
    public static GravatarQrCodeRequest fromHash(String hash) {
        Preconditions.checkNotNull(hash);
        Preconditions.checkArgument(!hash.trim().isEmpty());

        return new GravatarQrCodeRequest(hash);
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
     * Sets the length of the image this request should return.
     *
     * @param imageLength the length of the image this request should return
     * @return this builder
     * @throws IllegalArgumentException if the provided image length is not in the range {@link #IMAGE_LENGTH_RANGE}
     */
    @CanIgnoreReturnValue
    public GravatarQrCodeRequest setSize(int imageLength) {
        Preconditions.checkArgument(IMAGE_LENGTH_RANGE.contains(imageLength));
        this.size = imageLength;
        return this;
    }

    /**
     * Returns the size of the image this request should return.
     *
     * @return the size of the image this request should return
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the image type this request will use.
     * See {@link GravatarQrImageType}.
     *
     * @param type the image type
     * @return this builder
     * @throws NullPointerException if the provided type is null
     */
    @CanIgnoreReturnValue
    public GravatarQrCodeRequest setImageType(GravatarQrImageType type) {
        Preconditions.checkNotNull(type);
        this.imageType = type;
        return this;
    }

    /**
     * Returns the image type this request will use.
     *
     * @return the image type this request will use
     */
    public GravatarQrImageType getImageType() {
        return imageType;
    }

    /**
     * Sets the QR code version this request will use.
     *
     * @param version the QR code version this request will use
     * @return this builder
     * @throws NullPointerException if the provided version is null
     */
    @CanIgnoreReturnValue
    public GravatarQrCodeRequest setVersion(GravatarQrImageVersion version) {
        Preconditions.checkNotNull(version);
        this.version = version;
        return this;
    }

    /**
     * Returns the QR code version this request will use.
     *
     * @return the QR code version this request will use
     */
    public GravatarQrImageVersion getVersion() {
        return version;
    }

    /**
     * Returns the URL for requesting the QR code based on the current state of this.
     *
     * @return the URL for requesting the QR code based on the current state of this
     */
    public String getRequestUrl() {
        return BASE_URL + hash + ".qr"
                + imageType.getAsUrlParameter(true)
                + version.getAsUrlParameter(false)
                + "&size=" + this.size;
    }

    /**
     * Reads and returns a {@link BufferedImage} using the URL constructed from the current state of this.
     *
     * @return a {@link BufferedImage} representing a QR code
     * @throws GravatarJavaClientException if an exception occurs reading from the URL
     */
    public BufferedImage getBufferedImage() {
        try {
            String url = getRequestUrl();
            return ImageIO.read(URI.create(url).toURL());
        } catch (IOException e) {
            throw new GravatarJavaClientException(e);
        }
    }

    /**
     * Saves the QR code obtained from this to the provided file as a PNG.
     * Note: the API returns a PNG which is why encoding options are not allowed by this method.
     *
     * @param saveTo the file to save the QR code to as a PNG
     * @return whether the save operation was successful
     * @throws NullPointerException if the provided file is null
     * @throws IllegalArgumentException if the provided file references a directory or exists
     */
    @CanIgnoreReturnValue
    public boolean saveTo(File saveTo) {
        Preconditions.checkNotNull(saveTo);
        Preconditions.checkArgument(!saveTo.isDirectory());
        Preconditions.checkArgument(!saveTo.exists());

        return GravatarAvatarRequestImageSaver.INSTANCE.saveTo(getBufferedImage(), saveTo, "png");
    }

    /**
     * Returns a hashcode of this object.
     *
     * @return a hashcode of this object
     */
    @Override
    public int hashCode() {
        int ret = hash.hashCode();
        ret = 31 * ret + Integer.hashCode(size);
        ret = 31 * ret + imageType.hashCode();
        ret = 31 * ret + version.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarQrCodeRequest{"
                + "hash=\"" + hash + "\", "
                + "size=" + size + ", "
                + "imageType=" + imageType + ", "
                + "version=" + version
                + "}";

    }

    /**
     * Returns whether the provided object equals this.
     *
     * @param o the other object
     * @return whether the provided object equals this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof GravatarQrCodeRequest)) {
            return false;
        }

        GravatarQrCodeRequest other = (GravatarQrCodeRequest) o;
        return other.hash.equals(hash)
                && other.version == version
                && other.imageType == imageType
                && other.size == size;
    }
}
