package com.appscol.security.auth.persistence.model.rol;

import com.appscol.security.auth.persistence.repositories.RoleRepository;
import com.appscol.user.persistence.entities.UserEntity;
import com.appscol.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Default admin credentials
    private static final String DEFAULT_ADMIN_EMAIL = "admin@admin.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        createRoleIfNotFound(RoleEnum.ADMIN);
        createRoleIfNotFound(RoleEnum.STUDENT);
        createRoleIfNotFound(RoleEnum.PROFESSOR);


        createDefaultAdminIfNotExists();
    }

    private void createRoleIfNotFound(RoleEnum roleEnum) {
        boolean exists = roleRepository.existsByRoleEnum(roleEnum);
        if (!exists) {
            RoleEntity roleEntity = RoleEntity.builder()
                    .roleEnum(roleEnum)
                    .build();
            roleRepository.save(roleEntity);
            System.out.println("Role created: " + roleEnum.name());
        }
    }

    private void createDefaultAdminIfNotExists() {
        if (userRepository.findUserEntityByEmail(DEFAULT_ADMIN_EMAIL).isPresent()) {
            System.out.println("Default admin user already exists");
            return;
        }

        // Obtener solo el ID del rol ADMIN
        RoleEntity adminRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        // Crear usuario sin roles
        UserEntity adminUser = UserEntity.builder()
                .email(DEFAULT_ADMIN_EMAIL)
                .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                .username("admin")
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        // Guardar usuario primero
        userRepository.save(adminUser);

        // Actualizar el usuario con roles
        adminUser.setRoles(Set.of(adminRole));
        userRepository.save(adminUser);

        System.out.println("Default admin user created with email: " + DEFAULT_ADMIN_EMAIL);
    }
}
