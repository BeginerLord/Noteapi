package com.appscol.security.utils.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el token JWT del encabezado de autorización de la solicitud
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si el token no es nulo, procede a validarlo
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            // Elimina el prefijo "Bearer " del token
            jwtToken = jwtToken.substring(7);

            try {
                // Valida el token y obtiene el token JWT decodificado
                DecodedJWT decodedJWT = jwtTokenProvider.validateToken(jwtToken);

                // Extrae el nombre de usuario del token decodificado
                String username = jwtTokenProvider.extractUsername(decodedJWT);

                // Extrae el rol del token
                Claim roleClaim = jwtTokenProvider.getSpecificClaim(decodedJWT, "role");
                String role = roleClaim.asString();

                // Crea una lista de autoridades con el rol
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority(role)
                );

                // Crea un contexto de seguridad vacío y establece la autenticación con el nombre de usuario y las autoridades
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
            } catch (Exception e) {
                // Si hay un error al validar el token, continúa sin autenticación
                logger.error("Error validating JWT token", e);
            }
        }

        // Continúa la cadena de filtros, permitiendo que la solicitud proceda
        filterChain.doFilter(request, response);
    }

}
