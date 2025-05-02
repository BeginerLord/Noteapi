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

    public StudentDto studentDto(StudentEntity studentEntity){
        return StudentDto.builder()
                .uuid(studentEntity.getUuid())
                .telefono(studentEntity.getTelefono())
                .direccion(studentEntity.getDireccion())
                .acudiente(studentEntity.getAcudiente())
                .username(studentEntity.getUserEntity().getUsername())
                .build();
    }
}
