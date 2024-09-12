package com.github.natche.gravatarjavaclient.profile.gson;

import com.github.natche.gravatarjavaclient.profile.serialization.*;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A deserializer for deserializing a {@link GravatarProfile} from a JSON element.
 */
public final class GravatarProfileDeserializer implements JsonDeserializer<GravatarProfile> {
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
     * Returns an optional String from a JsonObject.
     */
    private String getOptionalString(JsonObject jsonObject, String memberName) {
        return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsString() : null;
    }

    /**
     * Returns an optional list from a JsonObject. If the list is not present,
     * an empty list is returned instead of null. See Effective Java item 43.
     */
    private <T> List<T> getOptionalList(JsonObject jsonObject, String memberName,
                                        JsonDeserializationContext context, Type type) {
        return jsonObject.has(memberName)
                ? context.deserialize(jsonObject.get(memberName), type) : ImmutableList.of();
    }

    /**
     * Returns an optional object from a jsonObject.
     */
    private <T> T getOptionalObject(JsonObject jsonObject, String memberName,
                                    JsonDeserializationContext context, Class<T> classOfT) {
        return jsonObject.has(memberName)
                ? context.deserialize(jsonObject.get(memberName), classOfT) : null;
    }
}

