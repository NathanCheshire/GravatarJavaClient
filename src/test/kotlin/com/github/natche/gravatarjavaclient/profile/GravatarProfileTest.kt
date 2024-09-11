package com.github.natche.gravatarjavaclient.profile

import com.github.natche.gravatarjavaclient.profile.serialization.*
import com.google.common.collect.ImmutableList
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

    /**
     * Tests for the accessor methods.
     */
    @Test
    fun testAccessors() {
        val profileEmptyLists = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company", emptyList(),
            "test yoo-zer", "they/them", "America/New_York", emptyList(),
            "Test", "User", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )

        assertEquals("hash123", profileEmptyLists.hash)
        assertEquals("Test User", profileEmptyLists.displayName)
        assertEquals("https://gravatar.com/testuser", profileEmptyLists.profileUrl)
        assertEquals("https://gravatar.com/avatar/hash123", profileEmptyLists.avatarUrl)
        assertEquals("Test User's avatar", profileEmptyLists.avatarAltText)
        assertEquals("New York", profileEmptyLists.location)
        assertEquals("A test user", profileEmptyLists.description)
        assertEquals("Software Developer", profileEmptyLists.jobTitle)
        assertEquals("Test Company", profileEmptyLists.company)
        assertEquals(emptyList<GravatarProfileVerifiedAccount>(), profileEmptyLists.verifiedAccounts)
        assertEquals("test yoo-zer", profileEmptyLists.pronunciation)
        assertEquals("they/them", profileEmptyLists.pronouns)
        assertTrue(profileEmptyLists.timezone.isPresent)
        assertEquals("America/New_York", profileEmptyLists.timezone.get())
        assertEquals(emptyList<GravatarProfileLanguage>(), profileEmptyLists.languages)
        assertTrue(profileEmptyLists.firstName.isPresent)
        assertEquals("Test", profileEmptyLists.firstName.get())
        assertTrue(profileEmptyLists.lastName.isPresent)
        assertEquals("User", profileEmptyLists.lastName.get())
        assertEquals(false, profileEmptyLists.isOrganization)
        assertEquals(emptyList<GravatarProfileUrl>(), profileEmptyLists.links)
        assertEquals(emptyList<GravatarProfileInterest>(), profileEmptyLists.interests)
        assertTrue(profileEmptyLists.payments.isEmpty)
        assertTrue(profileEmptyLists.contactInfo.isEmpty)
        assertEquals(emptyList<GravatarProfileGalleryImage>(), profileEmptyLists.gallery)
        assertEquals(0, profileEmptyLists.numberVerifiedAccounts)

        val lastProfileEdit = profileEmptyLists.lastProfileEdit
        assertTrue(lastProfileEdit.isPresent)
        assertEquals(Instant.parse("2024-09-11T19:46:13Z"), profileEmptyLists.lastProfileEdit.get())
        val registrationDate = profileEmptyLists.registrationDate
        assertTrue(registrationDate.isPresent)
        assertEquals(Instant.parse("2023-09-11T19:46:13Z"), registrationDate.get())

        val profileFilledLists = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company",
            ImmutableList.of(
                GravatarProfileVerifiedAccount(
                    "type", "label", "icon", "url"
                )
            ),
            "test yoo-zer", "they/them", "America/New_York",
            ImmutableList.of(GravatarProfileLanguage("en", "English", false, 0)),
            "Test", "User", false,
            ImmutableList.of(GravatarProfileUrl("label", "url")),
            ImmutableList.of(GravatarProfileInterest(0, "React")),
            GravatarProfilePayments(
                ImmutableList.of(
                    GravatarProfileUrl("label", "url")
                ), ImmutableList.of(GravatarCryptoWalletAddress("label", "address"))
            ),
            GravatarProfileContactInfo(
                "homePhone", "workPhone",
                "cellPhone", "email", "contactForm", "calendar"
            ),
            ImmutableList.of(GravatarProfileGalleryImage("url", "alt")),
            0, "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )

        assertEquals(
            ImmutableList.of(
                GravatarProfileVerifiedAccount("type", "label", "icon", "url")
            ),
            profileFilledLists.verifiedAccounts
        )
        assertEquals("test yoo-zer", profileFilledLists.pronunciation)
        assertEquals("they/them", profileFilledLists.pronouns)
        assertTrue(profileFilledLists.timezone.isPresent)
        assertEquals("America/New_York", profileFilledLists.timezone.get())
        assertEquals(
            ImmutableList.of(
                GravatarProfileLanguage("en", "English", false, 0)
            ),
            profileFilledLists.languages
        )
        assertTrue(profileFilledLists.firstName.isPresent)
        assertEquals("Test", profileFilledLists.firstName.get())
        assertTrue(profileFilledLists.lastName.isPresent)
        assertEquals("User", profileFilledLists.lastName.get())
        assertEquals(false, profileFilledLists.isOrganization)
        assertEquals(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), profileFilledLists.links
        )
        assertEquals(
            ImmutableList.of(
                GravatarProfileInterest(0, "React")
            ), profileFilledLists.interests
        )
        assertTrue(profileFilledLists.payments.isPresent)
        assertEquals(
            GravatarProfilePayments(
                ImmutableList.of(
                    GravatarProfileUrl("label", "url")
                ), ImmutableList.of(GravatarCryptoWalletAddress("label", "address"))
            ), profileFilledLists.payments.get()
        )
        assertTrue(profileFilledLists.contactInfo.isPresent)
        assertEquals(
            GravatarProfileContactInfo(
                "homePhone", "workPhone",
                "cellPhone", "email", "contactForm", "calendar"
            ), profileFilledLists.contactInfo.get()
        )
        assertEquals(
            ImmutableList.of(
                GravatarProfileGalleryImage("url", "alt")
            ), profileFilledLists.gallery
        )
        assertEquals(0, profileFilledLists.numberVerifiedAccounts)
        assertTrue(profileFilledLists.registrationDate.isPresent)
        assertTrue(profileFilledLists.lastProfileEdit.isPresent)
        assertEquals(Instant.parse("2023-09-11T19:46:13Z"), profileFilledLists.registrationDate.get())
        assertEquals(Instant.parse("2024-09-11T19:46:13Z"), profileFilledLists.lastProfileEdit.get())
    }
}