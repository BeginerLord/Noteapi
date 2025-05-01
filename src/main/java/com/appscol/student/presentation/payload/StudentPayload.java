package com.appscol.student.presentation.payload;

import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.section.persistence.entities.SectionsEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPayload {
    private UUID uuid; //Solo para actualizaciones, no para creación
    @NotBlank(message = "El username no puede estar en null")
    private String username;
    @NotBlank(message = "El email no puede estar en null")
    private String email;
    @NotBlank(message = "La password no puede estar vacia")
    private String password;
    @NotBlank(message = "no puede estar vacio el campo de acudiente")
    private String acudiente;
    @NotBlank(message = "No puede estar vacio el campo de direccion")
    private String direccion;
    @NotBlank(message = "No puede estar  vacio el campo de telefono")
    private String telefono;
}
