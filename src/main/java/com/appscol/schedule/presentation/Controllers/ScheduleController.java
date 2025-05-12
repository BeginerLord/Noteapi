package com.appscol.schedule.presentation.Controllers;

import com.appscol.constants.EndpointsConstants;
import com.appscol.professor.presentation.payload.ProfessorPayload;
import com.appscol.schedule.presentation.dto.ScheduleDto;
import com.appscol.schedule.presentation.payload.SchedulePayload;
import com.appscol.schedule.services.interfaces.IScheduleService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EndpointsConstants.ENDPOINT_SCHEDULE)
@RequiredArgsConstructor
@Tag(name = "Schedule", description = "ACCIONES DEL HORARIO")
public class ScheduleController {

    private final IScheduleService scheduleService;

    @Operation(summary = "Crear un nuevo horario Professor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Horario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @Hidden
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody SchedulePayload payload){
        scheduleService.createSchedule(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_SCHEDULE)).build();
    }

    @Operation(summary = "Asignar horario a un profesor")
    @ApiResponse(responseCode = "201", description = "Horario asignado exitosamente")
    @PostMapping("/asignarHorarioProfesor")
    public ResponseEntity<Void> asignarHorario(@Validated @RequestBody SchedulePayload payload) {
        scheduleService.asignarHorarioProfesor(payload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(
            summary = "Listar horarios de un profesor por UUID",
            description = "Obtiene todos los horarios asignados al profesor identificado por UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horarios encontrados"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/professor/{uuid}")
    public ResponseEntity<List<ScheduleDto>> findScheduleByProfessorUuid(@PathVariable UUID uuid) {
        List<ScheduleDto> horarios = scheduleService.findScheduleByProfessorUuid(uuid);
        return ResponseEntity.ok(horarios);
    }

    @Operation(
            summary = "Confirmar horario asignado a profesor",
            description = "Permite a un profesor confirmar un horario que le ha sido asignado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horario confirmado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para confirmar este horario"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado")
    })
    @PutMapping("/profesor/{profesorUuid}/horario/{horarioId}/confirmar")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<Void> confirmarHorario(
            @PathVariable UUID profesorUuid, 
            @PathVariable Long horarioId,
            Authentication authentication) {

        // Verificar si el usuario es un profesor y si está intentando confirmar su propio horario
        boolean isProfessor = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (isProfessor && !isAdmin) {
            // Si es profesor pero no admin, verificar que el UUID coincida con el del profesor autenticado
            UUID authenticatedUserUuid = UUID.fromString(authentication.getName());
            if (!profesorUuid.equals(authenticatedUserUuid)) {
                throw new AccessDeniedException("No puedes confirmar horarios de otro profesor");
            }
        }

        scheduleService.confirmarHorarioAsignado(profesorUuid, horarioId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Obtener horario de un estudiante",
            description = "Obtiene todos los horarios asignados al estudiante identificado por UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horarios encontrados"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/student/{studentUuid}/horario")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFESSOR') or hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<List<ScheduleDto>> findScheduleByStudentUuid(
            @PathVariable UUID studentUuid,
            Authentication authentication) {

        // Verificar si el usuario es un estudiante y si está intentando ver su propio horario
        boolean isStudent = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isProfessor = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"));

        if (isStudent && !isAdmin && !isProfessor) {
            // Si es estudiante pero no admin ni profesor, verificar que el UUID coincida con el del estudiante autenticado
            UUID authenticatedUserUuid = UUID.fromString(authentication.getName());
            if (!studentUuid.equals(authenticatedUserUuid)) {
                throw new AccessDeniedException("No puedes ver horarios de otro estudiante");
            }
        }

        List<ScheduleDto> horarios = scheduleService.findScheduleByStudentUuid(studentUuid);
        return ResponseEntity.ok(horarios);
    }
}
