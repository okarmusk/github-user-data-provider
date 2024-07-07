package com.byteoffset.okarmusk.user.data.provider.api.rest;

import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiClient;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiException;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUser;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private final static String GITHUB_LOGIN = "okarmusk";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GithubApiClient githubApiClient;

    @Test
    void shouldReturnUserIfUserExistsInGithub() throws Exception {
        when(githubApiClient.getUser(GITHUB_LOGIN))
                .thenReturn(createGithubUser(GITHUB_LOGIN));

        var url = String.format("/users/%s", GITHUB_LOGIN);
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(GITHUB_LOGIN)));
    }

    @Test
    void shouldReturn404IfUserDoesNotExistsInGithub() throws Exception {
        when(githubApiClient.getUser(any()))
                .thenThrow(GithubUserNotFoundException.class);

        var url = String.format("/users/%s_invalid_user", GITHUB_LOGIN);
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn503IfGithubApiExceptionAppear() throws Exception {
        when(githubApiClient.getUser(any()))
                .thenThrow(GithubApiException.class);

        var url = String.format("/users/%s", GITHUB_LOGIN);
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }

    private static GithubUser createGithubUser(String login) {
        return new GithubUser(
                32,
                login,
                "test user",
                "User",
                "",
                ZonedDateTime.parse("2024-01-01T00:00:00Z"),
                1,
                1
        );
    }
}
