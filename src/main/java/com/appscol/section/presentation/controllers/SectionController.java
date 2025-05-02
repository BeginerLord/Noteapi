package com.appscol.section.presentation.controllers;

import com.appscol.constants.EndpointsConstants;
import com.appscol.section.presentation.dto.SectionDto;
import com.appscol.section.presentation.payload.SectionPayload;
import com.appscol.section.service.interfaces.ISectionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(EndpointsConstants.ENDPOINT_SECTION)
@RequiredArgsConstructor
@Tag(name = "Sections", description = "Operaciones relacionadas con las secciones escolares")
public class SectionController {
    private final ISectionService sectionService;

    @Operation(summary = "Crear una nueva sección")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sección creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody SectionPayload payload) {
        sectionService.save(payload);
        return ResponseEntity.created(URI.create("/sections")).build();
    }

    @Operation(summary = "Actualizar sección por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sección actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Sección no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody SectionPayload payload) {
        sectionService.update(payload, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar sección por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sección eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sección no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar secciones por grado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secciones encontradas"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/por-grado")
    public ResponseEntity<Page<SectionDto>> findByGrade(
            @RequestParam String grade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sectionName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<SectionDto> result = sectionService.findByGrade(grade, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener todas las secciones")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secciones encontradas")
    })
    @GetMapping
    public ResponseEntity<Page<SectionDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sectionName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<SectionDto> result = sectionService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buscar sección por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sección encontrada"),
            @ApiResponse(responseCode = "404", description = "Sección no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SectionDto> findById(@PathVariable Long id) {
        SectionDto result = sectionService.findById(id);
        return ResponseEntity.ok(result);
    }
}
