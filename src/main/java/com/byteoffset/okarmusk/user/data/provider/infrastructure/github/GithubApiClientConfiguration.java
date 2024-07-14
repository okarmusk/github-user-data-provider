package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class GithubApiClientConfiguration {
    private final static int MAX_ATTEMPTS = 4;
    private final GithubApiConfiguration githubApiConfiguration;

    @Bean
    public GithubApiClient githubApiClient() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("GithubApiClient");
        RateLimiter rateLimiter = RateLimiter.ofDefaults("GithubApiClient");
        FeignDecorators decorators = FeignDecorators.builder()
                .withRateLimiter(rateLimiter)
                .withCircuitBreaker(circuitBreaker)
                .build();

        return Resilience4jFeign.builder(decorators)
                .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
                .errorDecoder(new GithubErrorDecoder())
                .retryer(new Retryer.Default(1000, 3000, MAX_ATTEMPTS))
                .target(GithubApiClient.class, githubApiConfiguration.getUrl());
    }
}
