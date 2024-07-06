package com.byteoffset.okarmusk.user.data.provider.domain.user;

import java.time.ZonedDateTime;

public record User(String id,
                   String login,
                   String name,
                   String type,
                   String avatarUrl,
                   ZonedDateTime createdAt,
                   String calculations) {
}
