package com.appscol.schedule.presentation.dto;

import lombok.Builder;

@Builder
public record ScheduleDto(
        String dia,
        String horaInicio,
        String horaFin,
        String sectionName,
        String subjectName,
        String grade

) {
}
