package com.enershare.util.converter;

import com.enershare.dto.user.UserDTO;
import com.enershare.model.user.User;

public class UserConverter {
    public static UserDTO convertUserToDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstname());
        userDTO.setLastName(user.getLastname());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
