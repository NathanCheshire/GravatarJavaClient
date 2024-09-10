package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.gson.GsonProvider;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.github.natche.gravatarjavaclient.utils.GeneralUtils.HEX_BASE;

/**
 * The Gravatar Profile request handler for requesting profile API requests.
 */
public enum GravatarProfileRequestHandler {
    /**
     * The Gravatar Profile request handler.
     */
    INSTANCE;

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

    // todo unauthenticated requests are limited to 100 an hour, authed are 1000 per hour
    //  feature to track this would be cool

    /**
     * Reads and returns a serialized object from the Gravatar profiles API endpoint.
     *
     * @param bearerToken the authentication token to use; if not provided, only certain fields will be returned
     * @param nameOrHash  the name or SHA256 hash to use
     * @return a profile object
     */
    GravatarProfile getProfile(byte[] bearerToken, String nameOrHash) {
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            try (SSLSocket socket = (SSLSocket) factory.createSocket(API_HOST, HTTPS_PORT)) {
                socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
                OutputStream outputStream = socket.getOutputStream();

                String httpRequest = "GET /v" + API_VERSION + "/profiles/" + nameOrHash + " HTTP/1.1\r\n" +
                        "Host: " + API_HOST + "\r\n" +
                        "Authorization: Bearer ";

                outputStream.write(httpRequest.getBytes(StandardCharsets.US_ASCII));
                outputStream.write(bearerToken);
                outputStream.write("\r\n\r\n".getBytes(StandardCharsets.US_ASCII));
                outputStream.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                StringBuilder responseBody = new StringBuilder();
                while ((line = in.readLine()) != null) if (line.isEmpty()) break;

                // Read the chunked body
                while (true) {
                    String chunkSizeLine = in.readLine();
                    if (chunkSizeLine == null || chunkSizeLine.isEmpty()) break;

                    int chunkSize;
                    try {
                        chunkSize = Integer.parseInt(chunkSizeLine.trim(), HEX_BASE);
                    } catch (NumberFormatException e) {
                        throw new GravatarJavaClientException(e);
                    }

                    // End of chunks
                    if (chunkSize == 0) break;
                    char[] chunk = new char[chunkSize];
                    int totalBytesRead = 0;

                    while (totalBytesRead < chunkSize) {
                        int bytesRead = in.read(chunk, totalBytesRead, chunkSize - totalBytesRead);
                        // End of stream
                        if (bytesRead == -1) break;
                        totalBytesRead += bytesRead;
                    }

                    responseBody.append(chunk, 0, totalBytesRead);
                    in.readLine(); // trailing CRLF after chunk
                }

                return GsonProvider.INSTANCE.get().fromJson(responseBody.toString(), GravatarProfile.class);

            }
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        }
    }

}
