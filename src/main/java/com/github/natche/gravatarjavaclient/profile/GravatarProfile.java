package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

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

    // -----------------------------------
    // Fields which will always be present
    // -----------------------------------

    /**
     * The user profile id.
     */
    private String id = null;

    /**
     * The user email MD5 hash.
     */
    private String hash = null;

    /**
     * The user request hash, identical to {@link #hash}.
     */
    private String requestHash = null;

    /**
     * The URL to this profile.
     */
    private String profileUrl = null;

    // --------------------------------------------
    // Optional fields not guaranteed to be present
    // --------------------------------------------

    /**
     * The profile's preferred username.
     */
    private String preferredUsername = null;

    /**
     * The URL to this profile's primary thumbnail.
     */
    private String thumbnailUrl = null;

    /**
     * The list of profile photos on this profile.
     */
    private ImmutableList<GravatarProfilePhoto> profilePhotos = ImmutableList.of();

    /**
     * The user's first name.
     */
    private String givenName = null;

    /**
     * The user's last name.
     */
    private String familyName = null;

    /**
     * The user's display name.
     */
    private String displayName = null;

    /**
     * The user's pronouns.
     */
    private String pronouns = null;

    /**
     * The user's about me section.
     */
    private String aboutMe = null;

    /**
     * The user's set location.
     */
    private String currentLocation = null;

    /**
     * The list of URLs the user has linked to their profile.
     */
    private ImmutableList<GravatarProfileUrl> profileUrls = ImmutableList.of();

    /**
     * Constructs a new GravatarProfile object.
     *
     * @param jsonData the json data to parse for this profile object
     * @throws NullPointerException     if the json data is null
     * @throws IllegalArgumentException if the json data is empty or invalid
     * @throws JSONException            if a required key does not exist in the user's json data
     */
    public GravatarProfile(String jsonData) {
        Preconditions.checkNotNull(jsonData);
        Preconditions.checkArgument(!jsonData.isEmpty());

        JSONObject masterObject = new JSONObject(jsonData);
        JSONArray entryArray = masterObject.getJSONArray("entry");
        JSONObject entryObject = entryArray.getJSONObject(0);

        extractAndSetRequiredFields(entryObject);
        extractAndSetOptionalFields(entryObject);
    }

    /**
     * Constructs and returns a new GravatarProfile object using the provided email.
     *
     * @param userEmail the user's email linked to their Gravatar profile
     * @return a new Gravatar profile object
     * @throws NullPointerException        if the provided email is null
     * @throws IllegalArgumentException    if the provided email is empty or invalid
     * @throws GravatarJavaClientException if the Gravatar profile cannot be read from the constructed URL
     * @throws JSONException               if a required key does not exist in the user's json data
     */
    public static GravatarProfile fromUserEmail(String userEmail) {
        Preconditions.checkNotNull(userEmail);
        Preconditions.checkArgument(!userEmail.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(userEmail));

        String emailHash = GeneralUtils.emailAddressToGravatarHash(userEmail);
        String requestUrl = GRAVATAR_PROFILE_REQUEST_HEADER + emailHash + DATA_FORMAT_TYPE;
        String jsonData = GeneralUtils.readUrl(requestUrl);

        return new GravatarProfile(jsonData);
    }

    /**
     * Returns this profile's user id.
     *
     * @return this profile's user id
     */
    public String getUserId() {
        return id;
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
        return Optional.ofNullable(givenName);
    }

    /**
     * Returns the user's family/last name if present. Empty optional else.
     *
     * @return the user's family/last name if present. Empty optional else
     */
    public Optional<String> getFamilyName() {
        return Optional.ofNullable(familyName);
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
     * Returns the user's about me section if present. Empty optional else.
     *
     * @return the user's about me section if present. Empty optional else
     */
    public Optional<String> getAboutMe() {
        return Optional.ofNullable(aboutMe);
    }

    /**
     * Returns the user's current location if present. Empty optional else.
     *
     * @return the user's current location if present. Empty optional else
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
     * Extracts and sets the required fields from the provided object.
     *
     * @param object the object
     * @throws JSONException if an expected key cannot does not exist on the provided object
     */
    private void extractAndSetRequiredFields(JSONObject object) {
        id = object.getString("id");
        hash = object.getString("hash");
        requestHash = object.getString("requestHash");
        profileUrl = object.getString("profileUrl");
    }

    /**
     * Extracts and sets the optional fields from the provided object.
     *
     * @param object the object
     */
    private void extractAndSetOptionalFields(JSONObject object) {
        if (object.has("preferredUsername")) {
            preferredUsername = object.getString("preferredUsername");
        }
        if (object.has("thumbnailUrl")) {
            thumbnailUrl = object.getString("thumbnailUrl");
        }
        if (object.has("photos")) {
            profilePhotos = extractProfilePhotos(object.getJSONArray("photos"));
        }
        if (object.has("name")) {
            JSONObject nameObject = object.getJSONObject("name");
            extractNameFields(nameObject);
        }
        if (object.has("displayName")) {
            displayName = object.getString("displayName");
        }
        if (object.has("pronouns")) {
            pronouns = object.getString("pronouns");
        }
        if (object.has("aboutMe")) {
            aboutMe = object.getString("aboutMe");
        }
        if (object.has("currentLocation")) {
            currentLocation = object.getString("currentLocation");
        }
        if (object.has("urls")) {
            profileUrls = extractProfileUrls(object.getJSONArray("urls"));
        }
    }

    /**
     * Extracts the name field parts from the provided name object.
     *
     * @param nameObject the name object
     */
    private void extractNameFields(JSONObject nameObject) {
        if (nameObject.has("givenName")) givenName = nameObject.getString("givenName");
        if (nameObject.has("familyName")) familyName = nameObject.getString("familyName");

        /*
        Note to maintainers: the key "formatted" is present here which just performs a string
        concatenation using givenName and familyName. I have no idea why gravatar sends this information
        but I think it is rather stupid and obviously is redundant. Therefore, I have chosen to ignore it.
         */
    }

    /**
     * Extracts the profile photos from the provided array.
     *
     * @param photosArray the photos JSON array
     * @return the profile photos from the provided array
     */
    private static ImmutableList<GravatarProfilePhoto> extractProfilePhotos(JSONArray photosArray) {
        ArrayList<GravatarProfilePhoto> profilePhotos = new ArrayList<>();

        IntStream.range(0, photosArray.length()).forEach(index -> {
            JSONObject urlObject = photosArray.getJSONObject(index);
            String name = urlObject.getString("type");
            String link = urlObject.getString("value");
            profilePhotos.add(new GravatarProfilePhoto(name, link));
        });

        return ImmutableList.copyOf(profilePhotos);
    }

    /**
     * Extracts the profile urls from the provided array.
     *
     * @param urlsArray the url JSON array
     * @return the profile urls from the provided array
     */
    private static ImmutableList<GravatarProfileUrl> extractProfileUrls(JSONArray urlsArray) {
        ArrayList<GravatarProfileUrl> profileUrls = new ArrayList<>();

        IntStream.range(0, urlsArray.length()).forEach(index -> {
            JSONObject urlObject = urlsArray.getJSONObject(index);
            String name = urlObject.getString("title");
            String link = urlObject.getString("value");
            profileUrls.add(new GravatarProfileUrl(name, link));
        });

        return ImmutableList.copyOf(profileUrls);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfile{"
                + ", id=\"" + id + "\""
                + ", hash=\"" + hash + "\""
                + ", requestHash=\"" + requestHash + "\""
                + ", profileUrl=\"" + profileUrl + "\""
                + ", preferredUsername=\"" + preferredUsername + "\""
                + ", thumbnailUrl=\"" + thumbnailUrl + "\""
                + ", profilePhotos=" + profilePhotos
                + ", givenName=\"" + givenName + "\""
                + ", familyName=\"" + familyName + "\""
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
        int ret = id.hashCode();
        ret = 31 * ret + hash.hashCode();
        ret = 31 * ret + requestHash.hashCode();
        ret = 31 * ret + profileUrl.hashCode();
        ret = 31 * ret + Objects.hash(preferredUsername);
        ret = 31 * ret + Objects.hash(thumbnailUrl);
        ret = 31 * ret + Objects.hashCode(profilePhotos);
        ret = 31 * ret + Objects.hashCode(givenName);
        ret = 31 * ret + Objects.hashCode(familyName);
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
        return id.equals(other.id)
                && hash.equals(other.hash)
                && requestHash.equals(other.requestHash)
                && profileUrl.equals(other.profileUrl)
                && preferredUsername.equals(other.preferredUsername)
                && thumbnailUrl.equals(other.thumbnailUrl)
                && profilePhotos.equals(other.profilePhotos)
                && givenName.equals(other.givenName)
                && familyName.equals(other.familyName)
                && displayName.equals(other.displayName)
                && pronouns.equals(other.pronouns)
                && aboutMe.equals(other.aboutMe)
                && currentLocation.equals(other.currentLocation)
                && profileUrls.equals(other.profileUrls);
    }
}
