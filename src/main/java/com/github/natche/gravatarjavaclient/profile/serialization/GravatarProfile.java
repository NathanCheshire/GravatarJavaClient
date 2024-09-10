package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * The results of a Gravatar profile request.
 * <a href="https://en.gravatar.com/site/implement/profiles/">Profile Request API Documentation</a>.
 */
@Immutable
public final class GravatarProfile {
    /**
     * The SHA256 hash of the user's primary email address.
     */
    private final String hash;

    /**
     * The user's display name that appears on their profile.
     */
    @SerializedName("display_name")
    private final String displayName;

    /**
     * The full URL to the user's Gravatar profile.
     */
    @SerializedName("profile_url")
    private final String profileUrl;

    /**
     * The URL to the user's avatar image, if set.
     */
    @SerializedName("avatar_url")
    private final String avatarUrl;

    /**
     * Alternative text describing the user's avatar.
     */
    @SerializedName("avatar_alt_text")
    private final String avatarAltText;

    /**
     * The user's geographical location.
     */
    private final String location;

    /**
     * A short biography or description about the user found on their profile.
     */
    private final String description;

    /**
     * The user's current job title.
     */
    @SerializedName("job_title")
    private final String jobTitle;

    /**
     * The name of the company where the user is employed.
     */
    private final String company;

    /**
     * A list of verified accounts the user has added to their profile.
     */
    @SerializedName("verified_accounts")
    private final ImmutableList<GravatarProfileVerifiedAccount> verifiedAccounts;

    /**
     * A phonetic guide to pronouncing the user's name.
     */
    private final String pronunciation;

    /**
     * The pronouns the user prefers to use.
     */
    private final String pronouns;

    /**
     * The timezone the user has set.
     */
    private final String timezone;

    /**
     * The languages the user has selected on their profile.
     */
    private final ImmutableList<GravatarProfileLanguage> languages;

    /**
     * The user's first name.
     */
    @SerializedName("first_name")
    private final String firstName;

    /**
     * The user's last name.
     */
    @SerializedName("last_name")
    private final String lastName;

    /**
     * Whether this account is marked as an organization account;
     */
    @SerializedName("is_organization")
    private final boolean isOrganization;

    /**
     * The links the user has displayed on their profile.
     */
    private final ImmutableList<GravatarProfileUrl> links;

    /**
     * The interests the user has displayed on their profile.
     */
    private final ImmutableList<GravatarProfileInterest> interests;

    /**
     * The payments objects containing the payment data the user has displayed on their account.
     */
    private final GravatarProfilePayments payments;

    /**
     * The contact info object containing the contact information the user has displayed on their account
     */
    @SerializedName("contact_info")
    private final GravatarProfileContactInfo contactInfo;

    /**
     * The gallery images the user has displayed on their account.
     */
    private final ImmutableList<GravatarProfileGalleryImage> gallery;

    /**
     * The number of verified accounts associated with this user.
     */
    @SerializedName("number_verified_accounts")
    private final int numberVerifiedAccounts;

    /**
     * The last time this account was edited at.
     */
    @SerializedName("last_profile_edit")
    private final String lastProfileEdit;

    /**
     * The date this account was registered at.
     */
    @SerializedName("registration_date")
    private final String registrationDate;

    /**
     * Constructs a new GravatarProfile.
     *
     * @param hash                   the SHA256 hash of the user's primary email address
     * @param displayName            the user's display name
     * @param profileUrl             the full URL to the user's Gravatar profile
     * @param avatarUrl              the URL to the user's avatar image
     * @param avatarAltText          alternative text describing the user's avatar
     * @param location               the user's geographical location
     * @param description            a short biography or description about the user
     * @param jobTitle               the user's current job title
     * @param company                the name of the company where the user is employed
     * @param verifiedAccounts       a list of verified accounts the user has added to their profile
     * @param pronunciation          a phonetic guide to pronouncing the user's name
     * @param pronouns               the pronouns the user prefers to use
     * @param timezone               the timezone the user has set
     * @param languages              the languages the user has selected on their profile
     * @param firstName              the user's first name
     * @param lastName               the user's last name
     * @param isOrganization         whether this account is marked as an organization account
     * @param links                  the links the user has displayed on their profile
     * @param interests              the interests the user has displayed on their profile
     * @param payments               the payments objects containing the payment data
     * @param contactInfo            the contact info object containing the contact information
     * @param gallery                the gallery images the user has displayed on their account
     * @param numberVerifiedAccounts the number of verified accounts associated with this user
     * @param lastProfileEdit        the last time this account was edited
     * @param registrationDate       the date this account was registered
     */
    public GravatarProfile(String hash,
                           String displayName,
                           String profileUrl,
                           String avatarUrl,
                           String avatarAltText,
                           String location,
                           String description,
                           String jobTitle,
                           String company,
                           List<GravatarProfileVerifiedAccount> verifiedAccounts,
                           String pronunciation,
                           String pronouns,
                           String timezone,
                           List<GravatarProfileLanguage> languages,
                           String firstName,
                           String lastName,
                           boolean isOrganization,
                           List<GravatarProfileUrl> links,
                           List<GravatarProfileInterest> interests,
                           GravatarProfilePayments payments,
                           GravatarProfileContactInfo contactInfo,
                           List<GravatarProfileGalleryImage> gallery,
                           int numberVerifiedAccounts,
                           String lastProfileEdit,
                           String registrationDate) {
        this.hash = Preconditions.checkNotNull(hash);
        this.displayName = displayName;
        this.profileUrl = Preconditions.checkNotNull(profileUrl);
        this.avatarUrl = avatarUrl;
        this.avatarAltText = avatarAltText;
        this.location = location;
        this.description = description;
        this.jobTitle = jobTitle;
        this.company = company;
        this.verifiedAccounts = verifiedAccounts != null ? ImmutableList.copyOf(verifiedAccounts) : ImmutableList.of();
        this.pronunciation = pronunciation;
        this.pronouns = pronouns;
        this.timezone = timezone;
        this.languages = languages != null ? ImmutableList.copyOf(languages) : ImmutableList.of();
        this.firstName = firstName;
        this.lastName = lastName;
        this.isOrganization = isOrganization;
        this.links = links != null ? ImmutableList.copyOf(links) : ImmutableList.of();
        this.interests = interests != null ? ImmutableList.copyOf(interests) : ImmutableList.of();
        this.payments = payments;
        this.contactInfo = contactInfo;
        this.gallery = gallery != null ? ImmutableList.copyOf(gallery) : ImmutableList.of();
        this.numberVerifiedAccounts = numberVerifiedAccounts;
        this.lastProfileEdit = lastProfileEdit;
        this.registrationDate = registrationDate;
    }

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

