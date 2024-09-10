package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.TestingImageUrls;
import com.github.natche.gravatarjavaclient.enums.GravatarProtocol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for the avatar request handler.
 */
public class GravatarAvatarRequestHandlerTest {
    /**
     * Tests building a URL with a default image URL.
     */
    @Test
    public void testBuildUrlWithDefaultImageUrl() {
        GravatarAvatarRequest request = GravatarAvatarRequest.fromHash("hash")
                .setProtocol(GravatarProtocol.HTTPS)
                .setSize(200)
                .setDefaultImageUrl(TestingImageUrls.foreignImageUrl);

        assertDoesNotThrow(() -> GravatarAvatarRequestHandler.INSTANCE.buildUrl(request));
    }
}
