package com.enershare.mapper;

import com.enershare.dto.user.UserDTO;
import com.enershare.model.user.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;


@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class   UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract UserDTO map(User user);

    public abstract void updateUserDTO(User user, @MappingTarget UserDTO dto);

    @Mapping(source = "password",target ="password" ,qualifiedByName = "passwordEncoder")
    public abstract void updateUser(UserDTO dto, @MappingTarget User user);

    @Named("passwordEncoder")
    String passwordEncoder(String password) {
        return  passwordEncoder.encode(password);
    }


}
