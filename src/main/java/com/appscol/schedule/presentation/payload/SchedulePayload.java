package com.appscol.schedule.presentation.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedulePayload {
    private String dia;
    private String horaInicio;
    private String horaFin;
    private Long sectionId;
    private Long subjectId;
}
