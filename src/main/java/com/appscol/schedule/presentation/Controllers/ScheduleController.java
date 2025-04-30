package com.appscol.schedule.presentation.Controllers;

import com.appscol.constants.EndpointsConstants;
import com.appscol.professor.presentation.payload.ProfessorPayload;
import com.appscol.schedule.presentation.payload.SchedulePayload;
import com.appscol.schedule.services.interfaces.IScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody SchedulePayload payload){
        scheduleService.createSchedule(payload);
        return ResponseEntity.created(URI.create(EndpointsConstants.ENDPOINT_SCHEDULE)).build();
    }



}
