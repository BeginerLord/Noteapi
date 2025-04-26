package com.appscol.professor.presentation.dto;

import lombok.Builder;

@Builder
public record ProfessorDto(
        String especialidad,
        String telefono,
        String username
) {
}
