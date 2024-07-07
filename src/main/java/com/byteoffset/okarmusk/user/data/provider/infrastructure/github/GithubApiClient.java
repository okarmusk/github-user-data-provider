package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

import feign.Param;
import feign.RequestLine;

public interface GithubApiClient {
    @RequestLine("GET /users/{login}")
    GithubUser getUser(@Param("login") String login) throws GithubApiException, GithubUserNotFoundException;
}
