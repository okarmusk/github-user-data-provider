package com.byteoffset.okarmusk.user.data.provider.domain.user.calculations


import spock.lang.Specification

class BigBrainTimeCalculationsServiceTest extends Specification {
    private BigBrainTimeCalculationsService service

    def setup() {
        service = new BigBrainTimeCalculationsService()
    }

    // I assumed that when numberOfFollowers equals 0 then method should return 0
    def "should return 0 when no followers and no public repositories"() {
        when:
        def result = service.calculate(0, 0)

        then:
        result == 0
    }

    def "should return result bigger by B when numberOfFollowers equals A"(int numberOfPublicRepositories) {
        given:
        def expected = BigBrainTimeCalculationsService.B + numberOfPublicRepositories

        when:
        def result = service.calculate(BigBrainTimeCalculationsService.A as int, numberOfPublicRepositories)

        then:
        result == expected

        where:
        numberOfPublicRepositories      | _
        1                               | _
        2                               | _
        3                               | _
        4                               | _
        5                               | _
    }

    def "should return expected result for predefined set of valid numberOfFollowers and numberOfPublicRepositories"(int numberOfFollowers, int numberOfPublicRepositories, double expected) {
        when:
        def result = service.calculate(numberOfFollowers, numberOfPublicRepositories)

        then:
        result == expected

        where:
        numberOfFollowers           | numberOfPublicRepositories   | expected
        0                           | 10                           | 0.0
        1                           | 1                            | 18.0
        2                           | 10                           | 36.0
        888                         | 8                            | 0.06756756756756757
    }

    def "should throw exception when numberOfFollowers is negative"() {
        when:
        service.calculate(-1, 0)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Number of followers cannot be negative"
    }

    def "should throw exception when numberOfPublicRepositories is negative"() {
        when:
        service.calculate(0, -1)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Number of public repositories cannot be negative"
    }
}
