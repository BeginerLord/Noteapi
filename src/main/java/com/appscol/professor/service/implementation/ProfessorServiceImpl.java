package com.appscol.professor.service.implementation;

import com.appscol.helpers.exception.exceptions.ConflictException;
import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.professor.factory.ProfessorFactory;
import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.professor.persistence.repositories.ProfessorRepository;
import com.appscol.professor.presentation.dto.ProfessorDto;
import com.appscol.professor.presentation.payload.ProfessorPayload;
import com.appscol.professor.service.interfaces.IProfessorService;
import com.appscol.schedule.persistence.repositories.ScheduleRepository;
import com.appscol.schedule.presentation.dto.ScheduleDto;
import com.appscol.security.auth.persistence.model.rol.RoleEntity;
import com.appscol.security.auth.persistence.model.rol.RoleEnum;
import com.appscol.security.auth.persistence.repositories.RoleRepository;
import com.appscol.user.persistence.entities.UserEntity;
import com.appscol.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ProfessorServiceImpl implements IProfessorService {

    private final ModelMapper modelMapper;
    private final ProfessorFactory factory;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public void save(ProfessorPayload payload) {

        if (userRepository.existsByUsername(payload.getUsername())) {
            throw new ConflictException("El username ya está en uso");
        }
        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new ConflictException("El email ya está en uso");
        }

        UUID newUuid = UUID.randomUUID();

        RoleEntity professorRole = roleRepository.findByRoleEnum(RoleEnum.PROFESSOR)
                .orElseThrow(() -> new ResourceNotFoundException("Rol PROFESSOR no encontrado"));

        UserEntity user = UserEntity.builder()
                .uuid(newUuid)
                .username(payload.getUsername())
                .email(payload.getEmail())
                .password(passwordEncoder.encode(payload.getPassword()))
                .roles(Set.of(professorRole))
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .isEnabled(true)
                .build();

        user = userRepository.save(user);

        ProfessorEntity professor = ProfessorEntity.builder()
                .userEntity(user)
                .uuid(newUuid)
                .especialidad(payload.getEspecialidad().trim())
                .telefono(payload.getTelefono())
                .build();

        professorRepository.save(professor);
    }


    @Override
    @Transactional
    public void update(ProfessorPayload payload, UUID uuid) {
        UserEntity user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con UUID: " + uuid));

        ProfessorEntity professor = professorRepository.findByUserEntity(user)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el profesor con UUID de usuario: " + uuid));
        // Mapear campos de payload a user y profesor
        modelMapper.map(payload, user);
        modelMapper.map(payload, professor);

        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);
        professorRepository.save(professor);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        // 1. Buscar el usuario por UUID
        UserEntity user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con UUID: " + uuid));

        // 2. Buscar el profesor asociado a ese usuario
        ProfessorEntity professor = professorRepository.findByUserEntity(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado para el usuario con UUID: " + uuid));

        // 3. Eliminar al profesor
        professorRepository.delete(professor);

        // 4. Marcar el usuario como inactivo en lugar de eliminarlo
        user.setEnabled(false); // Si tu campo se llama diferente, ajústalo
        user.setAccountNoLocked(false);
        user.setAccountNoExpired(false);
        user.setCredentialNoExpired(false);

        userRepository.save(user);

    }

    @Override
    public Page<ProfessorDto> findByEspecialidad(String especialidad, Pageable pageable) {
        return professorRepository.findByEspecialidad(especialidad, pageable)
                .map(factory::professorDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProfessorDto> findAll(Pageable pageable) {
        return professorRepository.findAll(pageable)
                .map(factory::professorDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfessorDto findByUuid(UUID uuid) {
        return professorRepository.findByUuid(uuid)
                .map(factory::professorDto)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el profesor con UUID: " + uuid + "registrado en la DB"));
    }

    @Override
    public boolean confirmarDisponibilidadProfesor(UUID professorUuid, String dia, String horaInicio, String horaFin) {
        // Validamos que todos los campos necesarios estén presentes
        if (professorUuid == null || dia == null || horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("Todos los parámetros son obligatorios");
        }

        // Buscamos conflictos de horario para el profesor
        List<ScheduleDto> conflicts = scheduleRepository.findConflictingHorarios(
                professorUuid,
                dia.trim(),
                horaInicio.trim(),
                horaFin.trim()
        );

        // Si la lista está vacía, no hay conflictos y el profesor está disponible
        return conflicts.isEmpty();
    }


}
