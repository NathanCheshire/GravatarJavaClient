package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

/**
 * Represents an interest or hobby in a Gravatar user's profile.
 */
@SuppressWarnings("ClassCanBeRecord") /* Needed by GSON */
public final class GravatarProfileInterest {
    /**
     * The unique ID of the interest.
     */
    @SerializedName("id")
    private final int id;

    /**
     * The name of the interest.
     */
    @SerializedName("name")
    private final String name;

    /**
     * Constructs a new GravatarProfileInterest.
     *
     * @param id   the unique ID of the interest
     * @param name the name of the interest
     * @throws NullPointerException if the provided name is null
     * @throws IllegalArgumentException if the provided name is empty
     */
    public GravatarProfileInterest(int id,
                                   String name) {
        Preconditions.checkNotNull(name);
        Preconditions.checkArgument(!name.trim().isEmpty());

        this.id = id;
        this.name = name;
    }

    /**
     * Returns the unique ID of the interest.
     *
     * @return the unique ID of the interest
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the interest.
     *
     * @return the name of the interest
     */
    public String getName() {
        return name;
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
        if (!(o instanceof GravatarProfileInterest other)) return false;
        return id == other.id
                && name.equals(other.name);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = Integer.hashCode(id);
        ret = 31 * ret + name.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileInterest{"
                + "id=" + id
                + ", name=\"" + name + "\""
                + "}";
    }
}

