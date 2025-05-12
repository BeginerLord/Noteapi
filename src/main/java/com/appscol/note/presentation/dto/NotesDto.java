package com.appscol.note.presentation.dto;

import lombok.Builder;

@Builder
public record NotesDto(
        Long id,
        String studentName,
        Double valor,
        String periodo,
        String professorUsername
) {
}
