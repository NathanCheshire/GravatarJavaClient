package com.github.natche.gravatarjavaclient.profile.serialization

import com.google.common.collect.ImmutableList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Tests for [GravatarProfilePayments].
 */
class GravatarProfilePaymentsTest {
    /**
     * Tests for construction.
     */
    @Test
    fun testConstruction() {
        assertThrows(NullPointerException::class.java) {
            GravatarProfilePayments(null, null)
        }
        assertThrows(NullPointerException::class.java) {
            GravatarProfilePayments(ImmutableList.of(), null)
        }

        assertDoesNotThrow {
            GravatarProfilePayments(ImmutableList.of(), ImmutableList.of())
        }

        assertDoesNotThrow {
            GravatarProfilePayments(
                ImmutableList.of(
                    GravatarProfileUrl("label", "url")
                ), ImmutableList.of(
                    GravatarCryptoWalletAddress("label", "address")
                )
            )
        }
    }

    /**
     * Tests for the accessor methods.
     */
    @Test
    fun testAccessors() {
        val payments = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )

        assertEquals(
            payments.links,
            ImmutableList.of(GravatarProfileUrl("label", "url"))
        )
        assertEquals(
            payments.cryptoWallets,
            ImmutableList.of(GravatarCryptoWalletAddress("label", "address"))
        )
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val payments = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )
        val otherPayment = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )

        assertEquals(
            "GravatarProfilePayments{links=[GravatarProfileUrl{label=\"label\", url=\"url\"}],"
                    + " cryptoWallets=[CryptoWalletAddress{label=\"label\", address=\"address\"}]}",
            payments.toString()
        )
        assertEquals(
            "GravatarProfilePayments{links=[GravatarProfileUrl{label=\"label\", url=\"url\"}],"
                    + " cryptoWallets=[CryptoWalletAddress{label=\"label\", address=\"address\"}]}",
            otherPayment.toString()
        )
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val payments = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )
        val equal = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )
        val differentLink = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("labeler", "urls")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )
        val differentCrypto = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("labeler", "other address")
            )
        )

        assertEquals(payments, payments)
        assertEquals(payments, equal)
        assertNotEquals(payments, differentLink)
        assertNotEquals(payments, differentCrypto)
        assertNotEquals(payments, Object())
    }

    /**
     * Tests for the hash code.
     */
    @Test
    fun testHashCode() {
        val payments = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )
        val equalToPayments = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("label", "address")
            )
        )
        val otherPayment = GravatarProfilePayments(
            ImmutableList.of(
                GravatarProfileUrl("label", "url")
            ), ImmutableList.of(
                GravatarCryptoWalletAddress("labeler", "other address")
            )
        )

        assertEquals(1977252293, payments.hashCode())
        assertEquals(185752232, otherPayment.hashCode())
        assertEquals(payments.hashCode(), equalToPayments.hashCode())
        assertNotEquals(payments.hashCode(), otherPayment.hashCode())
    }
}