package com.github.natche.gravatarjavaclient.profile;

import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.github.natche.gravatarjavaclient.profile.serialization.GravatarProfile;
import com.github.natche.gravatarjavaclient.utils.GeneralUtils;
import com.github.natche.gravatarjavaclient.utils.ValidationUtils;
import com.google.common.base.Preconditions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class GravatarProfileRequest {
    // todo get request url
    // todo serialize to a file maybe?

    private Supplier<byte[]> tokenSupplier;
    private String hash;

    private GravatarProfileRequest(String hash) {
        this.hash = hash;
    }

    public static GravatarProfileRequest fromHash(String hash) {
        Preconditions.checkNotNull(hash);
        Preconditions.checkArgument(!hash.trim().isEmpty());

        return new GravatarProfileRequest(hash);
    }

    public static GravatarProfileRequest fromEmail(String email) {
        Preconditions.checkNotNull(email);
        Preconditions.checkArgument(!email.trim().isEmpty());
        Preconditions.checkArgument(ValidationUtils.isValidEmailAddress(email));

        return new GravatarProfileRequest(GeneralUtils.emailAddressToProfilesApiHash(email));
    }

    public GravatarProfileRequest setTokenSupplier(Supplier<byte[]> tokenSupplier) {
        Preconditions.checkNotNull(tokenSupplier);
        this.tokenSupplier = tokenSupplier;
        return this;
    }

    /**
     * Returns the SHA256 hash this request will use.
     *
     * @return the SHA256 hash this request will use
     */
    public String getHash() {
        return hash;
    }

    /**
     * Retrieves the profile using the provided email or hash from the
     * Gravatar Profile API using HTTPS as the protocol.
     *
     * @return the GravatarProfile obtained from the API
     * @throws GravatarJavaClientException if an exception occurs when fetching the profile
     */
    public GravatarProfile getProfile() {
        byte[] token = tokenSupplier == null ? null : tokenSupplier.get();
        return GravatarProfileRequestHandler.INSTANCE.getProfile(token, hash);
    }

    public static void main(String[] args) {
        Supplier<byte[]> myTokenSupplier = () -> {
            try {
                Path path = Paths.get("./key.txt");
                return Files.readAllBytes(path);
            } catch (Exception e) {
                return new byte[0];
            }
        };

        // Send GET request using raw socket communication
        GravatarProfile profile = GravatarProfileRequestHandler.INSTANCE.getProfile(myTokenSupplier.get(),
                "c83512d02db256cc5afb78376147ea0f2ea02e6a4e3399b980dea3bef9fc6168");
        System.out.println("Got profile: " + profile);
    }
}
