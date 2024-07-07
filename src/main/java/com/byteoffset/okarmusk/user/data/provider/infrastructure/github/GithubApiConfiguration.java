package com.byteoffset.okarmusk.user.data.provider.infrastructure.github;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "github.api")
public class GithubApiConfiguration {
    private String url;
}
