package com.appscol.user.persistence.repositories;

import com.appscol.user.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Long, UserEntity> {

    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntityByEmail(String email);
}
