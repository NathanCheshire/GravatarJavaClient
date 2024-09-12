package com.github.natche.gravatarjavaclient.profile.serialization;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

import java.time.Instant;

/**
 * Represents metadata of a Gravatar profile request.
 */
@SuppressWarnings("ClassCanBeRecord") /* GSON needs this */
@Immutable
public final class GravatarProfileRequestResult {
    /**
     * The instant the request was made at.
     */
    private final Instant requestInstant;

    /**
     * Whether the request succeeded.
     */
    private final boolean succeeded;

    /**
     * Constructs a new GravatarProfileRequestResult.
     *
     * @param requestInstant the instant when the request was made
     * @param succeeded      whether the request succeeded
     * @throws NullPointerException if requestInstant is null
     */
    public GravatarProfileRequestResult(Instant requestInstant, boolean succeeded) {
        Preconditions.checkNotNull(requestInstant);

        this.requestInstant = requestInstant;
        this.succeeded = succeeded;
    }

    /**
     * Returns the instant the request was made at.
     *
     * @return the instant of the request
     */
    public Instant getRequestInstant() {
        return requestInstant;
    }

    /**
     * Returns whether the request succeeded.
     *
     * @return whether the request succeeded
     */
    public boolean succeeded() {
        return succeeded;
    }

    /**
     * Returns whether the request failed.
     *
     * @return true if the request failed, false otherwise
     */
    public boolean failed() {
        return !succeeded;
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
        if (!(o instanceof GravatarProfileRequestResult other)) return false;
        return succeeded == other.succeeded
                && requestInstant.equals(other.requestInstant);
    }

    /**
     * Returns a hashcode for this object.
     *
     * @return a hashcode for this object
     */
    @Override
    public int hashCode() {
        int ret = requestInstant.hashCode();
        ret = 31 * ret + (succeeded ? 1 : 0);
        return ret;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GravatarProfileRequestResult{"
                + "requestInstant=" + requestInstant
                + ", succeeded=" + succeeded
                + "}";
    }
}
