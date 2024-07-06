package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

import feign.codec.ErrorDecoder;

public class GithubErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, feign.Response response) {
        if (response.status() == 404) {
            return new GithubUserNotFoundException("User not found");
        }

        if (response.status() != 200) {
            return new GithubApiException("Cannot reach github API, returned response status: " + response.status());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
