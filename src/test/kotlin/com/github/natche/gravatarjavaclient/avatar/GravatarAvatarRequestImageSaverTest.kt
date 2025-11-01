package com.github.natche.gravatarjavaclient.avatar

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException
import com.google.common.collect.ImmutableList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.awt.image.BufferedImage
import java.io.File

/**
 * Tests for the [GravatarRequestImageSaver].
 */
class GravatarAvatarRequestImageSaverTest {
    /**
     * Sets the SUPPORTED_IMAGE_FORMATS to an ArrayList containing a blank string which will cause
     * the image write to fail. This is performed for testing coverage purposes.
     */
    @BeforeEach
    fun injectBlankFormat() {
        try {
            val formatsField = GravatarRequestImageSaver::class.java
                .getDeclaredField("SUPPORTED_IMAGE_FORMATS")
            formatsField.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val originalList = formatsField[GravatarRequestImageSaver.INSTANCE] as ImmutableList<String>
            val mutableList: MutableList<String> = ArrayList(originalList)
            mutableList.add("")
            formatsField[GravatarRequestImageSaver.INSTANCE] = ImmutableList.copyOf(mutableList)
        } catch (e: Exception) {
            // Swallow
        }
    }

    /**
     * Tests for the getSavedCount method.
     */
    @Test
    fun testGetSavedCount() {
        assertEquals(0, GravatarRequestImageSaver.INSTANCE.savedCount)
    }

    /**
     * Tests for the save to method.
     */
    @Test
    fun testSaveTo() {
        assertThrows(
            NullPointerException::class.java
        ) { GravatarRequestImageSaver.INSTANCE.saveTo(null, null, null) }
        val mockImage = Mockito.mock(BufferedImage::class.java)
        assertThrows(
            NullPointerException::class.java
        ) { GravatarRequestImageSaver.INSTANCE.saveTo(mockImage, null, null) }
        val mockFile = Mockito.mock(File::class.java)
        Mockito.`when`(mockFile.name).thenReturn("validName.png")

        assertThrows(
            NullPointerException::class.java
        ) { GravatarRequestImageSaver.INSTANCE.saveTo(mockImage, mockFile, null) }
        assertThrows(
            IllegalArgumentException::class.java
        ) { GravatarRequestImageSaver.INSTANCE.saveTo(mockImage, File("."), null) }
        assertThrows(
            GravatarJavaClientException::class.java
        ) { GravatarRequestImageSaver.INSTANCE.saveTo(mockImage, mockFile, "") }
        assertThrows(
            java.lang.IllegalArgumentException::class.java
        ) { GravatarRequestImageSaver.INSTANCE.saveTo(mockImage, File("invalid<>.png"), "") }
    }
}