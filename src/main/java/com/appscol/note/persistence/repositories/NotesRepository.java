package com.appscol.note.persistence.repositories;

import com.appscol.note.persistence.entities.NotesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotesRepository extends JpaRepository<NotesEntity, Long> {
    @Query("""
    SELECT n FROM NotesEntity n
    JOIN n.subjectEntity s
    JOIN s.professorEntity p
    WHERE p.uuid = :professorUuid
""")
    Page<NotesEntity> findByProfessorUuid(Pageable pageable, @Param("professorUuid") UUID professorUuid);

    @Query("""
    SELECT n FROM NotesEntity n
    JOIN n.studentEntity s
    WHERE s.uuid = :studentUuid
""")
    Page<NotesEntity> findByStudentUuid(Pageable pageable, @Param("studentUuid") UUID studentUuid);

    boolean existsByIdAndStudentEntityUuid(Long id, UUID studentUuid);
}
