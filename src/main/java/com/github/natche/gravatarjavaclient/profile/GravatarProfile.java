package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.ImmutableListDeserializer;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The results of a Gravatar profile request.
 * <a href="https://en.gravatar.com/site/implement/profiles/">Profile Request API Documentation</a>.
 */
@Immutable
public final class GravatarProfile {
    /**
     * The header for Gravatar profile requests.
     */
    private static final String GRAVATAR_PROFILE_REQUEST_HEADER = "https://www.gravatar.com/";

    /**
     * The format for the returned data from profile requests.
     */
    private static final String DATA_FORMAT_TYPE = ".json";

    @SerializedName("hash")
    private String hash;

    @SerializedName("requestHash")
    private String requestHash;

    @SerializedName("profileUrl")
    private String profileUrl;

    @SerializedName("preferredUsername")
    private String preferredUsername;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    @SerializedName("photos")
    private ImmutableList<GravatarProfilePhoto> profilePhotos = ImmutableList.of();

    @SerializedName("name")
    private GravatarProfileName name;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("pronouns")
    private String pronouns;

    @SerializedName("aboutMe")
    private String aboutMe;

    @SerializedName("currentLocation")
    private String currentLocation;

    @SerializedName("urls")
    private ImmutableList<GravatarProfileUrl> profileUrls = ImmutableList.of();

    /**
     * Constructs and returns a new GravatarProfile object from the provided JSON.
     *
     * @param jsonData the JSON data
     * @return a new GravatarProfile object
     * @throws NullPointerException     if the provided string is null
     * @throws IllegalArgumentException if the provided data is empty or the
     *                                  provided data is missing the "entry" key
     */
    public static GravatarProfile fromJson(String jsonData) {
        Preconditions.checkNotNull(jsonData);
        Preconditions.checkArgument(!jsonData.trim().isEmpty());

        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray entryArray = jsonObject.getAsJsonArray("entry");
        if (entryArray.size() < 1) throw new IllegalArgumentException("Provided data has no entries");

        Map<Type, JsonDeserializer<?>> immutableTypeMap
                = ImmutableMap.<Type, JsonDeserializer<?>>builder()
                .put(ImmutableList.class, new ImmutableListDeserializer())
                .build();

        GsonBuilder builder = new GsonBuilder();
        immutableTypeMap.forEach(builder::registerTypeAdapter);
        Gson gson = builder.create();
        JsonObject firstEntry = entryArray.get(0).getAsJsonObject();
        return gson.fromJson(firstEntry, GravatarProfile.class);
    }

    /**
     * Constructs and returns a new GravatarProfile object using the provided email.
     *
     * @param userEmail the user's email linked to their Gravatar profile
     * @return a new Gravatar profile object
     * @throws NullPointerException        if the provided email is null
     * @throws IllegalArgumentException    if the provided email is empty or invalid
     * @throws GravatarJavaClientException if the Gravatar profile cannot be read from the constructed URL
     */
    public static GravatarProfile fromUserEmail(String userEmail) {
        Preconditions.checkNotNull(userEmail);
        Preconditions.checkArgument(!userEmail.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(userEmail));

        String emailHash = GeneralUtils.emailAddressToGravatarHash(userEmail);
        String requestUrl = GRAVATAR_PROFILE_REQUEST_HEADER + emailHash + DATA_FORMAT_TYPE;
        String jsonData = GeneralUtils.readUrl(requestUrl);

        return GravatarProfile.fromJson(jsonData);
    }

    /**
     * Returns this profile's hash.
     *
     * @return this profile's hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Returns this profile's request hash.
     *
     * @return this profile's request hash
     */
    public String getRequestHash() {
        return requestHash;
    }

    /**
     * Returns the URL to this profile.
     *
     * @return the URL to this profile
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Returns the user's preferred username if present. Empty optional else.
     *
     * @return the user's preferred username if present. Empty optional else
     */
    public Optional<String> getPreferredUsername() {
        return Optional.ofNullable(preferredUsername);
    }

    /**
     * Returns the thumbnail URL if present. Empty optional else.
     *
     * @return the thumbnail URL if present. Empty optional else
     */
    public Optional<String> getThumbnailUrl() {
        return Optional.ofNullable(thumbnailUrl);
    }

