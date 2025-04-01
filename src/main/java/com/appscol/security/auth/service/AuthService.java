package com.appscol.security.auth.service;

import com.appscol.security.auth.controller.dto.AuthResponse;
import com.appscol.security.auth.controller.payload.AuthCreateUserRequest;
import com.appscol.security.auth.controller.payload.AuthLoginRequest;
import com.appscol.security.auth.service.login.AuthLoginService;
import com.appscol.security.auth.service.sign_in.AuthRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRegisterService authRegisterService;
    private final AuthLoginService authLoginService;


    public AuthResponse register(AuthCreateUserRequest request) {
        return authRegisterService.register(request);
    }

    public AuthResponse login(AuthLoginRequest request) {
        return authLoginService.login(request);
    }
}
