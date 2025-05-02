package com.appscol.note.presentation.dto;

import lombok.Builder;

@Builder
public record NotesDto(
        Double valor,
        String periodo
) {
}
