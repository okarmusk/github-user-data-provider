package com.byteoffset.okarmusk.user.data.provider.domain.user;

import com.byteoffset.okarmusk.user.data.provider.domain.user.calculations.CalculationsService;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiClient;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiException;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GithubProxyUserService implements UserService {
    private final GithubApiClient githubApiClient;
    private final CalculationsService calculationsService;

    @Override
    public User getUser(@NonNull String login) throws UserException {
        try {
            var githubUser = githubApiClient.getUser(login);
            // TODO: save to database

            return mapToUser(githubUser);
        } catch (GithubApiException e) {
            throw new UserException(e.getMessage());
        }
    }

    private User mapToUser(GithubUser githubUser) {
        var calculations = calculationsService.calculate(githubUser.getPublicRepos(), githubUser.getFollowers());

        return new User(
                String.valueOf(githubUser.getId()),
                githubUser.getLogin(),
                githubUser.getName(),
                githubUser.getType(),
                githubUser.getAvatarUrl(),
                githubUser.getCreatedAt(),
                String.valueOf(calculations)
        );
    }
}
