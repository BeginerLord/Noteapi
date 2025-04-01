package com.appscol.security.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "status", "accessToken"})
public record AuthResponse(
        String username,
        String message,
        String accessToken
) {
}
