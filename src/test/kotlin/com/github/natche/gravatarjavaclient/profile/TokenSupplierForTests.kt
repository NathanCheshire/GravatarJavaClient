package com.github.natche.gravatarjavaclient.profile

/**
 * A token supplier for providing tests with an Gravatar API token..
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
         * The Gravatar API key used for testing.
         */
        val TOKEN: String = System.getenv(GRAVATAR_API_KEY_ENV_VAR)
    }
}