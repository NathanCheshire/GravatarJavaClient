package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Tests for [GravatarProfileGalleryImage]s.
 */
class GravatarProfileGalleryImageTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java)
        { GravatarProfileGalleryImage(null, null) }
        assertThrows(NullPointerException::class.java)
        { GravatarProfileGalleryImage("url", null) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarProfileGalleryImage("", null) }
        assertDoesNotThrow { GravatarProfileGalleryImage("url", "") }
        assertDoesNotThrow { GravatarProfileGalleryImage("url", "alt-text") }
    }

    /**
     * Tests for the accessor methods.
     */
    @Test
    fun testAccessors() {
        val image = GravatarProfileGalleryImage("url", "alt-text")
        assertEquals("url", image.url)
        assertEquals("alt-text", image.altText)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val image = GravatarProfileGalleryImage("url", "alt-text")
        val differentImage = GravatarProfileGalleryImage("different url", "alt-text")

        assertEquals("GravatarProfileGalleryImage{url=\"url\", altText=\"alt-text\"}", image.toString())
        assertEquals(
            "GravatarProfileGalleryImage{url=\"different url\","
                    + " altText=\"alt-text\"}", differentImage.toString()
        )
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val image = GravatarProfileGalleryImage("url", "alt-text")
        val equalToImage = GravatarProfileGalleryImage("url", "alt-text")
        val differentAltText = GravatarProfileGalleryImage("url", "alt-text-different")
        val differentUrl = GravatarProfileGalleryImage("different url", "alt-text")

        assertEquals(image, image)
        assertEquals(image, equalToImage)
        assertNotEquals(image, differentUrl)
        assertNotEquals(image, differentAltText)
        assertNotEquals(image, Object())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val image = GravatarProfileGalleryImage("url", "alt-text")
        val equalToImage = GravatarProfileGalleryImage("url", "alt-text")
        val differentAltText = GravatarProfileGalleryImage("url", "alt-text-different")

        assertEquals(1984722754, image.hashCode())
        assertEquals(1984722754, equalToImage.hashCode())
        assertEquals(1215764654, differentAltText.hashCode())
        assertEquals(image.hashCode(), equalToImage.hashCode())
        assertNotEquals(image.hashCode(), differentAltText.hashCode())
    }
}