package com.appscol.grade.persistence.entities;

import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.subject.persistence.entities.SubjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grade")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ej: "6°", "7°", "10°"

    @OneToMany(mappedBy = "gradeEntity")
    private List<SectionsEntity> sectionsEntity;

    @ManyToMany
    @JoinTable(
            name = "grade_subject",
            joinColumns = @JoinColumn(name = "grade_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<SubjectEntity> subjectEntities;
}
