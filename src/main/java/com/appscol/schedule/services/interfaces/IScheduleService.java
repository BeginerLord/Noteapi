package com.appscol.schedule.services.interfaces;

import com.appscol.schedule.presentation.dto.ScheduleDto;
import com.appscol.schedule.presentation.payload.SchedulePayload;

import java.util.List;
import java.util.UUID;

public interface IScheduleService {
   void createSchedule(SchedulePayload payload);
   void asignarHorarioProfesor(SchedulePayload payload);
   List<ScheduleDto> findScheduleByProfessorUuid(UUID uuid);
   void confirmarHorarioAsignado(UUID profesorUuid, Long horarioId);


}
