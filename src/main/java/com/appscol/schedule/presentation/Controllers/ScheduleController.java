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
    public ResponseEntity<Void> confirmarHorario(@PathVariable UUID profesorUuid, @PathVariable Long horarioId) {
        scheduleService.confirmarHorarioAsignado(profesorUuid, horarioId);
        return ResponseEntity.ok().build();
    }
}
