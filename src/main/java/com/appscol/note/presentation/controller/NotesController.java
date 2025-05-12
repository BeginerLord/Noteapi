package com.appscol.note.presentation.controller;

import com.appscol.constants.EndpointsConstants;
import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.note.presentation.dto.NotesDto;
import com.appscol.note.presentation.payload.AddNotePayload;
import com.appscol.note.presentation.payload.NotesPayload;
import com.appscol.note.service.interfaces.INotesService;
import com.sun.security.auth.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(EndpointsConstants.ENDPOINT_NOTE)
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Operaciones relacionadas con las notas")
public class NotesController {

    private final INotesService notesService;

    @Operation(summary = "Crear una nueva nota")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Nota creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<Void> save(@Validated @RequestBody NotesPayload payload) {
        notesService.saveNotes(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_NOTE)).build();
    }

    @Operation(summary = "Agregar nota por professor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Nota creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/add/{studentUuid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<Void> addNote(
            @PathVariable UUID studentUuid,
            @RequestBody NotesPayload payload, // Recibiendo el payload
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Authentication authentication  // Inyectar Authentication para acceder al usuario autenticado
    ) {
        // Obtener el UUID del usuario autenticado (a través de Authentication)
        UUID professorUuid = (UUID) authentication.getPrincipal();  // Esto supone que el UUID se almacena como principal

        // Crear el AddNotePayload para pasarlo al servicio
        AddNotePayload addNotePayload = AddNotePayload.builder()
                .studentUuid(studentUuid) // Usando el UUID del estudiante desde el PathVariable
                .valor(payload.getValor())
                .periodo(payload.getPeriodo())
                .build();

        // Llamar al servicio con el AddNotePayload y el UUID del profesor
        notesService.addNote(addNotePayload, professorUuid);

        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_NOTE)).build();
    }

    @Operation(summary = "Actualizar nota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nota actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody NotesPayload payload) {
        notesService.updateNotes(id, payload);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar nota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Nota eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notesService.deleteNotes(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todas las notas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notas encontradas"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    public ResponseEntity<Page<NotesDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Authentication authentication) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        // Verificar el rol del usuario
        boolean isStudent = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isProfessor = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"));

        Page<NotesDto> result;

        if (isStudent) {
            // Si es estudiante, solo puede ver sus propias notas
            UUID studentUuid = UUID.fromString(authentication.getName());
            result = notesService.getNotesByStudent(pageable, studentUuid);
        } else if (isAdmin || isProfessor) {
            // Si es admin o profesor, puede ver todas las notas
            result = notesService.findAll(pageable);
        } else {
            // Si no tiene un rol válido, denegar acceso
            throw new AccessDeniedException("No tiene permisos para ver notas");
        }

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener nota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nota encontrada"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotesDto> findById(@PathVariable Long id, Authentication authentication) {
        // Verificar el rol del usuario
        boolean isStudent = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isProfessor = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"));

        if (isStudent) {
            // Si es estudiante, verificar que la nota le pertenece
            UUID studentUuid = UUID.fromString(authentication.getName());
            if (!notesService.isNoteOwnedByStudent(id, studentUuid)) {
                throw new AccessDeniedException("No tiene permisos para ver esta nota");
            }
        } else if (!isAdmin && !isProfessor) {
            // Si no es admin ni profesor, denegar acceso
            throw new AccessDeniedException("No tiene permisos para ver notas");
        }
        // Buscar la nota por ID usando el servicio
        NotesDto note = notesService.findById(id);
        return ResponseEntity.ok(note);
    }
}
