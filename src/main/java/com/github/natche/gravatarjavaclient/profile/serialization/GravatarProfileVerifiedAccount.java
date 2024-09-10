package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a verified account profile for a Gravatar user.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
public final class GravatarProfileVerifiedAccount {
    /**
     * The type of the service (e.g., GitHub, Twitter).
     */
    @SerializedName("service_type")
    private final String serviceType;

    /**
     * The label or name of the service.
     */
    @SerializedName("service_label")
    private final String serviceLabel;

    /**
     * The URL to the service's icon.
     */
    @SerializedName("service_icon")
    private final String serviceIcon;

    /**
     * The URL to the user's profile on the service.
     */
    @SerializedName("url")
    private final String url;

    /**
     * Constructs a new GravatarProfileVerifiedAccount.
     *
     * @param serviceType  the type of the service
     * @param serviceLabel the label or name of the service
     * @param serviceIcon  the URL to the service's icon
     * @param url          the URL to the user's profile on the service
     * @throws NullPointerException if any parameter is null
     */
    public GravatarProfileVerifiedAccount(String serviceType,
                                          String serviceLabel,
                                          String serviceIcon,
                                          String url) {
        Preconditions.checkNotNull(serviceType);
        Preconditions.checkNotNull(serviceLabel);
        Preconditions.checkNotNull(serviceIcon);
        Preconditions.checkNotNull(url);

        this.serviceType = serviceType;
        this.serviceLabel = serviceLabel;
        this.serviceIcon = serviceIcon;
        this.url = url;
    }

    /**
     * Returns the type of the service.
     *
     * @return the type of the service
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Returns the label or name of the service.
     *
     * @return the label or name of the service
     */
    public String getServiceLabel() {
        return serviceLabel;
    }

    /**
     * Returns the URL to the service's icon.
     *
     * @return the URL to the service's icon
     */
    public String getServiceIcon() {
        return serviceIcon;
    }

    /**
     * Returns the URL to the user's profile on the service.
     *
     * @return the URL to the user's profile on the service
     */
    public String getUrl() {
        return url;
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
        if (!(o instanceof GravatarProfileVerifiedAccount other)) return false;
        return serviceType.equals(other.serviceType)
                && serviceLabel.equals(other.serviceLabel)
                && serviceIcon.equals(other.serviceIcon)
                && url.equals(other.url);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = serviceType.hashCode();
        ret = 31 * ret + serviceLabel.hashCode();
        ret = 31 * ret + serviceIcon.hashCode();
        ret = 31 * ret + url.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileVerifiedAccount{"
                + "serviceType=\"" + serviceType + "\""
                + ", serviceLabel=\"" + serviceLabel + "\""
                + ", serviceIcon=\"" + serviceIcon + "\""
                + ", url=\"" + url + "\""
                + "}";
    }
}
