package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A singleton for interfacing with the Gravatar Profile REST API.
 */
enum GravatarProfileRequestHandler {
    /**
     * The Gravatar Profile request handler.
     */
    INSTANCE;

    /**
     * The HTTP client this handler uses internally for communicating with the Gravatar API.
     */
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * The count of authenticated requests this handler has sent.
     */
    private final AtomicInteger authenticatedRequestCount = new AtomicInteger();

    /**
     * The count of unauthenticated requests this handler has sent.
     */
    private final AtomicInteger unauthenticatedRequestCount = new AtomicInteger();

    /**
     * Returns the number of authenticated requests this handler has sent.
     *
     * @return the number of authenticated requests this handler has sent
     */
    public int getAuthenticatedRequestCount() {
        return authenticatedRequestCount.get();
    }

    /**
     * Returns the number of unauthenticated requests this handler has sent.
     *
     * @return the number of unauthenticated requests this handler has sent
     */
    public int getUnauthenticatedRequestCount() {
        return unauthenticatedRequestCount.get();
    }

    /**
     * Reads and returns a serialized object from the Gravatar Profile API.
     *
     * @param token      the authentication token to use; if not provided, only certain fields will be returned
     * @param nameOrHash the name or SHA256 hash to use
     * @return a profile object
     * @throws NullPointerException     if the provided name or hash is null
     * @throws IllegalArgumentException if the provided name or hash is empty
     */
    GravatarProfile getProfile(String token, String nameOrHash) {
        Preconditions.checkNotNull(nameOrHash);
        Preconditions.checkArgument(!nameOrHash.trim().isEmpty());

        try {
            String url = "https://api.gravatar.com/v3/profiles/" + nameOrHash;
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET();

            if (token != null) {
                requestBuilder.header("Authorization", "Bearer " + token);
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            if (body.contains("error")) {
                JsonObject responseObject = GsonProvider.INSTANCE.get().fromJson(body, JsonObject.class);
                throw new RuntimeException("Gravatar API error: " + responseObject.get("error").getAsString());
            }

            return GsonProvider.INSTANCE.get().fromJson(body, GravatarProfile.class);
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        } finally {
            if (token == null) unauthenticatedRequestCount.incrementAndGet();
            else authenticatedRequestCount.incrementAndGet();
        }
    }
}
