package com.github.natche.gravatarjavaclient.utils

import com.github.natche.gravatarjavaclient.TestingImageUrls
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.atomic.AtomicReference

/**
 * Tests for the [GeneralUtils].
 */
internal class GeneralUtilsTest {
    /**
     * Tests to ensure reflection is guarded against.
     * Also helps reach 100% code coverage for testing.
     */
    @Test
    fun testCreation() {
        try {
            val constructor = GeneralUtils::class.java.getDeclaredConstructor()
            constructor.isAccessible = true
            constructor.newInstance()
        } catch (e: Exception) {
            Assertions.assertTrue(e is InvocationTargetException)
            val target = (e as InvocationTargetException).targetException
            Assertions.assertTrue(target is AssertionError)
            Assertions.assertEquals("Cannot create instances of GeneralUtils", target.message)
        }
    }

    /**
     * Tests for the read buffered image method.
     */
    @Test
    fun testReadBufferedImage() {
        Assertions.assertThrows(NullPointerException::class.java) { GeneralUtils.readBufferedImage(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java) { GeneralUtils.readBufferedImage("") }
        Assertions.assertThrows(GravatarJavaClientException::class.java) { GeneralUtils.readBufferedImage("url") }
        val bi = AtomicReference<BufferedImage>()
        Assertions.assertDoesNotThrow { bi.set(GeneralUtils.readBufferedImage(TestingImageUrls.foreignImageUrl)) }
        Assertions.assertEquals(200, bi.get().width)
        Assertions.assertEquals(300, bi.get().height)
    }

    /**
     * Tests for the email address to gravatar hash method.
     */
    @Test
    @Suppress("SpellCheckingInspection")
    fun testEmailAddressToGravatarHash() {
        Assertions.assertThrows(NullPointerException::class.java)
        { GeneralUtils.emailAddressToGravatarHash(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToGravatarHash("") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToGravatarHash("asdf") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToGravatarHash("invalid") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToGravatarHash("invalid@asdf") }
        Assertions.assertDoesNotThrow<String>
        { GeneralUtils.emailAddressToGravatarHash("valid@domain.com") }
        Assertions.assertEquals(
            "2bf1b7a19bcad06a8e894d7373a4cfc7",
            GeneralUtils.emailAddressToGravatarHash("nathan.vincent.2.718@gmail.com")
        )
        Assertions.assertEquals(
            "21bce028aab22e8c9ec03d66305a50dc",
            GeneralUtils.emailAddressToGravatarHash("nathan.cheshire.ctr@nrlssc.navy.mil")
        )
        Assertions.assertEquals(
            "e11e6bd8201d3bd6f22c4b206a863a2c",
            GeneralUtils.emailAddressToGravatarHash("ncheshire@camgian.com")
        )
    }

    /**
     * Test for the email address to profiles API hash.
     */
    @Test
    @Suppress("SpellCheckingInspection")
    fun testEmailAddressToProfilesApiHash() {
        Assertions.assertThrows(NullPointerException::class.java)
        { GeneralUtils.emailAddressToProfilesApiHash(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToProfilesApiHash("") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToProfilesApiHash("   ") }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.emailAddressToProfilesApiHash("invalid.email") }
        Assertions.assertDoesNotThrow<String>
        { GeneralUtils.emailAddressToProfilesApiHash("valid@domain.com") }
        Assertions.assertEquals(
            "8e50ba67083844f6c70829f2ad5e29eb6fff55123f2b34ee63ae9eb9adccb4b4",
            GeneralUtils.emailAddressToProfilesApiHash("valid.email@gmail.com")
        )
    }

    /**
     * Tests for the hash input method.
     */
    @Test
    @Suppress("SpellCheckingInspection")
    fun testHashInput() {
        Assertions.assertThrows(NullPointerException::class.java)
        { GeneralUtils.hashInput(null, null) }
        Assertions.assertThrows(NullPointerException::class.java)
        { GeneralUtils.hashInput("", null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.hashInput("", "") }
        Assertions.assertThrows(
            GravatarJavaClientException::class.java
        ) { GeneralUtils.hashInput("", "asdf") }
        Assertions.assertEquals(
            "d41d8cd98f00b204e9800998ecf8427e",
            GeneralUtils.hashInput("", "MD5")
        )
        Assertions.assertEquals(
            "da39a3ee5e6b4b0d3255bfef95601890afd80709",
            GeneralUtils.hashInput("", "SHA1")
        )
        Assertions.assertEquals(
            "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
            GeneralUtils.hashInput("", "SHA256")
        )
        Assertions.assertEquals(
            "2bf1b7a19bcad06a8e894d7373a4cfc7",
            GeneralUtils.hashInput("nathan.vincent.2.718@gmail.com", "MD5")
        )
        Assertions.assertEquals(
            "f84099a5bbef987de2f69b2caf96536d2c76fbf0",
            GeneralUtils.hashInput("nathan.vincent.2.718@gmail.com", "SHA1")
        )
        Assertions.assertEquals(
            "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168",
            GeneralUtils.hashInput("nathan.vincent.2.718@gmail.com", "SHA256")
        )
    }

    /**
     * Tests for the read url method.
     */
    @Test
    fun testReadUrl() {
        Assertions.assertThrows(NullPointerException::class.java)
        { GeneralUtils.readUrl(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java)
        { GeneralUtils.readUrl("") }
        Assertions.assertThrows(GravatarJavaClientException::class.java)
        { GeneralUtils.readUrl("invalid url") }
        Assertions.assertDoesNotThrow<String>
        { GeneralUtils.readUrl("https://www.google.com") }
    }

    /**
     * Tests for the is valid filename method.
     */
    @Test
    fun testIsValidFilename() {
        Assertions.assertThrows(NullPointerException::class.java) { GeneralUtils.isValidFilename(null) }
        Assertions.assertThrows(IllegalArgumentException::class.java) { GeneralUtils.isValidFilename("") }
        Assertions.assertDoesNotThrow<Boolean> { GeneralUtils.isValidFilename("filename") }
        Assertions.assertTrue(GeneralUtils.isValidFilename("filename"))
        Assertions.assertTrue(GeneralUtils.isValidFilename("my_filename_123456789"))
        Assertions.assertTrue(GeneralUtils.isValidFilename("my_filename_123456789.txt"))
        Assertions.assertTrue(GeneralUtils.isValidFilename("my_filename_123456789...txt"))
        Assertions.assertFalse(GeneralUtils.isValidFilename("$%^&*()"))
        Assertions.assertFalse(GeneralUtils.isValidFilename("<>:/"))
    }
}