package com.appscol.professor.presentation.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfessorDto(
        String especialidad,
        String telefono,
        String username,
        UUID uuid


) {
}
