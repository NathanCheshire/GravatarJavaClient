package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfileRequestResult;
import com.github.natche.gravatarjavaclient.utils.ResourceReader;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Gravatar Profile request handler for requesting profiles from the Gravatar Profile API.
 */
enum GravatarProfileRequestHandler {
    /**
     * The Gravatar Profile request handler.
     */
    INSTANCE;

    /**
     * The charset to encode data with.
     */
    private static final Charset ENCODE_CHARSET = StandardCharsets.US_ASCII;

    /**
     * The port SSL/TLS uses for HTTPS.
     */
    private static final int HTTPS_PORT = 443;

    /**
     * The API host.
     */
    private static final String API_HOST = "api.gravatar.com";

    /**
     * The API version of the host.
     */
    private static final String API_VERSION = "3";

    /**
     * A character return and line feed string.
     */
    private static final String CRLF = "\r\n";

    /**
     * The result of all authenticated requests during this JVM runtime.
     */
    private final ArrayList<GravatarProfileRequestResult> AUTHENTICATED_REQUESTS = new ArrayList<>();

    /**
     * The result of all unauthenticated requests during this JVM runtime.
     */
    private final ArrayList<GravatarProfileRequestResult> UNAUTHENTICATED_REQUESTS = new ArrayList<>();

    /**
     * Returns the result of all authenticated requests during this JVM runtime.
     *
     * @return the result of all authenticated requests during this JVM runtime
     */
    public ImmutableList<GravatarProfileRequestResult> getAuthenticatedRequestResults() {
        return ImmutableList.copyOf(AUTHENTICATED_REQUESTS);
    }

    /**
     * Returns the result of all unauthenticated requests during this JVM runtime.
     *
     * @return the result of all unauthenticated requests during this JVM runtime
     */
    public ImmutableList<GravatarProfileRequestResult> getUnauthenticatedRequestResults() {
        return ImmutableList.copyOf(UNAUTHENTICATED_REQUESTS);
    }

    /**
     * Reads and returns a serialized object from the Gravatar Profile API.
     *
     * @param bearerToken the authentication token to use; if not provided, only certain fields will be returned
     * @param nameOrHash  the name or SHA256 hash to use
     * @return a profile object
     * @throws NullPointerException     if the provided name or hash is null
     * @throws IllegalArgumentException if the provided name or hash is empty
     */
    GravatarProfile getProfile(byte[] bearerToken, String nameOrHash) {
        Preconditions.checkNotNull(nameOrHash);
        Preconditions.checkArgument(!nameOrHash.trim().isEmpty());

        Instant requestTime = Instant.now();
        AtomicBoolean failed = new AtomicBoolean(true);

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            try (SSLSocket socket = (SSLSocket) factory.createSocket(API_HOST, HTTPS_PORT)) {
                socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
                OutputStream outputStream = socket.getOutputStream();

                String httpRequest = "GET /v" + API_VERSION
                        + "/profiles/" + nameOrHash + " "
                        + "HTTP/1.1" + CRLF
                        + "Host: " + API_HOST
                        + CRLF;

                outputStream.write(encode(httpRequest));
                if (bearerToken != null) {
                    outputStream.write(encode(("Authorization: Bearer ")));
                    outputStream.write(bearerToken);
                }
                outputStream.write(encode(CRLF + CRLF));
                requestTime = Instant.now();
                outputStream.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = ResourceReader.from(br).skipHeaders().readChunkedBody();
                if (response.contains("error")) {
                    JsonObject responseObject = GsonProvider.INSTANCE.get().fromJson(response, JsonObject.class);
                    throw new RuntimeException("Gravatar API error: " + responseObject.get("error").getAsString());
                }

                GravatarProfile ret = GsonProvider.INSTANCE.get().fromJson(response, GravatarProfile.class);
                failed.set(false);
                return ret;
            }
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        } finally {
            GravatarProfileRequestResult result = new GravatarProfileRequestResult(requestTime, !failed.get());
            if (bearerToken == null) UNAUTHENTICATED_REQUESTS.add(result);
            else AUTHENTICATED_REQUESTS.add(result);
        }
    }

    /**
     * Encodes the provided string using the internal character set.
     *
     * @param string the string to encode
     * @return the encoded string
     */
    private byte[] encode(String string) {
        return string.getBytes(ENCODE_CHARSET);
    }
}
