package com.appscol.grade.presentation.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GradePayload {
    @NotBlank(message = "No puede estar vacio el campo de grado")
    private String grade;
}
