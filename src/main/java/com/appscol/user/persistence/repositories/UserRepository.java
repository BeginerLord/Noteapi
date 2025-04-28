package com.appscol.user.persistence.repositories;

import com.appscol.user.persistence.entities.UserEntity;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntityByEmail(String email);
    Optional<UserEntity> findByUuid(UUID uuid);

    boolean existsByUsername(String username);

    boolean existsByEmail(@Email String email);
}
