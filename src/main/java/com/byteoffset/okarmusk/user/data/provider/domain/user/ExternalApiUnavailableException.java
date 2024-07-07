package com.byteoffset.okarmusk.user.data.provider.domain.user;

public class ExternalApiUnavailableException extends RuntimeException {
    public ExternalApiUnavailableException(String message) {
        super(message);
    }
}
