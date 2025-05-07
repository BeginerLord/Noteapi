package com.appscol.section.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionPayload {
    @NotBlank(message = "El nombre de la seccion no puede estar en null")
    private String sectionName;
    @NotNull(message = "El gradeId no puede ser nulo")
    private Long gradeId;

}
