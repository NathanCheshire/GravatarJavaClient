package com.github.natche.gravatarjavaclient.avatar;

import com.github.natche.gravatarjavaclient.enums.GravatarDefaultImageType;
import com.github.natche.gravatarjavaclient.enums.GravatarForceDefaultImage;
import com.github.natche.gravatarjavaclient.enums.GravatarUrlParameter;
import com.github.natche.gravatarjavaclient.enums.GravatarUseFullUrlParameters;
import com.github.natche.gravatarjavaclient.exceptions.GravatarJavaClientException;
import com.google.common.base.Preconditions;

/**
 * A singleton for constructing request URLs from {@link GravatarAvatarRequest}s.
 */
enum GravatarAvatarRequestHandler {
    /**
     * The singleton instance.
     */
    INSTANCE;

    /**
     * The string to accompany {@link GravatarUrlParameter#ForceDefault} to indicate the default URL
     * should be used regardless of the validity of the Gravatar account.
     */
    private static final String FORCE_DEFAULT_URL_TRUE_STRING = "y";

    /**
     * Transforms the provided request into a URL representing the state of it.
     *
     * @param request the request to construct the URL from
     * @return the constructed URL
     * @throws NullPointerException        if the provided request is null
     * @throws GravatarJavaClientException if force default image is enabled yet no default image URL is provided
     */
    String buildUrl(GravatarAvatarRequest request) {
        Preconditions.checkNotNull(request);

        GravatarUseFullUrlParameters fullParams = request.getUseFullUrlParameters();

        StringBuilder urlBuilder =
                new StringBuilder(request.getProtocol().getAvatarRequestBaseurl());
        urlBuilder.append(request.getHash());
        urlBuilder.append(request.getShouldAppendJpgSuffix().getSuffix());

        String size = String.valueOf(request.getSize());
        String sizeParameter = GravatarUrlParameter.Size.constructUrlParameterWithValue(
                size, true, fullParams);
        urlBuilder.append(sizeParameter);

        String ratingsParameter = GravatarUrlParameter.Rating.constructUrlParameterWithValue(
                request.getRating().getUrlParameter(), fullParams);
        urlBuilder.append(ratingsParameter);

        GravatarDefaultImageType defaultImageType = request.getDefaultImageType();
        String defaultImageUrl = request.getDefaultImageUrl();

        if (defaultImageType != null) {
            String defaultImageTypeQueryString = GravatarUrlParameter.DefaultImageType
                    .constructUrlParameterWithValue(defaultImageType.getUrlParameterValue(), fullParams);
            urlBuilder.append(defaultImageTypeQueryString);
        } else {
            String defaultImageUrlQueryString = GravatarUrlParameter.DefaultImageUrl
                    .constructUrlParameterWithValue(defaultImageUrl, fullParams);
            urlBuilder.append(defaultImageUrlQueryString);
        }

        if (request.shouldForceDefaultImage() == GravatarForceDefaultImage.DoNotForce) {
            return urlBuilder.toString();
        }

        if (defaultImageUrl == null) {
            throw new GravatarJavaClientException("You must provide a default image URL if forcing default");
        }

        String forceDefaultImageQueryString = GravatarUrlParameter.ForceDefault
                .constructUrlParameterWithValue(FORCE_DEFAULT_URL_TRUE_STRING, fullParams);
        urlBuilder.append(forceDefaultImageQueryString);

        return urlBuilder.toString();
    }
}
