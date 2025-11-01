package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarForceDefaultImage;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.enums.GravatarUseFullUrlParameters;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;

/**
 * A handler for constructing A Gravatar Avatar request URL.
 */
enum GravatarAvatarRequestHandler {
    /**
     * The Gravatar Avatar request handler singleton instance.
     */
    INSTANCE;

    /**
     * The string to accompany {@link GravatarUrlParameter#ForceDefault} to indicate the default URL
     * should be used regardless of the validity of the Gravatar account.
     */
    private static final String FORCE_DEFAULT_URL_TRUE_STRING = "y";

    /**
     * Builds the provided Gravatar Avatar request into a URL representing the state of the builder.
     *
     * @param gravatarAvatarRequest the request to construct the URL from
     * @return the built URL
     * @throws NullPointerException        if the provided request is null
     * @throws GravatarJavaClientException if force default image is enabled yet no default image URL is provided
     */
    String buildUrl(GravatarAvatarRequest gravatarAvatarRequest) {
        Preconditions.checkNotNull(gravatarAvatarRequest);

        GravatarUseFullUrlParameters fullParams = gravatarAvatarRequest.getUseFullUrlParameters();

        StringBuilder urlBuilder =
                new StringBuilder(gravatarAvatarRequest.getProtocol().getAvatarRequestBaseurl());
        urlBuilder.append(gravatarAvatarRequest.getHash());
        urlBuilder.append(gravatarAvatarRequest.getShouldAppendJpgSuffix().getSuffix());

        String size = String.valueOf(gravatarAvatarRequest.getSize());
        String sizeParameter = GravatarUrlParameter.Size.constructUrlParameterWithValue(
                size, true, fullParams);
        urlBuilder.append(sizeParameter);

        String ratingsParameter = GravatarUrlParameter.Rating.constructUrlParameterWithValue(
                gravatarAvatarRequest.getRating().getUrlParameter(), fullParams);
        urlBuilder.append(ratingsParameter);

        GravatarDefaultImageType defaultImageType = gravatarAvatarRequest.getDefaultImageType();
        String defaultImageUrl = gravatarAvatarRequest.getDefaultImageUrl();

        if (defaultImageType != null) {
            String defaultImageTypeQueryString = GravatarUrlParameter.DefaultImageType
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), fullParams);
            urlBuilder.append(defaultImageTypeQueryString);
        } else {
            String defaultImageUrlQueryString = GravatarUrlParameter.DefaultImageUrl
                    .constructUrlParameterWithValue(defaultImageUrl, fullParams);
            urlBuilder.append(defaultImageUrlQueryString);
        }

        if (gravatarAvatarRequest.shouldForceDefaultImage() == GravatarForceDefaultImage.DoNotForce) {
            return urlBuilder.toString();
        }

        if (defaultImageUrl == null) {
            throw new GravatarJavaClientException("Must provide default URL if forcing default");
        }

        String forceDefaultImageQueryString = GravatarUrlParameter.ForceDefault
                .constructUrlParameterWithValue(FORCE_DEFAULT_URL_TRUE_STRING, fullParams);
        urlBuilder.append(forceDefaultImageQueryString);

        return urlBuilder.toString();
    }
}
