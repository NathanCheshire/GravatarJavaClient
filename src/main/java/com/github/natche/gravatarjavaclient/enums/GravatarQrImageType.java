package com.github.natche.gravatarjavaclient.enums;

/**
 * The valid types of QR images returned by the Gravatar API.
 */
public enum GravatarQrImageType {
    /**
     * No logo or avatar.
     */
    BLANK,

    /**
     * Same as {@link #BLANK}.
     */
    DEFAULT,

    /**
     * The user's avatar.
     */
    USER,

    /**
     * The Gravatar logo.
     */
    GRAVATAR;

    /**
     * Returns the URL parameter for this image type.
     *
     * @param isFirstUrlParameter whether this is the first url parameter
     * @return the URL parameter for this image type
     */
    public String getAsUrlParameter(boolean isFirstUrlParameter) {
        String firstChar = isFirstUrlParameter ? "?" : "&";
        return firstChar + "type=" + this.toString().toLowerCase();
    }
}
