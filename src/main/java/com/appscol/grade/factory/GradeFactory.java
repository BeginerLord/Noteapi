package com.appscol.grade.factory;

import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.grade.presentation.dto.GradeDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeFactory {

    private final ModelMapper modelMapper;

    public GradeDto gradeDto(GradeEntity gradeEntity){
        return GradeDto.builder()
                //.id(String.valueOf(gradeEntity.getId()))
                .grade(gradeEntity.getGrade())
                .build();
    }
}
