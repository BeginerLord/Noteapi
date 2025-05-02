package com.appscol.subject.presentation.controllers;

import com.appscol.constants.EndpointsConstants;
import org.springframework.data.domain.Page;
import com.appscol.subject.presentation.dto.SubjectDto;
import com.appscol.subject.presentation.payload.SubjectPayload;
import com.appscol.subject.service.interfaces.ISubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequestMapping(EndpointsConstants.ENDPOINT_SUBJECT)
@RequiredArgsConstructor
@Tag(name = "Subjects", description = "Operaciones relacionadas con materias/asignaturas")
public class SubjectController {
    private final ISubjectService subjectService;

    @Operation(summary = "Crear una nueva materia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Materia creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody SubjectPayload payload) {
        subjectService.save(payload);
        return ResponseEntity.created(URI.create("/subjects")).build();
    }

    @Operation(summary = "Actualizar materia por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materia actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody SubjectPayload payload) {
        subjectService.update(payload, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar materia por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Materia eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar materias por nombre (coincidencia parcial)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materias encontradas")
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<SubjectDto>> findBySubjectName(
            @RequestParam String subjectName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "subjectName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending());

        return ResponseEntity.ok(subjectService.findBySubjectName(subjectName, pageable));
    }

    @Operation(summary = "Buscar materias por profesor ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materias encontradas")
    })
    @GetMapping("/professor")
    public ResponseEntity<Page<SubjectDto>> findByProfessorId(
            @RequestParam Long professorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "subjectName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending());

        return ResponseEntity.ok(subjectService.findByProfessorEntity_Id(professorId, pageable));
    }

    @Operation(summary = "Obtener todas las materias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materias encontradas")
    })
    @GetMapping
    public ResponseEntity<Page<SubjectDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "subjectName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending());

        return ResponseEntity.ok(subjectService.findAll(pageable));
    }

    @Operation(summary = "Buscar materia por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materia encontrada"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.findById(id));
    }
}
