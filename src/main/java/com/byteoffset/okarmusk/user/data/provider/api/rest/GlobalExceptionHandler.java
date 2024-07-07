package com.byteoffset.okarmusk.user.data.provider.api.rest;

import com.byteoffset.okarmusk.user.data.provider.domain.user.ExternalApiUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExternalApiUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleExternalApiUnavailableException(ExternalApiUnavailableException e) {
        log.error("Error while communicating with external API", e);
    }
}
