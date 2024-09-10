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

    private static final int HTTPS_PORT = 443;
    private static final String API_HOST = "api.gravatar.com";

    // todo unauthenticated requests are limited to 100 an hour, authed are 1000 per hour
    //  feature to track this would be cool

    GravatarProfile getProfile(byte[] bearerToken, String nameOrHash) {
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            try (SSLSocket socket = (SSLSocket) factory.createSocket(API_HOST, HTTPS_PORT)) {
                socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
                OutputStream outputStream = socket.getOutputStream();

                // Manually construct the HTTP GET request
                String httpRequest = "GET /v3/profiles/" + nameOrHash + " HTTP/1.1\r\n" +
                        "Host: " + API_HOST + "\r\n" +
                        "Authorization: Bearer ";

                outputStream.write(httpRequest.getBytes(StandardCharsets.US_ASCII));
                outputStream.write(bearerToken);
                outputStream.write("\r\n\r\n".getBytes(StandardCharsets.US_ASCII));
                outputStream.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                StringBuilder responseBody = new StringBuilder();

                // Read headers
                while ((line = in.readLine()) != null) {
                    if (line.isEmpty()) break; // Headers end with an empty line
                }

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

                    // End of the chunks
                    if (chunkSize == 0) break;

                    char[] chunk = new char[chunkSize];
                    int totalBytesRead = 0;

                    // Read the chunk data fully
                    while (totalBytesRead < chunkSize) {
                        int bytesRead = in.read(chunk, totalBytesRead, chunkSize - totalBytesRead);
                        if (bytesRead == -1) break; // End of stream
                        totalBytesRead += bytesRead;
                    }

                    responseBody.append(chunk, 0, totalBytesRead);
                    in.readLine(); // Read the trailing CRLF after each chunk
                }

                // Debug: print the full response
                System.out.println(responseBody);

                // Convert the response body to GravatarProfile
                return GsonProvider.INSTANCE.get().fromJson(responseBody.toString(), GravatarProfile.class);

            }
        } catch (Exception e) {
            throw new GravatarJavaClientException(e);
        }
    }

}
