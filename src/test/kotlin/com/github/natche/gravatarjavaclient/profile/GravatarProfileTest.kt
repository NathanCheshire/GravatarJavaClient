package com.github.natche.gravatarjavaclient.profile

import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Tests for [GravatarProfile]s.
 */
class GravatarProfileTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java) {
            GravatarProfile()
        }
    }
}