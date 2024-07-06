package com.byteoffset.okarmusk.user.data.provider.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRequestStatisticRepository extends JpaRepository<UserRequestStatisticEntity, String> {
    Optional<UserRequestStatisticEntity> findByLogin(String login);

    @Transactional
    @Query(value = "UPDATE UserRequestStatisticEntity SET requestCount = requestCount + 1 WHERE login = :login")
    @Modifying
    int incrementRequestStatistic(String login);
}
