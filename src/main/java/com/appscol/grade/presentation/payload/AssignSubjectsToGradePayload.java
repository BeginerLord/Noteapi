package com.appscol.grade.presentation.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignSubjectsToGradePayload {
    @NotNull(message = "El ID del grado no puede ser nulo")
    private Long gradeId;
    @NotEmpty(message = "Debe proporcionar al menos un ID de materia")
    private List<Long> subjectIds;
}
