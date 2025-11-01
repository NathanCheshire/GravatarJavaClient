package com.github.natche.gravatarjavaclient.enums;

/**
 * The QR code version of the image returned.
 * These are specific to the Gravatar API.
 */
public enum GravatarQrImageVersion {
    /**
     * A standard QR code.
     */
    Blank("blank"),

    /**
     * The same as {@link #Blank}.
     */
    One("1"),

    /**
     * Modern "small dots" QR code.
     */
    Three("3");

    private final String urlParameter;

    GravatarQrImageVersion(String urlParameter) {
        this.urlParameter = urlParameter;
    }

    /**
     * Returns the URL parameter for this image version.
     *
     * @param isFirst whether this is the first url parameter
     * @return the URL parameter for this image version
     */
    public String getAsUrlParameter(boolean isFirst) {
        String firstChar = isFirst ? "?" : "&";
        return firstChar + "version=" + this.urlParameter;
    }
}
