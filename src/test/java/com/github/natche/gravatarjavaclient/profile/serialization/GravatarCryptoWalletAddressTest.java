package com.github.natche.gravatarjavaclient.profile.serialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GravatarCryptoWalletAddress}es.
 */
public class GravatarCryptoWalletAddressTest {
    GravatarCryptoWalletAddressTest() {}

    /**
     * Tests for creation of wallet addresses.
     */
    @Test
    void testCreation() {
        assertThrows(NullPointerException.class, () -> new GravatarCryptoWalletAddress(null, null));
        assertThrows(NullPointerException.class, () -> new GravatarCryptoWalletAddress("label", null));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarCryptoWalletAddress("", ""));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarCryptoWalletAddress("label", ""));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarCryptoWalletAddress("  ", ""));
        assertThrows(IllegalArgumentException.class,
                () -> new GravatarCryptoWalletAddress("label", "  "));
        assertDoesNotThrow(() -> new GravatarCryptoWalletAddress("label", "address"));
    }

    /**
     * Tests for the accessor methods.
     */
    @Test
    void testAccessors() {
        GravatarCryptoWalletAddress address = new GravatarCryptoWalletAddress("label", "address");
        assertEquals("label", address.getLabel());
        assertEquals("address", address.getAddress());
    }

    /**
     * Tests for the to string method.
     */
    @Test
    void testToString() {
        GravatarCryptoWalletAddress address = new GravatarCryptoWalletAddress("label", "address");
        GravatarCryptoWalletAddress otherAddress = new GravatarCryptoWalletAddress("label 2", "127");

        assertEquals("CryptoWalletAddress{label=\"label\", address=\"address\"}", address.toString());
        assertEquals("CryptoWalletAddress{label=\"label 2\", address=\"127\"}", otherAddress.toString());
    }

    /**
     * Tests for the equals method.
     */
    @Test
    void testEquals() {
        GravatarCryptoWalletAddress address = new GravatarCryptoWalletAddress("label", "address");
        GravatarCryptoWalletAddress equalAddress = new GravatarCryptoWalletAddress("label", "address");
        GravatarCryptoWalletAddress otherAddress = new GravatarCryptoWalletAddress("label 2", "127");

        assertEquals(address, address);
        assertEquals(address, equalAddress);
        assertNotEquals(address, otherAddress);
        assertNotEquals(address, new Object());
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    void testHashCode() {
        GravatarCryptoWalletAddress address = new GravatarCryptoWalletAddress("label", "address");
        GravatarCryptoWalletAddress equalAddress = new GravatarCryptoWalletAddress("label", "address");
        GravatarCryptoWalletAddress otherAddress = new GravatarCryptoWalletAddress("label 2", "127");

        assertEquals(2036857728, address.hashCode());
        assertEquals(2036857728, equalAddress.hashCode());
        assertEquals(-1959270160, otherAddress.hashCode());

        assertEquals(address.hashCode(), equalAddress.hashCode());
        assertNotEquals(address.hashCode(), otherAddress.hashCode());
    }
}
