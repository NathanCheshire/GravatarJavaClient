package com.github.natche.gravatarjavaclient.utils;

import com.github.natche.gravatarjavaclient.image.GravatarImageRequestBuilderImpl;
import com.google.common.base.Preconditions;

import javax.imageio.ImageIO;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Validation utils used by {@link GravatarImageRequestBuilderImpl}s.
 */
public final class ValidationUtils {
    /**
     * A pattern for validating email addresses.
     */
    private static final Pattern emailAddressRegexPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
            + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b"
            + "\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9]"
            + ")?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]"
            + ")\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\"
            + "x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    /**
     * Suppress default constructor.
     *
     * @throws AssertionError if invoked
     */
    private ValidationUtils() {
        throw new AssertionError("Cannot create instances of ValidationUtils");
    }

    /**
     * Returns whether the provided email address is valid.
     * Note, this does not check for existence, merely proper syntactical structure.
     *
     * @param emailAddress the email address to validate
     * @return whether the provided email address is valid
     * @throws NullPointerException     if the provided email address is null
     * @throws IllegalArgumentException if the provided email address is empty
     */
    public static boolean isValidEmailAddress(String emailAddress) {
        Preconditions.checkNotNull(emailAddress);
        Preconditions.checkArgument(!emailAddress.isEmpty());

        return emailAddressRegexPattern.matcher(emailAddress).matches();
    }

    /**
     * Returns whether the provided URL is a valid default image URL.
     *
     * @param defaultUrl the default URL
     * @return whether the provided URL is a valid default image URL
     * @throws NullPointerException     if the URL is null
     * @throws IllegalArgumentException if the URL is empty
     */
    public static boolean isValidDefaultUrl(String defaultUrl) {
        Preconditions.checkNotNull(defaultUrl);
        Preconditions.checkArgument(!defaultUrl.isEmpty());

        try {
            return ImageIO.read(new URL(defaultUrl)).getWidth() > 0;
        } catch (Exception ignored) {
            return false;
        }
    }
}
