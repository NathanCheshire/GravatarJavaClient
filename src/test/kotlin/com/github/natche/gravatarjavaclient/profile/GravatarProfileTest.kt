package com.github.natche.gravatarjavaclient.profile

import com.github.natche.gravatarjavaclient.profile.serialization.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

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
            GravatarProfile(
                null, "Test User", "https://gravatar.com/testuser",
                null, null, null, null, null,
                null, null,
                null, null, null, null, null,
                null, false,
                null, null, null, null, null,
                0, null, null
            )
        }

        assertThrows(NullPointerException::class.java) {
            GravatarProfile(
                "hash123", "Test User", null,
                null, null, null, null, null,
                null, null,
                null, null, null, null, null,
                null, false,
                null, null, null, null, null,
                0, null, null
            )
        }

        assertDoesNotThrow {
            GravatarProfile(
                "hash123", "Test User", "https://gravatar.com/testuser",
                null, null, null, null, null,
                null, null,
                null, null, null, null, null,
                null, false,
                null, null, null, null, null,
                0, null, null
            )
        }
    }

    @Test
    fun testAccessors() {
        val profile = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company", emptyList(),
            "test yoo-zer", "they/them", "America/New_York", emptyList(),
            "Test", "User", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )

        assertEquals("hash123", profile.hash)
        assertEquals("Test User", profile.displayName)
        assertEquals("https://gravatar.com/testuser", profile.profileUrl)
        assertEquals("https://gravatar.com/avatar/hash123", profile.avatarUrl)
        assertEquals("Test User's avatar", profile.avatarAltText)
        assertEquals("New York", profile.location)
        assertEquals("A test user", profile.description)
        assertEquals("Software Developer", profile.jobTitle)
        assertEquals("Test Company", profile.company)
        assertEquals(emptyList<GravatarProfileVerifiedAccount>(), profile.verifiedAccounts)
        assertEquals("test yoo-zer", profile.pronunciation)
        assertEquals("they/them", profile.pronouns)
        assertTrue(profile.timezone.isPresent)
        assertEquals("America/New_York", profile.timezone.get())
        assertEquals(emptyList<GravatarProfileLanguage>(), profile.languages)
        assertTrue(profile.firstName.isPresent)
        assertEquals("Test", profile.firstName.get())
        assertTrue(profile.lastName.isPresent)
        assertEquals("User", profile.lastName.get())
        assertEquals(false, profile.isOrganization)
        assertEquals(emptyList<GravatarProfileUrl>(), profile.links)
        assertEquals(emptyList<GravatarProfileInterest>(), profile.interests)
        assertTrue(profile.payments.isEmpty)
        assertTrue(profile.contactInfo.isEmpty)
        assertEquals(emptyList<GravatarProfileGalleryImage>(), profile.gallery)
        assertEquals(0, profile.numberVerifiedAccounts)

        val lastProfileEdit = profile.lastProfileEdit
        assertTrue(lastProfileEdit.isPresent)
        assertEquals(Instant.parse("2024-09-11T19:46:13Z"), profile.lastProfileEdit.get())
        val registrationDate = profile.registrationDate
        assertTrue(registrationDate.isPresent)
        assertEquals(Instant.parse("2023-09-11T19:46:13Z"), registrationDate.get())
    }
}