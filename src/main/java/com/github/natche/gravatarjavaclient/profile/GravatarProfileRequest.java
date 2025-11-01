package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.github.natche.gravatarjavaclient.utils.Hasher;
import com.github.natche.gravatarjavaclient.utils.InputValidator;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;

/**
 * A class for requesting a Profile from the Gravatar Profile API.
 * Both authenticated and unauthenticated requests are supported by this API.
 */
public final class GravatarProfileRequest {
    /**
     * The number of characters to reveal of the token when the {@link #toString()} method is invoked.
     */
    private static final int TOKEN_SUBSTRING_LENGTH = 5;

    /**
     * The SHA256 hash or profile ID.
     */
    private final String hashOrId;

    /**
     * The token used for obtaining authenticated fields in the response.
     */
    private String token;

    private GravatarProfileRequest(String hashOrId) {
        this.hashOrId = hashOrId;
    }

    /**
     * Constructs a new GravatarProfileRequest from the provided ID.
     *
     * @param id the ID to use in the request
     * @return a new GravatarProfileRequest
     * @throws NullPointerException     if the provided ID is null
     * @throws IllegalArgumentException if the provided ID is empty
     */
    public static GravatarProfileRequest from(String id) {
        Preconditions.checkNotNull(id);
        Preconditions.checkArgument(!id.trim().isEmpty());

        return new GravatarProfileRequest(id);
    }

    /**
     * Constructs a new GravatarProfileRequest from the provided email.
     *
     * @param email the email address
     * @return a new GravatarProfileRequest
     * @throws NullPointerException     if the provided email is null
     * @throws IllegalArgumentException if the provided email is empty or not a valid email
     */
    public static GravatarProfileRequest fromEmail(String email) {
        Preconditions.checkNotNull(email);
        Preconditions.checkArgument(!email.trim().isEmpty());
        Preconditions.checkArgument(InputValidator.isValidEmailAddress(email));

        return new GravatarProfileRequest(Hasher.SHA256.hash(email));
    }

    /**
     * Sets the token this request will use when requesting information from the Gravatar API.
     * When a valid token is provided, fields requiring authentication will be provided in the response.
     *
     * @param token the token
     * @return this request
     */
    public GravatarProfileRequest setToken(String token) {
        Preconditions.checkNotNull(token);
        Preconditions.checkArgument(!token.trim().isEmpty());

        this.token = token;
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
     * provided GSON object as the serializer. The provided file must have a valid name.
     *
     * @param serializer the GSON object to serialize the profile
     * @param file       the file to write the object to
     * @return whether the write operation was successful
     * @throws NullPointerException     if the provided file or serializer is null
     * @throws IllegalArgumentException if the provided file is a directory, does not exist, or has an invalid name
     */
    @CanIgnoreReturnValue
    public boolean writeToFile(Gson serializer, File file) {
        Preconditions.checkNotNull(serializer);
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(!file.isDirectory());
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(InputValidator.isValidFilename(file.getName()));

        try (FileWriter writer = new FileWriter(file)) {
            serializer.toJson(getProfile(), writer);
            return true;
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        }
    }

    /**
     * Returns a hashcode of this object.
     *
     * @return a hashcode of this object
     */
    @Override
    public int hashCode() {
        int ret = hashOrId.hashCode();
        if (token != null) ret = 31 * ret + token.hashCode();
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
                && token.equals(other.token);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    public String toString() {
        String sub = token != null && token.length() > TOKEN_SUBSTRING_LENGTH
                ? token.substring(0, TOKEN_SUBSTRING_LENGTH) + "..."
                : token;

        return "GravatarProfileRequest{"
                + "hash=\"" + hashOrId + "\", "
                + "token=" + sub
                + "}";
    }
}
