package com.byteoffset.okarmusk.user.data.provider.infrastructure.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_data_get_statistic")
public class UserEntity {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "request_count")
    private int requestCount;
}
