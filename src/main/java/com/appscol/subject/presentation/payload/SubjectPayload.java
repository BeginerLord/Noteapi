package com.appscol.subject.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectPayload {
    @NotBlank(message = "El nombre de la materia no puede estar en null")
    private String subjectName;

    @NotNull
    private UUID professorUuid;

    @NotNull(message = "Los IDS de los grados no puede estar en null")
    private List<Long> gradeIds;
}
