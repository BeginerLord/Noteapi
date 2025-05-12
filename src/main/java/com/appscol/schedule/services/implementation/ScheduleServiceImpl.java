package com.appscol.schedule.services.implementation;

import com.appscol.helpers.exception.exceptions.ConflictException;
import com.appscol.helpers.exception.exceptions.ForbiddenException;
import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.schedule.persistence.entities.SchedulesEntity;
import com.appscol.schedule.persistence.repositories.ScheduleRepository;
import com.appscol.schedule.presentation.dto.ScheduleDto;
import com.appscol.schedule.presentation.payload.SchedulePayload;
import com.appscol.schedule.services.interfaces.IScheduleService;
import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.section.persistence.repositories.SectionRepository;
import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.subject.persistence.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {

    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional()
    public void createSchedule(SchedulePayload payload) {

        SectionsEntity section = sectionRepository.findById(payload.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        SubjectEntity subject = subjectRepository.findById(payload.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

// no usar ModelMapper pq ve sectionId y subjectId, ambos tipo Long, y cree erróneamente que alguno puede coincidir con SchedulesEntity.id.
      //  SchedulesEntity schedules = modelMapper.map(payload, SchedulesEntity.class);

        SchedulesEntity schedules = SchedulesEntity.builder()
                .dia(payload.getDia().trim())
                .horaInicio(payload.getHoraInicio().trim())
                .horaFin(payload.getHoraFin().trim())
                .sectionsEntity(section)
                .subjectEntity(subject)
                .build();
        scheduleRepository.save(schedules);

    }

    @Override
    @Transactional
    public void asignarHorarioProfesor(SchedulePayload payload) {

        SectionsEntity section = sectionRepository.findById(payload.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada"));

        SubjectEntity subject = subjectRepository.findById(payload.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));

        UUID professorUuid = subject.getProfessorEntity().getUuid();
        List<ScheduleDto> conflicts = scheduleRepository.findConflictingHorarios(
                professorUuid,
                payload.getDia().trim(),
                payload.getHoraInicio().trim(),
                payload.getHoraFin().trim()
        );

        if (!conflicts.isEmpty()) {
            throw new ConflictException("El profesor ya tiene clases asignadas en ese horario");
        }

        SchedulesEntity schedules = SchedulesEntity.builder()
                .dia(payload.getDia().trim())
                .horaInicio(payload.getHoraInicio().trim())
                .horaFin(payload.getHoraFin().trim())
                .sectionsEntity(section)
                .subjectEntity(subject)
                .build();

        scheduleRepository.save(schedules);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDto> findScheduleByProfessorUuid(UUID uuid) {
        return scheduleRepository.findScheduleByProfessorUuid(uuid);
    }

    @Override
    public void confirmarHorarioAsignado(UUID profesorUuid, Long horarioId) {
        SchedulesEntity horario = scheduleRepository.findById(horarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));

        // Verificar que el profesor sea el dueño del horario
        UUID horarioProfesorUuid = horario.getSubjectEntity().getProfessorEntity().getUuid();
        if (!horarioProfesorUuid.equals(profesorUuid)) {
            throw new ForbiddenException("No puedes confirmar un horario que no te pertenece");
        }

        horario.setConfirmado(true);
        scheduleRepository.save(horario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDto> findScheduleByStudentUuid(UUID studentUuid) {
        return scheduleRepository.findScheduleByStudentUuid(studentUuid);
    }

}
