package com.appscol.professor.presentation.controllers;

import com.appscol.constants.EndpointsConstants;
import com.appscol.professor.presentation.dto.ProfessorDto;
import com.appscol.professor.presentation.payload.ProfessorPayload;
import com.appscol.professor.service.interfaces.IProfessorService;
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
import java.util.UUID;

@RestController
@RequestMapping(EndpointsConstants.ENDPOINT_PROFESSOR)
@RequiredArgsConstructor
@Tag(name = "Professors", description = "Operaciones relacionadas con los profesores")
public class ProfessorController {

    private final IProfessorService professorService;
    @Operation(summary = "Crear un nuevo profesor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profesor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody ProfessorPayload payload) {
        professorService.save(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_PROFESSOR)).build();
    }

    @Operation(summary = "Actualizar profesor por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @Validated @RequestBody ProfessorPayload payload) {
        professorService.update(payload, uuid);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar profesor por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profesor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        professorService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar profesores por especialidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesores encontrados"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/especialidad")
    public ResponseEntity<Page<ProfessorDto>> findByEspecialidad(
            @RequestParam String especialidad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<ProfessorDto> result = professorService.findByEspecialidad(especialidad, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener todos los profesores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesores encontrados")
    })
    @GetMapping
    public ResponseEntity<Page<ProfessorDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<ProfessorDto> result = professorService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buscar profesor por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<ProfessorDto> findByUuid(@PathVariable UUID uuid) {
        ProfessorDto result = professorService.findByUuid(uuid);
        return ResponseEntity.ok(result);
    }
}