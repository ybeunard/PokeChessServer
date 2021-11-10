package com.pokechess.server.controllers.security.mapper;

import com.pokechess.server.controllers.security.dto.UserDTO;
import com.pokechess.server.controllers.security.dto.UserRegisterDTO;
import com.pokechess.server.models.globals.User;

public class UserMapper {
    public static User mapUserFromUserRegisterDTO(UserRegisterDTO dto) {
        return User.builder().username(dto.getUsername()).passwordHashed(dto.getPassword())
                .trainerName(dto.getTrainerName()).build();
    }

    public static UserDTO mapUserToUserDTO(User model) {
        UserDTO dto = new UserDTO();
        dto.setUsername(model.getUsername());
        dto.setTrainerName(model.getTrainerName());
        return dto;
    }
}
