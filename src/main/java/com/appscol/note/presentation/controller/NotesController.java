package com.appscol.note.presentation.controller;

import com.appscol.constants.EndpointsConstants;
import com.appscol.note.presentation.dto.NotesDto;
import com.appscol.note.presentation.payload.NotesPayload;
import com.appscol.note.service.interfaces.INotesService;
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
@RequestMapping(EndpointsConstants.ENDPOINT_NOTE)
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Operaciones relacionadas con las notas")
public class NotesController {

    private final INotesService notesService;

    @Operation(summary = "Crear una nueva nota")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Nota creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody NotesPayload payload) {
        notesService.saveNotes(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_NOTE)).build();
    }

    @Operation(summary = "Actualizar nota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nota actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody NotesPayload payload) {
        notesService.updateNotes(id, payload);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar nota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Nota eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notesService.deleteNotes(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todas las notas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notas encontradas")
    })
    @GetMapping
    public ResponseEntity<Page<NotesDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<NotesDto> result = notesService.findAll(pageable);
        return ResponseEntity.ok(result);
    }
}
