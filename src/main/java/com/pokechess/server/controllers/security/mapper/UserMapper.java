package com.pokechess.server.controllers.security.mapper;

import com.pokechess.server.controllers.security.dto.user.register.UserRegisterResponseDTO;
import com.pokechess.server.controllers.security.dto.user.register.UserRegisterRequestDTO;
import com.pokechess.server.models.globals.user.User;

public class UserMapper {
    public static User mapUserFromUserRegisterRequestDTO(UserRegisterRequestDTO dto) {
        return User.builder().username(dto.getUsername()).passwordHashed(dto.getPassword())
                .trainerName(dto.getTrainerName()).build();
    }

    public static UserRegisterResponseDTO mapUserToUserRegisterResponseDTO(User model) {
        UserRegisterResponseDTO dto = new UserRegisterResponseDTO();
        dto.setUsername(model.getUsername());
        dto.setTrainerName(model.getTrainerName());
        return dto;
    }
}
