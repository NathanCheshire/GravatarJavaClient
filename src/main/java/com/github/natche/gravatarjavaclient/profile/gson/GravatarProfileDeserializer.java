package com.github.natche.gravatarjavaclient.profile.gson;

import com.github.natche.gravatarjavaclient.profile.serialization.*;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class GravatarProfileDeserializer implements JsonDeserializer<GravatarProfile> {
    @Override
    public GravatarProfile deserialize(JsonElement json,
                                       Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String hash = jsonObject.get("hash").getAsString();
        String profileUrl = jsonObject.get("profile_url").getAsString();

        String displayName = getOptionalString(jsonObject, "display_name");
        String avatarUrl = getOptionalString(jsonObject, "avatar_url");
        String avatarAltText = getOptionalString(jsonObject, "avatar_alt_text");
        String location = getOptionalString(jsonObject, "location");
        String description = getOptionalString(jsonObject, "description");
        String jobTitle = getOptionalString(jsonObject, "job_title");
        String company = getOptionalString(jsonObject, "company");
        String pronunciation = getOptionalString(jsonObject, "pronunciation");
        String pronouns = getOptionalString(jsonObject, "pronouns");
        String timezone = getOptionalString(jsonObject, "timezone");
        String firstName = getOptionalString(jsonObject, "first_name");
        String lastName = getOptionalString(jsonObject, "last_name");
        boolean isOrganization = jsonObject.has("is_organization")
                && jsonObject.get("is_organization").getAsBoolean();

        String lastProfileEdit = getOptionalString(jsonObject, "last_profile_edit");
        String registrationDate = getOptionalString(jsonObject, "registration_date");

        List<GravatarProfileVerifiedAccount> verifiedAccounts = getOptionalList(
                jsonObject, "verified_accounts", context,
                new TypeToken<List<GravatarProfileVerifiedAccount>>() {}.getType()
        );
        List<GravatarProfileLanguage> languages = getOptionalList(
                jsonObject, "languages", context,
                new TypeToken<List<GravatarProfileLanguage>>() {}.getType()
        );

        List<GravatarProfileUrl> links = getOptionalList(
                jsonObject, "links", context,
                new TypeToken<List<GravatarProfileUrl>>() {}.getType()
        );

        List<GravatarProfileInterest> interests = getOptionalList(
                jsonObject, "interests", context,
                new TypeToken<List<GravatarProfileInterest>>() {}.getType()
        );

        List<GravatarProfileGalleryImage> gallery = getOptionalList(
                jsonObject, "gallery", context,
                new TypeToken<List<GravatarProfileGalleryImage>>() {}.getType()
        );

        GravatarProfilePayments payments = getOptionalObject(
                jsonObject, "payments", context,
                GravatarProfilePayments.class
        );

        GravatarProfileContactInfo contactInfo = getOptionalObject(
                jsonObject, "contact_info", context,
                GravatarProfileContactInfo.class
        );

        int numberVerifiedAccounts = jsonObject.has("number_verified_accounts")
                ? jsonObject.get("number_verified_accounts").getAsInt() : 0;

        return new GravatarProfile(
                hash, displayName, profileUrl, avatarUrl,
                avatarAltText, location, description, jobTitle,
                company, verifiedAccounts, pronunciation, pronouns,
                timezone, languages, firstName, lastName,
                isOrganization, links, interests, payments,
                contactInfo, gallery, numberVerifiedAccounts, lastProfileEdit,
                registrationDate
        );
    }

    /**
     * Helper method to retrieve an optional string from a JsonObject.
     */
    private String getOptionalString(JsonObject jsonObject, String memberName) {
        return jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()
                ? jsonObject.get(memberName).getAsString()
                : null;
    }

    /**
     * Helper method to retrieve an optional list from a JsonObject.
     */
    private <T> List<T> getOptionalList(JsonObject jsonObject, String memberName, JsonDeserializationContext context,
                                        Type type) {
        return jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()
                ? context.deserialize(jsonObject.get(memberName), type)
                : ImmutableList.of();  // Default to empty list
    }

    /**
     * Helper method to retrieve an optional object from a JsonObject.
     */
    private <T> T getOptionalObject(JsonObject jsonObject, String memberName, JsonDeserializationContext context,
                                    Class<T> classOfT) {
        return jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()
                ? context.deserialize(jsonObject.get(memberName), classOfT)
                : null;
    }
}

