package com.appscol.security.auth.persistence.repositories;

import com.appscol.security.auth.persistence.model.rol.RoleEntity;
import com.appscol.security.auth.persistence.model.rol.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    // Para obtener varios roles por Enum
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

    boolean existsByRoleEnum(RoleEnum roleEnum);
    Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}
