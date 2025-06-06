package com.appscol.professor.persistence.repositories;

import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.user.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    Page<ProfessorEntity> findByEspecialidad (String especialidad,Pageable pageable);

    Optional<ProfessorEntity>findByUuid(UUID uuid);

    Optional<ProfessorEntity> findByUserEntity(UserEntity userEntity);
}
