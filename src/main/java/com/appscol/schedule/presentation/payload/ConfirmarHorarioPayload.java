package com.appscol.schedule.presentation.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmarHorarioPayload {
    @NotNull(message = "El ID del horario no puede ser nulo")
    private Long horarioId;
}
