package com.appscol.student.presentation.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StudentPayload {
    private Long id; // Solo se usa para actualizaciones, no en creación
    @NotBlank(message = "El username no puede estar en null")
    private String username;
    @Email(message = "El correo electrónico no es válido")
    private String email;
    @NotBlank(message = "La password no puede estar vacia")
    private String password;
    @NotBlank(message = "No puede estar en vacio el campo acudiente")
    private String acudiente;
    @NotBlank(message = "No puede estar en vacio el campo dirección")
    private String direccion;
    @NotBlank(message = "no puede estar vacio el campo telefono")
    private String telefono;
}
