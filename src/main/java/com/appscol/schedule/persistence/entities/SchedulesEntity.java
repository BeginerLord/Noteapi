package com.appscol.schedule.persistence.entities;

import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.subject.persistence.entities.SubjectEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule")
public class SchedulesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dia;
    private String horaInicio;
    private String horaFin;


    @ManyToOne
    @JoinColumn(name = "sections_id")
    private SectionsEntity sectionsEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjectEntity;
}
