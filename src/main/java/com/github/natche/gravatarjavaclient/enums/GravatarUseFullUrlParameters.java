package com.github.natche.gravatarjavaclient.enums;

/**
 * Whether to use full or short URL parameter names for an Avatar request.
 */
public enum GravatarUseFullUrlParameters {
    /**
     * Full URL parameters will be used when constructing the URL.
     */
    True,

    /**
     * Full URL parameters will not be used when constructing the URL.
     */
    False;

    /**
     * Returns whether full URL parameters will not be used when constructing the URL.
     *
     * @return whether full URL parameters will not be used when constructing the URL
     */
    boolean shouldUseFullParams() {
        return this == True;
    }
}
