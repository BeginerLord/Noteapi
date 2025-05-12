package com.appscol.note.presentation.payload;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNotePayload {
    private UUID studentUuid;
    private Double valor;
    private String periodo;
}
