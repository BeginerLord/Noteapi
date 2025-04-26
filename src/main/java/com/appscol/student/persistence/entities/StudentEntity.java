package com.appscol.student.persistence.entities;

import com.appscol.Note.persistence.entities.NotesEntity;
import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.user.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private String acudiente;
    private String direccion;
    private String telefono;

    @ManyToOne @JoinColumn(name = "grade_id")
    private GradeEntity gradeEntity;

    @OneToMany(mappedBy = "studentEntity")
    private List<NotesEntity> notesEntities;

    @ManyToOne
    @JoinColumn(name = "sections_id")
    private SectionsEntity sectionsEntity;

    @ManyToMany
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<SubjectEntity> subjectEntities; // solo si usas asignación individual
}
