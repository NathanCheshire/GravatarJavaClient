package com.github.natche.gravatarjavaclient.profile.gson;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

/**
 * A collection deserializer for {@link com.google.gson.Gson} to use when deserializing
 * a Collection into an {@link ImmutableList} from Guava.
 */
public final class ImmutableListDeserializer extends BaseCollectionDeserializer<ImmutableList<?>> {
    /**
     * Deserializes a collection into an {@link ImmutableList}.
     *
     * @param collection the collection to deserialize.
     * @return a new ImmutableList containing the elements of the provided collection
     */
    @Override
    protected ImmutableList<?> buildFrom(Collection<?> collection) {
        return ImmutableList.copyOf(collection);
    }
}
