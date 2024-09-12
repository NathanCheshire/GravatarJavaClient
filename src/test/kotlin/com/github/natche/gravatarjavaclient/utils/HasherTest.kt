package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

/**
 * Tests for the [Hasher].
 */
class HasherTest {
    /**
     * Tests for creation.
     */
    @Test
    fun testCreation() {
        assertThrows(NullPointerException::class.java) { Hasher.fromAlgorithm(null) }
        assertThrows(IllegalArgumentException::class.java) { Hasher.fromAlgorithm("") }
        assertThrows(IllegalArgumentException::class.java) { Hasher.fromAlgorithm("  ") }

        assertDoesNotThrow { Hasher.fromSha256() }
        assertDoesNotThrow { Hasher.fromAlgorithm("algorithm") }
    }

    /**
     * Tests for the hash method.
     */
    @Test
    @Suppress("SpellCheckingInspection") /* Hashes */
    fun testHash() {
        val invalid = Hasher.fromAlgorithm("alg")
        val valid = Hasher.fromSha256()

        assertEquals("c96c6d5be8d08a12e7b5cdc1b207fa6b2430974c86803d8891675e76fd992c20",
            valid.hash("input"))
        assertEquals("833d2f04e21b4f8ad071e0bf984823755b456c65d3e0dcbabc92b30b47f90216",
            valid.hash("other input"))

        assertThrows(GravatarJavaClientException::class.java) { invalid.hash("input") }
        assertThrows(GravatarJavaClientException::class.java) { invalid.hash("other input") }
    }

    /**
     * Tests for the to string method.
     */
    @Test
    fun testToString() {
        val first = Hasher.fromAlgorithm("alg")
        val notEqual = Hasher.fromAlgorithm("other alg")

        assertEquals("Hasher{algorithm=\"alg\"}", first.toString())
        assertEquals("Hasher{algorithm=\"other alg\"}", notEqual.toString())
    }

    /**
     * Tests for the hash code method.
     */
    @Test
    fun testHashCode() {
        val first = Hasher.fromAlgorithm("alg")
        val equal = Hasher.fromAlgorithm("alg")
        val notEqual = Hasher.fromAlgorithm("other alg")

        assertEquals(2996708, first.hashCode())
        assertEquals(2996708, equal.hashCode())
        assertEquals(-240970508, notEqual.hashCode())
        assertEquals(first.hashCode(), equal.hashCode())
        assertNotEquals(first.hashCode(), notEqual.hashCode())
    }

    /**
     * Tests for the equals method.
     */
    @Test
    fun testEquals() {
        val first = Hasher.fromAlgorithm("alg")
        val equal = Hasher.fromAlgorithm("alg")
        val notEqual = Hasher.fromAlgorithm("other alg")

        assertEquals(first, first)
        assertEquals(first, equal)
        assertNotEquals(first, notEqual)
        assertNotEquals(first, Object())
    }
}