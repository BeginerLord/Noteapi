package com.appscol.Note.persistence.entities;

import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.subject.persistence.entities.SubjectEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nota")
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;
    private String periodo; // Ej: "Periodo 1", "Final"

    @ManyToOne
    private StudentEntity studentEntity;

    @ManyToOne
    private SubjectEntity subjectEntity;
}
