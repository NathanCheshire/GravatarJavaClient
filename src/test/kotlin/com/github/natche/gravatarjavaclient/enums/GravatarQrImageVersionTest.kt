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
        assertEquals("?version=blank", GravatarQrImageVersion.BLANK.getAsUrlParameter(true))
        assertEquals("&version=blank", GravatarQrImageVersion.BLANK.getAsUrlParameter(false))

        assertEquals("?version=1", GravatarQrImageVersion.ONE.getAsUrlParameter(true))
        assertEquals("&version=1", GravatarQrImageVersion.ONE.getAsUrlParameter(false))

        assertEquals("?version=3", GravatarQrImageVersion.THREE.getAsUrlParameter(true))
        assertEquals("&version=3", GravatarQrImageVersion.THREE.getAsUrlParameter(false))
    }
}