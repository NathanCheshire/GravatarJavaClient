package com.github.natche.gravatarjavaclient.profile.serialization

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for [GravatarCryptoWalletAddress]es.
 */
class GravatarCryptoWalletAddressTest internal constructor() {
    /**
     * Tests for creation of wallet addresses.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java)
        { GravatarCryptoWalletAddress(null, null) }
        assertThrows(NullPointerException::class.java)
        { GravatarCryptoWalletAddress("label", null) }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarCryptoWalletAddress("", "") }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarCryptoWalletAddress("label", "") }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarCryptoWalletAddress("  ", "") }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarCryptoWalletAddress("label", "  ") }
        assertDoesNotThrow<GravatarCryptoWalletAddress>
        { GravatarCryptoWalletAddress("label", "address") }
    }

    /**
     * Tests for the accessor methods.
     */
    @Test
    fun testAccessors() {
        val address = GravatarCryptoWalletAddress("label", "address")
        assertEquals("label", address.label)
        assertEquals("address", address.address)
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val address = GravatarCryptoWalletAddress("label", "address")
        val otherAddress = GravatarCryptoWalletAddress("label 2", "127")
        assertEquals("CryptoWalletAddress{label=\"label\", address=\"address\"}", address.toString())
        assertEquals("CryptoWalletAddress{label=\"label 2\", address=\"127\"}", otherAddress.toString())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val address = GravatarCryptoWalletAddress("label", "address")
        val equalAddress = GravatarCryptoWalletAddress("label", "address")
        val otherAddress = GravatarCryptoWalletAddress("label 2", "127")
        val differentAddress = GravatarCryptoWalletAddress("label 2", "128")
        assertEquals(address, address)
        assertEquals(address, equalAddress)
        assertNotEquals(address, otherAddress)
        assertNotEquals(address, Any())
        assertNotEquals(otherAddress, differentAddress)
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val address = GravatarCryptoWalletAddress("label", "address")
        val equalAddress = GravatarCryptoWalletAddress("label", "address")
        val otherAddress = GravatarCryptoWalletAddress("label 2", "127")
        assertEquals(2036857728, address.hashCode())
        assertEquals(2036857728, equalAddress.hashCode())
        assertEquals(-1959270160, otherAddress.hashCode())
        assertEquals(address.hashCode(), equalAddress.hashCode())
        assertNotEquals(address.hashCode(), otherAddress.hashCode())
    }
}