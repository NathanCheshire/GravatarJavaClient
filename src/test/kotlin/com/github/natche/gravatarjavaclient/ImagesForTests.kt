package com.github.natche.gravatarjavaclient

/**
 * Images used for tests requiring valid foreign images.
 */
class ImagesForTests private constructor() {
    /**
     * Suppress default constructor.
     */
    init {
        throw AssertionError("Cannot create instances of TestingConstants")
    }

    companion object {
        const val foreignImageUrl = "https://picsum.photos/seed/gravatar-java-client/200/300"
        const val anotherForeignImageUrl = "https://picsum.photos/seed/gravatar-java-client/300/300"
    }
}