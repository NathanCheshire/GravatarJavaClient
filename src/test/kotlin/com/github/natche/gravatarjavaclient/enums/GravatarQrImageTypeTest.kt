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
        assertEquals("?type=gravatar", GravatarQrImageType.GRAVATAR.getAsUrlParameter(true))
        assertEquals("&type=gravatar", GravatarQrImageType.GRAVATAR.getAsUrlParameter(false))

        assertEquals("?type=user", GravatarQrImageType.USER.getAsUrlParameter(true))
        assertEquals("&type=user", GravatarQrImageType.USER.getAsUrlParameter(false))

        assertEquals("?type=default", GravatarQrImageType.DEFAULT.getAsUrlParameter(true))
        assertEquals("&type=default", GravatarQrImageType.DEFAULT.getAsUrlParameter(false))

        assertEquals("?type=blank", GravatarQrImageType.BLANK.getAsUrlParameter(true))
        assertEquals("&type=blank", GravatarQrImageType.BLANK.getAsUrlParameter(false))
    }
}