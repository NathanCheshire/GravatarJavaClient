package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a gallery image in a Gravatar user's profile.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
public final class GravatarProfileGalleryImage {
    /**
     * The URL to the image.
     */
    @SerializedName("url")
    private final String url;

    /**
     * The alternative text for the image.
     */
    @SerializedName("alt_text")
    private final String altText;

    /**
     * Constructs a new GravatarProfileGalleryImage.
     *
     * @param url     the URL to the image
     * @param altText the alternative text for the image
     * @throws NullPointerException if any parameter is null
     */
    public GravatarProfileGalleryImage(String url,
                                       String altText) {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(altText);

        this.url = url;
        this.altText = altText;
    }

    /**
     * Returns the URL to the image.
     *
     * @return the URL to the image
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the alternative text for the image.
     *
     * @return the alternative text for the image
     */
    public String getAltText() {
        return altText;
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
        if (!(o instanceof GravatarProfileGalleryImage other)) return false;
        return url.equals(other.url)
                && altText.equals(other.altText);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = url.hashCode();
        ret = 31 * ret + altText.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileGalleryImage{"
                + "url=\"" + url + "\""
                + ", altText=\"" + altText + "\""
                + "}";
    }
}

