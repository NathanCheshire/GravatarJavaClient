package com.github.natche.gravatarjavaclient.enums;

/**
 * Whether Gravatar Avatar requests should append ".jpg" to the hash.
 */
public enum GravatarUseJpgSuffix {
    True,
    False;

    /**
     * Returns the suffix, ".jpg" if {@link #True} else "".
     *
     * @return the suffix, ".jpg" if {@link #True} else ""
     */
    public String getSuffix() {
        if (this == True) return ".jpg";
        else return "";
    }
}