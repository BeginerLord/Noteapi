package com.appscol.student.service.implementation;

import com.appscol.helpers.exception.exceptions.ConflictException;
import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.professor.presentation.payload.ProfessorPayload;
import com.appscol.security.auth.persistence.model.rol.RoleEntity;
import com.appscol.security.auth.persistence.model.rol.RoleEnum;
import com.appscol.security.auth.persistence.repositories.RoleRepository;
import com.appscol.student.factory.StudentFactory;
import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.student.persistence.repositories.StudentRepository;
import com.appscol.student.presentation.dto.StudentDto;
import com.appscol.student.presentation.payload.StudentPayload;
import com.appscol.student.service.interfaces.IStudentService;
import com.appscol.user.persistence.entities.UserEntity;
import com.appscol.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final ModelMapper modelMapper;
    private final StudentFactory studentFactory;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void save(StudentPayload payload) {

        if (userRepository.existsByUsername(payload.getUsername())) {
            throw new ConflictException("El username ya está en uso");
        }
        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new ConflictException("El email ya está en uso");
        }

        UUID newUuid = UUID.randomUUID();

        RoleEntity studentRole = roleRepository.findByRoleEnum(RoleEnum.STUDENT)
                .orElseThrow(() -> new ResourceNotFoundException("Rol PROFESSOR no encontrado"));

        UserEntity user = UserEntity.builder()
                .uuid(newUuid)
                .username(payload.getUsername())
                .email(payload.getEmail())
                .password(passwordEncoder.encode(payload.getPassword()))
                .roles(Set.of(studentRole))
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .isEnabled(true)
                .build();

        user = userRepository.save(user);

        StudentEntity student = StudentEntity.builder()
                .userEntity(user)
                .uuid(newUuid)
                .acudiente(payload.getAcudiente().trim())
                .direccion(payload.getDireccion().trim())
                .telefono(payload.getTelefono())
                .build();

        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void update(StudentPayload payload, UUID uuid) {
        UserEntity user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con UUID: " + uuid));

        StudentEntity student = studentRepository.findByUserEntity(user)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el estudiante con UUID de usuario: " + uuid));
        // Mapear campos de payload a user y estudiante
        modelMapper.map(payload, user);
        modelMapper.map(payload, student);

        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        // 1. Buscar el usuario por UUID
        UserEntity user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con UUID: " + uuid));

        // 2. Buscar el estudiante asociado a ese usuario
        StudentEntity student = studentRepository.findByUserEntity(user)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado para el usuario con UUID: " + uuid));

        // 3. Eliminar al estudiante
        studentRepository.delete(student);

        // 4. Marcar el usuario como inactivo en lugar de eliminarlo
        user.setEnabled(false); // Si tu campo se llama diferente, ajústalo
        user.setAccountNoLocked(false);
        user.setAccountNoExpired(false);
        user.setCredentialNoExpired(false);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentFactory::studentDto);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto findByUuid(UUID uuid) {
        return studentRepository.findByUuid(uuid)
                .map(studentFactory::studentDto)
                .orElseThrow(()->new ResourceNotFoundException("No existe el estudiante con UUID: " + uuid + "registrado en la DB"));
    }
}