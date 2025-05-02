package com.appscol.note.presentation.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesPayload {
    @NotNull(message = "El valor de la nota no puede estar vacio")
    Double valor;
    @NotBlank(message = "El campo periodo no puede estar vacio")
    String periodo;
}
