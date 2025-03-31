package com.appscol.user.factory;

import com.appscol.user.persistence.entities.UserEntity;
import com.appscol.user.presentation.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final ModelMapper modelMapper;

    // Convert a User entity to a UserDto
    public UserDto userDto(UserEntity userEntity) {

        return UserDto.builder()
                .fullName(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }
}
