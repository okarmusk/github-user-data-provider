package com.byteoffset.okarmusk.user.data.provider.domain.user;

import lombok.NonNull;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(@NonNull String login);
}
