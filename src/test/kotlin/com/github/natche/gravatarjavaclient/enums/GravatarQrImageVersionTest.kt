package com.github.natche.gravatarjavaclient.enums

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Tests for [GravatarQrImageVersion]s.
 */
class GravatarQrImageVersionTest {
    /**
     * Tests for the get as url parameter method.
     */
    @Test
    fun testGetAsUrlParameter() {
        assertEquals("?version=blank", GravatarQrImageVersion.Blank.getAsUrlParameter(true))
        assertEquals("&version=blank", GravatarQrImageVersion.Blank.getAsUrlParameter(false))

        assertEquals("?version=1", GravatarQrImageVersion.One.getAsUrlParameter(true))
        assertEquals("&version=1", GravatarQrImageVersion.One.getAsUrlParameter(false))

        assertEquals("?version=3", GravatarQrImageVersion.Three.getAsUrlParameter(true))
        assertEquals("&version=3", GravatarQrImageVersion.Three.getAsUrlParameter(false))
    }
}