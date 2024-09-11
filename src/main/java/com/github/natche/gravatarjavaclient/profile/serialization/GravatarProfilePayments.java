package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents a user's payment options in a Gravatar profile.
 */
public final class GravatarProfilePayments {
    /**
     * The list of links to external payment services (e.g., BuyMeACoffee).
     */
    @SerializedName("links")
    private final ImmutableList<GravatarProfileUrl> links;

    /**
     * The list of cryptocurrency wallet addresses.
     */
    @SerializedName("crypto_wallets")
    private final ImmutableList<GravatarCryptoWalletAddress> cryptoWallets;

    /**
     * Constructs a new GravatarProfilePayments.
     *
     * @param links         the list of external payment service links
     * @param cryptoWallets the list of cryptocurrency wallet addresses
     * @throws NullPointerException if links or cryptoWallets is null
     */
    public GravatarProfilePayments(List<GravatarProfileUrl> links,
                                   List<GravatarCryptoWalletAddress> cryptoWallets) {
        Preconditions.checkNotNull(links);
        Preconditions.checkNotNull(cryptoWallets);

        this.links = ImmutableList.copyOf(links);
        this.cryptoWallets = ImmutableList.copyOf(cryptoWallets);
    }

    /**
     * Returns the list of links to external payment services.
     *
     * @return the list of links to external payment services
     */
    public ImmutableList<GravatarProfileUrl> getLinks() {
        return links;
    }

    /**
     * Returns the list of cryptocurrency wallet addresses.
     *
     * @return the list of cryptocurrency wallet addresses
     */
    public ImmutableList<GravatarCryptoWalletAddress> getCryptoWallets() {
        return cryptoWallets;
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
        if (!(o instanceof GravatarProfilePayments other)) return false;
        return links.equals(other.links)
                && cryptoWallets.equals(other.cryptoWallets);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = links.hashCode();
        ret = 31 * ret + cryptoWallets.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfilePayments{"
                + "links=" + links
                + ", cryptoWallets=" + cryptoWallets
                + "}";
    }
}

