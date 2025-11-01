package com.github.natche.gravatarjavaclient.enums;

/**
 * Application layer protocols accepted by Gravatar for Avatar requests.
 */
public enum GravatarProtocol {
    /**
     * The hypertext transfer protocol.
     */
    Http("http"),

    /**
     * The hypertext transfer protocol secure.
     */
    Https("https");

    /**
     * The base URL for hitting the Gravatar Avatar API using this protocol.
     */
    private final String baseUrl;

    GravatarProtocol(String protocol) {
        this.baseUrl = protocol + "://www.gravatar.com/avatar/";
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
