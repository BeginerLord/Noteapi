package com.appscol.professor.presentation.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProfessorPayload {
    private UUID uuid; // Solo se usa para actualizaciones, no en creación
    @NotBlank(message = "El username no puede estar en null")
    private String username;
    @Email(message ="El correo electrónico no es válido")
    private String email;
    @NotBlank(message = "La password no puede estar vacia")
    private String password;
    @NotBlank(message = "no puede estar vacio el campo telefono")
    private String telefono;
    @NotBlank(message = "No puede estar en vacio la especialidad")
    private String especialidad;
}
