package com.appscol.user.persistence.entities;

import com.appscol.security.auth.persistence.model.rol.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    protected void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();  // Genera UUID antes de insertar
        }

    }

    private String username;

    @Email
    private String email;
    private String password;



    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;
    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;
    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();
}
