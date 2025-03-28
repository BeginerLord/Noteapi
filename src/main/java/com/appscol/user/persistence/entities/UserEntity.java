package com.appscol.user.persistence.entities;

import com.appscol.security.auth.persistence.model.rol.RoleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "is_enabled")
    private  boolean isEnabled;


    @Column(name = "account_No_Locked")
    private  boolean accountNoLocked;

    @Column(name = "account_No_Expired")
    private  boolean accountNoExpired;


    @Column(name = "credential_No_Expired")
    private  boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

}
