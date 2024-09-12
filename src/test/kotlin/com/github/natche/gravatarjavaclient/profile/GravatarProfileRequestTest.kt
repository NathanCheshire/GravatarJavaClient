package com.github.natche.gravatarjavaclient.profile

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * Tests for [GravatarProfileRequest]s.
 */
class GravatarProfileRequestTest
/**
 * Constructs a new instance for testing purposes.
 */
internal constructor() {
    /**
     * Tests for the "from hash or ID" method.
     */
    @Test
    fun fromHashOrId() {
        assertThrows(NullPointerException::class.java) { GravatarProfileRequest.fromHashOrId(null) }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileRequest.fromHashOrId("") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileRequest.fromHashOrId("   ") }
        assertDoesNotThrow { GravatarProfileRequest.fromHashOrId("hash") }
        assertDoesNotThrow { GravatarProfileRequest.fromHashOrId("nathanvcheshire") }
    }

    /**
     * Tests for the "from email" method.
     */
    @Test
    fun testFromEmail() {
        assertThrows(NullPointerException::class.java) { GravatarProfileRequest.fromEmail(null) }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileRequest.fromEmail("") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileRequest.fromEmail("   ") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileRequest.fromEmail("email.address") }
        assertThrows(IllegalArgumentException::class.java) { GravatarProfileRequest.fromEmail("email.address@gmail") }
        assertDoesNotThrow { GravatarProfileRequest.fromEmail("email.address@gmail.com") }
    }

    /**
     * Tests for the set token supplier method.
     */
    @Test
    fun testSetTokenSupplier() {
        assertThrows(
            NullPointerException::class.java
        ) { GravatarProfileRequest.fromHashOrId("hash").setTokenSupplier(null) }
        assertDoesNotThrow {
            GravatarProfileRequest.fromHashOrId("hash").setTokenSupplier(TokenSupplier.tokenSupplier)
        }
    }

    /**
     * Tests for the get hash or ID method.
     */
    @Test
    fun testGetHashOrId() {
        val request = GravatarProfileRequest.fromHashOrId("hash")
        assertDoesNotThrow { request.hashOrId }
        assertEquals("hash", request.hashOrId)
        assertEquals(
            "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
            GravatarProfileRequest.fromEmail("nathan.vincent.2.718@gmail.com").hashOrId
        )
    }

    /**
     * Tests for the get profile method.
     */
    @Test
    fun testGetProfile() {
        val startingAuthSize = GravatarProfileRequestHandler.INSTANCE.authenticatedRequestResults.size
        val startingUnAuthSize = GravatarProfileRequestHandler.INSTANCE.unauthenticatedRequestResults.size

        val authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        val unauthenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
        assertDoesNotThrow { authenticatedRequest.profile }
        assertDoesNotThrow { unauthenticatedRequest.profile }
        val authenticatedProfile = authenticatedRequest.profile
        val unauthenticatedProfile = unauthenticatedRequest.profile
        assertFalse(authenticatedProfile.links.isEmpty())
        assertTrue(authenticatedProfile.lastProfileEdit.isPresent)
        assertTrue(authenticatedProfile.registrationDate.isPresent)
        assertTrue(unauthenticatedProfile.links.isEmpty())
        assertTrue(unauthenticatedProfile.lastProfileEdit.isEmpty)
        assertTrue(unauthenticatedProfile.registrationDate.isEmpty)
        assertEquals(
            startingAuthSize + 2,
            GravatarProfileRequestHandler.INSTANCE.authenticatedRequestResults.size
        )
        assertEquals(
            startingUnAuthSize + 2,
            GravatarProfileRequestHandler.INSTANCE.unauthenticatedRequestResults.size
        )
    }

    /**
     * Tests for the write to file method.
     */
    @Test
    fun testWriteToFile() {
        val outputDirectory = File("./save_to_output")
        outputDirectory.mkdir()

        val authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        assertThrows(NullPointerException::class.java) { authenticatedRequest.writeToFile(null, null) }
        assertThrows(NullPointerException::class.java) { authenticatedRequest.writeToFile(null) }
        assertThrows(NullPointerException::class.java) { authenticatedRequest.writeToFile(Gson(), null) }
        assertThrows(
            IllegalArgumentException::class.java
        ) { authenticatedRequest.writeToFile(Gson(), File(".")) }
        assertThrows(
            IllegalArgumentException::class.java
        ) { authenticatedRequest.writeToFile(Gson(), File("non_existent_file.json")) }
        assertThrows(
            IllegalArgumentException::class.java
        ) { authenticatedRequest.writeToFile(Gson(), File("bad_file_name<>.json")) }

        val tempFile = File(outputDirectory, "test.json")
        tempFile.createNewFile()
        Thread.sleep(100)
        assertTrue(authenticatedRequest.writeToFile(tempFile))
        assertTrue(tempFile.exists())
        assertTrue(tempFile.length() > 0)

        val mockFile = Mockito.mock(File::class.java)
        Mockito.`when`(mockFile.isDirectory).thenReturn(false)
        Mockito.`when`(mockFile.absolutePath).thenReturn(tempFile.absolutePath)
        Mockito.`when`(mockFile.exists()).thenReturn(true)
        Mockito.`when`(mockFile.name).thenReturn("name.json")
        Mockito.doThrow(IOException("Test exception")).`when`(mockFile).canonicalFile

        assertThrows(GravatarJavaClientException::class.java) { authenticatedRequest.writeToFile(mockFile) }
        val customSerializer = Gson()
        assertTrue(authenticatedRequest.writeToFile(customSerializer, tempFile))
        assertTrue(tempFile.exists())
        assertTrue(tempFile.length() > 0)
        authenticatedRequest.writeToFile(tempFile)
        val fileBytes = Files.readAllBytes(tempFile.toPath())
        assertNotNull(fileBytes)
        assertTrue(String(fileBytes).contains("\"registration_date\":")) // Authenticated field
        tempFile.delete()
        Thread.sleep(50)
        outputDirectory.delete()
        Thread.sleep(50)
        assertFalse(tempFile.exists())
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        val unauthenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
        assertEquals(
            "GravatarProfileRequest{hash=\"nathanvcheshire\","
                    + " tokenSupplier=GravatarProfileTokenProvider{source=\"TokenSupplier class\"}}",
            authenticatedRequest.toString()
        )
        assertEquals(
            "GravatarProfileRequest{hash=\"nathanvcheshire\", tokenSupplier=null}",
            unauthenticatedRequest.toString()
        )
    }

    /**
     * Tests for the hashcode method.
     */
    @Test
    fun testHashCode() {
        val authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        val equal = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        val unauthenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
        assertEquals(equal.hashCode(), authenticatedRequest.hashCode())
        assertNotEquals(authenticatedRequest.hashCode(), unauthenticatedRequest.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val authenticatedRequest = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        val equal = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
            .setTokenSupplier(TokenSupplier.tokenSupplier)
        val notEqual = GravatarProfileRequest.fromHashOrId("nathanvcheshire")
        val notEqualToNotEqual = GravatarProfileRequest.fromHashOrId("NathanCheshire")
        assertEquals(authenticatedRequest, authenticatedRequest)
        assertEquals(authenticatedRequest, equal)
        assertNotEquals(authenticatedRequest, notEqual)
        assertNotEquals(authenticatedRequest, Any())
        assertNotEquals(notEqual, notEqualToNotEqual)
    }
}