package com.github.natche.gravatarjavaclient.profile.gson;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Utility class for constructing parameterized type tokens used in Gson deserialization.
 * <p>
 * This class provides helper methods for creating {@link TypeToken} instances that represent
 * generic collection types, which are necessary for Gson to properly deserialize generic collections.
 */
@SuppressWarnings({"unchecked"})
public final class Types {
    private Types() {}

    /**
     * Creates a {@link TypeToken} representing a {@link Collection} of the specified element type.
     * <p>
     * This method is useful for deserializing JSON arrays into strongly-typed collections when
     * using Gson. For example, to deserialize a JSON array into a {@code Collection<String>}:
     * <pre>{@code
     * Type collectionType = Types.collectionOf(String.class).getType();
     * Collection<String> result = gson.fromJson(json, collectionType);
     * }</pre>
     *
     * @param type the element type of the collection
     * @param <E>  the element type parameter
     * @return a TypeToken representing {@code Collection<E>}
     * @throws NullPointerException if type is null
     */
    public static <E> TypeToken<Collection<E>> collectionOf(Type type) {
        TypeParameter<E> newTypeParameter = new TypeParameter<E>() {};
        return new TypeToken<Collection<E>>() {}
                .where(newTypeParameter, (TypeToken<E>) TypeToken.of(type));
    }
}