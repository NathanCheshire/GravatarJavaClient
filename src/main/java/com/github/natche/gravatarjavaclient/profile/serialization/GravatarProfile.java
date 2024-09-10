package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;

/**
 * The results of a Gravatar profile request.
 * <a href="https://en.gravatar.com/site/implement/profiles/">Profile Request API Documentation</a>.
 */
@Immutable
public final class GravatarProfile {
    /**
     * The SHA256 hash of the user’s primary email address.
     */
    private String hash;

    /**
     * The user’s display name that appears on their profile.
     */
    @SerializedName("display_name")
    private String displayName;

    /**
     * The full URL to the user’s Gravatar profile.
     */
    @SerializedName("profile_url")
    private String profileUrl;

    /**
     * The URL to the user’s avatar image, if set.
     */
    @SerializedName("avatar_url")
    private String avatarUrl;

    /**
     * Alternative text describing the user’s avatar.
     */
    @SerializedName("avatar_alt_text")
    private String avatarAltText;

    /**
     * The user’s geographical location.
     */
    private String location;

    /**
     * A short biography or description about the user found on their profile.
     */
    private String description;

    /**
     * The user’s current job title.
     */
    @SerializedName("job_title")
    private String jobTitle;

    /**
     * The name of the company where the user is employed.
     */
    private String company;

    /**
     * A list of verified accounts the user has added to their profile.
     */
    @SerializedName("verified_accounts")
    private ImmutableList<GravatarProfileVerifiedAccount> verifiedAccounts;

    /**
     * A phonetic guide to pronouncing the user’s name.
     */
    private String pronunciation;

    /**
     * The pronouns the user prefers to use.
     */
    private String pronouns;

    /**
     * The timezone the user has set.
     */
    private String timezone;

    /**
     * The languages the user has selected on their profile.
     */
    private ImmutableList<GravatarProfileLanguage> languages;

    /**
     * The user's first name.
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The user's last name.
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * Whether this account is marked as an organization account;
     */
    @SerializedName("is_organization")
    private boolean isOrganization;

    /**
     * The links the user has displayed on their profile.
     */
    private ImmutableList<GravatarProfileUrl> links;

    /**
     * The interests the user has displayed on their profile.
     */
    private ImmutableList<GravatarProfileInterest> interests;

    /**
     * The payments objects containing the payment data the user has displayed on their account.
     */
    private GravatarProfilePayments payments;

    /**
     * The contact info object containing the contact information the user has displayed on their account
     */
    @SerializedName("contact_info")
    private GravatarProfileContactInfo contactInfo;

    /**
     * The gallery images the user has displayed on their account.
     */
    private ImmutableList<GravatarProfileGalleryImage> gallery;

    /**
     * The number of verified accounts associated with this user.
     */
    @SerializedName("number_verified_accounts")
    private int numberVerifiedAccounts;

    /**
     * The last time this account was edited at.
     */
    @SerializedName("last_profile_edit")
    private String lastProfileEdit;

    /**
     * The date this account was registered at.
     */
    @SerializedName("registration_date")
    private String registrationDate;

    // todo constructor.

    /**
     * Returns the SHA256 hash of the user’s primary email address.
     *
     * @return the SHA256 hash of the user’s primary email address
     */
    public String getHash() {
        return hash;
    }

    /**
     * Returns the user’s display name that appears on their profile.
     *
     * @return the user’s display name that appears on their profile
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the full URL to the user’s Gravatar profile.
     *
     * @return the full URL to the user’s Gravatar profile
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Returns the URL to the user’s avatar image, if set.
     *
     * @return the URL to the user’s avatar image, if set
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Returns alternative text describing the user’s avatar.
     *
     * @return alternative text describing the user’s avatar
     */
    public String getAvatarAltText() {
        return avatarAltText;
    }

    /**
     * Returns the user’s geographical location.
     *
     * @return The user’s geographical location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns a short biography or description about the user found on their profile.
     *
     * @return a short biography or description about the user found on their profile
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the user’s current job title.
     *
     * @return the user’s current job title
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Returns the name of the company where the user is employed.
     *
     * @return the name of the company where the user is employed
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns a list of verified accounts the user has added to their profile.
     * The number of verified accounts displayed is limited to a maximum of 4 in unauthenticated requests
     *
     * @return a list of verified accounts
     */
    public ImmutableList<GravatarProfileVerifiedAccount> getVerifiedAccounts() {
        return verifiedAccounts;
    }

    /**
     * Returns a phonetic guide to pronouncing the user’s name.
     *
     * @return a phonetic guide to pronouncing the user’s name
     */
    public String getPronunciation() {
        return pronunciation;
    }

    /**
     * Returns the pronouns the user prefers to use.
     *
     * @return the pronouns the user prefers to use
     */
    public String getPronouns() {
        return pronouns;
    }

    /**
     * Returns the timezone the user has set.
     *
     * @return the timezone the user has set
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Returns the languages the user has selected on their profile.
     *
     * @return the languages the user has selected on their profile
     */
    public ImmutableList<GravatarProfileLanguage> getLanguages() {
        return languages;
    }

    /**
     * Returns the user's first name.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns whether this account is marked as an organization account.
     *
     * @return whether this account is marked as an organization account
     */
    public boolean isOrganization() {
        return isOrganization;
    }

    /**
     * Returns the links the user has displayed on their profile.
     *
     * @return the links the user has displayed on their profile
     */
    public ImmutableList<GravatarProfileUrl> getLinks() {
        return links;
    }

    /**
     * Returns the interests the user has displayed on their profile.
     *
     * @return the interests the user has displayed on their profile
     */
    public ImmutableList<GravatarProfileInterest> getInterests() {
        return interests;
    }

    /**
     * Returns the payments objects containing the payment data the user has displayed on their account.
     *
     * @return the payments objects containing the payment data the user has displayed on their account
     */
    public GravatarProfilePayments getPayments() {
        return payments;
    }

    /**
     * Returns the contact info object containing the contact information the user has displayed on their account.
     *
     * @return the contact info object containing the contact information the user has displayed on their account
     */
    public GravatarProfileContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Returns the gallery images the user has displayed on their account.
     *
     * @return the gallery images the user has displayed on their account
     */
    public ImmutableList<GravatarProfileGalleryImage> getGallery() {
        return gallery;
    }

    /**
     * Returns the number of verified accounts associated with this user.
     *
     * @return the number of verified accounts associated with this user
     */
    public int getNumberVerifiedAccounts() {
        return numberVerifiedAccounts;
    }

    /**
     * Returns the last time this account was edited at.
     *
     * @return the last time this account was edited at
     */
    public Instant getLastProfileEdit() {
        return Instant.parse(lastProfileEdit);
    }

    /**
     * Returns the date this account was registered at.
     *
     * @return the date this account was registered at
     */
    public Instant getRegistrationDate() {
        return Instant.parse(registrationDate);
    }
}
