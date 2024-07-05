package com.byteoffset.okarmusk.user.data.provider.domain.user.calculations;

import org.springframework.stereotype.Service;

@Service
public class EmpikTechCalculationsService implements CalculationsService {
    public static final double A = 6.0;
    public static final int B = 2;

    @Override
    public double calculate(int numberOfFollowers, int numberOfPublicRepositories) throws IllegalArgumentException {
        validate(numberOfFollowers, numberOfPublicRepositories);

        if (numberOfFollowers == 0) {
            return 0.0;
        }

        return A / numberOfFollowers * (B + numberOfPublicRepositories);
    }

    private void validate(int numberOfFollowers, int numberOfPublicRepositories) {
        validateNumberOfFollowers(numberOfFollowers);
        validateNumberOfPublicRepositories(numberOfPublicRepositories);
    }

    private void validateNumberOfFollowers(int numberOfFollowers) {
        if (numberOfFollowers < 0) {
            throw new IllegalArgumentException("Number of followers cannot be negative");
        }
    }

    private void validateNumberOfPublicRepositories(int numberOfPublicRepositories) {
        if (numberOfPublicRepositories < 0) {
            throw new IllegalArgumentException("Number of public repositories cannot be negative");
        }
    }
}
