package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

public class GithubUserNotFoundException extends Exception {
    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
