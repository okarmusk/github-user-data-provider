package com.byteoffset.okarmusk.user.data.provider.domain.user;

import lombok.NonNull;

public interface UserService {
    User getUser(@NonNull String login) throws UserException;
}
