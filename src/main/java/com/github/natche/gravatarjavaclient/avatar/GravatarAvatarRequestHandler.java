package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarForceDefaultImage;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.enums.GravatarUseFullUrlParameters;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;

/**
 * A handler for constructing the Avatar request URL.
 */
enum GravatarAvatarRequestHandler {
    /**
     * The Gravatar Avatar request handler singleton instance.
     */
    INSTANCE;

    /**
     * The string to accompany {@link GravatarUrlParameter#FORCE_DEFAULT} to indicate the default URL
     * should be used regardless of the validity of the user account.
     */
    private static final String FORCE_DEFAULT_URL_TRUE_STRING = "y";

    /**
     * Builds the provided Gravatar image request into a URL representing the state of the builder.
     *
     * @param gravatarImageRequestBuilder the builder to construct the URL from
     * @return the built URL
     */
    String buildUrl(GravatarAvatarRequest gravatarImageRequestBuilder) {
        Preconditions.checkNotNull(gravatarImageRequestBuilder);

        GravatarUseFullUrlParameters fullParams = gravatarImageRequestBuilder.getUseFullUrlParameters();

        StringBuilder urlBuilder =
                new StringBuilder(gravatarImageRequestBuilder.getProtocol().getAvatarRequestBaseurl());
        urlBuilder.append(gravatarImageRequestBuilder.getShouldAppendJpgSuffix().getSuffix());

        String size = String.valueOf(gravatarImageRequestBuilder.getSize());
        String sizeParameter = GravatarUrlParameter.SIZE.constructUrlParameterWithValue(
                size, true, fullParams);
        urlBuilder.append(sizeParameter);

        String ratingsParameter = GravatarUrlParameter.RATING.constructUrlParameterWithValue(
                gravatarImageRequestBuilder.getRating().getUrlParameter(), fullParams);
        urlBuilder.append(ratingsParameter);

        GravatarDefaultImageType defaultImageType = gravatarImageRequestBuilder.getDefaultImageType();
        String defaultImageUrl = gravatarImageRequestBuilder.getDefaultImageUrl();

        if (defaultImageType != null) {
            String defaultImageTypeQueryString = GravatarUrlParameter.DEFAULT_IMAGE_TYPE
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), fullParams);
            urlBuilder.append(defaultImageTypeQueryString);
        } else if (defaultImageUrl != null) {
            String defaultImageUrlQueryString = GravatarUrlParameter.DEFAULT_IMAGE_URL
                    .constructUrlParameterWithValue(defaultImageUrl, fullParams);
            urlBuilder.append(defaultImageUrlQueryString);
        }

        if (gravatarImageRequestBuilder.shouldForceDefaultImage() == GravatarForceDefaultImage.DoNotForce) {
            return urlBuilder.toString();
        }

        if (defaultImageUrl == null) {
            throw new GravatarJavaClientException("Must provide default URL if forcing default");
        }

        String forceDefaultImageQueryString = GravatarUrlParameter.FORCE_DEFAULT
                .constructUrlParameterWithValue(FORCE_DEFAULT_URL_TRUE_STRING, fullParams);
        urlBuilder.append(forceDefaultImageQueryString);

        return urlBuilder.toString();
    }
}
