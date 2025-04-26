package com.appscol.security.auth.service.sign_in;

import com.appscol.security.auth.controller.dto.AuthResponse;
import com.appscol.security.auth.controller.payload.AuthCreateUserRequest;
import com.appscol.security.auth.factory.AuthUserMapper;
import com.appscol.security.auth.persistence.model.rol.RoleEntity;
import com.appscol.security.auth.persistence.repositories.RoleRepository;
import com.appscol.security.utils.jwt.JwtTokenProvider;
import com.appscol.user.persistence.entities.UserEntity;
import com.appscol.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthRegisterService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserMapper authUserMapper;

    public AuthResponse register(AuthCreateUserRequest request) {
        // Obtener los roles del usuario
        Set<RoleEntity> roleEntities = roleRepository
                .findRoleEntitiesByRoleEnumIn(request.roleRequest().roleListName())
                .stream().collect(Collectors.toSet());

        if (roleEntities.isEmpty()) throw new IllegalArgumentException("Roles not found");

        // Mapear y guardar el usuario
        UserEntity user = authUserMapper.toUserEntity(request, roleEntities, passwordEncoder);
        userRepository.save(user);

        // Crear autenticación con roles
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, authUserMapper.mapRoles(user.getRoles()));

        // Generar Access Token
        String accessToken = jwtTokenProvider.createAccessToken(authentication);

        return new AuthResponse(user.getUsername(), "User created successfully", accessToken);
    }
}
