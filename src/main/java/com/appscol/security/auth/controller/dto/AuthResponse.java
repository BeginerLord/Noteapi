package com.appscol.security.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder({"username", "message", "status", "accessToken","uuid"})
public record AuthResponse(
        String username,
        String message,
        String accessToken,
        UUID uuid
) {
}
