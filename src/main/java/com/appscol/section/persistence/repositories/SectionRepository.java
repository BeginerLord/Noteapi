package com.appscol.section.persistence.repositories;

import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.section.persistence.entities.SectionsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionsEntity, Long> {

    Page<SectionsEntity> findByGradeEntity_Grade(String grade, Pageable pageable);

}
