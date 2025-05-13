package com.appscol.security.config;

import com.appscol.constants.EndpointsConstants;
import com.appscol.security.utils.jwt.JwtTokenProvider;
import com.appscol.security.utils.jwt.JwtTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(http -> {

                    // Endpoints Swagger
                    http.requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/v3/api-docs.json"
                    ).permitAll();

                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SIGNUP).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_LOGIN).permitAll();

                    // Crear estudiante y profesor (solo admin puede crearlos)
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_STUDENT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").permitAll();

                    // Endpoints CRUD de recursos (solo admin)
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SECTION + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SUBJECT + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_NOTE + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_GRADE + "/**").hasRole("ADMIN");

                    // Endpoints de consulta, accesibles solo para usuarios autenticados según rol
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").hasAnyRole("ADMIN", "PROFESSOR");
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_STUDENT + "/**").hasAnyRole("ADMIN", "STUDENT");
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").hasAnyRole("ADMIN", "STUDENT", "PROFESSOR");
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_SECTION + "/**").hasAnyRole("ADMIN", "PROFESSOR");
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_SUBJECT + "/**").hasAnyRole("ADMIN", "PROFESSOR");
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_NOTE + "/**").hasAnyRole("ADMIN", "PROFESSOR", "STUDENT");
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_GRADE + "/**").hasAnyRole("ADMIN", "PROFESSOR");

                    // PUT para actualizar recursos solo para ADMIN y PROFESOR
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_STUDENT + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_SECTION + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_SUBJECT + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_NOTE + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_GRADE + "/**").hasRole("ADMIN");

                    // DELETE solo para ADMIN
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_STUDENT + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_SECTION + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_SUBJECT + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_NOTE + "/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_GRADE + "/**").hasRole("ADMIN");

                    // PUT para confirmar horarios (profesor o admin)
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_SCHEDULE + "/profesor/{profesorUuid}/horario/{horarioId}/confirmar")
                            .hasAnyRole("ADMIN", "PROFESSOR");

                    // GET para horarios de estudiantes (solo estudiante, admin y profesor)
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_SCHEDULE + "/student/{studentUuid}/horario")
                            .hasAnyRole("ADMIN", "STUDENT");

                    // Otras reglas personalizadas por método y permisos
                    http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
                    http.requestMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE");

                    // Cualquier otra petición requiere autenticación
                    http.anyRequest().authenticated();
                })

                .addFilterBefore(new JwtTokenValidator(jwtTokenProvider), BasicAuthenticationFilter.class)
                .build();
    }
}
