package com.appscol.student.presentation.dto;

import lombok.Builder;

@Builder
public record StudentDto(
        Long id,
        String acudiente,
        String direccion,
        String telefono,
        String username
) {
}
