package com.appscol.schedule.persistence.repositories;

import com.appscol.schedule.persistence.entities.SchedulesEntity;
import com.appscol.schedule.presentation.dto.ScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<SchedulesEntity,Long > {
    @Query("""
    SELECT new com.appscol.schedule.presentation.dto.ScheduleDto(
        s.dia,
        s.horaInicio,
        s.horaFin,
        sec.sectionName,
        sub.subjectName,
        g.grade
    )
    FROM SchedulesEntity s
    JOIN s.sectionsEntity sec
    JOIN sec.gradeEntity g
    JOIN s.subjectEntity sub
    WHERE sub.professorEntity.id = :professorId
""")
    List<ScheduleDto> findScheduleByProfessorId(@Param("professorId") Long professorId);
}
