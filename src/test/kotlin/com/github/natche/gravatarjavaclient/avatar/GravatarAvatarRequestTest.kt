package com.github.natche.gravatarjavaclient.avatar

import com.github.natche.gravatarjavaclient.TestingImageUrls
import com.github.natche.gravatarjavaclient.enums.*
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.ImageIcon

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
        Assertions.assertThrows(NullPointerException::class.java)
        { GravatarAvatarRequest.fromEmail(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("    ") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("invalid.email") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromEmail("invalid.email@email") }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { GravatarAvatarRequest.fromEmail("valid.email@email.com") }
    }

    /**
     * Tests for creation from a hash.
     */
    @Test
    fun testCreationFromHash() {
        Assertions.assertThrows(NullPointerException::class.java)
        { GravatarAvatarRequest.fromHash(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromHash("") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GravatarAvatarRequest.fromHash("    ") }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { GravatarAvatarRequest.fromHash("some hash") }
    }

    /**
     * Tests for the accessors and mutators.
     */
    @Test
    fun testAccessorsAndMutators() {
        // Hash
        val request = GravatarAvatarRequest.fromEmail("valid.email@email.com")
        Assertions.assertEquals("1f60940c52efc3c031b29d6e87a01ed9", request.hash)

        // Default image URL
        Assertions.assertThrows(NullPointerException::class.java) { request.defaultImageUrl = null }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.defaultImageUrl = "" }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.defaultImageUrl = "   " }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.defaultImageUrl = "invalid.url" }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            request.defaultImageUrl = "https://not.valid.url.com"
        }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageUrl(TestingImageUrls.foreignImageUrl) }
        Assertions.assertEquals(TestingImageUrls.foreignImageUrl, request.defaultImageUrl)

        // Full URL parameters
        Assertions.assertThrows(NullPointerException::class.java)
        { request.useFullUrlParameters = null }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> {
            request.setUseFullUrlParameters(
                GravatarUseFullUrlParameters.False
            )
        }
        Assertions.assertEquals(GravatarUseFullUrlParameters.False, request.useFullUrlParameters)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> {
            request.setUseFullUrlParameters(
                GravatarUseFullUrlParameters.True
            )
        }
        Assertions.assertEquals(GravatarUseFullUrlParameters.True, request.useFullUrlParameters)

        // Protocol
        Assertions.assertThrows(NullPointerException::class.java)
        { request.protocol = null }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setProtocol(GravatarProtocol.HTTP) }
        Assertions.assertEquals(GravatarProtocol.HTTP, request.protocol)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setProtocol(GravatarProtocol.HTTPS) }
        Assertions.assertEquals(GravatarProtocol.HTTPS, request.protocol)

        // Default image type
        Assertions.assertThrows(NullPointerException::class.java) { request.defaultImageType = null }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType._404) }
        Assertions.assertEquals(GravatarDefaultImageType._404, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.BLANK) }
        Assertions.assertEquals(GravatarDefaultImageType.BLANK, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.IDENT_ICON) }
        Assertions.assertEquals(GravatarDefaultImageType.IDENT_ICON, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.MONSTER_ID) }
        Assertions.assertEquals(GravatarDefaultImageType.MONSTER_ID, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.MYSTERY_PERSON) }
        Assertions.assertEquals(GravatarDefaultImageType.MYSTERY_PERSON, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.RETRO) }
        Assertions.assertEquals(GravatarDefaultImageType.RETRO, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.ROBO_HASH) }
        Assertions.assertEquals(GravatarDefaultImageType.ROBO_HASH, request.defaultImageType)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setDefaultImageType(GravatarDefaultImageType.WAVATAR) }
        Assertions.assertEquals(GravatarDefaultImageType.WAVATAR, request.defaultImageType)

        // Force default image
        Assertions.assertThrows(NullPointerException::class.java) { request.setForceDefaultImage(null) }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setForceDefaultImage(GravatarForceDefaultImage.Force) }
        Assertions.assertEquals(GravatarForceDefaultImage.Force, request.shouldForceDefaultImage())
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setForceDefaultImage(GravatarForceDefaultImage.DoNotForce) }
        Assertions.assertEquals(GravatarForceDefaultImage.DoNotForce, request.shouldForceDefaultImage())

        // Rating
        Assertions.assertThrows(NullPointerException::class.java) { request.rating = null }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setRating(GravatarRating.G) }
        Assertions.assertEquals(GravatarRating.G, request.rating)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setRating(GravatarRating.PG) }
        Assertions.assertEquals(GravatarRating.PG, request.rating)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setRating(GravatarRating.R) }
        Assertions.assertEquals(GravatarRating.R, request.rating)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setRating(GravatarRating.X) }
        Assertions.assertEquals(GravatarRating.X, request.rating)

        // Append JPG suffix
        Assertions.assertThrows(NullPointerException::class.java) { request.shouldAppendJpgSuffix = null }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setShouldAppendJpgSuffix(GravatarUseJpgSuffix.True) }
        Assertions.assertEquals(GravatarUseJpgSuffix.True, request.shouldAppendJpgSuffix)
        Assertions.assertDoesNotThrow<GravatarAvatarRequest>
        { request.setShouldAppendJpgSuffix(GravatarUseJpgSuffix.False) }
        Assertions.assertEquals(GravatarUseJpgSuffix.False, request.shouldAppendJpgSuffix)

        // Size
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.size = -10 }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.size = 0 }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.size = 2049 }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.size = 2080 }
        Assertions.assertThrows(IllegalArgumentException::class.java) { request.size = 4000 }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setSize(1) }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setSize(200) }
        Assertions.assertDoesNotThrow<GravatarAvatarRequest> { request.setSize(2048) }
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
        Assertions.assertEquals(
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
        Assertions.assertEquals(
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
        Assertions.assertThrows(GravatarJavaClientException::class.java) { invalidDefaultUrl.requestUrl }
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
        Assertions.assertEquals(fromEmail.hashCode(), equalToFromEmail.hashCode())
        Assertions.assertNotEquals(fromEmail.hashCode(), fromHash.hashCode())
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
        Assertions.assertEquals(fromEmail, fromEmail)
        Assertions.assertEquals(fromEmail, equalToFromEmail)
        Assertions.assertNotEquals(fromEmail, fromHash)
        Assertions.assertNotEquals(fromEmail, Any())

        // Tests for Codecov to report 100%
        val hashOne = GravatarAvatarRequest.fromHash("one")
        val hashTwo = GravatarAvatarRequest.fromHash("two")
        Assertions.assertNotEquals(hashOne, hashTwo)
        val jpgSuffix = GravatarAvatarRequest.fromHash("one")
        val differentJpgSuffix = GravatarAvatarRequest.fromHash("one")
        jpgSuffix.shouldAppendJpgSuffix = GravatarUseJpgSuffix.True
        differentJpgSuffix.shouldAppendJpgSuffix = GravatarUseJpgSuffix.False
        Assertions.assertNotEquals(jpgSuffix, differentJpgSuffix)
        val size = GravatarAvatarRequest.fromHash("one")
        val differentSize = GravatarAvatarRequest.fromHash("one")
        size.size = 100
        differentSize.size = 101
        Assertions.assertNotEquals(size, differentSize)
        differentSize.size = 100
        Assertions.assertEquals(size, differentSize)
        val force = GravatarAvatarRequest.fromHash("one")
        val doNotForce = GravatarAvatarRequest.fromHash("one")
        force.setForceDefaultImage(GravatarForceDefaultImage.Force)
        doNotForce.setForceDefaultImage(GravatarForceDefaultImage.DoNotForce)
        Assertions.assertNotEquals(force, doNotForce)
        val roboHash = GravatarAvatarRequest.fromHash("one")
        val wavatar = GravatarAvatarRequest.fromHash("one")
        roboHash.defaultImageType = GravatarDefaultImageType.ROBO_HASH
        wavatar.defaultImageType = GravatarDefaultImageType.WAVATAR
        Assertions.assertNotEquals(roboHash, wavatar)
        val http = GravatarAvatarRequest.fromHash("one")
        val https = GravatarAvatarRequest.fromHash("one")
        http.protocol = GravatarProtocol.HTTP
        https.protocol = GravatarProtocol.HTTPS
        Assertions.assertNotEquals(http, https)
        val notFullParams = GravatarAvatarRequest.fromHash("one")
        val fullParams = GravatarAvatarRequest.fromHash("one")
        notFullParams.useFullUrlParameters = GravatarUseFullUrlParameters.False
        fullParams.useFullUrlParameters = GravatarUseFullUrlParameters.True
        Assertions.assertNotEquals(notFullParams, fullParams)
        val foreignImage = GravatarAvatarRequest.fromHash("one")
        val otherForeignImage = GravatarAvatarRequest.fromHash("one")
        foreignImage.defaultImageUrl = TestingImageUrls.foreignImageUrl
        otherForeignImage.defaultImageUrl = TestingImageUrls.anotherForeignImageUrl
        Assertions.assertNotEquals(foreignImage, otherForeignImage)
        val gRating = GravatarAvatarRequest.fromHash("one")
        val pgRating = GravatarAvatarRequest.fromHash("one")
        gRating.rating = GravatarRating.G
        pgRating.rating = GravatarRating.PG
        Assertions.assertNotEquals(gRating, pgRating)
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
        Assertions.assertEquals(
            "GravatarAvatarRequest{hash=\"80c44e7f3f5082023ede351d396844f5\", size=2000,"
                    + " rating=X, forceDefaultImage=Force, defaultImageType=null, protocol=HTTP,"
                    + " useFullUrlParameters=False,"
                    + " defaultImageUrl=\"https://picsum.photos/seed/gravatar-java-client/200/300\", }",
            fromEmail.toString()
        )
        Assertions.assertEquals(
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
        Assertions.assertDoesNotThrow<ImageIcon> { fromEmail.imageIcon }
        Assertions.assertNotNull(fromEmail.imageIcon)
        Assertions.assertDoesNotThrow<ImageIcon> { fromHash.imageIcon }
        Assertions.assertNotNull(fromHash.imageIcon)
        Mockito.doReturn("invalid://url").`when`(spiedRequest).requestUrl
        Assertions.assertThrows(GravatarJavaClientException::class.java) { spiedRequest.imageIcon }
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
        Assertions.assertDoesNotThrow<BufferedImage> { fromHash.bufferedImage }
        Assertions.assertNotNull(fromHash.bufferedImage)
        Mockito.doReturn("invalid://url").`when`(spiedRequest).requestUrl
        Assertions.assertThrows(GravatarJavaClientException::class.java) { spiedRequest.bufferedImage }
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
        Assertions.assertThrows(NullPointerException::class.java)
        { fromHash.saveTo(null, null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { fromHash.saveTo(File("."), "png") }
        Assertions.assertThrows(NullPointerException::class.java)
        { fromHash.saveTo(File("file.png"), null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { fromHash.saveTo(File("file.png"), "") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { fromHash.saveTo(File("file.png"), " ") }
        val saveToOutput = File("./save_to_output")
        saveToOutput.delete()
        Assertions.assertFalse(saveToOutput.exists())
        val created = saveToOutput.mkdir()
        Assertions.assertTrue(created)
        val saveFromHashTo = File("./save_to_output/FromHash.png")
        val saved = fromHash.saveTo(saveFromHashTo, "png")
        Assertions.assertTrue(saved)
        Assertions.assertTrue(saveFromHashTo.exists())
        try {
            saveFromHashTo.delete()
            saveToOutput.delete()
            Thread.sleep(500)
        } catch (e: Exception) {
            // Swallow possible thread exception
        }
        Assertions.assertFalse(saveToOutput.exists())
    }
}