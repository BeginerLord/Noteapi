package com.appscol.section.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionPayload {
    @NotBlank(message = "El nombre de la seccion no puede estar en null")
    private String sectionName;
    @NotBlank(message = "El id del grado no puede estar en null")
    private Long gradeId;

}
