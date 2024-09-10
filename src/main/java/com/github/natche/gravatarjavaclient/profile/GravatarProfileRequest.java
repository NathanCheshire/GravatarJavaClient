package com.github.natche.gravatarjavaclient.profile;

import com.google.common.base.Preconditions;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class GravatarProfileRequest {
    // todo from email
    // todo from hash (sha256)
    // todo get request url
    // todo serialize to a file maybe?

    private Supplier<byte[]> tokenSupplier;

    public void setTokenSupplier(Supplier<byte[]> tokenSupplier) {
        Preconditions.checkNotNull(tokenSupplier);
        this.tokenSupplier = tokenSupplier;
    }

    public static void main(String[] args) throws Exception {
        Supplier<byte[]> myTokenSupplier = () -> {
            try {
                Path path = Paths.get("./key.txt");
                return Files.readAllBytes(path);
            } catch (Exception e) {
                return new byte[0];
            }
        };

        // Send GET request using raw socket communication
        sendGetRequestWithBearerToken(myTokenSupplier.get(), "nathanvcheshire");
    }

    public static void sendGetRequestWithBearerToken(byte[] bearerToken, String nameOrHash) throws Exception {
        int port = 443;  // Port 443 for HTTPS
        String host = "api.gravatar.com";

        // Create an SSL socket to handle HTTPS
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
            // Enable all available cipher suites
            socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());

            // Get the output stream to send the request
            OutputStream outputStream = socket.getOutputStream();

            // Manually construct the HTTP GET request
            String httpRequest = "GET /v3/profiles/" + nameOrHash + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Authorization: Bearer ";

            outputStream.write(httpRequest.getBytes(StandardCharsets.US_ASCII));
            outputStream.write(bearerToken);
            outputStream.write("\r\n\r\n".getBytes(StandardCharsets.US_ASCII));

            // Send
            outputStream.flush();

            // Handle the response: reading from the input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            StringBuilder responseBody = new StringBuilder();

            // Read the headers first
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) break;
            }

            while (true) {
                String chunkSizeLine = in.readLine();
                if (chunkSizeLine == null) break;
                int chunkSize = Integer.parseInt(chunkSizeLine.trim(), 16);

                // End of response
                if (chunkSize == 0) break;

                char[] chunk = new char[chunkSize];
                int bytesRead = in.read(chunk, 0, chunkSize);
                responseBody.append(chunk, 0, bytesRead);
                in.readLine();
            }

            System.out.println(System.getenv("GRAVATAR_JAVA_CLIENT_GITHUB_API_KEY"));
            System.out.println(responseBody);
        }
    }
}
