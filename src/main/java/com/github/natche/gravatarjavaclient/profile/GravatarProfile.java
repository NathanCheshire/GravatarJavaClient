package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import org.json.JSONArray;
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
     */
    private void requestData() {
        String requestUrl = gravatarProfileRequestHeader
                + GeneralUtils.emailAddressToGravatarHash(userEmail) + formatType;
        String jsonString = GeneralUtils.readUrl(requestUrl);
        JSONObject masterObject = new JSONObject(jsonString);
        JSONArray entry = masterObject.getJSONArray("entry");
        if (entry.isEmpty()) return;

        // todo keys might not exist

        JSONObject firstEntry = entry.getJSONObject(0);
        String id = firstEntry.getString("id");
        String hash = firstEntry.getString("hash");
        String requestHash = firstEntry.getString("requestHash");
        String profileUrl = firstEntry.getString("profileUrl");
        String preferredUsername = firstEntry.getString("preferredUsername");
        String thumbnailUrl = firstEntry.getString("thumbnailUrl");

        ArrayList<GravatarProfilePhoto> profilePhotos = new ArrayList<>();
        JSONArray photosArray = firstEntry.getJSONArray("photos");
        for (int i = 0 ; i < photosArray.length() ; i++) {
            JSONObject urlObject = photosArray.getJSONObject(i);
            String name = urlObject.getString("type");
            String link = urlObject.getString("value");
            profilePhotos.add(new GravatarProfilePhoto(name, link));
        }
        ImmutableList<GravatarProfilePhoto> profilePhotos1 = ImmutableList.copyOf(profilePhotos);

        JSONObject nameObject = firstEntry.getJSONObject("name");
        String givenName = nameObject.getString("givenName");
        String familyName = nameObject.getString("familyName");
        String formatted = nameObject.getString("formatted");

        String displayName = firstEntry.getString("displayName");
        String pronouns = firstEntry.getString("pronouns");
        String aboutMe = firstEntry.getString("aboutMe");
        String currentLocation = firstEntry.getString("currentLocation");

        ArrayList<GravatarProfileUrl> profileUrls = new ArrayList<>();
        JSONArray urlsArray = firstEntry.getJSONArray("urls");
        for (int i = 0 ; i < urlsArray.length() ; i++) {
            JSONObject urlObject = urlsArray.getJSONObject(i);
            String name = urlObject.getString("title");
            String link = urlObject.getString("value");
            profileUrls.add(new GravatarProfileUrl(name, link));
        }
        ImmutableList<GravatarProfileUrl> profileUrls1 = ImmutableList.copyOf(profileUrls);
    }
}
