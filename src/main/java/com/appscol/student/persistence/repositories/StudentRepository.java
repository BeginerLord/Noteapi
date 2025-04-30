package com.appscol.student.persistence.repositories;

import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.user.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findById(Long id);
    
    Page<StudentEntity> findAllBy(Pageable pageable);
    
    Optional<StudentEntity> findByUserEntity(UserEntity userEntity);
}
