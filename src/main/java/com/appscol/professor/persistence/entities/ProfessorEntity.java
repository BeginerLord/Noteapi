package com.appscol.professor.persistence.entities;

import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.user.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private String especialidad;
    private String telefono;

    @OneToMany(mappedBy = "professorEntity")
    private List<SubjectEntity> subjectEntities;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

}
