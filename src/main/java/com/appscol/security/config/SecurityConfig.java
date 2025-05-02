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

                    // Endpoints públicos
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SIGNUP).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_LOGIN).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SCHEDULE).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_GRADE).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_NOTE).permitAll();
                    //post
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_STUDENT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_GRADE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_NOTE + "/**").permitAll();

                    //GETS
                    http.requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET,EndpointsConstants.ENDPOINT_STUDENT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET,EndpointsConstants.ENDPOINT_SCHEDULE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET,EndpointsConstants.ENDPOINT_GRADE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET,EndpointsConstants.ENDPOINT_NOTE + "/**").permitAll();
                    //delete
                    http.requestMatchers(HttpMethod.DELETE,EndpointsConstants.ENDPOINT_PROFESSOR + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_STUDENT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_GRADE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_NOTE + "/**").permitAll();

                    //update
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_PROFESSOR + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_STUDENT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_SCHEDULE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_GRADE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_NOTE + "/**").permitAll();

                    //http.requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll();

                    // Endpoints Swagger
                    http.requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/v3/api-docs.json"
                    ).permitAll();

                    // Endpoints solo para ADMIN
                    http.requestMatchers("/admin/**").hasRole("ADMIN");

                    // Endpoints solo para USER
                    http.requestMatchers("/user/**").hasRole("USER");

                    // Endpoints accesibles por USER y ADMIN
                    http.requestMatchers("/common/**").hasAnyRole("USER", "ADMIN");

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
