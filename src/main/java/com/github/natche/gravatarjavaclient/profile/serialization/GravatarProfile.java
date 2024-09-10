package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;

/**
 * The results of a Gravatar profile request.
 * <a href="https://en.gravatar.com/site/implement/profiles/">Profile Request API Documentation</a>.
 */
@Immutable
public final class GravatarProfile {
    private String hash;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("profile_url")
    private String profileUrl;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("avatar_alt_text")
    private String avatarAltText;
    private String location;
    private String description;
    @SerializedName("job_title")
    private String jobTitle;
    private String company;
    @SerializedName("verified_accounts")
    private ImmutableList<GravatarProfileVerifiedAccount> verifiedAccounts;
    private String pronunciation;
    private String pronouns;
    private String timezone;
    private ImmutableList<GravatarProfileLanguage> languages;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("is_organization")
    private String isOrganization;
    private ImmutableList<GravatarProfileUrl> links;
    private ImmutableList<GravatarProfileInterest> interests;
    private GravatarProfilePayments payments;
    @SerializedName("contact_info")
    private GravatarProfileContactInfo contactInfo;
    private ImmutableList<GravatarProfileGalleryImage> gallery;
    @SerializedName("number_verified_accounts")
    private String numberVerifiedAccounts;
    @SerializedName("last_profile_edit")
    private String lastProfileEdit;
    @SerializedName("registration_date")
    private String registrationDate;
}
