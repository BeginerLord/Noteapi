package com.appscol.section.persistence.entities;

import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.schedule.persistence.entities.SchedulesEntity;
import com.appscol.student.persistence.entities.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sections")
public class SectionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ej: "A", "B", "C"

    @ManyToOne
    private GradeEntity gradeEntity;

    @OneToMany(mappedBy = "sectionsEntity")
    private List<StudentEntity> studentEntities;

    @OneToMany(mappedBy = "sectionsEntity")
    private List<SchedulesEntity> schedulesEntities;
}
