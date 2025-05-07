package com.appscol.professor.service.interfaces;

import com.appscol.professor.presentation.dto.CargaAcademicaDto;
import com.appscol.professor.presentation.dto.ProfessorDto;
import com.appscol.professor.presentation.payload.ProfessorPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProfessorService {

    void save(ProfessorPayload payload);
    void update(ProfessorPayload payload, UUID uuid);
    void  deleteByUuid(UUID uuid);
    Page<ProfessorDto> findByEspecialidad(String especialidad, Pageable pageable);
    Page<ProfessorDto>findAll (Pageable pageable);
    ProfessorDto findByUuid(UUID uuid);
    boolean confirmarDisponibilidadProfesor(UUID professorUuid, String dia, String horaInicio, String horaFin);
    List<CargaAcademicaDto> obtenerCargaAcademica(UUID profesorUuid);

}
