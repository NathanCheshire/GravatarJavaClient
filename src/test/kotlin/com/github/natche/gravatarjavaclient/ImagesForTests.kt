package com.github.natche.gravatarjavaclient

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Images used for tests requiring valid foreign images.
 */
class ImagesForTests private constructor() {
    /**
     * Suppress default constructor.
     */
    init {
        throw AssertionError("Cannot create instances of TestingConstants")
    }

    companion object {
        const val foreignImageUrl = "https://picsum.photos/seed/gravatar-java-client/200/300"
        const val anotherForeignImageUrl = "https://picsum.photos/seed/gravatar-java-client/300/300"

        private val jpegSignatures = listOf(
            byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE0.toByte()),
            byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE1.toByte()),
            byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xEE.toByte())
        )
         private val pngSignature = byteArrayOf(
            0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(),
            0x0D.toByte(), 0x0A.toByte(), 0x1A.toByte(), 0x0A.toByte()
        )

        /**
         * Internal function to validate a file for being a PNG.
         */
        fun isValidPng(file: File): Boolean {
            if (!file.exists() || !file.isFile) return false

            return try {
                FileInputStream(file).use { inputStream ->
                    val header = ByteArray(8)
                    val bytesRead = inputStream.read(header)
                    @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog") /* not valid here */
                    if (bytesRead != 8) return false
                    else return Arrays.equals(header, pngSignature)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

        /**
         * Internal function to validate a file for being a JPG.
         */
        fun isValidJpg(file: File): Boolean {
            if (!file.exists() || !file.isFile) return false

            return try {
                FileInputStream(file).use { inputStream ->
                    val header = ByteArray(4)
                    val bytesRead = inputStream.read(header)
                    if (bytesRead != 4) false
                    else jpegSignatures.any {
                        @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog") /* not valid here */
                        Arrays.equals(it, header)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }
}