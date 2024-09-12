package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a cryptocurrency wallet address on a Gravatar user's profile.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
public final class GravatarCryptoWalletAddress {
    /**
     * The label for the cryptocurrency (e.g., ETH, BTC).
     */
    @SerializedName("label")
    private final String label;

    /**
     * The cryptocurrency wallet address.
     */
    @SerializedName("address")
    private final String address;

    /**
     * Constructs a new CryptoWalletAddress.
     *
     * @param label   the label for the cryptocurrency
     * @param address the cryptocurrency wallet address
     * @throws NullPointerException if label or address is null
     */
    public GravatarCryptoWalletAddress(String label,
                                       String address) {
        Preconditions.checkNotNull(label);
        Preconditions.checkNotNull(address);
        Preconditions.checkArgument(!label.trim().isEmpty());
        Preconditions.checkArgument(!address.trim().isEmpty());

        this.label = label;
        this.address = address;
    }

    /**
     * Returns the label for the cryptocurrency.
     *
     * @return the label for the cryptocurrency
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the cryptocurrency wallet address.
     *
     * @return the cryptocurrency wallet address
     */
    public String getAddress() {
        return address;
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
        if (!(o instanceof GravatarCryptoWalletAddress other)) return false;
        return label.equals(other.label)
                && address.equals(other.address);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = label.hashCode();
        ret = 31 * ret + address.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "CryptoWalletAddress{"
                + "label=\"" + label + "\""
                + ", address=\"" + address + "\""
                + "}";
    }
}
