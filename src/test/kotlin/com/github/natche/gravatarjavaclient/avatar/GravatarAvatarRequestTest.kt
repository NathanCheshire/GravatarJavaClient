package com.github.natche.gravatarjavaclient.avatar

import com.github.natche.gravatarjavaclient.TestingImageUrls
import com.github.natche.gravatarjavaclient.enums.*
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.io.File

/**
 * Tests for [GravatarAvatarRequest]s.
 */
@ExtendWith(MockitoExtension::class)
class GravatarAvatarRequestTest
/**
 * Creates a new instance of this for testing purposes.
 */
internal constructor() {
    /**
     * A GravatarAvatarRequest, spied on by Mockito, used to ensure a [GravatarJavaClientException] is
     * thrown when the [GravatarAvatarRequest.getImageIcon] or [GravatarAvatarRequest.getBufferedImage]
     * fails to retrieve the image.
     */
    @Spy
    private val spiedRequest = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")

    /**
     * Tests for creation from an email address.
     */
    @Test
    fun testCreationFromEmail() {
        assertThrows(NullPointerException::class.java)
        { GravatarAvatarRequest.fromEmail(null) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("") }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("    ") }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("invalid.email") }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("invalid.email@email") }
        assertDoesNotThrow { GravatarAvatarRequest.fromEmail("valid.email@email.com") }
    }

    /**
     * Tests for creation from a hash.
     */
    @Test
    fun testCreationFromHash() {
        assertThrows(NullPointerException::class.java)
        { GravatarAvatarRequest.fromHash(null) }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromHash("") }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromHash("    ") }
        assertDoesNotThrow { GravatarAvatarRequest.fromHash("some hash") }
    }

    /**
     * Tests for the accessors and mutators.
     */
    @Test
    fun testAccessorsAndMutators() {
        // Hash
        val request = GravatarAvatarRequest.fromEmail("valid.email@email.com")
        assertEquals("1f60940c52efc3c031b29d6e87a01ed9", request.hash)

        // Default image URL
        assertThrows(NullPointerException::class.java) { request.defaultImageUrl = null }
        assertThrows(IllegalArgumentException::class.java) { request.defaultImageUrl = "" }
        assertThrows(IllegalArgumentException::class.java) { request.defaultImageUrl = "   " }
        assertThrows(IllegalArgumentException::class.java) { request.defaultImageUrl = "invalid.url" }
        assertThrows(IllegalArgumentException::class.java) {
            request.defaultImageUrl = "https://not.valid.url.com"
        }
        assertDoesNotThrow { request.defaultImageUrl = TestingImageUrls.foreignImageUrl }
        assertEquals(TestingImageUrls.foreignImageUrl, request.defaultImageUrl)

        // Full URL parameters
        assertThrows(NullPointerException::class.java)
        { request.useFullUrlParameters = null }
        assertDoesNotThrow {
            request.useFullUrlParameters = GravatarUseFullUrlParameters.False
        }
        assertEquals(GravatarUseFullUrlParameters.False, request.useFullUrlParameters)
        assertDoesNotThrow { request.useFullUrlParameters = GravatarUseFullUrlParameters.True }
        assertEquals(GravatarUseFullUrlParameters.True, request.useFullUrlParameters)

        // Protocol
        assertThrows(NullPointerException::class.java)
        { request.protocol = null }
        assertDoesNotThrow { request.protocol = GravatarProtocol.HTTP }
        assertEquals(GravatarProtocol.HTTP, request.protocol)
        assertDoesNotThrow { request.protocol = GravatarProtocol.HTTPS }
        assertEquals(GravatarProtocol.HTTPS, request.protocol)

        // Default image type
        assertThrows(NullPointerException::class.java) { request.defaultImageType = null }
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType._404 }
        assertEquals(GravatarDefaultImageType._404, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.BLANK }
        assertEquals(GravatarDefaultImageType.BLANK, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.IDENT_ICON }
        assertEquals(GravatarDefaultImageType.IDENT_ICON, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.MONSTER_ID }
        assertEquals(GravatarDefaultImageType.MONSTER_ID, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.MYSTERY_PERSON }
        assertEquals(GravatarDefaultImageType.MYSTERY_PERSON, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.RETRO }
        assertEquals(GravatarDefaultImageType.RETRO, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.ROBO_HASH }
        assertEquals(GravatarDefaultImageType.ROBO_HASH, request.defaultImageType)
        assertDoesNotThrow { request.defaultImageType = GravatarDefaultImageType.WAVATAR }
        assertEquals(GravatarDefaultImageType.WAVATAR, request.defaultImageType)

        // Force default image
        assertThrows(NullPointerException::class.java) { request.setForceDefaultImage(null) }
        assertDoesNotThrow { request.setForceDefaultImage(GravatarForceDefaultImage.Force) }
        assertEquals(GravatarForceDefaultImage.Force, request.shouldForceDefaultImage())
        assertDoesNotThrow { request.setForceDefaultImage(GravatarForceDefaultImage.DoNotForce) }
        assertEquals(GravatarForceDefaultImage.DoNotForce, request.shouldForceDefaultImage())

        // Rating
        assertThrows(NullPointerException::class.java) { request.rating = null }
        assertDoesNotThrow { request.rating = GravatarRating.G }
        assertEquals(GravatarRating.G, request.rating)
        assertDoesNotThrow { request.rating = GravatarRating.PG }
        assertEquals(GravatarRating.PG, request.rating)
        assertDoesNotThrow { request.rating = GravatarRating.R }
        assertEquals(GravatarRating.R, request.rating)
        assertDoesNotThrow { request.rating = GravatarRating.X }
        assertEquals(GravatarRating.X, request.rating)

        // Append JPG suffix
        assertThrows(NullPointerException::class.java) { request.shouldAppendJpgSuffix = null }
        assertDoesNotThrow { request.shouldAppendJpgSuffix = GravatarUseJpgSuffix.True }
        assertEquals(GravatarUseJpgSuffix.True, request.shouldAppendJpgSuffix)
        assertDoesNotThrow { request.shouldAppendJpgSuffix = GravatarUseJpgSuffix.False }
        assertEquals(GravatarUseJpgSuffix.False, request.shouldAppendJpgSuffix)

        // Size
        assertThrows(IllegalArgumentException::class.java) { request.size = -10 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 0 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 2049 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 2080 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 4000 }
        assertDoesNotThrow { request.size = 1 }
        assertDoesNotThrow { request.size = 200 }
        assertDoesNotThrow { request.size = 2048 }
    }

    /**
     * Tests for the get request URL method.
     */
    @Test
    fun testGetRequestUrl() {
        val fromEmailUrl = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
            .requestUrl
        assertEquals(
            "http://www.gravatar.com/avatar/80c44e7f3f5082023ede351d396844f5.jpg"
                    + "?s=2000&r=x&d=https://picsum.photos/seed/gravatar-java-client/200/300&f=y", fromEmailUrl
        )
        val fromHashUrl = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
            .requestUrl
        assertEquals(
            "https://www.gravatar.com/avatar/80c44e7f3f5082023ede351d396844f5"
                    + "?size=1776&rating=r&default=wavatar", fromHashUrl
        )
        val invalidDefaultUrl = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        assertThrows(GravatarJavaClientException::class.java) { invalidDefaultUrl.requestUrl }
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        val equalToFromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        val fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
        assertEquals(fromEmail.hashCode(), equalToFromEmail.hashCode())
        assertNotEquals(fromEmail.hashCode(), fromHash.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        val equalToFromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        val fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
        assertEquals(fromEmail, fromEmail)
        assertEquals(fromEmail, equalToFromEmail)
        assertNotEquals(fromEmail, fromHash)
        assertNotEquals(fromEmail, Any())

        // Tests for Codecov to report 100%
        val hashOne = GravatarAvatarRequest.fromHash("one")
        val hashTwo = GravatarAvatarRequest.fromHash("two")
        assertNotEquals(hashOne, hashTwo)
        val jpgSuffix = GravatarAvatarRequest.fromHash("one")
        val differentJpgSuffix = GravatarAvatarRequest.fromHash("one")
        jpgSuffix.shouldAppendJpgSuffix = GravatarUseJpgSuffix.True
        differentJpgSuffix.shouldAppendJpgSuffix = GravatarUseJpgSuffix.False
        assertNotEquals(jpgSuffix, differentJpgSuffix)
        val size = GravatarAvatarRequest.fromHash("one")
        val differentSize = GravatarAvatarRequest.fromHash("one")
        size.size = 100
        differentSize.size = 101
        assertNotEquals(size, differentSize)
        differentSize.size = 100
        assertEquals(size, differentSize)
        val force = GravatarAvatarRequest.fromHash("one")
        val doNotForce = GravatarAvatarRequest.fromHash("one")
        force.setForceDefaultImage(GravatarForceDefaultImage.Force)
        doNotForce.setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
        assertNotEquals(force, doNotForce)
        val roboHash = GravatarAvatarRequest.fromHash("one")
        val wavatar = GravatarAvatarRequest.fromHash("one")
        roboHash.defaultImageType = GravatarDefaultImageType.ROBO_HASH
        wavatar.defaultImageType = GravatarDefaultImageType.WAVATAR
        assertNotEquals(roboHash, wavatar)
        val http = GravatarAvatarRequest.fromHash("one")
        val https = GravatarAvatarRequest.fromHash("one")
        http.protocol = GravatarProtocol.HTTP
        https.protocol = GravatarProtocol.HTTPS
        assertNotEquals(http, https)
        val notFullParams = GravatarAvatarRequest.fromHash("one")
        val fullParams = GravatarAvatarRequest.fromHash("one")
        notFullParams.useFullUrlParameters = GravatarUseFullUrlParameters.False
        fullParams.useFullUrlParameters = GravatarUseFullUrlParameters.True
        assertNotEquals(notFullParams, fullParams)
        val foreignImage = GravatarAvatarRequest.fromHash("one")
        val otherForeignImage = GravatarAvatarRequest.fromHash("one")
        foreignImage.defaultImageUrl = TestingImageUrls.foreignImageUrl
        otherForeignImage.defaultImageUrl = TestingImageUrls.anotherForeignImageUrl
        assertNotEquals(foreignImage, otherForeignImage)
        val gRating = GravatarAvatarRequest.fromHash("one")
        val pgRating = GravatarAvatarRequest.fromHash("one")
        gRating.rating = GravatarRating.G
        pgRating.rating = GravatarRating.PG
        assertNotEquals(gRating, pgRating)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        val fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
        assertEquals(
            "GravatarAvatarRequest{hash=\"80c44e7f3f5082023ede351d396844f5\", size=2000,"
                    + " rating=X, forceDefaultImage=Force, defaultImageType=null, protocol=HTTP,"
                    + " useFullUrlParameters=False,"
                    + " defaultImageUrl=\"https://picsum.photos/seed/gravatar-java-client/200/300\", }",
            fromEmail.toString()
        )
        assertEquals(
            "GravatarAvatarRequest{hash=\"80c44e7f3f5082023ede351d396844f5\", size=1776,"
                    + " rating=R, forceDefaultImage=DoNotForce, defaultImageType=WAVATAR, protocol=HTTPS,"
                    + " useFullUrlParameters=True, defaultImageUrl=\"null\", }", fromHash.toString()
        )
    }

    /**
     * Tests for the get image icon method.
     */
    @Test
    fun testGetImageIcon() {
        val fromEmail = GravatarAvatarRequest.fromEmail("my.email@email.com")
            .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
            .setDefaultImageUrl(TestingImageUrls.foreignImageUrl)
            .setForceDefaultImage(GravatarForceDefaultImage.Force)
            .setRating(GravatarRating.X)
            .setSize(2000)
            .setProtocol(GravatarProtocol.HTTP)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.False)
        val fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
        assertDoesNotThrow { fromEmail.imageIcon }
        assertNotNull(fromEmail.imageIcon)
        assertDoesNotThrow { fromHash.imageIcon }
        assertNotNull(fromHash.imageIcon)
        Mockito.doReturn("invalid://url").`when`(spiedRequest).requestUrl
        assertThrows(GravatarJavaClientException::class.java) { spiedRequest.imageIcon }
    }

    /**
     * Tests for the get buffered image method.
     */
    @Test
    fun testGetBufferedImage() {
        val fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
        assertDoesNotThrow { fromHash.bufferedImage }
        assertNotNull(fromHash.bufferedImage)
        Mockito.doReturn("invalid://url").`when`(spiedRequest).requestUrl
        assertThrows(GravatarJavaClientException::class.java) { spiedRequest.bufferedImage }
    }

    /**
     * Tests for the save to method.
     */
    @Test
    fun testSaveTo() {
        val fromHash = GravatarAvatarRequest.fromHash("80c44e7f3f5082023ede351d396844f5")
            .setDefaultImageType(GravatarDefaultImageType.WAVATAR)
            .setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
            .setRating(GravatarRating.R)
            .setSize(1776)
            .setProtocol(GravatarProtocol.HTTPS)
            .setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False)
            .setUseFullUrlParameters(GravatarUseFullUrlParameters.True)
        assertThrows(NullPointerException::class.java)
        { fromHash.saveTo(null, null) }
        assertThrows(IllegalArgumentException::class.java)
        { fromHash.saveTo(File("."), "png") }
        assertThrows(NullPointerException::class.java)
        { fromHash.saveTo(File("file.png"), null) }
        assertThrows(IllegalArgumentException::class.java)
        { fromHash.saveTo(File("file.png"), "") }
        assertThrows(IllegalArgumentException::class.java)
        { fromHash.saveTo(File("file.png"), " ") }
        val saveToOutput = File("./save_to_output")
        saveToOutput.delete()
        assertFalse(saveToOutput.exists())
        val created = saveToOutput.mkdir()
        assertTrue(created)
        val saveFromHashTo = File("./save_to_output/FromHash.png")
        val saved = fromHash.saveTo(saveFromHashTo, "png")
        assertTrue(saved)
        assertTrue(saveFromHashTo.exists())
        try {
            saveFromHashTo.delete()
            saveToOutput.delete()
            Thread.sleep(500)
        } catch (e: Exception) {
            // Swallow possible thread exception
        }
        assertFalse(saveToOutput.exists())
    }
}