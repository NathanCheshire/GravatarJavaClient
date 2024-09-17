package com.github.natche.gravatarjavaclient.enums;

/**
 * The QR code version of the image returned.
 * These are specific to the Gravatar API.
 */
public enum GravatarQrImageVersion {
    /**
     * A standard QR code.
     */
    BLANK("blank"),

    /**
     * The same as {@link #BLANK}.
     */
    ONE("1"),

    /**
     * Modern "small dots" QR code.
     */
    THREE("3");

    private final String urlParameter;

    GravatarQrImageVersion(String urlParameter) {
        this.urlParameter = urlParameter;
    }

    /**
     * Returns the URL parameter for this image version.
     *
     * @param isFirstUrlParameter whether this is the first url parameter
     * @return the URL parameter for this image version
     */
    public String getAsUrlParameter(boolean isFirstUrlParameter) {
        String firstChar = isFirstUrlParameter ? "?" : "&";
        return firstChar + "version=" + this.urlParameter;
    }
}
