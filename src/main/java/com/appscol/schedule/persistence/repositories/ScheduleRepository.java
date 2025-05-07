package com.appscol.schedule.persistence.repositories;

import com.appscol.schedule.persistence.entities.SchedulesEntity;
import com.appscol.schedule.presentation.dto.ScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<SchedulesEntity, Long> {
    @Query("""
            SELECT new com.appscol.schedule.presentation.dto.ScheduleDto(
                s.dia,
                s.horaInicio,
                s.horaFin,
                sec.sectionName,
                sub.subjectName,
                g.grade,
                sub.professorEntity.userEntity.username
            )
            FROM SchedulesEntity s
            JOIN s.sectionsEntity sec
            JOIN sec.gradeEntity g
            JOIN s.subjectEntity sub
            WHERE sub.professorEntity.uuid = :professorUuid
            """)
    List<ScheduleDto> findScheduleByProfessorUuid(@Param("professorUuid") UUID professorUuid);

    @Query("""
                SELECT new com.appscol.schedule.presentation.dto.ScheduleDto(
                    s.dia,
                    s.horaInicio,
                    s.horaFin,
                    sec.sectionName,
                    sub.subjectName,
                    g.grade,
                    sub.professorEntity.userEntity.username
                )
                FROM SchedulesEntity s
                JOIN s.sectionsEntity sec
                JOIN sec.gradeEntity g
                JOIN s.subjectEntity sub
                WHERE sub.professorEntity.uuid = :professorUuid
                AND s.dia = :dia
                AND (
                    (CONCAT(:horaInicio, ' ', CURRENT_DATE) BETWEEN s.horaInicio AND s.horaFin OR
                    CONCAT(:horaFin, ' ', CURRENT_DATE) BETWEEN s.horaInicio AND s.horaFin)
                )
            """)
    List<ScheduleDto> findConflictingHorarios(
            @Param("professorUuid") UUID professorUuid,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin
    );

    @Query("SELECT s.subjectEntity.subjectName, COUNT(s) FROM SchedulesEntity s " +
            "WHERE s.subjectEntity.professorEntity.uuid = :profesorUuid " +
            "GROUP BY s.subjectEntity.subjectName")
    List<Object[]> obtenerCargaAcademicaPorProfesor(UUID profesorUuid);

}
