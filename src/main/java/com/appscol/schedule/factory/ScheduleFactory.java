package com.appscol.schedule.factory;

import com.appscol.schedule.persistence.entities.SchedulesEntity;
import com.appscol.schedule.presentation.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleFactory {
    private final ModelMapper modelMapper;

    public ScheduleDto scheduleDto(SchedulesEntity schedules){
        return ScheduleDto.builder()
                .dia(schedules.getDia())
                .horaInicio(schedules.getHoraInicio())
                .horaFin(schedules.getHoraFin())
                .sectionName(schedules.getSectionsEntity().getSectionName())
                .subjectName(schedules.getSubjectEntity().getSubjectName())
                .grade(schedules.getSectionsEntity().getGradeEntity().getGrade())
                .professorUsername(schedules.getSubjectEntity().getProfessorEntity().getUserEntity().getUsername())
                .build();
    }
}
