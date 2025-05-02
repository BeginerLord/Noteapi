package com.appscol.section.factory;

import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.section.presentation.dto.SectionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SectionFactory {

    public SectionDto sectionDto(SectionsEntity sections) {
        return SectionDto.builder()
                .sectionName(sections.getSectionName())
                .id(sections.getId())
                .grade(sections.getGradeEntity().getGrade())
                .studentCount(sections.getStudentEntities().size())
                .subjectNames(sections.getSchedulesEntities().stream().
                        map(
                                s -> s.getSubjectEntity().getSubjectName())
                        .distinct()
                        .collect(
                                Collectors.toList()
                        ))
                .build();
    }
}
