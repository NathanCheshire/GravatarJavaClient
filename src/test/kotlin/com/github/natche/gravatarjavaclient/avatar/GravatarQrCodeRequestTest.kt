package com.github.natche.gravatarjavaclient.avatar

import com.github.natche.gravatarjavaclient.ImagesForTests.Companion.isValidPng
import com.github.natche.gravatarjavaclient.enums.GravatarQrImageType
import com.github.natche.gravatarjavaclient.enums.GravatarQrImageVersion
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

/**
 * Tests for [GravatarQrCodeRequest]s.
 */
class GravatarQrCodeRequestTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java) { GravatarQrCodeRequest.fromEmail(null) }
        assertThrows(IllegalArgumentException::class.java) { GravatarQrCodeRequest.fromEmail("") }
        assertThrows(IllegalArgumentException::class.java) { GravatarQrCodeRequest.fromEmail("  ") }
        assertThrows(IllegalArgumentException::class.java)
        { GravatarQrCodeRequest.fromEmail("invalid.email@") }

        assertDoesNotThrow { GravatarQrCodeRequest.fromEmail("valid.email@email.com") }

        assertThrows(NullPointerException::class.java) { GravatarQrCodeRequest.fromHash(null) }
        assertThrows(IllegalArgumentException::class.java) { GravatarQrCodeRequest.fromHash("") }
        assertThrows(IllegalArgumentException::class.java) { GravatarQrCodeRequest.fromHash("  ") }

        assertDoesNotThrow { GravatarQrCodeRequest.fromHash("hash") }
    }

    /**
     * Tests for the accessor and mutator methods.
     */
    @Test
    fun testAccessorsAndMutators() {
        val request = GravatarQrCodeRequest.fromEmail("valid.email@email.com")
        assertEquals("4dfc17ce9e02fc266f013a59852803fbb47b65b07b0b53b439b99c73c110082", request.hash)

        assertThrows(IllegalArgumentException::class.java) { request.size = 0 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 1 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 79 }
        assertThrows(IllegalArgumentException::class.java) { request.size = 1025 }
        assertDoesNotThrow { request.size = 80 }
        assertEquals(80, request.size)
        assertDoesNotThrow { request.size = 100 }
        assertEquals(100, request.size)
        assertDoesNotThrow { request.size = 1024 }
        assertEquals(1024, request.size)

        assertThrows(NullPointerException::class.java) { request.version = null }
        assertDoesNotThrow { request.version = GravatarQrImageVersion.One }
        assertEquals(GravatarQrImageVersion.One, request.version)
        assertDoesNotThrow { request.version = GravatarQrImageVersion.Blank }
        assertEquals(GravatarQrImageVersion.Blank, request.version)
        assertDoesNotThrow { request.version = GravatarQrImageVersion.Three }
        assertEquals(GravatarQrImageVersion.Three, request.version)

        assertThrows(NullPointerException::class.java) { request.imageType = null }
        assertDoesNotThrow { request.imageType = GravatarQrImageType.Gravatar }
        assertEquals(GravatarQrImageType.Gravatar, request.imageType)
        assertDoesNotThrow { request.imageType = GravatarQrImageType.Blank }
        assertEquals(GravatarQrImageType.Blank, request.imageType)
        assertDoesNotThrow { request.imageType = GravatarQrImageType.Default }
        assertEquals(GravatarQrImageType.Default, request.imageType)
        assertDoesNotThrow { request.imageType = GravatarQrImageType.User }
        assertEquals(GravatarQrImageType.User, request.imageType)
    }

    /**
     * Tests for the get request URL method.
     */
    @Test
    fun testGetRequestUrl() {
        val default = GravatarQrCodeRequest.fromHash("hash")
        assertEquals("https://gravatar.com/hash.qr?type=default&version=blank&size=80", default.requestUrl)

        val fromEmail = GravatarQrCodeRequest.fromEmail("valid.email@email.com")
            .setSize(1000)
            .setImageType(GravatarQrImageType.Gravatar)
            .setVersion(GravatarQrImageVersion.Three)
        assertEquals(
            "https://gravatar.com/4dfc17ce9e02fc266f013a59852803fbb47b65b07b0b53b439b99c73c110082"
                    + ".qr?type=gravatar&version=3&size=1000", fromEmail.requestUrl
        )
    }

    /**
     * Tests for the get buffered image method.
     */
    @Test
    fun testGetBufferedImage() {
        assertDoesNotThrow {
            GravatarQrCodeRequest.fromHash(
                "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168"
            ).bufferedImage
        }

        assertThrows(GravatarJavaClientException::class.java)
        { GravatarQrCodeRequest.fromHash("hash").bufferedImage }
    }

    /**
     * Tests for the save to method.
     */
    @Test
    fun testSaveTo() {
        val request = GravatarQrCodeRequest.fromHash(
            "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168"
        )

        assertThrows(NullPointerException::class.java) { request.saveTo(null) }
        assertThrows(IllegalArgumentException::class.java) { request.saveTo(File(".")) }
        assertThrows(IllegalArgumentException::class.java) { request.saveTo(File("settings.gradle")) }

        val tempFolder = File("./temp_output")
        assertFalse(tempFolder.exists())
        tempFolder.mkdir()
        assertTrue(tempFolder.exists())

        val saveToFile = File(tempFolder, "output.png")
        assertTrue(request.saveTo(saveToFile))
        assertTrue(saveToFile.exists())
        assertTrue(isValidPng(saveToFile))

        assertTrue(saveToFile.delete())
        assertTrue(tempFolder.delete())
        assertFalse(tempFolder.exists())
    }

    /**
     * Tests for the hashcode method.
     */
    @Test
    fun testHashCode() {
        val one = GravatarQrCodeRequest.fromHash("hash")
        val equal = GravatarQrCodeRequest.fromHash("hash")
        val notEqual = GravatarQrCodeRequest.fromHash("other-hash")

        assertEquals(one.hashCode(), equal.hashCode())
        assertNotEquals(one.hashCode(), notEqual.hashCode())
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val one = GravatarQrCodeRequest.fromHash("hash")
            .setSize(800)
            .setVersion(GravatarQrImageVersion.Three)
        assertEquals(
            "GravatarQrCodeRequest{hash=\"hash\", size=800,"
                    + " imageType=Default, version=Three}", one.toString()
        )
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val one = GravatarQrCodeRequest.fromHash("hash")
        val equal = GravatarQrCodeRequest.fromHash("hash")
        val differentHash = GravatarQrCodeRequest.fromHash("other-hash")

        assertEquals(one, one)
        assertEquals(one, equal)
        assertNotEquals(one, Object())
        assertNotEquals(equal, differentHash)

        assertNotEquals(one, equal.setSize(100))

        equal.size = 80
        assertNotEquals(one, equal.setVersion(GravatarQrImageVersion.Three))

        equal.version = GravatarQrImageVersion.Blank
        assertNotEquals(one, equal.setImageType(GravatarQrImageType.Gravatar))
    }
}