package com.appscol.professor.factory;

import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.professor.presentation.dto.ProfessorDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessorFactory {
    private final ModelMapper modelMapper;

    public ProfessorDto professorDto(ProfessorEntity professorEntity) {
        return ProfessorDto.builder()
                .uuid(professorEntity.getUuid())
                .especialidad(professorEntity.getEspecialidad())
                .telefono(professorEntity.getTelefono())
                .username(professorEntity.getUserEntity().getUsername())
                .build();
    }
}
