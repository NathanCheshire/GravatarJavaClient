package com.github.natche.gravatarjavaclient.exceptions;

import com.google.common.base.Preconditions;

/**
 * An exception thrown by public methods of the GravatarJavaClient API.
 */
public final class GravatarJavaClientException extends RuntimeException {
    /**
     * Creates a new {@link GravatarJavaClientException} with the provided message.
     *
     * @param message the message
     * @throws NullPointerException if the provided message is null
     */
    public GravatarJavaClientException(String message) {
        super(Preconditions.checkNotNull(message));
    }

    /**
     * Creates a new {@link GravatarJavaClientException} from the provided exception.
     *
     * @param exception the exception
     * @throws NullPointerException if the provided exception is null
     */
    public GravatarJavaClientException(Exception exception) {
        super(Preconditions.checkNotNull(exception).getMessage());
    }
}
