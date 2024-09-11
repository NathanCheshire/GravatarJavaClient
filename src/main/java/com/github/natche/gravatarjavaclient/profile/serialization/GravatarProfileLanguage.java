package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a language in a Gravatar user's profile.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
public final class GravatarProfileLanguage {
    /**
     * The language code (e.g., "en" for English).
     */
    @SerializedName("code")
    private final String code;

    /**
     * The name of the language.
     */
    @SerializedName("name")
    private final String name;

    /**
     * Whether this language is the primary language for the user.
     */
    @SerializedName("is_primary")
    private final boolean isPrimary;

    /**
     * The order in which this language appears.
     */
    @SerializedName("order")
    private final int order;

    /**
     * Constructs a new GravatarProfileLanguage.
     *
     * @param code      the language code
     * @param name      the name of the language
     * @param isPrimary whether this is the primary language
     * @param order     the order of the language in the profile
     * @throws NullPointerException if any string is null
     * @throws IllegalArgumentException if name or code is empty
     */
    public GravatarProfileLanguage(String code,
                                   String name,
                                   boolean isPrimary,
                                   int order) {
        Preconditions.checkNotNull(code);
        Preconditions.checkNotNull(name);
        Preconditions.checkArgument(!name.trim().isEmpty());
        Preconditions.checkArgument(!code.trim().isEmpty());

        this.code = code;
        this.name = name;
        this.isPrimary = isPrimary;
        this.order = order;
    }

    /**
     * Returns the language code.
     *
     * @return the language code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the name of the language.
     *
     * @return the name of the language
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether this is the primary language.
     *
     * @return whether this is the primary language
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * Returns the order of the language in the profile.
     *
     * @return the order of the language in the profile
     */
    public int getOrder() {
        return order;
    }

    /**
     * Returns whether the provided object is equal to this.
     *
     * @param o the other object
     * @return whether the provided object is equal to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GravatarProfileLanguage other)) return false;
        return isPrimary == other.isPrimary
                && order == other.order
                && code.equals(other.code)
                && name.equals(other.name);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = code.hashCode();
        ret = 31 * ret + name.hashCode();
        ret = 31 * ret + Boolean.hashCode(isPrimary);
        ret = 31 * ret + Integer.hashCode(order);
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileLanguage{"
                + "code=\"" + code + "\""
                + ", name=\"" + name + "\""
                + ", isPrimary=" + isPrimary
                + ", order=" + order
                + "}";
    }
}
