package com.github.natche.gravatarjavaclient.profile.gson;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A singleton for providing a {@link Gson} instance for serialization/deserialization
 * with custom registered type adapters.
 */
public enum GsonProvider {
    /**
     * The gson provider instance.
     */
    INSTANCE;

    /**
     * The encapsulated gson object.
     */
    private final Gson gson;

    GsonProvider() {
        gson = new GsonBuilder()
                .registerTypeAdapter(
                        ImmutableList.class, new ImmutableListDeserializer()
                )
                .create();
    }

    /**
     * Returns the gson object.
     *
     * @return the gson object
     */
    public Gson get() {
        return gson;
    }
}
