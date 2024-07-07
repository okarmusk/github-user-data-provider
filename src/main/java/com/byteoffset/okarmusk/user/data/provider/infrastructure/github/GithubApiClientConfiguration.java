package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class GithubApiClientConfiguration {
    private final GithubApiConfiguration githubApiConfiguration;

    @Bean
    public GithubApiClient githubApiClient() {
        return Feign.builder()
                .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
                .errorDecoder(new GithubErrorDecoder())
                .target(GithubApiClient.class, githubApiConfiguration.getUrl());
    }
}