    /**
     * Returns whether the provided object is equal to this.
     *
     * @param o the other object
     * @return whether the provided object is equal to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GravatarProfile that)) return false;

        return isOrganization == that.isOrganization
                && numberVerifiedAccounts == that.numberVerifiedAccounts
                && Objects.equals(hash, that.hash)
                && Objects.equals(displayName, that.displayName)
                && Objects.equals(profileUrl, that.profileUrl)
                && Objects.equals(avatarUrl, that.avatarUrl)
                && Objects.equals(avatarAltText, that.avatarAltText)
                && Objects.equals(location, that.location)
                && Objects.equals(description, that.description)
                && Objects.equals(jobTitle, that.jobTitle)
                && Objects.equals(company, that.company)
                && Objects.equals(verifiedAccounts, that.verifiedAccounts)
                && Objects.equals(pronunciation, that.pronunciation)
                && Objects.equals(pronouns, that.pronouns)
                && Objects.equals(timezone, that.timezone)
                && Objects.equals(languages, that.languages)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(links, that.links)
                && Objects.equals(interests, that.interests)
                && Objects.equals(payments, that.payments)
                && Objects.equals(contactInfo, that.contactInfo)
                && Objects.equals(gallery, that.gallery)
                && Objects.equals(lastProfileEdit, that.lastProfileEdit)
                && Objects.equals(registrationDate, that.registrationDate);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = hash.hashCode();
        ret = 31 * ret + (displayName != null ? displayName.hashCode() : 0);
        ret = 31 * ret + profileUrl.hashCode();
        ret = 31 * ret + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        ret = 31 * ret + (avatarAltText != null ? avatarAltText.hashCode() : 0);
        ret = 31 * ret + (location != null ? location.hashCode() : 0);
        ret = 31 * ret + (description != null ? description.hashCode() : 0);
        ret = 31 * ret + (jobTitle != null ? jobTitle.hashCode() : 0);
        ret = 31 * ret + (company != null ? company.hashCode() : 0);
        ret = 31 * ret + verifiedAccounts.hashCode();
        ret = 31 * ret + (pronunciation != null ? pronunciation.hashCode() : 0);
        ret = 31 * ret + (pronouns != null ? pronouns.hashCode() : 0);
        ret = 31 * ret + (timezone != null ? timezone.hashCode() : 0);
        ret = 31 * ret + languages.hashCode();
        ret = 31 * ret + (firstName != null ? firstName.hashCode() : 0);
        ret = 31 * ret + (lastName != null ? lastName.hashCode() : 0);
        ret = 31 * ret + (isOrganization ? 1 : 0);
        ret = 31 * ret + links.hashCode();
        ret = 31 * ret + interests.hashCode();
        ret = 31 * ret + (payments != null ? payments.hashCode() : 0);
        ret = 31 * ret + (contactInfo != null ? contactInfo.hashCode() : 0);
        ret = 31 * ret + gallery.hashCode();
        ret = 31 * ret + numberVerifiedAccounts;
        ret = 31 * ret + (lastProfileEdit != null ? lastProfileEdit.hashCode() : 0);
        ret = 31 * ret + (registrationDate != null ? registrationDate.hashCode() : 0);
        return ret;
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
                + ", displayName=\"" + displayName + "\""
                + ", profileUrl=\"" + profileUrl + "\""
                + ", avatarUrl=\"" + avatarUrl + "\""
                + ", avatarAltText=\"" + avatarAltText + "\""
                + ", location=\"" + location + "\""
                + ", description=\"" + description + "\""
                + ", jobTitle=\"" + jobTitle + "\""
                + ", company=\"" + company + "\""
                + ", verifiedAccounts=" + verifiedAccounts
                + ", pronunciation=\"" + pronunciation + "\""
                + ", pronouns=\"" + pronouns + "\""
                + ", timezone=\"" + timezone + "\""
                + ", languages=" + languages
                + ", firstName=\"" + firstName + "\""
                + ", lastName=\"" + lastName + "\""
                + ", isOrganization=" + isOrganization
                + ", links=" + links
                + ", interests=" + interests
                + ", payments=" + payments
                + ", contactInfo=" + contactInfo
                + ", gallery=" + gallery
                + ", numberVerifiedAccounts=" + numberVerifiedAccounts
                + ", lastProfileEdit=\"" + lastProfileEdit + "\""
                + ", registrationDate=\"" + registrationDate + "\""
                + "}";
    }
}
