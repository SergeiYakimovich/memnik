package com.memnik.dao.user;


import com.memnik.dao.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNameIgnoreCase(String name);
    Optional<UserEntity> findByCode(String code);
    @Modifying
    int deleteAllByEmailConfirmedFalseAndCreatedAtBefore(LocalDateTime minusHours);
    List<UserEntity> findAllByMailingForbiddenFalse();
    List<UserEntity> findAllByMailingForbiddenFalseAndLanguage(String language);
}
