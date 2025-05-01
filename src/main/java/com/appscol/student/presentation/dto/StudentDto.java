package com.appscol.student.presentation.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StudentDto(
        String acudiente,
        String direccion,
        String telefono,
        String username,
        UUID uuid
) {
}
