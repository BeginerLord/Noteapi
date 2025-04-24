package com.appscol.professor.persistence.entities;

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
@Table(name = "professor")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private String especialidad;
    private String telefono;

    @OneToMany(mappedBy = "professorEntity")
    private List<SubjectEntity> subjectEntities;
}
