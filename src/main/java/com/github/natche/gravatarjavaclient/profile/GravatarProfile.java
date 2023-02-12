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

/**
 * The results of a Gravatar profile request.
 */
@Immutable
public final class GravatarProfile {
    /**
     * The header for Gravatar profile requests.
     */
    private static final String gravatarProfileRequestHeader = "https://www.gravatar.com/";

    /**
     * The format for the returned data from profile requests.
     */
    private static final String formatType = ".json";

    /**
     * The user email address.
     */
    private final String userEmail;

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
     * The url to this profile.
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
     * The url to this profile's primary thumbnail.
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
     * The user's dislay name.
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
     * The list of urls the user has linked to their profile.
     */
    private ImmutableList<GravatarProfileUrl> profileUrls = ImmutableList.of();

    /**
     * Constructs a new Gravatar profile object.
     *
     * @param userEmail the user email to pull the profile data from.
     * @throws NullPointerException     if the provided user email is null
     * @throws IllegalArgumentException if the provided user email is empty or invalid
     */
    public GravatarProfile(String userEmail) {
        Preconditions.checkNotNull(userEmail);
        Preconditions.checkArgument(!userEmail.isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(userEmail));

        this.userEmail = userEmail;

        requestData();
    }

    /**
     * Performs a request to the Gravatar API using the provided email address and fills the data fields of this class.
     *
     * @throws GravatarJavaClientException if an exception occurs reading from the url generated from the user email
     * @throws JSONException               if an expected key is missing
     */
    private void requestData() {
        String emailHash = GeneralUtils.emailAddressToGravatarHash(userEmail);
        String requestUrl = gravatarProfileRequestHeader + emailHash + formatType;
        JSONObject masterObject = new JSONObject(GeneralUtils.readUrl(requestUrl));
        JSONArray entry = masterObject.getJSONArray("entry");
        if (entry.isEmpty()) return;
        JSONObject firstEntry = entry.getJSONObject(0);

        /*
        Required fields
         */
        id = firstEntry.getString("id");
        hash = firstEntry.getString("hash");
        requestHash = firstEntry.getString("requestHash");
        profileUrl = firstEntry.getString("profileUrl");

        /*
        Optional fields
         */
        if (firstEntry.has("preferredUsername")) {
            preferredUsername = firstEntry.getString("preferredUsername");
        }
        if (firstEntry.has("thumbnailUrl")) {
            thumbnailUrl = firstEntry.getString("thumbnailUrl");
        }
        if (firstEntry.has("photos")) {
            profilePhotos = extractProfilePhotos(firstEntry.getJSONArray("photos"));
        }
        if (firstEntry.has("name")) {
            JSONObject nameObject = firstEntry.getJSONObject("name");
            extractNameFields(nameObject);
        }
        if (firstEntry.has("displayName")) {
            displayName = firstEntry.getString("displayName");
        }
        if (firstEntry.has("pronouns")) {
            pronouns = firstEntry.getString("pronouns");
        }
        if (firstEntry.has("aboutMe")) {
            aboutMe = firstEntry.getString("aboutMe");
        }
        if (firstEntry.has("currentLocation")) {
            currentLocation = firstEntry.getString("currentLocation");
        }
        if (firstEntry.has("urls")) {
            profileUrls = extractProfileUrls(firstEntry.getJSONArray("urls"));
        }
    }

    // todo getters for required and optionals for optional params

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
    private ImmutableList<GravatarProfilePhoto> extractProfilePhotos(JSONArray photosArray) {
        ArrayList<GravatarProfilePhoto> profilePhotos = new ArrayList<>();

        for (int i = 0 ; i < photosArray.length() ; i++) {
            JSONObject urlObject = photosArray.getJSONObject(i);
            String name = urlObject.getString("type");
            String link = urlObject.getString("value");
            profilePhotos.add(new GravatarProfilePhoto(name, link));
        }
        return ImmutableList.copyOf(profilePhotos);
    }

    /**
     * Extracts the profile urls from the provided array.
     *
     * @param urlsArray the url JSON array
     * @return the profile urls from the provided array
     */
    private ImmutableList<GravatarProfileUrl> extractProfileUrls(JSONArray urlsArray) {
        ArrayList<GravatarProfileUrl> profileUrls = new ArrayList<>();

        for (int i = 0 ; i < urlsArray.length() ; i++) {
            JSONObject urlObject = urlsArray.getJSONObject(i);
            String name = urlObject.getString("title");
            String link = urlObject.getString("value");
            profileUrls.add(new GravatarProfileUrl(name, link));
        }

        return ImmutableList.copyOf(profileUrls);
    }
}
