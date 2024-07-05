package com.byteoffset.okarmusk.user.data.provider.domain.user.calculations;

public interface CalculationsService {
    double calculate(int numberOfFollowers, int numberOfRepositories) throws IllegalArgumentException;
}
