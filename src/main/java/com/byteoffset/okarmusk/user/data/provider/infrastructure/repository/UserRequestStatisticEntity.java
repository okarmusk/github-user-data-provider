package com.byteoffset.okarmusk.user.data.provider.infrastructure.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_request_statistic")
public class UserRequestStatisticEntity {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "request_count")
    private int requestCount;
}
