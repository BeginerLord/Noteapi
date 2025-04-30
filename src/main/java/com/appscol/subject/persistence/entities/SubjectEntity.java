package com.appscol.subject.persistence.entities;

import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.professor.persistence.entities.ProfessorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;

    @ManyToOne
    private ProfessorEntity professorEntity;

    @ManyToMany(mappedBy = "subjectEntities")
    private List<GradeEntity> gradeEntities;
}
