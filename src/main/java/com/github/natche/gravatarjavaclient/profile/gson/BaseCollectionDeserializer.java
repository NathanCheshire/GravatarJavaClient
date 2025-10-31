package com.github.natche.gravatarjavaclient.profile.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * A collection deserializer for use by {@link com.google.gson.Gson}
 * to deserialize a collection token into an {@link com.google.common.collect.ImmutableList}.
 *
 * @param <E> the entity type
 */
public abstract class BaseCollectionDeserializer<E> implements JsonDeserializer<E> {
    /**
     * {@inheritDoc}
     */
    protected abstract E buildFrom(Collection<?> collection);

    /**
     * Deserializes the provided JSON element.
     *
     * @param json    The Json data being deserialized
     * @param type    The type of the Object to deserialize to
     * @param context tge deserialization context
     * @return the deserialized object, collection in this case
     */
    public E deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
        Type parameterizedType = Types.collectionOf(typeArguments[0]).getType();
        Collection<?> collection = context.deserialize(json, parameterizedType);

        return buildFrom(collection);
    }
}
