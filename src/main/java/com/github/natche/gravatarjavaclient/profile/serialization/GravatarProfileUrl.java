package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;

/**
 * A link on a user's Gravatar profile page.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
@Immutable
public final class GravatarProfileUrl {
    /**
     * The display label for this profile URL.
     */
    private final String label;

    /**
     * The link for this profile URL.
     */
    @SerializedName("value")
    private final String url;

    /**
     * Constructs a new profile URL.
     *
     * @param label the label for this profile URL
     * @param url the link for this profile URL
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if any parameter is empty
     */
    public GravatarProfileUrl(String label, String url) {
        Preconditions.checkNotNull(label);
        Preconditions.checkNotNull(url);
        Preconditions.checkArgument(!label.isEmpty());
        Preconditions.checkArgument(!url.isEmpty());

        this.label = label;
        this.url = url;
    }

    /**
     * Returns the display label for this profile URL.
     *
     * @return the display label for this profile URL
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the link for this profile URL.
     *
     * @return the link for this profile URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileUrl{"
                + "label=\"" + label + "\""
                + ", url=\"" + url + "\""
                + "}";
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = label.hashCode();
        ret = 31 * ret + url.hashCode();
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
        return label.equals(other.label)
                && url.equals(other.url);
    }
}
