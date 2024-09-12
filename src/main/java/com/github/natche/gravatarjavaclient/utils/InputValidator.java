package com.github.natche.gravatarjavaclient.utils;

import com.google.common.base.Preconditions;

import javax.imageio.ImageIO;
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
     * The encapsulated input.
     */
    private final String input;

    private InputValidator(String input) {
        this.input = input;
    }

    /**
     * Constructs a new input validator from the provided input.
     *
     * @param input the input
     * @return a new InputValidator instance
     * @throws NullPointerException     if the provided input is null
     * @throws IllegalArgumentException if the provided input is empty
     */
    public static InputValidator from(String input) {
        Preconditions.checkNotNull(input);
        Preconditions.checkArgument(!input.trim().isEmpty());

        return new InputValidator(input);
    }

    /**
     * Returns whether the encapsulated email input is a valid email address.
     * Note, this does not check for existence, merely proper syntactical structure.
     *
     * @return whether the encapsulated input is a valid email address
     */
    public boolean isValidEmailAddress() {
        return EMAIL_ADDRESS_REGEX_PATTERN.matcher(input).matches();
    }

    /**
     * Returns whether the encapsulated input is a valid image URL.
     * See {@link ImageIO#read(URL)} for more information.
     *
     * @return whether the encapsulated input is a valid image URL
     */
    public boolean isValidImageUrl() {
        try {
            ImageIO.read(new URL(input)).getWidth();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Returns whether the provided object equals this object.
     *
     * @param o the other object
     * @return whether the provided object equals this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof InputValidator)) return false;

        InputValidator other = (InputValidator) o;
        return this.input.equals(other.input);
    }

    /**
     * Returns a hash code of this object.
     *
     * @return a hash code of this object
     */
    @Override
    public int hashCode() {
        return 31 * input.hashCode();
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "InputValidator{input=\"" + input + "\"}";
    }
}
