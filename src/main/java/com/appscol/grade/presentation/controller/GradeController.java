package com.appscol.grade.presentation.controller;

import com.appscol.constants.EndpointsConstants;
import com.appscol.grade.presentation.dto.GradeDto;
import com.appscol.grade.presentation.payload.GradePayload;
import com.appscol.grade.service.interfaces.IGradeService;
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
@RequestMapping(EndpointsConstants.ENDPOINT_GRADE)
@RequiredArgsConstructor
@Tag(name = "Grades", description = "Operaciones relacionadas con los grados académicos")
public class GradeController {
    private final IGradeService gradeService;

    @Operation(summary = "Crear un nuevo grado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody GradePayload payload) {
        gradeService.createGrade(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_GRADE)).build();
    }

    @Operation(summary = "Actualizar grado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grado actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Grado no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody GradePayload payload) {
        gradeService.updateGrade(id, payload);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar grado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grado eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Grado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los grados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grados encontrados")
    })
    @GetMapping
    public ResponseEntity<Page<GradeDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "grade") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<GradeDto> result = gradeService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buscar grado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grado encontrado"),
            @ApiResponse(responseCode = "404", description = "Grado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GradeDto> findById(@PathVariable Long id) {
        GradeDto result = gradeService.findById(id);
        return ResponseEntity.ok(result);
    }
}
