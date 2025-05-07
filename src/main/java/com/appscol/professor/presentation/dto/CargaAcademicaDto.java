package com.appscol.professor.presentation.dto;

import lombok.Builder;

@Builder
public record CargaAcademicaDto(
        String materia,
        int clasesPorSemana
) {
}
