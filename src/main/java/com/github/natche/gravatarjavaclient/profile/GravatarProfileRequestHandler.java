package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.github.natche.gravatarjavaclient.utils.ResourceReader;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            try (SSLSocket socket = (SSLSocket) factory.createSocket(API_HOST, HTTPS_PORT)) {
                socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
                OutputStream outputStream = socket.getOutputStream();

                // ToDo: this should probably be a more standard pattern
                String httpRequest = "GET /v" + API_VERSION
                        + "/profiles/" + nameOrHash + " "
                        + "HTTP/1.1" + CRLF
                        + "Host: " + API_HOST
                        + CRLF;

                outputStream.write(encode(httpRequest));
                if (token != null) {
                    outputStream.write(encode(("Authorization: Bearer " + token)));
                }
                outputStream.write(encode(CRLF + CRLF));
                outputStream.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = ResourceReader.from(br).skipHeaders().readChunkedBody();
                if (response.contains("error")) {
                    JsonObject responseObject = GsonProvider.INSTANCE.get().fromJson(response, JsonObject.class);
                    throw new RuntimeException("Gravatar API error: " + responseObject.get("error").getAsString());
                }

                return GsonProvider.INSTANCE.get().fromJson(response, GravatarProfile.class);
            }
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        } finally {
            if (token == null) unauthenticatedRequestCount.incrementAndGet();
            else authenticatedRequestCount.incrementAndGet();
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
