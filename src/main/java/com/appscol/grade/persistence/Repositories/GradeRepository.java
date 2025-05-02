package com.appscol.grade.persistence.Repositories;

import com.appscol.grade.persistence.entities.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    Optional<GradeEntity> findByGrade(String grade);
    boolean existsByGrade(String grade);
}
