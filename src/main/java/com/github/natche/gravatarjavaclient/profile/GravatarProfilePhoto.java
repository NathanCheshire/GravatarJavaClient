package com.github.natche.gravatarjavaclient.profile;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

/**
 * A photo on a user's Gravatar profile page.
 */
@Immutable
@SuppressWarnings("ClassCanBeRecord")
public final class GravatarProfilePhoto {
    /**
     * The type of this profile photo.
     */
    private final String type;

    /**
     * The link for this profile photo.
     */
    private final String link;

    /**
     * Constructs a new GravatarProfilePhoto.
     *
     * @param type the type of this profile photo
     * @param link the link for this profile photo
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if any parameter is empty
     */
    public GravatarProfilePhoto(String type, String link) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(link);
        Preconditions.checkArgument(!type.isEmpty());
        Preconditions.checkArgument(!link.isEmpty());

        this.type = type;
        this.link = link;
    }

    /**
     * Returns the type of this profile photo.
     *
     * @return the type of this profile photo
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the link for this profile photo.
     *
     * @return the link for this profile photo
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfilePhoto{"
                + "type=\"" + type + "\""
                + ", link=\"" + link + "\""
                + "}";
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = type.hashCode();
        ret = 31 * ret + link.hashCode();
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
        } else if (!(o instanceof GravatarProfilePhoto)) {
            return false;
        }

        GravatarProfilePhoto other = (GravatarProfilePhoto) o;
        return type.equals(other.type)
                && link.equals(other.link);
    }
}
