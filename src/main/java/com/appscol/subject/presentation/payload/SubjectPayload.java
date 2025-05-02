package com.appscol.subject.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectPayload {
    @NotBlank(message = "El nombre de la materia no puede estar en null")
    private String subjectName;

    @NotBlank(message = "El ID del profe no puede estar en null")
    private Long professorId;

    @NotBlank(message = "Los IDS de los grados no puede estar en null")
    private List<Long> gradeIds;
}
