package com.github.natche.gravatarjavaclient.profile

import java.nio.charset.StandardCharsets

/**
 * A token supplier for grabbing the Profile API key from the system environment.
 */
class TokenSupplier private constructor() {
    /**
     * Suppress default constructor
     */
    init {
        throw AssertionError("Cannot create instances of TokenSupplier")
    }

    companion object {
        private const val KEY_NAME = "GRAVATAR_JAVA_CLIENT_GITHUB_API_KEY"

        /**
         * Returns the token supplier to grab the API key from the system environment.
         *
         * @return the token supplier to grab the API key from the system environment.
         */
        val tokenSupplier = GravatarProfileTokenProvider(
            { System.getenv(KEY_NAME).toByteArray(StandardCharsets.US_ASCII) }, "TokenSupplier class"
        )
    }
}