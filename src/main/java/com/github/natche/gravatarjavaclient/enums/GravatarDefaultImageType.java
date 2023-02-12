package com.github.natche.gravatarjavaclient.enums;

/**
 * The default image types supported by Gravatar for the image API.
 * These themes are generated based off of the user email hash.
 */
public enum GravatarDefaultImageType {
    /**
     * The 404 image type.
     * This tells Gravatar not to load any image if none is associated with the
     * email hash, instead return an HTTP 404 response.
     */
    _404("404"),

    /**
     * The mystery person image type.
     * A simple, cartoon-style silhouetted outline of a person (does not vary by email hash).
     */
    MYSTERY_PERSON("mp"),

    /**
     * The ident icon image type.
     * A geometric pattern based on an email hash; this is what GitHub uses for default user avatars.
     */
    IDENT_ICON("identicon"),

    /**
     * The monster id image type.
     * A generated "monster" with different colors, faces, etc.
     */
    MONSTER_ID("monsterid"),

    /**
     * The wavatar image type.
     * Generated faces with differing features and backgrounds.
     */
    WAVATAR("wavatar"),

    /**
     * The retro image type.
     * An awesome generated, 8-bit arcade-style pixelated faces.
     */
    RETRO("retro"),

    /**
     * The robo hash image type.
     * A generated robot with different colors, faces, etc; this is what the GitLens plugin for vscode uses.
     */
    ROBO_HASH("robohash"),

    /**
     * The blank image type.
     * A blank PNG image, how boring.
     */
    BLANK("blank");

    /**
     * The url parameter value for this {@link GravatarDefaultImageType}.
     */
    private final String urlParameterValue;

    GravatarDefaultImageType(String urlParameterValue) {
        this.urlParameterValue = urlParameterValue;
    }

    /**
     * Returns the url parameter value for this {@link GravatarDefaultImageType}.
     *
     * @return the url parameter value for this {@link GravatarDefaultImageType}
     */
    public String getUrlParameterValue() {
        return urlParameterValue;
    }
}
