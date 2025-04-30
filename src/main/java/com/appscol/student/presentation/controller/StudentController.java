package com.appscol.student.presentation.controller;

import com.appscol.constants.EndpointsConstants;
import com.appscol.student.presentation.dto.StudentDto;
import com.appscol.student.presentation.payload.StudentPayload;
import com.appscol.student.service.interfaces.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(EndpointsConstants.ENDPOINT_STUDENT)
@RequiredArgsConstructor
@Tag(name = "Students", description = "Operaciones relacionadas con los estudiantes")
public class StudentController {

    private final IStudentService studentService;
    @Operation(summary = "Crear un nuevo estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody StudentPayload payload){
        studentService.save(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_STUDENT)).build();
    }

    @Operation(summary = "Actualizar estudiante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody StudentPayload payload) {
        studentService.update(payload, id);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Eliminar estudiante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los estudiantes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiantes encontrados")
    })
    @GetMapping
    public ResponseEntity<Page<StudentDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<StudentDto> result = studentService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buscar estudiante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        StudentDto result = studentService.findById(id);
        return ResponseEntity.ok(result);
    }
}
