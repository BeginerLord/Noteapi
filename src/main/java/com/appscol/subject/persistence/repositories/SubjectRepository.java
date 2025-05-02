package com.appscol.subject.persistence.repositories;

import com.appscol.subject.persistence.entities.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Page<SubjectEntity> findBySubjectNameContainingIgnoreCase(String subjectName, Pageable pageable);
    Page<SubjectEntity> findByProfessorEntity_Id(Long professorId, Pageable pageable);
}
