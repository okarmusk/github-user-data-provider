package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "github.api")
public class GithubUserApiConfiguration {
    private final String url;
    private final String usersEndpoint;
}
