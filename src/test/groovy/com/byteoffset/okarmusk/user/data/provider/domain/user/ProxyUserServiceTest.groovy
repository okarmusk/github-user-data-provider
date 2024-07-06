package com.byteoffset.okarmusk.user.data.provider.domain.user

import com.byteoffset.okarmusk.user.data.provider.domain.user.calculations.CalculationsService
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiClient
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubApiException
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUser
import com.byteoffset.okarmusk.user.data.provider.infrastructure.github.GithubUserNotFoundException
import com.byteoffset.okarmusk.user.data.provider.infrastructure.repository.UserRequestStatisticEntity
import com.byteoffset.okarmusk.user.data.provider.infrastructure.repository.UserRequestStatisticRepository
import spock.lang.Specification

import java.time.ZonedDateTime

class ProxyUserServiceTest extends Specification {
    private static final String LOGIN = "test"

    private GithubApiClient githubApiClientMock
    private CalculationsService calculationsServiceMock
    private UserRequestStatisticRepository userRequestStatisticRepositoryMock
    private ProxyUserService service

    def setup() {
        githubApiClientMock = Mock(GithubApiClient)
        calculationsServiceMock = Mock(CalculationsService)
        userRequestStatisticRepositoryMock = Mock(UserRequestStatisticRepository)
        service = new ProxyUserService(githubApiClientMock, calculationsServiceMock, userRequestStatisticRepositoryMock)
    }

    def "should return no user when api response is empty"() {
        given:
        1 * githubApiClientMock.getUser(LOGIN) >> {
            throw new GithubUserNotFoundException("User not found")
        }

        when:
        def optional = service.getUser(LOGIN)

        then:
        optional.isEmpty()
    }

    def "should return expected user when api returned response with user"() {
        given:
        GithubUser githubUser = createGithubUser(LOGIN)
        1 * githubApiClientMock.getUser(LOGIN) >> githubUser
        1 * userRequestStatisticRepositoryMock.findByLogin(LOGIN) >> Optional.empty()
        double expectedCalculations = 123.0
        1 * calculationsServiceMock.calculate(githubUser.followers, githubUser.publicRepos) >> expectedCalculations

        when:
        def optional = service.getUser(LOGIN)

        then:
        optional.isPresent()
        def user = optional.get()
        user.login() == LOGIN
        user.id() == String.valueOf(githubUser.id)
        user.name() == githubUser.name
        user.type() == githubUser.type
        user.avatarUrl() == githubUser.avatarUrl
        user.createdAt() == githubUser.createdAt
        user.calculations() == String.valueOf(expectedCalculations)
        1 * userRequestStatisticRepositoryMock.save(new UserRequestStatisticEntity(LOGIN, 1))
    }

    def "should throw exception when communication with api appeared"() {
        given:
        1 * githubApiClientMock.getUser(LOGIN) >> {
            throw new GithubApiException("Unhandled response from Github API")
        }

        when:
        service.getUser(LOGIN)

        then:
        thrown(UserException)
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
        )
    }
}
