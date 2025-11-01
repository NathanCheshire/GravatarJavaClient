package com.github.natche.gravatarjavaclient.enums;

/**
 * The default image types supported by Gravatar for their Avatar API.
 * These themes are generated per-user based off of the provided hash.
 */
@SuppressWarnings("SpellCheckingInspection")
public enum GravatarDefaultImageType {
    /**
     * The 404 image type.
     * This tells Gravatar not to load any image if none is associated with the
     * email hash, instead return an HTTP 404 response.
     */
    _404("404"),

    /**
     * The mystery person image type.
     * A simple, cartoon-style silhouetted outline of a person.
     * Note: this does not vary by email hash.
     */
    MysteryPerson("mp"),

    /**
     * The ident icon image type.
     * A geometric pattern based on an email hash; this is what GitHub uses for their default user avatars.
     */
    IdentIcon("identicon"),

    /**
     * The monster ID image type.
     * A generated "monster" with different colors, faces, and features.
     */
    MonsterId("monsterid"),

    /**
     * The wavatar image type.
     * Generated faces with differing features and backgrounds.
     */
    Wavatar("wavatar"),

    /**
     * The retro image type.
     * An awesome generated, 8-bit arcade-style pixelated faces.
     */
    Retro("retro"),

    /**
     * The RoboHash image type.
     * A generated robot with different colors, faces, and features.
     * This is what the GitLens plugin for vscode uses.
     */
    RoboHash("robohash"),

    /**
     * The blank image type, returns a blank PNG.
     */
    Blank("blank");

    /**
     * The URL parameter value for this {@link GravatarDefaultImageType}.
     */
    private final String urlParameterValue;

    GravatarDefaultImageType(String urlParameterValue) {
        this.urlParameterValue = urlParameterValue;
    }

    /**
     * Returns the URL parameter value for this {@link GravatarDefaultImageType}.
     *
     * @return the URL parameter value for this {@link GravatarDefaultImageType}
     */
    public String getUrlParameterValue() {
        return urlParameterValue;
    }
}
