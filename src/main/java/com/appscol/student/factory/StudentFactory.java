package com.appscol.student.factory;

import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.student.presentation.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentFactory {
    private final ModelMapper modelMapper;

    public StudentDto studentDto(StudentEntity studentEntity) {
        return StudentDto.builder()
                .id(studentEntity.getId())
                .acudiente(studentEntity.getAcudiente())
                .direccion(studentEntity.getDireccion())
                .telefono(studentEntity.getTelefono())
                .username(studentEntity.getUserEntity().getUsername())
                .build();
    }
}
