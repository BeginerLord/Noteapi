package com.appscol.student.persistence.repositories;

import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.user.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByUuid(UUID uuid);

    Optional<StudentEntity> findByUserEntity(UserEntity userEntity);
}
