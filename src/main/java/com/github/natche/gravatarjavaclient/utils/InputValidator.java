package com.github.natche.gravatarjavaclient.utils;

import com.google.common.collect.ImmutableList;

import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * A string validator used for validating parameter inputs.
 */
public final class InputValidator {
    /**
     * A compiled regex pattern for validating email addresses.
     */
    private static final Pattern EMAIL_ADDRESS_REGEX_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
            + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b"
            + "\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9]"
            + ")?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]"
            + ")\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\"
            + "x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    /**
     * The invalid filename characters for Windows and Unix based systems.
     */
    private static final ImmutableList<Character> INVALID_FILENAME_CHARS = ImmutableList.of(
            '<', '>', ':', '\\', '|', '?', '*', '/', '\'', '"', '\u0000'
    );

    /**
     * Returns whether the provided input is a valid email address.
     * Note, this does not check for existence, merely proper syntactical structure.
     *
     * @param input the input
     * @return whether the encapsulated input is a valid email address
     */
    public static boolean isValidEmailAddress(String input) {
        if (input == null) return false;
        if (input.trim().isEmpty()) return false;

        return EMAIL_ADDRESS_REGEX_PATTERN.matcher(input).matches();
    }

    /**
     * Returns whether the provided input is a valid image URL.
     * See {@link ImageIO#read(URL)} for more information.
     *
     * @param input the input
     * @return whether the encapsulated input is a valid image URL
     */
    @SuppressWarnings("ResultOfMethodCallIgnored") /* Validation */
    public static boolean isValidImageUrl(String input) {
        if (input == null) return false;
        if (input.trim().isEmpty()) return false;

        try {
            URI.create(input).toURL();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Returns whether the provided input is a valid filename for this operating system.
     *
     * @param input the input to use as a filename
     * @return whether the provided filename is valid for this operating system
     * @throws NullPointerException     if the provided filename is null
     * @throws IllegalArgumentException if the provided filename is empty
     */
    public static boolean isValidFilename(String input) {
        if (input == null) return false;
        if (input.trim().isEmpty()) return false;

        for (char c : input.toCharArray()) {
            if (INVALID_FILENAME_CHARS.contains(c)) return false;
        }

        return true;
    }
}
