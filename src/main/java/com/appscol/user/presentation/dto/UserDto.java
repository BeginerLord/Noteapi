package com.appscol.user.presentation.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record UserDto(
        String fullName,
        String email
)
{}
