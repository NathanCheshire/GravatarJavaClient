package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a user's public contact information on a Gravatar profile.
 * This information is provided only in authenticated requests.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
public final class GravatarProfileContactInfo {
    /**
     * The user's home phone number.
     */
    @SerializedName("home_phone")
    private final String homePhone;

    /**
     * The user's work phone number.
     */
    @SerializedName("work_phone")
    private final String workPhone;

    /**
     * The user's cell phone number.
     */
    @SerializedName("cell_phone")
    private final String cellPhone;

    /**
     * The user's email address.
     */
    @SerializedName("email")
    private final String email;

    /**
     * The URL to the user's contact form.
     */
    @SerializedName("contact_form")
    private final String contactForm;

    /**
     * The URL to the user's calendar.
     */
    @SerializedName("calendar")
    private final String calendar;

    /**
     * Constructs a new GravatarProfileContactInfo.
     *
     * @param homePhone   the user's home phone number
     * @param workPhone   the user's work phone number
     * @param cellPhone   the user's cell phone number
     * @param email       the user's email address
     * @param contactForm the URL to the user's contact form
     * @param calendar    the URL to the user's calendar
     * @throws NullPointerException if any parameter is null
     */
    public GravatarProfileContactInfo(String homePhone,
                                      String workPhone,
                                      String cellPhone,
                                      String email,
                                      String contactForm,
                                      String calendar) {
        Preconditions.checkNotNull(homePhone);
        Preconditions.checkNotNull(workPhone);
        Preconditions.checkNotNull(cellPhone);
        Preconditions.checkNotNull(email);
        Preconditions.checkNotNull(contactForm);
        Preconditions.checkNotNull(calendar);

        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.cellPhone = cellPhone;
        this.email = email;
        this.contactForm = contactForm;
        this.calendar = calendar;
    }

    /**
     * Returns the user's home phone number.
     *
     * @return the user's home phone number
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Returns the user's work phone number.
     *
     * @return the user's work phone number
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Returns the user's cell phone number.
     *
     * @return the user's cell phone number
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the URL to the user's contact form.
     *
     * @return the URL to the user's contact form
     */
    public String getContactForm() {
        return contactForm;
    }

    /**
     * Returns the URL to the user's calendar.
     *
     * @return the URL to the user's calendar
     */
    public String getCalendar() {
        return calendar;
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
        if (!(o instanceof GravatarProfileContactInfo other)) return false;
        return homePhone.equals(other.homePhone)
                && workPhone.equals(other.workPhone)
                && cellPhone.equals(other.cellPhone)
                && email.equals(other.email)
                && contactForm.equals(other.contactForm)
                && calendar.equals(other.calendar);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = homePhone.hashCode();
        ret = 31 * ret + workPhone.hashCode();
        ret = 31 * ret + cellPhone.hashCode();
        ret = 31 * ret + email.hashCode();
        ret = 31 * ret + contactForm.hashCode();
        ret = 31 * ret + calendar.hashCode();
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileContactInfo{"
                + "homePhone=\"" + homePhone + "\""
                + ", workPhone=\"" + workPhone + "\""
                + ", cellPhone=\"" + cellPhone + "\""
                + ", email=\"" + email + "\""
                + ", contactForm=\"" + contactForm + "\""
                + ", calendar=\"" + calendar + "\""
                + "}";
    }
}
