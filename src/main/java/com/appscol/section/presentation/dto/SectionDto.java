package com.appscol.section.presentation.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SectionDto(
        Long id,
        String sectionName,
        String grade,
        Integer studentCount,
        List<String> subjectNames
        ) {
}
