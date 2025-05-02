package com.appscol.subject.factory;

import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.subject.presentation.dto.SubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubjectFactory {
    public SubjectDto subjectDto(SubjectEntity subject) {
        var professor = subject.getProfessorEntity();
        var user = professor.getUserEntity();

        return new SubjectDto(
                subject.getId(),
                subject.getSubjectName(),
                user.getUsername(),
                user.getEmail(),
                professor.getEspecialidad(),
                subject.getGradeEntities().stream()
                        .map(g -> g.getGrade())
                        .distinct()
                        .collect(Collectors.toList())
        );
    }
}
