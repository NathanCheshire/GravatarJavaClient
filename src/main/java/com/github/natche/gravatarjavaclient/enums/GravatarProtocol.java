package com.github.natche.gravatarjavaclient.enums;

/**
 * Application layer protocols accepted by Gravatar for Avatar requests.
 */
public enum GravatarProtocol {
    /**
     * The hypertext transfer protocol.
     */
    HTTP("http://www.gravatar.com/avatar/"),

    /**
     * The hypertext transfer protocol secure.
     */
    HTTPS("https://www.gravatar.com/avatar/");

    private final String baseUrl;

    GravatarProtocol(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Returns the base URL using this protocol to request an Avatar from Gravatar.
     *
     * @return the base URL using this protocol to request an Avatar from Gravatar
     */
    public String getAvatarRequestBaseurl() {
        return this.baseUrl;
    }
}
