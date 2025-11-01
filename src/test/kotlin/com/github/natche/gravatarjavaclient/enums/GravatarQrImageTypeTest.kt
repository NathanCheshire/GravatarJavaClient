package com.github.natche.gravatarjavaclient.enums

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for [GravatarQrImageType]s.
 */
class GravatarQrImageTypeTest {
    /**
     * Tests for the get as url parameter method.
     */
    @Test
    fun testGetAsUrlParameter() {
        assertEquals("?type=gravatar", GravatarQrImageType.Gravatar.getAsUrlParameter(true))
        assertEquals("&type=gravatar", GravatarQrImageType.Gravatar.getAsUrlParameter(false))

        assertEquals("?type=user", GravatarQrImageType.User.getAsUrlParameter(true))
        assertEquals("&type=user", GravatarQrImageType.User.getAsUrlParameter(false))

        assertEquals("?type=default", GravatarQrImageType.Default.getAsUrlParameter(true))
        assertEquals("&type=default", GravatarQrImageType.Default.getAsUrlParameter(false))

        assertEquals("?type=blank", GravatarQrImageType.Blank.getAsUrlParameter(true))
        assertEquals("&type=blank", GravatarQrImageType.Blank.getAsUrlParameter(false))
    }
}