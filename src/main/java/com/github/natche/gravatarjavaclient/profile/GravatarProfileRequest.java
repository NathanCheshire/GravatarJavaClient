package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Objects;

/**
 * A request for obtaining a Gravatar Profile from the API.
 * Both authenticated and unauthenticated requests are supported by this class.
 */
public final class GravatarProfileRequest {
    /**
     * The authorization token supplier
     */
    private GravatarProfileTokenProvider tokenSupplier;

    /**
     * The SHA256 hash or profile ID.
     */
    private final String hashOrId;

    private GravatarProfileRequest(String hashOrId) {
        this.hashOrId = hashOrId;
    }

    /**
     * Constructs a new GravatarProfileRequest from the provided hash or ID such as "nathanvcheshire".
     *
     * @param hashOrId the hash or ID
     * @return a new GravatarProfileRequest
     * @throws NullPointerException     if the provided hash/ID is null
     * @throws IllegalArgumentException if the provided hash/ID is empty
     */
    public static GravatarProfileRequest fromHashOrId(String hashOrId) {
        Preconditions.checkNotNull(hashOrId);
        Preconditions.checkArgument(!hashOrId.trim().isEmpty());

        return new GravatarProfileRequest(hashOrId);
    }

    /**
     * Constructs a new GravatarProfileRequest from the provided email.
     *
     * @param email the email address
     * @return a new GravatarProfileRequest
     * @throws NullPointerException     if the provided email is null
     * @throws IllegalArgumentException if the provided email is empty or not a valid address
     */
    public static GravatarProfileRequest fromEmail(String email) {
        Preconditions.checkNotNull(email);
        Preconditions.checkArgument(!email.trim().isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(email));

        return new GravatarProfileRequest(GeneralUtils.emailAddressToProfilesApiHash(email));
    }

    /**
     * Sets the API token to use when requesting this profile from the Gravatar API.
     * If not provided, only certain fields will be returned.
     *
     * @param tokenSupplier a supplier for returning a token
     * @return this request builder
     */
    public GravatarProfileRequest setTokenSupplier(GravatarProfileTokenProvider tokenSupplier) {
        Preconditions.checkNotNull(tokenSupplier);
        this.tokenSupplier = tokenSupplier;
        return this;
    }

    /**
     * Returns the SHA256 hash or ID this request will use.
     *
     * @return the SHA256 hash or ID this request will use
     */
    public String getHashOrId() {
        return hashOrId;
    }

    /**
     * Retrieves the profile using the provided email or hash from the
     * Gravatar Profile API using HTTPS as the protocol.
     *
     * @return the GravatarProfile obtained from the API
     * @throws GravatarJavaClientException if an exception occurs when fetching the profile
     */
    public GravatarProfile getProfile() {
        byte[] token = tokenSupplier == null ? null : tokenSupplier.getToken();
        return GravatarProfileRequestHandler.INSTANCE.getProfile(token, hashOrId);
    }

    /**
     * Writes the profile object obtained from this request to the provided file.
     *
     * @param file the file to write the object to
     * @return whether the write operation was successful
     * @throws NullPointerException     if the provided file is null
     * @throws IllegalArgumentException if the provided file is a directory
     */
    @CanIgnoreReturnValue
    public boolean writeToFile(File file) {
        return writeToFile(GsonProvider.INSTANCE.get(), file);
    }

    /**
     * Writes the profile object obtained from this request to the provided file using the
     * provided GSON object as the serializer.
     *
     * @param serializer the GSON object to serialize the profile
     * @param file       the file to write the object to
     * @return whether the write operation was successful
     * @throws NullPointerException     if the provided file or serializer is null
     * @throws IllegalArgumentException if the provided file is a directory
     */
    @CanIgnoreReturnValue
    public boolean writeToFile(Gson serializer, File file) {
        Preconditions.checkNotNull(serializer);
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(!file.isDirectory());

        try (FileWriter writer = new FileWriter(file)) {
            serializer.toJson(getProfile(), writer);
            return true;
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        }
    }

    /**
     * Returns a hashcode representation of this object.
     *
     * @return a hashcode representation of this object
     */
    @Override
    public int hashCode() {
        int ret = hashOrId.hashCode();
        if (tokenSupplier != null) ret = 31 * ret + Arrays.hashCode(tokenSupplier.getToken());
        return ret;
    }

    /**
     * Returns whether the provided object equals this.
     *
     * @param o the other object
     * @return whether the provided object equals this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof GravatarProfileRequest)) return false;

        GravatarProfileRequest other = (GravatarProfileRequest) o;
        return hashOrId.equals(other.hashOrId)
                && Objects.equals(tokenSupplier, other.tokenSupplier);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    public String toString() {
        return "GravatarProfileRequest{"
                + "hash=\"" + hashOrId + "\", "
                + "tokenSupplier=" + tokenSupplier
                + "}";
    }
}
