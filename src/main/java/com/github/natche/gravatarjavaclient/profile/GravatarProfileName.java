package com.github.natche.gravatarjavaclient.profile;

import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;

/**
 * A name for a Gravatar user.
 */
@Immutable
public final class GravatarProfileName {
    @SerializedName("givenName")
    private final String givenName;

    @SerializedName("familyName")
    private final String familyName;

    @SerializedName("formatted")
    private final String formatted;

    /**
     * Constructs a new GravatarProfileName.
     *
     * @param givenName  the first name of the user
     * @param familyName the last name of the user
     * @param formatted  the combined given name and family name
     */
    public GravatarProfileName(String givenName, String familyName, String formatted) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.formatted = formatted;
    }

    /**
     * Returns the first name for this user.
     *
     * @return the first name for this user.
     */
    public String getGivenName() {
        return this.givenName;
    }

    /**
     * Returns the last name for this user.
     *
     * @return the last name for this user
     */
    public String getFamilyName() {
        return this.familyName;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileName{"
                + "givenName=\"" + givenName + "\""
                + ", familyName=\"" + familyName + "\""
                + "}";
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = givenName.hashCode();
        ret = 31 * ret + familyName.hashCode();
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
        } else if (!(o instanceof GravatarProfileName)) {
            return false;
        }

        GravatarProfileName other = (GravatarProfileName) o;
        return familyName.equals(other.familyName)
                && givenName.equals(other.givenName);
    }
}

