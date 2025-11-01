package com.github.natche.gravatarjavaclient.enums;

import com.github.natche.gravatarjavaclient.avatar.GravatarAvatarRequest;

/**
 * Whether the ".jpg" suffix should be appended following the hash in a URL for a Gravatar Avatar request.
 *
 * Used by {@link GravatarAvatarRequest}s.
 */
public enum GravatarUseJpgSuffix {
    /**
     * The ".jpg" suffix will be appended following the hash in the URL.
     */
    True,

    /**
     * The ".jpg" suffix will not be appended following the hash in the URL.
     */
    False;

    /**
     * Returns the suffix, ".jpg" if {@link #True} else empty.
     *
     * @return the suffix, ".jpg" if {@link #True} else empty
     */
    public String getSuffix() {
        return this == True ? ".jpg" : "";
    }
}
