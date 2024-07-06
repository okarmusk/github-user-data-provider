package com.byteoffset.okarmusk.user.data.provider.domain.user;

import com.byteoffset.okarmusk.user.data.provider.domain.user.calculations.CalculationsService;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiClient;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiException;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUser;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUserNotFoundException;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.repository.UserRequestStatisticEntity;
import com.byteoffset.okarmusk.user.data.provider.infrastructure.repository.UserRequestStatisticRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProxyUserService implements UserService {
    private final GithubApiClient githubApiClient;
    private final CalculationsService calculationsService;
    private final UserRequestStatisticRepository userRequestStatisticRepository;

    @Override
    public Optional<User> getUser(@NonNull String login) throws UserException {
        try {
            var githubUser = githubApiClient.getUser(login);

            if (userRequestStatisticRepository.findByLogin(login).isPresent()) {
                userRequestStatisticRepository.incrementRequestStatistic(login);
            } else {
                userRequestStatisticRepository.save(new UserRequestStatisticEntity(login, 1));
            }

            return Optional.of(mapToUser(githubUser));
        } catch (GithubUserNotFoundException e) {
            return Optional.empty();
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
