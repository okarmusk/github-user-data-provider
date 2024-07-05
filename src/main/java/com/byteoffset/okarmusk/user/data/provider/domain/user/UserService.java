package com.byteoffset.okarmusk.user.data.provider.domain.user;

public interface UserService {
    User getUser(String login) throws UserException;
}
