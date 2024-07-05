package com.byteoffset.okarmusk.user.data.provider.domain.user;

public record User(String id,
                   String login,
                   String name,
                   String type,
                   String avatarUrl,
                   String createdAt,
                   String calculations) {
}