    /**
     * Returns the list of profile photos if present. Empty optional else.
     *
     * @return the list of profile photos if present. Empty optional else
     */
    public ImmutableList<GravatarProfilePhoto> getProfilePhotos() {
        return profilePhotos;
    }

    /**
     * Returns the user's given name if present. Empty optional else.
     *
     * @return the user's given name if present. Empty optional else
     */
    public Optional<String> getGivenName() {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(name.getGivenName());
    }

    /**
     * Returns the user's family/last name if present. Empty optional else.
     *
     * @return the user's family/last name if present. Empty optional else
     */
    public Optional<String> getFamilyName() {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(name.getFamilyName());
    }

    /**
     * Returns the user's display name if present. Empty optional else.
     *
     * @return the user's display name if present. Empty optional else
     */
    public Optional<String> getDisplayName() {
        return Optional.ofNullable(displayName);
    }

    /**
     * Returns the user's pronouns if present. Empty optional else.
     *
     * @return the user's pronouns if present. Empty optional else
     */
    public Optional<String> getPronouns() {
        return Optional.ofNullable(pronouns);
    }

    /**
     * Returns the user's "about me" section if present. Empty optional else.
     *
     * @return the user's "about me" section if present. Empty optional else
     */
    public Optional<String> getAboutMe() {
        return Optional.ofNullable(aboutMe);
    }

    /**
     * Returns the user's publicly displayed location if present. Empty optional else.
     *
     * @return the user's publicly displayed location if present. Empty optional else
     */
    public Optional<String> getCurrentLocation() {
        return Optional.ofNullable(currentLocation);
    }

    /**
     * Returns a list of the user's profile URLs if present. Empty optional else.
     *
     * @return a list of the user's profile URLs if present. Empty optional else
     */
    public ImmutableList<GravatarProfileUrl> getProfileUrls() {
        return profileUrls;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfile{"
                + "hash=\"" + hash + "\""
                + ", requestHash=\"" + requestHash + "\""
                + ", profileUrl=\"" + profileUrl + "\""
                + ", preferredUsername=\"" + preferredUsername + "\""
                + ", thumbnailUrl=\"" + thumbnailUrl + "\""
                + ", profilePhotos=" + profilePhotos
                + ", givenName=\"" + getGivenName().orElse("") + "\""
                + ", familyName=\"" + getFamilyName().orElse("") + "\""
                + ", displayName=\"" + displayName + "\""
                + ", pronouns=\"" + pronouns + "\""
                + ", aboutMe=\"" + aboutMe + "\""
                + ", currentLocation=\"" + currentLocation + "\""
                + ", profileUrls=" + profileUrls
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
        ret = 31 * ret + requestHash.hashCode();
        ret = 31 * ret + profileUrl.hashCode();
        ret = 31 * ret + Objects.hash(preferredUsername);
        ret = 31 * ret + Objects.hash(thumbnailUrl);
        ret = 31 * ret + Objects.hashCode(profilePhotos);
        ret = 31 * ret + Objects.hashCode(getGivenName());
        ret = 31 * ret + Objects.hashCode(getFamilyName());
        ret = 31 * ret + Objects.hashCode(displayName);
        ret = 31 * ret + Objects.hashCode(pronouns);
        ret = 31 * ret + Objects.hashCode(aboutMe);
        ret = 31 * ret + Objects.hashCode(currentLocation);
        ret = 31 * ret + Objects.hashCode(profileUrls);
        return ret;
    }

    /**
     * Returns whether the provided object is equal to this.
     *
     * @param o the object
     * @return whether the provided object is equal to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof GravatarProfile)) {
            return false;
        }

        GravatarProfile other = (GravatarProfile) o;
        return hash.equals(other.hash)
                && requestHash.equals(other.requestHash)
                && profileUrl.equals(other.profileUrl)
                && preferredUsername.equals(other.preferredUsername)
                && thumbnailUrl.equals(other.thumbnailUrl)
                && profilePhotos.equals(other.profilePhotos)
                && getGivenName().equals(other.getGivenName())
                && getFamilyName().equals(other.getFamilyName())
                && displayName.equals(other.displayName)
                && pronouns.equals(other.pronouns)
                && aboutMe.equals(other.aboutMe)
                && currentLocation.equals(other.currentLocation)
                && profileUrls.equals(other.profileUrls);
    }
}
