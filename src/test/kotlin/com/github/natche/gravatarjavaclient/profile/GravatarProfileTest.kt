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

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val profileEmptyLists = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company", emptyList(),
            "test yoo-zer", "they/them", "America/New_York", emptyList(),
            "Test", "User", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )

        assertEquals("GravatarProfile{hash=\"hash123\", displayName=\"Test User\","
                + " profileUrl=\"https://gravatar.com/testuser\", avatarUrl=\"https://gravatar.com/avatar/hash123\","
                + " avatarAltText=\"Test User's avatar\", location=\"New York\","
                + " description=\"A test user\", jobTitle=\"Software Developer\", company=\"Test Company\","
                + " verifiedAccounts=[], pronunciation=\"test yoo-zer\", pronouns=\"they/them\","
                + " timezone=\"America/New_York\", languages=[], firstName=\"Test\", lastName=\"User\", "
                + "isOrganization=false, links=[], interests=[], payments=null, contactInfo=null, gallery=[],"
                + " numberVerifiedAccounts=0, lastProfileEdit=\"2024-09-11T19:46:13Z\","
                + " registrationDate=\"2023-09-11T19:46:13Z\"}", profileEmptyLists.toString())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val base = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val equal = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentHash = GravatarProfile(
            "hash1", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentDisplayName = GravatarProfile(
            "hash", "l", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentProfileUrl = GravatarProfile(
            "hash", "name", "https://gravatar.com/testusers",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentAvatarUrl = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash1234", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentAltText = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "altoids", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentLocation = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "Penn",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentDescription = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "description", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentJobTitle = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "job title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentCompany = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "companies", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentVerified = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", ImmutableList.of(
                GravatarProfileVerifiedAccount("", "", "", "")
            ),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentPronunciation = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pros", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentPronouns = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "me", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentTimezone = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "time", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentLanguages = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", ImmutableList.of(
                GravatarProfileLanguage("en", "English", false, 0)
            ),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentFirst = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "l", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentLast = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "f", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentOrganization = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", true, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentLink = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentInterests = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), ImmutableList.of(
                GravatarProfileInterest(0, "React")
            ),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentPayments = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            GravatarProfilePayments(ImmutableList.of(), ImmutableList.of()),
            null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentContactInfo = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, GravatarProfileContactInfo("", "", "",
                "", "", ""), emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentGallery = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, ImmutableList.of(
                GravatarProfileGalleryImage("url", "label")
            ), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentVerifiedAccounts = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 5,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentLastProfileEdit = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2023-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val differentRegistrationDate = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2024-09-11T19:46:13Z"
        )

        assertEquals(base, base)
        assertEquals(base, equal)
        assertNotEquals(base, Object())
        assertNotEquals(base, differentHash)
        assertNotEquals(base, differentDisplayName)
        assertNotEquals(base, differentProfileUrl)
        assertNotEquals(base, differentAvatarUrl)
        assertNotEquals(base, differentAltText)
        assertNotEquals(base, differentLocation)
        assertNotEquals(base, differentDescription)
        assertNotEquals(base, differentJobTitle)
        assertNotEquals(base, differentCompany)
        assertNotEquals(base, differentVerified)
        assertNotEquals(base, differentPronunciation)
        assertNotEquals(base, differentPronouns)
        assertNotEquals(base, differentTimezone)
        assertNotEquals(base, differentLanguages)
        assertNotEquals(base, differentFirst)
        assertNotEquals(base, differentLast)
        assertNotEquals(base, differentOrganization)
        assertNotEquals(base, differentLink)
        assertNotEquals(base, differentInterests)
        assertNotEquals(base, differentPayments)
        assertNotEquals(base, differentContactInfo)
        assertNotEquals(base, differentGallery)
        assertNotEquals(base, differentVerifiedAccounts)
        assertNotEquals(base, differentLastProfileEdit)
        assertNotEquals(base, differentRegistrationDate)
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val one = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company", emptyList(),
            "test yoo-zer", "they/them", "America/New_York", emptyList(),
            "Test", "User", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val equal = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company", emptyList(),
            "test yoo-zer", "they/them", "America/New_York", emptyList(),
            "Test", "User", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val notEqual = GravatarProfile(
            "hash123", "Test User", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "Test User's avatar", "New York",
            "A test user", "Software Developer", "Test Company", emptyList(),
            "test yoo-zer", "they/them", "America/New_York", emptyList(),
            "Test", "User", false, emptyList(), emptyList(),
            null, null, emptyList(), 20,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val filled = GravatarProfile(
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

        assertEquals(1145132939, one.hashCode())
        assertEquals(1145132939, equal.hashCode())
        assertEquals(1145152159, notEqual.hashCode())
        assertEquals(-1232376980, filled.hashCode())
        assertEquals(one.hashCode(), equal.hashCode())
        assertNotEquals(one.hashCode(), notEqual.hashCode())

        val notOrg = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", false, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        val org = GravatarProfile(
            "hash", "name", "https://gravatar.com/testuser",
            "https://gravatar.com/avatar/hash123", "alt", "New York",
            "desc", "title", "company", emptyList(),
            "pro", "pron", "tmz", emptyList(),
            "f", "l", true, emptyList(), emptyList(),
            null, null, emptyList(), 0,
            "2024-09-11T19:46:13Z", "2023-09-11T19:46:13Z"
        )
        assertNotEquals(org.hashCode(), notOrg.hashCode())
    }
}