package com.github.natche.gravatarjavaclient.profile;

/**
 * The Gravatar Profile request handler for requesting profile API requests.
 */
public enum GravatarProfileRequestHandler {
    /**
     * The Gravatar Profile request handler.
     */
    INSTANCE;

    // todo unauthenticated requests are limited to 100 an hour, authed are 1000 per hour

    public String buildUrl() {
        return "";
    }

    private String get(String bearerToken, String nameOrHash) {
        return "";
    }
}
