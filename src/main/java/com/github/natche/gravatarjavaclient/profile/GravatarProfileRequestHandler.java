package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfileRequestResult;
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

import static com.github.natche.gravatarjavaclient.utils.GeneralUtils.HEX_BASE;
import static com.github.natche.gravatarjavaclient.utils.GeneralUtils.skipHeaders;

/**
 * The Gravatar Profile request handler for requesting profile API requests.
 */
public enum GravatarProfileRequestHandler {
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

    private static final String RETURN_NEWLINE = "\r\n";

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
     * Reads and returns a serialized object from the Gravatar profiles API endpoint.
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
                        + "HTTP/1.1" + RETURN_NEWLINE
                        + "Host: " + API_HOST
                        + RETURN_NEWLINE;

                outputStream.write(encode(httpRequest));
                if (bearerToken != null) {
                    outputStream.write(encode(("Authorization: Bearer ")));
                    outputStream.write(bearerToken);
                }
                outputStream.write(encode(RETURN_NEWLINE + RETURN_NEWLINE));
                outputStream.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                skipHeaders(br);

                // Read the chunked body
                StringBuilder responseBody = new StringBuilder();
                while (true) {
                    String chunkSizeLine = br.readLine();
                    if (chunkSizeLine.isEmpty()) break;

                    int chunkSize = Integer.parseInt(chunkSizeLine.trim(), HEX_BASE);
                    // End of chunks
                    if (chunkSize == 0) break;
                    char[] chunk = new char[chunkSize];
                    int totalBytesRead = 0;

                    while (totalBytesRead < chunkSize) {
                        int bytesRead = br.read(chunk, totalBytesRead, chunkSize - totalBytesRead);
                        // End of stream
                        if (bytesRead == -1) break;
                        totalBytesRead += bytesRead;
                    }

                    responseBody.append(chunk, 0, totalBytesRead);
                    br.readLine(); // trailing CRLF after chunk
                }

                String response = responseBody.toString();
                if (response.contains("error")) {
                    JsonObject responseObject = GsonProvider.INSTANCE.get()
                            .fromJson(responseBody.toString(), JsonObject.class);
                    throw new RuntimeException("API error: " + responseObject.get("error").getAsString());
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
     * Encodes the provided string using the internal char set.
     *
     * @param string the string to encode
     * @return the encoded string
     */
    private byte[] encode(String string) {
        return string.getBytes(ENCODE_CHARSET);
    }
}
