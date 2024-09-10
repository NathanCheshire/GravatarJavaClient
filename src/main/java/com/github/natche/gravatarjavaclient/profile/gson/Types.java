package com.github.natche.gravatarjavaclient.profile.gson;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

@SuppressWarnings({"unchecked"})
public final class Types {
    private Types() {}

    public static <E> TypeToken<Collection<E>> collectionOf(Type type) {
        TypeParameter<E> newTypeParameter = new TypeParameter<E>() {};
        return new TypeToken<Collection<E>>() {}
                .where(newTypeParameter, (TypeToken<E>) TypeToken.of(type));
    }
}
