package com.appscol.subject.presentation.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record SubjectDto(
        Long id,
        String subjectName,
        String profesorNombre,
        String profesorEmail,
        String especialidad,
        List<String> grados
) {
}
