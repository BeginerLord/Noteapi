package com.appscol.student.service.implementation;

import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
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

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final ModelMapper modelMapper;
    private final StudentFactory  studentFactory;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(StudentPayload payload) {
        StudentEntity student = modelMapper.map(payload, StudentEntity.class);
        student = studentRepository.save(student);
    }

    @Override
    @Transactional
    public void update(StudentPayload payload, Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con ID: " + id));

        StudentEntity student = studentRepository.findByUserEntity(user)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el estudiante con ID de usuario: " + id));

        // Mapear campos de payload y estudiante
        modelMapper.map(payload, user);
        modelMapper.map(payload, student);

        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);
        studentRepository.save(student);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // 1. Buscar el usuario por ID
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // 2. Buscar el estudiante asociado con ese usuario
        StudentEntity student = studentRepository.findByUserEntity(user)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado para el usuario con ID: " + id));

        // 3. Eliminar al estudiante
        studentRepository.delete(student);

        // 4. Marcara el usuario como inactivo en lugar de eliminarlo
        user.setEnabled(false); // Si el campo se llama diferente hay que ajustarlo
        user.setAccountNoLocked(false);
        user.setAccountNoExpired(false);
        user.setCredentialNoExpired(false);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto findById(Long id) {
        return studentRepository.findById(id)
                .map(studentFactory::studentDto)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el estudiante con ID: " + id + "registrado en la DB"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentFactory::studentDto);
    }
}
