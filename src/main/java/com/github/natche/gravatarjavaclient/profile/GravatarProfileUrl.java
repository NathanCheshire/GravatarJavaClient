package com.github.natche.gravatarjavaclient.profile;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

/**
 * A linked URL on a user's Gravatar profile page.
 */
@Immutable
@SuppressWarnings("ClassCanBeRecord")
public final class GravatarProfileUrl {
    /**
     * The display name for this profile URL.
     */
    private final String name;

    /**
     * The link for this profile URL.
     */
    private final String link;

    /**
     * Constructs a new profile URL.
     *
     * @param name the name for this profile URL
     * @param link the link for this profile URL
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if any parameter is empty
     */
    public GravatarProfileUrl(String name, String link) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(link);
        Preconditions.checkArgument(!name.isEmpty());
        Preconditions.checkArgument(!link.isEmpty());

        this.name = name;
        this.link = link;
    }

    /**
     * Returns the display name for this profile URL.
     *
     * @return the display name for this profile URL
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the link for this profile URL.
     *
     * @return the link for this profile URL
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
        return "GravatarProfileUrl{"
                + "name=\"" + name + "\""
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
        int ret = name.hashCode();
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
        } else if (!(o instanceof GravatarProfileUrl)) {
            return false;
        }

        GravatarProfileUrl other = (GravatarProfileUrl) o;
        return name.equals(other.name)
                && link.equals(other.link);
    }
}
