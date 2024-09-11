package com.github.natche.gravatarjavaclient.profile.gson

import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

/**
 * Tests for deserialization of [GravatarProfile]s.
 */
class GravatarProfileDeserializerTest {
    /**
     * Tests for deserialization of the is_organization field being not present,
     * present and false, and present and true.
     */
    @Test
    fun testDeserializationOfIsOrganization() {
        assertDoesNotThrow { GsonProvider.INSTANCE.get().fromJson(withoutOrganization, GravatarProfile::class.java) }
        assertDoesNotThrow { GsonProvider.INSTANCE.get().fromJson(withOrganizationFalse, GravatarProfile::class.java) }
        assertDoesNotThrow { GsonProvider.INSTANCE.get().fromJson(withOrganizationTrue, GravatarProfile::class.java) }
    }

    companion object {
        val withoutOrganization =
            """
                {
                    "hash": "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
                    "display_name": "Nathan Cheshire",
                    "profile_url": "https://gravatar.com/nathanvcheshire",
                    "avatar_url": "https://0.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7",
                    "avatar_alt_text": "West of Loathing character",
                    "location": "New Orleans",
                    "description": "I make the computer go beep boop and then people ask me to fix their printer.",
                    "job_title": "Lead software engineer",
                    "company": "Natche Group",
                    "verified_accounts": [
                        {
                            "service_type": "github",
                            "service_label": "GitHub",
                            "service_icon": "https://gravatar.com/icons/github.svg",
                            "url": "https://github.com/NathanCheshire"
                        }
                    ],
                    "pronunciation": "Guess",
                    "pronouns": "Guess"
                }
            """.trimIndent()

        val withOrganizationFalse =
            """
                {
                    "hash": "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
                    "display_name": "Nathan Cheshire",
                    "profile_url": "https://gravatar.com/nathanvcheshire",
                    "avatar_url": "https://0.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7",
                    "avatar_alt_text": "West of Loathing character",
                    "location": "New Orleans",
                    "description": "I make the computer go beep boop and then people ask me to fix their printer.",
                    "job_title": "Lead software engineer",
                    "company": "Natche Group",
                    "verified_accounts": [
                        {
                            "service_type": "github",
                            "service_label": "GitHub",
                            "service_icon": "https://gravatar.com/icons/github.svg",
                            "url": "https://github.com/NathanCheshire"
                        }
                    ],
                    "pronunciation": "Guess",
                    "pronouns": "Guess",
                    "is_organization": false
                }
            """.trimIndent()

        val withOrganizationTrue =
            """
                {
                    "hash": "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
                    "display_name": "Nathan Cheshire",
                    "profile_url": "https://gravatar.com/nathanvcheshire",
                    "avatar_url": "https://0.gravatar.com/avatar/2bf1b7a19bcad06a8e894d7373a4cfc7",
                    "avatar_alt_text": "West of Loathing character",
                    "location": "New Orleans",
                    "description": "I make the computer go beep boop and then people ask me to fix their printer.",
                    "job_title": "Lead software engineer",
                    "company": "Natche Group",
                    "verified_accounts": [
                        {
                            "service_type": "github",
                            "service_label": "GitHub",
                            "service_icon": "https://gravatar.com/icons/github.svg",
                            "url": "https://github.com/NathanCheshire"
                        }
                    ],
                    "pronunciation": "Guess",
                    "pronouns": "Guess",
                    "is_organization": true
                }
            """.trimIndent()
    }
}