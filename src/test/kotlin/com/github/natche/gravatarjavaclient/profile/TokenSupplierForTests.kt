package com.github.natche.gravatarjavaclient.profile

import java.nio.charset.StandardCharsets

/**
 * A token supplier for providing tests with an instance of a [GravatarProfileTokenProvider].
 */
class TokenSupplierForTests private constructor() {
    /**
     * Suppress default constructor
     */
    init {
        throw AssertionError("Cannot create instances of TokenSupplierForTests")
    }

    companion object {
        /**
         * The name of the environment variable containing the Gravatar API key for testing.
         */
        private const val GRAVATAR_API_KEY_ENV_VAR = "GRAVATAR_JAVA_CLIENT_API_KEY"

        /**
         * Returns the token supplier to grab the API key from the system environment.
         *
         * @return the token supplier to grab the API key from the system environment.
         */
        val tokenSupplier = GravatarProfileTokenProvider(
            { System.getenv(GRAVATAR_API_KEY_ENV_VAR).toByteArray(StandardCharsets.US_ASCII) }, "TokenSupplierForTests tokenSupplier"
        )
    }
}