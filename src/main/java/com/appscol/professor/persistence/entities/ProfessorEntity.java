package com.appscol.professor.persistence.entities;

import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.user.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private Long id;
    @MapsId
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private UserEntity userEntity;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;
    private String especialidad;
    private String telefono;

    @OneToMany(mappedBy = "professorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectEntity> subjects = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = userEntity.getUuid();
        }
    }

}
