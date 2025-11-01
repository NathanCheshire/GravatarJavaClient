package com.github.natche.gravatarjavaclient.enums;

/**
 * The types of QR images returned by the Gravatar API.
 * Types, within the scope of Gravatar, are indicative of the image in the center of the QR code.
 */
public enum GravatarQrImageType {
    /**
     * No logo or avatar in the center.
     */
    Blank,

    /**
     * Same as {@link #Blank}.
     */
    Default,

    /**
     * The user's avatar in the center.
     */
    User,

    /**
     * The Gravatar logo in the center.
     */
    Gravatar;

    /**
     * Returns the URL parameter for this image type.
     *
     * @param isFirst whether this is the first url parameter
     * @return the URL parameter for this image type
     */
    public String getAsUrlParameter(boolean isFirst) {
        // TODO: consolidate ? and & to some kind of URL encoding enum or something
        String firstChar = isFirst ? "?" : "&";
        return firstChar + "type=" + this.toString().toLowerCase();
    }
}
