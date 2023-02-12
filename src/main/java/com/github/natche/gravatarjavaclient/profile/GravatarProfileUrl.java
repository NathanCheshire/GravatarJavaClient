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
     * The display name for this profile url.
     */
    private final String name;

    /**
     * The link for this profile url,
     */
    private final String link;

    /**
     * Constructs a new profile url.
     *
     * @param name the name for this profile url
     * @param link the link for this profile url
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
     * Returns the display name for this profile url.
     *
     * @return the display name for this profile url
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the link for this profile url.
     *
     * @return the link for this profile url
     */
    public String getLink() {
        return link;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GravatarProfileUrl{"
                + "name=\"" + name + "\""
                + ", link=\"" + link + "\""
                + "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int ret = name.hashCode();
        ret = 31 * ret + link.hashCode();
        return ret;
    }

    /**
     * {@inheritDoc}
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
